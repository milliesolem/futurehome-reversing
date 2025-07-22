package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class ObservableFromFuture<T> extends Observable<T> {
   final Future<? extends T> future;
   final long timeout;
   final TimeUnit unit;

   public ObservableFromFuture(Future<? extends T> var1, long var2, TimeUnit var4) {
      this.future = var1;
      this.timeout = var2;
      this.unit = var4;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      DeferredScalarDisposable var3 = new DeferredScalarDisposable(var1);
      var1.onSubscribe(var3);
      if (!var3.isDisposed()) {
         Object var26;
         label151: {
            label144: {
               try {
                  var26 = this.unit;
               } catch (Throwable var23) {
                  var26 = var23;
                  Exceptions.throwIfFatal(var23);
                  if (var3.isDisposed()) {
                     return;
                  }
                  break label144;
               }

               if (var26 != null) {
                  try {
                     var26 = this.future.get(this.timeout, (TimeUnit)var26);
                  } catch (Throwable var22) {
                     var26 = var22;
                     Exceptions.throwIfFatal(var22);
                     if (var3.isDisposed()) {
                        return;
                     }
                     break label144;
                  }
               } else {
                  try {
                     var26 = this.future.get();
                  } catch (Throwable var21) {
                     var26 = var21;
                     Exceptions.throwIfFatal(var21);
                     if (var3.isDisposed()) {
                        return;
                     }
                     break label144;
                  }
               }

               label130:
               try {
                  var26 = ObjectHelper.requireNonNull(var26, "Future returned null");
                  break label151;
               } catch (Throwable var20) {
                  var26 = var20;
                  Exceptions.throwIfFatal(var20);
                  if (var3.isDisposed()) {
                     return;
                  }
                  break label130;
               }
            }

            var1.onError((Throwable)var26);
            return;
         }

         var3.complete(var26);
      }
   }
}
