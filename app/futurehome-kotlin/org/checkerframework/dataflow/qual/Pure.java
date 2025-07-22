package org.checkerframework.dataflow.qual;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Pure {
   public static enum Kind {
      DETERMINISTIC,
      SIDE_EFFECT_FREE;
      private static final Pure.Kind[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Pure.Kind var0 = new Pure.Kind();
         SIDE_EFFECT_FREE = var0;
         Pure.Kind var1 = new Pure.Kind();
         DETERMINISTIC = var1;
         $VALUES = new Pure.Kind[]{var0, var1};
      }
   }
}
