package com.rzx.commonlibrary.http;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/6
 */
public class ApiException extends RuntimeException {

    private int code;
    private String displayMessage;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
