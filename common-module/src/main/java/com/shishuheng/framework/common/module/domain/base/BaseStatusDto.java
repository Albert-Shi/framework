package com.shishuheng.framework.common.module.domain.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shishuheng
 * @date 2020/2/26 9:34 上午
 */
@Data
@ApiModel(value = "基础包含状态的Dto类")
public class BaseStatusDto extends BaseDto {
    @ApiModelProperty(value = "状态id")
    private Long status;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "状态标签")
    private String statusLabel;
}
