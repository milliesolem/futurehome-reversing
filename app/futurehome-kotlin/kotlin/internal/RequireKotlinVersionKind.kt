package kotlin.internal

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

internal enum class RequireKotlinVersionKind {
   API_VERSION,
   COMPILER_VERSION,
   LANGUAGE_VERSION   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private RequireKotlinVersionKind[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<RequireKotlinVersionKind> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<RequireKotlinVersionKind> {
      return $ENTRIES;
   }
}
