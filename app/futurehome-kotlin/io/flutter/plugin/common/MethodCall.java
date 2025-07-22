package io.flutter.plugin.common;

import java.util.Map;
import org.json.JSONObject;

public final class MethodCall {
   public final Object arguments;
   public final String method;

   public MethodCall(String var1, Object var2) {
      this.method = var1;
      this.arguments = var2;
   }

   public <T> T argument(String var1) {
      Object var2 = this.arguments;
      if (var2 == null) {
         return null;
      } else if (var2 instanceof Map) {
         return (T)((Map)var2).get(var1);
      } else if (var2 instanceof JSONObject) {
         return (T)((JSONObject)var2).opt(var1);
      } else {
         throw new ClassCastException();
      }
   }

   public <T> T arguments() {
      return (T)this.arguments;
   }

   public boolean hasArgument(String var1) {
      Object var2 = this.arguments;
      if (var2 == null) {
         return false;
      } else if (var2 instanceof Map) {
         return ((Map)var2).containsKey(var1);
      } else if (var2 instanceof JSONObject) {
         return ((JSONObject)var2).has(var1);
      } else {
         throw new ClassCastException();
      }
   }
}
