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
import java.util.Map;

public final class ProfileMeasurementValue implements JsonUnknown, JsonSerializable {
   private String relativeStartNs;
   private Map<String, Object> unknown;
   private double value;

   public ProfileMeasurementValue() {
      this(0L, 0);
   }

   public ProfileMeasurementValue(Long var1, Number var2) {
      this.relativeStartNs = var1.toString();
      this.value = var2.doubleValue();
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.unknown, var1.unknown) || !this.relativeStartNs.equals(var1.relativeStartNs) || this.value != var1.value) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getRelativeStartNs() {
      return this.relativeStartNs;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public double getValue() {
      return this.value;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.unknown, this.relativeStartNs, this.value);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("value").value(var2, this.value);
      var1.name("elapsed_since_start_ns").value(var2, this.relativeStartNs);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var4 = this.unknown.get(var6);
            var1.name(var6);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ProfileMeasurementValue> {
      public ProfileMeasurementValue deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         ProfileMeasurementValue var5 = new ProfileMeasurementValue();
         ConcurrentHashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var6 = var1.nextName();
            var6.hashCode();
            if (!var6.equals("elapsed_since_start_ns")) {
               if (!var6.equals("value")) {
                  ConcurrentHashMap var4 = var3;
                  if (var3 == null) {
                     var4 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var4, var6);
                  var3 = var4;
               } else {
                  Double var7 = var1.nextDoubleOrNull();
                  if (var7 != null) {
                     var5.value = var7;
                  }
               }
            } else {
               String var8 = var1.nextStringOrNull();
               if (var8 != null) {
                  var5.relativeStartNs = var8;
               }
            }
         }

         var5.setUnknown(var3);
         var1.endObject();
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String START_NS = "elapsed_since_start_ns";
      public static final String VALUE = "value";
   }
}
