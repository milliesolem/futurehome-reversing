package io.sentry.android.core;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.SystemClock;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.sentry.ILogger;
import io.sentry.android.core.performance.AppStartMetrics;

public final class SentryPerformanceProvider extends EmptySecureContentProvider {
   private static final long sdkInitMillis = SystemClock.uptimeMillis();
   private ActivityLifecycleCallbacks activityCallback;
   private Application app;
   private final BuildInfoProvider buildInfoProvider;
   private final ILogger logger;

   public SentryPerformanceProvider() {
      AndroidLogger var1 = new AndroidLogger();
      this.logger = var1;
      this.buildInfoProvider = new BuildInfoProvider(var1);
   }

   SentryPerformanceProvider(ILogger var1, BuildInfoProvider var2) {
      this.logger = var1;
      this.buildInfoProvider = var2;
   }

   private void launchAppStartProfiler(AppStartMetrics param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual io/sentry/android/core/SentryPerformanceProvider.getContext ()Landroid/content/Context;
      // 004: astore 5
      // 006: aload 5
      // 008: ifnonnull 01e
      // 00b: aload 0
      // 00c: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 00f: getstatic io/sentry/SentryLevel.FATAL Lio/sentry/SentryLevel;
      // 012: ldc "App. Context from ContentProvider is null"
      // 014: bipush 0
      // 015: anewarray 59
      // 018: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 01d: return
      // 01e: aload 0
      // 01f: getfield io/sentry/android/core/SentryPerformanceProvider.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 022: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 025: bipush 21
      // 027: if_icmpge 02b
      // 02a: return
      // 02b: new java/io/File
      // 02e: dup
      // 02f: aload 5
      // 031: invokestatic io/sentry/android/core/AndroidOptionsInitializer.getCacheDir (Landroid/content/Context;)Ljava/io/File;
      // 034: ldc "app_start_profiling_config"
      // 036: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 039: astore 6
      // 03b: aload 6
      // 03d: invokevirtual java/io/File.exists ()Z
      // 040: ifeq 1d7
      // 043: aload 6
      // 045: invokevirtual java/io/File.canRead ()Z
      // 048: ifne 04e
      // 04b: goto 1d7
      // 04e: new java/io/BufferedReader
      // 051: astore 4
      // 053: new java/io/InputStreamReader
      // 056: astore 7
      // 058: new java/io/FileInputStream
      // 05b: astore 8
      // 05d: aload 8
      // 05f: aload 6
      // 061: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 064: aload 7
      // 066: aload 8
      // 068: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;)V
      // 06b: aload 4
      // 06d: aload 7
      // 06f: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 072: new io/sentry/JsonSerializer
      // 075: astore 6
      // 077: aload 6
      // 079: invokestatic io/sentry/SentryOptions.empty ()Lio/sentry/SentryOptions;
      // 07c: invokespecial io/sentry/JsonSerializer.<init> (Lio/sentry/SentryOptions;)V
      // 07f: aload 6
      // 081: aload 4
      // 083: ldc io/sentry/SentryAppStartProfilingOptions
      // 085: invokevirtual io/sentry/JsonSerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;
      // 088: checkcast io/sentry/SentryAppStartProfilingOptions
      // 08b: astore 10
      // 08d: aload 10
      // 08f: ifnonnull 0aa
      // 092: aload 0
      // 093: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 096: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 099: ldc "Unable to deserialize the SentryAppStartProfilingOptions. App start profiling will not start."
      // 09b: bipush 0
      // 09c: anewarray 59
      // 09f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0a4: aload 4
      // 0a6: invokevirtual java/io/Reader.close ()V
      // 0a9: return
      // 0aa: aload 10
      // 0ac: invokevirtual io/sentry/SentryAppStartProfilingOptions.isProfilingEnabled ()Z
      // 0af: ifne 0ca
      // 0b2: aload 0
      // 0b3: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 0b6: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 0b9: ldc "Profiling is not enabled. App start profiling will not start."
      // 0bb: bipush 0
      // 0bc: anewarray 59
      // 0bf: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0c4: aload 4
      // 0c6: invokevirtual java/io/Reader.close ()V
      // 0c9: return
      // 0ca: new io/sentry/TracesSamplingDecision
      // 0cd: astore 6
      // 0cf: aload 6
      // 0d1: aload 10
      // 0d3: invokevirtual io/sentry/SentryAppStartProfilingOptions.isTraceSampled ()Z
      // 0d6: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0d9: aload 10
      // 0db: invokevirtual io/sentry/SentryAppStartProfilingOptions.getTraceSampleRate ()Ljava/lang/Double;
      // 0de: aload 10
      // 0e0: invokevirtual io/sentry/SentryAppStartProfilingOptions.isProfileSampled ()Z
      // 0e3: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0e6: aload 10
      // 0e8: invokevirtual io/sentry/SentryAppStartProfilingOptions.getProfileSampleRate ()Ljava/lang/Double;
      // 0eb: invokespecial io/sentry/TracesSamplingDecision.<init> (Ljava/lang/Boolean;Ljava/lang/Double;Ljava/lang/Boolean;Ljava/lang/Double;)V
      // 0ee: aload 1
      // 0ef: aload 6
      // 0f1: invokevirtual io/sentry/android/core/performance/AppStartMetrics.setAppStartSamplingDecision (Lio/sentry/TracesSamplingDecision;)V
      // 0f4: aload 6
      // 0f6: invokevirtual io/sentry/TracesSamplingDecision.getProfileSampled ()Ljava/lang/Boolean;
      // 0f9: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 0fc: ifeq 189
      // 0ff: aload 6
      // 101: invokevirtual io/sentry/TracesSamplingDecision.getSampled ()Ljava/lang/Boolean;
      // 104: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 107: ifne 10d
      // 10a: goto 189
      // 10d: aload 0
      // 10e: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 111: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 114: ldc "App start profiling started."
      // 116: bipush 0
      // 117: anewarray 59
      // 11a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 11f: new io/sentry/android/core/AndroidTransactionProfiler
      // 122: astore 8
      // 124: aload 0
      // 125: getfield io/sentry/android/core/SentryPerformanceProvider.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 128: astore 9
      // 12a: new io/sentry/android/core/internal/util/SentryFrameMetricsCollector
      // 12d: astore 7
      // 12f: aload 7
      // 131: aload 5
      // 133: aload 0
      // 134: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 137: aload 0
      // 138: getfield io/sentry/android/core/SentryPerformanceProvider.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 13b: invokespecial io/sentry/android/core/internal/util/SentryFrameMetricsCollector.<init> (Landroid/content/Context;Lio/sentry/ILogger;Lio/sentry/android/core/BuildInfoProvider;)V
      // 13e: aload 0
      // 13f: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 142: astore 11
      // 144: aload 10
      // 146: invokevirtual io/sentry/SentryAppStartProfilingOptions.getProfilingTracesDirPath ()Ljava/lang/String;
      // 149: astore 6
      // 14b: aload 10
      // 14d: invokevirtual io/sentry/SentryAppStartProfilingOptions.isProfilingEnabled ()Z
      // 150: istore 3
      // 151: aload 10
      // 153: invokevirtual io/sentry/SentryAppStartProfilingOptions.getProfilingTracesHz ()I
      // 156: istore 2
      // 157: new io/sentry/SentryExecutorService
      // 15a: astore 10
      // 15c: aload 10
      // 15e: invokespecial io/sentry/SentryExecutorService.<init> ()V
      // 161: aload 8
      // 163: aload 5
      // 165: aload 9
      // 167: aload 7
      // 169: aload 11
      // 16b: aload 6
      // 16d: iload 3
      // 16e: iload 2
      // 16f: aload 10
      // 171: invokespecial io/sentry/android/core/AndroidTransactionProfiler.<init> (Landroid/content/Context;Lio/sentry/android/core/BuildInfoProvider;Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;Lio/sentry/ILogger;Ljava/lang/String;ZILio/sentry/ISentryExecutorService;)V
      // 174: aload 1
      // 175: aload 8
      // 177: invokevirtual io/sentry/android/core/performance/AppStartMetrics.setAppStartProfiler (Lio/sentry/ITransactionProfiler;)V
      // 17a: aload 8
      // 17c: invokeinterface io/sentry/ITransactionProfiler.start ()V 1
      // 181: aload 4
      // 183: invokevirtual java/io/Reader.close ()V
      // 186: goto 1d7
      // 189: aload 0
      // 18a: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 18d: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 190: ldc "App start profiling was not sampled. It will not start."
      // 192: bipush 0
      // 193: anewarray 59
      // 196: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 19b: aload 4
      // 19d: invokevirtual java/io/Reader.close ()V
      // 1a0: return
      // 1a1: astore 1
      // 1a2: aload 4
      // 1a4: invokevirtual java/io/Reader.close ()V
      // 1a7: goto 1b2
      // 1aa: astore 4
      // 1ac: aload 1
      // 1ad: aload 4
      // 1af: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 1b2: aload 1
      // 1b3: athrow
      // 1b4: astore 1
      // 1b5: aload 0
      // 1b6: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 1b9: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 1bc: ldc "Error reading app start profiling config file. "
      // 1be: aload 1
      // 1bf: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 1c4: goto 1d7
      // 1c7: astore 1
      // 1c8: aload 0
      // 1c9: getfield io/sentry/android/core/SentryPerformanceProvider.logger Lio/sentry/ILogger;
      // 1cc: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 1cf: ldc "App start profiling config file not found. "
      // 1d1: aload 1
      // 1d2: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 1d7: return
   }

