package com.dusty.boring.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *
 *  <元数据注解>
 *
 *   @author: xiehongfei[humphreytes@gmail.com]
 *   @date: 2018年05月19日 21:09
 *   @version: V1.0
 *   @review: xiehongfei[humphreytes@gmail.com]/2018年05月19日 21:09
 *
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
public @interface MetaData {

    String value() default "";

    String note() default "";

    /**
     * 自增类型主键，初始化自增值
     */
    long autoIncrementInitValue() default 0;

}
