package org.hamcrest.core;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class StringStartsWith extends SubstringMatcher {
   public StringStartsWith(String var1) {
      super(var1);
   }

   @Factory
   public static Matcher<String> startsWith(String var0) {
      return new StringStartsWith(var0);
   }

   @Override
   protected boolean evalSubstringOf(String var1) {
      return var1.startsWith(this.substring);
   }

   @Override
   protected String relationship() {
      return "starting with";
   }
}
