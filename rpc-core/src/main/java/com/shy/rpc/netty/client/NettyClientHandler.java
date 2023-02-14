package com.shy.rpc.netty.client;

import com.shy.rpc.pojo.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/***
 * @author shy
 * @date 2023-02-13 16:33
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        //客户端拿到响应结果
        log.info(String.format("客户端接收到消息: %s", rpcResponse));
        System.out.println(rpcResponse.getData().getClass());
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
        ctx.channel().attr(key).set(rpcResponse);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
