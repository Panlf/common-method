package com.plf.pattern.single;

/**
 * 懒汉式(线程安全，同步代码块)[不可用]
 * @author panlf
 * @date 2021/10/6
 */
public class Singleton5 {
    //由于第四种实现方式同步效率太低，所以摒弃同步方法，改为同步产生实例化的的代码块。
    //但是这种同步并不能起到线程同步的作用。
    //跟第3种实现方式遇到的情形一致，假如一个线程进入了if (singleton == null)判断语句块，还未来得及往下执行，
    //另一个线程也通过了这个判断语句，这时便会产生多个实例。
    private static Singleton5 singleton;

    private Singleton5() {}

    public static Singleton5 getInstance() {
        if (singleton == null) {
            synchronized (Singleton5.class) {
                singleton = new Singleton5();
            }
        }
        return singleton;
    }
}
