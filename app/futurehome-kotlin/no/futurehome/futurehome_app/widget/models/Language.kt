package no.futurehome.futurehome_app.widget.models

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class Language {
   DA,
   EN,
   FI,
   NB,
   SV   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private Language[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<Language> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<Language> {
      return $ENTRIES;
   }
}
