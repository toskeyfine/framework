package com.toskey.framework.modules.admin.util;

import com.toskey.framework.core.util.SpringContextHolder;
import com.toskey.framework.modules.admin.dao.MenuDao;
import com.toskey.framework.modules.admin.dao.RoleDao;
import com.toskey.framework.modules.admin.dao.UserDao;
import com.toskey.framework.modules.admin.model.Menu;
import com.toskey.framework.modules.admin.model.Role;
import com.toskey.framework.modules.admin.model.User;

import java.util.List;

public class LoginUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

    public static List<Menu> queryMenuByRole(String roleId) {
        return menuDao.selectListByRole(roleId);
    }

    public static List<Role> queryRoleByUser(String userId) {
        return roleDao.selectByUserId(userId);
    }

    public static User queryByLoginName(String loginName) {
        return userDao.getByUserName(loginName);
    }

    public static User queryById(String id) {
        return userDao.selectById(id);
    }


}
