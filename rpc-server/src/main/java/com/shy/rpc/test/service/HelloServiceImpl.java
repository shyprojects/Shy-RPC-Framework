package com.shy.rpc.test.service;

import com.shy.rpc.annotation.Service;
import com.shy.rpc.api.HelloObject;
import com.shy.rpc.api.service.HelloService;
import lombok.extern.slf4j.Slf4j;

/***
 * @author shy
 * @date 2023-02-12 19:56
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(HelloObject object) {
        log.info("接收到消息:{}",object);
        return "接收到消息";
    }
}
