package com.shy.rpc.codec;

import com.shy.rpc.enumeration.MessageType;
import com.shy.rpc.enumeration.SerializerCode;
import com.shy.rpc.exception.RpcException;
import com.shy.rpc.pojo.RpcRequest;
import com.shy.rpc.pojo.RpcResponse;
import com.shy.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

import static com.shy.rpc.enumeration.RpcError.ERROR_MSG_TYPE;

/***
 * 自定义编解码器
 * @author shy
 * @date 2023-02-13 15:29
 */
public class MessageEncoder extends MessageToByteEncoder {

    private CommonSerializer serializer;

    private static final int MAGIC_NUM = 0xABABABAB;

    public MessageEncoder(CommonSerializer serializer){
        this.serializer = serializer;
    }

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
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {
        //写入四个字节魔术:
        out.writeInt(MAGIC_NUM);
        //写入消息类型
        if (msg instanceof RpcRequest){
            out.writeInt(MessageType.REQUEST_MSG.getCode());
        } else if (msg instanceof RpcResponse) {
            out.writeInt(MessageType.RESPONSE_MSG.getCode());
        }else {
            throw new RpcException(ERROR_MSG_TYPE);
        }
        //写入序列化方式
        out.writeInt(serializer.getCode());
        //序列化消息
        byte[] bytes = serializer.serialize(msg);
        //写入消息长度
        out.writeInt(bytes.length);
        //写入消息
        out.writeBytes(bytes);
    }

}
