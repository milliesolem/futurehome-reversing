package com.signify.hue.flutterreactiveble.ble

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class Connectable(code: Int) {
   CONNECTABLE(2),
   NOT_CONNECTABLE(1),
   UNKNOWN(0)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private Connectable[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<Connectable> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<Connectable> {
      return $ENTRIES;
   }
}
