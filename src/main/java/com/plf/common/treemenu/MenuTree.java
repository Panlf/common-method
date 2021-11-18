package com.plf.common.treemenu;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author panlf
 * @date 2021/11/10
 */
public class MenuTree {
    public static void main(String[] args) {
        List<Menu> list = new ArrayList<Menu>();
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("系统管理");
        menu.setOrdinal(1);
        menu.setParentId(0L);
        menu.setPath("");
        menu.setIcon("icon-1");
        list.add(menu);
        menu = new Menu();
        menu.setId(2L);
        menu.setName("数据管理管理");
        menu.setOrdinal(2);
        menu.setParentId(0L);
        menu.setPath("");
        menu.setIcon("icon-2");
        list.add(menu);
        menu = new Menu();
        menu.setId(3L);
        menu.setName("菜单管理");
        menu.setOrdinal(2);
        menu.setParentId(1L);
        menu.setPath("Menu Path");
        menu.setIcon("icon-3");
        list.add(menu);
        menu = new Menu();
        menu.setId(4L);
        menu.setName("数据校验");
        menu.setOrdinal(1);
        menu.setParentId(2L);
        menu.setPath("Data Valid");
        menu.setIcon("icon-4");
        list.add(menu);


        //获取父节点
        List<Menu> collect = list.stream().filter(m -> m.getParentId() == 0).map(
                m -> {
                    m.setChildList(getChildren(m, list));
                    return m;
                }
        ).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));

    }


    /**
     * 递归查询子节点
     * @param menu  根节点
     * @param menusList   所有节点
     * @return 根节点信息
     */
    private static List<Menu> getChildren(Menu menu, List<Menu> menusList) {
        List<Menu> children = menusList.stream().filter(m -> {
            return Objects.equals(m.getParentId(), menu.getId());
        }).map(
                m -> {
                    m.setChildList(getChildren(m, menusList));
                    return m;
                }
        ).collect(Collectors.toList());
        return children;
    }
}
