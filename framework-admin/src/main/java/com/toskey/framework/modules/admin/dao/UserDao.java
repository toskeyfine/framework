package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends BaseMapper<User> {

    User getByUserName(@Param("userName") String userName);

    List<String> getChildUserByTopUser(@Param("userId") String userId);

}
