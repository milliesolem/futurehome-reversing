package org.hamcrest;

public class JavaLangMatcherAssert {
   private JavaLangMatcherAssert() {
   }

   public static <T> boolean that(T var0, Matcher<? super T> var1) {
      return var1.matches(var0);
   }
}
