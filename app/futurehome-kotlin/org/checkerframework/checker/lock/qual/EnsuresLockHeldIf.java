package org.checkerframework.checker.lock.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.ConditionalPostconditionAnnotation;
import org.checkerframework.framework.qual.InheritedAnnotation;

@Documented
@Repeatable(EnsuresLockHeldIf.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@ConditionalPostconditionAnnotation(
   qualifier = LockHeld.class
)
@InheritedAnnotation
public @interface EnsuresLockHeldIf {
   String[] expression();

   boolean result();

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @ConditionalPostconditionAnnotation(
      qualifier = LockHeld.class
   )
   @InheritedAnnotation
   public @interface List {
      EnsuresLockHeldIf[] value();
   }
}
