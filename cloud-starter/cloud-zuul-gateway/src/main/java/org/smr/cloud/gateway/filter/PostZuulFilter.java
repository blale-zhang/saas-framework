package org.smr.cloud.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.common.utils.RestStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.smr.cloud.gateway.filter.ZuulFilterConstants.FILTER_TYPE_POST;

/**
 * 监听Post回的报文，主要功能进行异常处理，并统一格式返回
 */
public class PostZuulFilter extends BaseZuulFilter {
    private static Logger log = LoggerFactory.getLogger(PostZuulFilter.class);
    @Override
    public String filterType() {
        return FILTER_TYPE_POST;
    }
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 后期类型处理
     * @return
     */
    public boolean shouldFilter() {
        return true;
    }

    public Object run() {


        RequestContext context = RequestContext.getCurrentContext();
        /**
         * 大于http1.1 大于400小于505的属于系统异常
         */
        if(getResponse().getStatus()>= HttpServletResponse.SC_BAD_REQUEST
                   && getResponse().getStatus()<HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED){

            write(RestStatus.SERVER_ERROR.setCode(getResponse().getStatus()).asString() );
            return null;
        }

        log.info("request is ok:"+getResponse().getStatus());



        HttpServletRequest request = getRequest();
        try {
           String param = this.getBodyString(request.getReader());
           JSONObject josnObject = JSON.parseObject(param);
            String organId = MapUtils.getString(josnObject,"organId");

            String requestURI = context.getRequest().getRequestURI();
            context.getRequest().setAttribute("key",22);

            String []paths = requestURI.split("/");
            String pathApiVersion = paths[1];
            String pathService = paths[2];

           if(josnObject.containsKey("organId")){

               RibbonFilterContextHolder.getCurrentContext()
                       .add("lancher", organId);

               System.out.println("organId:" +organId);
               logger.debug(" organId:{}", organId);

           }


            //添加版本号
            if(context.getRequestQueryParams()==null){
                context.setRequestQueryParams(new HashMap<String, List<String>>());
            }
            if(!context.getRequestQueryParams().containsKey("_API_VERSION")){    //若未传入版本号
                List params = new ArrayList<String>();
                params.add(pathApiVersion);
                context.getRequestQueryParams().put("_API_VERSION", params);
            }
            //替换请求地址
            requestURI = requestURI.replaceFirst("/" + pathApiVersion + "/" + pathService, "");
            context.put("requestURI", requestURI);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //获取request请求body中参数
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }

    public PostZuulFilter(){
        log.debug("{} 初始化", PostZuulFilter.class);
    }

}
