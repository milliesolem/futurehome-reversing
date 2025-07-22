package io.sentry;

import java.util.Locale;

public enum MonitorScheduleType {
   CRONTAB,
   INTERVAL;
   private static final MonitorScheduleType[] $VALUES = $values();

   public String apiName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
