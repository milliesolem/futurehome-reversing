package io.reactivex.disposables;

import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SerialDisposable implements Disposable {
   final AtomicReference<Disposable> resource;

   public SerialDisposable() {
      this.resource = new AtomicReference<>();
   }

   public SerialDisposable(Disposable var1) {
      this.resource = new AtomicReference<>(var1);
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this.resource);
   }

   public Disposable get() {
      Disposable var2 = this.resource.get();
      Disposable var1 = var2;
      if (var2 == DisposableHelper.DISPOSED) {
         var1 = Disposables.disposed();
      }

      return var1;
   }

   @Override
   public boolean isDisposed() {
      return DisposableHelper.isDisposed(this.resource.get());
   }

   public boolean replace(Disposable var1) {
      return DisposableHelper.replace(this.resource, var1);
   }

   public boolean set(Disposable var1) {
      return DisposableHelper.set(this.resource, var1);
   }
}
