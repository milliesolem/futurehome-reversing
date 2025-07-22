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

public final class OperatingSystem implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "os";
   private String build;
   private String kernelVersion;
   private String name;
   private String rawDescription;
   private Boolean rooted;
   private Map<String, Object> unknown;
   private String version;

   public OperatingSystem() {
   }

   OperatingSystem(OperatingSystem var1) {
      this.name = var1.name;
      this.version = var1.version;
      this.rawDescription = var1.rawDescription;
      this.build = var1.build;
      this.kernelVersion = var1.kernelVersion;
      this.rooted = var1.rooted;
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.name, var1.name)
            || !Objects.equals(this.version, var1.version)
            || !Objects.equals(this.rawDescription, var1.rawDescription)
            || !Objects.equals(this.build, var1.build)
            || !Objects.equals(this.kernelVersion, var1.kernelVersion)
            || !Objects.equals(this.rooted, var1.rooted)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getBuild() {
      return this.build;
   }

   public String getKernelVersion() {
      return this.kernelVersion;
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
   public int hashCode() {
      return Objects.hash(this.name, this.version, this.rawDescription, this.build, this.kernelVersion, this.rooted);
   }

   public Boolean isRooted() {
      return this.rooted;
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

      if (this.build != null) {
         var1.name("build").value(this.build);
      }

      if (this.kernelVersion != null) {
         var1.name("kernel_version").value(this.kernelVersion);
      }

      if (this.rooted != null) {
         var1.name("rooted").value(this.rooted);
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

   public void setBuild(String var1) {
      this.build = var1;
   }

   public void setKernelVersion(String var1) {
      this.kernelVersion = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setRawDescription(String var1) {
      this.rawDescription = var1;
   }

   public void setRooted(Boolean var1) {
      this.rooted = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }

   public static final class Deserializer implements JsonDeserializer<OperatingSystem> {
      public OperatingSystem deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         OperatingSystem var7 = new OperatingSystem();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -925311743:
                  if (var8.equals("rooted")) {
                     var3 = 0;
                  }
                  break;
               case -339173787:
                  if (var8.equals("raw_description")) {
                     var3 = 1;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 2;
                  }
                  break;
               case 94094958:
                  if (var8.equals("build")) {
                     var3 = 3;
                  }
                  break;
               case 351608024:
                  if (var8.equals("version")) {
                     var3 = 4;
                  }
                  break;
               case 2015527638:
                  if (var8.equals("kernel_version")) {
                     var3 = 5;
                  }
            }

            switch (var3) {
               case 0:
                  var7.rooted = var1.nextBooleanOrNull();
                  break;
               case 1:
                  var7.rawDescription = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.name = var1.nextStringOrNull();
                  break;
               case 3:
                  var7.build = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.version = var1.nextStringOrNull();
                  break;
               case 5:
                  var7.kernelVersion = var1.nextStringOrNull();
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
      public static final String BUILD = "build";
      public static final String KERNEL_VERSION = "kernel_version";
      public static final String NAME = "name";
      public static final String RAW_DESCRIPTION = "raw_description";
      public static final String ROOTED = "rooted";
      public static final String VERSION = "version";
   }
}
