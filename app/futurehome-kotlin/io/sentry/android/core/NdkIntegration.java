package io.sentry.android.core;

import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class NdkIntegration implements Integration, Closeable {
   public static final String SENTRY_NDK_CLASS_NAME = "io.sentry.android.ndk.SentryNdk";
   private SentryAndroidOptions options;
   private final Class<?> sentryNdkClass;

   public NdkIntegration(Class<?> var1) {
      this.sentryNdkClass = var1;
   }

   private void disableNdkIntegration(SentryAndroidOptions var1) {
      var1.setEnableNdk(false);
      var1.setEnableScopeSync(false);
   }

   @Override
   public void close() throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/core/NdkIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 04: astore 1
      // 05: aload 1
      // 06: ifnull 7d
      // 09: aload 1
      // 0a: invokevirtual io/sentry/android/core/SentryAndroidOptions.isEnableNdk ()Z
      // 0d: ifeq 7d
      // 10: aload 0
      // 11: getfield io/sentry/android/core/NdkIntegration.sentryNdkClass Ljava/lang/Class;
      // 14: astore 1
      // 15: aload 1
      // 16: ifnull 7d
      // 19: aload 1
      // 1a: ldc "close"
      // 1c: aconst_null
      // 1d: invokevirtual java/lang/Class.getMethod (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      // 20: aconst_null
      // 21: aconst_null
      // 22: invokevirtual java/lang/reflect/Method.invoke (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      // 25: pop
      // 26: aload 0
      // 27: getfield io/sentry/android/core/NdkIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 2a: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 2d: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 30: ldc "NdkIntegration removed."
      // 32: bipush 0
      // 33: anewarray 4
      // 36: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 3b: goto 67
      // 3e: astore 1
      // 3f: aload 0
      // 40: getfield io/sentry/android/core/NdkIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 43: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 46: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 49: ldc "Failed to close SentryNdk."
      // 4b: aload 1
      // 4c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 51: goto 67
      // 54: astore 1
      // 55: aload 0
      // 56: getfield io/sentry/android/core/NdkIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 59: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 5c: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 5f: ldc "Failed to invoke the SentryNdk.close method."
      // 61: aload 1
      // 62: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 67: aload 0
      // 68: aload 0
      // 69: getfield io/sentry/android/core/NdkIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 6c: invokespecial io/sentry/android/core/NdkIntegration.disableNdkIntegration (Lio/sentry/android/core/SentryAndroidOptions;)V
      // 6f: goto 7d
      // 72: astore 1
      // 73: aload 0
      // 74: aload 0
      // 75: getfield io/sentry/android/core/NdkIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 78: invokespecial io/sentry/android/core/NdkIntegration.disableNdkIntegration (Lio/sentry/android/core/SentryAndroidOptions;)V
      // 7b: aload 1
      // 7c: athrow
      // 7d: return
   }

   Class<?> getSentryNdkClass() {
      return this.sentryNdkClass;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void register(IHub var1, SentryOptions var2) {
      Objects.requireNonNull(var1, "Hub is required");
      SentryAndroidOptions var8;
      if (var2 instanceof SentryAndroidOptions) {
         var8 = (SentryAndroidOptions)var2;
      } else {
         var8 = null;
      }

      SentryAndroidOptions var9 = Objects.requireNonNull(var8, "SentryAndroidOptions is required");
      this.options = var9;
      boolean var3 = var9.isEnableNdk();
      this.options.getLogger().log(SentryLevel.DEBUG, "NdkIntegration enabled: %s", var3);
      if (var3 && this.sentryNdkClass != null) {
         if (this.options.getCacheDirPath() == null) {
            this.options.getLogger().log(SentryLevel.ERROR, "No cache dir path is defined in options.");
            this.disableNdkIntegration(this.options);
            return;
         }

         try {
            try {
               this.sentryNdkClass.getMethod("init", SentryAndroidOptions.class).invoke(null, this.options);
               this.options.getLogger().log(SentryLevel.DEBUG, "NdkIntegration installed.");
               IntegrationUtils.addIntegrationToSdkVersion("Ndk");
               return;
            } catch (NoSuchMethodException var6) {
               var10 = var6;
            }
         } catch (Throwable var7) {
            this.disableNdkIntegration(this.options);
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to initialize SentryNdk.", var7);
            return;
         }

         this.disableNdkIntegration(this.options);
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to invoke the SentryNdk.init method.", var10);
      } else {
         this.disableNdkIntegration(this.options);
      }
   }
}
