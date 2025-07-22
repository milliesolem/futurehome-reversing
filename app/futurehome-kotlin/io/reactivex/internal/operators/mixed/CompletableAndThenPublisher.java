package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class CompletableAndThenPublisher<R> extends Flowable<R> {
   final Publisher<? extends R> other;
   final CompletableSource source;

   public CompletableAndThenPublisher(CompletableSource var1, Publisher<? extends R> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new CompletableAndThenPublisher.AndThenPublisherSubscriber(var1, this.other));
   }

   static final class AndThenPublisherSubscriber<R> extends AtomicReference<Subscription> implements FlowableSubscriber<R>, CompletableObserver, Subscription {
      private static final long serialVersionUID = -8948264376121066672L;
      final Subscriber<? super R> downstream;
      Publisher<? extends R> other;
      final AtomicLong requested;
      Disposable upstream;

      AndThenPublisherSubscriber(Subscriber<? super R> var1, Publisher<? extends R> var2) {
         this.downstream = var1;
         this.other = var2;
         this.requested = new AtomicLong();
      }

      public void cancel() {
         this.upstream.dispose();
         SubscriptionHelper.cancel(this);
      }

      @Override
      public void onComplete() {
         Publisher var1 = this.other;
         if (var1 == null) {
            this.downstream.onComplete();
         } else {
            this.other = null;
            var1.subscribe(this);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(R var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this, this.requested, var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this, this.requested, var1);
      }
   }
}
