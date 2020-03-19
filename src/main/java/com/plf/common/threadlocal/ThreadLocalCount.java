package com.plf.common.threadlocal;

public class ThreadLocalCount {

    private static ThreadLocal<Integer> threadLocalCount = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public int nextCount() {
    	threadLocalCount.set(threadLocalCount.get() +1);
        return threadLocalCount.get();
    }

    public static void main(String [] args) {
    	ThreadLocalCount count = new ThreadLocalCount();

    	CountThread thread1 = new CountThread(count);
    	CountThread thread2 = new CountThread(count);
    	CountThread thread3 = new CountThread(count);
    	CountThread thread4 = new CountThread(count);

    	thread1.start();
    	thread2.start();
    	thread3.start();
    	thread4.start();
    }

    public static class CountThread extends Thread {

        private ThreadLocalCount threadLocalCount;

        public CountThread(ThreadLocalCount threadLocalCount) {
            this.threadLocalCount = threadLocalCount;
        }

        @Override
        public void run() {
            for (int i=0; i<3; i++) {
                System.out.println(Thread.currentThread().getName()+" threadLocalCount:"+threadLocalCount.nextCount());
            }
        }
    }
 }
