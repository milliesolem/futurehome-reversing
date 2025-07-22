package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRetryBiPredicate<T> extends AbstractObservableWithUpstream<T, T> {
   final BiPredicate<? super Integer, ? super Throwable> predicate;

   public ObservableRetryBiPredicate(Observable<T> var1, BiPredicate<? super Integer, ? super Throwable> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SequentialDisposable var2 = new SequentialDisposable();
      var1.onSubscribe(var2);
      new ObservableRetryBiPredicate.RetryBiObserver<>(var1, this.predicate, var2, this.source).subscribeNext();
   }

   static final class RetryBiObserver<T> extends AtomicInteger implements Observer<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Observer<? super T> downstream;
      final BiPredicate<? super Integer, ? super Throwable> predicate;
      int retries;
      final ObservableSource<? extends T> source;
      final SequentialDisposable upstream;

      RetryBiObserver(
         Observer<? super T> var1, BiPredicate<? super Integer, ? super Throwable> var2, SequentialDisposable var3, ObservableSource<? extends T> var4
      ) {
         this.downstream = var1;
         this.upstream = var3;
         this.source = var4;
         this.predicate = var2;
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         boolean var3;
         try {
            BiPredicate var4 = this.predicate;
            int var2 = this.retries + 1;
            this.retries = var2;
            var3 = var4.test(var2, var1);
         } catch (Throwable var6) {
            Exceptions.throwIfFatal(var6);
            this.downstream.onError(new CompositeException(var1, var6));
            return;
         }

         if (!var3) {
            this.downstream.onError(var1);
         } else {
            this.subscribeNext();
         }
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
               if (this.upstream.isDisposed()) {
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
