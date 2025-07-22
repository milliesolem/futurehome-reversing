package io.reactivex.android.plugins;

import io.reactivex.Scheduler;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import java.util.concurrent.Callable;

public final class RxAndroidPlugins {
   private static volatile Function<Callable<Scheduler>, Scheduler> onInitMainThreadHandler;
   private static volatile Function<Scheduler, Scheduler> onMainThreadHandler;

   private RxAndroidPlugins() {
      throw new AssertionError("No instances.");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static <T, R> R apply(Function<T, R> var0, T var1) {
      try {
         return (R)var0.apply(var1);
      } catch (Throwable var3) {
         throw Exceptions.propagate(var3);
      }
   }

   static Scheduler applyRequireNonNull(Function<Callable<Scheduler>, Scheduler> var0, Callable<Scheduler> var1) {
      Scheduler var2 = apply(var0, var1);
      if (var2 != null) {
         return var2;
      } else {
         throw new NullPointerException("Scheduler Callable returned null");
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static Scheduler callRequireNonNull(Callable<Scheduler> var0) {
      try {
         var7 = (Scheduler)var0.call();
      } catch (Throwable var6) {
         throw Exceptions.propagate(var6);
      }

      if (var7 != null) {
         return var7;
      } else {
         try {
            NullPointerException var8 = new NullPointerException("Scheduler Callable returned null");
            throw var8;
         } catch (Throwable var5) {
            throw Exceptions.propagate(var5);
         }
      }
   }

   public static Function<Callable<Scheduler>, Scheduler> getInitMainThreadSchedulerHandler() {
      return onInitMainThreadHandler;
   }

   public static Function<Scheduler, Scheduler> getOnMainThreadSchedulerHandler() {
      return onMainThreadHandler;
   }

   public static Scheduler initMainThreadScheduler(Callable<Scheduler> var0) {
      if (var0 != null) {
         Function var1 = onInitMainThreadHandler;
         return var1 == null ? callRequireNonNull(var0) : applyRequireNonNull(var1, var0);
      } else {
         throw new NullPointerException("scheduler == null");
      }
   }

   public static Scheduler onMainThreadScheduler(Scheduler var0) {
      if (var0 != null) {
         Function var1 = onMainThreadHandler;
         return var1 == null ? var0 : apply(var1, var0);
      } else {
         throw new NullPointerException("scheduler == null");
      }
   }

   public static void reset() {
      setInitMainThreadSchedulerHandler(null);
      setMainThreadSchedulerHandler(null);
   }

   public static void setInitMainThreadSchedulerHandler(Function<Callable<Scheduler>, Scheduler> var0) {
      onInitMainThreadHandler = var0;
   }

   public static void setMainThreadSchedulerHandler(Function<Scheduler, Scheduler> var0) {
      onMainThreadHandler = var0;
   }
}
