package io.sentry.clientreport;

import io.sentry.DateUtils;
import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ClientReport implements JsonUnknown, JsonSerializable {
   private final List<DiscardedEvent> discardedEvents;
   private final Date timestamp;
   private Map<String, Object> unknown;

   public ClientReport(Date var1, List<DiscardedEvent> var2) {
      this.timestamp = var1;
      this.discardedEvents = var2;
   }

   public List<DiscardedEvent> getDiscardedEvents() {
      return this.discardedEvents;
   }

   public Date getTimestamp() {
      return this.timestamp;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("timestamp").value(DateUtils.getTimestamp(this.timestamp));
      var1.name("discarded_events").value(var2, this.discardedEvents);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var4 = this.unknown.get(var6);
            var1.name(var6).value(var2, var4);
         }
      }

      var1.endObject();
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ClientReport> {
      private Exception missingRequiredFieldException(String var1, ILogger var2) {
         StringBuilder var3 = new StringBuilder("Missing required field \"");
         var3.append(var1);
         var3.append("\"");
         var1 = var3.toString();
         IllegalStateException var5 = new IllegalStateException(var1);
         var2.log(SentryLevel.ERROR, var1, var5);
         return var5;
      }

      public ClientReport deserialize(ObjectReader var1, ILogger var2) throws Exception {
         ArrayList var6 = new ArrayList();
         var1.beginObject();
         Date var4 = null;
         HashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("discarded_events")) {
               if (!var7.equals("timestamp")) {
                  HashMap var5 = var3;
                  if (var3 == null) {
                     var5 = new HashMap();
                  }

                  var1.nextUnknown(var2, var5, var7);
                  var3 = var5;
               } else {
                  var4 = var1.nextDateOrNull(var2);
               }
            } else {
               var6.addAll(var1.nextListOrNull(var2, new DiscardedEvent.Deserializer()));
            }
         }

         var1.endObject();
         if (var4 == null) {
            throw this.missingRequiredFieldException("timestamp", var2);
         } else if (!var6.isEmpty()) {
            ClientReport var8 = new ClientReport(var4, var6);
            var8.setUnknown(var3);
            return var8;
         } else {
            throw this.missingRequiredFieldException("discarded_events", var2);
         }
      }
   }

   public static final class JsonKeys {
      public static final String DISCARDED_EVENTS = "discarded_events";
      public static final String TIMESTAMP = "timestamp";
   }
}
