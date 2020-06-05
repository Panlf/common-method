package com.plf.common.treenode;

/**
 * 继承BaseMenu进行扩展
 * @author Panlf
 *
 */
public class SysMenu extends BaseMenu {

	/**
	 * 0 目录 1菜单  2按钮
	 */
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SysMenu [type=" + type + ", getType()=" + getType() + ", getId()=" + getId() + ", getName()="
				+ getName() + ", getPath()=" + getPath() + ", getParentId()=" + getParentId() + ", getIcon()="
				+ getIcon() + ", getOrdinal()=" + getOrdinal() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
