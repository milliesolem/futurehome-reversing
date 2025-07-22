package io.sentry.android.ndk;

import io.sentry.android.core.SentryAndroidOptions;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class SentryNdk {
   private static final CountDownLatch loadLibraryLatch = new CountDownLatch(1);

   static {
      new Thread(new SentryNdk$$ExternalSyntheticLambda0(), "SentryNdkLoadLibs").start();
   }

   private SentryNdk() {
   }

   public static void close() {
      try {
         if (loadLibraryLatch.await(2000L, TimeUnit.MILLISECONDS)) {
            shutdown();
         } else {
            IllegalStateException var0 = new IllegalStateException("Timeout waiting for Sentry NDK library to load");
            throw var0;
         }
      } catch (InterruptedException var1) {
         throw new IllegalStateException("Thread interrupted while waiting for NDK libs to be loaded", var1);
      }
   }

   public static void init(SentryAndroidOptions var0) {
      SentryNdkUtil.addPackage(var0.getSdkVersion());

      try {
         if (loadLibraryLatch.await(2000L, TimeUnit.MILLISECONDS)) {
            initSentryNative(var0);
            if (var0.isEnableScopeSync()) {
               NdkScopeObserver var1 = new NdkScopeObserver(var0);
               var0.addScopeObserver(var1);
            }

            NativeModuleListLoader var5 = new NativeModuleListLoader();
            DebugImagesLoader var2 = new DebugImagesLoader(var0, var5);
            var0.setDebugImagesLoader(var2);
         } else {
            IllegalStateException var4 = new IllegalStateException("Timeout waiting for Sentry NDK library to load");
            throw var4;
         }
      } catch (InterruptedException var3) {
         throw new IllegalStateException("Thread interrupted while waiting for NDK libs to be loaded", var3);
      }
   }

   private static native void initSentryNative(SentryAndroidOptions var0);

   private static native void shutdown();
}
