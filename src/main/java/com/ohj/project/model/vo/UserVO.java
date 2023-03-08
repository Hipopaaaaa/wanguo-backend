package com.ohj.project.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class UserVO implements Serializable {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 状态（0-未登陆，1-已登陆）
     */
    private String status;


    /**
     * 过期时间
     */
    private String pastTime;


    /**
     * 创建时间
     */
    private String createTime;


    private static final long serialVersionUID = 1L;
}