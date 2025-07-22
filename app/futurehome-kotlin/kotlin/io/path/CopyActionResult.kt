package kotlin.io.path

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class CopyActionResult {
   CONTINUE,
   SKIP_SUBTREE,
   TERMINATE   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private CopyActionResult[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<CopyActionResult> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<CopyActionResult> {
      return $ENTRIES;
   }
}
