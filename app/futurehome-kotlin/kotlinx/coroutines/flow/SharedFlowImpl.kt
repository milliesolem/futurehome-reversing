package kotlinx.coroutines.flow

import java.util.Arrays
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.internal.AbstractSharedFlow
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot
import kotlinx.coroutines.flow.internal.FusibleFlow

internal open class SharedFlowImpl<T>(replay: Int, bufferCapacity: Int, onBufferOverflow: BufferOverflow)
   : AbstractSharedFlow<SharedFlowSlot>,
   MutableSharedFlow<T>,
   CancellableFlow<T>,
   FusibleFlow<T> {
   private final var buffer: Array<Any?>?
   private final val bufferCapacity: Int

   private final val bufferEndIndex: Long
      private final get() {
         return this.getHead() + this.bufferSize;
      }


   private final var bufferSize: Int

   private final val head: Long
      private final get() {
         return Math.min(this.minCollectorIndex, this.replayIndex);
      }


   protected final val lastReplayedLocked: Any
      protected final get() {
         val var1: Array<Any> = this.buffer;
         return (T)SharedFlowKt.access$getBufferAt(var1, this.replayIndex + (long)this.getReplaySize() - 1L);
      }


   private final var minCollectorIndex: Long
   private final val onBufferOverflow: BufferOverflow

   private final val queueEndIndex: Long
      private final get() {
         return this.getHead() + this.bufferSize + this.queueSize;
      }


   private final var queueSize: Int
   private final val replay: Int

   public open val replayCache: List<Any>
      public open get() {
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
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.getReplaySize ()I
         // 06: istore 2
         // 07: iload 2
         // 08: ifne 13
         // 0b: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
         // 0e: astore 3
         // 0f: aload 0
         // 10: monitorexit
         // 11: aload 3
         // 12: areturn
         // 13: new java/util/ArrayList
         // 16: astore 3
         // 17: aload 3
         // 18: iload 2
         // 19: invokespecial java/util/ArrayList.<init> (I)V
         // 1c: aload 0
         // 1d: getfield kotlinx/coroutines/flow/SharedFlowImpl.buffer [Ljava/lang/Object;
         // 20: astore 4
         // 22: aload 4
         // 24: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 27: bipush 0
         // 28: istore 1
         // 29: iload 1
         // 2a: iload 2
         // 2b: if_icmpge 4a
         // 2e: aload 3
         // 2f: checkcast java/util/Collection
         // 32: aload 4
         // 34: aload 0
         // 35: getfield kotlinx/coroutines/flow/SharedFlowImpl.replayIndex J
         // 38: iload 1
         // 39: i2l
         // 3a: ladd
         // 3b: invokestatic kotlinx/coroutines/flow/SharedFlowKt.access$getBufferAt ([Ljava/lang/Object;J)Ljava/lang/Object;
         // 3e: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 43: pop
         // 44: iinc 1 1
         // 47: goto 29
         // 4a: aload 0
         // 4b: monitorexit
         // 4c: aload 3
         // 4d: checkcast java/util/List
         // 50: areturn
         // 51: astore 3
         // 52: aload 0
         // 53: monitorexit
         // 54: aload 3
         // 55: athrow
      }


   private final var replayIndex: Long

   private final val replaySize: Int
      private final get() {
         return (int)(this.getHead() + this.bufferSize - this.replayIndex);
      }


   private final val totalSize: Int
      private final get() {
         return this.bufferSize + this.queueSize;
      }


   init {
      this.replay = var1;
      this.bufferCapacity = var2;
      this.onBufferOverflow = var3;
   }

   private suspend fun awaitValue(slot: SharedFlowSlot) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: new kotlinx/coroutines/CancellableContinuationImpl
      // 03: dup
      // 04: aload 2
      // 05: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
      // 08: bipush 1
      // 09: invokespecial kotlinx/coroutines/CancellableContinuationImpl.<init> (Lkotlin/coroutines/Continuation;I)V
      // 0c: astore 3
      // 0d: aload 3
      // 0e: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.initCancellability ()V
      // 11: aload 3
      // 12: checkcast kotlinx/coroutines/CancellableContinuation
      // 15: astore 4
      // 17: aload 0
      // 18: monitorenter
      // 19: aload 0
      // 1a: aload 1
      // 1b: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$tryPeekLocked (Lkotlinx/coroutines/flow/SharedFlowImpl;Lkotlinx/coroutines/flow/SharedFlowSlot;)J
      // 1e: lconst_0
      // 1f: lcmp
      // 20: ifge 38
      // 23: aload 1
      // 24: aload 4
      // 26: checkcast kotlin/coroutines/Continuation
      // 29: putfield kotlinx/coroutines/flow/SharedFlowSlot.cont Lkotlin/coroutines/Continuation;
      // 2c: aload 1
      // 2d: aload 4
      // 2f: checkcast kotlin/coroutines/Continuation
      // 32: putfield kotlinx/coroutines/flow/SharedFlowSlot.cont Lkotlin/coroutines/Continuation;
      // 35: goto 50
      // 38: aload 4
      // 3a: checkcast kotlin/coroutines/Continuation
      // 3d: astore 4
      // 3f: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 42: astore 1
      // 43: aload 4
      // 45: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 48: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4b: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 50: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 53: astore 1
      // 54: aload 0
      // 55: monitorexit
      // 56: aload 3
      // 57: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
      // 5a: astore 1
      // 5b: aload 1
      // 5c: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 5f: if_acmpne 66
      // 62: aload 2
      // 63: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
      // 66: aload 1
      // 67: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 6a: if_acmpne 6f
      // 6d: aload 1
      // 6e: areturn
      // 6f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 72: areturn
      // 73: astore 1
      // 74: aload 0
      // 75: monitorexit
      // 76: aload 1
      // 77: athrow
   }

   private fun cancelEmitter(emitter: kotlinx.coroutines.flow.SharedFlowImpl.Emitter) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: getfield kotlinx/coroutines/flow/SharedFlowImpl$Emitter.index J
      // 06: lstore 4
      // 08: aload 0
      // 09: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.getHead ()J
      // 0c: lstore 2
      // 0d: lload 4
      // 0f: lload 2
      // 10: lcmp
      // 11: ifge 17
      // 14: aload 0
      // 15: monitorexit
      // 16: return
      // 17: aload 0
      // 18: getfield kotlinx/coroutines/flow/SharedFlowImpl.buffer [Ljava/lang/Object;
      // 1b: astore 6
      // 1d: aload 6
      // 1f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 22: aload 6
      // 24: aload 1
      // 25: getfield kotlinx/coroutines/flow/SharedFlowImpl$Emitter.index J
      // 28: invokestatic kotlinx/coroutines/flow/SharedFlowKt.access$getBufferAt ([Ljava/lang/Object;J)Ljava/lang/Object;
      // 2b: astore 7
      // 2d: aload 7
      // 2f: aload 1
      // 30: if_acmpeq 36
      // 33: aload 0
      // 34: monitorexit
      // 35: return
      // 36: aload 6
      // 38: aload 1
      // 39: getfield kotlinx/coroutines/flow/SharedFlowImpl$Emitter.index J
      // 3c: getstatic kotlinx/coroutines/flow/SharedFlowKt.NO_VALUE Lkotlinx/coroutines/internal/Symbol;
      // 3f: invokestatic kotlinx/coroutines/flow/SharedFlowKt.access$setBufferAt ([Ljava/lang/Object;JLjava/lang/Object;)V
      // 42: aload 0
      // 43: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.cleanupTailLocked ()V
      // 46: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 49: astore 1
      // 4a: aload 0
      // 4b: monitorexit
      // 4c: return
      // 4d: astore 1
      // 4e: aload 0
      // 4f: monitorexit
      // 50: aload 1
      // 51: athrow
   }

   private fun cleanupTailLocked() {
      if (this.bufferCapacity != 0 || this.queueSize > 1) {
         val var1: Array<Any> = this.buffer;

         while (this.queueSize > 0 && SharedFlowKt.access$getBufferAt(var1, this.getHead() + this.getTotalSize() - 1L) == SharedFlowKt.NO_VALUE) {
            this.queueSize--;
            SharedFlowKt.access$setBufferAt(var1, this.getHead() + (long)this.getTotalSize(), null);
         }
      }
   }

   private fun correctCollectorIndexesOnDropOldest(newHead: Long) {
      val var5: AbstractSharedFlow = this;
      if (AbstractSharedFlow.access$getNCollectors(this) != 0) {
         val var7: Array<AbstractSharedFlowSlot> = AbstractSharedFlow.access$getSlots(var5);
         if (var7 != null) {
            val var4: Int = var7.length;

            for (int var3 = 0; var3 < var4; var3++) {
               val var6: AbstractSharedFlowSlot = var7[var3];
               if (var7[var3] != null) {
                  val var8: SharedFlowSlot = var6 as SharedFlowSlot;
                  if ((var6 as SharedFlowSlot).index >= 0L && (var6 as SharedFlowSlot).index < var1) {
                     var8.index = var1;
                  }
               }
            }
         }
      }

      this.minCollectorIndex = var1;
   }

   private fun dropOldestLocked() {
      val var3: Array<Any> = this.buffer;
      SharedFlowKt.access$setBufferAt(var3, this.getHead(), null);
      this.bufferSize--;
      val var1: Long = this.getHead() + 1L;
      if (this.replayIndex < var1) {
         this.replayIndex = var1;
      }

      if (this.minCollectorIndex < var1) {
         this.correctCollectorIndexesOnDropOldest(var1);
      }

      if (DebugKt.getASSERTIONS_ENABLED() && this.getHead() != var1) {
         throw new AssertionError();
      }
   }

   private suspend fun emitSuspend(value: Any) {
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
      // 00: new kotlinx/coroutines/CancellableContinuationImpl
      // 03: dup
      // 04: aload 2
      // 05: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
      // 08: bipush 1
      // 09: invokespecial kotlinx/coroutines/CancellableContinuationImpl.<init> (Lkotlin/coroutines/Continuation;I)V
      // 0c: astore 9
      // 0e: aload 9
      // 10: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.initCancellability ()V
      // 13: aload 9
      // 15: checkcast kotlinx/coroutines/CancellableContinuation
      // 18: astore 10
      // 1a: getstatic kotlinx/coroutines/flow/internal/AbstractSharedFlowKt.EMPTY_RESUMES [Lkotlin/coroutines/Continuation;
      // 1d: astore 7
      // 1f: aload 0
      // 20: monitorenter
      // 21: aload 0
      // 22: aload 1
      // 23: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$tryEmitLocked (Lkotlinx/coroutines/flow/SharedFlowImpl;Ljava/lang/Object;)Z
      // 26: ifeq 4e
      // 29: aload 10
      // 2b: checkcast kotlin/coroutines/Continuation
      // 2e: astore 8
      // 30: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 33: astore 1
      // 34: aload 8
      // 36: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 39: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 3c: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 41: aload 0
      // 42: aload 7
      // 44: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$findSlotsToResumeLocked (Lkotlinx/coroutines/flow/SharedFlowImpl;[Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;
      // 47: astore 1
      // 48: aconst_null
      // 49: astore 7
      // 4b: goto 92
      // 4e: new kotlinx/coroutines/flow/SharedFlowImpl$Emitter
      // 51: astore 8
      // 53: aload 0
      // 54: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$getHead (Lkotlinx/coroutines/flow/SharedFlowImpl;)J
      // 57: lstore 5
      // 59: aload 8
      // 5b: aload 0
      // 5c: aload 0
      // 5d: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$getTotalSize (Lkotlinx/coroutines/flow/SharedFlowImpl;)I
      // 60: i2l
      // 61: lload 5
      // 63: ladd
      // 64: aload 1
      // 65: aload 10
      // 67: checkcast kotlin/coroutines/Continuation
      // 6a: invokespecial kotlinx/coroutines/flow/SharedFlowImpl$Emitter.<init> (Lkotlinx/coroutines/flow/SharedFlowImpl;JLjava/lang/Object;Lkotlin/coroutines/Continuation;)V
      // 6d: aload 0
      // 6e: aload 8
      // 70: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$enqueueLocked (Lkotlinx/coroutines/flow/SharedFlowImpl;Ljava/lang/Object;)V
      // 73: aload 0
      // 74: aload 0
      // 75: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$getQueueSize$p (Lkotlinx/coroutines/flow/SharedFlowImpl;)I
      // 78: bipush 1
      // 79: iadd
      // 7a: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$setQueueSize$p (Lkotlinx/coroutines/flow/SharedFlowImpl;I)V
      // 7d: aload 7
      // 7f: astore 1
      // 80: aload 0
      // 81: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$getBufferCapacity$p (Lkotlinx/coroutines/flow/SharedFlowImpl;)I
      // 84: ifne 8e
      // 87: aload 0
      // 88: aload 7
      // 8a: invokestatic kotlinx/coroutines/flow/SharedFlowImpl.access$findSlotsToResumeLocked (Lkotlinx/coroutines/flow/SharedFlowImpl;[Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;
      // 8d: astore 1
      // 8e: aload 8
      // 90: astore 7
      // 92: aload 0
      // 93: monitorexit
      // 94: aload 7
      // 96: ifnull a3
      // 99: aload 10
      // 9b: aload 7
      // 9d: checkcast kotlinx/coroutines/DisposableHandle
      // a0: invokestatic kotlinx/coroutines/CancellableContinuationKt.disposeOnCancellation (Lkotlinx/coroutines/CancellableContinuation;Lkotlinx/coroutines/DisposableHandle;)V
      // a3: aload 1
      // a4: arraylength
      // a5: istore 4
      // a7: bipush 0
      // a8: istore 3
      // a9: iload 3
      // aa: iload 4
      // ac: if_icmpge d1
      // af: aload 1
      // b0: iload 3
      // b1: aaload
      // b2: astore 8
      // b4: aload 8
      // b6: ifnull cb
      // b9: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // bc: astore 7
      // be: aload 8
      // c0: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // c3: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // c6: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // cb: iinc 3 1
      // ce: goto a9
      // d1: aload 9
      // d3: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
      // d6: astore 1
      // d7: aload 1
      // d8: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // db: if_acmpne e2
      // de: aload 2
      // df: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
      // e2: aload 1
      // e3: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // e6: if_acmpne eb
      // e9: aload 1
      // ea: areturn
      // eb: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // ee: areturn
      // ef: astore 1
      // f0: aload 0
      // f1: monitorexit
      // f2: aload 1
      // f3: athrow
   }

   private fun enqueueLocked(item: Any?) {
      val var2: Int = this.getTotalSize();
      var var3: Array<Any>;
      if (this.buffer == null) {
         var3 = this.growBuffer(null, 0, 2);
      } else {
         var3 = this.buffer;
         if (var2 >= this.buffer.length) {
            var3 = this.growBuffer(this.buffer, var2, this.buffer.length * 2);
         }
      }

      SharedFlowKt.access$setBufferAt(var3, this.getHead() + (long)var2, var1);
   }

   private fun findSlotsToResumeLocked(resumesIn: Array<Continuation<Unit>?>): Array<Continuation<Unit>?> {
      var var3: Int = ((Object[])var1).length;
      val var7: AbstractSharedFlow = this;
      var var6: Any = var1;
      if (AbstractSharedFlow.access$getNCollectors(var7) != 0) {
         val var11: Array<AbstractSharedFlowSlot> = AbstractSharedFlow.access$getSlots(var7);
         var6 = var1;
         if (var11 != null) {
            val var5: Int = var11.length;
            var var2: Int = 0;

            while (true) {
               var6 = var1;
               if (var2 >= var5) {
                  break;
               }

               val var8: AbstractSharedFlowSlot = var11[var2];
               var var4: Int = var3;
               var6 = var1;
               if (var8 != null) {
                  val var9: SharedFlowSlot = var8 as SharedFlowSlot;
                  val var12: Continuation = (var8 as SharedFlowSlot).cont;
                  if ((var8 as SharedFlowSlot).cont == null) {
                     var4 = var3;
                     var6 = var1;
                  } else {
                     var4 = var3;
                     var6 = var1;
                     if (this.tryPeekLocked(var9) >= 0L) {
                        var6 = var1;
                        if (var3 >= ((Object[])var1).length) {
                           var6 = Arrays.copyOf((T[])var1, Math.max(2, ((Object[])var1).length * 2));
                        }

                        (var6 as Array<Continuation>)[var3] = var12;
                        var9.cont = null;
                        var4 = var3 + 1;
                     }
                  }
               }

               var2++;
               var3 = var4;
               var1 = var6;
            }
         }
      }

      return var6 as Array<Continuation<Unit>>;
   }

   private fun getPeekedValueLockedAt(index: Long): Any? {
      var var3: Array<Any> = this.buffer;
      val var4: Any = SharedFlowKt.access$getBufferAt(var3, var1);
      var3 = (Object[])var4;
      if (var4 is SharedFlowImpl.Emitter) {
         var3 = (Object[])(var4 as SharedFlowImpl.Emitter).value;
      }

      return var3;
   }

   private fun growBuffer(curBuffer: Array<Any?>?, curSize: Int, newSize: Int): Array<Any?> {
      if (var3 <= 0) {
         throw new IllegalStateException("Buffer size overflow".toString());
      } else {
         val var8: Array<Any> = new Object[var3];
         this.buffer = var8;
         if (var1 == null) {
            return var8;
         } else {
            val var4: Long = this.getHead();

            for (int var9 = 0; var9 < var2; var9++) {
               SharedFlowKt.access$setBufferAt(var8, (long)var9 + var4, SharedFlowKt.access$getBufferAt(var1, (long)var9 + var4));
            }

            return var8;
         }
      }
   }

   private fun tryEmitLocked(value: Any): Boolean {
      if (this.getNCollectors() == 0) {
         return this.tryEmitNoCollectorsLocked((T)var1);
      } else {
         if (this.bufferSize >= this.bufferCapacity && this.minCollectorIndex <= this.replayIndex) {
            val var2: Int = SharedFlowImpl.WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()];
            if (var2 == 1) {
               return false;
            }

            if (var2 == 2) {
               return true;
            }
         }

         this.enqueueLocked(var1);
         val var4: Int = this.bufferSize + 1;
         this.bufferSize += 1;
         if (var4 > this.bufferCapacity) {
            this.dropOldestLocked();
         }

         if (this.getReplaySize() > this.replay) {
            this.updateBufferLocked(this.replayIndex + 1L, this.minCollectorIndex, this.getBufferEndIndex(), this.getQueueEndIndex());
         }

         return true;
      }
   }

   private fun tryEmitNoCollectorsLocked(value: Any): Boolean {
      if (DebugKt.getASSERTIONS_ENABLED() && this.getNCollectors() != 0) {
         throw new AssertionError();
      } else if (this.replay == 0) {
         return true;
      } else {
         this.enqueueLocked(var1);
         val var2: Int = this.bufferSize + 1;
         this.bufferSize += 1;
         if (var2 > this.replay) {
            this.dropOldestLocked();
         }

         this.minCollectorIndex = this.getHead() + this.bufferSize;
         return true;
      }
   }

   private fun tryPeekLocked(slot: SharedFlowSlot): Long {
      val var2: Long = var1.index;
      if (var1.index < this.getBufferEndIndex()) {
         return var2;
      } else if (this.bufferCapacity > 0) {
         return -1L;
      } else if (var2 > this.getHead()) {
         return -1L;
      } else {
         return if (this.queueSize == 0) -1L else var2;
      }
   }

   private fun tryTakeValue(slot: SharedFlowSlot): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: getstatic kotlinx/coroutines/flow/internal/AbstractSharedFlowKt.EMPTY_RESUMES [Lkotlin/coroutines/Continuation;
      // 03: astore 9
      // 05: aload 0
      // 06: monitorenter
      // 07: aload 0
      // 08: aload 1
      // 09: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.tryPeekLocked (Lkotlinx/coroutines/flow/SharedFlowSlot;)J
      // 0c: lstore 6
      // 0e: lload 6
      // 10: lconst_0
      // 11: lcmp
      // 12: ifge 20
      // 15: getstatic kotlinx/coroutines/flow/SharedFlowKt.NO_VALUE Lkotlinx/coroutines/internal/Symbol;
      // 18: astore 8
      // 1a: aload 9
      // 1c: astore 1
      // 1d: goto 3d
      // 20: aload 1
      // 21: getfield kotlinx/coroutines/flow/SharedFlowSlot.index J
      // 24: lstore 4
      // 26: aload 0
      // 27: lload 6
      // 29: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.getPeekedValueLockedAt (J)Ljava/lang/Object;
      // 2c: astore 8
      // 2e: aload 1
      // 2f: lload 6
      // 31: lconst_1
      // 32: ladd
      // 33: putfield kotlinx/coroutines/flow/SharedFlowSlot.index J
      // 36: aload 0
      // 37: lload 4
      // 39: invokevirtual kotlinx/coroutines/flow/SharedFlowImpl.updateCollectorIndexLocked$kotlinx_coroutines_core (J)[Lkotlin/coroutines/Continuation;
      // 3c: astore 1
      // 3d: aload 0
      // 3e: monitorexit
      // 3f: aload 1
      // 40: arraylength
      // 41: istore 3
      // 42: bipush 0
      // 43: istore 2
      // 44: iload 2
      // 45: iload 3
      // 46: if_icmpge 6b
      // 49: aload 1
      // 4a: iload 2
      // 4b: aaload
      // 4c: astore 9
      // 4e: aload 9
      // 50: ifnull 65
      // 53: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 56: astore 10
      // 58: aload 9
      // 5a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 5d: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 60: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 65: iinc 2 1
      // 68: goto 44
      // 6b: aload 8
      // 6d: areturn
      // 6e: astore 1
      // 6f: aload 0
      // 70: monitorexit
      // 71: aload 1
      // 72: athrow
   }

   private fun updateBufferLocked(newReplayIndex: Long, newMinCollectorIndex: Long, newBufferEndIndex: Long, newQueueEndIndex: Long) {
      val var11: Long = Math.min(var3, var1);
      if (DebugKt.getASSERTIONS_ENABLED() && var11 < this.getHead()) {
         throw new AssertionError();
      } else {
         for (long var9 = this.getHead(); var9 < var11; var9++) {
            val var13: Array<Any> = this.buffer;
            SharedFlowKt.access$setBufferAt(var13, var9, null);
         }

         this.replayIndex = var1;
         this.minCollectorIndex = var3;
         this.bufferSize = (int)(var5 - var11);
         this.queueSize = (int)(var7 - var5);
         if (DebugKt.getASSERTIONS_ENABLED() && this.bufferSize < 0) {
            throw new AssertionError();
         } else if (DebugKt.getASSERTIONS_ENABLED() && this.queueSize < 0) {
            throw new AssertionError();
         } else if (DebugKt.getASSERTIONS_ENABLED() && this.replayIndex > this.getHead() + this.bufferSize) {
            throw new AssertionError();
         }
      }
   }

   public override suspend fun collect(collector: FlowCollector<Any>): Nothing {
      return collect$suspendImpl(this, var1, var2);
   }

   protected open fun createSlot(): SharedFlowSlot {
      return new SharedFlowSlot();
   }

   protected open fun createSlotArray(size: Int): Array<SharedFlowSlot?> {
      return new SharedFlowSlot[var1];
   }

   public override suspend fun emit(value: Any) {
      return emit$suspendImpl(this, (T)var1, var2);
   }

   public override fun fuse(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): Flow<Any> {
      return SharedFlowKt.fuseSharedFlow(this, var1, var2, var3);
   }

   public override fun resetReplayCache() {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            this.updateBufferLocked(this.getBufferEndIndex(), this.minCollectorIndex, this.getBufferEndIndex(), this.getQueueEndIndex());
         } catch (var2: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public override fun tryEmit(value: Any): Boolean {
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
      // 00: getstatic kotlinx/coroutines/flow/internal/AbstractSharedFlowKt.EMPTY_RESUMES [Lkotlin/coroutines/Continuation;
      // 03: astore 5
      // 05: aload 0
      // 06: monitorenter
      // 07: aload 0
      // 08: aload 1
      // 09: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.tryEmitLocked (Ljava/lang/Object;)Z
      // 0c: istore 4
      // 0e: bipush 0
      // 0f: istore 2
      // 10: iload 4
      // 12: ifeq 22
      // 15: aload 0
      // 16: aload 5
      // 18: invokespecial kotlinx/coroutines/flow/SharedFlowImpl.findSlotsToResumeLocked ([Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;
      // 1b: astore 1
      // 1c: bipush 1
      // 1d: istore 4
      // 1f: goto 28
      // 22: bipush 0
      // 23: istore 4
      // 25: aload 5
      // 27: astore 1
      // 28: aload 0
      // 29: monitorexit
      // 2a: aload 1
      // 2b: arraylength
      // 2c: istore 3
      // 2d: iload 2
      // 2e: iload 3
      // 2f: if_icmpge 54
      // 32: aload 1
      // 33: iload 2
      // 34: aaload
      // 35: astore 5
      // 37: aload 5
      // 39: ifnull 4e
      // 3c: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 3f: astore 6
      // 41: aload 5
      // 43: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 46: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 49: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 4e: iinc 2 1
      // 51: goto 2d
      // 54: iload 4
      // 56: ireturn
      // 57: astore 1
      // 58: aload 0
      // 59: monitorexit
      // 5a: aload 1
      // 5b: athrow
   }

   internal fun updateCollectorIndexLocked(oldIndex: Long): Array<Continuation<Unit>?> {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 < this.minCollectorIndex) {
         throw new AssertionError();
      } else if (var1 > this.minCollectorIndex) {
         return AbstractSharedFlowKt.EMPTY_RESUMES;
      } else {
         var var12: Long = this.getHead();
         var1 = this.bufferSize + var12;
         var var6: Long = this.bufferSize + var12;
         if (this.bufferCapacity == 0) {
            var6 = var1;
            if (this.queueSize > 0) {
               var6 = var1 + 1L;
            }
         }

         val var16: AbstractSharedFlow = this;
         var1 = var6;
         if (AbstractSharedFlow.access$getNCollectors(var16) != 0) {
            val var35: Array<AbstractSharedFlowSlot> = AbstractSharedFlow.access$getSlots(var16);
            var1 = var6;
            if (var35 != null) {
               val var4: Int = var35.length;
               var var3: Int = 0;

               while (true) {
                  var1 = var6;
                  if (var3 >= var4) {
                     break;
                  }

                  val var17: AbstractSharedFlowSlot = var35[var3];
                  var1 = var6;
                  if (var17 != null) {
                     val var37: SharedFlowSlot = var17 as SharedFlowSlot;
                     var1 = var6;
                     if (var37.index >= 0L) {
                        var1 = var6;
                        if (var37.index < var6) {
                           var1 = var37.index;
                        }
                     }
                  }

                  var3++;
                  var6 = var1;
               }
            }
         }

         if (DebugKt.getASSERTIONS_ENABLED() && var1 < this.minCollectorIndex) {
            throw new AssertionError();
         } else if (var1 <= this.minCollectorIndex) {
            return AbstractSharedFlowKt.EMPTY_RESUMES;
         } else {
            var6 = this.getBufferEndIndex();
            var var24: Int;
            if (this.getNCollectors() > 0) {
               var24 = Math.min(this.queueSize, this.bufferCapacity - (int)(var6 - var1));
            } else {
               var24 = this.queueSize;
            }

            var var36: Array<Continuation> = AbstractSharedFlowKt.EMPTY_RESUMES;
            val var14: Long = this.queueSize + var6;
            var var31: Long;
            if (var24 > 0) {
               var36 = new Continuation[var24];
               val var38: Array<Any> = this.buffer;
               var var27: Int = 0;
               var31 = var6;

               for (var6 = var6; var31 < var14; var31++) {
                  var var18: SharedFlowImpl.Emitter = (SharedFlowImpl.Emitter)SharedFlowKt.access$getBufferAt(var38, var31);
                  if (var18 != SharedFlowKt.NO_VALUE) {
                     var18 = var18;
                     val var5: Int = var27 + 1;
                     var36[var27] = var18.cont;
                     SharedFlowKt.access$setBufferAt(var38, var31, SharedFlowKt.NO_VALUE);
                     SharedFlowKt.access$setBufferAt(var38, var6, var18.value);
                     val var10: Long = var6 + 1L;
                     var6 += 1L;
                     if (var5 >= var24) {
                        break;
                     }

                     var27 = var5;
                     var6 = var10;
                  }
               }

               var31 = var1;
               var1 = var6;
            } else {
               var31 = var1;
               var1 = var6;
            }

            var24 = (int)(var1 - var12);
            if (this.getNCollectors() == 0) {
               var6 = var1;
            } else {
               var6 = var31;
            }

            var12 = Math.max(this.replayIndex, var1 - (long)Math.min(this.replay, var24));
            var var33: Long = var12;
            var31 = var1;
            if (this.bufferCapacity == 0) {
               var33 = var12;
               var31 = var1;
               if (var12 < var14) {
                  val var39: Array<Any> = this.buffer;
                  var33 = var12;
                  var31 = var1;
                  if (SharedFlowKt.access$getBufferAt(var39, var12) == SharedFlowKt.NO_VALUE) {
                     var31 = var1 + 1L;
                     var33 = var12 + 1L;
                  }
               }
            }

            this.updateBufferLocked(var33, var6, var31, var14);
            this.cleanupTailLocked();
            val var26: Boolean;
            if (var36.length == 0) {
               var26 = true;
            } else {
               var26 = false;
            }

            var var40: Array<Continuation> = var36;
            if (!var26) {
               var40 = this.findSlotsToResumeLocked(var36);
            }

            return var40;
         }
      }
   }

   internal fun updateNewCollectorIndexLocked(): Long {
      val var1: Long = this.replayIndex;
      if (this.replayIndex < this.minCollectorIndex) {
         this.minCollectorIndex = this.replayIndex;
      }

      return this.replayIndex;
   }

   private class Emitter(flow: SharedFlowImpl<*>, index: Long, value: Any?, cont: Continuation<Unit>) : DisposableHandle {
      public final val cont: Continuation<Unit>
      public final val flow: SharedFlowImpl<*>

      public final var index: Long
         private set

      public final val value: Any?

      init {
         this.flow = var1;
         this.index = var2;
         this.value = var4;
         this.cont = var5;
      }

      public override fun dispose() {
         SharedFlowImpl.access$cancelEmitter(this.flow, this);
      }
   }
}
