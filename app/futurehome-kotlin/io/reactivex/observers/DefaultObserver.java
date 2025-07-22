package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

public abstract class DefaultObserver<T> implements Observer<T> {
   private Disposable upstream;

   protected final void cancel() {
      Disposable var1 = this.upstream;
      this.upstream = DisposableHelper.DISPOSED;
      var1.dispose();
   }

   protected void onStart() {
   }

   @Override
   public final void onSubscribe(Disposable var1) {
      if (EndConsumerHelper.validate(this.upstream, var1, this.getClass())) {
         this.upstream = var1;
         this.onStart();
      }
   }
}
