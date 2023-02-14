package com.shy.rpc.test;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.shy.rpc.api.service.HelloService;
import com.shy.rpc.api.service.UserService;
import com.shy.rpc.netty.server.NettyServer;
import com.shy.rpc.register.DefaultServiceRegistry;
import com.shy.rpc.register.ServiceRegistry;
import com.shy.rpc.test.service.HelloServiceImpl;
import com.shy.rpc.test.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.Serializable;


/***
 * @author shy
 * @date 2023-02-12 19:53
 */
public class TestServer {
    public static void main(String[] args) throws Exception {

        HelloService helloService = new HelloServiceImpl();
        UserService userService = new UserServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        serviceRegistry.register(userService);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(8080);
        //        test();
    }

    static void test() throws IOException {
        Person person = new Person("张三");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(person);
        Person person1 = objectMapper.readValue(bytes, Person.class);
        System.out.println(person1);
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    static class Person implements Serializable {
        private String name;
    }
}
