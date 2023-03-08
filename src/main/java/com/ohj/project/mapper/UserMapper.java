package com.ohj.project.mapper;

import com.ohj.project.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hipopaaaa
* @description 针对表【User(用户表)】的数据库操作Mapper
* @createDate 2023-03-01 11:33:01
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




