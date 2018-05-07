package com.toskey.framework.common.aop;

import com.baomidou.mybatisplus.plugins.Page;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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




}
