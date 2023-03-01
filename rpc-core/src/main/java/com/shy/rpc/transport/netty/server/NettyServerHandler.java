package com.shy.rpc.transport.netty.server;

import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import com.shy.rpc.handler.RequestHandler;
import com.shy.rpc.provider.DefaultServiceProvider;
import com.shy.rpc.provider.ServiceProvider;
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
    private static ServiceProvider serviceProvider;

    static {
        requestHandler = new RequestHandler();
        serviceProvider = new DefaultServiceProvider();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        log.info("服务器收到消息:{}", request);
        //通过接口名获取对应的实现类
        Object service = serviceProvider.getServiceProvider(request.getInterfaceName());
        //调用该实现类对应的方法获取返回值
        Object res = requestHandler.handler(request, service);
        //封装response对象返回给客户端
        RpcResponse<Object> resp = RpcResponse.ok(res);
        resp.setRequestId(request.getRequestId());
        channelHandlerContext.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
