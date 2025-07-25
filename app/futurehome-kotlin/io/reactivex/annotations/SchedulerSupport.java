package io.reactivex.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
public @interface SchedulerSupport {
   String COMPUTATION = "io.reactivex:computation";
   String CUSTOM = "custom";
   String IO = "io.reactivex:io";
   String NEW_THREAD = "io.reactivex:new-thread";
   String NONE = "none";
   String SINGLE = "io.reactivex:single";
   String TRAMPOLINE = "io.reactivex:trampoline";

   String value();
}
