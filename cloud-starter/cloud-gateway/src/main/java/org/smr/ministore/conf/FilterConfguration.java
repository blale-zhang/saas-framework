package org.smr.ministore.conf;

import org.smr.ministore.gateway.filter.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class FilterConfguration {



    @Value("${smr.uri.exclude}")
    private String[] excludeUris;


    //@Bean
    public ApiFilter apiFilter(){

        return new ApiFilter();
    }


    private static final String MAX_AGE = "18000L";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders headers = response.getHeaders();
                String origin = requestHeaders.getOrigin();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders
                        .getAccessControlRequestHeaders());
                if (requestMethod != null) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
                }
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }

            }
            return chain.filter(ctx);
        };
    }

/*    @Bean
    public JwtCheckGatewayFilterFactory jwtCheckGatewayFilterFactory(){
        return new JwtCheckGatewayFilterFactory();
    }*/


    @Bean
    public AuthorizationGatewayFilterFactory authorizationGatewayFilterFactory(RedisTemplate redisTemplate){
        AuthorizationGatewayFilterFactory authorizationGatewayFilterFactory = new AuthorizationGatewayFilterFactory();
        authorizationGatewayFilterFactory.setRedisTemplate(redisTemplate);
        authorizationGatewayFilterFactory.setExcludeUris(excludeUris);
        return authorizationGatewayFilterFactory;
    }

/*
    @Bean
    public MyModifyResponseBodyGatewayFilterFactory MyModifyRequestBodyGatewayFilterFactory( ){
        MyModifyResponseBodyGatewayFilterFactory authorizationGatewayFilterFactory = new MyModifyResponseBodyGatewayFilterFactory(
                new MyServerCodecConfigurer()
        );

        return authorizationGatewayFilterFactory;
    }
*/



}
