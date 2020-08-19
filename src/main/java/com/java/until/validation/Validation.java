package com.java.until.validation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

    //校验的方法
    String[] name() default {};

    //是否为必填字段
    boolean required() default false;

}