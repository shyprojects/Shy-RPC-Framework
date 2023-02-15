package com.shy.rpc.exception;

/***
 * 序列化异常
 * @author shy
 * @date 2023-02-14 10:57
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}