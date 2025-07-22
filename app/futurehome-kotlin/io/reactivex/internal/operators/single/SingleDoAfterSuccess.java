package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class SingleDoAfterSuccess<T> extends Single<T> {
   final Consumer<? super T> onAfterSuccess;
   final SingleSource<T> source;

   public SingleDoAfterSuccess(SingleSource<T> var1, Consumer<? super T> var2) {
      this.source = var1;
      this.onAfterSuccess = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoAfterSuccess.DoAfterObserver<>(var1, this.onAfterSuccess));
   }

   static final class DoAfterObserver<T> implements SingleObserver<T>, Disposable {
      final SingleObserver<? super T> downstream;
      final Consumer<? super T> onAfterSuccess;
      Disposable upstream;

      DoAfterObserver(SingleObserver<? super T> var1, Consumer<? super T> var2) {
         this.downstream = var1;
         this.onAfterSuccess = var2;
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
      public void onError(Throwable var1) {
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
         this.downstream.onSuccess((T)var1);

         try {
            this.onAfterSuccess.accept((T)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }
   }
}
