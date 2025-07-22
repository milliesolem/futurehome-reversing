package com.polidea.rxandroidble2.internal.serialization;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.operations.Operation;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;

public class ClientOperationQueueImpl implements ClientOperationQueue {
   final OperationPriorityFifoBlockingQueue queue = new OperationPriorityFifoBlockingQueue();

   @Inject
   public ClientOperationQueueImpl(@Named("bluetooth_interaction") Scheduler var1) {
      new Thread(new Runnable(this, var1) {
         final ClientOperationQueueImpl this$0;
         final Scheduler val$callbackScheduler;

         {
            this.this$0 = var1;
            this.val$callbackScheduler = var2;
         }

         @Override
         public void run() {
            while (true) {
               try {
                  FIFORunnableEntry var4 = this.this$0.queue.take();
                  Operation var3 = var4.operation;
                  long var1x = System.currentTimeMillis();
                  LoggerUtil.logOperationStarted(var3);
                  LoggerUtil.logOperationRunning(var3);
                  QueueSemaphore var5 = new QueueSemaphore();
                  var4.run(var5, this.val$callbackScheduler);
                  var5.awaitRelease();
                  LoggerUtil.logOperationFinished(var3, var1x, System.currentTimeMillis());
               } catch (InterruptedException var6) {
                  RxBleLog.e(var6, "Error while processing client operation queue");
               }
            }
         }
      }).start();
   }

   @Override
   public <T> Observable<T> queue(Operation<T> var1) {
      return Observable.create(new ObservableOnSubscribe<T>(this, var1) {
         final ClientOperationQueueImpl this$0;
         final Operation val$operation;

         {
            this.this$0 = var1;
            this.val$operation = var2;
         }

         @Override
         public void subscribe(ObservableEmitter<T> var1) {
            FIFORunnableEntry var2 = new FIFORunnableEntry(this.val$operation, var1);
            var1.setDisposable(Disposables.fromAction(new Action(this, var2) {
               final <unrepresentable> this$1;
               final FIFORunnableEntry val$entry;

               {
                  this.this$1 = var1;
                  this.val$entry = var2x;
               }

               @Override
               public void run() {
                  if (this.this$1.this$0.queue.remove(this.val$entry)) {
                     LoggerUtil.logOperationRemoved(this.this$1.val$operation);
                  }
               }
            }));
            LoggerUtil.logOperationQueued(this.val$operation);
            this.this$0.queue.add(var2);
         }
      });
   }
}
