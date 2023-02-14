package com.shy.rpc.netty.server;

import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import com.shy.rpc.register.DefaultServiceRegistry;
import com.shy.rpc.register.ServiceRegistry;
import com.shy.rpc.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/***
 * @author shy
 * @date 2023-02-13 15:00
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        log.info("服务器收到消息:{}", request);
        //通过接口名获取对应的实现类
        Object service = serviceRegistry.getService(request.getInterfaceName());
        //调用该实现类对应的方法获取返回值
        Object res = requestHandler.handler(request, service);
        //封装response对象返回给客户端
        RpcResponse<Object> resp = RpcResponse.ok(res);
        channelHandlerContext.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
