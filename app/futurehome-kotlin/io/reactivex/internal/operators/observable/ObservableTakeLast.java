package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.ArrayDeque;

public final class ObservableTakeLast<T> extends AbstractObservableWithUpstream<T, T> {
   final int count;

   public ObservableTakeLast(ObservableSource<T> var1, int var2) {
      super(var1);
      this.count = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableTakeLast.TakeLastObserver<>(var1, this.count));
   }

   static final class TakeLastObserver<T> extends ArrayDeque<T> implements Observer<T>, Disposable {
      private static final long serialVersionUID = 7240042530241604978L;
      volatile boolean cancelled;
      final int count;
      final Observer<? super T> downstream;
      Disposable upstream;

      TakeLastObserver(Observer<? super T> var1, int var2) {
         this.downstream = var1;
         this.count = var2;
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         Observer var2 = this.downstream;

         while (!this.cancelled) {
            Object var1 = this.poll();
            if (var1 == null) {
               if (!this.cancelled) {
                  var2.onComplete();
               }

               return;
            }

            var2.onNext(var1);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         if (this.count == this.size()) {
            this.poll();
         }

         this.offer((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
