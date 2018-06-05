package com.toskey.framework.common.aop;

import com.google.gson.Gson;
import com.toskey.framework.common.shiro.ShiroUtils;
import com.toskey.framework.core.annotation.SysLog;
import com.toskey.framework.core.util.HttpUtils;
import com.toskey.framework.core.util.IdGen;
import com.toskey.framework.modules.admin.model.Log;
import com.toskey.framework.modules.admin.service.ILogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAop {

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.toskey.framework.core.annotation.SysLog)")
    public void logPoint() {

    }

    @Around("logPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if(null != sysLog) {
            log.setOperation(sysLog.value());
        }
        // 方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");
        // 参数
        Object[] args = point.getArgs();
        String params = new Gson().toJson(args[0]);
        log.setParams(params);

        //获取request
        HttpServletRequest request = HttpUtils.getRequest();
        //设置IP地址
        log.setIp(HttpUtils.getIpAddr(request));

        //用户名
        String username = ShiroUtils.getUser().getLoginName();
        log.setUserName(username);

        log.setTime(time);

        new SaveLogThread(log).run();
    }

    private class SaveLogThread extends Thread {

        private Log log;

        public SaveLogThread(Log log) {
            this.log = log;
        }

        @Override
        public void run() {
            log.setId(IdGen.uuid());
            log.setCreateDate(new Date());
            logService.insert(log);
        }
    }

}
