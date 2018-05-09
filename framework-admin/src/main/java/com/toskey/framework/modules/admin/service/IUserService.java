package com.toskey.framework.modules.admin.service;

import com.toskey.framework.core.base.service.IService;
import com.toskey.framework.modules.admin.model.User;

/**
 * 系统用户业务服务类接口
 *
 * @author toskey
 */
public interface IUserService extends IService<User> {

    User queryByUserName(String userName);

}
