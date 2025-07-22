package no.futurehome.futurehome_app.widget.models

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class Mode {
   AWAY,
   HOME,
   SLEEP,
   VACATION   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private Mode[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<Mode> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<Mode> {
      return $ENTRIES;
   }
}
