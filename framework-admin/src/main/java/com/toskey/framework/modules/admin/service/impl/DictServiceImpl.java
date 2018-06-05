package com.toskey.framework.modules.admin.service.impl;

import com.toskey.framework.core.base.service.BaseService;
import com.toskey.framework.modules.admin.dao.DictDao;
import com.toskey.framework.modules.admin.model.Dict;
import com.toskey.framework.modules.admin.service.IDictService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends BaseService<DictDao, Dict> implements IDictService {
    @Override
    public String getLabel(String type, String value) {
        Dict dict = baseMapper.getByTypeValue(type, value);
        if(null == dict) {
            return null;
        }
        return dict.getLabel();
    }
}
