package com.toskey.framework.modules.admin.service;

import com.toskey.framework.core.base.service.IService;
import com.toskey.framework.core.constant.R;
import com.toskey.framework.modules.admin.model.UserToken;

public interface IUserTokenService extends IService<UserToken> {

    R createToken(String userId);

    void logout(String userId);
}
