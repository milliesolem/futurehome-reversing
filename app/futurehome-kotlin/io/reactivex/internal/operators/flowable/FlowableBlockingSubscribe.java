package io.reactivex.internal.operators.flowable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.BlockingSubscriber;
import io.reactivex.internal.subscribers.BoundedSubscriber;
import io.reactivex.internal.subscribers.LambdaSubscriber;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.BlockingIgnoringReceiver;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.concurrent.LinkedBlockingQueue;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableBlockingSubscribe {
   private FlowableBlockingSubscribe() {
      throw new IllegalStateException("No instances!");
   }

   public static <T> void subscribe(Publisher<? extends T> var0) {
      BlockingIgnoringReceiver var1 = new BlockingIgnoringReceiver();
      LambdaSubscriber var2 = new LambdaSubscriber(Functions.emptyConsumer(), var1, var1, Functions.REQUEST_MAX);
      var0.subscribe(var2);
      BlockingHelper.awaitForComplete(var1, var2);
      Throwable var3 = var1.error;
      if (var3 != null) {
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   public static <T> void subscribe(Publisher<? extends T> var0, Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      subscribe(var0, new LambdaSubscriber<>(var1, var2, var3, Functions.REQUEST_MAX));
   }

   public static <T> void subscribe(Publisher<? extends T> var0, Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, int var4) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.verifyPositive(var4, "number > 0 required");
      subscribe(var0, new BoundedSubscriber<>(var1, var2, var3, Functions.boundedConsumer(var4), var4));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static <T> void subscribe(Publisher<? extends T> var0, Subscriber<? super T> var1) {
      LinkedBlockingQueue var5 = new LinkedBlockingQueue();
      BlockingSubscriber var4 = new BlockingSubscriber(var5);
      var0.subscribe(var4);

      boolean var2;
      do {
         try {
            if (var4.isCancelled()) {
               break;
            }
         } catch (InterruptedException var11) {
            var4.cancel();
            var1.onError(var11);
            break;
         }

         Object var3;
         try {
            var3 = var5.poll();
         } catch (InterruptedException var10) {
            var4.cancel();
            var1.onError(var10);
            break;
         }

         Object var12 = var3;
         if (var3 == null) {
            try {
               if (var4.isCancelled()) {
                  break;
               }
            } catch (InterruptedException var9) {
               var4.cancel();
               var1.onError(var9);
               break;
            }

            try {
               BlockingHelper.verifyNonBlocking();
               var12 = var5.take();
            } catch (InterruptedException var8) {
               var4.cancel();
               var1.onError(var8);
               break;
            }
         }

         try {
            if (var4.isCancelled()) {
               break;
            }
         } catch (InterruptedException var7) {
            var4.cancel();
            var1.onError(var7);
            break;
         }

         try {
            if (var12 == BlockingSubscriber.TERMINATED) {
               break;
            }

            var2 = NotificationLite.acceptFull(var12, var1);
         } catch (InterruptedException var6) {
            var4.cancel();
            var1.onError(var6);
            break;
         }
      } while (!var2);
   }
}
