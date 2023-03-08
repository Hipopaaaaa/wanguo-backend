package com.ohj.project.model.dto.user;

import com.ohj.project.common.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author ohj
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {


    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String userAccount;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;


    ///**
    // * 创建时间
    // */
    //@ApiModelProperty("注册时间")
    //private Date createTime;
    //
    //
    ///**
    // * 过期时间
    // */
    //@ApiModelProperty("过期时间")
    //private Date pastTime;




    private static final long serialVersionUID = 1L;
}