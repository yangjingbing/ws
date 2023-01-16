package com.ws.until;

import lombok.Data;

/**
 * 通用的接口输出信息
 */
@Data
public class RestUntil {

    private Object data;
    private String status;
    private String msg;

    /*public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }*/
}
