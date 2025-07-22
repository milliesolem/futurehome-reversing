package okhttp3.internal.concurrent

import java.util.ArrayList
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class TaskRunner(backend: okhttp3.internal.concurrent.TaskRunner.Backend) {
   public final val backend: okhttp3.internal.concurrent.TaskRunner.Backend
   private final val busyQueues: MutableList<TaskQueue>
   private final var coordinatorWaiting: Boolean
   private final var coordinatorWakeUpAt: Long
   private final var nextQueueName: Int
   private final val readyQueues: MutableList<TaskQueue>
   private final val runnable: Runnable

   @JvmStatic
   fun {
      val var0: StringBuilder = new StringBuilder();
      var0.append(Util.okHttpName);
      var0.append(" TaskRunner");
      INSTANCE = new TaskRunner(new TaskRunner.RealBackend(Util.threadFactory(var0.toString(), true)));
      val var1: Logger = Logger.getLogger(TaskRunner.class.getName());
      Intrinsics.checkExpressionValueIsNotNull(var1, "Logger.getLogger(TaskRunner::class.java.name)");
      logger = var1;
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "backend");
      super();
      this.backend = var1;
      this.nextQueueName = 10000;
      this.busyQueues = new ArrayList<>();
      this.readyQueues = new ArrayList<>();
      this.runnable = new Runnable(this) {
         final TaskRunner this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void run() {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
            //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
            //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
            //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
            //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield okhttp3/internal/concurrent/TaskRunner$runnable$1.this$0 Lokhttp3/internal/concurrent/TaskRunner;
            // 04: astore 7
            // 06: aload 7
            // 08: monitorenter
            // 09: aload 0
            // 0a: getfield okhttp3/internal/concurrent/TaskRunner$runnable$1.this$0 Lokhttp3/internal/concurrent/TaskRunner;
            // 0d: invokevirtual okhttp3/internal/concurrent/TaskRunner.awaitTaskToRun ()Lokhttp3/internal/concurrent/Task;
            // 10: astore 6
            // 12: aload 7
            // 14: monitorexit
            // 15: aload 6
            // 17: ifnull f1
            // 1a: aload 6
            // 1c: invokevirtual okhttp3/internal/concurrent/Task.getQueue$okhttp ()Lokhttp3/internal/concurrent/TaskQueue;
            // 1f: astore 7
            // 21: aload 7
            // 23: ifnonnull 29
            // 26: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
            // 29: getstatic okhttp3/internal/concurrent/TaskRunner.Companion Lokhttp3/internal/concurrent/TaskRunner$Companion;
            // 2c: invokevirtual okhttp3/internal/concurrent/TaskRunner$Companion.getLogger ()Ljava/util/logging/Logger;
            // 2f: getstatic java/util/logging/Level.FINE Ljava/util/logging/Level;
            // 32: invokevirtual java/util/logging/Logger.isLoggable (Ljava/util/logging/Level;)Z
            // 35: istore 5
            // 37: iload 5
            // 39: ifeq 56
            // 3c: aload 7
            // 3e: invokevirtual okhttp3/internal/concurrent/TaskQueue.getTaskRunner$okhttp ()Lokhttp3/internal/concurrent/TaskRunner;
            // 41: invokevirtual okhttp3/internal/concurrent/TaskRunner.getBackend ()Lokhttp3/internal/concurrent/TaskRunner$Backend;
            // 44: invokeinterface okhttp3/internal/concurrent/TaskRunner$Backend.nanoTime ()J 1
            // 49: lstore 1
            // 4a: aload 6
            // 4c: aload 7
            // 4e: ldc "starting"
            // 50: invokestatic okhttp3/internal/concurrent/TaskLoggerKt.access$log (Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Ljava/lang/String;)V
            // 53: goto 5a
            // 56: ldc2_w -1
            // 59: lstore 1
            // 5a: aload 0
            // 5b: getfield okhttp3/internal/concurrent/TaskRunner$runnable$1.this$0 Lokhttp3/internal/concurrent/TaskRunner;
            // 5e: aload 6
            // 60: invokestatic okhttp3/internal/concurrent/TaskRunner.access$runTask (Lokhttp3/internal/concurrent/TaskRunner;Lokhttp3/internal/concurrent/Task;)V
            // 63: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 66: astore 8
            // 68: iload 5
            // 6a: ifeq 00
            // 6d: aload 7
            // 6f: invokevirtual okhttp3/internal/concurrent/TaskQueue.getTaskRunner$okhttp ()Lokhttp3/internal/concurrent/TaskRunner;
            // 72: invokevirtual okhttp3/internal/concurrent/TaskRunner.getBackend ()Lokhttp3/internal/concurrent/TaskRunner$Backend;
            // 75: invokeinterface okhttp3/internal/concurrent/TaskRunner$Backend.nanoTime ()J 1
            // 7a: lstore 3
            // 7b: new java/lang/StringBuilder
            // 7e: dup
            // 7f: ldc "finished run in "
            // 81: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 84: astore 8
            // 86: aload 8
            // 88: lload 3
            // 89: lload 1
            // 8a: lsub
            // 8b: invokestatic okhttp3/internal/concurrent/TaskLoggerKt.formatDuration (J)Ljava/lang/String;
            // 8e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 91: pop
            // 92: aload 6
            // 94: aload 7
            // 96: aload 8
            // 98: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 9b: invokestatic okhttp3/internal/concurrent/TaskLoggerKt.access$log (Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Ljava/lang/String;)V
            // 9e: goto 00
            // a1: astore 8
            // a3: aload 0
            // a4: getfield okhttp3/internal/concurrent/TaskRunner$runnable$1.this$0 Lokhttp3/internal/concurrent/TaskRunner;
            // a7: invokevirtual okhttp3/internal/concurrent/TaskRunner.getBackend ()Lokhttp3/internal/concurrent/TaskRunner$Backend;
            // aa: aload 0
            // ab: checkcast java/lang/Runnable
            // ae: invokeinterface okhttp3/internal/concurrent/TaskRunner$Backend.execute (Ljava/lang/Runnable;)V 2
            // b3: aload 8
            // b5: athrow
            // b6: astore 8
            // b8: iload 5
            // ba: ifeq ee
            // bd: aload 7
            // bf: invokevirtual okhttp3/internal/concurrent/TaskQueue.getTaskRunner$okhttp ()Lokhttp3/internal/concurrent/TaskRunner;
            // c2: invokevirtual okhttp3/internal/concurrent/TaskRunner.getBackend ()Lokhttp3/internal/concurrent/TaskRunner$Backend;
            // c5: invokeinterface okhttp3/internal/concurrent/TaskRunner$Backend.nanoTime ()J 1
            // ca: lstore 3
            // cb: new java/lang/StringBuilder
            // ce: dup
            // cf: ldc "failed a run in "
            // d1: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // d4: astore 9
            // d6: aload 9
            // d8: lload 3
            // d9: lload 1
            // da: lsub
            // db: invokestatic okhttp3/internal/concurrent/TaskLoggerKt.formatDuration (J)Ljava/lang/String;
            // de: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // e1: pop
            // e2: aload 6
            // e4: aload 7
            // e6: aload 9
            // e8: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // eb: invokestatic okhttp3/internal/concurrent/TaskLoggerKt.access$log (Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Ljava/lang/String;)V
            // ee: aload 8
            // f0: athrow
            // f1: return
            // f2: astore 6
            // f4: aload 7
            // f6: monitorexit
            // f7: aload 6
            // f9: athrow
         }
      };
   }

   private fun afterRun(task: Task, delayNanos: Long) {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var8: StringBuilder = new StringBuilder("Thread ");
         val var9: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var9, "Thread.currentThread()");
         var8.append(var9.getName());
         var8.append(" MUST hold lock on ");
         var8.append(this);
         throw (new AssertionError(var8.toString())) as java.lang.Throwable;
      } else {
         val var6: TaskQueue = var1.getQueue$okhttp();
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         val var4: Boolean;
         if (var6.getActiveTask$okhttp() === var1) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            val var5: Boolean = var6.getCancelActiveTask$okhttp();
            var6.setCancelActiveTask$okhttp(false);
            val var7: Task = null as Task;
            var6.setActiveTask$okhttp(null);
            this.busyQueues.remove(var6);
            if (var2 != -1L && !var5 && !var6.getShutdown$okhttp()) {
               var6.scheduleAndDecide$okhttp(var1, var2, true);
            }

            if (!var6.getFutureTasks$okhttp().isEmpty()) {
               this.readyQueues.add(var6);
            }
         } else {
            throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
         }
      }
   }

   private fun beforeRun(task: Task) {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var3: StringBuilder = new StringBuilder("Thread ");
         val var4: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var4, "Thread.currentThread()");
         var3.append(var4.getName());
         var3.append(" MUST hold lock on ");
         var3.append(this);
         throw (new AssertionError(var3.toString())) as java.lang.Throwable;
      } else {
         var1.setNextExecuteNanoTime$okhttp(-1L);
         val var2: TaskQueue = var1.getQueue$okhttp();
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         var2.getFutureTasks$okhttp().remove(var1);
         this.readyQueues.remove(var2);
         var2.setActiveTask$okhttp(var1);
         this.busyQueues.add(var2);
      }
   }

   private fun runTask(task: Task) {
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         val var23: StringBuilder = new StringBuilder("Thread ");
         val var22: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var22, "Thread.currentThread()");
         var23.append(var22.getName());
         var23.append(" MUST NOT hold lock on ");
         var23.append(this);
         throw (new AssertionError(var23.toString())) as java.lang.Throwable;
      } else {
         label87: {
            val var5: Thread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var5, "currentThread");
            val var4: java.lang.String = var5.getName();
            var5.setName(var1.getName());

            try {
               ;
            } catch (var9: java.lang.Throwable) {
               synchronized (this){} // $VF: monitorenter 

               try {
                  this.afterRun(var1, -1L);
               } catch (var7: java.lang.Throwable) {
                  // $VF: monitorexit
               }

               // $VF: monitorexit
            }

            synchronized (this){} // $VF: monitorenter 

            try {
               val var2: Long;
               this.afterRun(var1, var2);
            } catch (var8: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         }
      }
   }

   public fun activeQueues(): List<TaskQueue> {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            val var1: java.util.List = CollectionsKt.plus(this.busyQueues, this.readyQueues);
         } catch (var2: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public fun awaitTaskToRun(): Task? {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var16: StringBuilder = new StringBuilder("Thread ");
         val var17: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var17, "Thread.currentThread()");
         var16.append(var17.getName());
         var16.append(" MUST hold lock on ");
         var16.append(this);
         throw (new AssertionError(var16.toString())) as java.lang.Throwable;
      } else {
         label111: {
            for (; !this.readyQueues.isEmpty(); this.coordinatorWaiting = false) {
               val var4: Long = this.backend.nanoTime();
               var var8: Task = null as Task;
               val var10: java.util.Iterator = this.readyQueues.iterator();
               var var2: Long = java.lang.Long.MAX_VALUE;
               var8 = null;

               var var1: Boolean;
               while (true) {
                  if (!var10.hasNext()) {
                     var1 = false;
                     break;
                  }

                  val var9: Task = (var10.next() as TaskQueue).getFutureTasks$okhttp().get(0);
                  val var6: Long = Math.max(0L, var9.getNextExecuteNanoTime$okhttp() - var4);
                  if (var6 > 0L) {
                     var2 = Math.min(var6, var2);
                  } else {
                     if (var8 != null) {
                        var1 = true;
                        break;
                     }

                     var8 = var9;
                  }
               }

               if (var8 != null) {
                  this.beforeRun(var8);
                  if (var1 || !this.coordinatorWaiting && !this.readyQueues.isEmpty()) {
                     this.backend.execute(this.runnable);
                  }

                  return var8;
               }

               if (this.coordinatorWaiting) {
                  if (var2 < this.coordinatorWakeUpAt - var4) {
                     this.backend.coordinatorNotify(this);
                  }

                  return null;
               }

               this.coordinatorWaiting = true;
               this.coordinatorWakeUpAt = var4 + var2;

               try {
                  try {
                     this.backend.coordinatorWait(this, var2);
                  } catch (var11: InterruptedException) {
                     this.cancelAll();
                  }
               } catch (var12: java.lang.Throwable) {
                  this.coordinatorWaiting = false;
               }
            }

            return null;
         }
      }
   }

   public fun cancelAll() {
      for (int var1 = this.busyQueues.size() - 1; var1 >= 0; var1--) {
         this.busyQueues.get(var1).cancelAllAndDecide$okhttp();
      }

      for (int var3 = this.readyQueues.size() - 1; var3 >= 0; var3--) {
         val var2: TaskQueue = this.readyQueues.get(var3);
         var2.cancelAllAndDecide$okhttp();
         if (var2.getFutureTasks$okhttp().isEmpty()) {
            this.readyQueues.remove(var3);
         }
      }
   }

   internal fun kickCoordinator(taskQueue: TaskQueue) {
      Intrinsics.checkParameterIsNotNull(var1, "taskQueue");
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var2: StringBuilder = new StringBuilder("Thread ");
         val var3: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var2.append(var3.getName());
         var2.append(" MUST hold lock on ");
         var2.append(this);
         throw (new AssertionError(var2.toString())) as java.lang.Throwable;
      } else {
         if (var1.getActiveTask$okhttp() == null) {
            if (!var1.getFutureTasks$okhttp().isEmpty()) {
               Util.addIfAbsent(this.readyQueues, var1);
            } else {
               this.readyQueues.remove(var1);
            }
         }

         if (this.coordinatorWaiting) {
            this.backend.coordinatorNotify(this);
         } else {
            this.backend.execute(this.runnable);
         }
      }
   }

   public fun newQueue(): TaskQueue {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            val var1: Int = this.nextQueueName++;
         } catch (var3: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public interface Backend {
      public abstract fun beforeTask(taskRunner: TaskRunner) {
      }

      public abstract fun coordinatorNotify(taskRunner: TaskRunner) {
      }

      public abstract fun coordinatorWait(taskRunner: TaskRunner, nanos: Long) {
      }

      public abstract fun execute(runnable: Runnable) {
      }

      public abstract fun nanoTime(): Long {
      }
   }

   public companion object {
      public final val INSTANCE: TaskRunner
      public final val logger: Logger
   }

   public class RealBackend(threadFactory: ThreadFactory) : TaskRunner.Backend {
      private final val executor: ThreadPoolExecutor

      init {
         Intrinsics.checkParameterIsNotNull(var1, "threadFactory");
         super();
         this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), var1);
      }

      public override fun beforeTask(taskRunner: TaskRunner) {
         Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      }

      public override fun coordinatorNotify(taskRunner: TaskRunner) {
         Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
         var1.notify();
      }

      @Throws(java/lang/InterruptedException::class)
      public override fun coordinatorWait(taskRunner: TaskRunner, nanos: Long) {
         Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
         val var4: Long = var2 / 1000000L;
         if (var2 / 1000000L > 0L || var2 > 0L) {
            var1.wait(var4, (int)(var2 - 1000000L * var4));
         }
      }

      public override fun execute(runnable: Runnable) {
         Intrinsics.checkParameterIsNotNull(var1, "runnable");
         this.executor.execute(var1);
      }

      public override fun nanoTime(): Long {
         return System.nanoTime();
      }

      public fun shutdown() {
         this.executor.shutdown();
      }
   }
}
