package com.plf.common.repeat;

import org.apache.commons.collections4.map.LRUMap;

/**
 * @author panlf
 * @date 2021/9/2
 */
public class IsRepeatUtils {

    // 根据 LRU(Least Recently Used，最近最少使用)算法淘汰数据的 Map 集合，最大容量 100 个
    private static LRUMap<String, Integer> reqCache = new LRUMap<>(100);

    /**
     * 幂等性判断
     * @return
     */
    public static boolean isRepeat(String id, Object lockClass) {
        synchronized (lockClass) {
            // 重复请求判断
            if (reqCache.containsKey(id)) {
                return false;
            }
            // 非重复请求，存储请求 ID
            reqCache.put(id, 1);
        }
        return true;
    }
}
