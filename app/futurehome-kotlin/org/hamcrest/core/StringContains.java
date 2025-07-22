package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringContains extends SubstringMatcher {
   public StringContains(String var1) {
      super(var1);
   }

   @Factory
   public static Matcher<String> containsString(String var0) {
      return new StringContains(var0);
   }

   @Override
   protected boolean evalSubstringOf(String var1) {
      boolean var2;
      if (var1.indexOf(this.substring) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   protected String relationship() {
      return "containing";
   }
}
