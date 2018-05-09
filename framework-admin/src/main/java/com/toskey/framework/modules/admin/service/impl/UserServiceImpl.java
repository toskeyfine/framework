package com.toskey.framework.modules.admin.service.impl;

import com.toskey.framework.core.base.service.BaseService;
import com.toskey.framework.modules.admin.dao.UserDao;
import com.toskey.framework.modules.admin.model.User;
import com.toskey.framework.modules.admin.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService<UserDao, User> implements IUserService {

    @Override
    public User queryByUserName(String userName) {
        return baseMapper.getByUserName(userName);
    }

}
