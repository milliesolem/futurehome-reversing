package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.internal.ChannelFlowKt

@JvmSynthetic
internal class FlowKt__ChannelsKt {
   @Deprecated(level = DeprecationLevel.ERROR, message = "'BroadcastChannel' is obsolete and all corresponding operators are deprecated in the favour of StateFlow and SharedFlow")
   @JvmStatic
   public fun <T> BroadcastChannel<T>.asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final BroadcastChannel $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            val var3: Any = FlowKt.emitAll(var1, this.$this_asFlow$inlined.openSubscription(), var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> ReceiveChannel<T>.consumeAsFlow(): Flow<T> {
      return new ChannelAsFlow(var0, true, null, 0, null, 28, null);
   }

   @JvmStatic
   public suspend fun <T> FlowCollector<T>.emitAll(channel: ReceiveChannel<T>) {
      val var3: Any = emitAllImpl$FlowKt__ChannelsKt(var0, var1, true, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }

   @JvmStatic
   private suspend fun <T> FlowCollector<T>.emitAllImpl(channel: ReceiveChannel<T>, consume: Boolean) {
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
      // 000: aload 3
      // 001: instanceof kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1
      // 004: ifeq 028
      // 007: aload 3
      // 008: checkcast kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1
      // 00b: astore 6
      // 00d: aload 6
      // 00f: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 028
      // 018: aload 6
      // 01a: aload 6
      // 01c: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.label I
      // 025: goto 032
      // 028: new kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1
      // 02b: dup
      // 02c: aload 3
      // 02d: invokespecial kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.<init> (Lkotlin/coroutines/Continuation;)V
      // 030: astore 6
      // 032: aload 6
      // 034: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.result Ljava/lang/Object;
      // 037: astore 8
      // 039: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03c: astore 9
      // 03e: aload 6
      // 040: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.label I
      // 043: istore 4
      // 045: iload 4
      // 047: ifeq 0c9
      // 04a: iload 4
      // 04c: bipush 1
      // 04d: if_icmpeq 09a
      // 050: iload 4
      // 052: bipush 2
      // 053: if_icmpne 090
      // 056: aload 6
      // 058: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.Z$0 Z
      // 05b: istore 2
      // 05c: aload 6
      // 05e: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$2 Ljava/lang/Object;
      // 061: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 064: astore 1
      // 065: aload 6
      // 067: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$1 Ljava/lang/Object;
      // 06a: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 06d: astore 0
      // 06e: aload 6
      // 070: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$0 Ljava/lang/Object;
      // 073: checkcast kotlinx/coroutines/flow/FlowCollector
      // 076: astore 7
      // 078: aload 0
      // 079: astore 3
      // 07a: iload 2
      // 07b: istore 5
      // 07d: aload 8
      // 07f: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 082: aload 1
      // 083: astore 3
      // 084: aload 7
      // 086: astore 8
      // 088: aload 0
      // 089: astore 1
      // 08a: aload 3
      // 08b: astore 7
      // 08d: goto 0e2
      // 090: new java/lang/IllegalStateException
      // 093: dup
      // 094: ldc "call to 'resume' before 'invoke' with coroutine"
      // 096: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 099: athrow
      // 09a: aload 6
      // 09c: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.Z$0 Z
      // 09f: istore 2
      // 0a0: aload 6
      // 0a2: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$2 Ljava/lang/Object;
      // 0a5: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 0a8: astore 1
      // 0a9: aload 6
      // 0ab: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$1 Ljava/lang/Object;
      // 0ae: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 0b1: astore 0
      // 0b2: aload 6
      // 0b4: getfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$0 Ljava/lang/Object;
      // 0b7: checkcast kotlinx/coroutines/flow/FlowCollector
      // 0ba: astore 7
      // 0bc: aload 0
      // 0bd: astore 3
      // 0be: iload 2
      // 0bf: istore 5
      // 0c1: aload 8
      // 0c3: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 0c6: goto 141
      // 0c9: aload 8
      // 0cb: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 0ce: aload 0
      // 0cf: invokestatic kotlinx/coroutines/flow/FlowKt.ensureActive (Lkotlinx/coroutines/flow/FlowCollector;)V
      // 0d2: aload 1
      // 0d3: astore 3
      // 0d4: iload 2
      // 0d5: istore 5
      // 0d7: aload 1
      // 0d8: invokeinterface kotlinx/coroutines/channels/ReceiveChannel.iterator ()Lkotlinx/coroutines/channels/ChannelIterator; 1
      // 0dd: astore 7
      // 0df: aload 0
      // 0e0: astore 8
      // 0e2: aload 1
      // 0e3: astore 3
      // 0e4: iload 2
      // 0e5: istore 5
      // 0e7: aload 6
      // 0e9: aload 8
      // 0eb: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$0 Ljava/lang/Object;
      // 0ee: aload 1
      // 0ef: astore 3
      // 0f0: iload 2
      // 0f1: istore 5
      // 0f3: aload 6
      // 0f5: aload 1
      // 0f6: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$1 Ljava/lang/Object;
      // 0f9: aload 1
      // 0fa: astore 3
      // 0fb: iload 2
      // 0fc: istore 5
      // 0fe: aload 6
      // 100: aload 7
      // 102: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$2 Ljava/lang/Object;
      // 105: aload 1
      // 106: astore 3
      // 107: iload 2
      // 108: istore 5
      // 10a: aload 6
      // 10c: iload 2
      // 10d: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.Z$0 Z
      // 110: aload 1
      // 111: astore 3
      // 112: iload 2
      // 113: istore 5
      // 115: aload 6
      // 117: bipush 1
      // 118: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.label I
      // 11b: aload 1
      // 11c: astore 3
      // 11d: iload 2
      // 11e: istore 5
      // 120: aload 7
      // 122: aload 6
      // 124: invokeinterface kotlinx/coroutines/channels/ChannelIterator.hasNext (Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 2
      // 129: astore 0
      // 12a: aload 0
      // 12b: aload 9
      // 12d: if_acmpne 133
      // 130: aload 9
      // 132: areturn
      // 133: aload 8
      // 135: astore 3
      // 136: aload 0
      // 137: astore 8
      // 139: aload 1
      // 13a: astore 0
      // 13b: aload 7
      // 13d: astore 1
      // 13e: aload 3
      // 13f: astore 7
      // 141: aload 0
      // 142: astore 3
      // 143: iload 2
      // 144: istore 5
      // 146: aload 8
      // 148: checkcast java/lang/Boolean
      // 14b: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 14e: ifeq 1b2
      // 151: aload 0
      // 152: astore 3
      // 153: iload 2
      // 154: istore 5
      // 156: aload 1
      // 157: invokeinterface kotlinx/coroutines/channels/ChannelIterator.next ()Ljava/lang/Object; 1
      // 15c: astore 8
      // 15e: aload 0
      // 15f: astore 3
      // 160: iload 2
      // 161: istore 5
      // 163: aload 6
      // 165: aload 7
      // 167: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$0 Ljava/lang/Object;
      // 16a: aload 0
      // 16b: astore 3
      // 16c: iload 2
      // 16d: istore 5
      // 16f: aload 6
      // 171: aload 0
      // 172: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$1 Ljava/lang/Object;
      // 175: aload 0
      // 176: astore 3
      // 177: iload 2
      // 178: istore 5
      // 17a: aload 6
      // 17c: aload 1
      // 17d: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.L$2 Ljava/lang/Object;
      // 180: aload 0
      // 181: astore 3
      // 182: iload 2
      // 183: istore 5
      // 185: aload 6
      // 187: iload 2
      // 188: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.Z$0 Z
      // 18b: aload 0
      // 18c: astore 3
      // 18d: iload 2
      // 18e: istore 5
      // 190: aload 6
      // 192: bipush 2
      // 193: putfield kotlinx/coroutines/flow/FlowKt__ChannelsKt$emitAllImpl$1.label I
      // 196: aload 0
      // 197: astore 3
      // 198: iload 2
      // 199: istore 5
      // 19b: aload 7
      // 19d: aload 8
      // 19f: aload 6
      // 1a1: invokeinterface kotlinx/coroutines/flow/FlowCollector.emit (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 3
      // 1a6: astore 8
      // 1a8: aload 8
      // 1aa: aload 9
      // 1ac: if_acmpne 082
      // 1af: aload 9
      // 1b1: areturn
      // 1b2: iload 2
      // 1b3: ifeq 1bb
      // 1b6: aload 0
      // 1b7: aconst_null
      // 1b8: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 1bb: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1be: areturn
      // 1bf: astore 1
      // 1c0: aload 1
      // 1c1: athrow
      // 1c2: astore 0
      // 1c3: iload 5
      // 1c5: ifeq 1cd
      // 1c8: aload 3
      // 1c9: aload 1
      // 1ca: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 1cd: aload 0
      // 1ce: athrow
   }

   @JvmStatic
   public fun <T> Flow<T>.produceIn(scope: CoroutineScope): ReceiveChannel<T> {
      return ChannelFlowKt.asChannelFlow(var0).produceImpl(var1);
   }

   @JvmStatic
   public fun <T> ReceiveChannel<T>.receiveAsFlow(): Flow<T> {
      return new ChannelAsFlow(var0, false, null, 0, null, 28, null);
   }
}
