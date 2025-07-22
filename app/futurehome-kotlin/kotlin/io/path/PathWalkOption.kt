package kotlin.io.path

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class PathWalkOption {
   BREADTH_FIRST,
   FOLLOW_LINKS,
   INCLUDE_DIRECTORIES   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private PathWalkOption[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<PathWalkOption> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<PathWalkOption> {
      return $ENTRIES;
   }
}
