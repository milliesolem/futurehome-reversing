package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeUnsubscribeOn<T> extends AbstractMaybeWithUpstream<T, T> {
   final Scheduler scheduler;

   public MaybeUnsubscribeOn(MaybeSource<T> var1, Scheduler var2) {
      super(var1);
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeUnsubscribeOn.UnsubscribeOnMaybeObserver<>(var1, this.scheduler));
   }

   static final class UnsubscribeOnMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
      private static final long serialVersionUID = 3256698449646456986L;
      final MaybeObserver<? super T> downstream;
      Disposable ds;
      final Scheduler scheduler;

      UnsubscribeOnMaybeObserver(MaybeObserver<? super T> var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
      }

      @Override
      public void dispose() {
         Disposable var1 = this.getAndSet(DisposableHelper.DISPOSED);
         if (var1 != DisposableHelper.DISPOSED) {
            this.ds = var1;
            this.scheduler.scheduleDirect(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }

      @Override
      public void run() {
         this.ds.dispose();
      }
   }
}
