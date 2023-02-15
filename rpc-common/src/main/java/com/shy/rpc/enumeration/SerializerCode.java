package com.shy.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 枚举序列化方式与对应的code值，在字节流中标识
 * @author shy
 * @date 2023-02-13 14:40
 */
@Getter
@AllArgsConstructor
public enum SerializerCode {
    Kryo(0),
    JSON(1);
    int serializerCode;
}
