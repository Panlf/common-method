package com.plf.common.treenode;

import lombok.Data;

/**
 * menu菜单
 * @author panlf
 */
@Data
public class BaseMenu {

	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 地址
	 */
	private String path;
	
	/**
	 * 父级id
	 */
	private Long parentId;
	
	/**
	 * 图标
	 */
	private String icon;
	
	/**
	 * 序数
	 */
	private Integer ordinal;

}
