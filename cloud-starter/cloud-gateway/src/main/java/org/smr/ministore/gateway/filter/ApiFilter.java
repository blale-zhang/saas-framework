package org.smr.ministore.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class ApiFilter  implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String uri = exchange.getRequest().getURI().toString();
        if ( StringUtils.isNotBlank(uri) ) {

            if(uri.indexOf("api") >= 0){

               HttpHeaders httpHeaders =  exchange.getRequest().getHeaders();
               List<String> tokens = httpHeaders.get("Authorization");

               if(tokens != null){

                   for(String token : tokens) {
                       System.out.println(token);
                   }
               }
            }
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -99;
    }
}
