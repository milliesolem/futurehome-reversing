package kotlin.reflect

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class KVisibility {
   INTERNAL,
   PRIVATE,
   PROTECTED,
   PUBLIC   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private KVisibility[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<KVisibility> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<KVisibility> {
      return $ENTRIES;
   }
}
