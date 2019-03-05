package org.smr.cloud.gateway.filter;

/**
 * 常量
 */
public class ZuulFilterConstants {

    /**请求执行之前filter**/
    public static final String FILTER_TYPE_PRE = "pre";
    /**处理请求，进行路由**/
    public static final String FILTER_TYPE_ROUTE = "route";
    /**请求处理完成后执行的filter**/
    public static final String FILTER_TYPE_POST = "post";
    /**出现错误时执行的filter**/
    public static final String FILTER_TYPE_ERROR = "error";

}
