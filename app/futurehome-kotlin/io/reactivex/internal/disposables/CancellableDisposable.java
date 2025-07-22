package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CancellableDisposable extends AtomicReference<Cancellable> implements Disposable {
   private static final long serialVersionUID = 5718521705281392066L;

   public CancellableDisposable(Cancellable var1) {
      super(var1);
   }

   @Override
   public void dispose() {
      if (this.get() != null) {
         Cancellable var1 = this.getAndSet(null);
         if (var1 != null) {
            try {
               var1.cancel();
            } catch (Exception var2) {
               Exceptions.throwIfFatal(var2);
               RxJavaPlugins.onError(var2);
            }
         }
      }
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.get() == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
