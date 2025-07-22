package no.futurehome.futurehome_app.widget

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

private enum class SetupSiteTokenHashError {
   FetchError,
   NoActiveSiteId,
   NoAuthCredentials,
   NoRuntimeEnv,
   None   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private SetupSiteTokenHashError[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<SetupSiteTokenHashError> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<SetupSiteTokenHashError> {
      return $ENTRIES;
   }
}
