package com.shishuheng.framework.common.module.domain.department;

import com.shishuheng.framework.common.module.domain.base.BaseStatusEntity;
import com.shishuheng.framework.common.module.domain.permission.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/3/23 3:56 下午
 */
@Data
@Entity
@Api(tags = "部门")
public class Department extends BaseStatusEntity {
    @Column(name = "label", nullable = false)
    @ApiModelProperty(value = "标签")
    private String label;

    @Column(name = "code", nullable = false, unique = true)
    @ApiModelProperty(value = "部门代码")
    private String code;

    @Column(name = "parent_id")
    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @Column(name = "level")
    @ApiModelProperty(value = "层级 根级目录是0")
    private Integer level;

    @Column(name = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ManyToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "权限列表")
    private Set<Permission> permissions;

    public Department(String label, String code, Integer sort, Set<Permission> permissions, Department parent) {
        if (null != parent) {
            this.parentId = parent.getParentId();
            this.level = 0;
        } else {
            this.parentId = null;
            this.level = 0;
        }
        this.label = label;
        this.code = code;
        this.sort = sort;
        this.permissions = permissions;
    }

    public Department() {
    }
}
