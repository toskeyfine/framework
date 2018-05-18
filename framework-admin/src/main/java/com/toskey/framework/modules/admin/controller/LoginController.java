package com.toskey.framework.modules.admin.controller;

import com.toskey.framework.common.shiro.ShiroUtils;
import com.toskey.framework.core.base.controller.BaseController;
import com.toskey.framework.core.constant.R;
import com.toskey.framework.core.util.HttpUtils;
import com.toskey.framework.modules.admin.model.User;
import com.toskey.framework.modules.admin.service.IUserService;
import com.toskey.framework.modules.admin.service.IUserTokenService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserTokenService userTokenService;

    @RequestMapping({"index", ""})
    public String index(Model model) {
        User curUser = ShiroUtils.getCurUser();
        if(curUser != null) {
            return "/index";
        }
        return "/login.html";
    }

    @GetMapping("login")
    public String login() {
        if(ShiroUtils.getSubject() != null && ShiroUtils.getSubject().isAuthenticated()) {
            return REDIRECT + "index";
        }
        return "/login.html";
    }

    @RequestMapping(value="/loginT")
    public Map<String, Object> login(@RequestBody User user) throws IOException {
        User loginUser = userService.queryByUserName(user.getLoginName());

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(user.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //生成token，并保存到数据库
        R r = userTokenService.createToken(user.getId());
        return r;
    }

    @RequestMapping("logout")
    public String logout() {
        ShiroUtils.getSubject().logout();
        return REDIRECT + "login";
    }

}
