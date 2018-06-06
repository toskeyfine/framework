package com.toskey.framework.common.interceptor;

import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.toskey.framework.common.shiro.ShiroUtils;
import com.toskey.framework.core.annotation.DataScope;
import com.toskey.framework.modules.admin.util.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 数据权限拦截器
 * 根据部门进行数据范围划分
 * 所查数据表必须拥有create_by字段
 * 所有需要进行数据权限拦截的DAO层方法，必须添加@DataScope注解
 *
 * @author toskey
 */
@Intercepts({@Signature(type=StatementHandler.class, method="prepare", args={Connection.class, String.class})})
public class DataScopeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if(!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        // 获取拦截的方法，判断是否有@DataScope注解
        String namespace = mappedStatement.getId();
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        String methodName = namespace.substring(namespace.lastIndexOf(".") + 1);
        Method[] methods = Class.forName(className).getMethods();
        DataScope dataScope = null;
        for(Method method : methods) {
            if(method.getName().equals(methodName)) {
                if(!method.isAnnotationPresent(DataScope.class)) {
                    return invocation.proceed();
                } else {
                    dataScope = method.getAnnotation(DataScope.class);
                }
            }
        }
        // 获取拦截类型
        String filterType = dataScope.value();
        if(StringUtils.isNotBlank(filterType)) {
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            String curUserId = ShiroUtils.getUserId();
            List<String> ids = new ArrayList<String>();
            if("dept".equals(filterType)) { // 按部门过滤
                ids.addAll(UserUtils.getDeptUserIdsByUser(curUserId));
            } else {    // 按上下级用户过滤
                boolean subUserRole = dataScope.subUserRole();
                wrapUserCondition(curUserId, subUserRole, ids);
            }
            StringBuffer condition = new StringBuffer();
            for(String id : ids) {
                condition.append("'").append(id).append("'").append(",");
            }
            condition.deleteCharAt(condition.length() - 1);
            originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope.create_by in (" + condition.toString() + ")";
            metaObject.setValue("delegate.boundSql.sql", originalSql);
        } else {
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    private void wrapUserCondition(String userId, boolean subUserRole, List<String> result) {
        List<String> userIdList = UserUtils.getUserIdByTopUser(userId);
        result.addAll(userIdList);
        if(subUserRole) {
            for(String id : userIdList) {
                wrapUserCondition(id, subUserRole, result);
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
