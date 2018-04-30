package com.gdut.dkmfromcg.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dkmFromCG on 2018/3/14.
 * function:
 */
@Target(ElementType.TYPE) //表示是类注解
@Retention(RetentionPolicy.SOURCE) //在源码时处理
public @interface EntryGenerator {
    String packageName();
    Class<?> entryTemplate();
}
