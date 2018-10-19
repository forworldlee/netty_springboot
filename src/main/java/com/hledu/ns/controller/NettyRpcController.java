package com.hledu.ns.controller;

import com.hledu.ns.rpc.client.RpcClient;
import com.hledu.ns.rpc.client.RpcClientConfig;
import com.hledu.ns.rpc.body.RequestBody;
import com.hledu.ns.rpc.body.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@RestController
public class NettyRpcController {
    @Autowired
    private RpcClient rpcClient;

    @Resource
    private RpcClientConfig rpcClientConfig;

    @RequestMapping(path = "/getAge")
    public Integer getAge(@RequestParam("name") String name) throws InterruptedException {
        RequestBody requestBody = new RequestBody(rpcClientConfig.getGetAgeSvc(),name);

        ResponseBody responseBody = rpcClient.submitRequest(requestBody);
        return ((Integer) responseBody.getResponseBody());
    }

}
