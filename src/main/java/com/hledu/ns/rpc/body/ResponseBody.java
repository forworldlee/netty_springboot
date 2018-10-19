package com.hledu.ns.rpc.body;

import java.io.Serializable;

/**
 * RPC响应报文
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
public class ResponseBody implements Serializable {
    private static final long serialVersionUID = 6106740624758017436L;

    private String status;
    private Object responseBody;

    public ResponseBody(String status, Object responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}
