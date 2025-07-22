package io.reactivex.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DisposableSingleObserver<T> implements SingleObserver<T>, Disposable {
   final AtomicReference<Disposable> upstream = new AtomicReference<>();

   @Override
   public final void dispose() {
      DisposableHelper.dispose(this.upstream);
   }

   @Override
   public final boolean isDisposed() {
      boolean var1;
      if (this.upstream.get() == DisposableHelper.DISPOSED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void onStart() {
   }

   @Override
   public final void onSubscribe(Disposable var1) {
      if (EndConsumerHelper.setOnce(this.upstream, var1, this.getClass())) {
         this.onStart();
      }
   }
}
