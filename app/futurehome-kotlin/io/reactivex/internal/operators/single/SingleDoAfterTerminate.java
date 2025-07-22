package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class SingleDoAfterTerminate<T> extends Single<T> {
   final Action onAfterTerminate;
   final SingleSource<T> source;

   public SingleDoAfterTerminate(SingleSource<T> var1, Action var2) {
      this.source = var1;
      this.onAfterTerminate = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoAfterTerminate.DoAfterTerminateObserver<>(var1, this.onAfterTerminate));
   }

   static final class DoAfterTerminateObserver<T> implements SingleObserver<T>, Disposable {
      final SingleObserver<? super T> downstream;
      final Action onAfterTerminate;
      Disposable upstream;

      DoAfterTerminateObserver(SingleObserver<? super T> var1, Action var2) {
         this.downstream = var1;
         this.onAfterTerminate = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private void onAfterTerminate() {
         try {
            this.onAfterTerminate.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
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
         this.onAfterTerminate();
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
         this.onAfterTerminate();
      }
   }
}
