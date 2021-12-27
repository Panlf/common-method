package com.plf.common.closeable;

/**
 * @author panlf
 * @date 2021/12/27
 */
public class NetMessage implements IMessage {

    public NetMessage(){
        System.out.println("[服务器建立了连接...]");
    }

    @Override
    public void sendMessage(String message) {
        throw new RuntimeException("发生了异常，与服务器断掉了");
        //System.out.println("[服务器正在发送信息===>"+message+"]");
    }

    @Override
    public void close() throws Exception {
        System.out.println("[服务器关闭了...]");
    }
}
