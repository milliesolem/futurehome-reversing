package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public final class Geo implements JsonUnknown, JsonSerializable {
   private String city;
   private String countryCode;
   private String region;
   private Map<String, Object> unknown;

   public Geo() {
   }

   public Geo(Geo var1) {
      this.city = var1.city;
      this.countryCode = var1.countryCode;
      this.region = var1.region;
   }

   public static Geo fromMap(Map<String, Object> var0) {
      Geo var5 = new Geo();

      for (Entry var8 : var0.entrySet()) {
         Object var7 = var8.getValue();
         String var9 = (String)var8.getKey();
         var9.hashCode();
         int var2 = var9.hashCode();
         byte var1 = -1;
         switch (var2) {
            case -934795532:
               if (var9.equals("region")) {
                  var1 = 0;
               }
               break;
            case 3053931:
               if (var9.equals("city")) {
                  var1 = 1;
               }
               break;
            case 1481071862:
               if (var9.equals("country_code")) {
                  var1 = 2;
               }
         }

         Object var4 = null;
         String var10 = null;
         Object var3 = null;
         switch (var1) {
            case 0:
               if (var7 instanceof String) {
                  var10 = (String)var7;
               }

               var5.region = var10;
               break;
            case 1:
               String var12 = (String)var4;
               if (var7 instanceof String) {
                  var12 = (String)var7;
               }

               var5.city = var12;
               break;
            case 2:
               String var11 = (String)var3;
               if (var7 instanceof String) {
                  var11 = (String)var7;
               }

               var5.countryCode = var11;
         }
      }

      return var5;
   }

   public String getCity() {
      return this.city;
   }

   public String getCountryCode() {
      return this.countryCode;
   }

   public String getRegion() {
      return this.region;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.city != null) {
         var1.name("city").value(this.city);
      }

      if (this.countryCode != null) {
         var1.name("country_code").value(this.countryCode);
      }

      if (this.region != null) {
         var1.name("region").value(this.region);
      }

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

   public void setCity(String var1) {
      this.city = var1;
   }

   public void setCountryCode(String var1) {
      this.countryCode = var1;
   }

   public void setRegion(String var1) {
      this.region = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Geo> {
      public Geo deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Geo var7 = new Geo();
         ConcurrentHashMap var6 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -934795532:
                  if (var8.equals("region")) {
                     var3 = 0;
                  }
                  break;
               case 3053931:
                  if (var8.equals("city")) {
                     var3 = 1;
                  }
                  break;
               case 1481071862:
                  if (var8.equals("country_code")) {
                     var3 = 2;
                  }
            }

            switch (var3) {
               case 0:
                  var7.region = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.city = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.countryCode = var1.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var5 = var6;
                  if (var6 == null) {
                     var5 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var5, var8);
                  var6 = var5;
            }
         }

         var7.setUnknown(var6);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String CITY = "city";
      public static final String COUNTRY_CODE = "country_code";
      public static final String REGION = "region";
   }
}
