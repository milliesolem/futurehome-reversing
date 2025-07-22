package io.reactivex.disposables;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

final class FutureDisposable extends AtomicReference<Future<?>> implements Disposable {
   private static final long serialVersionUID = 6545242830671168775L;
   private final boolean allowInterrupt;

   FutureDisposable(Future<?> var1, boolean var2) {
      super(var1);
      this.allowInterrupt = var2;
   }

   @Override
   public void dispose() {
      Future var1 = this.getAndSet(null);
      if (var1 != null) {
         var1.cancel(this.allowInterrupt);
      }
   }

   @Override
   public boolean isDisposed() {
      Future var2 = this.get();
      boolean var1;
      if (var2 != null && !var2.isDone()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
