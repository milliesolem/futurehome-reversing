package com.polidea.rxandroidble2.internal.serialization;

import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.operations.Operation;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicLong;

class FIFORunnableEntry<T> implements Comparable<FIFORunnableEntry> {
   private static final AtomicLong SEQUENCE = new AtomicLong(0L);
   final Operation<T> operation;
   final ObservableEmitter<T> operationResultObserver;
   private final long seqNum = SEQUENCE.getAndIncrement();

   FIFORunnableEntry(Operation<T> var1, ObservableEmitter<T> var2) {
      this.operation = var1;
      this.operationResultObserver = var2;
   }

   public int compareTo(FIFORunnableEntry var1) {
      int var3 = this.operation.compareTo(var1.operation);
      int var2 = var3;
      if (var3 == 0) {
         var2 = var3;
         if (var1.operation != this.operation) {
            if (this.seqNum < var1.seqNum) {
               var2 = -1;
            } else {
               var2 = 1;
            }
         }
      }

      return var2;
   }

   public void run(QueueSemaphore var1, Scheduler var2) {
      if (this.operationResultObserver.isDisposed()) {
         LoggerUtil.logOperationSkippedBecauseDisposedWhenAboutToRun(this.operation);
         var1.release();
      } else {
         var2.scheduleDirect(new Runnable(this, var1, var2) {
            final FIFORunnableEntry this$0;
            final QueueSemaphore val$semaphore;
            final Scheduler val$subscribeScheduler;

            {
               this.this$0 = var1;
               this.val$semaphore = var2x;
               this.val$subscribeScheduler = var3;
            }

            @Override
            public void run() {
               this.this$0.operation.run(this.val$semaphore).unsubscribeOn(this.val$subscribeScheduler).subscribe(new Observer<T>(this) {
                  final <unrepresentable> this$1;

                  {
                     this.this$1 = var1;
                  }

                  @Override
                  public void onComplete() {
                     this.this$1.this$0.operationResultObserver.onComplete();
                  }

                  @Override
                  public void onError(Throwable var1) {
                     this.this$1.this$0.operationResultObserver.tryOnError(var1);
                  }

                  @Override
                  public void onNext(T var1) {
                     this.this$1.this$0.operationResultObserver.onNext((T)var1);
                  }

                  @Override
                  public void onSubscribe(Disposable var1) {
                     this.this$1.this$0.operationResultObserver.setDisposable(var1);
                  }
               });
            }
         });
      }
   }
}
