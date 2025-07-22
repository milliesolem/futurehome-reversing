package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSubscribeOn<T> extends AbstractMaybeWithUpstream<T, T> {
   final Scheduler scheduler;

   public MaybeSubscribeOn(MaybeSource<T> var1, Scheduler var2) {
      super(var1);
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeSubscribeOn.SubscribeOnMaybeObserver var2 = new MaybeSubscribeOn.SubscribeOnMaybeObserver(var1);
      var1.onSubscribe(var2);
      var2.task.replace(this.scheduler.scheduleDirect(new MaybeSubscribeOn.SubscribeTask<>(var2, this.source)));
   }

   static final class SubscribeOnMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 8571289934935992137L;
      final MaybeObserver<? super T> downstream;
      final SequentialDisposable task;

      SubscribeOnMaybeObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
         this.task = new SequentialDisposable();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         this.task.dispose();
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
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }

   static final class SubscribeTask<T> implements Runnable {
      final MaybeObserver<? super T> observer;
      final MaybeSource<T> source;

      SubscribeTask(MaybeObserver<? super T> var1, MaybeSource<T> var2) {
         this.observer = var1;
         this.source = var2;
      }

      @Override
      public void run() {
         this.source.subscribe(this.observer);
      }
   }
}
