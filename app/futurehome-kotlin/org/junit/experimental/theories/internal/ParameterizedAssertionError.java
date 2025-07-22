package org.junit.experimental.theories.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ParameterizedAssertionError extends AssertionError {
   private static final long serialVersionUID = 1L;

   public ParameterizedAssertionError(Throwable var1, String var2, Object... var3) {
      super(String.format("%s(%s)", var2, join(", ", var3)));
      this.initCause(var1);
   }

   public static String join(String var0, Collection<Object> var1) {
      StringBuilder var2 = new StringBuilder();
      Iterator var3 = var1.iterator();

      while (var3.hasNext()) {
         var2.append(stringValueOf(var3.next()));
         if (var3.hasNext()) {
            var2.append(var0);
         }
      }

      return var2.toString();
   }

   public static String join(String var0, Object... var1) {
      return join(var0, Arrays.asList(var1));
   }

   private static String stringValueOf(Object var0) {
      try {
         return String.valueOf(var0);
      } finally {
         ;
      }
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof ParameterizedAssertionError && this.toString().equals(var1.toString())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public int hashCode() {
      return this.toString().hashCode();
   }
}
