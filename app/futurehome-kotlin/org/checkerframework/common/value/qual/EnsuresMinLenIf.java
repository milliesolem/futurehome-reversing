package org.checkerframework.common.value.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.ConditionalPostconditionAnnotation;
import org.checkerframework.framework.qual.InheritedAnnotation;
import org.checkerframework.framework.qual.QualifierArgument;

@Documented
@Repeatable(EnsuresMinLenIf.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@ConditionalPostconditionAnnotation(
   qualifier = MinLen.class
)
@InheritedAnnotation
public @interface EnsuresMinLenIf {
   String[] expression();

   boolean result();

   @QualifierArgument("value")
   int targetValue() default 0;

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @ConditionalPostconditionAnnotation(
      qualifier = MinLen.class
   )
   @InheritedAnnotation
   public @interface List {
      EnsuresMinLenIf[] value();
   }
}
