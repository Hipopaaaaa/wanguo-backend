package com.ohj.project.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户删除请求体
 *
 * @author ohj
 */
@Data
public class UserDeleteRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;
    @ApiModelProperty("账号")
    private String userAccount;


}
