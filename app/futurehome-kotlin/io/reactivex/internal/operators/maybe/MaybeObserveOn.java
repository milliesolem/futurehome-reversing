package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeObserveOn<T> extends AbstractMaybeWithUpstream<T, T> {
   final Scheduler scheduler;

   public MaybeObserveOn(MaybeSource<T> var1, Scheduler var2) {
      super(var1);
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeObserveOn.ObserveOnMaybeObserver<>(var1, this.scheduler));
   }

   static final class ObserveOnMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
      private static final long serialVersionUID = 8571289934935992137L;
      final MaybeObserver<? super T> downstream;
      Throwable error;
      final Scheduler scheduler;
      T value;

      ObserveOnMaybeObserver(MaybeObserver<? super T> var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
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
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.value = (T)var1;
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
      }

      @Override
      public void run() {
         Throwable var1 = this.error;
         if (var1 != null) {
            this.error = null;
            this.downstream.onError(var1);
         } else {
            var1 = this.value;
            if (var1 != null) {
               this.value = null;
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }
   }
}
