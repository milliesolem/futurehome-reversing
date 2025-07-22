package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.internal.AbstractSharedFlow
import kotlinx.coroutines.flow.internal.FusibleFlow
import kotlinx.coroutines.flow.internal.NullSurrogateKt
import kotlinx.coroutines.internal.Symbol

private class StateFlowImpl<T>(initialState: Any) : AbstractSharedFlow<StateFlowSlot>, MutableStateFlow<T>, CancellableFlow<T>, FusibleFlow<T> {
   private final val _state: AtomicRef<Any>

   public open val replayCache: List<Any>
      public open get() {
         return CollectionsKt.listOf(this.getValue());
      }


   private final var sequence: Int

   public open var value: Any
      public open get() {
         val var3: Symbol = NullSurrogateKt.NULL;
         val var2: Any = _state$FU.get(this);
         var var1: Any = var2;
         if (var2 === var3) {
            var1 = null;
         }

         return (T)var1;
      }

      public open set(value) {
         var var2: Any = var1;
         if (var1 == null) {
            var2 = NullSurrogateKt.NULL;
         }

         this.updateState(null, var2);
      }


   init {
      this._state = var1;
   }

   private fun updateState(expectedState: Any?, newState: Any): Boolean {
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
      // 02: getstatic kotlinx/coroutines/flow/StateFlowImpl._state$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 05: astore 7
      // 07: aload 7
      // 09: aload 0
      // 0a: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 0d: astore 8
      // 0f: aload 1
      // 10: ifnull 24
      // 13: aload 8
      // 15: aload 1
      // 16: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 19: istore 6
      // 1b: iload 6
      // 1d: ifne 24
      // 20: aload 0
      // 21: monitorexit
      // 22: bipush 0
      // 23: ireturn
      // 24: aload 8
      // 26: aload 2
      // 27: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 2a: istore 6
      // 2c: iload 6
      // 2e: ifeq 35
      // 31: aload 0
      // 32: monitorexit
      // 33: bipush 1
      // 34: ireturn
      // 35: aload 7
      // 37: aload 0
      // 38: aload 2
      // 39: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.set (Ljava/lang/Object;Ljava/lang/Object;)V
      // 3c: aload 0
      // 3d: getfield kotlinx/coroutines/flow/StateFlowImpl.sequence I
      // 40: istore 3
      // 41: iload 3
      // 42: bipush 1
      // 43: iand
      // 44: ifne b3
      // 47: iinc 3 1
      // 4a: aload 0
      // 4b: iload 3
      // 4c: putfield kotlinx/coroutines/flow/StateFlowImpl.sequence I
      // 4f: aload 0
      // 50: invokevirtual kotlinx/coroutines/flow/StateFlowImpl.getSlots ()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 53: astore 1
      // 54: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 57: astore 2
      // 58: aload 0
      // 59: monitorexit
      // 5a: aload 1
      // 5b: checkcast [Lkotlinx/coroutines/flow/StateFlowSlot;
      // 5e: astore 2
      // 5f: aload 2
      // 60: ifnull 84
      // 63: aload 2
      // 64: arraylength
      // 65: istore 5
      // 67: bipush 0
      // 68: istore 4
      // 6a: iload 4
      // 6c: iload 5
      // 6e: if_icmpge 84
      // 71: aload 2
      // 72: iload 4
      // 74: aaload
      // 75: astore 1
      // 76: aload 1
      // 77: ifnull 7e
      // 7a: aload 1
      // 7b: invokevirtual kotlinx/coroutines/flow/StateFlowSlot.makePending ()V
      // 7e: iinc 4 1
      // 81: goto 6a
      // 84: aload 0
      // 85: monitorenter
      // 86: aload 0
      // 87: getfield kotlinx/coroutines/flow/StateFlowImpl.sequence I
      // 8a: istore 4
      // 8c: iload 4
      // 8e: iload 3
      // 8f: if_icmpne 9d
      // 92: aload 0
      // 93: iload 3
      // 94: bipush 1
      // 95: iadd
      // 96: putfield kotlinx/coroutines/flow/StateFlowImpl.sequence I
      // 99: aload 0
      // 9a: monitorexit
      // 9b: bipush 1
      // 9c: ireturn
      // 9d: aload 0
      // 9e: invokevirtual kotlinx/coroutines/flow/StateFlowImpl.getSlots ()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // a1: astore 1
      // a2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // a5: astore 2
      // a6: aload 0
      // a7: monitorexit
      // a8: iload 4
      // aa: istore 3
      // ab: goto 5a
      // ae: astore 1
      // af: aload 0
      // b0: monitorexit
      // b1: aload 1
      // b2: athrow
      // b3: aload 0
      // b4: iload 3
      // b5: bipush 2
      // b6: iadd
      // b7: putfield kotlinx/coroutines/flow/StateFlowImpl.sequence I
      // ba: aload 0
      // bb: monitorexit
      // bc: bipush 1
      // bd: ireturn
      // be: astore 1
      // bf: aload 0
      // c0: monitorexit
      // c1: aload 1
      // c2: athrow
   }

