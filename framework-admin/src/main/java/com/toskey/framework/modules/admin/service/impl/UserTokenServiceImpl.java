package com.toskey.framework.modules.admin.service.impl;

import com.toskey.framework.common.shiro.TokenGenerator;
import com.toskey.framework.core.base.service.BaseService;
import com.toskey.framework.core.constant.R;
import com.toskey.framework.modules.admin.dao.UserTokenDao;
import com.toskey.framework.modules.admin.model.UserToken;
import com.toskey.framework.modules.admin.service.IUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTokenServiceImpl extends BaseService<UserTokenDao, UserToken> implements IUserTokenService {

    private final static int EXPIRE_TIME = 12 * 60 * 60;

    @Override
    public R createToken(String userId) {
        String token = TokenGenerator.generateValue();
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE_TIME * 1000);

        //判断是否生成过token
        UserToken userToken = this.selectById(userId);
        if(userToken == null){
            userToken = new UserToken();
            userToken.setUserId(userId);
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);

            //保存token
            this.insert(userToken);
        }else{
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);

            //更新token
            this.updateById(userToken);
        }

        R r = R.ok().put("token", token).put("expire", EXPIRE_TIME);

        return r;
    }

    @Override
    public void logout(String userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);
        this.updateById(userToken);

    }
}
