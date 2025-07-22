package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

public final class HalfSerializer {
   private HalfSerializer() {
      throw new IllegalStateException("No instances!");
   }

   public static void onComplete(Observer<?> var0, AtomicInteger var1, AtomicThrowable var2) {
      if (var1.getAndIncrement() == 0) {
         Throwable var3 = var2.terminate();
         if (var3 != null) {
            var0.onError(var3);
         } else {
            var0.onComplete();
         }
      }
   }

   public static void onComplete(Subscriber<?> var0, AtomicInteger var1, AtomicThrowable var2) {
      if (var1.getAndIncrement() == 0) {
         Throwable var3 = var2.terminate();
         if (var3 != null) {
            var0.onError(var3);
         } else {
            var0.onComplete();
         }
      }
   }

   public static void onError(Observer<?> var0, Throwable var1, AtomicInteger var2, AtomicThrowable var3) {
      if (var3.addThrowable(var1)) {
         if (var2.getAndIncrement() == 0) {
            var0.onError(var3.terminate());
         }
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   public static void onError(Subscriber<?> var0, Throwable var1, AtomicInteger var2, AtomicThrowable var3) {
      if (var3.addThrowable(var1)) {
         if (var2.getAndIncrement() == 0) {
            var0.onError(var3.terminate());
         }
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   public static <T> void onNext(Observer<? super T> var0, T var1, AtomicInteger var2, AtomicThrowable var3) {
      if (var2.get() == 0 && var2.compareAndSet(0, 1)) {
         var0.onNext(var1);
         if (var2.decrementAndGet() != 0) {
            var1 = var3.terminate();
            if (var1 != null) {
               var0.onError(var1);
            } else {
               var0.onComplete();
            }
         }
      }
   }

   public static <T> void onNext(Subscriber<? super T> var0, T var1, AtomicInteger var2, AtomicThrowable var3) {
      if (var2.get() == 0 && var2.compareAndSet(0, 1)) {
         var0.onNext(var1);
         if (var2.decrementAndGet() != 0) {
            var1 = var3.terminate();
            if (var1 != null) {
               var0.onError(var1);
            } else {
               var0.onComplete();
            }
         }
      }
   }
}
