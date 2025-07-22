package org.hamcrest;

public interface Description {
   Description NONE = new Description.NullDescription();

   Description appendDescriptionOf(SelfDescribing var1);

   Description appendList(String var1, String var2, String var3, Iterable<? extends SelfDescribing> var4);

   Description appendText(String var1);

   Description appendValue(Object var1);

   <T> Description appendValueList(String var1, String var2, String var3, Iterable<T> var4);

   <T> Description appendValueList(String var1, String var2, String var3, T... var4);

   public static final class NullDescription implements Description {
      @Override
      public Description appendDescriptionOf(SelfDescribing var1) {
         return this;
      }

      @Override
      public Description appendList(String var1, String var2, String var3, Iterable<? extends SelfDescribing> var4) {
         return this;
      }

      @Override
      public Description appendText(String var1) {
         return this;
      }

      @Override
      public Description appendValue(Object var1) {
         return this;
      }

      @Override
      public <T> Description appendValueList(String var1, String var2, String var3, Iterable<T> var4) {
         return this;
      }

      @Override
      public <T> Description appendValueList(String var1, String var2, String var3, T... var4) {
         return this;
      }

      @Override
      public String toString() {
         return "";
      }
   }
}
