package com.anyoptional.lang;

import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.*;

/**
 * 表示被标识的元素不能为null。
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@javax.annotation.Nonnull
@TypeQualifierNickname
public @interface Nonnull {
}
