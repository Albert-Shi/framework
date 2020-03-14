package com.shishuheng.framework.common.module.domain.managed;

import com.shishuheng.framework.common.module.domain.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author shishuheng
 * @date 2020/3/3 10:55 上午
 */

@Data
@Entity
@ApiModel(value = "管理的实体信息")
public class ManagedEntity extends BaseEntity {
    @Column(name = "label")
    @ApiModelProperty(value = "实体标签")
    private String label;

    @Column(name = "description")
    @ApiModelProperty(value = "实体描述")
    private String description;

    @Column(name = "className")
    @ApiModelProperty(value = "类名称")
    private String className;

    @Column(name = "from_service")
    @ApiModelProperty(value = "所属服务端")
    private String fromServiceClient;
}
