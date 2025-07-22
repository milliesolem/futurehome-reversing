package kotlin.time

import java.util.concurrent.TimeUnit
import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class DurationUnit(timeUnit: TimeUnit) {
   DAYS(TimeUnit.DAYS),
   HOURS(TimeUnit.HOURS),
   MICROSECONDS(TimeUnit.MICROSECONDS),
   MILLISECONDS(TimeUnit.MILLISECONDS),
   MINUTES(TimeUnit.MINUTES),
   NANOSECONDS(TimeUnit.NANOSECONDS),
   SECONDS(TimeUnit.SECONDS)
   internal final val timeUnit: TimeUnit
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private DurationUnit[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<DurationUnit> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.timeUnit = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<DurationUnit> {
      return $ENTRIES;
   }
}
