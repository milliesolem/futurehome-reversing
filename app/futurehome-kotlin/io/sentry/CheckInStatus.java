package io.sentry;

import java.util.Locale;

public enum CheckInStatus {
   ERROR,
   IN_PROGRESS,
   OK;
   private static final CheckInStatus[] $VALUES = $values();

   public String apiName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
