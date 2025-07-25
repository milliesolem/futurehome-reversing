package org.checkerframework.checker.calledmethods.qual;

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
@Repeatable(EnsuresCalledMethodsIf.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@ConditionalPostconditionAnnotation(
   qualifier = CalledMethods.class
)
@InheritedAnnotation
public @interface EnsuresCalledMethodsIf {
   String[] expression();

   @QualifierArgument("value")
   String[] methods();

   boolean result();

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @ConditionalPostconditionAnnotation(
      qualifier = CalledMethods.class
   )
   @InheritedAnnotation
   public @interface List {
      EnsuresCalledMethodsIf[] value();
   }
}
