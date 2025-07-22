package kotlinx.coroutines.flow.internal

import kotlinx.coroutines.flow.StateFlow

internal abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>> {
   private final var _subscriptionCount: SubscriptionCountStateFlow?

   protected final var nCollectors: Int
      private set

   private final var nextIndex: Int

   protected final var slots: Array<Any?>?
      private set

   public final val subscriptionCount: StateFlow<Int>
      public final get() {
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
         // 02: aload 0
         // 03: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow._subscriptionCount Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;
         // 06: astore 2
         // 07: aload 2
         // 08: astore 1
         // 09: aload 2
         // 0a: ifnonnull 1e
         // 0d: new kotlinx/coroutines/flow/internal/SubscriptionCountStateFlow
         // 10: astore 1
         // 11: aload 1
         // 12: aload 0
         // 13: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nCollectors I
         // 16: invokespecial kotlinx/coroutines/flow/internal/SubscriptionCountStateFlow.<init> (I)V
         // 19: aload 0
         // 1a: aload 1
         // 1b: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow._subscriptionCount Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;
         // 1e: aload 0
         // 1f: monitorexit
         // 20: aload 1
         // 21: checkcast kotlinx/coroutines/flow/StateFlow
         // 24: areturn
         // 25: astore 1
         // 26: aload 0
         // 27: monitorexit
         // 28: aload 1
         // 29: athrow
      }


   protected fun allocateSlot(): Any {
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
      // 03: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.slots [Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 06: astore 4
      // 08: aload 4
      // 0a: ifnonnull 1b
      // 0d: aload 0
      // 0e: bipush 2
      // 0f: invokevirtual kotlinx/coroutines/flow/internal/AbstractSharedFlow.createSlotArray (I)[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 12: astore 3
      // 13: aload 0
      // 14: aload 3
      // 15: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.slots [Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 18: goto 46
      // 1b: aload 4
      // 1d: astore 3
      // 1e: aload 0
      // 1f: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nCollectors I
      // 22: aload 4
      // 24: arraylength
      // 25: if_icmplt 46
      // 28: aload 4
      // 2a: aload 4
      // 2c: arraylength
      // 2d: bipush 2
      // 2e: imul
      // 2f: invokestatic java/util/Arrays.copyOf ([Ljava/lang/Object;I)[Ljava/lang/Object;
      // 32: astore 3
      // 33: aload 3
      // 34: ldc "copyOf(this, newSize)"
      // 36: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 39: aload 0
      // 3a: aload 3
      // 3b: checkcast [Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 3e: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.slots [Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 41: aload 3
      // 42: checkcast [Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 45: astore 3
      // 46: aload 0
      // 47: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nextIndex I
      // 4a: istore 2
      // 4b: aload 3
      // 4c: iload 2
      // 4d: aaload
      // 4e: astore 5
      // 50: aload 5
      // 52: astore 4
      // 54: aload 5
      // 56: ifnonnull 64
      // 59: aload 0
      // 5a: invokevirtual kotlinx/coroutines/flow/internal/AbstractSharedFlow.createSlot ()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 5d: astore 4
      // 5f: aload 3
      // 60: iload 2
      // 61: aload 4
      // 63: aastore
      // 64: iinc 2 1
      // 67: iload 2
      // 68: istore 1
      // 69: iload 2
      // 6a: aload 3
      // 6b: arraylength
      // 6c: if_icmplt 71
      // 6f: bipush 0
      // 70: istore 1
      // 71: aload 4
      // 73: ldc "null cannot be cast to non-null type kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot<kotlin.Any>"
      // 75: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 78: iload 1
      // 79: istore 2
      // 7a: aload 4
      // 7c: aload 0
      // 7d: invokevirtual kotlinx/coroutines/flow/internal/AbstractSharedFlowSlot.allocateLocked (Ljava/lang/Object;)Z
      // 80: ifeq 4b
      // 83: aload 0
      // 84: iload 1
      // 85: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nextIndex I
      // 88: aload 0
      // 89: aload 0
      // 8a: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nCollectors I
      // 8d: bipush 1
      // 8e: iadd
      // 8f: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nCollectors I
      // 92: aload 0
      // 93: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow._subscriptionCount Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;
      // 96: astore 3
      // 97: aload 0
      // 98: monitorexit
      // 99: aload 3
      // 9a: ifnull a3
      // 9d: aload 3
      // 9e: bipush 1
      // 9f: invokevirtual kotlinx/coroutines/flow/internal/SubscriptionCountStateFlow.increment (I)Z
      // a2: pop
      // a3: aload 4
      // a5: areturn
      // a6: astore 3
      // a7: aload 0
      // a8: monitorexit
      // a9: aload 3
      // aa: athrow
   }

   protected abstract fun createSlot(): Any {
   }

   protected abstract fun createSlotArray(size: Int): Array<Any?> {
   }

   protected inline fun forEachSlotLocked(block: (Any) -> Unit) {
      if (access$getNCollectors(this) != 0) {
         val var5: Array<AbstractSharedFlowSlot> = access$getSlots(this);
         if (var5 != null) {
            val var3: Int = var5.length;

            for (int var2 = 0; var2 < var3; var2++) {
               val var4: AbstractSharedFlowSlot = var5[var2];
               if (var5[var2] != null) {
                  var1.invoke(var4);
               }
            }
         }
      }
   }

   protected fun freeSlot(slot: Any) {
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
      // 02: aload 0
      // 03: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nCollectors I
      // 06: bipush 1
      // 07: isub
      // 08: istore 3
      // 09: aload 0
      // 0a: iload 3
      // 0b: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nCollectors I
      // 0e: aload 0
      // 0f: getfield kotlinx/coroutines/flow/internal/AbstractSharedFlow._subscriptionCount Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;
      // 12: astore 4
      // 14: bipush 0
      // 15: istore 2
      // 16: iload 3
      // 17: ifne 1f
      // 1a: aload 0
      // 1b: bipush 0
      // 1c: putfield kotlinx/coroutines/flow/internal/AbstractSharedFlow.nextIndex I
      // 1f: aload 1
      // 20: ldc "null cannot be cast to non-null type kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot<kotlin.Any>"
      // 22: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 25: aload 1
      // 26: aload 0
      // 27: invokevirtual kotlinx/coroutines/flow/internal/AbstractSharedFlowSlot.freeLocked (Ljava/lang/Object;)[Lkotlin/coroutines/Continuation;
      // 2a: astore 5
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 5
      // 30: arraylength
      // 31: istore 3
      // 32: iload 2
      // 33: iload 3
      // 34: if_icmpge 57
      // 37: aload 5
      // 39: iload 2
      // 3a: aaload
      // 3b: astore 1
      // 3c: aload 1
      // 3d: ifnull 51
      // 40: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 43: astore 6
      // 45: aload 1
      // 46: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 49: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 4c: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 51: iinc 2 1
      // 54: goto 32
      // 57: aload 4
      // 59: ifnull 63
      // 5c: aload 4
      // 5e: bipush -1
      // 5f: invokevirtual kotlinx/coroutines/flow/internal/SubscriptionCountStateFlow.increment (I)Z
      // 62: pop
      // 63: return
      // 64: astore 1
      // 65: aload 0
      // 66: monitorexit
      // 67: aload 1
      // 68: athrow
   }
}
