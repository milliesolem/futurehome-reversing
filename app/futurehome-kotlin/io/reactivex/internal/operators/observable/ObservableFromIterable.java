package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;
import java.util.Iterator;

public final class ObservableFromIterable<T> extends Observable<T> {
   final Iterable<? extends T> source;

   public ObservableFromIterable(Iterable<? extends T> var1) {
      this.source = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      Iterator var3;
      try {
         var3 = this.source.iterator();
      } catch (Throwable var9) {
         Exceptions.throwIfFatal(var9);
         EmptyDisposable.error(var9, var1);
         return;
      }

      boolean var2;
      try {
         var2 = var3.hasNext();
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         EmptyDisposable.error(var8, var1);
         return;
      }

      if (!var2) {
         EmptyDisposable.complete(var1);
      } else {
         ObservableFromIterable.FromIterableDisposable var10 = new ObservableFromIterable.FromIterableDisposable(var1, var3);
         var1.onSubscribe(var10);
         if (!var10.fusionMode) {
            var10.run();
         }
      }
   }

   static final class FromIterableDisposable<T> extends BasicQueueDisposable<T> {
      boolean checkNext;
      volatile boolean disposed;
      boolean done;
      final Observer<? super T> downstream;
      boolean fusionMode;
      final Iterator<? extends T> it;

      FromIterableDisposable(Observer<? super T> var1, Iterator<? extends T> var2) {
         this.downstream = var1;
         this.it = var2;
      }

      @Override
      public void clear() {
         this.done = true;
      }

      @Override
      public void dispose() {
         this.disposed = true;
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public boolean isEmpty() {
         return this.done;
      }

      @Override
      public T poll() {
         if (this.done) {
            return null;
         } else {
            if (this.checkNext) {
               if (!this.it.hasNext()) {
                  this.done = true;
                  return null;
               }
            } else {
               this.checkNext = true;
            }

            return ObjectHelper.requireNonNull((T)this.it.next(), "The iterator returned a null value");
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 1) != 0) {
            this.fusionMode = true;
            return 1;
         } else {
            return 0;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void run() {
         while (!this.isDisposed()) {
            Object var2;
            try {
               var2 = ObjectHelper.requireNonNull((T)this.it.next(), "The iterator returned a null value");
            } catch (Throwable var8) {
               Exceptions.throwIfFatal(var8);
               this.downstream.onError(var8);
               return;
            }

            this.downstream.onNext((T)var2);
            if (this.isDisposed()) {
               return;
            }

            boolean var1;
            try {
               var1 = this.it.hasNext();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.downstream.onError(var7);
               return;
            }

            if (!var1) {
               if (!this.isDisposed()) {
                  this.downstream.onComplete();
               }

               return;
            }
         }
      }
   }
}
