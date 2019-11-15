package com.rzx.commonlibrary.base;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/5
 */
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public T getData() {
        if (null == data) {
            return (T) "";
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}
