package com.plf.pattern.state.order;

/**
 * 使用状态模式解决if else过多的问题
 * @author Lancer
 * @date 2023-12-15
 */
public class OrderMain {
    public static void main(String[] args) {
        Order order = new Order();

        System.out.println(order.getState());
        order.nextState();
        System.out.println(order.getState());
        order.nextState();
        System.out.println(order.getState());
    }
}
