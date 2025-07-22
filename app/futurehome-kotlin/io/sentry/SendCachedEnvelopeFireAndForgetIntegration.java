package io.sentry;

import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SendCachedEnvelopeFireAndForgetIntegration implements Integration, IConnectionStatusProvider.IConnectionStatusObserver, Closeable {
   private IConnectionStatusProvider connectionStatusProvider;
   private final SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory factory;
   private IHub hub;
   private final AtomicBoolean isClosed;
   private final AtomicBoolean isInitialized = new AtomicBoolean(false);
   private SentryOptions options;
   private SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget sender;

   public SendCachedEnvelopeFireAndForgetIntegration(SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory var1) {
      this.isClosed = new AtomicBoolean(false);
      this.factory = Objects.requireNonNull(var1, "SendFireAndForgetFactory is required");
   }

   private void sendCachedEnvelopes(IHub param1, SentryOptions param2) {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 2
      // 03: invokevirtual io/sentry/SentryOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // 06: astore 4
      // 08: new io/sentry/SendCachedEnvelopeFireAndForgetIntegration$$ExternalSyntheticLambda0
      // 0b: astore 3
      // 0c: aload 3
      // 0d: aload 0
      // 0e: aload 2
      // 0f: aload 1
      // 10: invokespecial io/sentry/SendCachedEnvelopeFireAndForgetIntegration$$ExternalSyntheticLambda0.<init> (Lio/sentry/SendCachedEnvelopeFireAndForgetIntegration;Lio/sentry/SentryOptions;Lio/sentry/IHub;)V
      // 13: aload 4
      // 15: aload 3
      // 16: invokeinterface io/sentry/ISentryExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 1b: pop
      // 1c: goto 42
      // 1f: astore 1
      // 20: aload 2
      // 21: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 24: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 27: ldc "Failed to call the executor. Cached events will not be sent"
      // 29: aload 1
      // 2a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 2f: goto 42
      // 32: astore 1
      // 33: aload 2
      // 34: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 37: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 3a: ldc "Failed to call the executor. Cached events will not be sent. Did you call Sentry.close()?"
      // 3c: aload 1
      // 3d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 42: aload 0
      // 43: monitorexit
      // 44: return
      // 45: astore 1
      // 46: aload 0
      // 47: monitorexit
      // 48: aload 1
      // 49: athrow
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
      IHub var3 = this.hub;
      if (var3 != null) {
         SentryOptions var2 = this.options;
         if (var2 != null) {
            this.sendCachedEnvelopes(var3, var2);
         }
      }
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      this.hub = Objects.requireNonNull(var1, "Hub is required");
      this.options = Objects.requireNonNull(var2, "SentryOptions is required");
      String var3 = var2.getCacheDirPath();
      if (!this.factory.hasValidPath(var3, var2.getLogger())) {
         var2.getLogger().log(SentryLevel.ERROR, "No cache dir path is defined in options.");
      } else {
         var2.getLogger().log(SentryLevel.DEBUG, "SendCachedEventFireAndForgetIntegration installed.");
         IntegrationUtils.addIntegrationToSdkVersion("SendCachedEnvelopeFireAndForget");
         this.sendCachedEnvelopes(var1, var2);
      }
   }

   public interface SendFireAndForget {
      void send();
   }

   public interface SendFireAndForgetDirPath {
      String getDirPath();
   }

   public interface SendFireAndForgetFactory {
      SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget create(IHub var1, SentryOptions var2);

      boolean hasValidPath(String var1, ILogger var2);

      SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget processDir(DirectoryProcessor var1, String var2, ILogger var3);
   }
}
