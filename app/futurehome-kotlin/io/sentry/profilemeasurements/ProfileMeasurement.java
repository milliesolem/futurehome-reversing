package io.sentry.profilemeasurements;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class ProfileMeasurement implements JsonUnknown, JsonSerializable {
   public static final String ID_CPU_USAGE = "cpu_usage";
   public static final String ID_FROZEN_FRAME_RENDERS = "frozen_frame_renders";
   public static final String ID_MEMORY_FOOTPRINT = "memory_footprint";
   public static final String ID_MEMORY_NATIVE_FOOTPRINT = "memory_native_footprint";
   public static final String ID_SCREEN_FRAME_RATES = "screen_frame_rates";
   public static final String ID_SLOW_FRAME_RENDERS = "slow_frame_renders";
   public static final String ID_UNKNOWN = "unknown";
   public static final String UNIT_BYTES = "byte";
   public static final String UNIT_HZ = "hz";
   public static final String UNIT_NANOSECONDS = "nanosecond";
   public static final String UNIT_PERCENT = "percent";
   public static final String UNIT_UNKNOWN = "unknown";
   private String unit;
   private Map<String, Object> unknown;
   private Collection<ProfileMeasurementValue> values;

   public ProfileMeasurement() {
      this("unknown", new ArrayList<>());
   }

   public ProfileMeasurement(String var1, Collection<ProfileMeasurementValue> var2) {
      this.unit = var1;
      this.values = var2;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.unknown, var1.unknown) || !this.unit.equals(var1.unit) || !new ArrayList<>(this.values).equals(new ArrayList<>(var1.values))) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getUnit() {
      return this.unit;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Collection<ProfileMeasurementValue> getValues() {
      return this.values;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.unknown, this.unit, this.values);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("unit").value(var2, this.unit);
      var1.name("values").value(var2, this.values);
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

   public void setUnit(String var1) {
      this.unit = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setValues(Collection<ProfileMeasurementValue> var1) {
      this.values = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ProfileMeasurement> {
      public ProfileMeasurement deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         ProfileMeasurement var5 = new ProfileMeasurement();
         ConcurrentHashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var6 = var1.nextName();
            var6.hashCode();
            if (!var6.equals("values")) {
               if (!var6.equals("unit")) {
                  ConcurrentHashMap var4 = var3;
                  if (var3 == null) {
                     var4 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var4, var6);
                  var3 = var4;
               } else {
                  String var7 = var1.nextStringOrNull();
                  if (var7 != null) {
                     var5.unit = var7;
                  }
               }
            } else {
               List var8 = var1.nextListOrNull(var2, new ProfileMeasurementValue.Deserializer());
               if (var8 != null) {
                  var5.values = var8;
               }
            }
         }

         var5.setUnknown(var3);
         var1.endObject();
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String UNIT = "unit";
      public static final String VALUES = "values";
   }
}
