package com.shy.rpc.server;

import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/***
 * @author shy
 * @date 2023-02-13 11:25
 */
@Slf4j
public class RequestHandlerThread implements Runnable {
    private Object service;
    private Socket socket;
    private RequestHandler requestHandler;
    public RequestHandlerThread(Object service, Socket socket,RequestHandler requestHandler) {
        this.service = service;
        this.socket = socket;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object res = requestHandler.handler(rpcRequest, service);
            objectOutputStream.writeObject(RpcResponse.ok(res));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用或发送时有错误发生：", e);
        }
    }
}
