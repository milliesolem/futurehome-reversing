package com.signify.hue.flutterreactiveble.ble

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class ConnectionPriority(code: Int) {
   BALANCED(0),
   HIGH_PERFORMACE(1),
   LOW_POWER(2)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private ConnectionPriority[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<ConnectionPriority> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<ConnectionPriority> {
      return $ENTRIES;
   }
}
