package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class SentryRuntime implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "runtime";
   private String name;
   private String rawDescription;
   private Map<String, Object> unknown;
   private String version;

   public SentryRuntime() {
   }

   SentryRuntime(SentryRuntime var1) {
      this.name = var1.name;
      this.version = var1.version;
      this.rawDescription = var1.rawDescription;
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   public String getName() {
      return this.name;
   }

   public String getRawDescription() {
      return this.rawDescription;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getVersion() {
      return this.version;
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

      if (this.rawDescription != null) {
         var1.name("raw_description").value(this.rawDescription);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.unknown.get(var6);
            var1.name(var6);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setRawDescription(String var1) {
      this.rawDescription = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryRuntime> {
      public SentryRuntime deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryRuntime var7 = new SentryRuntime();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -339173787:
                  if (var8.equals("raw_description")) {
                     var3 = 0;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 1;
                  }
                  break;
               case 351608024:
                  if (var8.equals("version")) {
                     var3 = 2;
                  }
            }

            switch (var3) {
               case 0:
                  var7.rawDescription = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.name = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.version = var1.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String NAME = "name";
      public static final String RAW_DESCRIPTION = "raw_description";
      public static final String VERSION = "version";
   }
}
