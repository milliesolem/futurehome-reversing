package org.junit;

import org.hamcrest.Matcher;

public class AssumptionViolatedException extends org.junit.internal.AssumptionViolatedException {
   private static final long serialVersionUID = 1L;

   public <T> AssumptionViolatedException(T var1, Matcher<T> var2) {
      super(var1, var2);
   }

   public AssumptionViolatedException(String var1) {
      super(var1);
   }

   public <T> AssumptionViolatedException(String var1, T var2, Matcher<T> var3) {
      super(var1, var2, var3);
   }

   public AssumptionViolatedException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
