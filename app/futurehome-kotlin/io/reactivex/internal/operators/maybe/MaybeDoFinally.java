package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class MaybeDoFinally<T> extends AbstractMaybeWithUpstream<T, T> {
   final Action onFinally;

   public MaybeDoFinally(MaybeSource<T> var1, Action var2) {
      super(var1);
      this.onFinally = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeDoFinally.DoFinallyObserver<>(var1, this.onFinally));
   }

   static final class DoFinallyObserver<T> extends AtomicInteger implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 4109457741734051389L;
      final MaybeObserver<? super T> downstream;
      final Action onFinally;
      Disposable upstream;

      DoFinallyObserver(MaybeObserver<? super T> var1, Action var2) {
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
