package kotlin

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class DeprecationLevel {
   ERROR,
   HIDDEN,
   WARNING   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private DeprecationLevel[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<DeprecationLevel> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<DeprecationLevel> {
      return $ENTRIES;
   }
}
