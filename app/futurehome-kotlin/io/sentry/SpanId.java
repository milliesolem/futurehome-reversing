package io.sentry;

import io.sentry.util.Objects;
import io.sentry.util.StringUtils;
import java.io.IOException;
import java.util.UUID;

public final class SpanId implements JsonSerializable {
   public static final SpanId EMPTY_ID = new SpanId(new UUID(0L, 0L));
   private final String value;

   public SpanId() {
      this(UUID.randomUUID());
   }

   public SpanId(String var1) {
      this.value = Objects.requireNonNull(var1, "value is required");
   }

   private SpanId(UUID var1) {
      this(StringUtils.normalizeUUID(var1.toString()).replace("-", "").substring(0, 16));
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.value.equals(var1.value);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.value(this.value);
   }

   @Override
   public String toString() {
      return this.value;
   }

   public static final class Deserializer implements JsonDeserializer<SpanId> {
      public SpanId deserialize(ObjectReader var1, ILogger var2) throws Exception {
         return new SpanId(var1.nextString());
      }
   }
}
