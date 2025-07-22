package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
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

public final class CompletableUsing<R> extends Completable {
   final Function<? super R, ? extends CompletableSource> completableFunction;
   final Consumer<? super R> disposer;
   final boolean eager;
   final Callable<R> resourceSupplier;

   public CompletableUsing(Callable<R> var1, Function<? super R, ? extends CompletableSource> var2, Consumer<? super R> var3, boolean var4) {
      this.resourceSupplier = var1;
      this.completableFunction = var2;
      this.disposer = var3;
      this.eager = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(CompletableObserver var1) {
      Object var2;
      try {
         var2 = this.resourceSupplier.call();
      } catch (Throwable var21) {
         Exceptions.throwIfFatal(var21);
         EmptyDisposable.error(var21, var1);
         return;
      }

      CompletableSource var3;
      try {
         var3 = ObjectHelper.requireNonNull(this.completableFunction.apply((R)var2), "The completableFunction returned a null CompletableSource");
      } catch (Throwable var23) {
         Exceptions.throwIfFatal(var23);
         if (this.eager) {
            try {
               this.disposer.accept((R)var2);
            } catch (Throwable var20) {
               Exceptions.throwIfFatal(var20);
               EmptyDisposable.error(new CompositeException(var23, var20), var1);
               return;
            }
         }

         EmptyDisposable.error(var23, var1);
         if (!this.eager) {
            try {
               this.disposer.accept((R)var2);
            } catch (Throwable var22) {
               Exceptions.throwIfFatal(var22);
               RxJavaPlugins.onError(var22);
               return;
            }
         }

         return;
      }

      var3.subscribe(new CompletableUsing.UsingObserver<>(var1, var2, this.disposer, this.eager));
   }

   static final class UsingObserver<R> extends AtomicReference<Object> implements CompletableObserver, Disposable {
      private static final long serialVersionUID = -674404550052917487L;
      final Consumer<? super R> disposer;
      final CompletableObserver downstream;
      final boolean eager;
      Disposable upstream;

      UsingObserver(CompletableObserver var1, R var2, Consumer<? super R> var3, boolean var4) {
         super(var2);
         this.downstream = var1;
         this.disposer = var3;
         this.eager = var4;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
         this.disposeResourceAfter();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void disposeResourceAfter() {
         Object var1 = this.getAndSet(this);
         if (var1 != this) {
            try {
               this.disposer.accept((R)var1);
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
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         if (this.eager) {
            Object var1 = this.getAndSet(this);
            if (var1 == this) {
               return;
            }

            try {
               this.disposer.accept((R)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.downstream.onError(var3);
               return;
            }
         }

         this.downstream.onComplete();
         if (!this.eager) {
            this.disposeResourceAfter();
         }
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
               this.disposer.accept((R)var2);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               var2 = new CompositeException(var1, var4);
               break label36;
            }

            var2 = var1;
         }

         this.downstream.onError((Throwable)var2);
         if (!this.eager) {
            this.disposeResourceAfter();
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
