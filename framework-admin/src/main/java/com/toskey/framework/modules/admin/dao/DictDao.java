package com.toskey.framework.modules.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.toskey.framework.modules.admin.model.Dict;
import org.apache.ibatis.annotations.Param;

public interface DictDao extends BaseMapper<Dict> {

    Dict getByTypeValue(@Param("type") String type, @Param("value") String value);

}
