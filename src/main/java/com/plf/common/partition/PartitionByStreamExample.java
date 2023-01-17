package com.plf.common.partition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author panlf
 * @date 2023/1/17
 */
public class PartitionByStreamExample {

    public static void main(String[] args) {
        final List<Integer> OLD_LIST = Arrays.asList(1,2,3,4,5);
        Map<Boolean, List<Integer>> newMap = OLD_LIST.stream().collect(
                Collectors.partitioningBy(i -> i > 3)
        );
        // 打印结果
        System.out.println(newMap);

    }
}
