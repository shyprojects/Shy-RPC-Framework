package com.shy.rpc.transport.socket.client;

import com.shy.rpc.pojo.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/***
 * @author shy
 * @date 2023-02-12 19:26
 */
@Slf4j
public class RpcClient {
    public Object sendRequest(RpcRequest request,int port,String host){
        try (Socket socket = new Socket(host, port)){
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(request);
            oos.flush();
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.info("错误:{}",e);
            return null;
        }
    }
}