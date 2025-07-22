package io.sentry;

import java.util.Locale;

public final class DsnUtil {
   public static boolean urlContainsDsnHost(SentryOptions var0, String var1) {
      if (var0 == null) {
         return false;
      } else if (var1 == null) {
         return false;
      } else if (var0.getDsn() == null) {
         return false;
      } else {
         String var2 = var0.retrieveParsedDsn().getSentryUri().getHost();
         return var2 == null ? false : var1.toLowerCase(Locale.ROOT).contains(var2.toLowerCase(Locale.ROOT));
      }
   }
}
