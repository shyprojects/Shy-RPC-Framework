package com.shy.rpc.api.service;

import com.shy.rpc.api.HelloObject;

/***
 * 通用接口
 * @author shy
 * @date 2023-02-12 19:01
 */
public interface HelloService {
    String hello(HelloObject object);
}