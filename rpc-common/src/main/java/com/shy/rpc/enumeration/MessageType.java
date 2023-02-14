package com.shy.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 消息类型枚举
 * @author shy
 * @date 2023-02-13 15:41
 */
@AllArgsConstructor
@Getter
public enum MessageType {

    REQUEST_MSG(0),
    RESPONSE_MSG(1);

    private final int code;
}
