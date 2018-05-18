package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.UserToken;

public interface UserTokenDao extends BaseMapper<UserToken> {

    UserToken selectByToken(String token);

}
