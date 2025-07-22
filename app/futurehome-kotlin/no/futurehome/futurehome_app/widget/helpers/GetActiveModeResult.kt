package no.futurehome.futurehome_app.widget.helpers

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class GetActiveModeResult {
   AwayMode,
   FetchError,
   HomeMode,
   HubTimeout,
   SleepMode,
   VacationMode   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private GetActiveModeResult[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<GetActiveModeResult> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<GetActiveModeResult> {
      return $ENTRIES;
   }
}
