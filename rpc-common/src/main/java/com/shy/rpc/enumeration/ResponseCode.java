package com.shy.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/***
 * 响应状态码及其消息: 200 --> 成功 , 500 --> 失败
 * @author shy
 * @date 2023-02-12 18:51
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(200,"调用方法成功"),
    FAIL(500,"调用方法失败"),
    METHOD_NOT_FOUND(500,"未找到指定方法"),
    NOT_FOUND_CLASS(500,"未找到指定类");

    private Integer code;
    private String msg;
}
