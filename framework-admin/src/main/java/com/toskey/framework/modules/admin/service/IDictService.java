package com.toskey.framework.modules.admin.service;

import com.toskey.framework.core.base.service.IService;
import com.toskey.framework.modules.admin.model.Dict;

public interface IDictService extends IService<Dict> {

    String getLabel(String type, String value);

}
