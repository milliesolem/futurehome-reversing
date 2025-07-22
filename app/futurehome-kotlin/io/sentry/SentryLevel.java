package io.sentry;

import java.io.IOException;
import java.util.Locale;

public enum SentryLevel implements JsonSerializable {
   DEBUG,
   ERROR,
   FATAL,
   INFO,
   WARNING;
   private static final SentryLevel[] $VALUES = $values();

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.value(this.name().toLowerCase(Locale.ROOT));
   }

   public static final class Deserializer implements JsonDeserializer<SentryLevel> {
      public SentryLevel deserialize(ObjectReader var1, ILogger var2) throws Exception {
         return SentryLevel.valueOf(var1.nextString().toUpperCase(Locale.ROOT));
      }
   }
}
