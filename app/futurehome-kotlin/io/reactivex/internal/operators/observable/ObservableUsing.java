package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableUsing<T, D> extends Observable<T> {
   final Consumer<? super D> disposer;
   final boolean eager;
   final Callable<? extends D> resourceSupplier;
   final Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier;

   public ObservableUsing(Callable<? extends D> var1, Function<? super D, ? extends ObservableSource<? extends T>> var2, Consumer<? super D> var3, boolean var4) {
      this.resourceSupplier = var1;
      this.sourceSupplier = var2;
      this.disposer = var3;
      this.eager = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      Object var3;
      try {
         var3 = this.resourceSupplier.call();
      } catch (Throwable var15) {
         Exceptions.throwIfFatal(var15);
         EmptyDisposable.error(var15, var1);
         return;
      }

      ObservableSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.sourceSupplier.apply((D)var3), "The sourceSupplier returned a null ObservableSource");
      } catch (Throwable var14) {
         Exceptions.throwIfFatal(var14);

         try {
            this.disposer.accept((D)var3);
         } catch (Throwable var13) {
            Exceptions.throwIfFatal(var13);
            EmptyDisposable.error(new CompositeException(var14, var13), var1);
            return;
         }

         EmptyDisposable.error(var14, var1);
         return;
      }

      var2.subscribe(new ObservableUsing.UsingObserver<>(var1, var3, this.disposer, this.eager));
   }

   static final class UsingObserver<T, D> extends AtomicBoolean implements Observer<T>, Disposable {
      private static final long serialVersionUID = 5904473792286235046L;
      final Consumer<? super D> disposer;
      final Observer<? super T> downstream;
      final boolean eager;
      final D resource;
      Disposable upstream;

      UsingObserver(Observer<? super T> var1, D var2, Consumer<? super D> var3, boolean var4) {
         this.downstream = var1;
         this.resource = (D)var2;
         this.disposer = var3;
         this.eager = var4;
      }

      @Override
      public void dispose() {
         this.disposeAfter();
         this.upstream.dispose();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void disposeAfter() {
         if (this.compareAndSet(false, true)) {
            try {
               this.disposer.accept(this.resource);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.get();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         if (this.eager) {
            if (this.compareAndSet(false, true)) {
               try {
                  this.disposer.accept(this.resource);
               } catch (Throwable var3) {
                  Exceptions.throwIfFatal(var3);
                  this.downstream.onError(var3);
                  return;
               }
            }

            this.upstream.dispose();
            this.downstream.onComplete();
         } else {
            this.downstream.onComplete();
            this.upstream.dispose();
            this.disposeAfter();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (this.eager) {
            Object var2;
            var2 = var1;
            label29:
            if (this.compareAndSet(false, true)) {
               try {
                  this.disposer.accept(this.resource);
               } catch (Throwable var4) {
                  Exceptions.throwIfFatal(var4);
                  var2 = new CompositeException(var1, var4);
                  break label29;
               }

               var2 = var1;
            }

            this.upstream.dispose();
            this.downstream.onError((Throwable)var2);
         } else {
            this.downstream.onError(var1);
            this.upstream.dispose();
            this.disposeAfter();
         }
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
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
