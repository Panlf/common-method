package com.plf.copy;

import java.util.ArrayList;

public class DeepCopy implements Cloneable{
   /**
    * 如果字段上有 final 修饰，就不能实现 clone 方法了，因为 final 变量不能再次被赋值。
    * 
    * 原型模式中会使用到拷贝。
    */
	private String name;
    private ArrayList<String> list = new ArrayList<String>();
    public void printlnName() {
        System.out.println(this.name);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void addListValue(String value) {
        this.list.add(value);
    }
    public void printlnList() {
        System.out.println(this.list);
    }
    public DeepCopy() {
        System.out.println("deep copy test");
    }
    @SuppressWarnings("unchecked")
	@Override
    protected DeepCopy clone() throws CloneNotSupportedException {
        DeepCopy clone = (DeepCopy) super.clone();
        clone.list = (ArrayList<String>) this.list.clone();
        return clone;
    }
    public static void main(String[] args) throws CloneNotSupportedException {
        DeepCopy deep = new DeepCopy();
        deep.setName("yhx");
        deep.addListValue("Java");
        deep.printlnName(); 
        deep.printlnList();  
        DeepCopy DeepCopy = deep.clone(); // 克隆
        // 打印出两个对象的地址
        System.out.println(deep);
        System.out.println(DeepCopy);
        DeepCopy.printlnList(); 
        DeepCopy.addListValue("Python");
        DeepCopy.printlnList(); 
        DeepCopy.printlnName(); 
        DeepCopy.setName("hello");
        deep.printlnName(); 
        deep.printlnList(); 
    }
}