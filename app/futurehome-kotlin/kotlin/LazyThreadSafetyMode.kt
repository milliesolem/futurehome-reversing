package kotlin

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class LazyThreadSafetyMode {
   NONE,
   PUBLICATION,
   SYNCHRONIZED   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private LazyThreadSafetyMode[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<LazyThreadSafetyMode> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<LazyThreadSafetyMode> {
      return $ENTRIES;
   }
}
