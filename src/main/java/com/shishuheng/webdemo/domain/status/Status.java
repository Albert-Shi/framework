package com.shishuheng.webdemo.domain.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shishuheng.webdemo.domain.base.BaseEntity;
import com.shishuheng.webdemo.domain.entity.ManagedEntity;
import com.shishuheng.webdemo.domain.permission.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author shishuheng
 * @date 2020/3/3 10:06 上午
 */
@Data
@Entity
@ApiModel(value = "状态")
public class Status extends BaseEntity {
    @Column(name = "code", nullable = false)
    @ApiModelProperty(value = "状态代码")
    private String code;

    @Column(name = "label", nullable = false)
    @ApiModelProperty(value = "标签")
    private String label;

    @Column(name = "description")
    @ApiModelProperty(value = "状态描述")
    private String description;

    @ManyToOne
    @JoinColumn(name = "effectEntity")
    private ManagedEntity effectEntity;

    @OneToMany(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "此状态拥有的权限")
    private Set<Permission> permissions;

    @Transient
    private Status status = null;

    public Status() {
    }

    public Status(String code, String label, String description, ManagedEntity effectEntity, Set<Permission> permissions) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.effectEntity = effectEntity;
        this.permissions = permissions;
        this.setCreatedDate(new Date());
    }
}
