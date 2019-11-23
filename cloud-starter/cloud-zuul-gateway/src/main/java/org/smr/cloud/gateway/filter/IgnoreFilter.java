package org.smr.cloud.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.smr.cloud.gateway.filter.ZuulFilterConstants.FILTER_TYPE_PRE;


/*@Configuration
@ConfigurationProperties(prefix = "app-name")*/
public class IgnoreFilter extends BaseZuulFilter {

    private Map<String, String> ignoreUrl=new HashMap<String, String>();

    public Map<String, String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(Map<String, String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    @Override
    public String filterType() {
        return FILTER_TYPE_PRE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = getRequest();
        String path = request.getServletPath();
        boolean flag = false;
        for (String u : ignoreUrl.values()) {
            if (path.startsWith("/" + u, path.indexOf("/", 1))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.set("isSuccess", true);
        logger.info("single login is ok");
        return null;
    }

    /**
     *
     * attention:简单跨域就是GET，HEAD和POST请求，但是POST请求的"Content-Type"只能是application/x-www-form-urlencoded, multipart/form-data 或 text/plain
     * 反之，就是非简单跨域，此跨域有一个预检机制，说直白点，就是会发两次请求，一次OPTIONS请求，一次真正的请求
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("OPTIONS");// 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");// 允许Get的请求方法
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
