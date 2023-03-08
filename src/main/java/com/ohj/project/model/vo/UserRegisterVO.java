package com.ohj.project.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Hipopaaaaa
 * @create 2023/3/1 12:33
 * 创建用户视图
 */
@Data
@Accessors(chain = true)
public class UserRegisterVO {
     /**
      * 账号
      */
     private String userAccount;

     /**
      * 密码
      */
     private String password;

}
