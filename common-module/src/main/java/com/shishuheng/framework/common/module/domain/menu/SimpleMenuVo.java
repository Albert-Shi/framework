package com.shishuheng.framework.common.module.domain.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.*;

/**
 * @author shishuheng
 * @date 2020年 03月 15日 20:32:52
 */
@Data
@ApiModel(value = "简化菜单信息")
public class SimpleMenuVo {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "页面路径")
    private String path;

    @ApiModelProperty(value = "子菜单")
    private Set<SimpleMenuVo> subMenu;

    public static SimpleMenuVo trans(Menu menu) {
        SimpleMenuVo vo = new SimpleMenuVo();
        vo.setId(menu.getId());
        vo.setLabel(menu.getLabel());
        vo.setPath(menu.getPath());
        vo.setSubMenu(new HashSet<>());
        return vo;
    }

    public static Set<SimpleMenuVo> createMenuTree(Collection<Menu> menus) {
        ArrayList<Menu> items = new ArrayList<>(menus);
        items.sort(new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if (o1.getId() > o2.getId()) {
                    return 1;
                } else if (o1.getId() < o2.getId()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        Set<SimpleMenuVo> tree = new LinkedHashSet<>();
        Map<Long, SimpleMenuVo> menuMap = new HashMap<>();
        for (Menu m : items) {
            SimpleMenuVo vo = trans(m);
            menuMap.put(m.getId(), vo);
            if (null == m.getParentId()) {
                tree.add(vo);
            } else {
                SimpleMenuVo parent = menuMap.get(m.getParentId());
                if (null != parent) {
                    parent.getSubMenu().add(vo);
                }
            }
        }
        return tree;
    }
}
