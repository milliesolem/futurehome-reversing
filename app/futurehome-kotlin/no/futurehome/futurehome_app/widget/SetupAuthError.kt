package no.futurehome.futurehome_app.widget

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

private enum class SetupAuthError {
   CheckAccessTokenError,
   NoAuthCredentials,
   NoRuntimeEnv,
   None,
   RefreshAuthTokenError   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private SetupAuthError[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<SetupAuthError> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   @JvmStatic
   fun getEntries(): EnumEntries<SetupAuthError> {
      return $ENTRIES;
   }
}
