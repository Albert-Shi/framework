package com.shishuheng.webdemo.domain.base;

import com.alibaba.fastjson.JSON;
import com.shishuheng.webdemo.utils.CommonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.BindingResult;

/**
 * @author shishuheng
 * @date 2020/2/28 9:28 上午
 */
@Data
@ApiModel(value = "响应结果")
public class Result<T> {
    @ApiModelProperty(value = "错误代码")
    private Integer code;

    @ApiModelProperty(value = "错误信息")
    private String message;

    @ApiModelProperty(value = "数据对象")
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T data) {
        this.data = data;
        trans(Exceptions.SUCCESS);
    }

    public Result(String... message) {
        if (null != message && message.length > 0) {
            trans(Exceptions.ERROR);
            if (message.length == 1) {
                this.message = message[0];
            } else {
                this.message = JSON.toJSONString(message);
            }
        } else {
            trans(Exceptions.SUCCESS);
        }
    }

    public Result(Exceptions e) {
        trans(e);
    }

    public Result(BindingResult result) {
        String message = CommonUtil.getValidateErrorMessage(result);
        if (null != message) {
            trans(Exceptions.ERROR);
            this.message = message;
        } else {
            trans(Exceptions.SUCCESS);
        }
    }

    public static Integer successCode() {
        return Exceptions.SUCCESS.code;
    }

    private void trans(Exceptions e) {
        this.code = e.code;
        this.message = e.msg;
    }

    public enum Exceptions {
        SUCCESS(200, "OK"),
        ERROR(500, "服务器内部错误"),
        PERMISSION_DENIED(600, "无操作权限"),
        ;

        Integer code;
        String msg;

        Exceptions(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
