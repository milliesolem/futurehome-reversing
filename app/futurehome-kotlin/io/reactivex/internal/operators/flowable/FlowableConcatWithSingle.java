package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

public final class FlowableConcatWithSingle<T> extends AbstractFlowableWithUpstream<T, T> {
   final SingleSource<? extends T> other;

   public FlowableConcatWithSingle(Flowable<T> var1, SingleSource<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableConcatWithSingle.ConcatWithSubscriber<>(var1, this.other));
   }

   static final class ConcatWithSubscriber<T> extends SinglePostCompleteSubscriber<T, T> implements SingleObserver<T> {
      private static final long serialVersionUID = -7346385463600070225L;
      SingleSource<? extends T> other;
      final AtomicReference<Disposable> otherDisposable;

      ConcatWithSubscriber(Subscriber<? super T> var1, SingleSource<? extends T> var2) {
         super(var1);
         this.other = var2;
         this.otherDisposable = new AtomicReference<>();
      }

      @Override
      public void cancel() {
         super.cancel();
         DisposableHelper.dispose(this.otherDisposable);
      }

      public void onComplete() {
         this.upstream = SubscriptionHelper.CANCELLED;
         SingleSource var1 = this.other;
         this.other = null;
         var1.subscribe(this);
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
