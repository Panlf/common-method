package com.plf.common.treenode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NodeTree {

	public static void main(String[] args) throws JsonProcessingException {
		List<SysMenu> list = new ArrayList<SysMenu>();
		SysMenu sysMenu = new SysMenu();
		sysMenu.setId(1L);
		sysMenu.setName("系统管理");
		sysMenu.setOrdinal(1);
		sysMenu.setParentId(0L);
		sysMenu.setPath("");
		sysMenu.setType(0);
		sysMenu.setIcon("icon-1");
		list.add(sysMenu);
		sysMenu = new SysMenu();
		sysMenu.setId(2L);
		sysMenu.setName("数据管理管理");
		sysMenu.setOrdinal(2);
		sysMenu.setParentId(0L);
		sysMenu.setPath("");
		sysMenu.setType(0);
		sysMenu.setIcon("icon-2");
		list.add(sysMenu);
		sysMenu = new SysMenu();
		sysMenu.setId(3L);
		sysMenu.setName("菜单管理");
		sysMenu.setOrdinal(2);
		sysMenu.setParentId(1L);
		sysMenu.setPath("Menu Path");
		sysMenu.setType(1);
		sysMenu.setIcon("icon-3");
		list.add(sysMenu);
		sysMenu = new SysMenu();
		sysMenu.setId(4L);
		sysMenu.setName("数据校验");
		sysMenu.setOrdinal(1);
		sysMenu.setParentId(2L);
		sysMenu.setPath("Data Valid");
		sysMenu.setType(1);
		sysMenu.setIcon("icon-4");
		list.add(sysMenu);
		ObjectMapper mapper = new ObjectMapper();
		List<Node<SysMenu>> listNode = getChildTree(0l,list);
		System.out.println(mapper.writeValueAsString(listNode));
	}
	
	
	public static <T> List<Node<T>> getChildTree(Long parentId, List<T> list) {
		if (list == null || list.size()<=0) {
			return null;
		} else {
			List<Node<T>> result = null;
			Iterator<T> data = list.iterator();
			while (data.hasNext()) {
				T menu = data.next();
				
				BaseMenu basemenu = (BaseMenu)menu;
				
				if (basemenu.getParentId() == parentId) {
					if (result == null) {
						result = new ArrayList<>();
					}
					Node<T> node = new Node<T>();
					node.setData(menu);
					node.setKey(basemenu.getId());
					node.setTitle(basemenu.getName());
					node.setChildren(getChildTree(basemenu.getId(), list));
					result.add(node);
				}
			}
			return result;
		}
	}
}
