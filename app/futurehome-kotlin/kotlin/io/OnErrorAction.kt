package kotlin.io

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class OnErrorAction {
   SKIP,
   TERMINATE   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private OnErrorAction[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<OnErrorAction> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<OnErrorAction> {
      return $ENTRIES;
   }
}
