package com.ohj.project.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Hipopaaaaa
 * @create 2023/3/1 20:00
 * 用户过期判定请求体
 */
@Data
public class UserJudgeRequest {

     private static final long serialVersionUID = 3191241716463120793L;
     @ApiModelProperty("账号")
     private String userAccount;
}
