package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class MaybeTakeUntilPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
   final Publisher<U> other;

   public MaybeTakeUntilPublisher(MaybeSource<T> var1, Publisher<U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeTakeUntilPublisher.TakeUntilMainMaybeObserver var2 = new MaybeTakeUntilPublisher.TakeUntilMainMaybeObserver(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class TakeUntilMainMaybeObserver<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = -2187421758664251153L;
      final MaybeObserver<? super T> downstream;
      final MaybeTakeUntilPublisher.TakeUntilMainMaybeObserver.TakeUntilOtherMaybeObserver<U> other;

      TakeUntilMainMaybeObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
         this.other = new MaybeTakeUntilPublisher.TakeUntilMainMaybeObserver.TakeUntilOtherMaybeObserver<>(this);
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         SubscriptionHelper.cancel(this.other);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         SubscriptionHelper.cancel(this.other);
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         SubscriptionHelper.cancel(this.other);
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         SubscriptionHelper.cancel(this.other);
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onSuccess((T)var1);
         }
      }

      void otherComplete() {
         if (DisposableHelper.dispose(this)) {
            this.downstream.onComplete();
         }
      }

      void otherError(Throwable var1) {
         if (DisposableHelper.dispose(this)) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      static final class TakeUntilOtherMaybeObserver<U> extends AtomicReference<Subscription> implements FlowableSubscriber<U> {
         private static final long serialVersionUID = -1266041316834525931L;
         final MaybeTakeUntilPublisher.TakeUntilMainMaybeObserver<?, U> parent;

         TakeUntilOtherMaybeObserver(MaybeTakeUntilPublisher.TakeUntilMainMaybeObserver<?, U> var1) {
            this.parent = var1;
         }

         public void onComplete() {
            this.parent.otherComplete();
         }

         public void onError(Throwable var1) {
            this.parent.otherError(var1);
         }

         public void onNext(Object var1) {
            SubscriptionHelper.cancel(this);
            this.parent.otherComplete();
         }

         @Override
         public void onSubscribe(Subscription var1) {
            SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
         }
      }
   }
}
