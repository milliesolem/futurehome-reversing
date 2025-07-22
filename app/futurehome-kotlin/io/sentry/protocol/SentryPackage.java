package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SentryPackage implements JsonUnknown, JsonSerializable {
   private String name;
   private Map<String, Object> unknown;
   private String version;

   public SentryPackage(String var1, String var2) {
      this.name = Objects.requireNonNull(var1, "name is required.");
      this.version = Objects.requireNonNull(var2, "version is required.");
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!java.util.Objects.equals(this.name, var1.name) || !java.util.Objects.equals(this.version, var1.version)) {
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
      return java.util.Objects.hash(this.name, this.version);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("name").value(this.name);
      var1.name("version").value(this.version);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setName(String var1) {
      this.name = Objects.requireNonNull(var1, "name is required.");
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVersion(String var1) {
      this.version = Objects.requireNonNull(var1, "version is required.");
   }

   public static final class Deserializer implements JsonDeserializer<SentryPackage> {
      public SentryPackage deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         String var5 = null;
         String var4 = null;
         HashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("name")) {
               if (!var7.equals("version")) {
                  HashMap var6 = var3;
                  if (var3 == null) {
                     var6 = new HashMap();
                  }

                  var1.nextUnknown(var2, var6, var7);
                  var3 = var6;
               } else {
                  var4 = var1.nextString();
               }
            } else {
               var5 = var1.nextString();
            }
         }

         var1.endObject();
         if (var5 == null) {
            IllegalStateException var8 = new IllegalStateException("Missing required field \"name\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"name\"", var8);
            throw var8;
         } else if (var4 != null) {
            SentryPackage var10 = new SentryPackage(var5, var4);
            var10.setUnknown(var3);
            return var10;
         } else {
            IllegalStateException var9 = new IllegalStateException("Missing required field \"version\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"version\"", var9);
            throw var9;
         }
      }
   }

   public static final class JsonKeys {
      public static final String NAME = "name";
      public static final String VERSION = "version";
   }
}
