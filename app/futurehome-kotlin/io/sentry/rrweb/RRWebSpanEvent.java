package io.sentry.rrweb;

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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class RRWebSpanEvent extends RRWebEvent implements JsonSerializable, JsonUnknown {
   public static final String EVENT_TAG = "performanceSpan";
   private Map<String, Object> data;
   private Map<String, Object> dataUnknown;
   private String description;
   private double endTimestamp;
   private String op;
   private Map<String, Object> payloadUnknown;
   private double startTimestamp;
   private String tag = "performanceSpan";
   private Map<String, Object> unknown;

   public RRWebSpanEvent() {
      super(RRWebEventType.Custom);
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("tag").value(this.tag);
      var1.name("payload");
      this.serializePayload(var1, var2);
      Map var3 = this.dataUnknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var4 = this.dataUnknown.get(var6);
            var1.name(var6);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   private void serializePayload(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.op != null) {
         var1.name("op").value(this.op);
      }

      if (this.description != null) {
         var1.name("description").value(this.description);
      }

      var1.name("startTimestamp").value(var2, BigDecimal.valueOf(this.startTimestamp));
      var1.name("endTimestamp").value(var2, BigDecimal.valueOf(this.endTimestamp));
      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      Map var3 = this.payloadUnknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.payloadUnknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public String getDescription() {
      return this.description;
   }

   public double getEndTimestamp() {
      return this.endTimestamp;
   }

   public String getOp() {
      return this.op;
   }

   public Map<String, Object> getPayloadUnknown() {
      return this.payloadUnknown;
   }

   public double getStartTimestamp() {
      return this.startTimestamp;
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

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setEndTimestamp(double var1) {
      this.endTimestamp = var1;
   }

   public void setOp(String var1) {
      this.op = var1;
   }

   public void setPayloadUnknown(Map<String, Object> var1) {
      this.payloadUnknown = var1;
   }

   public void setStartTimestamp(double var1) {
      this.startTimestamp = var1;
   }

   public void setTag(String var1) {
      this.tag = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<RRWebSpanEvent> {
      private void deserializeData(RRWebSpanEvent var1, ObjectReader var2, ILogger var3) throws Exception {
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

      private void deserializePayload(RRWebSpanEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         ConcurrentHashMap var6 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var8 = var2.nextName();
            var8.hashCode();
            int var5 = var8.hashCode();
            byte var4 = -1;
            switch (var5) {
               case -1724546052:
                  if (var8.equals("description")) {
                     var4 = 0;
                  }
                  break;
               case -356088197:
                  if (var8.equals("endTimestamp")) {
                     var4 = 1;
                  }
                  break;
               case -299216172:
                  if (var8.equals("startTimestamp")) {
                     var4 = 2;
                  }
                  break;
               case 3553:
                  if (var8.equals("op")) {
                     var4 = 3;
                  }
                  break;
               case 3076010:
                  if (var8.equals("data")) {
                     var4 = 4;
                  }
            }

            switch (var4) {
               case 0:
                  var1.description = var2.nextStringOrNull();
                  break;
               case 1:
                  var1.endTimestamp = var2.nextDouble();
                  break;
               case 2:
                  var1.startTimestamp = var2.nextDouble();
                  break;
               case 3:
                  var1.op = var2.nextStringOrNull();
                  break;
               case 4:
                  Map var9 = CollectionUtils.newConcurrentHashMap((Map)var2.nextObjectOrNull());
                  if (var9 != null) {
                     var1.data = var9;
                  }
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

      public RRWebSpanEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebSpanEvent var5 = new RRWebSpanEvent();
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
      public static final String DATA = "data";
      public static final String DESCRIPTION = "description";
      public static final String END_TIMESTAMP = "endTimestamp";
      public static final String OP = "op";
      public static final String PAYLOAD = "payload";
      public static final String START_TIMESTAMP = "startTimestamp";
   }
}
