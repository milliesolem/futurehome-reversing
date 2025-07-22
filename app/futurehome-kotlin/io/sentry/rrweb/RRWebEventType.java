package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import java.io.IOException;

public enum RRWebEventType implements JsonSerializable {
   Custom,
   DomContentLoaded,
   FullSnapshot,
   IncrementalSnapshot,
   Load,
   Meta,
   Plugin;
   private static final RRWebEventType[] $VALUES = $values();

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.value((long)this.ordinal());
   }

   public static final class Deserializer implements JsonDeserializer<RRWebEventType> {
      public RRWebEventType deserialize(ObjectReader var1, ILogger var2) throws Exception {
         return RRWebEventType.values()[var1.nextInt()];
      }
   }
}
