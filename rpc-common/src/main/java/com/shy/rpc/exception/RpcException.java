package com.shy.rpc.exception;

import com.shy.rpc.enumeration.RpcError;

/***
 * 调用过程中发生的异常
 * @author shy
 * @date 2023-02-13 10:48
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}
