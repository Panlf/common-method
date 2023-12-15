package com.plf.pattern.state.order;

import lombok.Data;

/**
 * @author Lancer
 * @date 2023-12-15
 */
@Data
public class Order {
    private OrderStateEnum state;

    public Order(){
        state = OrderStateEnum.UNPAY;
    }

    public void nextState(){
        state.nextState(this);
    }
}
