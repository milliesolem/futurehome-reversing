package org.hamcrest.core;

import java.util.Iterator;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

abstract class ShortcutCombination<T> extends BaseMatcher<T> {
   private final Iterable<Matcher<? super T>> matchers;

   public ShortcutCombination(Iterable<Matcher<? super T>> var1) {
      this.matchers = var1;
   }

   @Override
   public abstract void describeTo(Description var1);

   public void describeTo(Description var1, String var2) {
      StringBuilder var3 = new StringBuilder(" ");
      var3.append(var2);
      var3.append(" ");
      var1.appendList("(", var3.toString(), ")", this.matchers);
   }

   @Override
   public abstract boolean matches(Object var1);

   protected boolean matches(Object var1, boolean var2) {
      Iterator var3 = this.matchers.iterator();

      while (var3.hasNext()) {
         if (((Matcher)var3.next()).matches(var1) == var2) {
            return var2;
         }
      }

      return var2 ^ true;
   }
}
