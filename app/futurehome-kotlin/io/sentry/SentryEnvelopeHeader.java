package io.sentry;

import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryId;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class SentryEnvelopeHeader implements JsonSerializable, JsonUnknown {
   private final SentryId eventId;
   private final SdkVersion sdkVersion;
   private Date sentAt;
   private final TraceContext traceContext;
   private Map<String, Object> unknown;

   public SentryEnvelopeHeader() {
      this(new SentryId());
   }

   public SentryEnvelopeHeader(SentryId var1) {
      this(var1, null);
   }

   public SentryEnvelopeHeader(SentryId var1, SdkVersion var2) {
      this(var1, var2, null);
   }

   public SentryEnvelopeHeader(SentryId var1, SdkVersion var2, TraceContext var3) {
      this.eventId = var1;
      this.sdkVersion = var2;
      this.traceContext = var3;
   }

   public SentryId getEventId() {
      return this.eventId;
   }

   public SdkVersion getSdkVersion() {
      return this.sdkVersion;
   }

   public Date getSentAt() {
      return this.sentAt;
   }

   public TraceContext getTraceContext() {
      return this.traceContext;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.eventId != null) {
         var1.name("event_id").value(var2, this.eventId);
      }

      if (this.sdkVersion != null) {
         var1.name("sdk").value(var2, this.sdkVersion);
      }

      if (this.traceContext != null) {
         var1.name("trace").value(var2, this.traceContext);
      }

      if (this.sentAt != null) {
         var1.name("sent_at").value(var2, DateUtils.getTimestamp(this.sentAt));
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

   public void setSentAt(Date var1) {
      this.sentAt = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryEnvelopeHeader> {
      public SentryEnvelopeHeader deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryId var9 = null;
         SdkVersion var8 = null;
         TraceContext var7 = null;
         Object var5 = var7;
         Object var6 = var7;

         while (var1.peek() == JsonToken.NAME) {
            String var11 = var1.nextName();
            var11.hashCode();
            int var4 = var11.hashCode();
            byte var3 = -1;
            switch (var4) {
               case 113722:
                  if (var11.equals("sdk")) {
                     var3 = 0;
                  }
                  break;
               case 110620997:
                  if (var11.equals("trace")) {
                     var3 = 1;
                  }
                  break;
               case 278118624:
                  if (var11.equals("event_id")) {
                     var3 = 2;
                  }
                  break;
               case 1980389946:
                  if (var11.equals("sent_at")) {
                     var3 = 3;
                  }
            }

            switch (var3) {
               case 0:
                  var8 = var1.nextOrNull(var2, new SdkVersion.Deserializer());
                  break;
               case 1:
                  var7 = var1.nextOrNull(var2, new TraceContext.Deserializer());
                  break;
               case 2:
                  var9 = var1.nextOrNull(var2, new SentryId.Deserializer());
                  break;
               case 3:
                  var5 = var1.nextDateOrNull(var2);
                  break;
               default:
                  Object var10 = var6;
                  if (var6 == null) {
                     var10 = new HashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var10, var11);
                  var6 = var10;
            }
         }

         SentryEnvelopeHeader var12 = new SentryEnvelopeHeader(var9, var8, var7);
         var12.setSentAt((Date)var5);
         var12.setUnknown((Map<String, Object>)var6);
         var1.endObject();
         return var12;
      }
   }

   public static final class JsonKeys {
      public static final String EVENT_ID = "event_id";
      public static final String SDK = "sdk";
      public static final String SENT_AT = "sent_at";
      public static final String TRACE = "trace";
   }
}
