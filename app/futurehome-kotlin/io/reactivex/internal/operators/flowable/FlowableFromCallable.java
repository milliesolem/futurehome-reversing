package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableFromCallable<T> extends Flowable<T> implements Callable<T> {
   final Callable<? extends T> callable;

   public FlowableFromCallable(Callable<? extends T> var1) {
      this.callable = var1;
   }

   @Override
   public T call() throws Exception {
      return ObjectHelper.requireNonNull((T)this.callable.call(), "The callable returned a null value");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      DeferredScalarSubscription var2 = new DeferredScalarSubscription(var1);
      var1.onSubscribe(var2);

      Object var3;
      try {
         var3 = ObjectHelper.requireNonNull((T)this.callable.call(), "The callable returned a null value");
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         if (var2.isCancelled()) {
            RxJavaPlugins.onError(var5);
         } else {
            var1.onError(var5);
         }

         return;
      }

      var2.complete(var3);
   }
}
