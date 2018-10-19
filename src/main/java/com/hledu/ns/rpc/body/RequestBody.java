package com.hledu.ns.rpc.body;

import java.io.Serializable;

/**
 * RPC请求报文
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
public class RequestBody implements Serializable {

    private static final long serialVersionUID = 639481406900464202L;

    private String serviceName;

    private Serializable param;

    public RequestBody(String serviceName, Serializable param) {
        this.serviceName = serviceName;
        this.param = param;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Serializable getParam() {
        return param;
    }

    public void setParam(Serializable param) {
        this.param = param;
    }
}
