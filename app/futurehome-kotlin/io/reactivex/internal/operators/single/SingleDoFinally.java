package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class SingleDoFinally<T> extends Single<T> {
   final Action onFinally;
   final SingleSource<T> source;

   public SingleDoFinally(SingleSource<T> var1, Action var2) {
      this.source = var1;
      this.onFinally = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoFinally.DoFinallyObserver<>(var1, this.onFinally));
   }

   static final class DoFinallyObserver<T> extends AtomicInteger implements SingleObserver<T>, Disposable {
      private static final long serialVersionUID = 4109457741734051389L;
      final SingleObserver<? super T> downstream;
      final Action onFinally;
      Disposable upstream;

      DoFinallyObserver(SingleObserver<? super T> var1, Action var2) {
         this.downstream = var1;
         this.onFinally = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.runFinally();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
         this.runFinally();
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
         this.runFinally();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void runFinally() {
         if (this.compareAndSet(0, 1)) {
            try {
               this.onFinally.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }
   }
}
