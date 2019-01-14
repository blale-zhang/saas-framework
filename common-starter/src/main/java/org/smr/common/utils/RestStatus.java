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
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "未授权访问"),
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, "禁止访问"),
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "内容不存在"),
    SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常");

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
