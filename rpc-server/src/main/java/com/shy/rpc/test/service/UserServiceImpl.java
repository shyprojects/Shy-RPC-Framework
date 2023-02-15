package com.shy.rpc.test.service;

import com.shy.rpc.api.pojo.User;
import com.shy.rpc.api.service.UserService;

/***
 * @author shy
 * @date 2023-02-14 11:03
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getById(int i) {
        return new User(1,"张三");
    }
}
