package okhttp3.internal.concurrent

import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.RejectedExecutionException
import java.util.logging.Level
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class TaskQueue internal constructor(taskRunner: TaskRunner, name: String) {
   internal final var activeTask: Task?
   internal final var cancelActiveTask: Boolean
   internal final val futureTasks: MutableList<Task>
   internal final val name: String

   public final val scheduledTasks: List<Task>
      public final get() {
         label13: {
            val var1: TaskRunner = this.taskRunner;
            synchronized (this.taskRunner){} // $VF: monitorenter 

            try {
               val var2: java.util.List = CollectionsKt.toList(this.futureTasks);
            } catch (var3: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         }
      }


   internal final var shutdown: Boolean
   internal final val taskRunner: TaskRunner

   init {
      Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      Intrinsics.checkParameterIsNotNull(var2, "name");
      super();
      this.taskRunner = var1;
      this.name = var2;
      this.futureTasks = new ArrayList<>();
   }

   public fun cancelAll() {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         val var6: StringBuilder = new StringBuilder("Thread ");
         val var5: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var5, "Thread.currentThread()");
         var6.append(var5.getName());
         var6.append(" MUST NOT hold lock on ");
         var6.append(this);
         throw (new AssertionError(var6.toString())) as java.lang.Throwable;
      } else {
         label43: {
            val var1: TaskRunner = this.taskRunner;
            synchronized (this.taskRunner){} // $VF: monitorenter 

            try {
               if (this.cancelAllAndDecide$okhttp()) {
                  this.taskRunner.kickCoordinator$okhttp(this);
               }
            } catch (var3: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         }
      }
   }

   internal fun cancelAllAndDecide(): Boolean {
      if (this.activeTask != null) {
         if (this.activeTask == null) {
            Intrinsics.throwNpe();
         }

         if (this.activeTask.getCancelable()) {
            this.cancelActiveTask = true;
         }
      }

      var var1: Int = this.futureTasks.size() - 1;

      var var2: Boolean;
      for (var2 = false; var1 >= 0; var1--) {
         if (this.futureTasks.get(var1).getCancelable()) {
            val var4: Task = this.futureTasks.get(var1);
            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
               TaskLoggerKt.access$log(var4, this, "canceled");
            }

            this.futureTasks.remove(var1);
            var2 = true;
         }
      }

      return var2;
   }

   public inline fun execute(name: String, delayNanos: Long = 0L, cancelable: Boolean = true, crossinline block: () -> Unit) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var5, "block");
      this.schedule(new Task(var5, var1, var4, var1, var4) {
         final Function0 $block;
         final boolean $cancelable;
         final java.lang.String $name;

         {
            super(var4, var5);
            this.$block = var1;
            this.$name = var2x;
            this.$cancelable = var3;
         }

         @Override
         public long runOnce() {
            this.$block.invoke();
            return -1L;
         }
      }, var2);
   }

   public fun idleLatch(): CountDownLatch {
      label55: {
         val var1: TaskRunner = this.taskRunner;
         synchronized (this.taskRunner){} // $VF: monitorenter 

         label52: {
            label51: {
               label50: {
                  try {
                     if (this.activeTask == null && this.futureTasks.isEmpty()) {
                        new CountDownLatch(0);
                        break label52;
                     }

                     if (this.activeTask is TaskQueue.AwaitIdleTask) {
                        val var10: CountDownLatch = (this.activeTask as TaskQueue.AwaitIdleTask).getLatch();
                        break label51;
                     }

                     for (Task var3 : this.futureTasks) {
                        if (var3 is TaskQueue.AwaitIdleTask) {
                           val var7: CountDownLatch = (var3 as TaskQueue.AwaitIdleTask).getLatch();
                           break label50;
                        }
                     }

                     val var8: TaskQueue.AwaitIdleTask = new TaskQueue.AwaitIdleTask();
                     if (this.scheduleAndDecide$okhttp(var8, 0L, false)) {
                        this.taskRunner.kickCoordinator$okhttp(this);
                     }

                     val var9: CountDownLatch = var8.getLatch();
                  } catch (var4: java.lang.Throwable) {
                     // $VF: monitorexit
                  }

                  // $VF: monitorexit
               }

               // $VF: monitorexit
            }

            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public inline fun schedule(name: String, delayNanos: Long = 0L, crossinline block: () -> Long) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var4, "block");
      this.schedule(new Task(var4, var1, var1) {
         final Function0 $block;
         final java.lang.String $name;

         {
            super(var3, false, 2, null);
            this.$block = var1;
            this.$name = var2;
         }

         @Override
         public long runOnce() {
            return (this.$block.invoke() as java.lang.Number).longValue();
         }
      }, var2);
   }

   public fun schedule(task: Task, delayNanos: Long = 0L) {
      Intrinsics.checkParameterIsNotNull(var1, "task");
      synchronized (this.taskRunner) {
         if (this.shutdown) {
            if (!var1.getCancelable()) {
               if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
                  TaskLoggerKt.access$log(var1, this, "schedule failed (queue is shutdown)");
               }

               throw (new RejectedExecutionException()) as java.lang.Throwable;
            }

            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
               TaskLoggerKt.access$log(var1, this, "schedule canceled (queue is shutdown)");
            }

            return;
         }

         if (this.scheduleAndDecide$okhttp(var1, var2, false)) {
            this.taskRunner.kickCoordinator$okhttp(this);
         }
      }
   }

   internal fun scheduleAndDecide(task: Task, delayNanos: Long, recurrence: Boolean): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "task");
      var1.initQueue$okhttp(this);
      val var10: Long = this.taskRunner.getBackend().nanoTime();
      val var8: Long = var10 + var2;
      var var5: Int = this.futureTasks.indexOf(var1);
      if (var5 != -1) {
         if (var1.getNextExecuteNanoTime$okhttp() <= var8) {
            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
               TaskLoggerKt.access$log(var1, this, "already scheduled");
            }

            return false;
         }

         this.futureTasks.remove(var5);
      }

      var1.setNextExecuteNanoTime$okhttp(var8);
      if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
         val var15: java.lang.String;
         if (var4) {
            val var12: StringBuilder = new StringBuilder("run again after ");
            var12.append(TaskLoggerKt.formatDuration(var8 - var10));
            var15 = var12.toString();
         } else {
            val var16: StringBuilder = new StringBuilder("scheduled after ");
            var16.append(TaskLoggerKt.formatDuration(var8 - var10));
            var15 = var16.toString();
         }

         TaskLoggerKt.access$log(var1, this, var15);
      }

      val var17: java.util.Iterator = this.futureTasks.iterator();
      var5 = 0;

      while (true) {
         if (!var17.hasNext()) {
            var5 = -1;
            break;
         }

         if ((var17.next() as Task).getNextExecuteNanoTime$okhttp() - var10 > var2) {
            break;
         }

         var5++;
      }

      var var6: Int = var5;
      if (var5 == -1) {
         var6 = this.futureTasks.size();
      }

      this.futureTasks.add(var6, var1);
      var4 = false;
      if (var6 == 0) {
         var4 = true;
      }

      return var4;
   }

   public fun shutdown() {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         val var6: StringBuilder = new StringBuilder("Thread ");
         val var5: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var5, "Thread.currentThread()");
         var6.append(var5.getName());
         var6.append(" MUST NOT hold lock on ");
         var6.append(this);
         throw (new AssertionError(var6.toString())) as java.lang.Throwable;
      } else {
         label43: {
            val var1: TaskRunner = this.taskRunner;
            synchronized (this.taskRunner){} // $VF: monitorenter 

            try {
               this.shutdown = true;
               if (this.cancelAllAndDecide$okhttp()) {
                  this.taskRunner.kickCoordinator$okhttp(this);
               }
            } catch (var3: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         }
      }
   }

   public override fun toString(): String {
      return this.name;
   }

   private class AwaitIdleTask : Task {
      public final val latch: CountDownLatch

      init {
         val var1: StringBuilder = new StringBuilder();
         var1.append(Util.okHttpName);
         var1.append(" awaitIdle");
         super(var1.toString(), false);
         this.latch = new CountDownLatch(1);
      }

      public override fun runOnce(): Long {
         this.latch.countDown();
         return -1L;
      }
   }
}
