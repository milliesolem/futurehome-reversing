package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ViewHierarchy implements JsonUnknown, JsonSerializable {
   private final String renderingSystem;
   private Map<String, Object> unknown;
   private final List<ViewHierarchyNode> windows;

   public ViewHierarchy(String var1, List<ViewHierarchyNode> var2) {
      this.renderingSystem = var1;
      this.windows = var2;
   }

   public String getRenderingSystem() {
      return this.renderingSystem;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public List<ViewHierarchyNode> getWindows() {
      return this.windows;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.renderingSystem != null) {
         var1.name("rendering_system").value(this.renderingSystem);
      }

      if (this.windows != null) {
         var1.name("windows").value(var2, this.windows);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4).value(var2, var3);
         }
      }

      var1.endObject();
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ViewHierarchy> {
      public ViewHierarchy deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         String var5 = null;
         List var4 = null;
         HashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("rendering_system")) {
               if (!var7.equals("windows")) {
                  HashMap var6 = var3;
                  if (var3 == null) {
                     var6 = new HashMap();
                  }

                  var1.nextUnknown(var2, var6, var7);
                  var3 = var6;
               } else {
                  var4 = var1.nextListOrNull(var2, new ViewHierarchyNode.Deserializer());
               }
            } else {
               var5 = var1.nextStringOrNull();
            }
         }

         var1.endObject();
         ViewHierarchy var8 = new ViewHierarchy(var5, var4);
         var8.setUnknown(var3);
         return var8;
      }
   }

   public static final class JsonKeys {
      public static final String RENDERING_SYSTEM = "rendering_system";
      public static final String WINDOWS = "windows";
   }
}
