package io.sentry.clientreport;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class DiscardedEvent implements JsonUnknown, JsonSerializable {
   private final String category;
   private final Long quantity;
   private final String reason;
   private Map<String, Object> unknown;

   public DiscardedEvent(String var1, String var2, Long var3) {
      this.reason = var1;
      this.category = var2;
      this.quantity = var3;
   }

   public String getCategory() {
      return this.category;
   }

   public Long getQuantity() {
      return this.quantity;
   }

   public String getReason() {
      return this.reason;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("reason").value(this.reason);
      var1.name("category").value(this.category);
      var1.name("quantity").value(this.quantity);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.unknown.get(var6);
            var1.name(var6).value(var2, var5);
         }
      }

      var1.endObject();
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DiscardedEvent{reason='");
      var1.append(this.reason);
      var1.append("', category='");
      var1.append(this.category);
      var1.append("', quantity=");
      var1.append(this.quantity);
      var1.append('}');
      return var1.toString();
   }

   public static final class Deserializer implements JsonDeserializer<DiscardedEvent> {
      private Exception missingRequiredFieldException(String var1, ILogger var2) {
         StringBuilder var3 = new StringBuilder("Missing required field \"");
         var3.append(var1);
         var3.append("\"");
         var1 = var3.toString();
         IllegalStateException var5 = new IllegalStateException(var1);
         var2.log(SentryLevel.ERROR, var1, var5);
         return var5;
      }

      public DiscardedEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         String var8 = null;
         String var7 = null;
         Long var6 = null;
         Object var5 = var6;

         while (var1.peek() == JsonToken.NAME) {
            String var10 = var1.nextName();
            var10.hashCode();
            int var4 = var10.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1285004149:
                  if (var10.equals("quantity")) {
                     var3 = 0;
                  }
                  break;
               case -934964668:
                  if (var10.equals("reason")) {
                     var3 = 1;
                  }
                  break;
               case 50511102:
                  if (var10.equals("category")) {
                     var3 = 2;
                  }
            }

            switch (var3) {
               case 0:
                  var6 = var1.nextLongOrNull();
                  break;
               case 1:
                  var8 = var1.nextStringOrNull();
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
            throw this.missingRequiredFieldException("reason", var2);
         } else if (var7 == null) {
            throw this.missingRequiredFieldException("category", var2);
         } else if (var6 != null) {
            DiscardedEvent var11 = new DiscardedEvent(var8, var7, var6);
            var11.setUnknown((Map<String, Object>)var5);
            return var11;
         } else {
            throw this.missingRequiredFieldException("quantity", var2);
         }
      }
   }

   public static final class JsonKeys {
      public static final String CATEGORY = "category";
      public static final String QUANTITY = "quantity";
      public static final String REASON = "reason";
   }
}
