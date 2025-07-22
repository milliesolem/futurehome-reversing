package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class MaybeFromFuture<T> extends Maybe<T> {
   final Future<? extends T> future;
   final long timeout;
   final TimeUnit unit;

   public MaybeFromFuture(Future<? extends T> var1, long var2, TimeUnit var4) {
      this.future = var1;
      this.timeout = var2;
      this.unit = var4;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      Disposable var6 = Disposables.empty();
      var1.onSubscribe(var6);
      if (!var6.isDisposed()) {
         Object var19;
         label124: {
            label117: {
               Throwable var5;
               label116: {
                  long var2;
                  try {
                     var2 = this.timeout;
                  } catch (Throwable var18) {
                     var5 = var18;
                     var19 = var18;
                     if (!(var18 instanceof ExecutionException)) {
                        break label117;
                     }
                     break label116;
                  }

                  if (var2 <= 0L) {
                     label110:
                     try {
                        var19 = this.future.get();
                     } catch (Throwable var16) {
                        var5 = var16;
                        var19 = var16;
                        if (!(var16 instanceof ExecutionException)) {
                           break label117;
                        }
                        break label110;
                     }
                  } else {
                     label112:
                     try {
                        var19 = this.future.get(var2, this.unit);
                     } catch (Throwable var17) {
                        var5 = var17;
                        var19 = var17;
                        if (!(var17 instanceof ExecutionException)) {
                           break label117;
                        }
                        break label112;
                     }
                  }
                  break label124;
               }

               var19 = var5.getCause();
            }

            Exceptions.throwIfFatal((Throwable)var19);
            if (!var6.isDisposed()) {
               var1.onError((Throwable)var19);
            }

            return;
         }

         if (!var6.isDisposed()) {
            if (var19 == null) {
               var1.onComplete();
            } else {
               var1.onSuccess(var19);
            }
         }
      }
   }
}
