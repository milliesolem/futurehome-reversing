package com.signify.hue.flutterreactiveble.model

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class CharacteristicErrorType(code: Int) {
   UNKNOWN(0)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private CharacteristicErrorType[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<CharacteristicErrorType> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<CharacteristicErrorType> {
      return $ENTRIES;
   }
}
