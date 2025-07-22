package no.futurehome.futurehome_app.widget

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

private enum class SetupSitesError {
   FetchError,
   NoAuthCredentials,
   NoRuntimeEnv,
   None,
   SiteHasNoHub,
   UserHasNoSites   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private SetupSitesError[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<SetupSitesError> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<SetupSitesError> {
      return $ENTRIES;
   }
}
