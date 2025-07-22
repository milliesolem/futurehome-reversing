package kotlin.contracts

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class InvocationKind {
   AT_LEAST_ONCE,
   AT_MOST_ONCE,
   EXACTLY_ONCE,
   UNKNOWN   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private InvocationKind[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<InvocationKind> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<InvocationKind> {
      return $ENTRIES;
   }
}
