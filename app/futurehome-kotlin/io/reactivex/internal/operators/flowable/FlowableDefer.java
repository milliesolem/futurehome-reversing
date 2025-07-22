package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableDefer<T> extends Flowable<T> {
   final Callable<? extends Publisher<? extends T>> supplier;

   public FlowableDefer(Callable<? extends Publisher<? extends T>> var1) {
      this.supplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Publisher var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.supplier.call(), "The publisher supplied is null");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      var2.subscribe(var1);
   }
}
