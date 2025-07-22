package no.futurehome.futurehome_app.widget

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

private enum class SetupActiveModeError {
   FetchError,
   HubOffline,
   NoActiveSiteId,
   NoRuntimeEnv,
   NoSiteTokenHash,
   None   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private SetupActiveModeError[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<SetupActiveModeError> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<SetupActiveModeError> {
      return $ENTRIES;
   }
}
