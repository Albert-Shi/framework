package com.shishuheng.framework.common.module.domain.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shishuheng.framework.common.module.utils.group.UpdateValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
@ApiModel(value = "基础Dto类")
public class BaseDto {
    @NotNull(message = "主键id不能为空", groups = {UpdateValidationGroup.class})
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "created_by_user_id")
    @ApiModelProperty(value = "创建人id")
    private Long createdByUserId;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "最后修改人id")
    private Long lastModifiedUserId;

    @ApiModelProperty(value = "最近修改人")
    private String lastModifiedBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "最近修改时间")
    private Date lastModifiedDate;
}
