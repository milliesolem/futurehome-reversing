package kotlin.reflect

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class KVariance {
   IN,
   INVARIANT,
   OUT   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private KVariance[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<KVariance> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<KVariance> {
      return $ENTRIES;
   }
}
