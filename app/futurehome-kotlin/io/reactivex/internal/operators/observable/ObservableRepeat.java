package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRepeat<T> extends AbstractObservableWithUpstream<T, T> {
   final long count;

   public ObservableRepeat(Observable<T> var1, long var2) {
      super(var1);
      this.count = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SequentialDisposable var6 = new SequentialDisposable();
      var1.onSubscribe(var6);
      long var4 = this.count;
      long var2 = Long.MAX_VALUE;
      if (var4 != Long.MAX_VALUE) {
         var2 = var4 - 1L;
      }

      new ObservableRepeat.RepeatObserver<>(var1, var2, var6, this.source).subscribeNext();
   }

   static final class RepeatObserver<T> extends AtomicInteger implements Observer<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Observer<? super T> downstream;
      long remaining;
      final SequentialDisposable sd;
      final ObservableSource<? extends T> source;

      RepeatObserver(Observer<? super T> var1, long var2, SequentialDisposable var4, ObservableSource<? extends T> var5) {
         this.downstream = var1;
         this.sd = var4;
         this.source = var5;
         this.remaining = var2;
      }

      @Override
      public void onComplete() {
         long var1 = this.remaining;
         if (var1 != Long.MAX_VALUE) {
            this.remaining = var1 - 1L;
         }

         if (var1 != 0L) {
            this.subscribeNext();
         } else {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.sd.replace(var1);
      }

      void subscribeNext() {
         if (this.getAndIncrement() == 0) {
            int var1 = 1;

            int var2;
            do {
               if (this.sd.isDisposed()) {
                  return;
               }

               this.source.subscribe(this);
               var2 = this.addAndGet(-var1);
               var1 = var2;
            } while (var2 != 0);
         }
      }
   }
}
