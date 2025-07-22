package io.reactivex.internal.disposables;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public enum DisposableHelper implements Disposable {
   DISPOSED;
   private static final DisposableHelper[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      DisposableHelper var0 = new DisposableHelper();
      DISPOSED = var0;
      $VALUES = new DisposableHelper[]{var0};
   }

   public static boolean dispose(AtomicReference<Disposable> var0) {
      Disposable var2 = (Disposable)var0.get();
      DisposableHelper var1 = DISPOSED;
      if (var2 != var1) {
         Disposable var3 = var0.getAndSet(var1);
         if (var3 != var1) {
            if (var3 != null) {
               var3.dispose();
            }

            return true;
         }
      }

      return false;
   }

   public static boolean isDisposed(Disposable var0) {
      boolean var1;
      if (var0 == DISPOSED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean replace(AtomicReference<Disposable> var0, Disposable var1) {
      Disposable var2;
      do {
         var2 = (Disposable)var0.get();
         if (var2 == DISPOSED) {
            if (var1 != null) {
               var1.dispose();
            }

            return false;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var0, var2, var1));

      return true;
   }

   public static void reportDisposableSet() {
      RxJavaPlugins.onError(new ProtocolViolationException("Disposable already set!"));
   }

   public static boolean set(AtomicReference<Disposable> var0, Disposable var1) {
      Disposable var2;
      do {
         var2 = (Disposable)var0.get();
         if (var2 == DISPOSED) {
            if (var1 != null) {
               var1.dispose();
            }

            return false;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var0, var2, var1));

      if (var2 != null) {
         var2.dispose();
      }

      return true;
   }

   public static boolean setOnce(AtomicReference<Disposable> var0, Disposable var1) {
      ObjectHelper.requireNonNull(var1, "d is null");
      if (!ExternalSyntheticBackportWithForwarding0.m(var0, null, var1)) {
         var1.dispose();
         if (var0.get() != DISPOSED) {
            reportDisposableSet();
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean trySet(AtomicReference<Disposable> var0, Disposable var1) {
      if (!ExternalSyntheticBackportWithForwarding0.m(var0, null, var1)) {
         if (var0.get() == DISPOSED) {
            var1.dispose();
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean validate(Disposable var0, Disposable var1) {
      if (var1 == null) {
         RxJavaPlugins.onError(new NullPointerException("next is null"));
         return false;
      } else if (var0 != null) {
         var1.dispose();
         reportDisposableSet();
         return false;
      } else {
         return true;
      }
   }

   @Override
   public void dispose() {
   }

   @Override
   public boolean isDisposed() {
      return true;
   }
}
