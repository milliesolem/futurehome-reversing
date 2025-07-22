package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableError<T> extends Flowable<T> {
   final Callable<? extends Throwable> errorSupplier;

   public FlowableError(Callable<? extends Throwable> var1) {
      this.errorSupplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Throwable var2;
      label17:
      try {
         var2 = ObjectHelper.requireNonNull(
            this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         var2 = var4;
         Exceptions.throwIfFatal(var4);
         break label17;
      }

      EmptySubscription.error(var2, var1);
   }
}
