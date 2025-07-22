package no.futurehome.futurehome_app.widget.models

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class ModeE {
   AWAY,
   HOME,
   SLEEP,
   VACATION   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private ModeE[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<ModeE> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<ModeE> {
      return $ENTRIES;
   }
}
