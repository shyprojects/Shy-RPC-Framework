package com.shy.rpc.test;

import com.alibaba.nacos.api.exception.NacosException;
import com.shy.rpc.annotation.ServiceScan;
import com.shy.rpc.transport.RpcServer;
import com.shy.rpc.transport.netty.server.NettyServer;

import java.util.ArrayList;
import java.util.List;


/***
 * @author shy
 * @date 2023-02-12 19:53
 */
@ServiceScan
public class TestServer {
    public static void main(String[] args) throws NacosException {
//        RpcServer server = new NettyServer("127.0.0.1",8080);
//        server.start();
    }
}

