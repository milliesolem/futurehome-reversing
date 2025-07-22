package com.signify.hue.flutterreactiveble.model

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class ScanMode(code: Int) {
   BALANCED(1),
   LOW_LATENCY(2),
   LOW_POWER(0),
   OPPORTUNISTIC(-1)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private ScanMode[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<ScanMode> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<ScanMode> {
      return $ENTRIES;
   }
}
