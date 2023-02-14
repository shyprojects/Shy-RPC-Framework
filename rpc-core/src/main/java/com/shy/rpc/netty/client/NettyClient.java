package com.shy.rpc.netty.client;

import com.shy.rpc.RpcClient;
import com.shy.rpc.codec.MessageDecoder;
import com.shy.rpc.codec.MessageEncoder;
import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import com.shy.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/***
 *
 * Netty客户端
 * @author shy
 * @date 2023-02-13 14:44
 */
@Slf4j
public class NettyClient implements RpcClient {
    private String host;
    private Integer port;
    private static Bootstrap bootstrap;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    //初始化客户端连接
    static {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap()
                .group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageDecoder());
                        ch.pipeline().addLast(new MessageEncoder(new JsonSerializer()));
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            //连接服务器端
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //连接成功,打印服务端消息
            log.info("连接服务器成功 host:{},port:{}", host, port);
            //发送消息到服务器端
            Channel channel = channelFuture.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        log.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
            }
            channel.closeFuture().sync();
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            RpcResponse rpcResponse = channel.attr(key).get();
            Object data = rpcResponse.getData();
            return data;
            } catch(InterruptedException e){
                log.info("连接发生异常:{}", e);
            }
            return null;
    }
}