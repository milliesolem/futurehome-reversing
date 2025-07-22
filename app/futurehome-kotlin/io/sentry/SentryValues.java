package io.sentry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class SentryValues<T> {
   private final List<T> values;

   SentryValues(List<T> var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = new ArrayList(0);
      }

      this.values = new ArrayList<>((Collection<? extends T>)var2);
   }

   public List<T> getValues() {
      return this.values;
   }

   public static final class JsonKeys {
      public static final String VALUES = "values";
   }
}
