package org.checkerframework.common.initializedfields.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.InheritedAnnotation;
import org.checkerframework.framework.qual.PostconditionAnnotation;
import org.checkerframework.framework.qual.QualifierArgument;

@Documented
@Repeatable(EnsuresInitializedFields.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@InheritedAnnotation
@PostconditionAnnotation(
   qualifier = InitializedFields.class
)
public @interface EnsuresInitializedFields {
   @QualifierArgument("value")
   String[] fields();

   String[] value() default {"this"};

   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   @InheritedAnnotation
   @PostconditionAnnotation(
      qualifier = InitializedFields.class
   )
   public @interface List {
      EnsuresInitializedFields[] value();
   }
}
