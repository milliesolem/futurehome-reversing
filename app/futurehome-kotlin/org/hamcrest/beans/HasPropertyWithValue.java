package org.hamcrest.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasPropertyWithValue<T> extends TypeSafeDiagnosingMatcher<T> {
   private static final Condition.Step<PropertyDescriptor, Method> WITH_READ_METHOD = withReadMethod();
   private final String propertyName;
   private final Matcher<Object> valueMatcher;

   public HasPropertyWithValue(String var1, Matcher<?> var2) {
      this.propertyName = var1;
      this.valueMatcher = nastyGenericsWorkaround(var2);
   }

   @Factory
   public static <T> Matcher<T> hasProperty(String var0, Matcher<?> var1) {
      return new HasPropertyWithValue<>(var0, var1);
   }

   private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> var0) {
      return var0;
   }

   private Condition<PropertyDescriptor> propertyOn(T var1, Description var2) {
      var1 = PropertyUtil.getPropertyDescriptor(this.propertyName, var1);
      if (var1 == null) {
         StringBuilder var4 = new StringBuilder("No property \"");
         var4.append(this.propertyName);
         var4.append("\"");
         var2.appendText(var4.toString());
         return Condition.notMatched();
      } else {
         return Condition.matched(var1, var2);
      }
   }

   private Condition.Step<Method, Object> withPropertyValue(T var1) {
      return new Condition.Step<Method, Object>(this, var1) {
         final HasPropertyWithValue this$0;
         final Object val$bean;

         {
            this.this$0 = var1;
            this.val$bean = var2;
         }

         public Condition<Object> apply(Method var1, Description var2) {
            try {
               return Condition.matched(var1.invoke(this.val$bean, PropertyUtil.NO_ARGUMENTS), var2);
            } catch (Exception var3) {
               var2.appendText(var3.getMessage());
               return Condition.notMatched();
            }
         }
      };
   }

   private static Condition.Step<PropertyDescriptor, Method> withReadMethod() {
      return new Condition.Step<PropertyDescriptor, Method>() {
         public Condition<Method> apply(PropertyDescriptor var1, Description var2) {
            Method var3 = var1.getReadMethod();
            if (var3 == null) {
               StringBuilder var4 = new StringBuilder("property \"");
               var4.append(var1.getName());
               var4.append("\" is not readable");
               var2.appendText(var4.toString());
               return Condition.notMatched();
            } else {
               return Condition.matched(var3, var2);
            }
         }
      };
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("hasProperty(").appendValue(this.propertyName).appendText(", ").appendDescriptionOf(this.valueMatcher).appendText(")");
   }

   @Override
   public boolean matchesSafely(T var1, Description var2) {
      Condition var5 = this.propertyOn((T)var1, var2).and(WITH_READ_METHOD).and(this.withPropertyValue((T)var1));
      var1 = this.valueMatcher;
      StringBuilder var3 = new StringBuilder("property '");
      var3.append(this.propertyName);
      var3.append("' ");
      return var5.matching(var1, var3.toString());
   }
}
