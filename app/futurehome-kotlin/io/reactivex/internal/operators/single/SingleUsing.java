package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
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
import java.util.concurrent.atomic.AtomicReference;

public final class SingleUsing<T, U> extends Single<T> {
   final Consumer<? super U> disposer;
   final boolean eager;
   final Callable<U> resourceSupplier;
   final Function<? super U, ? extends SingleSource<? extends T>> singleFunction;

   public SingleUsing(Callable<U> var1, Function<? super U, ? extends SingleSource<? extends T>> var2, Consumer<? super U> var3, boolean var4) {
      this.resourceSupplier = var1;
      this.singleFunction = var2;
      this.disposer = var3;
      this.eager = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      Object var4;
      try {
         var4 = this.resourceSupplier.call();
      } catch (Throwable var21) {
         Exceptions.throwIfFatal(var21);
         EmptyDisposable.error(var21, var1);
         return;
      }

      SingleSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.singleFunction.apply((U)var4), "The singleFunction returned a null SingleSource");
      } catch (Throwable var24) {
         Object var3;
         Exceptions.throwIfFatal(var24);
         var3 = var24;
         label115:
         if (this.eager) {
            try {
               this.disposer.accept((U)var4);
            } catch (Throwable var23) {
               Exceptions.throwIfFatal(var23);
               var3 = new CompositeException(var24, var23);
               break label115;
            }

            var3 = var24;
         }

         EmptyDisposable.error((Throwable)var3, var1);
         if (!this.eager) {
            try {
               this.disposer.accept((U)var4);
            } catch (Throwable var22) {
               Exceptions.throwIfFatal(var22);
               RxJavaPlugins.onError(var22);
               return;
            }
         }

         return;
      }

      var2.subscribe(new SingleUsing.UsingSingleObserver<>(var1, var4, this.eager, this.disposer));
   }

   static final class UsingSingleObserver<T, U> extends AtomicReference<Object> implements SingleObserver<T>, Disposable {
      private static final long serialVersionUID = -5331524057054083935L;
      final Consumer<? super U> disposer;
      final SingleObserver<? super T> downstream;
      final boolean eager;
      Disposable upstream;

      UsingSingleObserver(SingleObserver<? super T> var1, U var2, boolean var3, Consumer<? super U> var4) {
         super(var2);
         this.downstream = var1;
         this.eager = var3;
         this.disposer = var4;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
         this.disposeAfter();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void disposeAfter() {
         Object var1 = this.getAndSet(this);
         if (var1 != this) {
            try {
               this.disposer.accept((U)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         Object var2;
         this.upstream = DisposableHelper.DISPOSED;
         var2 = var1;
         label36:
         if (this.eager) {
            var2 = this.getAndSet(this);
            if (var2 == this) {
               return;
            }

            try {
               this.disposer.accept((U)var2);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               var2 = new CompositeException(var1, var4);
               break label36;
            }

            var2 = var1;
         }

         this.downstream.onError((Throwable)var2);
         if (!this.eager) {
            this.disposeAfter();
         }
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
         this.upstream = DisposableHelper.DISPOSED;
         if (this.eager) {
            Object var2 = this.getAndSet(this);
            if (var2 == this) {
               return;
            }

            try {
               this.disposer.accept((U)var2);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.downstream.onError(var4);
               return;
            }
         }

         this.downstream.onSuccess((T)var1);
         if (!this.eager) {
            this.disposeAfter();
         }
      }
   }
}
