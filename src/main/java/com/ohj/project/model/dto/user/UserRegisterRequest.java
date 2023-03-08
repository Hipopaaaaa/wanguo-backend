package com.ohj.project.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author ohj
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @ApiModelProperty("会员数值")
    private Long month;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("会员数值的单位：天")
    private boolean day;
}
