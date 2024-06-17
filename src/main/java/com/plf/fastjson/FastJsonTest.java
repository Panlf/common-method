package com.plf.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Breeze
 * @date 2024/1/1
 */
public class FastJsonTest {

    @Test
    public void testToCollectionBean(){
        String result = "[{\"id\":1,\"name\":\"aa\"},{\"id\":2,\"name\":\"bb\"}]";
        List<User> users = JSONObject.parseObject(result, new TypeReference<List<User>>(){});
        System.out.println(users);
    }

    @Test
    public void testJsonFeature(){
        User user = new User();
        user.setId(null);
        user.setName(null);
        System.out.println(FastJsonUtils.toJsonString(user));
    }

}
