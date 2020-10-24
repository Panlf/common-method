package com.plf.common.treemap;

public class TreeMapTest {

    public static void main(String[] args) {
        TreeMap<Integer,String> map = new TreeMap<>();
        map.put(5,"aaa");
        map.put(1,"bbb");
        map.put(9,"ccc");
        map.put(98,"ddd");
        map.put(65,"eee");
        map.put(12,"ggg");

        map.put(12,"kkk");

        System.out.println(map.get(5));
        System.out.println(map.get(4));
        System.out.println(map.remove(9));
        System.out.println(map.remove(5));

        System.out.println(map.toString());
    }
}
