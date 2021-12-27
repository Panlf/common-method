package com.plf.common.closeable;

import org.junit.jupiter.api.Test;

/**
 * @author panlf
 * @date 2021/12/27
 */
public class AutoCloseableTest {

    @Test
    public void test(){
        try(NetMessage netMessage = new NetMessage()){
            netMessage.sendMessage("Hello World");
        }catch (Exception e){
            System.out.println("发生了异常==>"+e.getMessage());
        }
    }
}
