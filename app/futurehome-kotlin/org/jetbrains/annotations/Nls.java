package org.jetbrains.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE, ElementType.TYPE, ElementType.PACKAGE})
public @interface Nls {
   Nls.Capitalization capitalization() default Nls.Capitalization.NotSpecified;

   public static enum Capitalization {
      NotSpecified,
      Sentence,
      Title;
      private static final Nls.Capitalization[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Nls.Capitalization var2 = new Nls.Capitalization();
         NotSpecified = var2;
         Nls.Capitalization var0 = new Nls.Capitalization();
         Title = var0;
         Nls.Capitalization var1 = new Nls.Capitalization();
         Sentence = var1;
         $VALUES = new Nls.Capitalization[]{var2, var0, var1};
      }
   }
}
