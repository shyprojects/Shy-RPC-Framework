package com.shy.rpc.serializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shy.rpc.enumeration.SerializerCode;
import com.shy.rpc.pojo.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/***
 * Json的序列化器
 * @author shy
 * @date 2023-02-13 12:19
 */
@Slf4j
public class JsonSerializer implements CommonSerializer{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化时有错误发生: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object res = objectMapper.readValue(bytes, clazz);
            if(res instanceof RpcRequest) {
                res = handleRequest(res);
            }
            return res;
        } catch (IOException e) {
            log.info("反序列化出现异常:{}",e);
        }
        return null;
    }

    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for(int i = 0; i < rpcRequest.getParamTypes().length; i ++) {
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }


    @Override
    public int getCode() {
        return SerializerCode.JSON.getSerializerCode();
    }
}
