package com.toskey.framework.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class ShiroUserToken implements AuthenticationToken {

    private String token;

    public ShiroUserToken(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
