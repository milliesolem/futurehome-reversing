package io.sentry.android.core;

import io.sentry.IConnectionStatusProvider;
import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SendCachedEnvelopeFireAndForgetIntegration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.LazyEvaluator;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

final class SendCachedEnvelopeIntegration implements Integration, IConnectionStatusProvider.IConnectionStatusObserver, Closeable {
   private IConnectionStatusProvider connectionStatusProvider;
   private final SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory factory;
   private IHub hub;
   private final AtomicBoolean isClosed;
   private final AtomicBoolean isInitialized;
   private SentryAndroidOptions options;
   private SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget sender;
   private final AtomicBoolean startupCrashHandled = new AtomicBoolean(false);
   private final LazyEvaluator<Boolean> startupCrashMarkerEvaluator;

   public SendCachedEnvelopeIntegration(SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory var1, LazyEvaluator<Boolean> var2) {
      this.isInitialized = new AtomicBoolean(false);
      this.isClosed = new AtomicBoolean(false);
      this.factory = Objects.requireNonNull(var1, "SendFireAndForgetFactory is required");
      this.startupCrashMarkerEvaluator = var2;
   }

   private void sendCachedEnvelopes(IHub param1, SentryAndroidOptions param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 2
      // 03: invokevirtual io/sentry/android/core/SentryAndroidOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // 06: astore 3
      // 07: new io/sentry/android/core/SendCachedEnvelopeIntegration$$ExternalSyntheticLambda0
      // 0a: astore 4
      // 0c: aload 4
      // 0e: aload 0
      // 0f: aload 2
      // 10: aload 1
      // 11: invokespecial io/sentry/android/core/SendCachedEnvelopeIntegration$$ExternalSyntheticLambda0.<init> (Lio/sentry/android/core/SendCachedEnvelopeIntegration;Lio/sentry/android/core/SentryAndroidOptions;Lio/sentry/IHub;)V
      // 14: aload 3
      // 15: aload 4
      // 17: invokeinterface io/sentry/ISentryExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 1c: astore 1
      // 1d: aload 0
      // 1e: getfield io/sentry/android/core/SendCachedEnvelopeIntegration.startupCrashMarkerEvaluator Lio/sentry/util/LazyEvaluator;
      // 21: invokevirtual io/sentry/util/LazyEvaluator.getValue ()Ljava/lang/Object;
      // 24: checkcast java/lang/Boolean
      // 27: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 2a: ifeq 6f
      // 2d: aload 0
      // 2e: getfield io/sentry/android/core/SendCachedEnvelopeIntegration.startupCrashHandled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 31: bipush 0
      // 32: bipush 1
      // 33: invokevirtual java/util/concurrent/atomic/AtomicBoolean.compareAndSet (ZZ)Z
      // 36: ifeq 6f
      // 39: aload 2
      // 3a: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 3d: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 40: ldc "Startup Crash marker exists, blocking flush."
      // 42: bipush 0
      // 43: anewarray 4
      // 46: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 4b: aload 1
      // 4c: aload 2
      // 4d: invokevirtual io/sentry/android/core/SentryAndroidOptions.getStartupCrashFlushTimeoutMillis ()J
      // 50: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 53: invokeinterface java/util/concurrent/Future.get (JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object; 4
      // 58: pop
      // 59: goto 6f
      // 5c: astore 1
      // 5d: aload 2
      // 5e: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 61: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 64: ldc "Synchronous send timed out, continuing in the background."
      // 66: bipush 0
      // 67: anewarray 4
      // 6a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 6f: aload 2
      // 70: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 73: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 76: ldc "SendCachedEnvelopeIntegration installed."
      // 78: bipush 0
      // 79: anewarray 4
      // 7c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 81: goto a7
      // 84: astore 1
      // 85: aload 2
      // 86: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 89: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 8c: ldc "Failed to call the executor. Cached events will not be sent"
      // 8e: aload 1
      // 8f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 94: goto a7
      // 97: astore 1
      // 98: aload 2
      // 99: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 9c: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 9f: ldc "Failed to call the executor. Cached events will not be sent. Did you call Sentry.close()?"
      // a1: aload 1
      // a2: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // a7: aload 0
      // a8: monitorexit
      // a9: return
      // aa: astore 1
      // ab: aload 0
      // ac: monitorexit
      // ad: aload 1
      // ae: athrow
   }

   @Override
   public void close() throws IOException {
      this.isClosed.set(true);
      IConnectionStatusProvider var1 = this.connectionStatusProvider;
      if (var1 != null) {
         var1.removeConnectionStatusObserver(this);
      }
   }

   @Override
   public void onConnectionStatusChanged(IConnectionStatusProvider.ConnectionStatus var1) {
      IHub var2 = this.hub;
      if (var2 != null) {
         SentryAndroidOptions var3 = this.options;
         if (var3 != null) {
            this.sendCachedEnvelopes(var2, var3);
         }
      }
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      this.hub = Objects.requireNonNull(var1, "Hub is required");
      SentryAndroidOptions var3;
      if (var2 instanceof SentryAndroidOptions) {
         var3 = (SentryAndroidOptions)var2;
      } else {
         var3 = null;
      }

      this.options = Objects.requireNonNull(var3, "SentryAndroidOptions is required");
      String var4 = var2.getCacheDirPath();
      if (!this.factory.hasValidPath(var4, var2.getLogger())) {
         var2.getLogger().log(SentryLevel.ERROR, "No cache dir path is defined in options.");
      } else {
         IntegrationUtils.addIntegrationToSdkVersion("SendCachedEnvelope");
         this.sendCachedEnvelopes(var1, this.options);
      }
   }
}
