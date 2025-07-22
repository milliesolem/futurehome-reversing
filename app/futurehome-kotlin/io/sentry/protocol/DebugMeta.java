package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DebugMeta implements JsonUnknown, JsonSerializable {
   private List<DebugImage> images;
   private SdkInfo sdkInfo;
   private Map<String, Object> unknown;

   public List<DebugImage> getImages() {
      return this.images;
   }

   public SdkInfo getSdkInfo() {
      return this.sdkInfo;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.sdkInfo != null) {
         var1.name("sdk_info").value(var2, this.sdkInfo);
      }

      if (this.images != null) {
         var1.name("images").value(var2, this.images);
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

   public void setImages(List<DebugImage> var1) {
      ArrayList var2;
      if (var1 != null) {
         var2 = new ArrayList(var1);
      } else {
         var2 = null;
      }

      this.images = var2;
   }

   public void setSdkInfo(SdkInfo var1) {
      this.sdkInfo = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<DebugMeta> {
      public DebugMeta deserialize(ObjectReader var1, ILogger var2) throws Exception {
         DebugMeta var5 = new DebugMeta();
         var1.beginObject();
         HashMap var4 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var6 = var1.nextName();
            var6.hashCode();
            if (!var6.equals("images")) {
               if (!var6.equals("sdk_info")) {
                  HashMap var3 = var4;
                  if (var4 == null) {
                     var3 = new HashMap();
                  }

                  var1.nextUnknown(var2, var3, var6);
                  var4 = var3;
               } else {
                  var5.sdkInfo = var1.nextOrNull(var2, new SdkInfo.Deserializer());
               }
            } else {
               var5.images = var1.nextListOrNull(var2, new DebugImage.Deserializer());
            }
         }

         var1.endObject();
         var5.setUnknown(var4);
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String IMAGES = "images";
      public static final String SDK_INFO = "sdk_info";
   }
}
