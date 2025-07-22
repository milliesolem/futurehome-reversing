package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableTake<T> extends AbstractObservableWithUpstream<T, T> {
   final long limit;

   public ObservableTake(ObservableSource<T> var1, long var2) {
      super(var1);
      this.limit = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableTake.TakeObserver<>(var1, this.limit));
   }

   static final class TakeObserver<T> implements Observer<T>, Disposable {
      boolean done;
      final Observer<? super T> downstream;
      long remaining;
      Disposable upstream;

      TakeObserver(Observer<? super T> var1, long var2) {
         this.downstream = var1;
         this.remaining = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.upstream.dispose();
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.upstream.dispose();
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            long var5 = this.remaining;
            long var3 = var5 - 1L;
            this.remaining = var3;
            if (var5 > 0L) {
               boolean var2;
               if (var3 == 0L) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               this.downstream.onNext((T)var1);
               if (var2) {
                  this.onComplete();
               }
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (this.remaining == 0L) {
               this.done = true;
               var1.dispose();
               EmptyDisposable.complete(this.downstream);
            } else {
               this.downstream.onSubscribe(this);
            }
         }
      }
   }
}
