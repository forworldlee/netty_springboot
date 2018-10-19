package com.hledu.ns.rpc.client;

import com.alibaba.fastjson.JSONObject;
import com.hledu.ns.rpc.body.RequestBody;
import com.hledu.ns.rpc.body.ResponseBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@Slf4j
@Component
public class RpcClient {


    @Resource
    private RpcClientConfig rpcClientConfig;

    public ResponseBody submitRequest(RequestBody requestBody) {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        RpcClientHandler rpcClientHandler = new RpcClientHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new RpcClientChannelHandlerInitializer(rpcClientHandler));
            InetSocketAddress remoteAddress = new InetSocketAddress(rpcClientConfig.getHost(), rpcClientConfig.getPort());
            ChannelFuture sync = bootstrap.connect(remoteAddress).sync();
            sync.channel().writeAndFlush(requestBody).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("RPC请求出错",e);
        } finally {
            clientGroup.shutdownGracefully();
        }

        Object rb = rpcClientHandler.getResponseBody();
        if (rb instanceof ResponseBody) {
            ResponseBody responseBody = (ResponseBody) rb;
            log.info("rpc client get result:"+responseBody);
            if ("ok".equals(responseBody.getStatus())) {
                log.info("["+JSONObject.toJSONString(requestBody)+"]RPC请求成功");
                return responseBody;
            }else {
                throw new RuntimeException("["+JSONObject.toJSONString(requestBody)+"]RPC请求失败");
            }
        }else {
            throw new RuntimeException("["+JSONObject.toJSONString(requestBody)+"]RPC请求失败");
        }

    }

}
