package com.toskey.framework.common.aop;

import com.baomidou.mybatisplus.plugins.Page;
import com.toskey.framework.core.annotation.DictField;
import com.toskey.framework.modules.admin.service.IDictService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP的方式实现数据字典的自动转换
 * 必须将该组件设为懒加载，否则RedisTemplate将注入失败
 *
 * @author toskey
 */
@Aspect
@Component
@Lazy
public class DictAop {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private IDictService dictService;

    @AfterReturning(returning = "rvt", pointcut = "@annotation(com.toskey.framework.core.annotation.DictMethod)")
    public void doAfter(JoinPoint joinPoint, Object rvt) {
        handleDict(joinPoint, rvt);
    }

    /**
     * 数据字典处理
     * @param point
     * @param rvt
     */
    private void handleDict(JoinPoint point, Object rvt) {
        Signature signature = point.getSignature();
        Class rvtClassType = ((MethodSignature) signature).getReturnType();
        if(rvtClassType != List.class) {
            if(rvtClassType == Page.class) {
                List list = ((Page) rvt).getRecords();
                for(Object obj : list) {
                    reflectDictLabel(obj);
                }
            } else {
                reflectDictLabel(rvt);
            }
        } else {
            List list = (List) rvt;
            for(Object obj : list) {
                reflectDictLabel(obj);
            }
        }
    }

    /**
     * 反射获取字典Label
     * 根据DictField注解中填写的Type来取
     * DictField中的value为空，则无法转换
     * @param obj
     */
    private void reflectDictLabel(Object obj) {
        if(obj == null) {
            return;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(DictField.class)) {
                DictField dictField = field.getAnnotation(DictField.class);
                String dictType = dictField.value();
                String dictValue = "";
                try {
                    Method getMethod = clazz.getDeclaredMethod("get" + upperCase(field.getName()));
                    dictValue = (String) getMethod.invoke(obj);
                    if(StringUtils.isNotBlank(dictValue)) {
                        String dictLabel = (String) redisTemplate.opsForValue().get("dict_" + dictType + "_" + dictValue);
                        if(StringUtils.isBlank(dictLabel)) {
                            dictLabel = dictService.getLabel(dictType, dictValue);
                            redisTemplate.opsForValue().set("dict_" + dictType + "_" + dictValue, dictLabel);
                        }
                        if(StringUtils.isBlank(dictLabel)) {
                            dictLabel = "--";
                        }
                        Method setMethod = clazz.getDeclaredMethod("set" + upperCase(field.getName()), field.getType());
                        setMethod.invoke(obj, dictLabel);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private String upperCase(String str) {
        char[] ch = str.toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        return new String(ch);
    }

}
