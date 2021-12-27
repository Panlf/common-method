package com.plf.common.closeable;

/**
 * @author panlf
 * @date 2021/12/27
 */
public interface IMessage extends AutoCloseable {
    void sendMessage(String message);

    void close() throws Exception;
}
