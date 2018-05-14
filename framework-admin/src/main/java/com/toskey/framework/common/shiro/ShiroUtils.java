package com.toskey.framework.common.shiro;

import com.toskey.framework.modules.admin.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static User getCurUser() {
        if(getSubject() == null || getSubject().getPrincipal() == null) {
            return null;
        }
        return (User) getSubject().getPrincipals().getPrimaryPrincipal();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }
}
