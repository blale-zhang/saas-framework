package org.smr.cloud.gateway.filter;


import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.common.cons.RedisUtils;
import org.smr.common.cons.RestStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

import static org.smr.cloud.gateway.filter.ZuulFilterConstants.FILTER_TYPE_PRE;

@Configuration
public class TokenZuulFilter extends BaseZuulFilter {
    private static Logger log = LoggerFactory.getLogger(TokenZuulFilter.class);

    @Value("${app-name.authcenter}")
    private String authcenter;

    @Override
    public String filterType() {
        return FILTER_TYPE_PRE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = getRequest();
        String token = request.getHeader("token");
        return token != null;
    }


    @Override
    public Object run() {
        HttpServletRequest request = getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        String token = request.getHeader("token");
        String appid = request.getHeader("appid");
        if (token == null) {
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }
        String secretKey = authcenter + "-" + appid + "-" + token;
        String phone = RedisUtils.getStr(secretKey);
        if (phone == null) {
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.set("isSuccess",true);
        return null;
    }
}
