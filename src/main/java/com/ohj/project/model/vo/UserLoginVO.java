package com.ohj.project.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Hipopaaaaa
 * @create 2023/3/1 12:45
 * 用户登陆视图
 */
@Data
@Accessors(chain = true)
public class UserLoginVO {
     private String email;

     /**
      * 登陆状态： 1-成功登陆 2-登陆失败
      */
     private String status;
}
