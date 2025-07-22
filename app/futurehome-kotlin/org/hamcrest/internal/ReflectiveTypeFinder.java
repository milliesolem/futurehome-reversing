package org.hamcrest.internal;

import java.lang.reflect.Method;

public class ReflectiveTypeFinder {
   private final int expectedNumberOfParameters;
   private final String methodName;
   private final int typedParameter;

   public ReflectiveTypeFinder(String var1, int var2, int var3) {
      this.methodName = var1;
      this.expectedNumberOfParameters = var2;
      this.typedParameter = var3;
   }

   protected boolean canObtainExpectedTypeFrom(Method var1) {
      boolean var2;
      if (var1.getName().equals(this.methodName) && var1.getParameterTypes().length == this.expectedNumberOfParameters && !var1.isSynthetic()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected Class<?> expectedTypeFrom(Method var1) {
      return var1.getParameterTypes()[this.typedParameter];
   }

   public Class<?> findExpectedType(Class<?> var1) {
      while (var1 != Object.class) {
         for (Method var5 : var1.getDeclaredMethods()) {
            if (this.canObtainExpectedTypeFrom(var5)) {
               return this.expectedTypeFrom(var5);
            }
         }

         var1 = var1.getSuperclass();
      }

      StringBuilder var6 = new StringBuilder("Cannot determine correct type for ");
      var6.append(this.methodName);
      var6.append("() method.");
      throw new Error(var6.toString());
   }
}
