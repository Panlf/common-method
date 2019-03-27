package com.plf.common.copy;

import java.util.ArrayList;

public class ShallowCopy implements Cloneable {
	/**
	 * 拷贝时候虽然创建了新的对象，但是并没有调用构造方法
	 * 对象中的引用对象并没有拷贝，引用的地址还是和原对象一致
	 * 基本类型或者 String 默认会拷贝
	 * 像这种只拷贝了对象本身，而对象中引用数据类型没有被拷贝的拷贝方式，叫做浅拷贝。
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
    public ShallowCopy() {
        System.out.println("shallow copy test");
    }
    @Override
    protected ShallowCopy clone() throws CloneNotSupportedException {
        return (ShallowCopy) super.clone();
    }
    public static void main(String[] args) throws CloneNotSupportedException {
        ShallowCopy shallow = new ShallowCopy();
        shallow.setName("yhx");
        shallow.addListValue("Java");
        
        shallow.printlnName();  // 输出 yhx
        shallow.printlnList();  // 输出 Java
        
        ShallowCopy shallowCopy = shallow.clone(); // 克隆
        // 打印出两个对象的地址
        System.out.println(shallow);
        System.out.println(shallowCopy);
        
        shallowCopy.printlnList(); // 输出 Java
        
        shallowCopy.addListValue("Python");
        shallowCopy.printlnList(); // 输出 Java,Python
        shallowCopy.printlnName(); // 输出 yhx
        
        shallowCopy.setName("hello");
        shallow.printlnName(); // 输出 yhx
        shallow.printlnList(); // // 输出 Java,Python
    }
}
