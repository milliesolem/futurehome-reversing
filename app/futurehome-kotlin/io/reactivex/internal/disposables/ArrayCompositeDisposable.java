package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ArrayCompositeDisposable extends AtomicReferenceArray<Disposable> implements Disposable {
   private static final long serialVersionUID = 2746389416410565408L;

   public ArrayCompositeDisposable(int var1) {
      super(var1);
   }

   @Override
   public void dispose() {
      int var1 = 0;
      if (this.get(0) != DisposableHelper.DISPOSED) {
         for (int var2 = this.length(); var1 < var2; var1++) {
            if (this.get(var1) != DisposableHelper.DISPOSED) {
               Disposable var3 = this.getAndSet(var1, DisposableHelper.DISPOSED);
               if (var3 != DisposableHelper.DISPOSED && var3 != null) {
                  var3.dispose();
               }
            }
         }
      }
   }

   @Override
   public boolean isDisposed() {
      boolean var1 = false;
      if (this.get(0) == DisposableHelper.DISPOSED) {
         var1 = true;
      }

      return var1;
   }

   public Disposable replaceResource(int var1, Disposable var2) {
      Disposable var3;
      do {
         var3 = this.get(var1);
         if (var3 == DisposableHelper.DISPOSED) {
            var2.dispose();
            return null;
         }
      } while (!this.compareAndSet(var1, var3, var2));

      return var3;
   }

   public boolean setResource(int var1, Disposable var2) {
      Disposable var3;
      do {
         var3 = this.get(var1);
         if (var3 == DisposableHelper.DISPOSED) {
            var2.dispose();
            return false;
         }
      } while (!this.compareAndSet(var1, var3, var2));

      if (var3 != null) {
         var3.dispose();
      }

      return true;
   }
}