   public override suspend fun collect(collector: FlowCollector<Any>): Nothing {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 000: aload 2
      // 001: instanceof kotlinx/coroutines/flow/StateFlowImpl$collect$1
      // 004: ifeq 028
      // 007: aload 2
      // 008: checkcast kotlinx/coroutines/flow/StateFlowImpl$collect$1
      // 00b: astore 4
      // 00d: aload 4
      // 00f: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 028
      // 018: aload 4
      // 01a: aload 4
      // 01c: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 025: goto 033
      // 028: new kotlinx/coroutines/flow/StateFlowImpl$collect$1
      // 02b: dup
      // 02c: aload 0
      // 02d: aload 2
      // 02e: invokespecial kotlinx/coroutines/flow/StateFlowImpl$collect$1.<init> (Lkotlinx/coroutines/flow/StateFlowImpl;Lkotlin/coroutines/Continuation;)V
      // 031: astore 4
      // 033: aload 4
      // 035: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.result Ljava/lang/Object;
      // 038: astore 12
      // 03a: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03d: astore 17
      // 03f: aload 4
      // 041: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 044: istore 3
      // 045: iload 3
      // 046: ifeq 119
      // 049: iload 3
      // 04a: bipush 1
      // 04b: if_icmpeq 0e8
      // 04e: iload 3
      // 04f: bipush 2
      // 050: if_icmpeq 0a3
      // 053: iload 3
      // 054: bipush 3
      // 055: if_icmpne 099
      // 058: aload 4
      // 05a: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$4 Ljava/lang/Object;
      // 05d: astore 9
      // 05f: aload 4
      // 061: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$3 Ljava/lang/Object;
      // 064: checkcast kotlinx/coroutines/Job
      // 067: astore 7
      // 069: aload 4
      // 06b: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$2 Ljava/lang/Object;
      // 06e: checkcast kotlinx/coroutines/flow/StateFlowSlot
      // 071: astore 5
      // 073: aload 4
      // 075: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$1 Ljava/lang/Object;
      // 078: checkcast kotlinx/coroutines/flow/FlowCollector
      // 07b: astore 8
      // 07d: aload 4
      // 07f: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$0 Ljava/lang/Object;
      // 082: checkcast kotlinx/coroutines/flow/StateFlowImpl
      // 085: astore 6
      // 087: aload 5
      // 089: astore 2
      // 08a: aload 6
      // 08c: astore 1
      // 08d: aload 12
      // 08f: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 092: aload 4
      // 094: astore 12
      // 096: goto 193
      // 099: new java/lang/IllegalStateException
      // 09c: dup
      // 09d: ldc "call to 'resume' before 'invoke' with coroutine"
      // 09f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 0a2: athrow
      // 0a3: aload 4
      // 0a5: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$4 Ljava/lang/Object;
      // 0a8: astore 11
      // 0aa: aload 4
      // 0ac: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$3 Ljava/lang/Object;
      // 0af: checkcast kotlinx/coroutines/Job
      // 0b2: astore 14
      // 0b4: aload 4
      // 0b6: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$2 Ljava/lang/Object;
      // 0b9: checkcast kotlinx/coroutines/flow/StateFlowSlot
      // 0bc: astore 5
      // 0be: aload 4
      // 0c0: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$1 Ljava/lang/Object;
      // 0c3: checkcast kotlinx/coroutines/flow/FlowCollector
      // 0c6: astore 15
      // 0c8: aload 4
      // 0ca: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$0 Ljava/lang/Object;
      // 0cd: checkcast kotlinx/coroutines/flow/StateFlowImpl
      // 0d0: astore 10
      // 0d2: aload 5
      // 0d4: astore 2
      // 0d5: aload 10
      // 0d7: astore 1
      // 0d8: aload 12
      // 0da: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 0dd: aload 4
      // 0df: astore 13
      // 0e1: aload 5
      // 0e3: astore 4
      // 0e5: goto 276
      // 0e8: aload 4
      // 0ea: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$2 Ljava/lang/Object;
      // 0ed: checkcast kotlinx/coroutines/flow/StateFlowSlot
      // 0f0: astore 5
      // 0f2: aload 4
      // 0f4: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$1 Ljava/lang/Object;
      // 0f7: checkcast kotlinx/coroutines/flow/FlowCollector
      // 0fa: astore 7
      // 0fc: aload 4
      // 0fe: getfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$0 Ljava/lang/Object;
      // 101: checkcast kotlinx/coroutines/flow/StateFlowImpl
      // 104: astore 6
      // 106: aload 5
      // 108: astore 2
      // 109: aload 6
      // 10b: astore 1
      // 10c: aload 12
      // 10e: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 111: goto 167
      // 114: astore 4
      // 116: goto 31e
      // 119: aload 12
      // 11b: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 11e: aload 0
      // 11f: invokevirtual kotlinx/coroutines/flow/StateFlowImpl.allocateSlot ()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
      // 122: checkcast kotlinx/coroutines/flow/StateFlowSlot
      // 125: astore 2
      // 126: aload 1
      // 127: instanceof kotlinx/coroutines/flow/SubscribedFlowCollector
      // 12a: ifeq 15e
      // 12d: aload 1
      // 12e: checkcast kotlinx/coroutines/flow/SubscribedFlowCollector
      // 131: astore 5
      // 133: aload 4
      // 135: aload 0
      // 136: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$0 Ljava/lang/Object;
      // 139: aload 4
      // 13b: aload 1
      // 13c: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$1 Ljava/lang/Object;
      // 13f: aload 4
      // 141: aload 2
      // 142: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$2 Ljava/lang/Object;
      // 145: aload 4
      // 147: bipush 1
      // 148: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 14b: aload 5
      // 14d: aload 4
      // 14f: invokevirtual kotlinx/coroutines/flow/SubscribedFlowCollector.onSubscription (Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
      // 152: astore 5
      // 154: aload 5
      // 156: aload 17
      // 158: if_acmpne 15e
      // 15b: aload 17
      // 15d: areturn
      // 15e: aload 0
      // 15f: astore 6
      // 161: aload 1
      // 162: astore 7
      // 164: aload 2
      // 165: astore 5
      // 167: aload 5
      // 169: astore 2
      // 16a: aload 6
      // 16c: astore 1
      // 16d: aload 4
      // 16f: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 174: getstatic kotlinx/coroutines/Job.Key Lkotlinx/coroutines/Job$Key;
      // 177: checkcast kotlin/coroutines/CoroutineContext$Key
      // 17a: invokeinterface kotlin/coroutines/CoroutineContext.get (Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element; 2
      // 17f: checkcast kotlinx/coroutines/Job
      // 182: astore 9
      // 184: aload 7
      // 186: astore 8
      // 188: aload 9
      // 18a: astore 7
      // 18c: aconst_null
      // 18d: astore 9
      // 18f: aload 4
      // 191: astore 12
      // 193: aload 5
      // 195: astore 2
      // 196: aload 6
      // 198: astore 1
      // 199: getstatic kotlinx/coroutines/flow/StateFlowImpl._state$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 19c: aload 6
      // 19e: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 1a1: astore 16
      // 1a3: aload 7
      // 1a5: ifnull 1b3
      // 1a8: aload 5
      // 1aa: astore 2
      // 1ab: aload 6
      // 1ad: astore 1
      // 1ae: aload 7
      // 1b0: invokestatic kotlinx/coroutines/JobKt.ensureActive (Lkotlinx/coroutines/Job;)V
      // 1b3: aload 9
      // 1b5: ifnull 1e0
      // 1b8: aload 5
      // 1ba: astore 2
      // 1bb: aload 6
      // 1bd: astore 1
      // 1be: aload 12
      // 1c0: astore 13
      // 1c2: aload 7
      // 1c4: astore 14
      // 1c6: aload 5
      // 1c8: astore 4
      // 1ca: aload 8
      // 1cc: astore 15
      // 1ce: aload 6
      // 1d0: astore 10
      // 1d2: aload 9
      // 1d4: astore 11
      // 1d6: aload 9
      // 1d8: aload 16
      // 1da: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 1dd: ifne 276
      // 1e0: aload 5
      // 1e2: astore 2
      // 1e3: aload 6
      // 1e5: astore 1
      // 1e6: aload 16
      // 1e8: getstatic kotlinx/coroutines/flow/internal/NullSurrogateKt.NULL Lkotlinx/coroutines/internal/Symbol;
      // 1eb: if_acmpne 1f4
      // 1ee: aconst_null
      // 1ef: astore 4
      // 1f1: goto 1f8
      // 1f4: aload 16
      // 1f6: astore 4
      // 1f8: aload 5
      // 1fa: astore 2
      // 1fb: aload 6
      // 1fd: astore 1
      // 1fe: aload 12
      // 200: aload 6
      // 202: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$0 Ljava/lang/Object;
      // 205: aload 5
      // 207: astore 2
      // 208: aload 6
      // 20a: astore 1
      // 20b: aload 12
      // 20d: aload 8
      // 20f: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$1 Ljava/lang/Object;
      // 212: aload 5
      // 214: astore 2
      // 215: aload 6
      // 217: astore 1
      // 218: aload 12
      // 21a: aload 5
      // 21c: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$2 Ljava/lang/Object;
      // 21f: aload 5
      // 221: astore 2
      // 222: aload 6
      // 224: astore 1
      // 225: aload 12
      // 227: aload 7
      // 229: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$3 Ljava/lang/Object;
      // 22c: aload 5
      // 22e: astore 2
      // 22f: aload 6
      // 231: astore 1
      // 232: aload 12
      // 234: aload 16
      // 236: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$4 Ljava/lang/Object;
      // 239: aload 5
      // 23b: astore 2
      // 23c: aload 6
      // 23e: astore 1
      // 23f: aload 12
      // 241: bipush 2
      // 242: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 245: aload 5
      // 247: astore 2
      // 248: aload 6
      // 24a: astore 1
      // 24b: aload 8
      // 24d: aload 4
      // 24f: aload 12
      // 251: invokeinterface kotlinx/coroutines/flow/FlowCollector.emit (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 3
      // 256: aload 17
      // 258: if_acmpne 25e
      // 25b: aload 17
      // 25d: areturn
      // 25e: aload 16
      // 260: astore 11
      // 262: aload 6
      // 264: astore 10
      // 266: aload 8
      // 268: astore 15
      // 26a: aload 5
      // 26c: astore 4
      // 26e: aload 7
      // 270: astore 14
      // 272: aload 12
      // 274: astore 13
      // 276: aload 4
      // 278: astore 2
      // 279: aload 10
      // 27b: astore 1
      // 27c: aload 13
      // 27e: astore 12
      // 280: aload 14
      // 282: astore 7
      // 284: aload 4
      // 286: astore 5
      // 288: aload 15
      // 28a: astore 8
      // 28c: aload 10
      // 28e: astore 6
      // 290: aload 11
      // 292: astore 9
      // 294: aload 4
      // 296: invokevirtual kotlinx/coroutines/flow/StateFlowSlot.takePending ()Z
      // 299: ifne 193
      // 29c: aload 4
      // 29e: astore 2
      // 29f: aload 10
      // 2a1: astore 1
      // 2a2: aload 13
      // 2a4: aload 10
      // 2a6: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$0 Ljava/lang/Object;
      // 2a9: aload 4
      // 2ab: astore 2
      // 2ac: aload 10
      // 2ae: astore 1
      // 2af: aload 13
      // 2b1: aload 15
      // 2b3: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$1 Ljava/lang/Object;
      // 2b6: aload 4
      // 2b8: astore 2
      // 2b9: aload 10
      // 2bb: astore 1
      // 2bc: aload 13
      // 2be: aload 4
      // 2c0: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$2 Ljava/lang/Object;
      // 2c3: aload 4
      // 2c5: astore 2
      // 2c6: aload 10
      // 2c8: astore 1
      // 2c9: aload 13
      // 2cb: aload 14
      // 2cd: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$3 Ljava/lang/Object;
      // 2d0: aload 4
      // 2d2: astore 2
      // 2d3: aload 10
      // 2d5: astore 1
      // 2d6: aload 13
      // 2d8: aload 11
      // 2da: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.L$4 Ljava/lang/Object;
      // 2dd: aload 4
      // 2df: astore 2
      // 2e0: aload 10
      // 2e2: astore 1
      // 2e3: aload 13
      // 2e5: bipush 3
      // 2e6: putfield kotlinx/coroutines/flow/StateFlowImpl$collect$1.label I
      // 2e9: aload 4
      // 2eb: astore 2
      // 2ec: aload 10
      // 2ee: astore 1
      // 2ef: aload 4
      // 2f1: aload 13
      // 2f3: invokevirtual kotlinx/coroutines/flow/StateFlowSlot.awaitPending (Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
      // 2f6: astore 16
      // 2f8: aload 13
      // 2fa: astore 12
      // 2fc: aload 14
      // 2fe: astore 7
      // 300: aload 4
      // 302: astore 5
      // 304: aload 15
      // 306: astore 8
      // 308: aload 10
      // 30a: astore 6
      // 30c: aload 11
      // 30e: astore 9
      // 310: aload 16
      // 312: aload 17
      // 314: if_acmpne 193
      // 317: aload 17
      // 319: areturn
      // 31a: astore 4
      // 31c: aload 0
      // 31d: astore 1
      // 31e: aload 1
      // 31f: aload 2
      // 320: checkcast kotlinx/coroutines/flow/internal/AbstractSharedFlowSlot
      // 323: invokevirtual kotlinx/coroutines/flow/StateFlowImpl.freeSlot (Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V
      // 326: aload 4
      // 328: athrow
   }

   public override fun compareAndSet(expect: Any, update: Any): Boolean {
      var var3: Any = var1;
      if (var1 == null) {
         var3 = NullSurrogateKt.NULL;
      }

      var1 = var2;
      if (var2 == null) {
         var1 = NullSurrogateKt.NULL;
      }

      return this.updateState(var3, var1);
   }

   protected open fun createSlot(): StateFlowSlot {
      return new StateFlowSlot();
   }

   protected open fun createSlotArray(size: Int): Array<StateFlowSlot?> {
      return new StateFlowSlot[var1];
   }

   public override suspend fun emit(value: Any) {
      this.setValue((T)var1);
      return Unit.INSTANCE;
   }

   public override fun fuse(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): Flow<Any> {
      return StateFlowKt.fuseStateFlow(this, var1, var2, var3);
   }

   public override fun resetReplayCache() {
      throw new UnsupportedOperationException("MutableStateFlow.resetReplayCache is not supported");
   }

   public override fun tryEmit(value: Any): Boolean {
      this.setValue((T)var1);
      return true;
   }
}
