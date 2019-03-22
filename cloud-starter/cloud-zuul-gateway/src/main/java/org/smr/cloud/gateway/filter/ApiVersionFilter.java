package org.smr.cloud.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.collections4.MapUtils;
import org.smr.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户会话过滤
 */
public class ApiVersionFilter extends BaseZuulFilter {
    public String filterType() {
        return ZuulFilterConstants.FILTER_TYPE_PRE;
    }

    public int filterOrder() {
        return 100;
    }

    public boolean shouldFilter() {
        return true;
    }

    /**
     * 1、判断前端是否传入User-SessionId，没有则跳出
     * 2、判断当前Session是否存在
     * @return
     */
    public Object run() {
        logger.info("ApiVersionFilter.....");
        RequestContext context = RequestContext.getCurrentContext();
        /*if(context.get("isSuccess")==null){
            throw new RuntimeException(RestStatus.UNAUTHORIZED.asString());
        }*/
        HttpServletRequest request = getRequest();

        String requestURI = context.getRequest().getRequestURI();
        context.getRequest().setAttribute("key",22);

        String organId = request.getHeader("organId");
        if(StringUtils.isNotEmpty(organId)){

            System.out.println("organId:" +organId);
            logger.debug(" organId:{}", organId);

        }

        String []paths = requestURI.split("/");
        String pathApiVersion = paths[1];
        String pathService = paths[2];
        //添加版本号
        if(context.getRequestQueryParams()==null){
            context.setRequestQueryParams(new HashMap<String, List<String>>());
        }
        if(!context.getRequestQueryParams().containsKey("_API_VERSION")){    //若未传入版本号
            List params = new ArrayList<String>();
            params.add(pathApiVersion+"_"+organId);
            context.getRequestQueryParams().put("_API_VERSION", params);
        }
        //替换请求地址
/*
        requestURI = requestURI.replaceFirst("/" + pathApiVersion + "/" + pathService, "");
*/
        requestURI = requestURI.replaceFirst("/" + pathApiVersion, "");
        context.put("requestURI", requestURI);
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


}
