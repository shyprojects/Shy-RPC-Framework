package com.shy.rpc.serializer;



/***
 * 序列化接口,提供序列化反序列化方法,序列化类需要实现该接口
 * @author shy
 * @date 2023-02-13 12:14
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes,Class<?> clazz);

    //获取具体的序列化类
    static CommonSerializer getSerializer(int serializerCode){
        switch (serializerCode){
            case 1:
                return new JsonSerializer();
            default:
                throw new RuntimeException("错误的序列化方式");
        }
    }
    //获取该序列化方式对应的code值
    int getCode();
}
