package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class SamePropertyValuesAs<T> extends TypeSafeDiagnosingMatcher<T> {
   private final T expectedBean;
   private final List<SamePropertyValuesAs.PropertyMatcher> propertyMatchers;
   private final Set<String> propertyNames;

   public SamePropertyValuesAs(T var1) {
      PropertyDescriptor[] var2 = PropertyUtil.propertyDescriptorsFor(var1, Object.class);
      this.expectedBean = (T)var1;
      this.propertyNames = propertyNamesFrom(var2);
      this.propertyMatchers = propertyMatchersFor(var1, var2);
   }

   private boolean hasMatchingValues(T var1, Description var2) {
      for (SamePropertyValuesAs.PropertyMatcher var3 : this.propertyMatchers) {
         if (!var3.matches(var1)) {
            var3.describeMismatch(var1, var2);
            return false;
         }
      }

      return true;
   }

   private boolean hasNoExtraProperties(T var1, Description var2) {
      var1 = propertyNamesFrom(PropertyUtil.propertyDescriptorsFor(var1, Object.class));
      var1.removeAll(this.propertyNames);
      if (!var1.isEmpty()) {
         StringBuilder var3 = new StringBuilder("has extra properties called ");
         var3.append(var1);
         var2.appendText(var3.toString());
         return false;
      } else {
         return true;
      }
   }

   private boolean isCompatibleType(T var1, Description var2) {
      if (!this.expectedBean.getClass().isAssignableFrom(var1.getClass())) {
         StringBuilder var3 = new StringBuilder("is incompatible type: ");
         var3.append(var1.getClass().getSimpleName());
         var2.appendText(var3.toString());
         return false;
      } else {
         return true;
      }
   }

   private static <T> List<SamePropertyValuesAs.PropertyMatcher> propertyMatchersFor(T var0, PropertyDescriptor[] var1) {
      ArrayList var4 = new ArrayList(var1.length);
      int var3 = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(new SamePropertyValuesAs.PropertyMatcher(var1[var2], var0));
      }

      return var4;
   }

   private static Set<String> propertyNamesFrom(PropertyDescriptor[] var0) {
      HashSet var3 = new HashSet();
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1].getDisplayName());
      }

      return var3;
   }

   private static Object readProperty(Method var0, Object var1) {
      try {
         return var0.invoke(var1, PropertyUtil.NO_ARGUMENTS);
      } catch (Exception var4) {
         StringBuilder var2 = new StringBuilder("Could not invoke ");
         var2.append(var0);
         var2.append(" on ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString(), var4);
      }
   }

   @Factory
   public static <T> Matcher<T> samePropertyValuesAs(T var0) {
      return new SamePropertyValuesAs<>((T)var0);
   }

   @Override
   public void describeTo(Description var1) {
      StringBuilder var2 = new StringBuilder("same property values as ");
      var2.append(this.expectedBean.getClass().getSimpleName());
      var1.appendText(var2.toString()).appendList(" [", ", ", "]", this.propertyMatchers);
   }

   @Override
   public boolean matchesSafely(T var1, Description var2) {
      boolean var3;
      if (this.isCompatibleType((T)var1, var2) && this.hasNoExtraProperties((T)var1, var2) && this.hasMatchingValues((T)var1, var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static class PropertyMatcher extends DiagnosingMatcher<Object> {
      private final Matcher<Object> matcher;
      private final String propertyName;
      private final Method readMethod;

      public PropertyMatcher(PropertyDescriptor var1, Object var2) {
         this.propertyName = var1.getDisplayName();
         Method var3 = var1.getReadMethod();
         this.readMethod = var3;
         this.matcher = IsEqual.equalTo(SamePropertyValuesAs.readProperty(var3, var2));
      }

      @Override
      public void describeTo(Description var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.propertyName);
         var2.append(": ");
         var1.appendText(var2.toString()).appendDescriptionOf(this.matcher);
      }

      @Override
      public boolean matches(Object var1, Description var2) {
         var1 = SamePropertyValuesAs.readProperty(this.readMethod, var1);
         if (!this.matcher.matches(var1)) {
            StringBuilder var3 = new StringBuilder();
            var3.append(this.propertyName);
            var3.append(" ");
            var2.appendText(var3.toString());
            this.matcher.describeMismatch(var1, var2);
            return false;
         } else {
            return true;
         }
      }
   }
}
