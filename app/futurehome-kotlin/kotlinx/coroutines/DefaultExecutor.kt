package kotlinx.coroutines

import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.EventLoopImplBase.DelayedTask

internal object DefaultExecutor : EventLoopImplBase, Runnable {
   private const val ACTIVE: Int = 1
   private const val DEFAULT_KEEP_ALIVE_MS: Long = 1000L
   private const val FRESH: Int = 0
   private final val KEEP_ALIVE_NANOS: Long
   private const val SHUTDOWN: Int = 4
   private const val SHUTDOWN_ACK: Int = 3
   private const val SHUTDOWN_REQ: Int = 2
   public const val THREAD_NAME: String = "kotlinx.coroutines.DefaultExecutor"
   private final var _thread: Thread?
   private final var debugStatus: Int

   private final val isShutDown: Boolean
      private final get() {
         val var1: Boolean;
         if (debugStatus == 4) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final val isShutdownRequested: Boolean
      private final get() {
         val var2: Boolean;
         if (debugStatus != 2 && debugStatus != 3) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }


   internal final val isThreadPresent: Boolean
      internal final get() {
         val var1: Boolean;
         if (_thread != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   protected open val thread: Thread
      protected open get() {
         var var1: Thread = _thread;
         if (_thread == null) {
            var1 = this.createThreadSync();
         }

         return var1;
      }


   @JvmStatic
   fun {
      val var0: DefaultExecutor = new DefaultExecutor();
      INSTANCE = var0;
      EventLoop.incrementUseCount$default(var0, false, 1, null);
      val var1: TimeUnit = TimeUnit.MILLISECONDS;

      try {
         var3 = java.lang.Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
      } catch (var2: SecurityException) {
         var3 = 1000L;
      }

      KEEP_ALIVE_NANOS = var1.toNanos(var3);
   }

   private fun acknowledgeShutdownIfNeeded() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: invokespecial kotlinx/coroutines/DefaultExecutor.isShutdownRequested ()Z
      // 06: istore 1
      // 07: iload 1
      // 08: ifne 0e
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: return
      // 0e: bipush 3
      // 0f: putstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 12: aload 0
      // 13: invokevirtual kotlinx/coroutines/DefaultExecutor.resetAll ()V
      // 16: aload 0
      // 17: ldc "null cannot be cast to non-null type java.lang.Object"
      // 19: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 1c: aload 0
      // 1d: checkcast java/lang/Object
      // 20: invokevirtual java/lang/Object.notifyAll ()V
      // 23: aload 0
      // 24: monitorexit
      // 25: return
      // 26: astore 2
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 2
      // 2a: athrow
   }

   private fun createThreadSync(): Thread {
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
      // 01: monitorenter
      // 02: getstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 05: astore 2
      // 06: aload 2
      // 07: astore 1
      // 08: aload 2
      // 09: ifnonnull 27
      // 0c: new java/lang/Thread
      // 0f: astore 1
      // 10: aload 1
      // 11: aload 0
      // 12: checkcast java/lang/Runnable
      // 15: ldc "kotlinx.coroutines.DefaultExecutor"
      // 17: invokespecial java/lang/Thread.<init> (Ljava/lang/Runnable;Ljava/lang/String;)V
      // 1a: aload 1
      // 1b: putstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 1e: aload 1
      // 1f: bipush 1
      // 20: invokevirtual java/lang/Thread.setDaemon (Z)V
      // 23: aload 1
      // 24: invokevirtual java/lang/Thread.start ()V
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 1
      // 2a: areturn
      // 2b: astore 1
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
   }

   private fun notifyStartup(): Boolean {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: invokespecial kotlinx/coroutines/DefaultExecutor.isShutdownRequested ()Z
      // 06: istore 1
      // 07: iload 1
      // 08: ifeq 0f
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: bipush 0
      // 0e: ireturn
      // 0f: bipush 1
      // 10: putstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 13: aload 0
      // 14: ldc "null cannot be cast to non-null type java.lang.Object"
      // 16: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 19: aload 0
      // 1a: checkcast java/lang/Object
      // 1d: invokevirtual java/lang/Object.notifyAll ()V
      // 20: aload 0
      // 21: monitorexit
      // 22: bipush 1
      // 23: ireturn
      // 24: astore 2
      // 25: aload 0
      // 26: monitorexit
      // 27: aload 2
      // 28: athrow
   }

   private fun shutdownError() {
      throw new RejectedExecutionException(
         "DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details"
      );
   }

   public override fun enqueue(task: Runnable) {
      if (this.isShutDown()) {
         this.shutdownError();
      }

      super.enqueue(var1);
   }

   internal fun ensureStarted() {
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
      // 01: monitorenter
      // 02: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 05: ifeq 1b
      // 08: getstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 0b: ifnonnull 11
      // 0e: goto 1b
      // 11: new java/lang/AssertionError
      // 14: astore 1
      // 15: aload 1
      // 16: invokespecial java/lang/AssertionError.<init> ()V
      // 19: aload 1
      // 1a: athrow
      // 1b: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 1e: ifeq 3b
      // 21: getstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 24: ifeq 3b
      // 27: getstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 2a: bipush 3
      // 2b: if_icmpne 31
      // 2e: goto 3b
      // 31: new java/lang/AssertionError
      // 34: astore 1
      // 35: aload 1
      // 36: invokespecial java/lang/AssertionError.<init> ()V
      // 39: aload 1
      // 3a: athrow
      // 3b: bipush 0
      // 3c: putstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 3f: aload 0
      // 40: invokespecial kotlinx/coroutines/DefaultExecutor.createThreadSync ()Ljava/lang/Thread;
      // 43: pop
      // 44: getstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 47: ifne 5a
      // 4a: aload 0
      // 4b: ldc "null cannot be cast to non-null type java.lang.Object"
      // 4d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 50: aload 0
      // 51: checkcast java/lang/Object
      // 54: invokevirtual java/lang/Object.wait ()V
      // 57: goto 44
      // 5a: aload 0
      // 5b: monitorexit
      // 5c: return
      // 5d: astore 1
      // 5e: aload 0
      // 5f: monitorexit
      // 60: aload 1
      // 61: athrow
   }

   public override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
      return this.scheduleInvokeOnTimeout(var1, var3);
   }

   protected override fun reschedule(now: Long, delayedTask: DelayedTask) {
      this.shutdownError();
   }

   public override fun run() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 000: getstatic kotlinx/coroutines/ThreadLocalEventLoop.INSTANCE Lkotlinx/coroutines/ThreadLocalEventLoop;
      // 003: aload 0
      // 004: checkcast kotlinx/coroutines/EventLoop
      // 007: invokevirtual kotlinx/coroutines/ThreadLocalEventLoop.setEventLoop$kotlinx_coroutines_core (Lkotlinx/coroutines/EventLoop;)V
      // 00a: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 00d: astore 10
      // 00f: aload 10
      // 011: ifnull 019
      // 014: aload 10
      // 016: invokevirtual kotlinx/coroutines/AbstractTimeSource.registerTimeLoopThread ()V
      // 019: aload 0
      // 01a: invokespecial kotlinx/coroutines/DefaultExecutor.notifyStartup ()Z
      // 01d: istore 9
      // 01f: iload 9
      // 021: ifne 048
      // 024: aconst_null
      // 025: putstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 028: aload 0
      // 029: invokespecial kotlinx/coroutines/DefaultExecutor.acknowledgeShutdownIfNeeded ()V
      // 02c: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 02f: astore 10
      // 031: aload 10
      // 033: ifnull 03b
      // 036: aload 10
      // 038: invokevirtual kotlinx/coroutines/AbstractTimeSource.unregisterTimeLoopThread ()V
      // 03b: aload 0
      // 03c: invokevirtual kotlinx/coroutines/DefaultExecutor.isEmpty ()Z
      // 03f: ifne 047
      // 042: aload 0
      // 043: invokevirtual kotlinx/coroutines/DefaultExecutor.getThread ()Ljava/lang/Thread;
      // 046: pop
      // 047: return
      // 048: ldc2_w 9223372036854775807
      // 04b: lstore 1
      // 04c: invokestatic java/lang/Thread.interrupted ()Z
      // 04f: pop
      // 050: aload 0
      // 051: invokevirtual kotlinx/coroutines/DefaultExecutor.processNextEvent ()J
      // 054: lstore 7
      // 056: lload 7
      // 058: ldc2_w 9223372036854775807
      // 05b: lcmp
      // 05c: ifne 0c5
      // 05f: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 062: astore 10
      // 064: aload 10
      // 066: ifnull 073
      // 069: aload 10
      // 06b: invokevirtual kotlinx/coroutines/AbstractTimeSource.nanoTime ()J
      // 06e: lstore 5
      // 070: goto 078
      // 073: invokestatic java/lang/System.nanoTime ()J
      // 076: lstore 5
      // 078: lload 1
      // 079: lstore 3
      // 07a: lload 1
      // 07b: ldc2_w 9223372036854775807
      // 07e: lcmp
      // 07f: ifne 08b
      // 082: getstatic kotlinx/coroutines/DefaultExecutor.KEEP_ALIVE_NANOS J
      // 085: lstore 1
      // 086: lload 1
      // 087: lload 5
      // 089: ladd
      // 08a: lstore 3
      // 08b: lload 3
      // 08c: lload 5
      // 08e: lsub
      // 08f: lstore 1
      // 090: lload 1
      // 091: lconst_0
      // 092: lcmp
      // 093: ifgt 0ba
      // 096: aconst_null
      // 097: putstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 09a: aload 0
      // 09b: invokespecial kotlinx/coroutines/DefaultExecutor.acknowledgeShutdownIfNeeded ()V
      // 09e: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 0a1: astore 10
      // 0a3: aload 10
      // 0a5: ifnull 0ad
      // 0a8: aload 10
      // 0aa: invokevirtual kotlinx/coroutines/AbstractTimeSource.unregisterTimeLoopThread ()V
      // 0ad: aload 0
      // 0ae: invokevirtual kotlinx/coroutines/DefaultExecutor.isEmpty ()Z
      // 0b1: ifne 0b9
      // 0b4: aload 0
      // 0b5: invokevirtual kotlinx/coroutines/DefaultExecutor.getThread ()Ljava/lang/Thread;
      // 0b8: pop
      // 0b9: return
      // 0ba: lload 7
      // 0bc: lload 1
      // 0bd: invokestatic kotlin/ranges/RangesKt.coerceAtMost (JJ)J
      // 0c0: lstore 5
      // 0c2: goto 0cd
      // 0c5: ldc2_w 9223372036854775807
      // 0c8: lstore 3
      // 0c9: lload 7
      // 0cb: lstore 5
      // 0cd: lload 3
      // 0ce: lstore 1
      // 0cf: lload 5
      // 0d1: lconst_0
      // 0d2: lcmp
      // 0d3: ifle 04c
      // 0d6: aload 0
      // 0d7: invokespecial kotlinx/coroutines/DefaultExecutor.isShutdownRequested ()Z
      // 0da: istore 9
      // 0dc: iload 9
      // 0de: ifeq 105
      // 0e1: aconst_null
      // 0e2: putstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 0e5: aload 0
      // 0e6: invokespecial kotlinx/coroutines/DefaultExecutor.acknowledgeShutdownIfNeeded ()V
      // 0e9: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 0ec: astore 10
      // 0ee: aload 10
      // 0f0: ifnull 0f8
      // 0f3: aload 10
      // 0f5: invokevirtual kotlinx/coroutines/AbstractTimeSource.unregisterTimeLoopThread ()V
      // 0f8: aload 0
      // 0f9: invokevirtual kotlinx/coroutines/DefaultExecutor.isEmpty ()Z
      // 0fc: ifne 104
      // 0ff: aload 0
      // 100: invokevirtual kotlinx/coroutines/DefaultExecutor.getThread ()Ljava/lang/Thread;
      // 103: pop
      // 104: return
      // 105: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 108: astore 10
      // 10a: aload 10
      // 10c: ifnull 11f
      // 10f: aload 10
      // 111: aload 0
      // 112: lload 5
      // 114: invokevirtual kotlinx/coroutines/AbstractTimeSource.parkNanos (Ljava/lang/Object;J)V
      // 117: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 11a: astore 10
      // 11c: goto 122
      // 11f: aconst_null
      // 120: astore 10
      // 122: lload 3
      // 123: lstore 1
      // 124: aload 10
      // 126: ifnonnull 04c
      // 129: aload 0
      // 12a: lload 5
      // 12c: invokestatic java/util/concurrent/locks/LockSupport.parkNanos (Ljava/lang/Object;J)V
      // 12f: lload 3
      // 130: lstore 1
      // 131: goto 04c
      // 134: astore 10
      // 136: aconst_null
      // 137: putstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 13a: aload 0
      // 13b: invokespecial kotlinx/coroutines/DefaultExecutor.acknowledgeShutdownIfNeeded ()V
      // 13e: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 141: astore 11
      // 143: aload 11
      // 145: ifnull 14d
      // 148: aload 11
      // 14a: invokevirtual kotlinx/coroutines/AbstractTimeSource.unregisterTimeLoopThread ()V
      // 14d: aload 0
      // 14e: invokevirtual kotlinx/coroutines/DefaultExecutor.isEmpty ()Z
      // 151: ifne 159
      // 154: aload 0
      // 155: invokevirtual kotlinx/coroutines/DefaultExecutor.getThread ()Ljava/lang/Thread;
      // 158: pop
      // 159: aload 10
      // 15b: athrow
   }

