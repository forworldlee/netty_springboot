package com.hledu.ns.rpc.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@Configuration
@PropertySource("rpc_client.properties")
public class RpcClientConfig {
    @Value("${hledu.rpc.server.host}")
    private String host;
    @Value("${hledu.rpc.server.port:16962}")
    private int port;
    @Value("${SVC_NAME_GET_AGE}")
    private String getAgeSvc;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getGetAgeSvc() {
        return getAgeSvc;
    }

    public void setGetAgeSvc(String getAgeSvc) {
        this.getAgeSvc = getAgeSvc;
    }
}
