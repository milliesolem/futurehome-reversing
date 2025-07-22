package com.signify.hue.flutterreactiveble.model

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class ScanErrorType(code: Int) {
   UNKNOWN(0)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private ScanErrorType[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<ScanErrorType> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<ScanErrorType> {
      return $ENTRIES;
   }
}
