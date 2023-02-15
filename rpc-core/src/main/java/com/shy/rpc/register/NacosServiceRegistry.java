package com.shy.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.shy.rpc.enumeration.RpcError;
import com.shy.rpc.exception.RpcException;
import com.shy.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/***
 * nacos服务注册
 * @author shy
 * @date 2023-02-14 14:16
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry{

    //注册服务到注册中心
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {

        try {
            NacosUtil.registerService
                    (serviceName,inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }
}
