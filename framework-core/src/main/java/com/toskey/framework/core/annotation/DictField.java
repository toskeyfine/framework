package com.toskey.framework.core.annotation;


import java.lang.annotation.*;

/**
 * 数据字典属性级注解
 * @author toskey
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DictField {

    /**
     * 字典类型
     * @return
     */
    String value() default "";

}
