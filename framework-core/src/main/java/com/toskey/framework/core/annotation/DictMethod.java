package com.toskey.framework.core.annotation;

import java.lang.annotation.*;

/**
 * 数据字典方法级注解
 * @author toskey
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DictMethod {
}
