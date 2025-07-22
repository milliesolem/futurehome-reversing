package io.sentry;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class SentryExecutorService implements ISentryExecutorService {
   private final ScheduledExecutorService executorService;

   public SentryExecutorService() {
      this(Executors.newSingleThreadScheduledExecutor(new SentryExecutorService.SentryExecutorServiceThreadFactory()));
   }

   SentryExecutorService(ScheduledExecutorService var1) {
      this.executorService = var1;
   }

   @Override
   public void close(long param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 0b: invokeinterface java/util/concurrent/ScheduledExecutorService.isShutdown ()Z 1
      // 10: ifne 4b
      // 13: aload 0
      // 14: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 17: invokeinterface java/util/concurrent/ScheduledExecutorService.shutdown ()V 1
      // 1c: aload 0
      // 1d: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 20: lload 1
      // 21: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 24: invokeinterface java/util/concurrent/ScheduledExecutorService.awaitTermination (JLjava/util/concurrent/TimeUnit;)Z 4
      // 29: ifne 4b
      // 2c: aload 0
      // 2d: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 30: invokeinterface java/util/concurrent/ScheduledExecutorService.shutdownNow ()Ljava/util/List; 1
      // 35: pop
      // 36: goto 4b
      // 39: astore 4
      // 3b: aload 0
      // 3c: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 3f: invokeinterface java/util/concurrent/ScheduledExecutorService.shutdownNow ()Ljava/util/List; 1
      // 44: pop
      // 45: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 48: invokevirtual java/lang/Thread.interrupt ()V
      // 4b: aload 3
      // 4c: monitorexit
      // 4d: return
      // 4e: astore 4
      // 50: aload 3
      // 51: monitorexit
      // 52: aload 4
      // 54: athrow
   }

   @Override
   public boolean isClosed() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/SentryExecutorService.executorService Ljava/util/concurrent/ScheduledExecutorService;
      // 0b: invokeinterface java/util/concurrent/ScheduledExecutorService.isShutdown ()Z 1
      // 10: istore 1
      // 11: aload 3
      // 12: monitorexit
      // 13: iload 1
      // 14: ireturn
      // 15: astore 2
      // 16: aload 3
      // 17: monitorexit
      // 18: aload 2
      // 19: athrow
   }

   @Override
   public Future<?> schedule(Runnable var1, long var2) {
      return this.executorService.schedule(var1, var2, TimeUnit.MILLISECONDS);
   }

   @Override
   public Future<?> submit(Runnable var1) {
      return this.executorService.submit(var1);
   }

   @Override
   public <T> Future<T> submit(Callable<T> var1) {
      return this.executorService.submit(var1);
   }

   private static final class SentryExecutorServiceThreadFactory implements ThreadFactory {
      private int cnt;

      private SentryExecutorServiceThreadFactory() {
      }

      @Override
      public Thread newThread(Runnable var1) {
         StringBuilder var3 = new StringBuilder("SentryExecutorServiceThreadFactory-");
         int var2 = this.cnt++;
         var3.append(var2);
         Thread var4 = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }
}
