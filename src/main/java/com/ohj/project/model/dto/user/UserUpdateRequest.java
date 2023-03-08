package com.ohj.project.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author ohj
 */
@Data
public class UserUpdateRequest implements Serializable {


    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String userAccount;


    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 月份或天数
     */
    @ApiModelProperty("会员数值")
    private Long month;
    @ApiModelProperty("会员数值的单位：天")
    private boolean day;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}