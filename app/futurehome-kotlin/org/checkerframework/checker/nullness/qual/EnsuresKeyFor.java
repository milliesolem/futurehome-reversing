package org.checkerframework.checker.nullness.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.InheritedAnnotation;
import org.checkerframework.framework.qual.JavaExpression;
import org.checkerframework.framework.qual.PostconditionAnnotation;
import org.checkerframework.framework.qual.QualifierArgument;

@Documented
@Repeatable(EnsuresKeyFor.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@InheritedAnnotation
@PostconditionAnnotation(
   qualifier = KeyFor.class
)
public @interface EnsuresKeyFor {
   @JavaExpression
   @QualifierArgument("value")
   String[] map();

   String[] value();

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @InheritedAnnotation
   @PostconditionAnnotation(
      qualifier = KeyFor.class
   )
   public @interface List {
      EnsuresKeyFor[] value();
   }
}
