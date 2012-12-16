package com.ehaqui.ehcore.api.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Requirements
{
    boolean vip() default false;
    
    boolean admin() default false;
}
