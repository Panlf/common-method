package com.plf.pattern.state.order;

/**
 * @author Lancer
 * @date 2023-12-15
 */
public enum OrderStateEnum {
    UNPAY{
        @Override
        public void nextState(Order order){
            order.setState(PAID);
        }
    },
    PAID {
        @Override
        public void nextState(Order order){
            order.setState(FINISHED);
        }
    },
    FINISHED {
        @Override
        public void nextState(Order order) {

        }
    };

    public abstract void nextState(Order order);
}
