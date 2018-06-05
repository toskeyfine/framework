package com.toskey.framework.modules.admin.controller;

import com.google.code.kaptcha.Producer;
import com.toskey.framework.common.constant.Global;
import com.toskey.framework.common.shiro.ShiroUtils;
import com.toskey.framework.core.base.controller.BaseController;
import com.toskey.framework.core.constant.R;
import com.toskey.framework.core.util.DESUtils;
import com.toskey.framework.core.util.HttpUtils;
import com.toskey.framework.modules.admin.service.IUserService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private Producer producer;

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @PostMapping("/captcha")
    public void captcha(HttpServletResponse response, String uuid) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        ShiroUtils.setSessionAttribute(Global.KAPTCHA_SESSION_KEY, text);
        IOUtils.closeQuietly(out);
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping(value="/loginValid")
    //@ResponseBody
    public Map<String, Object> login(String data) throws Exception {
        data = DESUtils.base64Decode1(data);
        String formValue[] = data.split(",");
        String loginName = formValue[0];
        String password = formValue[1];
        /*String uuid = formValue[2];
        String captchaCode = formValue[3];

        boolean captcha = captchaService.validate(uuid, captchaCode);
        if(!captcha){
            return R.error("验证码不正确");
        }
        */
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password.toCharArray());

        subject.login(token);

        if(ShiroUtils.getUser() == null) {
            return R.error("用户名或密码错误");
        }

        HttpUtils.getSession().setAttribute(Global.USER_SESSION_KEY, ShiroUtils.getUser());
        ShiroUtils.setSessionAttribute(Global.SHIRO_SESSION_KEY, true);

        return R.ok();
    }

    @GetMapping("/logout")
    public String logout() {
        ShiroUtils.getSubject().logout();
        return REDIRECT + "/login";
    }

}
