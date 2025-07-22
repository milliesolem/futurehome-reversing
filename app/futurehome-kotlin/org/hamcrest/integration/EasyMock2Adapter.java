package org.hamcrest.integration;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class EasyMock2Adapter implements IArgumentMatcher {
   private final Matcher<?> hamcrestMatcher;

   public EasyMock2Adapter(Matcher<?> var1) {
      this.hamcrestMatcher = var1;
   }

   public static IArgumentMatcher adapt(Matcher<?> var0) {
      EasyMock2Adapter var1 = new EasyMock2Adapter(var0);
      EasyMock.reportMatcher(var1);
      return var1;
   }

   public void appendTo(StringBuffer var1) {
      this.hamcrestMatcher.describeTo(new StringDescription(var1));
   }

   public boolean matches(Object var1) {
      return this.hamcrestMatcher.matches(var1);
   }
}
