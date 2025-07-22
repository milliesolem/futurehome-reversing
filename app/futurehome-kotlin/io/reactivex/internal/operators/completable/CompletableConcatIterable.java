package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableConcatIterable extends Completable {
   final Iterable<? extends CompletableSource> sources;

   public CompletableConcatIterable(Iterable<? extends CompletableSource> var1) {
      this.sources = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(CompletableObserver var1) {
      Iterator var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.sources.iterator(), "The iterator returned is null");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      CompletableConcatIterable.ConcatInnerObserver var5 = new CompletableConcatIterable.ConcatInnerObserver(var1, var2);
      var1.onSubscribe(var5.sd);
      var5.next();
   }

   static final class ConcatInnerObserver extends AtomicInteger implements CompletableObserver {
      private static final long serialVersionUID = -7965400327305809232L;
      final CompletableObserver downstream;
      final SequentialDisposable sd;
      final Iterator<? extends CompletableSource> sources;

      ConcatInnerObserver(CompletableObserver var1, Iterator<? extends CompletableSource> var2) {
         this.downstream = var1;
         this.sources = var2;
         this.sd = new SequentialDisposable();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void next() {
         if (!this.sd.isDisposed()) {
            if (this.getAndIncrement() == 0) {
               Iterator var2 = this.sources;

               while (!this.sd.isDisposed()) {
                  boolean var1;
                  try {
                     var1 = var2.hasNext();
                  } catch (Throwable var8) {
                     Exceptions.throwIfFatal(var8);
                     this.downstream.onError(var8);
                     return;
                  }

                  if (!var1) {
                     this.downstream.onComplete();
                     return;
                  }

                  CompletableSource var3;
                  try {
                     var3 = ObjectHelper.requireNonNull((CompletableSource)var2.next(), "The CompletableSource returned is null");
                  } catch (Throwable var9) {
                     Exceptions.throwIfFatal(var9);
                     this.downstream.onError(var9);
                     return;
                  }

                  var3.subscribe(this);
                  if (this.decrementAndGet() == 0) {
                     return;
                  }
               }
            }
         }
      }

      @Override
      public void onComplete() {
         this.next();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.sd.replace(var1);
      }
   }
}
