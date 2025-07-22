package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation

internal class SubscribedFlowCollector<T>(collector: FlowCollector<Any>, action: (FlowCollector<Any>, Continuation<Unit>) -> Any?) : FlowCollector<T> {
   private final val action: (FlowCollector<Any>, Continuation<Unit>) -> Any?
   private final val collector: FlowCollector<Any>

   init {
      this.collector = var1;
      this.action = var2;
   }

   public override suspend fun emit(value: Any) {
      return this.collector.emit((T)var1, var2);
   }

   public suspend fun onSubscription() {
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
      // 000: aload 1
      // 001: instanceof kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1
      // 004: ifeq 026
      // 007: aload 1
      // 008: checkcast kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1
      // 00b: astore 3
      // 00c: aload 3
      // 00d: getfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.label I
      // 010: ldc -2147483648
      // 012: iand
      // 013: ifeq 026
      // 016: aload 3
      // 017: aload 3
      // 018: getfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.label I
      // 01b: ldc -2147483648
      // 01d: iadd
      // 01e: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.label I
      // 021: aload 3
      // 022: astore 1
      // 023: goto 030
      // 026: new kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1
      // 029: dup
      // 02a: aload 0
      // 02b: aload 1
      // 02c: invokespecial kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.<init> (Lkotlinx/coroutines/flow/SubscribedFlowCollector;Lkotlin/coroutines/Continuation;)V
      // 02f: astore 1
      // 030: aload 1
      // 031: getfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.result Ljava/lang/Object;
      // 034: astore 7
      // 036: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 039: astore 6
      // 03b: aload 1
      // 03c: getfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.label I
      // 03f: istore 2
      // 040: iload 2
      // 041: ifeq 080
      // 044: iload 2
      // 045: bipush 1
      // 046: if_icmpeq 060
      // 049: iload 2
      // 04a: bipush 2
      // 04b: if_icmpne 056
      // 04e: aload 7
      // 050: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 053: goto 107
      // 056: new java/lang/IllegalStateException
      // 059: dup
      // 05a: ldc "call to 'resume' before 'invoke' with coroutine"
      // 05c: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 05f: athrow
      // 060: aload 1
      // 061: getfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.L$1 Ljava/lang/Object;
      // 064: checkcast kotlinx/coroutines/flow/internal/SafeCollector
      // 067: astore 4
      // 069: aload 1
      // 06a: getfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.L$0 Ljava/lang/Object;
      // 06d: checkcast kotlinx/coroutines/flow/SubscribedFlowCollector
      // 070: astore 5
      // 072: aload 4
      // 074: astore 3
      // 075: aload 7
      // 077: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 07a: aload 5
      // 07c: astore 3
      // 07d: goto 0d5
      // 080: aload 7
      // 082: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 085: new kotlinx/coroutines/flow/internal/SafeCollector
      // 088: dup
      // 089: aload 0
      // 08a: getfield kotlinx/coroutines/flow/SubscribedFlowCollector.collector Lkotlinx/coroutines/flow/FlowCollector;
      // 08d: aload 1
      // 08e: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 093: invokespecial kotlinx/coroutines/flow/internal/SafeCollector.<init> (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V
      // 096: astore 4
      // 098: aload 4
      // 09a: astore 3
      // 09b: aload 0
      // 09c: getfield kotlinx/coroutines/flow/SubscribedFlowCollector.action Lkotlin/jvm/functions/Function2;
      // 09f: astore 5
      // 0a1: aload 4
      // 0a3: astore 3
      // 0a4: aload 1
      // 0a5: aload 0
      // 0a6: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.L$0 Ljava/lang/Object;
      // 0a9: aload 4
      // 0ab: astore 3
      // 0ac: aload 1
      // 0ad: aload 4
      // 0af: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.L$1 Ljava/lang/Object;
      // 0b2: aload 4
      // 0b4: astore 3
      // 0b5: aload 1
      // 0b6: bipush 1
      // 0b7: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.label I
      // 0ba: aload 4
      // 0bc: astore 3
      // 0bd: aload 5
      // 0bf: aload 4
      // 0c1: aload 1
      // 0c2: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0c7: astore 5
      // 0c9: aload 5
      // 0cb: aload 6
      // 0cd: if_acmpne 0d3
      // 0d0: aload 6
      // 0d2: areturn
      // 0d3: aload 0
      // 0d4: astore 3
      // 0d5: aload 4
      // 0d7: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
      // 0da: aload 3
      // 0db: getfield kotlinx/coroutines/flow/SubscribedFlowCollector.collector Lkotlinx/coroutines/flow/FlowCollector;
      // 0de: astore 3
      // 0df: aload 3
      // 0e0: instanceof kotlinx/coroutines/flow/SubscribedFlowCollector
      // 0e3: ifeq 10b
      // 0e6: aload 3
      // 0e7: checkcast kotlinx/coroutines/flow/SubscribedFlowCollector
      // 0ea: astore 3
      // 0eb: aload 1
      // 0ec: aconst_null
      // 0ed: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.L$0 Ljava/lang/Object;
      // 0f0: aload 1
      // 0f1: aconst_null
      // 0f2: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.L$1 Ljava/lang/Object;
      // 0f5: aload 1
      // 0f6: bipush 2
      // 0f7: putfield kotlinx/coroutines/flow/SubscribedFlowCollector$onSubscription$1.label I
      // 0fa: aload 3
      // 0fb: aload 1
      // 0fc: invokevirtual kotlinx/coroutines/flow/SubscribedFlowCollector.onSubscription (Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
      // 0ff: aload 6
      // 101: if_acmpne 107
      // 104: aload 6
      // 106: areturn
      // 107: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 10a: areturn
      // 10b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 10e: areturn
      // 10f: astore 1
      // 110: aload 3
      // 111: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
      // 114: aload 1
      // 115: athrow
   }
}
