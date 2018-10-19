package com.hledu.ns.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@Slf4j
@Component
public class RpcServer {

    @Resource
    private RpcServerConfig rpcServerConfig;


    @PostConstruct
    public void startRpcServer() throws InterruptedException {
        new Thread(()->{
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup,workerGroup)
                        .option(ChannelOption.SO_BACKLOG,128)
                        .childOption(ChannelOption.SO_KEEPALIVE,true)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new RpcServerChannelHandlerInitializer());
                SocketAddress socketAddress = new InetSocketAddress(rpcServerConfig.getHost(),rpcServerConfig.getPort());
                ChannelFuture future = serverBootstrap.bind(socketAddress).sync();
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException("RpcServer出错",e);
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }).start();

    }

}
