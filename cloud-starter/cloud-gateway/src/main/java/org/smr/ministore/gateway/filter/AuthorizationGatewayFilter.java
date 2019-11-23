package org.smr.ministore.gateway.filter;

import io.jsonwebtoken.Claims;
import org.smr.common.constant.CommonConstant;
import org.smr.ministore.security.conf.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * token校验过滤器
 * @Version V1.0
 */
public class AuthorizationGatewayFilter implements GatewayFilter, Ordered {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.REQ_HEADER);

        if (token == null) {
            token = request.getQueryParams().getFirst(CommonConstant.REQ_HEADER);
        }

        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token) ) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        token = token.replace(token, CommonConstant.TOKEN_SPLIT);

        Claims claims = JwtHelper.parseJWT(token);
        //
        if ( claims == null ) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}