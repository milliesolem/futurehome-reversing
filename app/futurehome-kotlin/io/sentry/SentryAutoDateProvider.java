package io.sentry;

import io.sentry.util.Platform;

public final class SentryAutoDateProvider implements SentryDateProvider {
   private final SentryDateProvider dateProvider;

   public SentryAutoDateProvider() {
      if (checkInstantAvailabilityAndPrecision()) {
         this.dateProvider = new SentryInstantDateProvider();
      } else {
         this.dateProvider = new SentryNanotimeDateProvider();
      }
   }

   private static boolean checkInstantAvailabilityAndPrecision() {
      boolean var0;
      if (Platform.isJvm() && Platform.isJavaNinePlus()) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Override
   public SentryDate now() {
      return this.dateProvider.now();
   }
}
