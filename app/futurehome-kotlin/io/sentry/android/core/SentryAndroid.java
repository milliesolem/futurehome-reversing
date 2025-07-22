package io.sentry.android.core;

import android.content.Context;
import android.os.SystemClock;
import io.sentry.ILogger;
import io.sentry.Integration;
import io.sentry.Sentry;
import io.sentry.SentryOptions;
import io.sentry.android.fragment.FragmentLifecycleIntegration;
import io.sentry.android.timber.SentryTimberIntegration;
import java.util.ArrayList;

public final class SentryAndroid {
   private static final String FRAGMENT_CLASS_NAME = "androidx.fragment.app.FragmentManager$FragmentLifecycleCallbacks";
   static final String SENTRY_FRAGMENT_INTEGRATION_CLASS_NAME = "io.sentry.android.fragment.FragmentLifecycleIntegration";
   static final String SENTRY_REPLAY_INTEGRATION_CLASS_NAME = "io.sentry.android.replay.ReplayIntegration";
   static final String SENTRY_TIMBER_INTEGRATION_CLASS_NAME = "io.sentry.android.timber.SentryTimberIntegration";
   private static final String TIMBER_CLASS_NAME = "timber.log.Timber";
   private static final long sdkInitMillis = SystemClock.uptimeMillis();

   private SentryAndroid() {
   }

   private static void deduplicateIntegrations(SentryOptions var0, boolean var1, boolean var2) {
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();

      for (Integration var8 : var0.getIntegrations()) {
         if (var1 && var8 instanceof FragmentLifecycleIntegration) {
            var6.add(var8);
         }

         if (var2 && var8 instanceof SentryTimberIntegration) {
            var5.add(var8);
         }
      }

      int var3 = var6.size();
      byte var4 = 0;
      if (var3 > 1) {
         for (int var9 = 0; var9 < var6.size() - 1; var9++) {
            Integration var12 = (Integration)var6.get(var9);
            var0.getIntegrations().remove(var12);
         }
      }

      if (var5.size() > 1) {
         for (int var10 = var4; var10 < var5.size() - 1; var10++) {
            Integration var11 = (Integration)var5.get(var10);
            var0.getIntegrations().remove(var11);
         }
      }
   }

   public static void init(Context var0) {
      init(var0, new AndroidLogger());
   }

   public static void init(Context var0, ILogger var1) {
      init(var0, var1, new SentryAndroid$$ExternalSyntheticLambda3());
   }

