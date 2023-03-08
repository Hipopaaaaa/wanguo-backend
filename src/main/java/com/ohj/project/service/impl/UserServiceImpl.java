package com.ohj.project.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohj.project.common.ErrorCode;
import com.ohj.project.exception.BusinessException;
import com.ohj.project.mapper.UserMapper;
import com.ohj.project.model.dto.user.UserJudgeRequest;
import com.ohj.project.model.dto.user.UserLogoutRequest;
import com.ohj.project.model.dto.user.UserUpdateRequest;
import com.ohj.project.model.entity.User;
import com.ohj.project.model.enums.UserEnum;
import com.ohj.project.model.vo.UserLoginVO;
import com.ohj.project.model.vo.UserRegisterVO;
import com.ohj.project.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * 用户服务实现类
 *
 * @author ohj
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "ohj";

    /**
     * 用户注册
     *
     * @param month 开通的会员月份
     * @param email 邮箱
     * @return
     */
    @Override
    public UserRegisterVO userRegister(Long month, String email, boolean isDay) {
        // 1. 校验
        if (StringUtils.isAnyBlank(String.valueOf(month), email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        synchronized (email.intern()) {
            //随机生成账号:

            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", email);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            //随机生成密码：
            String password = RandomUtil.randomString(8);
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 生成会员月份
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime vipTime = isDay ? now.plusDays(month) : now.plusMonths(month);
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = vipTime.atZone(zoneId);
            Date newDate = Date.from(zonedDateTime.toInstant());

            // 4. 插入数据
            User user = new User();
            user.setPastTime(newDate);
            user.setUserAccount(email);
            user.setPassword(encryptPassword);
            user.setEmail(email);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            // 5.返回显示内容
            return new UserRegisterVO().setUserAccount(email).setPassword(password);
        }
    }


    /**
     * 用户登陆
     *
     * @param userAccount 用户账户
     * @param password    用户密码
     * @return
     */
    @Override
    public UserLoginVO userLogin(String userAccount, String password) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("status","pastTime");
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password", encryptPassword);
        synchronized (userAccount.intern()) {
            User user = userMapper.selectOne(queryWrapper);
            // 用户不存在
            if (user == null) {
                log.info("user login failed, userAccount cannot match password");
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
            }
            // 判断登陆状态
            if (user.getStatus().equals(UserEnum.ONLINE.getValue())) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户已登陆");
            }
            //验证是否过期
            Date time = user.getPastTime();
            Instant instant = time.toInstant();
            LocalDateTime pastTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            if (!pastTime.isAfter(LocalDateTime.now())) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前用户已过期");
            }
            // 3.修改用户的登陆状态
            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(User::getUserAccount, userAccount)
                    .set(User::getStatus, UserEnum.ONLINE.getValue());
            int res = userMapper.update(null, updateWrapper);
            if (res < 1) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            return new UserLoginVO().setEmail(userAccount).setStatus(UserEnum.ONLINE.getValue());
        }
    }


    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(UserLogoutRequest request) {
        //把登陆状态修改成0
        String userAccount = request.getUserAccount();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        synchronized (userAccount.intern()){
            User user = this.getOne(queryWrapper);
            if (user.getStatus().equals(UserEnum.OFFLINE.getValue())) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户未登陆");
            }
            user.setStatus(UserEnum.OFFLINE.getValue());
            return this.updateById(user);
        }
    }

    /**
     * 更新用户的密码 或 开通会员日期
     *
     * @param userUpdateRequest
     * @return
     */
    @Override
    public boolean updatePasswordOrMonth(UserUpdateRequest userUpdateRequest) {
        String password = userUpdateRequest.getPassword();
        String userAccount = userUpdateRequest.getUserAccount();
        Long month = userUpdateRequest.getMonth();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //修改密码
        if (password != null) {
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            user.setPassword(encryptPassword);
        }
        //会员续期
        if (month != null && month != 0) {
            Date date = user.getPastTime();
            Instant instant = date.toInstant();
            LocalDateTime pastTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            boolean isDay = userUpdateRequest.isDay();
            LocalDateTime vipTime = isDay ? pastTime.plusDays(month) : pastTime.plusMonths(month);
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = vipTime.atZone(zoneId);
            Date newDate = Date.from(zonedDateTime.toInstant());
            user.setPastTime(newDate);
        }
        return this.update(user, queryWrapper);
    }

    /**
     * 删除所有过期一个星期的用户
     *
     * @return
     */
    @Override
    public boolean removeALlDatedUser() {
        //一个星期前的时间
        LocalDateTime now = LocalDateTime.now().minusWeeks(1);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        Date newDate = Date.from(zonedDateTime.toInstant());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(User::getPastTime, newDate);
        return this.remove(queryWrapper);
    }

    /**
     * 用户过期判定
     * @param userJudgeRequest
     * @return
     */
    @Override
    public boolean userJudgement(UserJudgeRequest userJudgeRequest) {
       String userAccount = userJudgeRequest.getUserAccount();
       if(StringUtils.isBlank(userAccount)){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
       }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("pastTime").
                lambda().eq(User::getUserAccount,userAccount);
        User user = this.getOne(queryWrapper);
        Date date = user.getPastTime();
        Instant instant = date.toInstant();
        LocalDateTime pastTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return pastTime.isBefore(LocalDateTime.now());
    }
}




