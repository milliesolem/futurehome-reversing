package org.hamcrest;

import java.util.Arrays;
import java.util.Iterator;
import org.hamcrest.internal.ArrayIterator;
import org.hamcrest.internal.SelfDescribingValueIterator;

public abstract class BaseDescription implements Description {
   private Description appendList(String var1, String var2, String var3, Iterator<? extends SelfDescribing> var4) {
      this.append(var1);

      for (boolean var5 = false; var4.hasNext(); var5 = true) {
         if (var5) {
            this.append(var2);
         }

         this.appendDescriptionOf((SelfDescribing)var4.next());
      }

      this.append(var3);
      return this;
   }

   private <T> Description appendValueList(String var1, String var2, String var3, Iterator<T> var4) {
      return this.appendList(var1, var2, var3, new SelfDescribingValueIterator(var4));
   }

   private String descriptionOf(Object var1) {
      try {
         return String.valueOf(var1);
      } catch (Exception var3) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1.getClass().getName());
         var2.append("@");
         var2.append(Integer.toHexString(var1.hashCode()));
         return var2.toString();
      }
   }

   private void toJavaSyntax(char var1) {
      if (var1 != '\t') {
         if (var1 != '\n') {
            if (var1 != '\r') {
               if (var1 != '"') {
                  this.append(var1);
               } else {
                  this.append("\\\"");
               }
            } else {
               this.append("\\r");
            }
         } else {
            this.append("\\n");
         }
      } else {
         this.append("\\t");
      }
   }

   private void toJavaSyntax(String var1) {
      this.append('"');

      for (int var2 = 0; var2 < var1.length(); var2++) {
         this.toJavaSyntax(var1.charAt(var2));
      }

      this.append('"');
   }

   protected abstract void append(char var1);

   protected void append(String var1) {
      for (int var2 = 0; var2 < var1.length(); var2++) {
         this.append(var1.charAt(var2));
      }
   }

   @Override
   public Description appendDescriptionOf(SelfDescribing var1) {
      var1.describeTo(this);
      return this;
   }

   @Override
   public Description appendList(String var1, String var2, String var3, Iterable<? extends SelfDescribing> var4) {
      return this.appendList(var1, var2, var3, var4.iterator());
   }

   @Override
   public Description appendText(String var1) {
      this.append(var1);
      return this;
   }

   @Override
   public Description appendValue(Object var1) {
      if (var1 == null) {
         this.append("null");
      } else if (var1 instanceof String) {
         this.toJavaSyntax((String)var1);
      } else if (var1 instanceof Character) {
         this.append('"');
         this.toJavaSyntax((Character)var1);
         this.append('"');
      } else if (var1 instanceof Short) {
         this.append('<');
         this.append(this.descriptionOf(var1));
         this.append("s>");
      } else if (var1 instanceof Long) {
         this.append('<');
         this.append(this.descriptionOf(var1));
         this.append("L>");
      } else if (var1 instanceof Float) {
         this.append('<');
         this.append(this.descriptionOf(var1));
         this.append("F>");
      } else if (var1.getClass().isArray()) {
         this.appendValueList("[", ", ", "]", new ArrayIterator(var1));
      } else {
         this.append('<');
         this.append(this.descriptionOf(var1));
         this.append('>');
      }

      return this;
   }

   @Override
   public <T> Description appendValueList(String var1, String var2, String var3, Iterable<T> var4) {
      return this.appendValueList(var1, var2, var3, var4.iterator());
   }

   @Override
   public <T> Description appendValueList(String var1, String var2, String var3, T... var4) {
      return this.appendValueList(var1, var2, var3, Arrays.asList(var4));
   }
}
