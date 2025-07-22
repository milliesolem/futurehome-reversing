package com.polidea.rxandroidble2.internal;

import android.os.DeadObjectException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.internal.operations.Operation;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public abstract class QueueOperation<T> implements Operation<T> {
   public int compareTo(Operation var1) {
      return var1.definedPriority().priority - this.definedPriority().priority;
   }

   @Override
   public Priority definedPriority() {
      return Priority.NORMAL;
   }

   protected abstract void protectedRun(ObservableEmitter<T> var1, QueueReleaseInterface var2) throws Throwable;

   protected abstract BleException provideException(DeadObjectException var1);

   @Override
   public final Observable<T> run(QueueReleaseInterface var1) {
      return Observable.create(
         new ObservableOnSubscribe<T>(this, var1) {
            final QueueOperation this$0;
            final QueueReleaseInterface val$queueReleaseInterface;

            {
               this.this$0 = var1;
               this.val$queueReleaseInterface = var2;
            }

            // $VF: Could not inline inconsistent finally blocks
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            @Override
            public void subscribe(ObservableEmitter<T> var1) {
               DeadObjectException var2;
               try {
                  try {
                     this.this$0.protectedRun(var1, this.val$queueReleaseInterface);
                     return;
                  } catch (DeadObjectException var5) {
                     var2 = var5;
                  }
               } catch (Throwable var6) {
                  var1.tryOnError(var6);
                  RxBleLog.e(var6, "QueueOperation terminated with an unexpected exception");
                  return;
               }

               var1.tryOnError(this.this$0.provideException(var2));
               RxBleLog.e(var2, "QueueOperation terminated with a DeadObjectException");
            }
         }
      );
   }
}
