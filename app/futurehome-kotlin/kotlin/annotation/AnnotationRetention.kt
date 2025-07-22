package kotlin.annotation

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class AnnotationRetention {
   BINARY,
   RUNTIME,
   SOURCE   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private AnnotationRetention[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<AnnotationRetention> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<AnnotationRetention> {
      return $ENTRIES;
   }
}
