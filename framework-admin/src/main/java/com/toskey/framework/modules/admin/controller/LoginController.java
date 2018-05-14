package com.toskey.framework.modules.admin.controller;

import com.toskey.framework.common.shiro.ShiroUtils;
import com.toskey.framework.core.base.controller.BaseController;
import com.toskey.framework.core.util.HttpUtils;
import com.toskey.framework.modules.admin.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends BaseController {

    @RequestMapping({"index", ""})
    public String index(Model model) {
        User curUser = ShiroUtils.getCurUser();
        if(curUser != null) {
            return "index";
        }
        return "login";
    }

    @GetMapping("login")
    public String login() {
        if(ShiroUtils.getSubject() != null && ShiroUtils.getSubject().isAuthenticated()) {
            return REDIRECT + "index";
        }
        return "login";
    }

    @PostMapping("login")
    public String login(User user, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken shiroToken = new UsernamePasswordToken(user.getLoginName(), user.getPassword().toCharArray());
        subject.login(shiroToken);
        User curUser = ShiroUtils.getCurUser();
        if(curUser == null) {
            model.addAttribute("errMsg", "用户名或密码错误");
            return "login";
        }
        HttpUtils.getSession().setAttribute("userSession", curUser);
        ShiroUtils.getSession().setAttribute("sessionFlag", true);
        return "index";
    }

    @RequestMapping("logout")
    public String logout() {
        ShiroUtils.getSubject().logout();
        return REDIRECT + "login";
    }

}
