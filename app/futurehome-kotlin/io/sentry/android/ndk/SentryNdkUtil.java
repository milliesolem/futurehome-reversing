package io.sentry.android.ndk;

import io.sentry.protocol.SdkVersion;

final class SentryNdkUtil {
   private SentryNdkUtil() {
   }

   static void addPackage(SdkVersion var0) {
      if (var0 != null) {
         var0.addPackage("maven:io.sentry:sentry-android-ndk", "7.22.1");
      }
   }
}
