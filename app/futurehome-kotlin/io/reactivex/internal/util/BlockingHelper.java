package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NonBlockingThread;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CountDownLatch;

public final class BlockingHelper {
   private BlockingHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static void awaitForComplete(CountDownLatch var0, Disposable var1) {
      if (var0.getCount() != 0L) {
         try {
            verifyNonBlocking();
            var0.await();
         } catch (InterruptedException var2) {
            var1.dispose();
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for subscription to complete.", var2);
         }
      }
   }

   public static void verifyNonBlocking() {
      if (RxJavaPlugins.isFailOnNonBlockingScheduler() && (Thread.currentThread() instanceof NonBlockingThread || RxJavaPlugins.onBeforeBlocking())) {
         StringBuilder var0 = new StringBuilder("Attempt to block on a Scheduler ");
         var0.append(Thread.currentThread().getName());
         var0.append(" that doesn't support blocking operators as they may lead to deadlock");
         throw new IllegalStateException(var0.toString());
      }
   }
}
