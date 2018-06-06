package com.toskey.framework.core.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataScope {

    /**
     * 拦截类型 dept：按部门 user：按下级用户
     * 默认为dept
     * @return
     */
    String value() default "dept";

    /**
     * 是否递归应用下级用户的权限
     * 当拦截类型为user时使用，默认为false
     * @return
     */
    boolean subUserRole() default false;

}