   private void onAppLaunched(Context var1, AppStartMetrics var2) {
      var2.getSdkInitTimeSpan().setStartedAt(sdkInitMillis);
      if (this.buildInfoProvider.getSdkInfoVersion() >= 24) {
         var2.getAppStartTimeSpan().setStartedAt(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m());
      }

      if (var1 instanceof Application) {
         this.app = (Application)var1;
      }

      Application var3 = this.app;
      if (var3 != null) {
         var2.registerLifecycleCallbacks(var3);
      }
   }

   public void attachInfo(Context var1, ProviderInfo var2) {
      if (!SentryPerformanceProvider.class.getName().equals(var2.authority)) {
         super.attachInfo(var1, var2);
      } else {
         throw new IllegalStateException("An applicationId is required to fulfill the manifest placeholder.");
      }
   }

   public String getType(Uri var1) {
      return null;
   }

   public boolean onCreate() {
      AppStartMetrics var1 = AppStartMetrics.getInstance();
      this.onAppLaunched(this.getContext(), var1);
      this.launchAppStartProfiler(var1);
      return true;
   }

   public void shutdown() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 03: astore 1
      // 04: aload 1
      // 05: monitorenter
      // 06: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 09: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartProfiler ()Lio/sentry/ITransactionProfiler;
      // 0c: astore 2
      // 0d: aload 2
      // 0e: ifnull 17
      // 11: aload 2
      // 12: invokeinterface io/sentry/ITransactionProfiler.close ()V 1
      // 17: aload 1
      // 18: monitorexit
      // 19: return
      // 1a: astore 2
      // 1b: aload 1
      // 1c: monitorexit
      // 1d: aload 2
      // 1e: athrow
   }
}
