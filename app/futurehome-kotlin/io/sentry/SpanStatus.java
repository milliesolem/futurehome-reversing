package io.sentry;

import java.io.IOException;
import java.util.Locale;

public enum SpanStatus implements JsonSerializable {
   ABORTED(409),
   ALREADY_EXISTS(409),
   CANCELLED(499),
   DATA_LOSS(500),
   DEADLINE_EXCEEDED(504),
   FAILED_PRECONDITION(400),
   INTERNAL_ERROR(500),
   INVALID_ARGUMENT(400),
   NOT_FOUND(404),
   OK(200, 299),
   OUT_OF_RANGE(400),
   PERMISSION_DENIED(403),
   RESOURCE_EXHAUSTED(429),
   UNAUTHENTICATED(401),
   UNAVAILABLE(503),
   UNIMPLEMENTED(501),
   UNKNOWN(500),
   UNKNOWN_ERROR(500);

   private static final SpanStatus[] $VALUES = $values();
   private final int maxHttpStatusCode;
   private final int minHttpStatusCode;

   private SpanStatus(int var3) {
      this.minHttpStatusCode = var3;
      this.maxHttpStatusCode = var3;
   }

   private SpanStatus(int var3, int var4) {
      this.minHttpStatusCode = var3;
      this.maxHttpStatusCode = var4;
   }

   public static SpanStatus fromHttpStatusCode(int var0) {
      for (SpanStatus var4 : values()) {
         if (var4.matches(var0)) {
            return var4;
         }
      }

      return null;
   }

   public static SpanStatus fromHttpStatusCode(Integer var0, SpanStatus var1) {
      SpanStatus var2;
      if (var0 != null) {
         var2 = fromHttpStatusCode(var0);
      } else {
         var2 = var1;
      }

      if (var2 != null) {
         var1 = var2;
      }

      return var1;
   }

   private boolean matches(int var1) {
      boolean var2;
      if (var1 >= this.minHttpStatusCode && var1 <= this.maxHttpStatusCode) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.value(this.name().toLowerCase(Locale.ROOT));
   }

   public static final class Deserializer implements JsonDeserializer<SpanStatus> {
      public SpanStatus deserialize(ObjectReader var1, ILogger var2) throws Exception {
         return SpanStatus.valueOf(var1.nextString().toUpperCase(Locale.ROOT));
      }
   }
}
