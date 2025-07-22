package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public abstract class BasicFuseableObserver<T, R> implements Observer<T>, QueueDisposable<R> {
   protected boolean done;
   protected final Observer<? super R> downstream;
   protected QueueDisposable<T> qd;
   protected int sourceMode;
   protected Disposable upstream;

   public BasicFuseableObserver(Observer<? super R> var1) {
      this.downstream = var1;
   }

   protected void afterDownstream() {
   }

   protected boolean beforeDownstream() {
      return true;
   }

   @Override
   public void clear() {
      this.qd.clear();
   }

   @Override
   public void dispose() {
      this.upstream.dispose();
   }

   protected final void fail(Throwable var1) {
      Exceptions.throwIfFatal(var1);
      this.upstream.dispose();
      this.onError(var1);
   }

   @Override
   public boolean isDisposed() {
      return this.upstream.isDisposed();
   }

   @Override
   public boolean isEmpty() {
      return this.qd.isEmpty();
   }

   @Override
   public final boolean offer(R var1) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public final boolean offer(R var1, R var2) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public void onComplete() {
      if (!this.done) {
         this.done = true;
         this.downstream.onComplete();
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
   public final void onSubscribe(Disposable var1) {
      if (DisposableHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         if (var1 instanceof QueueDisposable) {
            this.qd = (QueueDisposable<T>)var1;
         }

         if (this.beforeDownstream()) {
            this.downstream.onSubscribe(this);
            this.afterDownstream();
         }
      }
   }

   protected final int transitiveBoundaryFusion(int var1) {
      QueueDisposable var2 = this.qd;
      if (var2 != null && (var1 & 4) == 0) {
         var1 = var2.requestFusion(var1);
         if (var1 != 0) {
            this.sourceMode = var1;
         }

         return var1;
      } else {
         return 0;
      }
   }
}
