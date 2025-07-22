package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableRetryPredicate<T> extends AbstractObservableWithUpstream<T, T> {
   final long count;
   final Predicate<? super Throwable> predicate;

   public ObservableRetryPredicate(Observable<T> var1, long var2, Predicate<? super Throwable> var4) {
      super(var1);
      this.predicate = var4;
      this.count = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SequentialDisposable var2 = new SequentialDisposable();
      var1.onSubscribe(var2);
      new ObservableRetryPredicate.RepeatObserver<>(var1, this.count, this.predicate, var2, this.source).subscribeNext();
   }

   static final class RepeatObserver<T> extends AtomicInteger implements Observer<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Observer<? super T> downstream;
      final Predicate<? super Throwable> predicate;
      long remaining;
      final ObservableSource<? extends T> source;
      final SequentialDisposable upstream;

      RepeatObserver(Observer<? super T> var1, long var2, Predicate<? super Throwable> var4, SequentialDisposable var5, ObservableSource<? extends T> var6) {
         this.downstream = var1;
         this.upstream = var5;
         this.source = var6;
         this.predicate = var4;
         this.remaining = var2;
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         long var2 = this.remaining;
         if (var2 != Long.MAX_VALUE) {
            this.remaining = var2 - 1L;
         }

         if (var2 == 0L) {
            this.downstream.onError(var1);
         } else {
            boolean var4;
            try {
               var4 = this.predicate.test(var1);
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.downstream.onError(new CompositeException(var1, var7));
               return;
            }

            if (!var4) {
               this.downstream.onError(var1);
               return;
            }

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
