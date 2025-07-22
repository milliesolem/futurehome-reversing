package kotlinx.coroutines.flow

public abstract class AbstractFlow<T> : Flow<T>, CancellableFlow<T> {
   public override suspend fun collect(collector: FlowCollector<Any>) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 2
      // 01: instanceof kotlinx/coroutines/flow/AbstractFlow$collect$1
      // 04: ifeq 2b
      // 07: aload 2
      // 08: checkcast kotlinx/coroutines/flow/AbstractFlow$collect$1
      // 0b: astore 4
      // 0d: aload 4
      // 0f: getfield kotlinx/coroutines/flow/AbstractFlow$collect$1.label I
      // 12: ldc -2147483648
      // 14: iand
      // 15: ifeq 2b
      // 18: aload 4
      // 1a: aload 4
      // 1c: getfield kotlinx/coroutines/flow/AbstractFlow$collect$1.label I
      // 1f: ldc -2147483648
      // 21: iadd
      // 22: putfield kotlinx/coroutines/flow/AbstractFlow$collect$1.label I
      // 25: aload 4
      // 27: astore 2
      // 28: goto 35
      // 2b: new kotlinx/coroutines/flow/AbstractFlow$collect$1
      // 2e: dup
      // 2f: aload 0
      // 30: aload 2
      // 31: invokespecial kotlinx/coroutines/flow/AbstractFlow$collect$1.<init> (Lkotlinx/coroutines/flow/AbstractFlow;Lkotlin/coroutines/Continuation;)V
      // 34: astore 2
      // 35: aload 2
      // 36: getfield kotlinx/coroutines/flow/AbstractFlow$collect$1.result Ljava/lang/Object;
      // 39: astore 5
      // 3b: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 3e: astore 4
      // 40: aload 2
      // 41: getfield kotlinx/coroutines/flow/AbstractFlow$collect$1.label I
      // 44: istore 3
      // 45: iload 3
      // 46: ifeq 6c
      // 49: iload 3
      // 4a: bipush 1
      // 4b: if_icmpne 62
      // 4e: aload 2
      // 4f: getfield kotlinx/coroutines/flow/AbstractFlow$collect$1.L$0 Ljava/lang/Object;
      // 52: checkcast kotlinx/coroutines/flow/internal/SafeCollector
      // 55: astore 1
      // 56: aload 5
      // 58: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 5b: goto a1
      // 5e: astore 2
      // 5f: goto aa
      // 62: new java/lang/IllegalStateException
      // 65: dup
      // 66: ldc "call to 'resume' before 'invoke' with coroutine"
      // 68: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 6b: athrow
      // 6c: aload 5
      // 6e: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 71: new kotlinx/coroutines/flow/internal/SafeCollector
      // 74: dup
      // 75: aload 1
      // 76: aload 2
      // 77: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 7c: invokespecial kotlinx/coroutines/flow/internal/SafeCollector.<init> (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V
      // 7f: astore 1
      // 80: aload 1
      // 81: checkcast kotlinx/coroutines/flow/FlowCollector
      // 84: astore 5
      // 86: aload 2
      // 87: aload 1
      // 88: putfield kotlinx/coroutines/flow/AbstractFlow$collect$1.L$0 Ljava/lang/Object;
      // 8b: aload 2
      // 8c: bipush 1
      // 8d: putfield kotlinx/coroutines/flow/AbstractFlow$collect$1.label I
      // 90: aload 0
      // 91: aload 5
      // 93: aload 2
      // 94: invokevirtual kotlinx/coroutines/flow/AbstractFlow.collectSafely (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
      // 97: astore 2
      // 98: aload 2
      // 99: aload 4
      // 9b: if_acmpne a1
      // 9e: aload 4
      // a0: areturn
      // a1: aload 1
      // a2: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
      // a5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // a8: areturn
      // a9: astore 2
      // aa: aload 1
      // ab: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
      // ae: aload 2
      // af: athrow
   }

   public abstract suspend fun collectSafely(collector: FlowCollector<Any>) {
   }
}
