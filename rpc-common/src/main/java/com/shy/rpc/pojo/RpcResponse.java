package com.shy.rpc.pojo;

import com.shy.rpc.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/***
 * @author shy
 * @date 2023-02-12 18:48
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer statusCode;

    /**
     * 响应状态补充信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public static <T>RpcResponse<T> ok(T data){
        RpcResponse<T> resp = new RpcResponse<>();
        resp.statusCode = ResponseCode.SUCCESS.getCode();
        resp.message = ResponseCode.SUCCESS.getMsg();
        resp.data = data;
        return resp;
    }
    public static <T>RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> resp = new RpcResponse<>();
        resp.statusCode = code.getCode();
        resp.message = code.getMsg();
        return resp;
    }
}
