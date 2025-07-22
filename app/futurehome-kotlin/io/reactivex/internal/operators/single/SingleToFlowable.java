package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import org.reactivestreams.Subscriber;

public final class SingleToFlowable<T> extends Flowable<T> {
   final SingleSource<? extends T> source;

   public SingleToFlowable(SingleSource<? extends T> var1) {
      this.source = var1;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new SingleToFlowable.SingleToFlowableObserver<>(var1));
   }

   static final class SingleToFlowableObserver<T> extends DeferredScalarSubscription<T> implements SingleObserver<T> {
      private static final long serialVersionUID = 187782011903685568L;
      Disposable upstream;

      SingleToFlowableObserver(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.dispose();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.complete((T)var1);
      }
   }
}
