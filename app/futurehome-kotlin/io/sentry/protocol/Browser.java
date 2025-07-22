package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class Browser implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "browser";
   private String name;
   private Map<String, Object> unknown;
   private String version;

   public Browser() {
   }

   Browser(Browser var1) {
      this.name = var1.name;
      this.version = var1.version;
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.name, var1.name) || !Objects.equals(this.version, var1.version)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getName() {
      return this.name;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getVersion() {
      return this.version;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.name, this.version);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.name != null) {
         var1.name("name").value(this.name);
      }

      if (this.version != null) {
         var1.name("version").value(this.version);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setName(String var1) {
      this.name = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Browser> {
      public Browser deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Browser var5 = new Browser();
         ConcurrentHashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var6 = var1.nextName();
            var6.hashCode();
            if (!var6.equals("name")) {
               if (!var6.equals("version")) {
                  ConcurrentHashMap var4 = var3;
                  if (var3 == null) {
                     var4 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var4, var6);
                  var3 = var4;
               } else {
                  var5.version = var1.nextStringOrNull();
               }
            } else {
               var5.name = var1.nextStringOrNull();
            }
         }

         var5.setUnknown(var3);
         var1.endObject();
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String NAME = "name";
      public static final String VERSION = "version";
   }
}
