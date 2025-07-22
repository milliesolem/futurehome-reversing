package io.sentry;

import io.sentry.clientreport.ClientReport;
import io.sentry.protocol.SentryTransaction;
import java.io.IOException;
import java.util.Locale;

public enum SentryItemType implements JsonSerializable {
   Attachment("attachment"),
   CheckIn("check_in"),
   ClientReport("client_report"),
   Event("event"),
   Feedback("feedback"),
   Profile("profile"),
   ReplayEvent("replay_event"),
   ReplayRecording("replay_recording"),
   ReplayVideo("replay_video"),
   Session("session"),
   Statsd("statsd"),
   Transaction("transaction"),
   Unknown("__unknown__"),
   UserFeedback("user_report");

   private static final SentryItemType[] $VALUES = $values();
   private final String itemType;

   private SentryItemType(String var3) {
      this.itemType = var3;
   }

   public static SentryItemType resolve(Object var0) {
      if (var0 instanceof SentryEvent) {
         return Event;
      } else if (var0 instanceof SentryTransaction) {
         return Transaction;
      } else if (var0 instanceof Session) {
         return Session;
      } else {
         return var0 instanceof ClientReport ? ClientReport : Attachment;
      }
   }

   public static SentryItemType valueOfLabel(String var0) {
      for (SentryItemType var3 : values()) {
         if (var3.itemType.equals(var0)) {
            return var3;
         }
      }

      return Unknown;
   }

   public String getItemType() {
      return this.itemType;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.value(this.itemType);
   }

   public static final class Deserializer implements JsonDeserializer<SentryItemType> {
      public SentryItemType deserialize(ObjectReader var1, ILogger var2) throws Exception {
         return SentryItemType.valueOfLabel(var1.nextString().toLowerCase(Locale.ROOT));
      }
   }
}
