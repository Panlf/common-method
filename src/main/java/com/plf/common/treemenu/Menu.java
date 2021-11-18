package com.plf.common.treemenu;

import com.plf.common.treenode.BaseMenu;
import lombok.Data;

import java.util.List;

/**
 * @author panlf
 * @date 2021/11/10
 */
public class Menu extends BaseMenu {
    /**
     * 子节点信息
     */
    public List<Menu> childList;

    public List<Menu> getChildList() {
        return childList;
    }

    public void setChildList(List<Menu> childList) {
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "childList=" + childList +
                '}';
    }
}
