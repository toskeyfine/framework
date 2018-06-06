package com.toskey.framework.modules.admin.util;

import com.google.common.collect.Lists;
import com.toskey.framework.core.util.SpringContextHolder;
import com.toskey.framework.modules.admin.dao.DeptDao;
import com.toskey.framework.modules.admin.dao.UserDao;
import com.toskey.framework.modules.admin.model.User;

import java.util.List;

public class UserUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static DeptDao deptDao = SpringContextHolder.getBean(DeptDao.class);

    public static List<String> getDeptUserIds(String deptId) {
        return deptDao.getDeptUser(deptId);
    }

    public static List<String> getDeptUserIdsByUser(String userId) {
        User user = userDao.selectById(userId);
        List<String> result = Lists.newArrayList();
        if(null != user) {
            String[] deptIds = user.getDeptId().split(",");
            for(String deptId : deptIds) {
                result.addAll(getDeptUserIds(deptId));
            }
        }
        return result;
    }

    public static List<String> getUserIdByTopUser(String userId) {
        return userDao.getChildUserByTopUser(userId);
    }


}
