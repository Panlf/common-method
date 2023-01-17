package com.plf.netty.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class DataHandler extends ChannelHandlerAdapter {

    private final Channel channel;

    public DataHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取读取的数据， 是一个缓冲。
        ByteBuf readBuffer = (ByteBuf) msg;
        System.out.println("get data: " + readBuffer.toString(CharsetUtil.UTF_8));
        //这里的复位不能省略,不然会因为计数器问题报错.
        readBuffer.retain();
        //将数据发到远程客户端那边
        channel.writeAndFlush(readBuffer);
    }
}
