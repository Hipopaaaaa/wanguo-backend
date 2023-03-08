package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohj.project.model.entity.User;
import generator.service.UserService;
import com.ohj.project.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author hipopaaaa
* @description 针对表【User(用户表)】的数据库操作Service实现
* @createDate 2023-03-01 11:33:01
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




