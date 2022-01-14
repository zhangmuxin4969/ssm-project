package zhangmuxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zhangmuxin.pojo.Result;
import zhangmuxin.pojo.Role;
import zhangmuxin.pojo.User;
import zhangmuxin.service.RoleService;
import zhangmuxin.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhang muxin
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/getUserLisByPage")
    public String getUserLisByPage(Model model, User user,
                                   @RequestParam(value = "inputPage", defaultValue = "1", required = false) Integer currentpage) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (user.getUserCode() != null && user.getUserCode() != "") {
            wrapper.like("userCode", user.getUserCode());
        }
        if (user.getUserName() != null && user.getUserName() != "") {
            wrapper.like("userName", user.getUserName());
        }
        if (user.getGender() != null && user.getGender() != 0) {
            wrapper.eq("gender", user.getGender());
        }
        if (user.getUserRole() != null && user.getUserRole() != 0) {
            wrapper.eq("userRole", user.getUserRole());
        }

        //分页查询数据
        Page<User> userPage = new Page<>(currentpage, 5);
        Page<User> page = userService.page(userPage, wrapper);
        model.addAttribute("userList", page.getRecords());
        //分页pageinfo
        model.addAttribute("page", page);
        model.addAttribute("pages", page.getPages());
        //标记分页条跳转
        model.addAttribute("mark", "user");
        //查询条件
        model.addAttribute("user", user);
        List<Role> roleList = roleService.list();
        model.addAttribute("roleList", roleList);
        return "jsp/userlist.jsp";
    }

    @RequestMapping("/queryUserId")
    public String queryUserId(Model model, Integer userid) {
        User user = userService.getById(userid);
        Role role = roleService.getById(user.getUserRole());
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return "jsp/userview.jsp";
    }

    @Transactional
    @RequestMapping("/modifyUser")
    public String modifyUser(Model model, Integer userid) {
        User user = userService.getById(userid);
        model.addAttribute("user", user);
        return "jsp/usermodify.jsp";
    }

    @Transactional
    @RequestMapping("/updateUser")
    public String updateUser(User user, HttpServletRequest request, String birthdayStr) throws ParseException {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("modifyDate", new Date());
        updateWrapper.set("modifyBy", userSession.getId());
        updateWrapper.set("birthday", new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr));
        System.out.println("--------------------");
        System.out.println(user.getId());
        System.out.println("--------------------");
        updateWrapper.eq("id", user.getId());
        userService.update(user, updateWrapper);
        return "redirect:getUserLisByPage";
    }

    @Transactional
    @RequestMapping("/deleteUserById")
    @ResponseBody
    public Result deleteUserById(Integer userid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Result result = new Result();
        wrapper.eq("id", userid);
        User user = userService.getById(userid);
        if (user != null) {
            boolean remove = userService.remove(wrapper);
            if (remove) {
                result.setResult("true");
                return result;
            } else {
                result.setResult("false");
                return result;
            }
        } else {
            result.setResult("notexist");
            return result;
        }

    }

    @Transactional
    @RequestMapping("/addUser")
    public String addUser(User user, HttpServletRequest request, String birthdayStr) throws ParseException {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");

        Date date = new Date();
        user.setCreatedBy(userSession.getId());
        user.setCreationDate(date);
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr));

        userService.save(user);
        return "redirect:getUserLisByPage";
    }

    @RequestMapping("/queryUseruserCode")
    @ResponseBody
    public User queryUserId(String userCode) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userCode", userCode);
        User user = userService.getOne(wrapper);
        return user;
    }

    @RequestMapping("/queryNameAndPwd")
    @ResponseBody
    public Result queryNameAndPwd(String oldpassword, HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");
        Result result = new Result();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", userSession.getId());
        wrapper.eq("userPassword", oldpassword);
        User user = userService.getOne(wrapper);
        if (oldpassword.equals("")) {
            result.setResult("error");
            return result;
        } else {
            if (user != null) {
                result.setResult("true");
                return result;
            } else {
                result.setResult("false");
                return result;
            }
        }
    }

    @Transactional
    @RequestMapping("/pwdmodifyx")
    public String pwdmodifyx(String newpassword, HttpServletRequest request, Model model) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");
        wrapper.set("modifyBy", userSession.getId());
        wrapper.set("modifyDate", new Date());
        wrapper.eq("id", userSession.getId());
        wrapper.set("userPassword", newpassword);
        boolean update = userService.update(wrapper);
        if (update) {
            model.addAttribute("message", "修改密码成功");
        } else {
            model.addAttribute("message", "修改密码失败");
        }
        return "jsp/pwdmodify.jsp";
    }
}


