package io.sentry;

import io.sentry.util.Objects;
import java.util.HashMap;
import java.util.Map;

public final class CustomSamplingContext {
   private final Map<String, Object> data = new HashMap<>();

   public Object get(String var1) {
      Objects.requireNonNull(var1, "key is required");
      return this.data.get(var1);
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public void set(String var1, Object var2) {
      Objects.requireNonNull(var1, "key is required");
      this.data.put(var1, var2);
   }
}
