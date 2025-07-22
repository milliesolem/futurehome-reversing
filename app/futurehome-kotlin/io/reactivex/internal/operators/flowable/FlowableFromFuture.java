package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;

public final class FlowableFromFuture<T> extends Flowable<T> {
   final Future<? extends T> future;
   final long timeout;
   final TimeUnit unit;

   public FlowableFromFuture(Future<? extends T> var1, long var2, TimeUnit var4) {
      this.future = var1;
      this.timeout = var2;
      this.unit = var4;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      DeferredScalarSubscription var3 = new DeferredScalarSubscription(var1);
      var1.onSubscribe(var3);

      Object var17;
      label115: {
         label106: {
            try {
               var17 = this.unit;
            } catch (Throwable var15) {
               var17 = var15;
               Exceptions.throwIfFatal(var15);
               if (var3.isCancelled()) {
                  return;
               }
               break label106;
            }

            if (var17 != null) {
               label100:
               try {
                  var17 = this.future.get(this.timeout, (TimeUnit)var17);
               } catch (Throwable var13) {
                  var17 = var13;
                  Exceptions.throwIfFatal(var13);
                  if (var3.isCancelled()) {
                     return;
                  }
                  break label100;
               }
            } else {
               label102:
               try {
                  var17 = this.future.get();
               } catch (Throwable var14) {
                  var17 = var14;
                  Exceptions.throwIfFatal(var14);
                  if (var3.isCancelled()) {
                     return;
                  }
                  break label102;
               }
            }
            break label115;
         }

         var1.onError((Throwable)var17);
         return;
      }

      if (var17 == null) {
         var1.onError(new NullPointerException("The future returned null"));
      } else {
         var3.complete(var17);
      }
   }
}
