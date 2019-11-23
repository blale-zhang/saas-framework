package org.smr.ministore.entities;

/**
 * 响应对象
 *
 * @param <T>
 */
public class ResponseVo<T> {

    /**
     * 状态码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的数据.
     */
    private T data;

    public ResponseVo() {
    }


    public ResponseVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}