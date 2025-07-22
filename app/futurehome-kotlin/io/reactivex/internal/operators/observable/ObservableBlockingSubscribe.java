package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BlockingObserver;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.BlockingIgnoringReceiver;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.concurrent.LinkedBlockingQueue;

public final class ObservableBlockingSubscribe {
   private ObservableBlockingSubscribe() {
      throw new IllegalStateException("No instances!");
   }

   public static <T> void subscribe(ObservableSource<? extends T> var0) {
      BlockingIgnoringReceiver var1 = new BlockingIgnoringReceiver();
      LambdaObserver var2 = new LambdaObserver(Functions.emptyConsumer(), var1, var1, Functions.emptyConsumer());
      var0.subscribe(var2);
      BlockingHelper.awaitForComplete(var1, var2);
      Throwable var3 = var1.error;
      if (var3 != null) {
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   public static <T> void subscribe(ObservableSource<? extends T> var0, Observer<? super T> var1) {
      LinkedBlockingQueue var4 = new LinkedBlockingQueue();
      BlockingObserver var3 = new BlockingObserver(var4);
      var1.onSubscribe(var3);
      var0.subscribe(var3);

      while (!var3.isDisposed()) {
         Object var2 = var4.poll();
         Object var6 = var2;
         if (var2 == null) {
            try {
               var6 = var4.take();
            } catch (InterruptedException var5) {
               var3.dispose();
               var1.onError(var5);
               return;
            }
         }

         if (var3.isDisposed() || var6 == BlockingObserver.TERMINATED || NotificationLite.acceptFull(var6, var1)) {
            break;
         }
      }
   }

   public static <T> void subscribe(ObservableSource<? extends T> var0, Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      subscribe(var0, new LambdaObserver<>(var1, var2, var3, Functions.emptyConsumer()));
   }
}
