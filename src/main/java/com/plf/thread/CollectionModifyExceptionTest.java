package com.plf.thread;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Collection<User> users = new CopyOnWriteArrayList<User>();
				//new ArrayList<User>();
		users.add(new User("张三",28));
		users.add(new User("李四",30));
		users.add(new User("王五",35));
		Iterator<User> itrUsers = users.iterator();
		while(itrUsers.hasNext()){
			User user = itrUsers.next();
			if("张三".equals(user.getName())){
				users.remove(user);
			}else{
				System.out.println(user);
			}
		}
	}

}

class User {
	private String name;
	private Integer age;
	public User(String name,Integer age){
		this.name=name;
		this.age=age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
	
}