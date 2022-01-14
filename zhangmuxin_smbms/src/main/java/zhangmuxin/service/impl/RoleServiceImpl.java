package zhangmuxin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhangmuxin.pojo.Role;
import zhangmuxin.mapper.RoleMapper;
import zhangmuxin.service.RoleService;

/**
 * @author zhang muxin
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}