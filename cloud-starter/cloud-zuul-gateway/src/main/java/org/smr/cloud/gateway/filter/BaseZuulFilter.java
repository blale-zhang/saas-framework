package org.smr.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 基础过滤器
 */
public abstract class BaseZuulFilter extends ZuulFilter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected HttpServletRequest getRequest(){
        return RequestContext.getCurrentContext().getRequest();
    }

    protected HttpSession getSession(){
        return getRequest().getSession();
    }

    protected HttpServletResponse getResponse(){
        return RequestContext.getCurrentContext().getResponse();
    }


    /**
     * 写出内容
     * @param str
     */

    protected void write(String str){
        HttpServletResponse response = getResponse();
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            os.write(str.getBytes());
            os.flush();
        } catch (IOException e) {
            logger.error("Zuul response error:", e);
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("Zuul response close error:", e);
                }
            }
        }
    }

}
