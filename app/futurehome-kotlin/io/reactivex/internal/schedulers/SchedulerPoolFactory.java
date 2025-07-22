package io.reactivex.internal.schedulers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.functions.Function;
import j..util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SchedulerPoolFactory {
   static final Map<ScheduledThreadPoolExecutor, Object> POOLS = new ConcurrentHashMap();
   public static final boolean PURGE_ENABLED;
   static final String PURGE_ENABLED_KEY = "rx2.purge-enabled";
   public static final int PURGE_PERIOD_SECONDS;
   static final String PURGE_PERIOD_SECONDS_KEY = "rx2.purge-period-seconds";
   static final AtomicReference<ScheduledExecutorService> PURGE_THREAD = new AtomicReference<>();

   static {
      SchedulerPoolFactory.SystemPropertyAccessor var1 = new SchedulerPoolFactory.SystemPropertyAccessor();
      boolean var0 = getBooleanProperty(true, "rx2.purge-enabled", true, true, var1);
      PURGE_ENABLED = var0;
      PURGE_PERIOD_SECONDS = getIntProperty(var0, "rx2.purge-period-seconds", 1, 1, var1);
      start();
   }

   private SchedulerPoolFactory() {
      throw new IllegalStateException("No instances!");
   }

   public static ScheduledExecutorService create(ThreadFactory var0) {
      ScheduledExecutorService var1 = Executors.newScheduledThreadPool(1, var0);
      tryPutIntoPool(PURGE_ENABLED, var1);
      return var1;
   }

   static boolean getBooleanProperty(boolean param0, String param1, boolean param2, boolean param3, Function<String, String> param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: iload 0
      // 01: ifeq 22
      // 04: aload 4
      // 06: aload 1
      // 07: invokeinterface io/reactivex/functions/Function.apply (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0c: checkcast java/lang/String
      // 0f: astore 1
      // 10: aload 1
      // 11: ifnonnull 16
      // 14: iload 2
      // 15: ireturn
      // 16: ldc "true"
      // 18: aload 1
      // 19: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 1c: istore 0
      // 1d: iload 0
      // 1e: ireturn
      // 1f: astore 1
      // 20: iload 2
      // 21: ireturn
      // 22: iload 3
      // 23: ireturn
   }

   static int getIntProperty(boolean param0, String param1, int param2, int param3, Function<String, String> param4) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: iload 0
      // 01: ifeq 20
      // 04: aload 4
      // 06: aload 1
      // 07: invokeinterface io/reactivex/functions/Function.apply (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0c: checkcast java/lang/String
      // 0f: astore 1
      // 10: aload 1
      // 11: ifnonnull 16
      // 14: iload 2
      // 15: ireturn
      // 16: aload 1
      // 17: invokestatic java/lang/Integer.parseInt (Ljava/lang/String;)I
      // 1a: istore 3
      // 1b: iload 3
      // 1c: ireturn
      // 1d: astore 1
      // 1e: iload 2
      // 1f: ireturn
      // 20: iload 3
      // 21: ireturn
   }

   public static void shutdown() {
      ScheduledExecutorService var0 = PURGE_THREAD.getAndSet(null);
      if (var0 != null) {
         var0.shutdownNow();
      }

      POOLS.clear();
   }

   public static void start() {
      tryStart(PURGE_ENABLED);
   }

   static void tryPutIntoPool(boolean var0, ScheduledExecutorService var1) {
      if (var0 && var1 instanceof ScheduledThreadPoolExecutor) {
         ScheduledThreadPoolExecutor var2 = (ScheduledThreadPoolExecutor)var1;
         POOLS.put(var2, var1);
      }
   }

   static void tryStart(boolean var0) {
      if (var0) {
         while (true) {
            AtomicReference var4 = PURGE_THREAD;
            ScheduledExecutorService var3 = (ScheduledExecutorService)var4.get();
            if (var3 != null) {
               return;
            }

            ScheduledExecutorService var2 = Executors.newScheduledThreadPool(1, new RxThreadFactory("RxSchedulerPurge"));
            if (ExternalSyntheticBackportWithForwarding0.m(var4, var3, var2)) {
               SchedulerPoolFactory.ScheduledTask var5 = new SchedulerPoolFactory.ScheduledTask();
               int var1 = PURGE_PERIOD_SECONDS;
               var2.scheduleAtFixedRate(var5, var1, var1, TimeUnit.SECONDS);
               return;
            }

            var2.shutdownNow();
         }
      }
   }

   static final class ScheduledTask implements Runnable {
      @Override
      public void run() {
         for (ScheduledThreadPoolExecutor var2 : new ArrayList<>(SchedulerPoolFactory.POOLS.keySet())) {
            if (var2.isShutdown()) {
               SchedulerPoolFactory.POOLS.remove(var2);
            } else {
               var2.purge();
            }
         }
      }
   }

   static final class SystemPropertyAccessor implements Function<String, String> {
      public String apply(String var1) throws Exception {
         return System.getProperty(var1);
      }
   }
}
