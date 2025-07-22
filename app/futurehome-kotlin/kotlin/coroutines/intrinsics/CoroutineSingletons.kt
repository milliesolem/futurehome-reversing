package kotlin.coroutines.intrinsics

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

internal enum class CoroutineSingletons {
   COROUTINE_SUSPENDED,
   RESUMED,
   UNDECIDED   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private CoroutineSingletons[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<CoroutineSingletons> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<CoroutineSingletons> {
      return $ENTRIES;
   }
}
