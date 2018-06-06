package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptDao extends BaseMapper<Dept> {

    List<String> getDeptUser(@Param("deptId") String deptId);

}
