package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao extends BaseMapper<User> {

    User getByUserName(@Param("userName") String userName);

}
