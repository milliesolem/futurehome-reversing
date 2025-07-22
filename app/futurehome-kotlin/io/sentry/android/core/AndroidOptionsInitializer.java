package io.sentry.android.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import io.sentry.ILogger;
import io.sentry.SendFireAndForgetEnvelopeSender;
import io.sentry.SendFireAndForgetOutboxSender;
import io.sentry.SentryLevel;
import io.sentry.android.core.internal.util.SentryFrameMetricsCollector;
import io.sentry.android.fragment.FragmentLifecycleIntegration;
import io.sentry.android.replay.DefaultReplayBreadcrumbConverter;
import io.sentry.android.replay.ReplayIntegration;
import io.sentry.android.timber.SentryTimberIntegration;
import io.sentry.transport.CurrentDateProvider;
import io.sentry.util.LazyEvaluator;
import io.sentry.util.Objects;
import java.io.File;

final class AndroidOptionsInitializer {
   static final String COMPOSE_CLASS_NAME = "androidx.compose.ui.node.Owner";
   static final long DEFAULT_FLUSH_TIMEOUT_MS = 4000L;
   static final String SENTRY_COMPOSE_GESTURE_INTEGRATION_CLASS_NAME = "io.sentry.compose.gestures.ComposeGestureTargetLocator";
   static final String SENTRY_COMPOSE_VIEW_HIERARCHY_INTEGRATION_CLASS_NAME = "io.sentry.compose.viewhierarchy.ComposeViewHierarchyExporter";

   private AndroidOptionsInitializer() {
   }

   static File getCacheDir(Context var0) {
      return new File(var0.getCacheDir(), "sentry");
   }

