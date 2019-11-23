package org.smr.ministore.gateway.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.blade.msg.DataMessage;
import org.blade.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.common.constant.CommonConstant;
import org.smr.common.constant.GlobalConstant;
import org.smr.ministore.security.conf.JwtHelper;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

/**
 * 权限控制
 */
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationGatewayFilterFactory.class);


    private RedisTemplate redisTemplate;

    private String[] excludeUris;

    public AuthorizationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
           /* if (!config.isEnabled()) {
                return chain.filter(exchange);
            }*/

            URI uri = exchange.getRequest().getURI();
            logger.debug("{} , 登录权限过滤器，开始", AuthorizationGatewayFilterFactory.class);

            logger.debug("请求URI ：{} ", uri);

            for( String excludeUri : excludeUris ){
                if(uri.toString().indexOf(excludeUri) >= 0 ){
                    return chain.filter(exchange);
                }
            }

            String jwtToken = exchange.getRequest().getHeaders().getFirst(CommonConstant.REQ_HEADER);
            jwtToken = jwtToken.replaceAll("bearer ","");
            //校验jwtToken的合法性
            if (jwtToken != null) {

                Claims claims = JwtHelper.parseJWT(jwtToken);
                if(ObjectUtils.notEqual(claims, null)){
                    Object userId = claims.get(GlobalConstant.USER_ID_KEY);
                    Object organId = claims.get(GlobalConstant.ORGAN_ID_KEY);
                    String loginUserKey = String.format(GlobalConstant.AUTHORIZATION_USER_OF_ORGAN_KEY, userId, organId);

                    Map userLoginInfo = redisTemplate.boundHashOps(loginUserKey).entries();

                    String token = MapUtils.getString(userLoginInfo, GlobalConstant.TOKEN_KEY);

                    if(!jwtToken.equals( token )){
                        return responseNologin( exchange );
                    }
                    // 合法
                    // 将用户id作为参数传递下去
                    return chain.filter(exchange);
                }

                return responseNologin( exchange );
            }

            return responseNologin( exchange );
        };
    }


    private Mono<Void> responseNologin(ServerWebExchange exchange ) {
        //不合法(响应未登录的异常)
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        String warningStr = "未登录或登录超时";
        Message message = new DataMessage(401, warningStr,null);
        String messageJSON = JSON.toJSONString(message);
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(messageJSON.getBytes());

        return response.writeWith(Mono.just(bodyDataBuffer));

    }


    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {}

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public String[] getExcludeUris() {
        return excludeUris;
    }

    public void setExcludeUris(String[] excludeUris) {
        this.excludeUris = excludeUris;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}