package com.shy.rpc.codec;

import com.shy.rpc.enumeration.MessageType;
import com.shy.rpc.enumeration.RpcError;
import com.shy.rpc.exception.RpcException;
import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import com.shy.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.shy.rpc.enumeration.RpcError.ERROR_MSG_TYPE;

/***
 * @author shy
 * @date 2023-02-13 15:50
 */
@Slf4j
public class MessageDecoder extends ReplayingDecoder {
    private static final int MAGIC_NUM = 0xABABABAB;
    /**
     * 规定协议:
     * +---------------+---------------+-----------------+-------------+
     * |  Magic Number |  Message Type | Serializer Type | Data Length |
     * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
     * +---------------+---------------+-----------------+-------------+
     * |                          Data Bytes                           |
     * |                   Length: ${Data Length}                      |
     * +---------------------------------------------------------------+
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        //读取魔术
        int magic = in.readInt();
        if (MAGIC_NUM != magic){
            log.error("不识别的协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        //读取消息类型
        int msgType = in.readInt();
        Class<?> clazz;
        if (msgType == MessageType.REQUEST_MSG.getCode()){
            clazz = RpcRequest.class;
        }else if (msgType == MessageType.RESPONSE_MSG.getCode()){
            clazz = RpcResponse.class;
        }else {
            throw new RpcException(ERROR_MSG_TYPE);
        }
        //读取序列化方式
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getSerializer(serializerCode);
        //读取消息长度
        int msgLength = in.readInt();
        byte[] bytes = new byte[msgLength];
        in.readBytes(bytes);
        Object res = serializer.deserialize(bytes, clazz);
        list.add(res);
    }
}
