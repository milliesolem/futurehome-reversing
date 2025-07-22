package io.sentry.android.core.internal.util;

import android.content.ContentProvider;
import io.sentry.NoOpLogger;
import io.sentry.android.core.BuildInfoProvider;

public final class ContentProviderSecurityChecker {
   private final BuildInfoProvider buildInfoProvider;

   public ContentProviderSecurityChecker() {
      this(new BuildInfoProvider(NoOpLogger.getInstance()));
   }

   public ContentProviderSecurityChecker(BuildInfoProvider var1) {
      this.buildInfoProvider = var1;
   }

   public void checkPrivilegeEscalation(ContentProvider var1) {
      int var2 = this.buildInfoProvider.getSdkInfoVersion();
      if (var2 >= 26 && var2 <= 28) {
         String var3 = var1.getCallingPackage();
         String var4 = var1.getContext().getPackageName();
         if (var3 == null || !var3.equals(var4)) {
            throw new SecurityException("Provider does not allow for granting of Uri permissions");
         }
      }
   }
}
