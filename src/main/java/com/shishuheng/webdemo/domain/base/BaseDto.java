package com.shishuheng.webdemo.domain.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shishuheng.webdemo.domain.user.User;
import com.shishuheng.webdemo.helper.group.UpdateValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private User createdBy;

    @ApiModelProperty(value = "最近修改人")
    private User lastModifiedBy;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "最近修改时间")
    private Date lastModifiedDate;
}