package kotlin.io

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class FileWalkDirection {
   BOTTOM_UP,
   TOP_DOWN   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private FileWalkDirection[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<FileWalkDirection> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<FileWalkDirection> {
      return $ENTRIES;
   }
}
