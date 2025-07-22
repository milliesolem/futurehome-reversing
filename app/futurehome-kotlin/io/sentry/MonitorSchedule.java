package io.sentry;

import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MonitorSchedule implements JsonUnknown, JsonSerializable {
   private String type;
   private String unit;
   private Map<String, Object> unknown;
   private String value;

   public MonitorSchedule(String var1, String var2, String var3) {
      this.type = var1;
      this.value = var2;
      this.unit = var3;
   }

   public static MonitorSchedule crontab(String var0) {
      return new MonitorSchedule(MonitorScheduleType.CRONTAB.apiName(), var0, null);
   }

   public static MonitorSchedule interval(Integer var0, MonitorScheduleUnit var1) {
      return new MonitorSchedule(MonitorScheduleType.INTERVAL.apiName(), var0.toString(), var1.apiName());
   }

   public String getType() {
      return this.type;
   }

   public String getUnit() {
      return this.unit;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getValue() {
      return this.value;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("type").value(this.type);
      if (MonitorScheduleType.INTERVAL.apiName().equalsIgnoreCase(this.type)) {
         boolean var7 = false /* VF: Semaphore variable */;

         label45:
         try {
            var7 = true;
            var1.name("value").value(Integer.valueOf(this.value));
            var7 = false;
         } finally {
            if (var7) {
               var2.log(SentryLevel.ERROR, "Unable to serialize monitor schedule value: %s", this.value);
               break label45;
            }
         }
      } else {
         var1.name("value").value(this.value);
      }

      if (this.unit != null) {
         var1.name("unit").value(this.unit);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var9 : var3.keySet()) {
            Object var4 = this.unknown.get(var9);
            var1.name(var9).value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setType(String var1) {
      this.type = var1;
   }

   public void setUnit(MonitorScheduleUnit var1) {
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.apiName();
      }

      this.unit = var2;
   }

   public void setUnit(String var1) {
      this.unit = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setValue(Integer var1) {
      this.value = var1.toString();
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public static final class Deserializer implements JsonDeserializer<MonitorSchedule> {
      public MonitorSchedule deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         String var8 = null;
         String var7 = null;
         String var6 = null;
         Object var5 = var6;

         while (var1.peek() == JsonToken.NAME) {
            String var10 = var1.nextName();
            var10.hashCode();
            int var4 = var10.hashCode();
            byte var3 = -1;
            switch (var4) {
               case 3575610:
                  if (var10.equals("type")) {
                     var3 = 0;
                  }
                  break;
               case 3594628:
                  if (var10.equals("unit")) {
                     var3 = 1;
                  }
                  break;
               case 111972721:
                  if (var10.equals("value")) {
                     var3 = 2;
                  }
            }

            switch (var3) {
               case 0:
                  var8 = var1.nextStringOrNull();
                  break;
               case 1:
                  var6 = var1.nextStringOrNull();
                  break;
               case 2:
                  var7 = var1.nextStringOrNull();
                  break;
               default:
                  Object var9 = var5;
                  if (var5 == null) {
                     var9 = new HashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var9, var10);
                  var5 = var9;
            }
         }

         var1.endObject();
         if (var8 == null) {
            IllegalStateException var11 = new IllegalStateException("Missing required field \"type\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"type\"", var11);
            throw var11;
         } else if (var7 != null) {
            MonitorSchedule var13 = new MonitorSchedule(var8, var7, var6);
            var13.setUnknown((Map<String, Object>)var5);
            return var13;
         } else {
            IllegalStateException var12 = new IllegalStateException("Missing required field \"value\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"value\"", var12);
            throw var12;
         }
      }
   }

   public static final class JsonKeys {
      public static final String TYPE = "type";
      public static final String UNIT = "unit";
      public static final String VALUE = "value";
   }
}
