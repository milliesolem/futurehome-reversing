package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import java.util.Iterator;

public final class SingleFlatMapIterableObservable<T, R> extends Observable<R> {
   final Function<? super T, ? extends Iterable<? extends R>> mapper;
   final SingleSource<T> source;

   public SingleFlatMapIterableObservable(SingleSource<T> var1, Function<? super T, ? extends Iterable<? extends R>> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      this.source.subscribe(new SingleFlatMapIterableObservable.FlatMapIterableObserver<>(var1, this.mapper));
   }

   static final class FlatMapIterableObserver<T, R> extends BasicIntQueueDisposable<R> implements SingleObserver<T> {
      private static final long serialVersionUID = -8938804753851907758L;
      volatile boolean cancelled;
      final Observer<? super R> downstream;
      volatile Iterator<? extends R> it;
      final Function<? super T, ? extends Iterable<? extends R>> mapper;
      boolean outputFused;
      Disposable upstream;

      FlatMapIterableObserver(Observer<? super R> var1, Function<? super T, ? extends Iterable<? extends R>> var2) {
         this.downstream = var1;
         this.mapper = var2;
      }

      @Override
      public void clear() {
         this.it = null;
      }

      @Override
      public void dispose() {
         this.cancelled = true;
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public boolean isEmpty() {
         boolean var1;
         if (this.it == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         Observer var3 = this.downstream;

         boolean var2;
         Iterator var4;
         try {
            var4 = this.mapper.apply((T)var1).iterator();
            var2 = var4.hasNext();
         } catch (Throwable var16) {
            Exceptions.throwIfFatal(var16);
            this.downstream.onError(var16);
            return;
         }

         if (!var2) {
            var3.onComplete();
         } else if (this.outputFused) {
            this.it = var4;
            var3.onNext(null);
            var3.onComplete();
         } else {
            while (!this.cancelled) {
               try {
                  var1 = var4.next();
               } catch (Throwable var14) {
                  Exceptions.throwIfFatal(var14);
                  var3.onError(var14);
                  return;
               }

               var3.onNext(var1);
               if (this.cancelled) {
                  return;
               }

               try {
                  var2 = var4.hasNext();
               } catch (Throwable var15) {
                  Exceptions.throwIfFatal(var15);
                  var3.onError(var15);
                  return;
               }

               if (!var2) {
                  var3.onComplete();
                  return;
               }
            }
         }
      }

      @Override
      public R poll() throws Exception {
         Iterator var1 = this.it;
         if (var1 != null) {
            Object var2 = ObjectHelper.requireNonNull(var1.next(), "The iterator returned a null value");
            if (!var1.hasNext()) {
               this.it = null;
            }

            return (R)var2;
         } else {
            return null;
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.outputFused = true;
            return 2;
         } else {
            return 0;
         }
      }
   }
}
