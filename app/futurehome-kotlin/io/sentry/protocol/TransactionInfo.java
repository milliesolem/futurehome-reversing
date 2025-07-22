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

public final class TransactionInfo implements JsonSerializable, JsonUnknown {
   private final String source;
   private Map<String, Object> unknown;

   public TransactionInfo(String var1) {
      this.source = var1;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.source != null) {
         var1.name("source").value(var2, this.source);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var5);
            var1.name(var5);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<TransactionInfo> {
      public TransactionInfo deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         String var4 = null;
         ConcurrentHashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var6 = var1.nextName();
            var6.hashCode();
            if (!var6.equals("source")) {
               ConcurrentHashMap var5 = var3;
               if (var3 == null) {
                  var5 = new ConcurrentHashMap();
               }

               var1.nextUnknown(var2, var5, var6);
               var3 = var5;
            } else {
               var4 = var1.nextStringOrNull();
            }
         }

         TransactionInfo var7 = new TransactionInfo(var4);
         var7.setUnknown(var3);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String SOURCE = "source";
   }
}
