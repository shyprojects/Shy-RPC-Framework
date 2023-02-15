package com.shy.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.shy.rpc.enumeration.SerializerCode;
import com.shy.rpc.exception.SerializeException;
import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/***
 * Kryo的序列化器
 * @author shy
 * @date 2023-02-14 10:40
 */
@Slf4j
public class KryoSerializer implements CommonSerializer{

    /**
     * Kryo的序列化对象线程不安全,文档建议使用ThreadLocal进行存储
     */
    private final static ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(()->{
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        //配置成 false 的话，序列化速度更快，但是遇到循环引用，就会报 “栈内存溢出” 错误
        kryo.setReferences(true);
        //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream);
        ){
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output,obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (IOException e) {
            log.error("kryo序列化出现异常{}",e);
            throw new SerializeException("序列化异常");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
           Input input =  new Input(byteArrayInputStream)
        ){
            Kryo kryo = kryoThreadLocal.get();
            Object res = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return res;
        } catch (IOException e) {
            log.error("反序列化时有错误发生:", e);
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.Kryo.getSerializerCode();
    }
}
