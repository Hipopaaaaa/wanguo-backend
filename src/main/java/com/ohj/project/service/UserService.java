package com.ohj.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ohj.project.model.dto.user.UserJudgeRequest;
import com.ohj.project.model.dto.user.UserLogoutRequest;
import com.ohj.project.model.dto.user.UserUpdateRequest;
import com.ohj.project.model.entity.User;
import com.ohj.project.model.vo.UserLoginVO;
import com.ohj.project.model.vo.UserRegisterVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author ohj
 */
public interface UserService extends IService<User> {

    /**
     * 注册用户
     * @param month 开通的会员月份
     * @param email 邮箱
     * @param isday 判断month的值是否是天数
     * @return
     */
    UserRegisterVO userRegister(Long month, String email,boolean isday);


    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param password 用户密码
     * @return 脱敏后的用户信息
     */
    UserLoginVO userLogin(String userAccount, String password);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(UserLogoutRequest request);

    /**
     * 更新用户
     * @param userUpdateRequest
     * @return
     */
    boolean updatePasswordOrMonth(UserUpdateRequest userUpdateRequest);

    /**
     * 删除所有过期的用户
     * @return
     */
    boolean removeALlDatedUser();

    /**
     * 用户过期判定
     * @param userJudgeRequest
     * @return
     */
    boolean userJudgement(UserJudgeRequest userJudgeRequest);
}
