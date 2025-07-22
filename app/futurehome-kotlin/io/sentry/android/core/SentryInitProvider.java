package io.sentry.android.core;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import io.sentry.Sentry;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;

public final class SentryInitProvider extends EmptySecureContentProvider {
   public void attachInfo(Context var1, ProviderInfo var2) {
      if (!SentryInitProvider.class.getName().equals(var2.authority)) {
         super.attachInfo(var1, var2);
      } else {
         throw new IllegalStateException("An applicationId is required to fulfill the manifest placeholder.");
      }
   }

   public String getType(Uri var1) {
      return null;
   }

   public boolean onCreate() {
      AndroidLogger var1 = new AndroidLogger();
      Context var2 = this.getContext();
      if (var2 == null) {
         var1.log(SentryLevel.FATAL, "App. Context from ContentProvider is null");
         return false;
      } else {
         if (ManifestMetadataReader.isAutoInit(var2, var1)) {
            SentryAndroid.init(var2, var1);
            SentryIntegrationPackageStorage.getInstance().addIntegration("AutoInit");
         }

         return true;
      }
   }

   public void shutdown() {
      Sentry.close();
   }
}
