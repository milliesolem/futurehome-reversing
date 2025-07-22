package io.reactivex.internal.operators.flowable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;

public final class FlowableReduceWithSingle<T, R> extends Single<R> {
   final BiFunction<R, ? super T, R> reducer;
   final Callable<R> seedSupplier;
   final Publisher<T> source;

   public FlowableReduceWithSingle(Publisher<T> var1, Callable<R> var2, BiFunction<R, ? super T, R> var3) {
      this.source = var1;
      this.seedSupplier = var2;
      this.reducer = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      Object var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seedSupplier returned a null value");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableReduceSeedSingle.ReduceSeedObserver<>(var1, this.reducer, (R)var2));
   }
}
