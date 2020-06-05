package com.plf.common.treenode;

import java.util.List;

/**
 * 菜单树列表
 * @author Panlf
 * @param <T> 
 */
public class Node<T> {

	//当前数据
	private T data;
	
	//key值相当于id
	private Long key;
	
	//目前数据的名称
	private String title;
	
	//子级的数据
	private List<Node<T>> children;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<T>> children) {
		this.children = children;
	}
}
