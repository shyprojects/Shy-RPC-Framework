package com.shy.rpc.transport;

import com.shy.rpc.annotation.Service;
import com.shy.rpc.annotation.ServiceScan;
import com.shy.rpc.enumeration.RpcError;
import com.shy.rpc.exception.RpcException;
import com.shy.rpc.provider.ServiceProvider;
import com.shy.rpc.register.ServiceRegistry;
import com.shy.rpc.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author shy
 * @date 2023-02-15 15:06
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer {

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices() {
        //获取main();所在类的类名
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if(!startClass.isAnnotationPresent(ServiceScan.class)) {
                log.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            log.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        //获取ServiceScan值
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if("".equals(basePackage)) {
            //默认扫描main();所在类的包及其子包
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        //扫描包下所有类，找出含有@Service的类
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for(Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                //注册所有服务
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }
    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }
}
