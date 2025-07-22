package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class SingleDelayWithPublisher<T, U> extends Single<T> {
   final Publisher<U> other;
   final SingleSource<T> source;

   public SingleDelayWithPublisher(SingleSource<T> var1, Publisher<U> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.other.subscribe(new SingleDelayWithPublisher.OtherSubscriber<>(var1, this.source));
   }

   static final class OtherSubscriber<T, U> extends AtomicReference<Disposable> implements FlowableSubscriber<U>, Disposable {
      private static final long serialVersionUID = -8565274649390031272L;
      boolean done;
      final SingleObserver<? super T> downstream;
      final SingleSource<T> source;
      Subscription upstream;

      OtherSubscriber(SingleObserver<? super T> var1, SingleSource<T> var2) {
         this.downstream = var1;
         this.source = var2;
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.source.subscribe(new ResumeSingleObserver<>(this, this.downstream));
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      public void onNext(U var1) {
         this.upstream.cancel();
         this.onComplete();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }
   }
}