   public static void init(Context param0, ILogger param1, Sentry.OptionsConfiguration<SentryAndroidOptions> param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc io/sentry/android/core/SentryAndroid
      // 02: monitorenter
      // 03: ldc io/sentry/android/core/SentryAndroidOptions
      // 05: invokestatic io/sentry/OptionsContainer.create (Ljava/lang/Class;)Lio/sentry/OptionsContainer;
      // 08: astore 4
      // 0a: new io/sentry/android/core/SentryAndroid$$ExternalSyntheticLambda1
      // 0d: astore 3
      // 0e: aload 3
      // 0f: aload 1
      // 10: aload 0
      // 11: aload 2
      // 12: invokespecial io/sentry/android/core/SentryAndroid$$ExternalSyntheticLambda1.<init> (Lio/sentry/ILogger;Landroid/content/Context;Lio/sentry/Sentry$OptionsConfiguration;)V
      // 15: aload 4
      // 17: aload 3
      // 18: bipush 1
      // 19: invokestatic io/sentry/Sentry.init (Lio/sentry/OptionsContainer;Lio/sentry/Sentry$OptionsConfiguration;Z)V
      // 1c: invokestatic io/sentry/Sentry.getCurrentHub ()Lio/sentry/IHub;
      // 1f: astore 3
      // 20: invokestatic io/sentry/android/core/ContextUtils.isForegroundImportance ()Z
      // 23: ifeq 66
      // 26: aload 3
      // 27: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 2c: invokevirtual io/sentry/SentryOptions.isEnableAutoSessionTracking ()Z
      // 2f: ifeq 58
      // 32: new java/util/concurrent/atomic/AtomicBoolean
      // 35: astore 2
      // 36: aload 2
      // 37: bipush 0
      // 38: invokespecial java/util/concurrent/atomic/AtomicBoolean.<init> (Z)V
      // 3b: new io/sentry/android/core/SentryAndroid$$ExternalSyntheticLambda2
      // 3e: astore 0
      // 3f: aload 0
      // 40: aload 2
      // 41: invokespecial io/sentry/android/core/SentryAndroid$$ExternalSyntheticLambda2.<init> (Ljava/util/concurrent/atomic/AtomicBoolean;)V
      // 44: aload 3
      // 45: aload 0
      // 46: invokeinterface io/sentry/IHub.configureScope (Lio/sentry/ScopeCallback;)V 2
      // 4b: aload 2
      // 4c: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 4f: ifne 58
      // 52: aload 3
      // 53: invokeinterface io/sentry/IHub.startSession ()V 1
      // 58: aload 3
      // 59: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 5e: invokevirtual io/sentry/SentryOptions.getReplayController ()Lio/sentry/ReplayController;
      // 61: invokeinterface io/sentry/ReplayController.start ()V 1
      // 66: ldc io/sentry/android/core/SentryAndroid
      // 68: monitorexit
      // 69: return
      // 6a: astore 0
      // 6b: goto d6
      // 6e: astore 0
      // 6f: aload 1
      // 70: getstatic io/sentry/SentryLevel.FATAL Lio/sentry/SentryLevel;
      // 73: ldc "Fatal error during SentryAndroid.init(...)"
      // 75: aload 0
      // 76: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 7b: new java/lang/RuntimeException
      // 7e: astore 1
      // 7f: aload 1
      // 80: ldc "Failed to initialize Sentry's SDK"
      // 82: aload 0
      // 83: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 86: aload 1
      // 87: athrow
      // 88: astore 0
      // 89: aload 1
      // 8a: getstatic io/sentry/SentryLevel.FATAL Lio/sentry/SentryLevel;
      // 8d: ldc "Fatal error during SentryAndroid.init(...)"
      // 8f: aload 0
      // 90: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 95: new java/lang/RuntimeException
      // 98: astore 1
      // 99: aload 1
      // 9a: ldc "Failed to initialize Sentry's SDK"
      // 9c: aload 0
      // 9d: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // a0: aload 1
      // a1: athrow
      // a2: astore 0
      // a3: aload 1
      // a4: getstatic io/sentry/SentryLevel.FATAL Lio/sentry/SentryLevel;
      // a7: ldc "Fatal error during SentryAndroid.init(...)"
      // a9: aload 0
      // aa: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // af: new java/lang/RuntimeException
      // b2: astore 1
      // b3: aload 1
      // b4: ldc "Failed to initialize Sentry's SDK"
      // b6: aload 0
      // b7: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // ba: aload 1
      // bb: athrow
      // bc: astore 0
      // bd: aload 1
      // be: getstatic io/sentry/SentryLevel.FATAL Lio/sentry/SentryLevel;
      // c1: ldc "Fatal error during SentryAndroid.init(...)"
      // c3: aload 0
      // c4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // c9: new java/lang/RuntimeException
      // cc: astore 1
      // cd: aload 1
      // ce: ldc "Failed to initialize Sentry's SDK"
      // d0: aload 0
      // d1: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // d4: aload 1
      // d5: athrow
      // d6: ldc io/sentry/android/core/SentryAndroid
      // d8: monitorexit
      // d9: aload 0
      // da: athrow
   }

   public static void init(Context var0, Sentry.OptionsConfiguration<SentryAndroidOptions> var1) {
      init(var0, new AndroidLogger(), var1);
   }
}
