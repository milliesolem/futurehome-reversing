package no.futurehome.futurehome_app.widget

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

private enum class SetupConnectionError {
   NoConnection,
   None   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private SetupConnectionError[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<SetupConnectionError> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<SetupConnectionError> {
      return $ENTRIES;
   }
}
