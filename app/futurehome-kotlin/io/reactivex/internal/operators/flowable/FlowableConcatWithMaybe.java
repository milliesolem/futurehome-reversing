package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

public final class FlowableConcatWithMaybe<T> extends AbstractFlowableWithUpstream<T, T> {
   final MaybeSource<? extends T> other;

   public FlowableConcatWithMaybe(Flowable<T> var1, MaybeSource<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableConcatWithMaybe.ConcatWithSubscriber<>(var1, this.other));
   }

   static final class ConcatWithSubscriber<T> extends SinglePostCompleteSubscriber<T, T> implements MaybeObserver<T> {
      private static final long serialVersionUID = -7346385463600070225L;
      boolean inMaybe;
      MaybeSource<? extends T> other;
      final AtomicReference<Disposable> otherDisposable;

      ConcatWithSubscriber(Subscriber<? super T> var1, MaybeSource<? extends T> var2) {
         super(var1);
         this.other = var2;
         this.otherDisposable = new AtomicReference<>();
      }

      @Override
      public void cancel() {
         super.cancel();
         DisposableHelper.dispose(this.otherDisposable);
      }

      @Override
      public void onComplete() {
         if (this.inMaybe) {
            this.downstream.onComplete();
         } else {
            this.inMaybe = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            MaybeSource var1 = this.other;
            this.other = null;
            var1.subscribe(this);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.produced++;
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.otherDisposable, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.complete((T)var1);
      }
   }
}
