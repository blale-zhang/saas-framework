package org.smr.cloud.gateway.filter;


import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.common.cons.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.smr.cloud.gateway.filter.ZuulFilterConstants.FILTER_TYPE_ERROR;

/**
 * 统一处理Filter异常情况
 */
@Configuration
public class ErrorZuulFilter extends BaseZuulFilter {
    private static Logger log = LoggerFactory.getLogger(ErrorZuulFilter.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String filterType() {
        return FILTER_TYPE_ERROR;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Throwable th= ctx.getThrowable();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        HttpServletResponse response = ctx.getResponse();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        if(null!=th) {
            if (null != th.getCause())
                ctx.setResponseBody(th.getCause().getMessage());
        }else{
            ctx.setResponseBody(RestStatus.SERVER_ERROR.asString());
        }
        //FastJsonUtils.toString()
        ctx.setSendZuulResponse(false);
        return null;
    }
}
