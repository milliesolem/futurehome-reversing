package io.sentry.util;

import io.sentry.SentryIntegrationPackageStorage;

public final class IntegrationUtils {
   public static void addIntegrationToSdkVersion(String var0) {
      SentryIntegrationPackageStorage.getInstance().addIntegration(var0);
   }
}
