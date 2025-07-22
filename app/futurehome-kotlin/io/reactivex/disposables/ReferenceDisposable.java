package io.reactivex.disposables;

import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

abstract class ReferenceDisposable<T> extends AtomicReference<T> implements Disposable {
   private static final long serialVersionUID = 6537757548749041217L;

   ReferenceDisposable(T var1) {
      super(ObjectHelper.requireNonNull((T)var1, "value is null"));
   }

   @Override
   public final void dispose() {
      if (this.get() != null) {
         Object var1 = this.getAndSet(null);
         if (var1 != null) {
            this.onDisposed((T)var1);
         }
      }
   }

   @Override
   public final boolean isDisposed() {
      boolean var1;
      if (this.get() == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected abstract void onDisposed(T var1);
}
