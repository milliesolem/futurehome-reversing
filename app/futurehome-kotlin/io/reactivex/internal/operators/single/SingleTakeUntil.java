package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class SingleTakeUntil<T, U> extends Single<T> {
   final Publisher<U> other;
   final SingleSource<T> source;

   public SingleTakeUntil(SingleSource<T> var1, Publisher<U> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleTakeUntil.TakeUntilMainObserver var2 = new SingleTakeUntil.TakeUntilMainObserver(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class TakeUntilMainObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
      private static final long serialVersionUID = -622603812305745221L;
      final SingleObserver<? super T> downstream;
      final SingleTakeUntil.TakeUntilOtherSubscriber other;

      TakeUntilMainObserver(SingleObserver<? super T> var1) {
         this.downstream = var1;
         this.other = new SingleTakeUntil.TakeUntilOtherSubscriber(this);
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         this.other.dispose();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onError(Throwable var1) {
         this.other.dispose();
         if (this.get() != DisposableHelper.DISPOSED && this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
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
         this.other.dispose();
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onSuccess((T)var1);
         }
      }

      void otherError(Throwable var1) {
         if (this.get() != DisposableHelper.DISPOSED) {
            Disposable var2 = this.getAndSet(DisposableHelper.DISPOSED);
            if (var2 != DisposableHelper.DISPOSED) {
               if (var2 != null) {
                  var2.dispose();
               }

               this.downstream.onError(var1);
               return;
            }
         }

         RxJavaPlugins.onError(var1);
      }
   }

   static final class TakeUntilOtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
      private static final long serialVersionUID = 5170026210238877381L;
      final SingleTakeUntil.TakeUntilMainObserver<?> parent;

      TakeUntilOtherSubscriber(SingleTakeUntil.TakeUntilMainObserver<?> var1) {
         this.parent = var1;
      }

      public void dispose() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         if (this.get() != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.otherError(new CancellationException());
         }
      }

      public void onError(Throwable var1) {
         this.parent.otherError(var1);
      }

      public void onNext(Object var1) {
         if (SubscriptionHelper.cancel(this)) {
            this.parent.otherError(new CancellationException());
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }
}
