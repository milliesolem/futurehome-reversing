package io.sentry.android.core;

import android.content.Context;
import io.sentry.IHub;
import io.sentry.ISentryExecutorService;
import io.sentry.Integration;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.exception.ExceptionMechanismException;
import io.sentry.hints.AbnormalExit;
import io.sentry.hints.TransactionEnd;
import io.sentry.protocol.Mechanism;
import io.sentry.util.HintUtils;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class AnrIntegration implements Integration, Closeable {
   private static ANRWatchDog anrWatchDog;
   private static final Object watchDogLock = new Object();
   private final Context context;
   private boolean isClosed = false;
   private SentryOptions options;
   private final Object startLock = new Object();

   public AnrIntegration(Context var1) {
      this.context = ContextUtils.getApplicationContext(var1);
   }

   private Throwable buildAnrThrowable(boolean var1, SentryAndroidOptions var2, ApplicationNotResponding var3) {
      StringBuilder var4 = new StringBuilder("ANR for at least ");
      var4.append(var2.getAnrTimeoutIntervalMillis());
      var4.append(" ms.");
      String var9 = var4.toString();
      String var5 = var9;
      if (var1) {
         StringBuilder var6 = new StringBuilder("Background ");
         var6.append(var9);
         var5 = var6.toString();
      }

      var3 = new ApplicationNotResponding(var5, var3.getThread());
      Mechanism var7 = new Mechanism();
      var7.setType("ANR");
      return new ExceptionMechanismException(var7, var3, var3.getThread(), true);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void register(IHub var1, SentryAndroidOptions var2) {
      var2.getLogger().log(SentryLevel.DEBUG, "AnrIntegration enabled: %s", var2.isAnrEnabled());
      if (var2.isAnrEnabled()) {
         IntegrationUtils.addIntegrationToSdkVersion("Anr");

         try {
            ISentryExecutorService var4 = var2.getExecutorService();
            AnrIntegration$$ExternalSyntheticLambda0 var3 = new AnrIntegration$$ExternalSyntheticLambda0(this, var1, var2);
            var4.submit(var3);
         } catch (Throwable var6) {
            var2.getLogger().log(SentryLevel.DEBUG, "Failed to start AnrIntegration on executor thread.", var6);
            return;
         }
      }
   }

   private void startAnrWatchdog(IHub param1, SentryAndroidOptions param2) {
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
      // 00: getstatic io/sentry/android/core/AnrIntegration.watchDogLock Ljava/lang/Object;
      // 03: astore 6
      // 05: aload 6
      // 07: monitorenter
      // 08: getstatic io/sentry/android/core/AnrIntegration.anrWatchDog Lio/sentry/android/core/ANRWatchDog;
      // 0b: ifnonnull 75
      // 0e: aload 2
      // 0f: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 12: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 15: ldc "ANR timeout in milliseconds: %d"
      // 17: bipush 1
      // 18: anewarray 4
      // 1b: dup
      // 1c: bipush 0
      // 1d: aload 2
      // 1e: invokevirtual io/sentry/android/core/SentryAndroidOptions.getAnrTimeoutIntervalMillis ()J
      // 21: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 24: aastore
      // 25: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 2a: new io/sentry/android/core/ANRWatchDog
      // 2d: astore 8
      // 2f: aload 2
      // 30: invokevirtual io/sentry/android/core/SentryAndroidOptions.getAnrTimeoutIntervalMillis ()J
      // 33: lstore 4
      // 35: aload 2
      // 36: invokevirtual io/sentry/android/core/SentryAndroidOptions.isAnrReportInDebug ()Z
      // 39: istore 3
      // 3a: new io/sentry/android/core/AnrIntegration$$ExternalSyntheticLambda1
      // 3d: astore 7
      // 3f: aload 7
      // 41: aload 0
      // 42: aload 1
      // 43: aload 2
      // 44: invokespecial io/sentry/android/core/AnrIntegration$$ExternalSyntheticLambda1.<init> (Lio/sentry/android/core/AnrIntegration;Lio/sentry/IHub;Lio/sentry/android/core/SentryAndroidOptions;)V
      // 47: aload 8
      // 49: lload 4
      // 4b: iload 3
      // 4c: aload 7
      // 4e: aload 2
      // 4f: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 52: aload 0
      // 53: getfield io/sentry/android/core/AnrIntegration.context Landroid/content/Context;
      // 56: invokespecial io/sentry/android/core/ANRWatchDog.<init> (JZLio/sentry/android/core/ANRWatchDog$ANRListener;Lio/sentry/ILogger;Landroid/content/Context;)V
      // 59: aload 8
      // 5b: putstatic io/sentry/android/core/AnrIntegration.anrWatchDog Lio/sentry/android/core/ANRWatchDog;
      // 5e: aload 8
      // 60: invokevirtual io/sentry/android/core/ANRWatchDog.start ()V
      // 63: aload 2
      // 64: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 67: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 6a: ldc "AnrIntegration installed."
      // 6c: bipush 0
      // 6d: anewarray 4
      // 70: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 75: aload 6
      // 77: monitorexit
      // 78: return
      // 79: astore 1
      // 7a: aload 6
      // 7c: monitorexit
      // 7d: aload 1
      // 7e: athrow
   }

   @Override
   public void close() throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/core/AnrIntegration.startLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield io/sentry/android/core/AnrIntegration.isClosed Z
      // 0c: aload 2
      // 0d: monitorexit
      // 0e: getstatic io/sentry/android/core/AnrIntegration.watchDogLock Ljava/lang/Object;
      // 11: astore 1
      // 12: aload 1
      // 13: monitorenter
      // 14: getstatic io/sentry/android/core/AnrIntegration.anrWatchDog Lio/sentry/android/core/ANRWatchDog;
      // 17: astore 2
      // 18: aload 2
      // 19: ifnull 3f
      // 1c: aload 2
      // 1d: invokevirtual io/sentry/android/core/ANRWatchDog.interrupt ()V
      // 20: aconst_null
      // 21: putstatic io/sentry/android/core/AnrIntegration.anrWatchDog Lio/sentry/android/core/ANRWatchDog;
      // 24: aload 0
      // 25: getfield io/sentry/android/core/AnrIntegration.options Lio/sentry/SentryOptions;
      // 28: astore 2
      // 29: aload 2
      // 2a: ifnull 3f
      // 2d: aload 2
      // 2e: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 31: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 34: ldc "AnrIntegration removed."
      // 36: bipush 0
      // 37: anewarray 4
      // 3a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 3f: aload 1
      // 40: monitorexit
      // 41: return
      // 42: astore 2
      // 43: aload 1
      // 44: monitorexit
      // 45: aload 2
      // 46: athrow
      // 47: astore 1
      // 48: aload 2
      // 49: monitorexit
      // 4a: aload 1
      // 4b: athrow
   }

   ANRWatchDog getANRWatchDog() {
      return anrWatchDog;
   }

   @Override
   public final void register(IHub var1, SentryOptions var2) {
      this.options = Objects.requireNonNull(var2, "SentryOptions is required");
      this.register(var1, (SentryAndroidOptions)var2);
   }

   void reportANR(IHub var1, SentryAndroidOptions var2, ApplicationNotResponding var3) {
      var2.getLogger().log(SentryLevel.INFO, "ANR triggered with message: %s", var3.getMessage());
      boolean var4 = Boolean.TRUE.equals(AppState.getInstance().isInBackground());
      SentryEvent var5 = new SentryEvent(this.buildAnrThrowable(var4, var2, var3));
      var5.setLevel(SentryLevel.ERROR);
      var1.captureEvent(var5, HintUtils.createWithTypeCheckHint(new AnrIntegration.AnrHint(var4)));
   }

   static final class AnrHint implements AbnormalExit, TransactionEnd {
      private final boolean isBackgroundAnr;

      AnrHint(boolean var1) {
         this.isBackgroundAnr = var1;
      }

      @Override
      public boolean ignoreCurrentThread() {
         return true;
      }

      @Override
      public String mechanism() {
         String var1;
         if (this.isBackgroundAnr) {
            var1 = "anr_background";
         } else {
            var1 = "anr_foreground";
         }

         return var1;
      }

      @Override
      public Long timestamp() {
         return null;
      }
   }
}