   public override fun shutdown() {
      debugStatus = 4;
      super.shutdown();
   }

   public fun shutdownForTests(timeout: Long) {
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
      // 01: monitorenter
      // 02: invokestatic java/lang/System.currentTimeMillis ()J
      // 05: lstore 3
      // 06: aload 0
      // 07: invokespecial kotlinx/coroutines/DefaultExecutor.isShutdownRequested ()Z
      // 0a: ifne 11
      // 0d: bipush 2
      // 0e: putstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 11: getstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 14: bipush 3
      // 15: if_icmpeq 6b
      // 18: getstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 1b: ifnull 6b
      // 1e: getstatic kotlinx/coroutines/DefaultExecutor._thread Ljava/lang/Thread;
      // 21: astore 6
      // 23: aload 6
      // 25: ifnull 4e
      // 28: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 2b: astore 5
      // 2d: aload 5
      // 2f: ifnull 41
      // 32: aload 5
      // 34: aload 6
      // 36: invokevirtual kotlinx/coroutines/AbstractTimeSource.unpark (Ljava/lang/Thread;)V
      // 39: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 3c: astore 5
      // 3e: goto 44
      // 41: aconst_null
      // 42: astore 5
      // 44: aload 5
      // 46: ifnonnull 4e
      // 49: aload 6
      // 4b: invokestatic java/util/concurrent/locks/LockSupport.unpark (Ljava/lang/Thread;)V
      // 4e: lload 3
      // 4f: lload 1
      // 50: ladd
      // 51: invokestatic java/lang/System.currentTimeMillis ()J
      // 54: lsub
      // 55: lconst_0
      // 56: lcmp
      // 57: ifle 6b
      // 5a: aload 0
      // 5b: ldc "null cannot be cast to non-null type java.lang.Object"
      // 5d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 60: aload 0
      // 61: checkcast java/lang/Object
      // 64: lload 1
      // 65: invokevirtual java/lang/Object.wait (J)V
      // 68: goto 11
      // 6b: bipush 0
      // 6c: putstatic kotlinx/coroutines/DefaultExecutor.debugStatus I
      // 6f: aload 0
      // 70: monitorexit
      // 71: return
      // 72: astore 5
      // 74: aload 0
      // 75: monitorexit
      // 76: aload 5
      // 78: athrow
   }
}
