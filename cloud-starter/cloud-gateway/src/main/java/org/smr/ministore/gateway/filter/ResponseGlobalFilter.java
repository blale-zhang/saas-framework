package org.smr.ministore.gateway.filter;

import static org.springframework.cloud.gateway.filter.NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

import org.apache.commons.lang.StringEscapeUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultClientResponse;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Cash Zhang
 * @version v1.0
 * @since 2019/03/16 19:20
 */
@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

    private static final String SUCCESS_PREFIX = "{\"status\":50001,\"message\":\"success\",\"data\":";
    private static final String SUCCESS_SUFFIX = "}";

    @Override
    public int getOrder() {
        // -1 is response write filter, must be called before that
        return WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(
                exchange.getResponse()) {

            @Override
            @SuppressWarnings("unchecked")
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {

                String originalResponseContentType = exchange
                        .getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                HttpHeaders httpHeaders = new HttpHeaders();
                //explicitly add it in this way instead of 'httpHeaders.setContentType(originalResponseContentType)'
                //this will prevent exception in case of using non-standard media types like "Content-Type: image"
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);
                ResponseAdapter responseAdapter = new ResponseAdapter(body, httpHeaders);
                DefaultClientResponse clientResponse = new DefaultClientResponse(responseAdapter,
                        ExchangeStrategies
                                .withDefaults());

                //TODO: flux or mono
                Mono modifiedBody = clientResponse.bodyToMono(String.class).flatMap(originalBody -> {
                    if (originalResponse.getStatusCode() != null) {
                        if (originalResponse.getStatusCode().is2xxSuccessful()) {
                            originalBody = StringEscapeUtils.unescapeJavaScript(SUCCESS_PREFIX) + originalBody
                                        + SUCCESS_SUFFIX;

                        }
                    }
                    return Mono.just(originalBody);
                });

                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                        exchange.getResponse().getHeaders());
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            long contentLength1 = getDelegate().getHeaders().getContentLength();
                            Flux<DataBuffer> messageBody = outputMessage.getBody();
                            //TODO: if (inputStream instanceof Mono) {
                            HttpHeaders headers = getDelegate().getHeaders();
                            if (/*headers.getContentLength() < 0 &&*/ !headers
                                    .containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                messageBody = messageBody
                                        .doOnNext(data -> headers.setContentLength(data.readableByteCount()));
                            }
                            // }
                            //TODO: use isStreamingMediaType?
                            return getDelegate().writeWith(messageBody);
                        }));
            }

            @Override
            public Mono<Void> writeAndFlushWith(@NonNull
                                                        Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body)
                        .flatMapSequential(p -> p));
            }
        };

        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }


    public class ResponseAdapter implements ClientHttpResponse {

        private final Flux<DataBuffer> flux;
        private final HttpHeaders headers;

        public ResponseAdapter(Publisher<? extends DataBuffer> body, HttpHeaders headers) {
            this.headers = headers;
            if (body instanceof Flux) {
                flux = (Flux<DataBuffer>) body;
            } else {
                flux = ((Mono) body).flux();
            }
        }

        @Override
        public @NonNull
        Flux<DataBuffer> getBody() {
            return flux;
        }

        @Override
        public @NonNull
        HttpHeaders getHeaders() {
            return headers;
        }

        @Override
        public @NonNull
        HttpStatus getStatusCode() {
            return null;
        }

        @Override
        public int getRawStatusCode() {
            return 0;
        }

        @Override
        public @NonNull
        MultiValueMap<String, ResponseCookie> getCookies() {
            return null;
        }
    }

}