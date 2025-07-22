package io.reactivex.internal.operators.single;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDelayWithObservable<T, U> extends Single<T> {
   final ObservableSource<U> other;
   final SingleSource<T> source;

   public SingleDelayWithObservable(SingleSource<T> var1, ObservableSource<U> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.other.subscribe(new SingleDelayWithObservable.OtherSubscriber<>(var1, this.source));
   }

   static final class OtherSubscriber<T, U> extends AtomicReference<Disposable> implements Observer<U>, Disposable {
      private static final long serialVersionUID = -8565274649390031272L;
      boolean done;
      final SingleObserver<? super T> downstream;
      final SingleSource<T> source;

      OtherSubscriber(SingleObserver<? super T> var1, SingleSource<T> var2) {
         this.downstream = var1;
         this.source = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.source.subscribe(new ResumeSingleObserver<>(this, this.downstream));
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(U var1) {
         this.get().dispose();
         this.onComplete();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.set(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }
   }
}
