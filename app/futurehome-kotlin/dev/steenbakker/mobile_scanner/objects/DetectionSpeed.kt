package dev.steenbakker.mobile_scanner.objects

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class DetectionSpeed(intValue: Int) {
   NORMAL(1),
   NO_DUPLICATES(0),
   UNRESTRICTED(2)
   public final val intValue: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private DetectionSpeed[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<DetectionSpeed> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.intValue = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<DetectionSpeed> {
      return $ENTRIES;
   }
}
