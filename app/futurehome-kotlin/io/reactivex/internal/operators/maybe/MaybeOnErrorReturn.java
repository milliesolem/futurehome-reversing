package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;

public final class MaybeOnErrorReturn<T> extends AbstractMaybeWithUpstream<T, T> {
   final Function<? super Throwable, ? extends T> valueSupplier;

   public MaybeOnErrorReturn(MaybeSource<T> var1, Function<? super Throwable, ? extends T> var2) {
      super(var1);
      this.valueSupplier = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeOnErrorReturn.OnErrorReturnMaybeObserver<>(var1, this.valueSupplier));
   }

   static final class OnErrorReturnMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      Disposable upstream;
      final Function<? super Throwable, ? extends T> valueSupplier;

      OnErrorReturnMaybeObserver(MaybeObserver<? super T> var1, Function<? super Throwable, ? extends T> var2) {
         this.downstream = var1;
         this.valueSupplier = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         Object var2;
         try {
            var2 = ObjectHelper.requireNonNull((T)this.valueSupplier.apply(var1), "The valueSupplier returned a null value");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(new CompositeException(var1, var4));
            return;
         }

         this.downstream.onSuccess((T)var2);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }
}
