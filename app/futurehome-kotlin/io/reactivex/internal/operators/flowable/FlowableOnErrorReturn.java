package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableOnErrorReturn<T> extends AbstractFlowableWithUpstream<T, T> {
   final Function<? super Throwable, ? extends T> valueSupplier;

   public FlowableOnErrorReturn(Flowable<T> var1, Function<? super Throwable, ? extends T> var2) {
      super(var1);
      this.valueSupplier = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableOnErrorReturn.OnErrorReturnSubscriber<>(var1, this.valueSupplier));
   }

   static final class OnErrorReturnSubscriber<T> extends SinglePostCompleteSubscriber<T, T> {
      private static final long serialVersionUID = -3740826063558713822L;
      final Function<? super Throwable, ? extends T> valueSupplier;

      OnErrorReturnSubscriber(Subscriber<? super T> var1, Function<? super Throwable, ? extends T> var2) {
         super(var1);
         this.valueSupplier = var2;
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         Object var2;
         try {
            var2 = ObjectHelper.requireNonNull((T)this.valueSupplier.apply(var1), "The valueSupplier returned a null value");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(new CompositeException(var1, var4));
            return;
         }

         this.complete((T)var2);
      }

      public void onNext(T var1) {
         this.produced++;
         this.downstream.onNext(var1);
      }
   }
}
