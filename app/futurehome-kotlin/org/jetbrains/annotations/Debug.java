package org.jetbrains.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class Debug {
   private Debug() {
      throw new AssertionError("Debug should not be instantiated");
   }

   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.TYPE})
   public @interface Renderer {
      String childrenArray() default "";

      String hasChildren() default "";

      String text() default "";
   }
}
