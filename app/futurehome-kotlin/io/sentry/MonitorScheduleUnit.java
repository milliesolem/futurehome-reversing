package io.sentry;

import java.util.Locale;

public enum MonitorScheduleUnit {
   DAY,
   HOUR,
   MINUTE,
   MONTH,
   WEEK,
   YEAR;
   private static final MonitorScheduleUnit[] $VALUES = $values();

   public String apiName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
