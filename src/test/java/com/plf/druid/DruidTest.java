package com.plf.druid;

import com.alibaba.druid.DbType;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.jupiter.api.Test;

/**
 * @author panlf
 * @date 2021/8/29
 */
public class DruidTest {

    @Test
    public void test(){
        System.out.println(DruidUtils.parseSql("select id,name from user where id=1 order by id desc", DbType.mysql));
    }
}
