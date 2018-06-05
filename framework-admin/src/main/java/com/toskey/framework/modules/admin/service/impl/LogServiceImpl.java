package com.toskey.framework.modules.admin.service.impl;

import com.toskey.framework.core.base.service.BaseService;
import com.toskey.framework.modules.admin.dao.LogDao;
import com.toskey.framework.modules.admin.model.Log;
import com.toskey.framework.modules.admin.service.ILogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends BaseService<LogDao, Log> implements ILogService {
}
