package kotlin.io.path

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class OnErrorResult {
   SKIP_SUBTREE,
   TERMINATE   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private OnErrorResult[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<OnErrorResult> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<OnErrorResult> {
      return $ENTRIES;
   }
}
