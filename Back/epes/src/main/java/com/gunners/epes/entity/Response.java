package com.gunners.epes.entity;

import java.io.Serializable;

public class Response implements Serializable {

    // 响应业务状态
    private String status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static Response ok(){
        return new Response(null);
    }

    public static Response ok(Object data) {
        return new Response(data);
    }

    public static Response ok(Object data, String msg){
        return new Response(data, msg);
    }

    public Response(Object data){
        this.status = "200";
        this.msg = "success";
        this.data = data;
    }

    public Response(Object data, String msg){
        this.status = "200";
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
