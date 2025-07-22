package org.hamcrest.object;

import java.util.EventObject;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsEventFrom extends TypeSafeDiagnosingMatcher<EventObject> {
   private final Class<?> eventClass;
   private final Object source;

   public IsEventFrom(Class<?> var1, Object var2) {
      this.eventClass = var1;
      this.source = var2;
   }

   @Factory
   public static Matcher<EventObject> eventFrom(Class<? extends EventObject> var0, Object var1) {
      return new IsEventFrom(var0, var1);
   }

   @Factory
   public static Matcher<EventObject> eventFrom(Object var0) {
      return eventFrom(EventObject.class, var0);
   }

   private boolean eventHasSameSource(EventObject var1) {
      boolean var2;
      if (var1.getSource() == this.source) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an event of type ").appendText(this.eventClass.getName()).appendText(" from ").appendValue(this.source);
   }

   public boolean matchesSafely(EventObject var1, Description var2) {
      if (!this.eventClass.isInstance(var1)) {
         StringBuilder var3 = new StringBuilder("item type was ");
         var3.append(var1.getClass().getName());
         var2.appendText(var3.toString());
         return false;
      } else if (!this.eventHasSameSource(var1)) {
         var2.appendText("source was ").appendValue(var1.getSource());
         return false;
      } else {
         return true;
      }
   }
}
