package com.lufficc.simplereader.model;

/**
 * Created by lcc_luffy on 2016/8/9.
 */

public class Result<T> {
    private int code;
    private String msg;
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
