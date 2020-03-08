package com.shishuheng.webdemo.domain.base;

import com.shishuheng.webdemo.domain.status.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author shishuheng
 * @date 2020年 03月 08日 11:54:49
 */
@Data
@MappedSuperclass
public class BaseStatusEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    @ApiModelProperty(value = "状态")
    private Status status;
}
