package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier(
   applicableTo = Number.class
)
public @interface Nonnegative {
   When when() default When.ALWAYS;

   public static class Checker implements TypeQualifierValidator<Nonnegative> {
      public When forConstantValue(Nonnegative var1, Object var2) {
         if (!(var2 instanceof Number)) {
            return When.NEVER;
         } else {
            Number var3 = (Number)var2;
            return (
                  var3 instanceof Long
                     ? var3.longValue() >= 0L
                     : (var3 instanceof Double ? !(var3.doubleValue() < 0.0) : (var3 instanceof Float ? !(var3.floatValue() < 0.0F) : var3.intValue() >= 0))
               )
               ? When.ALWAYS
               : When.NEVER;
         }
      }
   }
}
