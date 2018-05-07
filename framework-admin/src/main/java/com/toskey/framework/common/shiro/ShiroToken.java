package com.toskey.framework.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Shiro用户令牌
 *
 * @author toskey
 */
public class ShiroToken extends UsernamePasswordToken {

    private String kaptcha;

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    public ShiroToken() { super(); }

    public ShiroToken(String userName, char[] password, boolean remeberMe, String host, String kaptcha) {
        super(userName, password, remeberMe, host);
        this.kaptcha = kaptcha;
    }

}
