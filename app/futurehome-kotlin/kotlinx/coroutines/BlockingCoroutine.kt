package kotlinx.coroutines

import java.util.concurrent.locks.LockSupport
import kotlin.coroutines.CoroutineContext

private class BlockingCoroutine<T>(parentContext: CoroutineContext, blockedThread: Thread, eventLoop: EventLoop?) : AbstractCoroutine(var1, true, true) {
   private final val blockedThread: Thread
   private final val eventLoop: EventLoop?

   protected open val isScopedCoroutine: Boolean
      protected open get() {
         return true;
      }


   init {
      this.blockedThread = var2;
      this.eventLoop = var3;
   }

   protected override fun afterCompletion(state: Any?) {
      if (!(Thread.currentThread() == this.blockedThread)) {
         val var2: Thread = this.blockedThread;
         var1 = AbstractTimeSourceKt.getTimeSource();
         val var4: Unit;
         if (var1 != null) {
            var1.unpark(var2);
            var4 = Unit.INSTANCE;
         } else {
            var4 = null;
         }

         if (var4 == null) {
            LockSupport.unpark(var2);
         }
      }
   }

   public fun joinBlocking(): Any {
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
      // 00: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 03: astore 3
      // 04: aload 3
      // 05: ifnull 0c
      // 08: aload 3
      // 09: invokevirtual kotlinx/coroutines/AbstractTimeSource.registerTimeLoopThread ()V
      // 0c: aload 0
      // 0d: getfield kotlinx/coroutines/BlockingCoroutine.eventLoop Lkotlinx/coroutines/EventLoop;
      // 10: astore 3
      // 11: aconst_null
      // 12: astore 4
      // 14: aload 3
      // 15: ifnull 1f
      // 18: aload 3
      // 19: bipush 0
      // 1a: bipush 1
      // 1b: aconst_null
      // 1c: invokestatic kotlinx/coroutines/EventLoop.incrementUseCount$default (Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
      // 1f: invokestatic java/lang/Thread.interrupted ()Z
      // 22: ifne a6
      // 25: aload 0
      // 26: getfield kotlinx/coroutines/BlockingCoroutine.eventLoop Lkotlinx/coroutines/EventLoop;
      // 29: astore 3
      // 2a: aload 3
      // 2b: ifnull 36
      // 2e: aload 3
      // 2f: invokevirtual kotlinx/coroutines/EventLoop.processNextEvent ()J
      // 32: lstore 1
      // 33: goto 3a
      // 36: ldc2_w 9223372036854775807
      // 39: lstore 1
      // 3a: aload 0
      // 3b: invokevirtual kotlinx/coroutines/BlockingCoroutine.isCompleted ()Z
      // 3e: ifne 64
      // 41: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 44: astore 3
      // 45: aload 3
      // 46: ifnull 56
      // 49: aload 3
      // 4a: aload 0
      // 4b: lload 1
      // 4c: invokevirtual kotlinx/coroutines/AbstractTimeSource.parkNanos (Ljava/lang/Object;J)V
      // 4f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 52: astore 3
      // 53: goto 58
      // 56: aconst_null
      // 57: astore 3
      // 58: aload 3
      // 59: ifnonnull 1f
      // 5c: aload 0
      // 5d: lload 1
      // 5e: invokestatic java/util/concurrent/locks/LockSupport.parkNanos (Ljava/lang/Object;J)V
      // 61: goto 1f
      // 64: aload 0
      // 65: getfield kotlinx/coroutines/BlockingCoroutine.eventLoop Lkotlinx/coroutines/EventLoop;
      // 68: astore 3
      // 69: aload 3
      // 6a: ifnull 74
      // 6d: aload 3
      // 6e: bipush 0
      // 6f: bipush 1
      // 70: aconst_null
      // 71: invokestatic kotlinx/coroutines/EventLoop.decrementUseCount$default (Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
      // 74: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 77: astore 3
      // 78: aload 3
      // 79: ifnull 80
      // 7c: aload 3
      // 7d: invokevirtual kotlinx/coroutines/AbstractTimeSource.unregisterTimeLoopThread ()V
      // 80: aload 0
      // 81: invokevirtual kotlinx/coroutines/BlockingCoroutine.getState$kotlinx_coroutines_core ()Ljava/lang/Object;
      // 84: invokestatic kotlinx/coroutines/JobSupportKt.unboxState (Ljava/lang/Object;)Ljava/lang/Object;
      // 87: astore 5
      // 89: aload 4
      // 8b: astore 3
      // 8c: aload 5
      // 8e: instanceof kotlinx/coroutines/CompletedExceptionally
      // 91: ifeq 9a
      // 94: aload 5
      // 96: checkcast kotlinx/coroutines/CompletedExceptionally
      // 99: astore 3
      // 9a: aload 3
      // 9b: ifnonnull a1
      // 9e: aload 5
      // a0: areturn
      // a1: aload 3
      // a2: getfield kotlinx/coroutines/CompletedExceptionally.cause Ljava/lang/Throwable;
      // a5: athrow
      // a6: new java/lang/InterruptedException
      // a9: astore 3
      // aa: aload 3
      // ab: invokespecial java/lang/InterruptedException.<init> ()V
      // ae: aload 0
      // af: aload 3
      // b0: checkcast java/lang/Throwable
      // b3: invokevirtual kotlinx/coroutines/BlockingCoroutine.cancelCoroutine (Ljava/lang/Throwable;)Z
      // b6: pop
      // b7: aload 3
      // b8: checkcast java/lang/Throwable
      // bb: athrow
      // bc: astore 4
      // be: aload 0
      // bf: getfield kotlinx/coroutines/BlockingCoroutine.eventLoop Lkotlinx/coroutines/EventLoop;
      // c2: astore 3
      // c3: aload 3
      // c4: ifnull ce
      // c7: aload 3
      // c8: bipush 0
      // c9: bipush 1
      // ca: aconst_null
      // cb: invokestatic kotlinx/coroutines/EventLoop.decrementUseCount$default (Lkotlinx/coroutines/EventLoop;ZILjava/lang/Object;)V
      // ce: aload 4
      // d0: athrow
      // d1: astore 4
      // d3: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // d6: astore 3
      // d7: aload 3
      // d8: ifnull df
      // db: aload 3
      // dc: invokevirtual kotlinx/coroutines/AbstractTimeSource.unregisterTimeLoopThread ()V
      // df: aload 4
      // e1: athrow
   }
}
