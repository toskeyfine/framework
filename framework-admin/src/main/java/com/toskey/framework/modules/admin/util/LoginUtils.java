package com.toskey.framework.modules.admin.util;

import com.toskey.framework.core.util.SpringContextHolder;
import com.toskey.framework.modules.admin.dao.MenuDao;
import com.toskey.framework.modules.admin.dao.RoleDao;
import com.toskey.framework.modules.admin.dao.UserDao;
import com.toskey.framework.modules.admin.dao.UserTokenDao;
import com.toskey.framework.modules.admin.model.Menu;
import com.toskey.framework.modules.admin.model.Role;
import com.toskey.framework.modules.admin.model.User;
import com.toskey.framework.modules.admin.model.UserToken;

import java.util.List;

public class LoginUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static UserTokenDao userTokenDao = SpringContextHolder.getBean(UserTokenDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

    public static List<Menu> queryMenuByRole(String roleId) {
        return menuDao.selectListByRole(roleId);
    }

    public static List<Role> queryRoleByUser(String userId) {
        return roleDao.selectByUserId(userId);
    }

    public static UserToken queryByUserToken(String token) {
        return userTokenDao.selectByToken(token);
    }

    public static User queryById(String id) {
        return userDao.selectById(id);
    }

}
