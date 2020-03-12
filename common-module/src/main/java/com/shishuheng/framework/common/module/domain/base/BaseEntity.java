package com.shishuheng.framework.common.module.domain.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
@MappedSuperclass
@ApiModel(value = "基础实体类")
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "created_by_user_id")
    @ApiModelProperty(value = "创建人id")
    private Long createdByUserId;

    @CreatedBy
    @Column(name = "created_by")
    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @Column(name = "last_modified_user_id")
    @ApiModelProperty(value = "最后修改人id")
    private Long lastModifiedUserId;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    @ApiModelProperty(value = "最近修改人")
    private String lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "最近修改时间")
    private Date lastModifiedDate;
}
