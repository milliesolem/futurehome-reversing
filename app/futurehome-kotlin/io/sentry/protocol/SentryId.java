package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.StringUtils;
import java.io.IOException;
import java.util.UUID;

public final class SentryId implements JsonSerializable {
   public static final SentryId EMPTY_ID = new SentryId(new UUID(0L, 0L));
   private final UUID uuid;

   public SentryId() {
      UUID var1 = (UUID)null;
      this(null);
   }

   public SentryId(String var1) {
      this.uuid = this.fromStringSentryId(StringUtils.normalizeUUID(var1));
   }

   public SentryId(UUID var1) {
      UUID var2 = var1;
      if (var1 == null) {
         var2 = UUID.randomUUID();
      }

      this.uuid = var2;
   }

   private UUID fromStringSentryId(String var1) {
      String var2 = var1;
      if (var1.length() == 32) {
         var2 = new StringBuilder(var1).insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-").toString();
      }

      if (var2.length() == 36) {
         return UUID.fromString(var2);
      } else {
         StringBuilder var3 = new StringBuilder(
            "String representation of SentryId has either 32 (UUID no dashes) or 36 characters long (completed UUID). Received: "
         );
         var3.append(var2);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (this.uuid.compareTo(var1.uuid) != 0) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.uuid.hashCode();
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.value(this.toString());
   }

   @Override
   public String toString() {
      return StringUtils.normalizeUUID(this.uuid.toString()).replace("-", "");
   }

   public static final class Deserializer implements JsonDeserializer<SentryId> {
      public SentryId deserialize(ObjectReader var1, ILogger var2) throws Exception {
         return new SentryId(var1.nextString());
      }
   }
}
