package com.plf.common.partition;

import org.apache.commons.collections4.ListUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author panlf
 * @date 2023/1/17
 */
public class ApachePartitionExample {
    // 原集合
    public static void main(String[] args) {
        final List<Integer> OLD_LIST = Arrays.asList(1,2,3,4,5);

        List<List<Integer>> newList = ListUtils.partition(OLD_LIST, 3);
        // 打印分片集合
        newList.forEach(i -> {
            System.out.println("集合长度：" + i.size());
        });
    }
}
