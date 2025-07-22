package org.hamcrest.integration;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jmock.core.Constraint;

public class JMock1Adapter implements Constraint {
   private final Matcher<?> hamcrestMatcher;

   public JMock1Adapter(Matcher<?> var1) {
      this.hamcrestMatcher = var1;
   }

   public static Constraint adapt(Matcher<?> var0) {
      return new JMock1Adapter(var0);
   }

   public StringBuffer describeTo(StringBuffer var1) {
      this.hamcrestMatcher.describeTo(new StringDescription(var1));
      return var1;
   }

   public boolean eval(Object var1) {
      return this.hamcrestMatcher.matches(var1);
   }
}
