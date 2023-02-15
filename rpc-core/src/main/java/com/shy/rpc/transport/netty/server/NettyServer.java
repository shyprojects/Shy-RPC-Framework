package com.shy.rpc.transport.netty.server;

import com.shy.rpc.RpcServer;
import com.shy.rpc.codec.MessageDecoder;
import com.shy.rpc.codec.MessageEncoder;
import com.shy.rpc.hook.ShutdownHook;
import com.shy.rpc.provider.DefaultServiceProvider;
import com.shy.rpc.provider.ServiceProvider;
import com.shy.rpc.register.NacosServiceRegistry;
import com.shy.rpc.register.ServiceRegistry;
import com.shy.rpc.serializer.KryoSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/***
 * Netty服务器端
 * @author shy
 * @date 2023-02-13 14:45
 */
@Slf4j
public class NettyServer implements RpcServer {


    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public NettyServer(String host,int port){
        this.host = host;
        this.port = port;
        serviceProvider = new DefaultServiceProvider();
        serviceRegistry = new NacosServiceRegistry();
    }
    @Override
    public void start() {
        //注册钩子，在服务器结束的时候注销服务
        ShutdownHook.getShutdownHook().addClearAllHook();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) //日志处理器
                    .option(ChannelOption.SO_BACKLOG, 256) //全连接队列大小
                    .option(ChannelOption.SO_KEEPALIVE, true) //没有响应2小时进行一次探测存活
                    .childOption(ChannelOption.TCP_NODELAY, true) //关闭nagle算法确保消息及时发送
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new MessageEncoder(new KryoSerializer()));
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.info("服务器启动出现异常:{}", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    //注册具体服务
    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(),new InetSocketAddress(host,port));
    }
}
