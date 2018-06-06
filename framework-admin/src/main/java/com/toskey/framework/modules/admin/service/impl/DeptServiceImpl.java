package com.toskey.framework.modules.admin.service.impl;

import com.toskey.framework.core.base.service.BaseService;
import com.toskey.framework.modules.admin.dao.DeptDao;
import com.toskey.framework.modules.admin.model.Dept;
import com.toskey.framework.modules.admin.service.IDeptService;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends BaseService<DeptDao, Dept> implements IDeptService {
}
