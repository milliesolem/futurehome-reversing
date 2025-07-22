package io.reactivex.internal.operators.flowable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatWithCompletable<T> extends AbstractFlowableWithUpstream<T, T> {
   final CompletableSource other;

   public FlowableConcatWithCompletable(Flowable<T> var1, CompletableSource var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableConcatWithCompletable.ConcatWithSubscriber<>(var1, this.other));
   }

   static final class ConcatWithSubscriber<T> extends AtomicReference<Disposable> implements FlowableSubscriber<T>, CompletableObserver, Subscription {
      private static final long serialVersionUID = -7346385463600070225L;
      final Subscriber<? super T> downstream;
      boolean inCompletable;
      CompletableSource other;
      Subscription upstream;

      ConcatWithSubscriber(Subscriber<? super T> var1, CompletableSource var2) {
         this.downstream = var1;
         this.other = var2;
      }

      public void cancel() {
         this.upstream.cancel();
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         if (this.inCompletable) {
            this.downstream.onComplete();
         } else {
            this.inCompletable = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            CompletableSource var1 = this.other;
            this.other = null;
            var1.subscribe(this);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }
   }
}
