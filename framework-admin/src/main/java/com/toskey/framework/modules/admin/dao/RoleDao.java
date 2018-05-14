package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseMapper<Role> {

    List<Role> selectByUserId(@Param("userId") String userId);
}
