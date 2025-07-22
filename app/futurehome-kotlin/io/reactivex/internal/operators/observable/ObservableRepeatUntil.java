package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRepeatUntil<T> extends AbstractObservableWithUpstream<T, T> {
   final BooleanSupplier until;

   public ObservableRepeatUntil(Observable<T> var1, BooleanSupplier var2) {
      super(var1);
      this.until = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SequentialDisposable var2 = new SequentialDisposable();
      var1.onSubscribe(var2);
      new ObservableRepeatUntil.RepeatUntilObserver<>(var1, this.until, var2, this.source).subscribeNext();
   }

   static final class RepeatUntilObserver<T> extends AtomicInteger implements Observer<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Observer<? super T> downstream;
      final ObservableSource<? extends T> source;
      final BooleanSupplier stop;
      final SequentialDisposable upstream;

      RepeatUntilObserver(Observer<? super T> var1, BooleanSupplier var2, SequentialDisposable var3, ObservableSource<? extends T> var4) {
         this.downstream = var1;
         this.upstream = var3;
         this.source = var4;
         this.stop = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         boolean var1;
         try {
            var1 = this.stop.getAsBoolean();
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(var4);
            return;
         }

         if (var1) {
            this.downstream.onComplete();
         } else {
            this.subscribeNext();
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
         this.upstream.replace(var1);
      }

      void subscribeNext() {
         if (this.getAndIncrement() == 0) {
            int var1 = 1;

            int var2;
            do {
               this.source.subscribe(this);
               var2 = this.addAndGet(-var1);
               var1 = var2;
            } while (var2 != 0);
         }
      }
   }
}
