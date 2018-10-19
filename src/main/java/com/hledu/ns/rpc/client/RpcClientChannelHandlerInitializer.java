package com.hledu.ns.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
public class RpcClientChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {
    private RpcClientHandler rpcClientHandler;

    public RpcClientChannelHandlerInitializer() {
    }

    public RpcClientChannelHandlerInitializer(RpcClientHandler rpcClientHandler) {
        this.rpcClientHandler = rpcClientHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理的拆包、粘包的解、编码器
        pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4,0,4));
        pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));

        //处理序列化的解、编码器（JDK默认的序列化）
        pipeline.addLast("encoder",new ObjectEncoder());
        pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)));
        pipeline.addLast(this.rpcClientHandler);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
