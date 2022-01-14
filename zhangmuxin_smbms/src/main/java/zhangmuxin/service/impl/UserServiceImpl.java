package zhangmuxin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhangmuxin.pojo.User;
import zhangmuxin.mapper.UserMapper;
import zhangmuxin.service.UserService;

/**
 * @author zhang muxin
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}