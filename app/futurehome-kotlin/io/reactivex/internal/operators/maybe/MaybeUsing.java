package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
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

public final class MaybeUsing<T, D> extends Maybe<T> {
   final boolean eager;
   final Consumer<? super D> resourceDisposer;
   final Callable<? extends D> resourceSupplier;
   final Function<? super D, ? extends MaybeSource<? extends T>> sourceSupplier;

   public MaybeUsing(Callable<? extends D> var1, Function<? super D, ? extends MaybeSource<? extends T>> var2, Consumer<? super D> var3, boolean var4) {
      this.resourceSupplier = var1;
      this.sourceSupplier = var2;
      this.resourceDisposer = var3;
      this.eager = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      Object var3;
      try {
         var3 = this.resourceSupplier.call();
      } catch (Throwable var21) {
         Exceptions.throwIfFatal(var21);
         EmptyDisposable.error(var21, var1);
         return;
      }

      MaybeSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.sourceSupplier.apply((D)var3), "The sourceSupplier returned a null MaybeSource");
      } catch (Throwable var23) {
         Exceptions.throwIfFatal(var23);
         if (this.eager) {
            try {
               this.resourceDisposer.accept((D)var3);
            } catch (Throwable var20) {
               Exceptions.throwIfFatal(var20);
               EmptyDisposable.error(new CompositeException(var23, var20), var1);
               return;
            }
         }

         EmptyDisposable.error(var23, var1);
         if (!this.eager) {
            try {
               this.resourceDisposer.accept((D)var3);
            } catch (Throwable var22) {
               Exceptions.throwIfFatal(var22);
               RxJavaPlugins.onError(var22);
               return;
            }
         }

         return;
      }

      var2.subscribe(new MaybeUsing.UsingObserver<>(var1, var3, this.resourceDisposer, this.eager));
   }

   static final class UsingObserver<T, D> extends AtomicReference<Object> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = -674404550052917487L;
      final Consumer<? super D> disposer;
      final MaybeObserver<? super T> downstream;
      final boolean eager;
      Disposable upstream;

      UsingObserver(MaybeObserver<? super T> var1, D var2, Consumer<? super D> var3, boolean var4) {
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
               this.disposer.accept((D)var1);
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
               this.disposer.accept((D)var1);
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
               this.disposer.accept((D)var2);
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
               this.disposer.accept((D)var2);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.downstream.onError(var4);
               return;
            }
         }

         this.downstream.onSuccess((T)var1);
         if (!this.eager) {
            this.disposeResourceAfter();
         }
      }
   }
}
