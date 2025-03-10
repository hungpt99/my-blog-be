package com.hungpt.myblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // Only applicable to methods
@Retention(RetentionPolicy.RUNTIME) // Retain at runtime for reflection
public @interface RateLimited {
    int requests() default 10; // Default limit per time window
    int duration() default 1; // Default time window in minutes
}
