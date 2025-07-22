package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class EmptyCompletableObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable, LambdaConsumerIntrospection {
   private static final long serialVersionUID = -7545121636549663526L;

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean hasCustomOnError() {
      return false;
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.get() == DisposableHelper.DISPOSED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      this.lazySet(DisposableHelper.DISPOSED);
   }

   @Override
   public void onError(Throwable var1) {
      this.lazySet(DisposableHelper.DISPOSED);
      RxJavaPlugins.onError(new OnErrorNotImplementedException(var1));
   }

   @Override
   public void onSubscribe(Disposable var1) {
      DisposableHelper.setOnce(this, var1);
   }
}
