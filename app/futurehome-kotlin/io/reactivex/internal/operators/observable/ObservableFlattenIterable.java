package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableFlattenIterable<T, R> extends AbstractObservableWithUpstream<T, R> {
   final Function<? super T, ? extends Iterable<? extends R>> mapper;

   public ObservableFlattenIterable(ObservableSource<T> var1, Function<? super T, ? extends Iterable<? extends R>> var2) {
      super(var1);
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      this.source.subscribe(new ObservableFlattenIterable.FlattenIterableObserver<>(var1, this.mapper));
   }

   static final class FlattenIterableObserver<T, R> implements Observer<T>, Disposable {
      final Observer<? super R> downstream;
      final Function<? super T, ? extends Iterable<? extends R>> mapper;
      Disposable upstream;

      FlattenIterableObserver(Observer<? super R> var1, Function<? super T, ? extends Iterable<? extends R>> var2) {
         this.downstream = var1;
         this.mapper = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         if (this.upstream != DisposableHelper.DISPOSED) {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.upstream == DisposableHelper.DISPOSED) {
            RxJavaPlugins.onError(var1);
         } else {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (this.upstream != DisposableHelper.DISPOSED) {
            try {
               var1 = this.mapper.apply((T)var1).iterator();
            } catch (Throwable var15) {
               Exceptions.throwIfFatal(var15);
               this.upstream.dispose();
               this.onError(var15);
               return;
            }

            Observer var3 = this.downstream;

            while (true) {
               boolean var2;
               try {
                  var2 = var1.hasNext();
               } catch (Throwable var14) {
                  Exceptions.throwIfFatal(var14);
                  this.upstream.dispose();
                  this.onError(var14);
                  return;
               }

               if (!var2) {
                  break;
               }

               Object var4;
               try {
                  var4 = ObjectHelper.requireNonNull(var1.next(), "The iterator returned a null value");
               } catch (Throwable var16) {
                  Exceptions.throwIfFatal(var16);
                  this.upstream.dispose();
                  this.onError(var16);
                  break;
               }

               var3.onNext(var4);
            }
         }
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
