package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> selectListByRole(@Param("roleId") String roleId);

}
