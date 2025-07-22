package io.sentry.android.replay.util

import io.sentry.ILogger
import io.sentry.ISentryExecutorService
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

@JvmSynthetic
fun `$r8$lambda$ObMDDNpHR7wFxglnsnnusThL8NY`(var0: Runnable, var1: SentryOptions, var2: java.lang.String) {
   scheduleAtFixedRateSafely$lambda$3(var0, var1, var2);
}

@JvmSynthetic
fun `$r8$lambda$vDu4aDIfWey-ssnK2M6wckmJiUQ`(var0: Runnable, var1: SentryOptions, var2: java.lang.String) {
   submitSafely$lambda$2(var0, var1, var2);
}

@JvmSynthetic
fun `$r8$lambda$yLl_DltBiGn0Xbm3L1scmLCeXhk`(var0: Runnable, var1: SentryOptions, var2: java.lang.String) {
   submitSafely$lambda$1(var0, var1, var2);
}

internal fun ExecutorService.gracefullyShutdown(options: SentryOptions) {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.RuntimeException: parsing failure!
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
   //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
   //
   // Bytecode:
   // 00: aload 0
   // 01: ldc "<this>"
   // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 06: aload 1
   // 07: ldc "options"
   // 09: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 0c: aload 0
   // 0d: monitorenter
   // 0e: aload 0
   // 0f: invokeinterface java/util/concurrent/ExecutorService.isShutdown ()Z 1
   // 14: ifne 1d
   // 17: aload 0
   // 18: invokeinterface java/util/concurrent/ExecutorService.shutdown ()V 1
   // 1d: aload 0
   // 1e: aload 1
   // 1f: invokevirtual io/sentry/SentryOptions.getShutdownTimeoutMillis ()J
   // 22: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
   // 25: invokeinterface java/util/concurrent/ExecutorService.awaitTermination (JLjava/util/concurrent/TimeUnit;)Z 4
   // 2a: ifne 45
   // 2d: aload 0
   // 2e: invokeinterface java/util/concurrent/ExecutorService.shutdownNow ()Ljava/util/List; 1
   // 33: pop
   // 34: goto 45
   // 37: astore 1
   // 38: aload 0
   // 39: invokeinterface java/util/concurrent/ExecutorService.shutdownNow ()Ljava/util/List; 1
   // 3e: pop
   // 3f: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
   // 42: invokevirtual java/lang/Thread.interrupt ()V
   // 45: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 48: astore 1
   // 49: aload 0
   // 4a: monitorexit
   // 4b: return
   // 4c: astore 1
   // 4d: aload 0
   // 4e: monitorexit
   // 4f: aload 1
   // 50: athrow
}

internal fun ScheduledExecutorService.scheduleAtFixedRateSafely(
   options: SentryOptions,
   taskName: String,
   initialDelay: Long,
   period: Long,
   unit: TimeUnit,
   task: Runnable
): ScheduledFuture<*>? {
   try {
      var12 = var0.scheduleAtFixedRate(new ExecutorsKt$$ExternalSyntheticLambda0(var8, var1, var2), var3, var5, var7);
   } catch (var10: java.lang.Throwable) {
      val var13: ILogger = var1.getLogger();
      val var14: SentryLevel = SentryLevel.ERROR;
      val var15: StringBuilder = new StringBuilder("Failed to submit task ");
      var15.append(var2);
      var15.append(" to executor");
      var13.log(var14, var15.toString(), var10);
      return null;
   }

   return var12;
}

// $VF: Could not inline inconsistent finally blocks
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
fun `scheduleAtFixedRateSafely$lambda$3`(var0: Runnable, var1: SentryOptions, var2: java.lang.String) {
   try {
      var0.run();
   } catch (var5: java.lang.Throwable) {
      val var7: ILogger = var1.getLogger();
      val var4: SentryLevel = SentryLevel.ERROR;
      val var3: StringBuilder = new StringBuilder("Failed to execute task ");
      var3.append(var2);
      var7.log(var4, var3.toString(), var5);
      return;
   }
}

internal fun ISentryExecutorService.submitSafely(options: SentryOptions, taskName: String, task: Runnable): Future<*>? {
   try {
      var7 = var0.submit(new ExecutorsKt$$ExternalSyntheticLambda2(var3, var1, var2));
   } catch (var5: java.lang.Throwable) {
      val var8: ILogger = var1.getLogger();
      val var9: SentryLevel = SentryLevel.ERROR;
      val var4: StringBuilder = new StringBuilder("Failed to submit task ");
      var4.append(var2);
      var4.append(" to executor");
      var8.log(var9, var4.toString(), var5);
      return null;
   }

   return var7;
}

internal fun ExecutorService.submitSafely(options: SentryOptions, taskName: String, task: Runnable): Future<*>? {
   val var5: java.lang.String = Thread.currentThread().getName();
   if (StringsKt.startsWith$default(var5, "SentryReplayIntegration", false, 2, null)) {
      var3.run();
      return null;
   } else {
      try {
         var8 = var0.submit(new ExecutorsKt$$ExternalSyntheticLambda1(var3, var1, var2));
      } catch (var6: java.lang.Throwable) {
         val var10: ILogger = var1.getLogger();
         val var11: SentryLevel = SentryLevel.ERROR;
         val var9: StringBuilder = new StringBuilder("Failed to submit task ");
         var9.append(var2);
         var9.append(" to executor");
         var10.log(var11, var9.toString(), var6);
         return null;
      }

      return var8;
   }
}

// $VF: Could not inline inconsistent finally blocks
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
fun `submitSafely$lambda$1`(var0: Runnable, var1: SentryOptions, var2: java.lang.String) {
   try {
      var0.run();
   } catch (var5: java.lang.Throwable) {
      val var7: ILogger = var1.getLogger();
      val var3: SentryLevel = SentryLevel.ERROR;
      val var4: StringBuilder = new StringBuilder("Failed to execute task ");
      var4.append(var2);
      var7.log(var3, var4.toString(), var5);
      return;
   }
}

// $VF: Could not inline inconsistent finally blocks
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
fun `submitSafely$lambda$2`(var0: Runnable, var1: SentryOptions, var2: java.lang.String) {
   try {
      var0.run();
   } catch (var5: java.lang.Throwable) {
      val var7: ILogger = var1.getLogger();
      val var3: SentryLevel = SentryLevel.ERROR;
      val var4: StringBuilder = new StringBuilder("Failed to execute task ");
      var4.append(var2);
      var7.log(var3, var4.toString(), var5);
      return;
   }
}
