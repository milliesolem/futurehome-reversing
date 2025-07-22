package io.sentry;

import io.sentry.backpressure.BackpressureMonitor;
import io.sentry.cache.EnvelopeCache;
import io.sentry.config.PropertiesProviderFactory;
import io.sentry.internal.debugmeta.NoOpDebugMetaLoader;
import io.sentry.internal.debugmeta.ResourcesDebugMetaLoader;
import io.sentry.internal.modules.CompositeModulesLoader;
import io.sentry.internal.modules.IModulesLoader;
import io.sentry.internal.modules.ManifestModulesLoader;
import io.sentry.internal.modules.NoOpModulesLoader;
import io.sentry.internal.modules.ResourcesModulesLoader;
import io.sentry.metrics.MetricsApi;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import io.sentry.transport.NoOpEnvelopeCache;
import io.sentry.util.DebugMetaPropertiesApplier;
import io.sentry.util.Platform;
import io.sentry.util.thread.MainThreadChecker;
import io.sentry.util.thread.NoOpMainThreadChecker;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public final class Sentry {
   public static final String APP_START_PROFILING_CONFIG_FILE_NAME = "app_start_profiling_config";
   private static final boolean GLOBAL_HUB_DEFAULT_MODE = false;
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private static final long classCreationTimestamp = System.currentTimeMillis();
   private static final ThreadLocal<IHub> currentHub = new ThreadLocal<>();
   private static volatile boolean globalHubMode = false;
   private static volatile IHub mainHub = NoOpHub.getInstance();

   private Sentry() {
   }

   public static void addBreadcrumb(Breadcrumb var0) {
      getCurrentHub().addBreadcrumb(var0);
   }

   public static void addBreadcrumb(Breadcrumb var0, Hint var1) {
      getCurrentHub().addBreadcrumb(var0, var1);
   }

   public static void addBreadcrumb(String var0) {
      getCurrentHub().addBreadcrumb(var0);
   }

   public static void addBreadcrumb(String var0, String var1) {
      getCurrentHub().addBreadcrumb(var0, var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static <T extends SentryOptions> void applyOptionsConfiguration(Sentry.OptionsConfiguration<T> var0, T var1) {
      try {
         var0.configure(var1);
      } catch (Throwable var3) {
         var1.getLogger().log(SentryLevel.ERROR, "Error in the 'OptionsConfiguration.configure' callback.", var3);
         return;
      }
   }

   public static void bindClient(ISentryClient var0) {
      getCurrentHub().bindClient(var0);
   }

   public static SentryId captureCheckIn(CheckIn var0) {
      return getCurrentHub().captureCheckIn(var0);
   }

   public static SentryId captureEvent(SentryEvent var0) {
      return getCurrentHub().captureEvent(var0);
   }

   public static SentryId captureEvent(SentryEvent var0, Hint var1) {
      return getCurrentHub().captureEvent(var0, var1);
   }

   public static SentryId captureEvent(SentryEvent var0, Hint var1, ScopeCallback var2) {
      return getCurrentHub().captureEvent(var0, var1, var2);
   }

   public static SentryId captureEvent(SentryEvent var0, ScopeCallback var1) {
      return getCurrentHub().captureEvent(var0, var1);
   }

   public static SentryId captureException(Throwable var0) {
      return getCurrentHub().captureException(var0);
   }

   public static SentryId captureException(Throwable var0, Hint var1) {
      return getCurrentHub().captureException(var0, var1);
   }

   public static SentryId captureException(Throwable var0, Hint var1, ScopeCallback var2) {
      return getCurrentHub().captureException(var0, var1, var2);
   }

   public static SentryId captureException(Throwable var0, ScopeCallback var1) {
      return getCurrentHub().captureException(var0, var1);
   }

   public static SentryId captureMessage(String var0) {
      return getCurrentHub().captureMessage(var0);
   }

   public static SentryId captureMessage(String var0, ScopeCallback var1) {
      return getCurrentHub().captureMessage(var0, var1);
   }

   public static SentryId captureMessage(String var0, SentryLevel var1) {
      return getCurrentHub().captureMessage(var0, var1);
   }

   public static SentryId captureMessage(String var0, SentryLevel var1, ScopeCallback var2) {
      return getCurrentHub().captureMessage(var0, var1, var2);
   }

   public static void captureUserFeedback(UserFeedback var0) {
      getCurrentHub().captureUserFeedback(var0);
   }

   public static void clearBreadcrumbs() {
      getCurrentHub().clearBreadcrumbs();
   }

   public static IHub cloneMainHub() {
      return globalHubMode ? mainHub : mainHub.clone();
   }

   public static void close() {
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
      // 00: ldc io/sentry/Sentry
      // 02: monitorenter
      // 03: invokestatic io/sentry/Sentry.getCurrentHub ()Lio/sentry/IHub;
      // 06: astore 0
      // 07: invokestatic io/sentry/NoOpHub.getInstance ()Lio/sentry/NoOpHub;
      // 0a: putstatic io/sentry/Sentry.mainHub Lio/sentry/IHub;
      // 0d: getstatic io/sentry/Sentry.currentHub Ljava/lang/ThreadLocal;
      // 10: invokevirtual java/lang/ThreadLocal.remove ()V
      // 13: aload 0
      // 14: bipush 0
      // 15: invokeinterface io/sentry/IHub.close (Z)V 2
      // 1a: ldc io/sentry/Sentry
      // 1c: monitorexit
      // 1d: return
      // 1e: astore 0
      // 1f: ldc io/sentry/Sentry
      // 21: monitorexit
      // 22: aload 0
      // 23: athrow
   }

   public static void configureScope(ScopeCallback var0) {
      getCurrentHub().configureScope(var0);
   }

   public static TransactionContext continueTrace(String var0, List<String> var1) {
      return getCurrentHub().continueTrace(var0, var1);
   }

   public static void endSession() {
      getCurrentHub().endSession();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void finalizePreviousSession(SentryOptions var0, IHub var1) {
      try {
         ISentryExecutorService var3 = var0.getExecutorService();
         PreviousSessionFinalizer var2 = new PreviousSessionFinalizer(var0, var1);
         var3.submit(var2);
      } catch (Throwable var5) {
         var0.getLogger().log(SentryLevel.DEBUG, "Failed to finalize previous session.", var5);
         return;
      }
   }

   public static void flush(long var0) {
      getCurrentHub().flush(var0);
   }

   public static BaggageHeader getBaggage() {
      return getCurrentHub().getBaggage();
   }

   public static IHub getCurrentHub() {
      if (globalHubMode) {
         return mainHub;
      } else {
         ThreadLocal var2 = currentHub;
         IHub var1 = (IHub)var2.get();
         if (var1 != null && !(var1 instanceof NoOpHub)) {
            return var1;
         } else {
            IHub var0 = mainHub.clone();
            var2.set(var0);
            return var0;
         }
      }
   }

   public static SentryId getLastEventId() {
      return getCurrentHub().getLastEventId();
   }

   public static ISpan getSpan() {
      return (ISpan)(globalHubMode && Platform.isAndroid() ? getCurrentHub().getTransaction() : getCurrentHub().getSpan());
   }

   public static SentryTraceHeader getTraceparent() {
      return getCurrentHub().getTraceparent();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void handleAppStartProfilingConfig(SentryOptions var0, ISentryExecutorService var1) {
      try {
         Sentry$$ExternalSyntheticLambda0 var2 = new Sentry$$ExternalSyntheticLambda0(var0);
         var1.submit(var2);
      } catch (Throwable var4) {
         var0.getLogger()
            .log(SentryLevel.ERROR, "Failed to call the executor. App start profiling config will not be changed. Did you call Sentry.close()?", var4);
         return;
      }
   }

   public static void init() {
      init(new Sentry$$ExternalSyntheticLambda2(), false);
   }

   public static <T extends SentryOptions> void init(OptionsContainer<T> var0, Sentry.OptionsConfiguration<T> var1) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
      init(var0, var1, false);
   }

   public static <T extends SentryOptions> void init(OptionsContainer<T> var0, Sentry.OptionsConfiguration<T> var1, boolean var2) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
      SentryOptions var3 = (SentryOptions)var0.createInstance();
      applyOptionsConfiguration(var1, var3);
      init(var3, var2);
   }

   public static void init(Sentry.OptionsConfiguration<SentryOptions> var0) {
      init(var0, false);
   }

   public static void init(Sentry.OptionsConfiguration<SentryOptions> var0, boolean var1) {
      SentryOptions var2 = new SentryOptions();
      applyOptionsConfiguration(var0, var2);
      init(var2, var1);
   }

   public static void init(SentryOptions var0) {
      init(var0, false);
   }

   private static void init(SentryOptions param0, boolean param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc io/sentry/Sentry
      // 02: monitorenter
      // 03: invokestatic io/sentry/Sentry.isEnabled ()Z
      // 06: ifeq 1c
      // 09: aload 0
      // 0a: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0d: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 10: ldc_w "Sentry has been already initialized. Previous configuration will be overwritten."
      // 13: bipush 0
      // 14: anewarray 4
      // 17: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1c: aload 0
      // 1d: invokestatic io/sentry/Sentry.initConfigurations (Lio/sentry/SentryOptions;)Z
      // 20: istore 2
      // 21: iload 2
      // 22: ifne 29
      // 25: ldc io/sentry/Sentry
      // 27: monitorexit
      // 28: return
      // 29: aload 0
      // 2a: invokevirtual io/sentry/SentryOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // 2d: astore 3
      // 2e: new io/sentry/Sentry$$ExternalSyntheticLambda4
      // 31: astore 4
      // 33: aload 4
      // 35: aload 0
      // 36: invokespecial io/sentry/Sentry$$ExternalSyntheticLambda4.<init> (Lio/sentry/SentryOptions;)V
      // 39: aload 3
      // 3a: aload 4
      // 3c: invokeinterface io/sentry/ISentryExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 41: pop
      // 42: goto 56
      // 45: astore 3
      // 46: aload 0
      // 47: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 4a: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 4d: ldc_w "Failed to call the executor. Lazy fields will not be loaded. Did you call Sentry.close()?"
      // 50: aload 3
      // 51: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 56: aload 0
      // 57: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 5a: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 5d: ldc_w "GlobalHubMode: '%s'"
      // 60: bipush 1
      // 61: anewarray 4
      // 64: dup
      // 65: bipush 0
      // 66: iload 1
      // 67: invokestatic java/lang/String.valueOf (Z)Ljava/lang/String;
      // 6a: aastore
      // 6b: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 70: iload 1
      // 71: putstatic io/sentry/Sentry.globalHubMode Z
      // 74: invokestatic io/sentry/Sentry.getCurrentHub ()Lio/sentry/IHub;
      // 77: astore 4
      // 79: new io/sentry/Hub
      // 7c: astore 3
      // 7d: aload 3
      // 7e: aload 0
      // 7f: invokespecial io/sentry/Hub.<init> (Lio/sentry/SentryOptions;)V
      // 82: aload 3
      // 83: putstatic io/sentry/Sentry.mainHub Lio/sentry/IHub;
      // 86: getstatic io/sentry/Sentry.currentHub Ljava/lang/ThreadLocal;
      // 89: getstatic io/sentry/Sentry.mainHub Lio/sentry/IHub;
      // 8c: invokevirtual java/lang/ThreadLocal.set (Ljava/lang/Object;)V
      // 8f: aload 4
      // 91: bipush 1
      // 92: invokeinterface io/sentry/IHub.close (Z)V 2
      // 97: aload 0
      // 98: invokevirtual io/sentry/SentryOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // 9b: invokeinterface io/sentry/ISentryExecutorService.isClosed ()Z 1
      // a0: ifeq b0
      // a3: new io/sentry/SentryExecutorService
      // a6: astore 3
      // a7: aload 3
      // a8: invokespecial io/sentry/SentryExecutorService.<init> ()V
      // ab: aload 0
      // ac: aload 3
      // ad: invokevirtual io/sentry/SentryOptions.setExecutorService (Lio/sentry/ISentryExecutorService;)V
      // b0: aload 0
      // b1: invokevirtual io/sentry/SentryOptions.getIntegrations ()Ljava/util/List;
      // b4: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // b9: astore 3
      // ba: aload 3
      // bb: invokeinterface java/util/Iterator.hasNext ()Z 1
      // c0: ifeq d8
      // c3: aload 3
      // c4: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // c9: checkcast io/sentry/Integration
      // cc: invokestatic io/sentry/HubAdapter.getInstance ()Lio/sentry/HubAdapter;
      // cf: aload 0
      // d0: invokeinterface io/sentry/Integration.register (Lio/sentry/IHub;Lio/sentry/SentryOptions;)V 3
      // d5: goto ba
      // d8: aload 0
      // d9: invokestatic io/sentry/Sentry.notifyOptionsObservers (Lio/sentry/SentryOptions;)V
      // dc: aload 0
      // dd: invokestatic io/sentry/HubAdapter.getInstance ()Lio/sentry/HubAdapter;
      // e0: invokestatic io/sentry/Sentry.finalizePreviousSession (Lio/sentry/SentryOptions;Lio/sentry/IHub;)V
      // e3: aload 0
      // e4: aload 0
      // e5: invokevirtual io/sentry/SentryOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // e8: invokestatic io/sentry/Sentry.handleAppStartProfilingConfig (Lio/sentry/SentryOptions;Lio/sentry/ISentryExecutorService;)V
      // eb: ldc io/sentry/Sentry
      // ed: monitorexit
      // ee: return
      // ef: astore 0
      // f0: ldc io/sentry/Sentry
      // f2: monitorexit
      // f3: aload 0
      // f4: athrow
   }

   public static void init(String var0) {
      init(new Sentry$$ExternalSyntheticLambda5(var0));
   }

   private static boolean initConfigurations(SentryOptions var0) {
      if (var0.isEnableExternalConfiguration()) {
         var0.merge(ExternalOptions.from(PropertiesProviderFactory.create(), var0.getLogger()));
      }

      String var1 = var0.getDsn();
      if (var0.isEnabled() && (var1 == null || !var1.isEmpty())) {
         if (var1 != null) {
            var0.retrieveParsedDsn();
            ILogger var2 = var0.getLogger();
            ILogger var5 = var2;
            if (var0.isDebug()) {
               var5 = var2;
               if (var2 instanceof NoOpLogger) {
                  var0.setLogger(new SystemOutLogger());
                  var5 = var0.getLogger();
               }
            }

            var5.log(SentryLevel.INFO, "Initializing SDK with DSN: '%s'", var0.getDsn());
            String var10 = var0.getOutboxPath();
            if (var10 != null) {
               new File(var10).mkdirs();
            } else {
               var5.log(SentryLevel.INFO, "No outbox dir path is defined in options.");
            }

            var1 = var0.getCacheDirPath();
            if (var1 != null) {
               new File(var1).mkdirs();
               if (var0.getEnvelopeDiskCache() instanceof NoOpEnvelopeCache) {
                  var0.setEnvelopeDiskCache(EnvelopeCache.create(var0));
               }
            }

            var1 = var0.getProfilingTracesDirPath();
            if (var0.isProfilingEnabled() && var1 != null) {
               File var8 = new File(var1);
               var8.mkdirs();

               try {
                  ISentryExecutorService var3 = var0.getExecutorService();
                  Sentry$$ExternalSyntheticLambda1 var11 = new Sentry$$ExternalSyntheticLambda1(var8);
                  var3.submit(var11);
               } catch (RejectedExecutionException var4) {
                  var0.getLogger().log(SentryLevel.ERROR, "Failed to call the executor. Old profiles will not be deleted. Did you call Sentry.close()?", var4);
               }
            }

            IModulesLoader var9 = var0.getModulesLoader();
            if (!var0.isSendModules()) {
               var0.setModulesLoader(NoOpModulesLoader.getInstance());
            } else if (var9 instanceof NoOpModulesLoader) {
               var0.setModulesLoader(
                  new CompositeModulesLoader(
                     Arrays.asList(new ManifestModulesLoader(var0.getLogger()), new ResourcesModulesLoader(var0.getLogger())), var0.getLogger()
                  )
               );
            }

            if (var0.getDebugMetaLoader() instanceof NoOpDebugMetaLoader) {
               var0.setDebugMetaLoader(new ResourcesDebugMetaLoader(var0.getLogger()));
            }

            DebugMetaPropertiesApplier.applyToOptions(var0, var0.getDebugMetaLoader().loadDebugMeta());
            if (var0.getMainThreadChecker() instanceof NoOpMainThreadChecker) {
               var0.setMainThreadChecker(MainThreadChecker.getInstance());
            }

            if (var0.getPerformanceCollectors().isEmpty()) {
               var0.addPerformanceCollector(new JavaMemoryCollector());
            }

            if (var0.isEnableBackpressureHandling() && Platform.isJvm()) {
               var0.setBackpressureMonitor(new BackpressureMonitor(var0, HubAdapter.getInstance()));
               var0.getBackpressureMonitor().start();
            }

            return true;
         } else {
            throw new IllegalArgumentException("DSN is required. Use empty string or set enabled to false in SentryOptions to disable SDK.");
         }
      } else {
         close();
         return false;
      }
   }

   public static Boolean isCrashedLastRun() {
      return getCurrentHub().isCrashedLastRun();
   }

   public static boolean isEnabled() {
      return getCurrentHub().isEnabled();
   }

   public static boolean isHealthy() {
      return getCurrentHub().isHealthy();
   }

   public static MetricsApi metrics() {
      return getCurrentHub().metrics();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void notifyOptionsObservers(SentryOptions var0) {
      try {
         ISentryExecutorService var2 = var0.getExecutorService();
         Sentry$$ExternalSyntheticLambda3 var1 = new Sentry$$ExternalSyntheticLambda3(var0);
         var2.submit(var1);
      } catch (Throwable var4) {
         var0.getLogger().log(SentryLevel.DEBUG, "Failed to notify options observers.", var4);
         return;
      }
   }

   public static void popScope() {
      if (!globalHubMode) {
         getCurrentHub().popScope();
      }
   }

   public static void pushScope() {
      if (!globalHubMode) {
         getCurrentHub().pushScope();
      }
   }

   public static void removeExtra(String var0) {
      getCurrentHub().removeExtra(var0);
   }

   public static void removeTag(String var0) {
      getCurrentHub().removeTag(var0);
   }

   @Deprecated
   public static void reportFullDisplayed() {
      reportFullyDisplayed();
   }

   public static void reportFullyDisplayed() {
      getCurrentHub().reportFullyDisplayed();
   }

   private static TracesSamplingDecision sampleAppStartProfiling(SentryOptions var0) {
      TransactionContext var1 = new TransactionContext("app.launch", "profile");
      var1.setForNextAppStart(true);
      SamplingContext var2 = new SamplingContext(var1, null);
      return new TracesSampler(var0).sample(var2);
   }

   public static void setCurrentHub(IHub var0) {
      currentHub.set(var0);
   }

   public static void setExtra(String var0, String var1) {
      getCurrentHub().setExtra(var0, var1);
   }

   public static void setFingerprint(List<String> var0) {
      getCurrentHub().setFingerprint(var0);
   }

   public static void setLevel(SentryLevel var0) {
      getCurrentHub().setLevel(var0);
   }

   public static void setTag(String var0, String var1) {
      getCurrentHub().setTag(var0, var1);
   }

   public static void setTransaction(String var0) {
      getCurrentHub().setTransaction(var0);
   }

   public static void setUser(User var0) {
      getCurrentHub().setUser(var0);
   }

   public static void startSession() {
      getCurrentHub().startSession();
   }

   public static ITransaction startTransaction(TransactionContext var0) {
      return getCurrentHub().startTransaction(var0);
   }

   public static ITransaction startTransaction(TransactionContext var0, TransactionOptions var1) {
      return getCurrentHub().startTransaction(var0, var1);
   }

   public static ITransaction startTransaction(String var0, String var1) {
      return getCurrentHub().startTransaction(var0, var1);
   }

   public static ITransaction startTransaction(String var0, String var1, TransactionOptions var2) {
      return getCurrentHub().startTransaction(var0, var1, var2);
   }

   public static ITransaction startTransaction(String var0, String var1, String var2, TransactionOptions var3) {
      ITransaction var4 = getCurrentHub().startTransaction(var0, var1, var3);
      var4.setDescription(var2);
      return var4;
   }

   @Deprecated
   public static SentryTraceHeader traceHeaders() {
      return getCurrentHub().traceHeaders();
   }

   public static void withScope(ScopeCallback var0) {
      getCurrentHub().withScope(var0);
   }

   public interface OptionsConfiguration<T extends SentryOptions> {
      void configure(T var1);
   }
}
