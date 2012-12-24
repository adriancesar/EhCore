package com.ehaqui.ehcore.api.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Command
    {
        String[] aliases();
        
        String[] modifiers() default "";
                
        String desc() default "";
        
        String flags() default "";
        
        String help() default "";
        
        int max() default -1;
        
        int min() default 0;
        
        String permission() default "";
        
        String usage() default "";
    }