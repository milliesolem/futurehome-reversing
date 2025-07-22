package com.signify.hue.flutterreactiveble.ble

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class BleStatus(code: Int) {
   LOCATION_SERVICES_DISABLED(4),
   POWERED_OFF(3),
   READY(5),
   UNAUTHORIZED(2),
   UNKNOWN(0),
   UNSUPPORTED(1)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private BleStatus[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<BleStatus> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<BleStatus> {
      return $ENTRIES;
   }
}
