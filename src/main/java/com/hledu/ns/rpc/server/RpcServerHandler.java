package com.hledu.ns.rpc.server;

import com.alibaba.fastjson.JSONObject;
import com.hledu.ns.rpc.body.RequestBody;
import com.hledu.ns.rpc.body.ResponseBody;
import com.hledu.ns.spring.SpringContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.Serializable;

/**
 * RPC服务端接收请求报文，处理报文
 * @program: netty_springboot
 * @description: ${description}
 * @author: Forwardlee
 * @create: 2018-06-23
 **/
@Slf4j
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            log.info("RPC服务器收到消息："+JSONObject.toJSONString(msg));
            if (msg instanceof RequestBody) {
                RequestBody requestBody = (RequestBody) msg;
                String serviceName = requestBody.getServiceName();
                Serializable param = requestBody.getParam();
                SpelExpressionParser parser = new SpelExpressionParser();
                StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
                evaluationContext.setBeanResolver(new BeanFactoryResolver(SpringContext.getContext()));
                evaluationContext.setVariable("param", param);
                Object responseBody = parser.parseExpression(serviceName).getValue(evaluationContext);
                log.info(ctx.channel().remoteAddress()+"-RPC请求执行结果："+JSONObject.toJSONString(responseBody));
                ctx.writeAndFlush(new ResponseBody("ok", responseBody));
            }
        } finally {
            //ctx.writeAndFlush中已经包含，可省略。
            ReferenceCountUtil.release(msg);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
