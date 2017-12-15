package com.zsc.game.di.module;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 类的用途：自定义注解   和single作用一致
 * <p>
 * mac周昇辰
 * 2017/12/12  16:31
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface YourScope {
}
