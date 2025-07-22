package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableTakeUntilCompletable extends Completable {
   final CompletableSource other;
   final Completable source;

   public CompletableTakeUntilCompletable(Completable var1, CompletableSource var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableTakeUntilCompletable.TakeUntilMainObserver var2 = new CompletableTakeUntilCompletable.TakeUntilMainObserver(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class TakeUntilMainObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
      private static final long serialVersionUID = 3533011714830024923L;
      final CompletableObserver downstream;
      final AtomicBoolean once;
      final CompletableTakeUntilCompletable.TakeUntilMainObserver.OtherObserver other;

      TakeUntilMainObserver(CompletableObserver var1) {
         this.downstream = var1;
         this.other = new CompletableTakeUntilCompletable.TakeUntilMainObserver.OtherObserver(this);
         this.once = new AtomicBoolean();
      }

      @Override
      public void dispose() {
         if (this.once.compareAndSet(false, true)) {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.other);
         }
      }

      void innerComplete() {
         if (this.once.compareAndSet(false, true)) {
            DisposableHelper.dispose(this);
            this.downstream.onComplete();
         }
      }

      void innerError(Throwable var1) {
         if (this.once.compareAndSet(false, true)) {
            DisposableHelper.dispose(this);
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.once.get();
      }

      @Override
      public void onComplete() {
         if (this.once.compareAndSet(false, true)) {
            DisposableHelper.dispose(this.other);
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.once.compareAndSet(false, true)) {
            DisposableHelper.dispose(this.other);
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      static final class OtherObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = 5176264485428790318L;
         final CompletableTakeUntilCompletable.TakeUntilMainObserver parent;

         OtherObserver(CompletableTakeUntilCompletable.TakeUntilMainObserver var1) {
            this.parent = var1;
         }

         @Override
         public void onComplete() {
            this.parent.innerComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.innerError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
