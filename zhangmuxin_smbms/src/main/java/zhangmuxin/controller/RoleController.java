package zhangmuxin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhangmuxin.pojo.Role;
import zhangmuxin.service.RoleService;

import java.util.List;
/**
 * @author zhang muxin
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("selectRolelist")
    @ResponseBody
    public List<Role> selectRolelist(){
        List<Role> roleList = roleService.list();
        return roleList;
    }
}
