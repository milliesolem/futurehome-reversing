package kotlinx.coroutines

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.internal.ConcurrentKt

internal class ExecutorCoroutineDispatcherImpl(executor: Executor) : ExecutorCoroutineDispatcher, Delay {
   public open val executor: Executor

   init {
      this.executor = var1;
      ConcurrentKt.removeFutureOnCancel(this.getExecutor());
   }

   private fun cancelJobOnRejection(context: CoroutineContext, exception: RejectedExecutionException) {
      JobKt.cancel(var1, ExceptionsKt.CancellationException("The task was rejected", var2));
   }

   private fun ScheduledExecutorService.scheduleBlock(block: Runnable, context: CoroutineContext, timeMillis: Long): ScheduledFuture<*>? {
      try {
         var7 = var1.schedule(var2, var4, TimeUnit.MILLISECONDS);
      } catch (var6: RejectedExecutionException) {
         this.cancelJobOnRejection(var3, var6);
         var7 = null;
      }

      return var7;
   }

   public override fun close() {
      val var1: Executor = this.getExecutor();
      val var2: ExecutorService;
      if (var1 is ExecutorService) {
         var2 = var1 as ExecutorService;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var2.shutdown();
      }
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
   override fun delay(var1: Long, var3: Continuation<? super Unit>): Any {
      return Delay.DefaultImpls.delay(this, var1, var3);
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      var var3: RejectedExecutionException;
      label39: {
         var var4: AbstractTimeSource;
         label42: {
            var var5: Executor;
            try {
               var5 = this.getExecutor();
               var9 = AbstractTimeSourceKt.getTimeSource();
            } catch (var8: RejectedExecutionException) {
               var3 = var8;
               var4 = AbstractTimeSourceKt.getTimeSource();
               if (var4 == null) {
                  break label39;
               }
               break label42;
            }

            label34: {
               if (var9 != null) {
                  try {
                     var11 = var9.wrapTask(var2);
                  } catch (var7: RejectedExecutionException) {
                     var3 = var7;
                     var4 = AbstractTimeSourceKt.getTimeSource();
                     if (var4 == null) {
                        break label39;
                     }
                     break label42;
                  }

                  var10 = var11;
                  if (var11 != null) {
                     break label34;
                  }
               }

               var10 = var2;
            }

            try {
               var5.execute(var10);
               return;
            } catch (var6: RejectedExecutionException) {
               var3 = var6;
               var4 = AbstractTimeSourceKt.getTimeSource();
               if (var4 == null) {
                  break label39;
               }
            }
         }

         var4.unTrackTask();
      }

      this.cancelJobOnRejection(var1, var3);
      Dispatchers.getIO().dispatch(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is ExecutorCoroutineDispatcherImpl && (var1 as ExecutorCoroutineDispatcherImpl).getExecutor() === this.getExecutor()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return System.identityHashCode(this.getExecutor());
   }

   public override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
      val var6: Executor = this.getExecutor();
      val var5: Boolean = var6 is ScheduledExecutorService;
      var var7: ScheduledFuture = null;
      val var9: ScheduledExecutorService;
      if (var5) {
         var9 = var6 as ScheduledExecutorService;
      } else {
         var9 = null;
      }

      if (var9 != null) {
         var7 = this.scheduleBlock(var9, var3, var4, var1);
      }

      val var8: DisposableHandle;
      if (var7 != null) {
         var8 = new DisposableFutureHandle(var7);
      } else {
         var8 = DefaultExecutor.INSTANCE.invokeOnTimeout(var1, var3, var4);
      }

      return var8;
   }

   public override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
      val var5: Executor = this.getExecutor();
      val var4: Boolean = var5 is ScheduledExecutorService;
      var var6: ScheduledFuture = null;
      val var7: ScheduledExecutorService;
      if (var4) {
         var7 = var5 as ScheduledExecutorService;
      } else {
         var7 = null;
      }

      if (var7 != null) {
         var6 = this.scheduleBlock(var7, new ResumeUndispatchedRunnable(this, var3), var3.getContext(), var1);
      }

      if (var6 != null) {
         JobKt.cancelFutureOnCancellation(var3, var6);
      } else {
         DefaultExecutor.INSTANCE.scheduleResumeAfterDelay(var1, var3);
      }
   }

   public override fun toString(): String {
      return this.getExecutor().toString();
   }
}
