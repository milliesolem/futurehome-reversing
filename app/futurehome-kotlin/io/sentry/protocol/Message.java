package io.sentry.protocol;

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
import java.util.List;
import java.util.Map;

public final class Message implements JsonUnknown, JsonSerializable {
   private String formatted;
   private String message;
   private List<String> params;
   private Map<String, Object> unknown;

   public String getFormatted() {
      return this.formatted;
   }

   public String getMessage() {
      return this.message;
   }

   public List<String> getParams() {
      return this.params;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.formatted != null) {
         var1.name("formatted").value(this.formatted);
      }

      if (this.message != null) {
         var1.name("message").value(this.message);
      }

      List var3 = this.params;
      if (var3 != null && !var3.isEmpty()) {
         var1.name("params").value(var2, this.params);
      }

      Map var6 = this.unknown;
      if (var6 != null) {
         for (String var5 : var6.keySet()) {
            var3 = (List)this.unknown.get(var5);
            var1.name(var5);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setFormatted(String var1) {
      this.formatted = var1;
   }

   public void setMessage(String var1) {
      this.message = var1;
   }

   public void setParams(List<String> var1) {
      this.params = CollectionUtils.newArrayList(var1);
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Message> {
      public Message deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Message var7 = new Message();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -995427962:
                  if (var8.equals("params")) {
                     var3 = 0;
                  }
                  break;
               case 954925063:
                  if (var8.equals("message")) {
                     var3 = 1;
                  }
                  break;
               case 1811591356:
                  if (var8.equals("formatted")) {
                     var3 = 2;
                  }
            }

            switch (var3) {
               case 0:
                  List var9 = (List)var1.nextObjectOrNull();
                  if (var9 != null) {
                     var7.params = var9;
                  }
                  break;
               case 1:
                  var7.message = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.formatted = var1.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String FORMATTED = "formatted";
      public static final String MESSAGE = "message";
      public static final String PARAMS = "params";
   }
}
