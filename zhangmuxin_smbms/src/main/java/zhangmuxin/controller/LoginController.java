package zhangmuxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zhangmuxin.pojo.User;
import zhangmuxin.service.UserService;


import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang muxin
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User userinfo, Model model, HttpServletRequest request) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userCode", userinfo.getUserCode()).eq("userPassword", userinfo.getUserPassword());
        User user = userService.getOne(wrapper);
        if (user == null) {
            model.addAttribute("error", "用户名或密码错误");
            return "login.jsp";
        } else {
            request.getSession().setAttribute("USER_SESSION",user);
            model.addAttribute("userSession", user);
            return "redirect:jsp/frame.jsp";
        }
    }
    @RequestMapping("/logout")
    public String logout( HttpServletRequest request) {
        request.getSession().removeAttribute("USER_SESSION");
        return "login.jsp";
    }

}
