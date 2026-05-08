package com.wtt.demo.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 用于标记需要记录用户操作的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    /**
     * 模块名称
     */
    String module() default "";
    
    /**
     * 操作类型
     */
    String actionType() default "QUERY";
    
    /**
     * 操作描述
     */
    String description() default "";
}