   private static String getSentryReleaseVersion(PackageInfo var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0.packageName);
      var2.append("@");
      var2.append(var0.versionName);
      var2.append("+");
      var2.append(var1);
      return var2.toString();
   }

   static void initializeIntegrationsAndProcessors(
      SentryAndroidOptions param0, Context param1, BuildInfoProvider param2, LoadClass param3, ActivityFramesTracker param4
   ) {
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
      // 000: aload 0
      // 001: invokevirtual io/sentry/android/core/SentryAndroidOptions.getCacheDirPath ()Ljava/lang/String;
      // 004: ifnull 01d
      // 007: aload 0
      // 008: invokevirtual io/sentry/android/core/SentryAndroidOptions.getEnvelopeDiskCache ()Lio/sentry/cache/IEnvelopeCache;
      // 00b: instanceof io/sentry/transport/NoOpEnvelopeCache
      // 00e: ifeq 01d
      // 011: aload 0
      // 012: new io/sentry/android/core/cache/AndroidEnvelopeCache
      // 015: dup
      // 016: aload 0
      // 017: invokespecial io/sentry/android/core/cache/AndroidEnvelopeCache.<init> (Lio/sentry/android/core/SentryAndroidOptions;)V
      // 01a: invokevirtual io/sentry/android/core/SentryAndroidOptions.setEnvelopeDiskCache (Lio/sentry/cache/IEnvelopeCache;)V
      // 01d: aload 0
      // 01e: invokevirtual io/sentry/android/core/SentryAndroidOptions.getConnectionStatusProvider ()Lio/sentry/IConnectionStatusProvider;
      // 021: instanceof io/sentry/NoOpConnectionStatusProvider
      // 024: ifeq 038
      // 027: aload 0
      // 028: new io/sentry/android/core/internal/util/AndroidConnectionStatusProvider
      // 02b: dup
      // 02c: aload 1
      // 02d: aload 0
      // 02e: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 031: aload 2
      // 032: invokespecial io/sentry/android/core/internal/util/AndroidConnectionStatusProvider.<init> (Landroid/content/Context;Lio/sentry/ILogger;Lio/sentry/android/core/BuildInfoProvider;)V
      // 035: invokevirtual io/sentry/android/core/SentryAndroidOptions.setConnectionStatusProvider (Lio/sentry/IConnectionStatusProvider;)V
      // 038: aload 0
      // 039: new io/sentry/DeduplicateMultithreadedEventProcessor
      // 03c: dup
      // 03d: aload 0
      // 03e: invokespecial io/sentry/DeduplicateMultithreadedEventProcessor.<init> (Lio/sentry/SentryOptions;)V
      // 041: invokevirtual io/sentry/android/core/SentryAndroidOptions.addEventProcessor (Lio/sentry/EventProcessor;)V
      // 044: aload 0
      // 045: new io/sentry/android/core/DefaultAndroidEventProcessor
      // 048: dup
      // 049: aload 1
      // 04a: aload 2
      // 04b: aload 0
      // 04c: invokespecial io/sentry/android/core/DefaultAndroidEventProcessor.<init> (Landroid/content/Context;Lio/sentry/android/core/BuildInfoProvider;Lio/sentry/android/core/SentryAndroidOptions;)V
      // 04f: invokevirtual io/sentry/android/core/SentryAndroidOptions.addEventProcessor (Lio/sentry/EventProcessor;)V
      // 052: aload 0
      // 053: new io/sentry/android/core/PerformanceAndroidEventProcessor
      // 056: dup
      // 057: aload 0
      // 058: aload 4
      // 05a: invokespecial io/sentry/android/core/PerformanceAndroidEventProcessor.<init> (Lio/sentry/android/core/SentryAndroidOptions;Lio/sentry/android/core/ActivityFramesTracker;)V
      // 05d: invokevirtual io/sentry/android/core/SentryAndroidOptions.addEventProcessor (Lio/sentry/EventProcessor;)V
      // 060: aload 0
      // 061: new io/sentry/android/core/ScreenshotEventProcessor
      // 064: dup
      // 065: aload 0
      // 066: aload 2
      // 067: invokespecial io/sentry/android/core/ScreenshotEventProcessor.<init> (Lio/sentry/android/core/SentryAndroidOptions;Lio/sentry/android/core/BuildInfoProvider;)V
      // 06a: invokevirtual io/sentry/android/core/SentryAndroidOptions.addEventProcessor (Lio/sentry/EventProcessor;)V
      // 06d: aload 0
      // 06e: new io/sentry/android/core/ViewHierarchyEventProcessor
      // 071: dup
      // 072: aload 0
      // 073: invokespecial io/sentry/android/core/ViewHierarchyEventProcessor.<init> (Lio/sentry/android/core/SentryAndroidOptions;)V
      // 076: invokevirtual io/sentry/android/core/SentryAndroidOptions.addEventProcessor (Lio/sentry/EventProcessor;)V
      // 079: aload 0
      // 07a: new io/sentry/android/core/AnrV2EventProcessor
      // 07d: dup
      // 07e: aload 1
      // 07f: aload 0
      // 080: aload 2
      // 081: invokespecial io/sentry/android/core/AnrV2EventProcessor.<init> (Landroid/content/Context;Lio/sentry/android/core/SentryAndroidOptions;Lio/sentry/android/core/BuildInfoProvider;)V
      // 084: invokevirtual io/sentry/android/core/SentryAndroidOptions.addEventProcessor (Lio/sentry/EventProcessor;)V
      // 087: aload 0
      // 088: new io/sentry/android/core/AndroidTransportGate
      // 08b: dup
      // 08c: aload 0
      // 08d: invokespecial io/sentry/android/core/AndroidTransportGate.<init> (Lio/sentry/SentryOptions;)V
      // 090: invokevirtual io/sentry/android/core/SentryAndroidOptions.setTransportGate (Lio/sentry/transport/ITransportGate;)V
      // 093: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 096: astore 4
      // 098: aload 4
      // 09a: monitorenter
      // 09b: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 09e: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartProfiler ()Lio/sentry/ITransactionProfiler;
      // 0a1: astore 7
      // 0a3: aload 7
      // 0a5: ifnull 0b8
      // 0a8: aload 0
      // 0a9: aload 7
      // 0ab: invokevirtual io/sentry/android/core/SentryAndroidOptions.setTransactionProfiler (Lio/sentry/ITransactionProfiler;)V
      // 0ae: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 0b1: aconst_null
      // 0b2: invokevirtual io/sentry/android/core/performance/AppStartMetrics.setAppStartProfiler (Lio/sentry/ITransactionProfiler;)V
      // 0b5: goto 0d7
      // 0b8: new io/sentry/android/core/AndroidTransactionProfiler
      // 0bb: astore 7
      // 0bd: aload 7
      // 0bf: aload 1
      // 0c0: aload 0
      // 0c1: aload 2
      // 0c2: aload 0
      // 0c3: invokevirtual io/sentry/android/core/SentryAndroidOptions.getFrameMetricsCollector ()Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 0c6: ldc "options.getFrameMetricsCollector is required"
      // 0c8: invokestatic io/sentry/util/Objects.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 0cb: checkcast io/sentry/android/core/internal/util/SentryFrameMetricsCollector
      // 0ce: invokespecial io/sentry/android/core/AndroidTransactionProfiler.<init> (Landroid/content/Context;Lio/sentry/android/core/SentryAndroidOptions;Lio/sentry/android/core/BuildInfoProvider;Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;)V
      // 0d1: aload 0
      // 0d2: aload 7
      // 0d4: invokevirtual io/sentry/android/core/SentryAndroidOptions.setTransactionProfiler (Lio/sentry/ITransactionProfiler;)V
      // 0d7: aload 4
      // 0d9: monitorexit
      // 0da: aload 0
      // 0db: new io/sentry/android/core/internal/modules/AssetsModulesLoader
      // 0de: dup
      // 0df: aload 1
      // 0e0: aload 0
      // 0e1: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 0e4: invokespecial io/sentry/android/core/internal/modules/AssetsModulesLoader.<init> (Landroid/content/Context;Lio/sentry/ILogger;)V
      // 0e7: invokevirtual io/sentry/android/core/SentryAndroidOptions.setModulesLoader (Lio/sentry/internal/modules/IModulesLoader;)V
      // 0ea: aload 0
      // 0eb: new io/sentry/android/core/internal/debugmeta/AssetsDebugMetaLoader
      // 0ee: dup
      // 0ef: aload 1
      // 0f0: aload 0
      // 0f1: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 0f4: invokespecial io/sentry/android/core/internal/debugmeta/AssetsDebugMetaLoader.<init> (Landroid/content/Context;Lio/sentry/ILogger;)V
      // 0f7: invokevirtual io/sentry/android/core/SentryAndroidOptions.setDebugMetaLoader (Lio/sentry/internal/debugmeta/IDebugMetaLoader;)V
      // 0fa: aload 3
      // 0fb: ldc "androidx.core.view.ScrollingView"
      // 0fd: aload 0
      // 0fe: invokevirtual io/sentry/android/core/LoadClass.isClassAvailable (Ljava/lang/String;Lio/sentry/SentryOptions;)Z
      // 101: istore 5
      // 103: aload 3
      // 104: ldc "androidx.compose.ui.node.Owner"
      // 106: aload 0
      // 107: invokevirtual io/sentry/android/core/LoadClass.isClassAvailable (Ljava/lang/String;Lio/sentry/SentryOptions;)Z
      // 10a: istore 6
      // 10c: aload 0
      // 10d: invokevirtual io/sentry/android/core/SentryAndroidOptions.getGestureTargetLocators ()Ljava/util/List;
      // 110: invokeinterface java/util/List.isEmpty ()Z 1
      // 115: ifeq 157
      // 118: new java/util/ArrayList
      // 11b: dup
      // 11c: bipush 2
      // 11d: invokespecial java/util/ArrayList.<init> (I)V
      // 120: astore 1
      // 121: aload 1
      // 122: new io/sentry/android/core/internal/gestures/AndroidViewGestureTargetLocator
      // 125: dup
      // 126: iload 5
      // 128: invokespecial io/sentry/android/core/internal/gestures/AndroidViewGestureTargetLocator.<init> (Z)V
      // 12b: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 130: pop
      // 131: iload 6
      // 133: ifeq 152
      // 136: aload 3
      // 137: ldc "io.sentry.compose.gestures.ComposeGestureTargetLocator"
      // 139: aload 0
      // 13a: invokevirtual io/sentry/android/core/LoadClass.isClassAvailable (Ljava/lang/String;Lio/sentry/SentryOptions;)Z
      // 13d: ifeq 152
      // 140: aload 1
      // 141: new io/sentry/compose/gestures/ComposeGestureTargetLocator
      // 144: dup
      // 145: aload 0
      // 146: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 149: invokespecial io/sentry/compose/gestures/ComposeGestureTargetLocator.<init> (Lio/sentry/ILogger;)V
      // 14c: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 151: pop
      // 152: aload 0
      // 153: aload 1
      // 154: invokevirtual io/sentry/android/core/SentryAndroidOptions.setGestureTargetLocators (Ljava/util/List;)V
      // 157: aload 0
      // 158: invokevirtual io/sentry/android/core/SentryAndroidOptions.getViewHierarchyExporters ()Ljava/util/List;
      // 15b: invokeinterface java/util/List.isEmpty ()Z 1
      // 160: ifeq 192
      // 163: iload 6
      // 165: ifeq 192
      // 168: aload 3
      // 169: ldc "io.sentry.compose.viewhierarchy.ComposeViewHierarchyExporter"
      // 16b: aload 0
      // 16c: invokevirtual io/sentry/android/core/LoadClass.isClassAvailable (Ljava/lang/String;Lio/sentry/SentryOptions;)Z
      // 16f: ifeq 192
      // 172: new java/util/ArrayList
      // 175: dup
      // 176: bipush 1
      // 177: invokespecial java/util/ArrayList.<init> (I)V
      // 17a: astore 1
      // 17b: aload 1
      // 17c: new io/sentry/compose/viewhierarchy/ComposeViewHierarchyExporter
      // 17f: dup
      // 180: aload 0
      // 181: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 184: invokespecial io/sentry/compose/viewhierarchy/ComposeViewHierarchyExporter.<init> (Lio/sentry/ILogger;)V
      // 187: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 18c: pop
      // 18d: aload 0
      // 18e: aload 1
      // 18f: invokevirtual io/sentry/android/core/SentryAndroidOptions.setViewHierarchyExporters (Ljava/util/List;)V
      // 192: aload 0
      // 193: invokestatic io/sentry/android/core/internal/util/AndroidMainThreadChecker.getInstance ()Lio/sentry/android/core/internal/util/AndroidMainThreadChecker;
      // 196: invokevirtual io/sentry/android/core/SentryAndroidOptions.setMainThreadChecker (Lio/sentry/util/thread/IMainThreadChecker;)V
      // 199: aload 0
      // 19a: invokevirtual io/sentry/android/core/SentryAndroidOptions.getPerformanceCollectors ()Ljava/util/List;
      // 19d: invokeinterface java/util/List.isEmpty ()Z 1
      // 1a2: ifeq 1df
      // 1a5: aload 0
      // 1a6: new io/sentry/android/core/AndroidMemoryCollector
      // 1a9: dup
      // 1aa: invokespecial io/sentry/android/core/AndroidMemoryCollector.<init> ()V
      // 1ad: invokevirtual io/sentry/android/core/SentryAndroidOptions.addPerformanceCollector (Lio/sentry/IPerformanceCollector;)V
      // 1b0: aload 0
      // 1b1: new io/sentry/android/core/AndroidCpuCollector
      // 1b4: dup
      // 1b5: aload 0
      // 1b6: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 1b9: aload 2
      // 1ba: invokespecial io/sentry/android/core/AndroidCpuCollector.<init> (Lio/sentry/ILogger;Lio/sentry/android/core/BuildInfoProvider;)V
      // 1bd: invokevirtual io/sentry/android/core/SentryAndroidOptions.addPerformanceCollector (Lio/sentry/IPerformanceCollector;)V
      // 1c0: aload 0
      // 1c1: invokevirtual io/sentry/android/core/SentryAndroidOptions.isEnablePerformanceV2 ()Z
      // 1c4: ifeq 1df
      // 1c7: aload 0
      // 1c8: new io/sentry/android/core/SpanFrameMetricsCollector
      // 1cb: dup
      // 1cc: aload 0
      // 1cd: aload 0
      // 1ce: invokevirtual io/sentry/android/core/SentryAndroidOptions.getFrameMetricsCollector ()Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 1d1: ldc "options.getFrameMetricsCollector is required"
      // 1d3: invokestatic io/sentry/util/Objects.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 1d6: checkcast io/sentry/android/core/internal/util/SentryFrameMetricsCollector
      // 1d9: invokespecial io/sentry/android/core/SpanFrameMetricsCollector.<init> (Lio/sentry/android/core/SentryAndroidOptions;Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;)V
      // 1dc: invokevirtual io/sentry/android/core/SentryAndroidOptions.addPerformanceCollector (Lio/sentry/IPerformanceCollector;)V
      // 1df: aload 0
      // 1e0: new io/sentry/DefaultTransactionPerformanceCollector
      // 1e3: dup
      // 1e4: aload 0
      // 1e5: invokespecial io/sentry/DefaultTransactionPerformanceCollector.<init> (Lio/sentry/SentryOptions;)V
      // 1e8: invokevirtual io/sentry/android/core/SentryAndroidOptions.setTransactionPerformanceCollector (Lio/sentry/TransactionPerformanceCollector;)V
      // 1eb: aload 0
      // 1ec: invokevirtual io/sentry/android/core/SentryAndroidOptions.getCacheDirPath ()Ljava/lang/String;
      // 1ef: ifnull 211
      // 1f2: aload 0
      // 1f3: invokevirtual io/sentry/android/core/SentryAndroidOptions.isEnableScopePersistence ()Z
      // 1f6: ifeq 205
      // 1f9: aload 0
      // 1fa: new io/sentry/cache/PersistingScopeObserver
      // 1fd: dup
      // 1fe: aload 0
      // 1ff: invokespecial io/sentry/cache/PersistingScopeObserver.<init> (Lio/sentry/SentryOptions;)V
      // 202: invokevirtual io/sentry/android/core/SentryAndroidOptions.addScopeObserver (Lio/sentry/IScopeObserver;)V
      // 205: aload 0
      // 206: new io/sentry/cache/PersistingOptionsObserver
      // 209: dup
      // 20a: aload 0
      // 20b: invokespecial io/sentry/cache/PersistingOptionsObserver.<init> (Lio/sentry/SentryOptions;)V
      // 20e: invokevirtual io/sentry/android/core/SentryAndroidOptions.addOptionsObserver (Lio/sentry/IOptionsObserver;)V
      // 211: return
      // 212: astore 0
      // 213: aload 4
      // 215: monitorexit
      // 216: aload 0
      // 217: athrow
   }

   static void initializeIntegrationsAndProcessors(SentryAndroidOptions var0, Context var1, LoadClass var2, ActivityFramesTracker var3) {
      initializeIntegrationsAndProcessors(var0, var1, new BuildInfoProvider(new AndroidLogger()), var2, var3);
   }

   static void installDefaultIntegrations(
      Context var0, SentryAndroidOptions var1, BuildInfoProvider var2, LoadClass var3, ActivityFramesTracker var4, boolean var5, boolean var6, boolean var7
   ) {
      LazyEvaluator var8 = new LazyEvaluator(new AndroidOptionsInitializer$$ExternalSyntheticLambda0(var1));
      var1.addIntegration(
         new SendCachedEnvelopeIntegration(new SendFireAndForgetEnvelopeSender(new AndroidOptionsInitializer$$ExternalSyntheticLambda1(var1)), var8)
      );
      var1.addIntegration(new NdkIntegration(var3.loadClass("io.sentry.android.ndk.SentryNdk", var1.getLogger())));
      var1.addIntegration(EnvelopeFileObserverIntegration.getOutboxFileObserver());
      var1.addIntegration(
         new SendCachedEnvelopeIntegration(new SendFireAndForgetOutboxSender(new AndroidOptionsInitializer$$ExternalSyntheticLambda2(var1)), var8)
      );
      var1.addIntegration(new AppLifecycleIntegration());
      var1.addIntegration(AnrIntegrationFactory.create(var0, var2));
      if (var0 instanceof Application) {
         Application var10 = (Application)var0;
         var1.addIntegration(new ActivityLifecycleIntegration(var10, var2, var4));
         var1.addIntegration(new ActivityBreadcrumbsIntegration(var10));
         var1.addIntegration(new CurrentActivityIntegration(var10));
         var1.addIntegration(new UserInteractionIntegration(var10, var3));
         if (var5) {
            var1.addIntegration(new FragmentLifecycleIntegration(var10, true, true));
         }
      } else {
         var1.getLogger()
            .log(SentryLevel.WARNING, "ActivityLifecycle, FragmentLifecycle and UserInteraction Integrations need an Application class to be installed.");
      }

      if (var6) {
         var1.addIntegration(new SentryTimberIntegration());
      }

      var1.addIntegration(new AppComponentsBreadcrumbsIntegration(var0));
      var1.addIntegration(new SystemEventsBreadcrumbsIntegration(var0));
      var1.addIntegration(new NetworkBreadcrumbsIntegration(var0, var2, var1.getLogger()));
      if (var7) {
         ReplayIntegration var9 = new ReplayIntegration(var0, CurrentDateProvider.getInstance());
         var9.setBreadcrumbConverter(new DefaultReplayBreadcrumbConverter());
         var1.addIntegration(var9);
         var1.setReplayController(var9);
      }
   }

   static void loadDefaultAndMetadataOptions(SentryAndroidOptions var0, Context var1) {
      AndroidLogger var2 = new AndroidLogger();
      loadDefaultAndMetadataOptions(var0, var1, var2, new BuildInfoProvider(var2));
   }

   static void loadDefaultAndMetadataOptions(SentryAndroidOptions var0, Context var1, ILogger var2, BuildInfoProvider var3) {
      Objects.requireNonNull(var1, "The context is required.");
      var1 = ContextUtils.getApplicationContext(var1);
      Objects.requireNonNull(var0, "The options object is required.");
      Objects.requireNonNull(var2, "The ILogger object is required.");
      var0.setLogger(var2);
      var0.setDateProvider(new SentryAndroidDateProvider());
      var0.setFlushTimeoutMillis(4000L);
      var0.setFrameMetricsCollector(new SentryFrameMetricsCollector(var1, var2, var3));
      ManifestMetadataReader.applyMetadata(var1, var0, var3);
      var0.setCacheDirPath(getCacheDir(var1).getAbsolutePath());
      readDefaultOptionValues(var0, var1, var3);
   }

   private static void readDefaultOptionValues(SentryAndroidOptions var0, Context var1, BuildInfoProvider var2) {
      PackageInfo var3 = ContextUtils.getPackageInfo(var1, var2);
      if (var3 != null) {
         if (var0.getRelease() == null) {
            var0.setRelease(getSentryReleaseVersion(var3, ContextUtils.getVersionCode(var3, var2)));
         }

         String var5 = var3.packageName;
         if (var5 != null && !var5.startsWith("android.")) {
            var0.addInAppInclude(var5);
         }
      }

      if (var0.getDistinctId() == null) {
         try {
            var0.setDistinctId(Installation.id(var1));
         } catch (RuntimeException var4) {
            var0.getLogger().log(SentryLevel.ERROR, "Could not generate distinct Id.", var4);
         }
      }
   }
}
