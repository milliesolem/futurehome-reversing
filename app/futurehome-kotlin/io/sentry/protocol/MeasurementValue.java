package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class MeasurementValue implements JsonUnknown, JsonSerializable {
   public static final String KEY_APP_START_COLD = "app_start_cold";
   public static final String KEY_APP_START_WARM = "app_start_warm";
   public static final String KEY_FRAMES_DELAY = "frames_delay";
   public static final String KEY_FRAMES_FROZEN = "frames_frozen";
   public static final String KEY_FRAMES_SLOW = "frames_slow";
   public static final String KEY_FRAMES_TOTAL = "frames_total";
   public static final String KEY_TIME_TO_FULL_DISPLAY = "time_to_full_display";
   public static final String KEY_TIME_TO_INITIAL_DISPLAY = "time_to_initial_display";
   private final String unit;
   private Map<String, Object> unknown;
   private final Number value;

   public MeasurementValue(Number var1, String var2) {
      this.value = var1;
      this.unit = var2;
   }

   public MeasurementValue(Number var1, String var2, Map<String, Object> var3) {
      this.value = var1;
      this.unit = var2;
      this.unknown = var3;
   }

   public String getUnit() {
      return this.unit;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Number getValue() {
      return this.value;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("value").value(this.value);
      if (this.unit != null) {
         var1.name("unit").value(this.unit);
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

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<MeasurementValue> {
      public MeasurementValue deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Number var5 = null;
         String var4 = null;
         ConcurrentHashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("unit")) {
               if (!var7.equals("value")) {
                  ConcurrentHashMap var6 = var3;
                  if (var3 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var7);
                  var3 = var6;
               } else {
                  var5 = (Number)var1.nextObjectOrNull();
               }
            } else {
               var4 = var1.nextStringOrNull();
            }
         }

         var1.endObject();
         if (var5 != null) {
            MeasurementValue var9 = new MeasurementValue(var5, var4);
            var9.setUnknown(var3);
            return var9;
         } else {
            IllegalStateException var8 = new IllegalStateException("Missing required field \"value\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"value\"", var8);
            throw var8;
         }
      }
   }

   public static final class JsonKeys {
      public static final String UNIT = "unit";
      public static final String VALUE = "value";
   }
}
