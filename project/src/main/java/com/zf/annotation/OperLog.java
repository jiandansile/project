package com.zf.annotation;

import java.lang.annotation.*;

/**
 * 操作日志
 * @author yuzhian
 * @date 2021/10/25
 **/
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {
    /** 操作模块 */
    String operModul() default "";
    /** 操作平台类型 */
    String operType() default "";
    /** 操作说明 */
    String operDesc() default "";
}
