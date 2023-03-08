package com.ohj.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.ohj.project.common.BaseResponse;
import com.ohj.project.common.ErrorCode;
import com.ohj.project.common.ResultUtils;
import com.ohj.project.exception.BusinessException;
import com.ohj.project.model.dto.user.*;
import com.ohj.project.model.entity.User;
import com.ohj.project.model.enums.UserEnum;
import com.ohj.project.model.vo.UserLoginVO;
import com.ohj.project.model.vo.UserRegisterVO;
import com.ohj.project.model.vo.UserVO;
import com.ohj.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户接口
 *
 * @author ohj
 */
@Api(value = "用户管理中心",tags = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // region 登录相关

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public BaseResponse<UserRegisterVO> userRegister(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long month = userRegisterRequest.getMonth();
        String email = userRegisterRequest.getEmail();
        boolean isDay = userRegisterRequest.isDay();
        if (StringUtils.isAnyBlank(String.valueOf(month),email)) {
            return null;
        }
        UserRegisterVO userRegisterVO = userService.userRegister(month, email,isDay);
        return ResultUtils.success(userRegisterVO);
    }



    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public BaseResponse<UserLoginVO> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserLoginVO userLoginVO = userService.userLogin(userAccount, password);
        return ResultUtils.success(userLoginVO);
    }

    /**
     * 用户注销
     *
     * @param userLogoutRequest
     * @return
     */
    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(@RequestBody UserLogoutRequest userLogoutRequest) {
        if (userLogoutRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(userLogoutRequest);
        return ResultUtils.success(result);
    }

    /**
     * 用户过期判断
     * @param userJudgeRequest
     * @return
     */
    @ApiOperation("用户已过期")
    @PostMapping("/judgement")
    public  BaseResponse<Boolean> userJudgement(@RequestBody UserJudgeRequest userJudgeRequest){
        if(userJudgeRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result=userService.userJudgement(userJudgeRequest);
        return ResultUtils.success(result);
    }

    /**
     * 删除所有过期用户
     * @return
     */
    @ApiOperation("删除所有过期用户")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser() {
        boolean isSuccess=userService.removeALlDatedUser();
        return ResultUtils.success(isSuccess);
    }

    /**
     * 删除指定用户
     * @return
     */
    @ApiOperation("删除指定用户")
    @PostMapping("/delete/one")
    public BaseResponse<Boolean> deleteUserOne(UserDeleteRequest userDeleteRequest) {
        String userAccount = userDeleteRequest.getUserAccount();
        if(StringUtils.isBlank(userAccount)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,userAccount);
        return ResultUtils.success(userService.remove(queryWrapper));
    }

    /**
     * 更新用户
     * @param userUpdateRequest
     * @return
     */
    @ApiOperation("修改密码与会员续期")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getUserAccount() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result =userService.updatePasswordOrMonth(userUpdateRequest);
        return ResultUtils.success(result);
    }



    /**
     * 获取用户列表
     *
     * @param userQueryRequest
     * @return
     */
    @ApiOperation("获取所有用户信息")
    @GetMapping("/list")
    public BaseResponse<List<UserVO>> listUser(UserQueryRequest userQueryRequest) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(queryWrapper);
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setUserAccount(user.getUserAccount());
            if (user.getStatus().equals(UserEnum.ONLINE.getValue())) {
                userVO.setStatus(UserEnum.ONLINE.getText());
            }else{
                userVO.setStatus(UserEnum.OFFLINE.getText());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分");
            String pastTimeStr = sdf.format(user.getPastTime());
            userVO.setPastTime(pastTimeStr);
            String createTimeStr = sdf.format(user.getCreateTime());
            userVO.setCreateTime(createTimeStr);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVOList);
    }

    /**
     * 分页获取用户列表
     * @param userQueryRequest
     * @param request
     * @return
     */
    @ApiOperation("分页获取用户列表")
    @GetMapping("/list/page")
    public BaseResponse<Page<UserVO>> listUserByPage(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
            current = userQueryRequest.getCurrent();
            size = userQueryRequest.getPageSize();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        Page<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setUserAccount(user.getUserAccount());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分");
            String pastTimeStr = sdf.format(user.getPastTime());
            userVO.setPastTime(pastTimeStr);
            String createTimeStr = sdf.format(user.getCreateTime());
            userVO.setCreateTime(createTimeStr);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

    // endregion
}
