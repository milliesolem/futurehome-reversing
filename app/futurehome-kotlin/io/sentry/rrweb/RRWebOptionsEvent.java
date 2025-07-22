package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryOptions;
import io.sentry.SentryReplayOptions;
import io.sentry.protocol.SdkVersion;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class RRWebOptionsEvent extends RRWebEvent implements JsonSerializable, JsonUnknown {
   public static final String EVENT_TAG = "options";
   private Map<String, Object> dataUnknown;
   private Map<String, Object> optionsPayload = new HashMap<>();
   private String tag = "options";
   private Map<String, Object> unknown;

   public RRWebOptionsEvent() {
      super(RRWebEventType.Custom);
   }

   public RRWebOptionsEvent(SentryOptions var1) {
      this();
      SdkVersion var2 = var1.getSdkVersion();
      if (var2 != null) {
         this.optionsPayload.put("nativeSdkName", var2.getName());
         this.optionsPayload.put("nativeSdkVersion", var2.getVersion());
      }

      SentryReplayOptions var3 = var1.getSessionReplay();
      this.optionsPayload.put("errorSampleRate", var3.getOnErrorSampleRate());
      this.optionsPayload.put("sessionSampleRate", var3.getSessionSampleRate());
      this.optionsPayload.put("maskAllImages", var3.getMaskViewClasses().contains("android.widget.ImageView"));
      this.optionsPayload.put("maskAllText", var3.getMaskViewClasses().contains("android.widget.TextView"));
      this.optionsPayload.put("quality", var3.getQuality().serializedName());
      this.optionsPayload.put("maskedViewClasses", var3.getMaskViewClasses());
      this.optionsPayload.put("unmaskedViewClasses", var3.getUnmaskViewClasses());
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("tag").value(this.tag);
      var1.name("payload");
      this.serializePayload(var1, var2);
      Map var3 = this.dataUnknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.dataUnknown.get(var6);
            var1.name(var6);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   private void serializePayload(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      Map var3 = this.optionsPayload;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            var3 = (Map)this.optionsPayload.get(var5);
            var1.name(var5);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public Map<String, Object> getOptionsPayload() {
      return this.optionsPayload;
   }

   public String getTag() {
      return this.tag;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      new RRWebEvent.Serializer().serialize(this, var1, var2);
      var1.name("data");
      this.serializeData(var1, var2);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            Object var4 = this.unknown.get(var5);
            var1.name(var5);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setDataUnknown(Map<String, Object> var1) {
      this.dataUnknown = var1;
   }

   public void setOptionsPayload(Map<String, Object> var1) {
      this.optionsPayload = var1;
   }

   public void setTag(String var1) {
      this.tag = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<RRWebOptionsEvent> {
      private void deserializeData(RRWebOptionsEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         ConcurrentHashMap var4 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var6 = var2.nextName();
            var6.hashCode();
            if (!var6.equals("payload")) {
               if (!var6.equals("tag")) {
                  ConcurrentHashMap var5 = var4;
                  if (var4 == null) {
                     var5 = new ConcurrentHashMap();
                  }

                  var2.nextUnknown(var3, var5, var6);
                  var4 = var5;
               } else {
                  var6 = var2.nextStringOrNull();
                  String var7 = var6;
                  if (var6 == null) {
                     var7 = "";
                  }

                  var1.tag = var7;
               }
            } else {
               this.deserializePayload(var1, var2, var3);
            }
         }

         var1.setDataUnknown(var4);
         var2.endObject();
      }

      private void deserializePayload(RRWebOptionsEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         HashMap var5 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var6 = var2.nextName();
            HashMap var4 = var5;
            if (var5 == null) {
               var4 = new HashMap();
            }

            var2.nextUnknown(var3, var4, var6);
            var5 = var4;
         }

         if (var5 != null) {
            var1.setOptionsPayload(var5);
         }

         var2.endObject();
      }

      public RRWebOptionsEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebOptionsEvent var5 = new RRWebOptionsEvent();
         RRWebEvent.Deserializer var6 = new RRWebEvent.Deserializer();
         HashMap var4 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("data")) {
               if (!var6.deserializeValue(var5, var7, var1, var2)) {
                  HashMap var3 = var4;
                  if (var4 == null) {
                     var3 = new HashMap();
                  }

                  var1.nextUnknown(var2, var3, var7);
                  var4 = var3;
               }
            } else {
               this.deserializeData(var5, var1, var2);
            }
         }

         var5.setUnknown(var4);
         var1.endObject();
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String PAYLOAD = "payload";
   }
}
