package com.toskey.framework.common.shiro;

import com.toskey.framework.core.exception.BusinessException;
import com.toskey.framework.core.exception.BusinessExceptionEnum;
import com.toskey.framework.modules.admin.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {

    /**  加密算法 */
    public final static String hashAlgorithmName = "SHA-256";
    /**  循环次数 */
    public final static int hashIterations = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static User getUser() {
        return (User)SecurityUtils.getSubject().getPrincipal();
    }

    public static String getUserId() {
        return getUser().getId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if(kaptcha == null){
            throw new BusinessException(BusinessExceptionEnum.KAPTCHA_ERROR);
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
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
