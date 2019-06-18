package org.smr.common.utils;


import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum RestStatus {

    SUCCESS(0),
    ERROR(-1),
    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, "请求失败"),
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "未授权访问"),
    PAYMENT_REQUIRED(HttpServletResponse.SC_PAYMENT_REQUIRED, "支付请求"),
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, "禁止访问"),
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "内容不存在"),
    METHOD_NOT_ALLOW(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "请求方法不允许"),
    NOT_ACCEPTABLE(HttpServletResponse.SC_NOT_ACCEPTABLE, "请求不接受"),
    PROXY_AUTHENTICATION_REQUIRED(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED, "代理认证请求"),
    REQUEST_TIMEOUT(HttpServletResponse.SC_REQUEST_TIMEOUT, "请求超时"),
    CONFLICT(HttpServletResponse.SC_CONFLICT, "请求冲突"),
    GONE(HttpServletResponse.SC_GONE, "请求失效"),
    LENGTH_REQUIRED(HttpServletResponse.SC_LENGTH_REQUIRED, "没有定义Content-Length头"),
    PRECONDITION_FAILED(HttpServletResponse.SC_PRECONDITION_FAILED, "请求先决条件失败"),
    REQUEST_ENTITY_TOO_LARGE(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "请求实体太大"),
    REQUEST_URI_TOO_LONG(HttpServletResponse.SC_REQUEST_URI_TOO_LONG, "请求URI过长"),
    UNSUPPORTED_MEDIA_TYPE(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持的媒体类型"),
    REQUESTED_RANGE_NOT_SATISFIABLE(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE, "所请求的范围无法满足"),
    EXPECTATION_FAILED(HttpServletResponse.SC_EXPECTATION_FAILED, "预期结果失败"),

    SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),
    NOT_IMPLEMENTED(HttpServletResponse.SC_NOT_IMPLEMENTED, "未实现"),
    BAD_GATEWAY(HttpServletResponse.SC_BAD_GATEWAY, "网关失败"),
    GATEWAY_TIMEOUT(HttpServletResponse.SC_GATEWAY_TIMEOUT, "网关超时"),
    SERVICE_UNAVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "服务不可用"),
    HTTP_VERSION_NOT_SUPPORTED(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED, "HTTP版本不支持");

    Integer code;
    String msg;

    RestStatus(Integer code){
        this.code = code;
        switch (code){
            case 0 : this.msg = "执行成功" ;break;
            case -1 : this.msg = "系统繁忙，请稍后再试" ;break;
            default: this.msg = "系统未定义";
        }
    }

    RestStatus(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Map<String, Object> asMap(){
        Map map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        return map;
    }

    public RestStatus setCode(Integer code) {
        this.code = code;
        return  this;
    }

    public RestStatus setMsg(String msg) {
        this.msg = msg;
        return  this;
    }

    public String asString(){
        return FastJsonUtils.toString(this.asMap());
    }

    @Override
    public String toString() {
        return FastJsonUtils.toString(this.asMap());
    }
}
