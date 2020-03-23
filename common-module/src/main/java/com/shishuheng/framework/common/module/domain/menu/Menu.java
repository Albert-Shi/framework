package com.shishuheng.framework.common.module.domain.menu;

import com.shishuheng.framework.common.module.domain.base.BaseStatusEntity;
import com.shishuheng.framework.common.module.domain.status.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author shishuheng
 * @date 2020年 03月 15日 14:04:43
 */
@Data
@Entity
@ApiModel(value = "菜单")
public class Menu extends BaseStatusEntity {
    @Column(name = "label", nullable = false)
    @ApiModelProperty(value = "标签")
    private String label;

    @Column(name = "path")
    @ApiModelProperty(value = "页面路径")
    private String path;

    @Column(name = "parent_id")
    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @Column(name = "level")
    @ApiModelProperty(value = "层级 根级目录是0")
    private Integer level;

    @Column(name = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    public Menu(String label, String path, Long parentId, Integer level, Integer sort, Status status) {
        this.label = label;
        this.path = path;
        this.parentId = parentId;
        this.level = level;
        this.sort = sort;
        this.setStatus(status);
    }

    public Menu(String label, String path, Long parentId, Integer sort, Status status) {
        this.label = label;
        this.path = path;
        this.parentId = parentId;
        this.sort = sort;
        this.setStatus(status);
    }

    /**
     * 根据父菜单创建
     *
     * @param label
     * @param path
     * @param sort
     * @param parent
     */
    public Menu(String label, String path, Integer sort, Menu parent, Status status) {
        this.label = label;
        this.path = path;
        this.sort = sort;
        this.parentId = parent.getId();
        this.level = parent.getLevel() + 1;
        this.setStatus(status);
    }

    /**
     * 创建根菜单
     *
     * @param label
     * @param sort
     */
    public Menu(String label, Integer sort, Status status) {
        this.label = label;
        this.sort = sort;
        this.level = 0;
        this.setStatus(status);
    }

    public Menu() {
    }
}
