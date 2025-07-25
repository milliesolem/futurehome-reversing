package org.checkerframework.checker.index.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.ConditionalPostconditionAnnotation;
import org.checkerframework.framework.qual.InheritedAnnotation;
import org.checkerframework.framework.qual.JavaExpression;
import org.checkerframework.framework.qual.QualifierArgument;

@Documented
@Repeatable(EnsuresLTLengthOfIf.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@ConditionalPostconditionAnnotation(
   qualifier = LTLengthOf.class
)
@InheritedAnnotation
public @interface EnsuresLTLengthOfIf {
   String[] expression();

   @JavaExpression
   @QualifierArgument("offset")
   String[] offset() default {};

   boolean result();

   @JavaExpression
   @QualifierArgument("value")
   String[] targetValue();

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @ConditionalPostconditionAnnotation(
      qualifier = LTLengthOf.class
   )
   @InheritedAnnotation
   public @interface List {
      EnsuresLTLengthOfIf[] value();
   }
}
