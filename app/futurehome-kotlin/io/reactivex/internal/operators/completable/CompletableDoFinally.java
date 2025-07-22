package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableDoFinally extends Completable {
   final Action onFinally;
   final CompletableSource source;

   public CompletableDoFinally(CompletableSource var1, Action var2) {
      this.source = var1;
      this.onFinally = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableDoFinally.DoFinallyObserver(var1, this.onFinally));
   }

   static final class DoFinallyObserver extends AtomicInteger implements CompletableObserver, Disposable {
      private static final long serialVersionUID = 4109457741734051389L;
      final CompletableObserver downstream;
      final Action onFinally;
      Disposable upstream;

      DoFinallyObserver(CompletableObserver var1, Action var2) {
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
      public void onComplete() {
         this.downstream.onComplete();
         this.runFinally();
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
