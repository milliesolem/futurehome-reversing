package no.futurehome.futurehome_app.widget.models

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class WidgetState {
   GeneralError,
   HubOffline,
   Loading,
   NoConnection,
   NoHub,
   NoSites,
   NoUser,
   OpenAppError,
   PresentingModesAndShortcuts,
   PresentingSites   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private WidgetState[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<WidgetState> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<WidgetState> {
      return $ENTRIES;
   }
}
