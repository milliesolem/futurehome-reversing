package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class RRWebBreadcrumbEvent extends RRWebEvent implements JsonUnknown, JsonSerializable {
   public static final String EVENT_TAG = "breadcrumb";
   private double breadcrumbTimestamp;
   private String breadcrumbType;
   private String category;
   private Map<String, Object> data;
   private Map<String, Object> dataUnknown;
   private SentryLevel level;
   private String message;
   private Map<String, Object> payloadUnknown;
   private String tag = "breadcrumb";
   private Map<String, Object> unknown;

   public RRWebBreadcrumbEvent() {
      super(RRWebEventType.Custom);
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("tag").value(this.tag);
      var1.name("payload");
      this.serializePayload(var1, var2);
      Map var3 = this.dataUnknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.dataUnknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   private void serializePayload(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.breadcrumbType != null) {
         var1.name("type").value(this.breadcrumbType);
      }

      var1.name("timestamp").value(var2, BigDecimal.valueOf(this.breadcrumbTimestamp));
      if (this.category != null) {
         var1.name("category").value(this.category);
      }

      if (this.message != null) {
         var1.name("message").value(this.message);
      }

      if (this.level != null) {
         var1.name("level").value(var2, this.level);
      }

      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      Map var3 = this.payloadUnknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            Object var4 = this.payloadUnknown.get(var5);
            var1.name(var5);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   public double getBreadcrumbTimestamp() {
      return this.breadcrumbTimestamp;
   }

   public String getBreadcrumbType() {
      return this.breadcrumbType;
   }

   public String getCategory() {
      return this.category;
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public SentryLevel getLevel() {
      return this.level;
   }

   public String getMessage() {
      return this.message;
   }

   public Map<String, Object> getPayloadUnknown() {
      return this.payloadUnknown;
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
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setBreadcrumbTimestamp(double var1) {
      this.breadcrumbTimestamp = var1;
   }

   public void setBreadcrumbType(String var1) {
      this.breadcrumbType = var1;
   }

   public void setCategory(String var1) {
      this.category = var1;
   }

   public void setData(Map<String, Object> var1) {
      ConcurrentHashMap var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = new ConcurrentHashMap(var1);
      }

      this.data = var2;
   }

   public void setDataUnknown(Map<String, Object> var1) {
      this.dataUnknown = var1;
   }

   public void setLevel(SentryLevel var1) {
      this.level = var1;
   }

   public void setMessage(String var1) {
      this.message = var1;
   }

   public void setPayloadUnknown(Map<String, Object> var1) {
      this.payloadUnknown = var1;
   }

   public void setTag(String var1) {
      this.tag = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<RRWebBreadcrumbEvent> {
      private void deserializeData(RRWebBreadcrumbEvent var1, ObjectReader var2, ILogger var3) throws Exception {
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

      private void deserializePayload(RRWebBreadcrumbEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         ConcurrentHashMap var6 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var8 = var2.nextName();
            var8.hashCode();
            int var5 = var8.hashCode();
            byte var4 = -1;
            switch (var5) {
               case 3076010:
                  if (var8.equals("data")) {
                     var4 = 0;
                  }
                  break;
               case 3575610:
                  if (var8.equals("type")) {
                     var4 = 1;
                  }
                  break;
               case 50511102:
                  if (var8.equals("category")) {
                     var4 = 2;
                  }
                  break;
               case 55126294:
                  if (var8.equals("timestamp")) {
                     var4 = 3;
                  }
                  break;
               case 102865796:
                  if (var8.equals("level")) {
                     var4 = 4;
                  }
                  break;
               case 954925063:
                  if (var8.equals("message")) {
                     var4 = 5;
                  }
            }

            switch (var4) {
               case 0:
                  Map var11 = CollectionUtils.newConcurrentHashMap((Map)var2.nextObjectOrNull());
                  if (var11 != null) {
                     var1.data = var11;
                  }
                  break;
               case 1:
                  var1.breadcrumbType = var2.nextStringOrNull();
                  break;
               case 2:
                  var1.category = var2.nextStringOrNull();
                  break;
               case 3:
                  var1.breadcrumbTimestamp = var2.nextDouble();
                  break;
               case 4:
                  try {
                     SentryLevel.Deserializer var10 = new SentryLevel.Deserializer();
                     var1.level = var10.deserialize(var2, var3);
                  } catch (Exception var9) {
                     var3.log(SentryLevel.DEBUG, var9, "Error when deserializing SentryLevel");
                  }
                  break;
               case 5:
                  var1.message = var2.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var7 = var6;
                  if (var6 == null) {
                     var7 = new ConcurrentHashMap();
                  }

                  var2.nextUnknown(var3, var7, var8);
                  var6 = var7;
            }
         }

         var1.setPayloadUnknown(var6);
         var2.endObject();
      }

      public RRWebBreadcrumbEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebBreadcrumbEvent var5 = new RRWebBreadcrumbEvent();
         RRWebEvent.Deserializer var6 = new RRWebEvent.Deserializer();
         HashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("data")) {
               if (!var6.deserializeValue(var5, var7, var1, var2)) {
                  HashMap var4 = var3;
                  if (var3 == null) {
                     var4 = new HashMap();
                  }

                  var1.nextUnknown(var2, var4, var7);
                  var3 = var4;
               }
            } else {
               this.deserializeData(var5, var1, var2);
            }
         }

         var5.setUnknown(var3);
         var1.endObject();
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String CATEGORY = "category";
      public static final String DATA = "data";
      public static final String LEVEL = "level";
      public static final String MESSAGE = "message";
      public static final String PAYLOAD = "payload";
      public static final String TIMESTAMP = "timestamp";
      public static final String TYPE = "type";
   }
}
