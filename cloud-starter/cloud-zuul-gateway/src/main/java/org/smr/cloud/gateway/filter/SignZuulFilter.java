package org.smr.cloud.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.common.utils.RSAUtils;
import org.smr.common.utils.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static org.smr.cloud.gateway.filter.ZuulFilterConstants.FILTER_TYPE_PRE;


@Configuration
public class SignZuulFilter extends BaseZuulFilter {
    private static Logger log = LoggerFactory.getLogger(SignZuulFilter.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${app-name.portal}")
    private String protalname;

    @Override
    public String filterType() {
        return FILTER_TYPE_PRE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        HttpServletRequest request = getRequest();
        String rsaSign = request.getHeader("rsaSign");
        return rsaSign!=null;
    }

    public Object run() {
        HttpServletRequest request = getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        String sign = request.getHeader("rsaSign");
        if (!valid(request.getContentType())) {
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }
        if (sign == null) {
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }
        String appid = request.getHeader("appid");
        if (!redisTemplate.hasKey(protalname + "-applist")) {
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }
        Map<String, String> applist = redisTemplate.boundHashOps(protalname + "-applist").entries();
        if (applist.containsKey(appid)) {
            try {
                String pubKey = applist.get(appid);
                String s = IOUtils.toString(request.getInputStream(), "utf-8");
                boolean verify = RSAUtils.verify(pubKey, s, sign);
                if (!verify) {
                    throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
                }
            } catch (IOException e) {
                throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
            } catch (Exception e) {
                throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
            }
        } else {
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.set("isSuccess",true);
        log.info("request is ok");
        return null;
    }

    private boolean valid(String type) {
        return type!=null?type.contains(ContentType.APPLICATION_JSON.getMimeType()):false;

    }
}
