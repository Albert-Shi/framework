package com.shishuheng.framework.authentication.domain.base;

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
    @ApiModelProperty(value = "状态")
    private Integer status;
}
