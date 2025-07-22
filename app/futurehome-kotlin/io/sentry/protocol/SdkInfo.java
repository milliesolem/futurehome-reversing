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
import java.util.Map;

public final class SdkInfo implements JsonUnknown, JsonSerializable {
   private String sdkName;
   private Map<String, Object> unknown;
   private Integer versionMajor;
   private Integer versionMinor;
   private Integer versionPatchlevel;

   public String getSdkName() {
      return this.sdkName;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Integer getVersionMajor() {
      return this.versionMajor;
   }

   public Integer getVersionMinor() {
      return this.versionMinor;
   }

   public Integer getVersionPatchlevel() {
      return this.versionPatchlevel;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.sdkName != null) {
         var1.name("sdk_name").value(this.sdkName);
      }

      if (this.versionMajor != null) {
         var1.name("version_major").value(this.versionMajor);
      }

      if (this.versionMinor != null) {
         var1.name("version_minor").value(this.versionMinor);
      }

      if (this.versionPatchlevel != null) {
         var1.name("version_patchlevel").value(this.versionPatchlevel);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.unknown.get(var6);
            var1.name(var6).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setSdkName(String var1) {
      this.sdkName = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVersionMajor(Integer var1) {
      this.versionMajor = var1;
   }

   public void setVersionMinor(Integer var1) {
      this.versionMinor = var1;
   }

   public void setVersionPatchlevel(Integer var1) {
      this.versionPatchlevel = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SdkInfo> {
      public SdkInfo deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SdkInfo var7 = new SdkInfo();
         var1.beginObject();
         HashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case 270207856:
                  if (var8.equals("sdk_name")) {
                     var3 = 0;
                  }
                  break;
               case 696101379:
                  if (var8.equals("version_patchlevel")) {
                     var3 = 1;
                  }
                  break;
               case 1111241618:
                  if (var8.equals("version_major")) {
                     var3 = 2;
                  }
                  break;
               case 1111483790:
                  if (var8.equals("version_minor")) {
                     var3 = 3;
                  }
            }

            switch (var3) {
               case 0:
                  var7.sdkName = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.versionPatchlevel = var1.nextIntegerOrNull();
                  break;
               case 2:
                  var7.versionMajor = var1.nextIntegerOrNull();
                  break;
               case 3:
                  var7.versionMinor = var1.nextIntegerOrNull();
                  break;
               default:
                  HashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new HashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var1.endObject();
         var7.setUnknown(var5);
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String SDK_NAME = "sdk_name";
      public static final String VERSION_MAJOR = "version_major";
      public static final String VERSION_MINOR = "version_minor";
      public static final String VERSION_PATCHLEVEL = "version_patchlevel";
   }
}
