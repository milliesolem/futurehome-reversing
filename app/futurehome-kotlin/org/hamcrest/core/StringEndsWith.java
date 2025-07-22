package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringEndsWith extends SubstringMatcher {
   public StringEndsWith(String var1) {
      super(var1);
   }

   @Factory
   public static Matcher<String> endsWith(String var0) {
      return new StringEndsWith(var0);
   }

   @Override
   protected boolean evalSubstringOf(String var1) {
      return var1.endsWith(this.substring);
   }

   @Override
   protected String relationship() {
      return "ending with";
   }
}
