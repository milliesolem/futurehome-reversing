package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDoOnDispose<T> extends Single<T> {
   final Action onDispose;
   final SingleSource<T> source;

   public SingleDoOnDispose(SingleSource<T> var1, Action var2) {
      this.source = var1;
      this.onDispose = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoOnDispose.DoOnDisposeObserver<>(var1, this.onDispose));
   }

   static final class DoOnDisposeObserver<T> extends AtomicReference<Action> implements SingleObserver<T>, Disposable {
      private static final long serialVersionUID = -8583764624474935784L;
      final SingleObserver<? super T> downstream;
      Disposable upstream;

      DoOnDisposeObserver(SingleObserver<? super T> var1, Action var2) {
         this.downstream = var1;
         this.lazySet(var2);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void dispose() {
         Action var1 = this.getAndSet(null);
         if (var1 != null) {
            label21:
            try {
               var1.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               break label21;
            }

            this.upstream.dispose();
         }
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

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }
}
