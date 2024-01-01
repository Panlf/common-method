package com.plf.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Breeze
 * @date 2024/1/1
 */
public class FastJsonTutorial {

    @Test
    public void testTypeReference(){
        String result = "[{\"id\":1,\"name\":\"aa\"},{\"id\":2,\"name\":\"bb\"}]";
        List<User> users = JSONObject.parseObject(result, new TypeReference<List<User>>(){});
        System.out.println(users);
    }

}
