package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.flow.internal.SafeCollector

@JvmSynthetic
internal class FlowKt__EmittersKt {
   @JvmStatic
   internal fun FlowCollector<*>.ensureActive() {
      if (var0 is ThrowingCollector) {
         throw (var0 as ThrowingCollector).e;
      }
   }

   @JvmStatic
   private suspend fun <T> FlowCollector<T>.invokeSafely(action: (FlowCollector<T>, Throwable?, Continuation<Unit>) -> Any?, cause: Throwable?) {
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
      // 00: aload 3
      // 01: instanceof kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1
      // 04: ifeq 28
      // 07: aload 3
      // 08: checkcast kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1
      // 0b: astore 5
      // 0d: aload 5
      // 0f: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.label I
      // 12: ldc -2147483648
      // 14: iand
      // 15: ifeq 28
      // 18: aload 5
      // 1a: aload 5
      // 1c: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.label I
      // 1f: ldc -2147483648
      // 21: iadd
      // 22: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.label I
      // 25: goto 32
      // 28: new kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1
      // 2b: dup
      // 2c: aload 3
      // 2d: invokespecial kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.<init> (Lkotlin/coroutines/Continuation;)V
      // 30: astore 5
      // 32: aload 5
      // 34: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.result Ljava/lang/Object;
      // 37: astore 6
      // 39: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 3c: astore 7
      // 3e: aload 5
      // 40: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.label I
      // 43: istore 4
      // 45: iload 4
      // 47: ifeq 6b
      // 4a: iload 4
      // 4c: bipush 1
      // 4d: if_icmpne 61
      // 50: aload 5
      // 52: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.L$0 Ljava/lang/Object;
      // 55: checkcast java/lang/Throwable
      // 58: astore 3
      // 59: aload 6
      // 5b: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 5e: goto 96
      // 61: new java/lang/IllegalStateException
      // 64: dup
      // 65: ldc "call to 'resume' before 'invoke' with coroutine"
      // 67: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 6a: athrow
      // 6b: aload 6
      // 6d: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 70: aload 2
      // 71: astore 3
      // 72: aload 5
      // 74: aload 2
      // 75: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.L$0 Ljava/lang/Object;
      // 78: aload 2
      // 79: astore 3
      // 7a: aload 5
      // 7c: bipush 1
      // 7d: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$invokeSafely$1.label I
      // 80: aload 2
      // 81: astore 3
      // 82: aload 1
      // 83: aload 0
      // 84: aload 2
      // 85: aload 5
      // 87: invokeinterface kotlin/jvm/functions/Function3.invoke (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 4
      // 8c: astore 0
      // 8d: aload 0
      // 8e: aload 7
      // 90: if_acmpne 96
      // 93: aload 7
      // 95: areturn
      // 96: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 99: areturn
      // 9a: astore 0
      // 9b: aload 3
      // 9c: ifnull a9
      // 9f: aload 3
      // a0: aload 0
      // a1: if_acmpeq a9
      // a4: aload 0
      // a5: aload 3
      // a6: invokestatic kotlin/ExceptionsKt.addSuppressed (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      // a9: aload 0
      // aa: athrow
   }

   @JvmStatic
   public fun <T> Flow<T>.onCompletion(action: (FlowCollector<T>, Throwable?, Continuation<Unit>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function3 $action$inlined;
         final Flow $this_onCompletion$inlined;

         {
            this.$this_onCompletion$inlined = var1;
            this.$action$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super T> param1, Continuation<? super Unit> param2) {
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
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
            //
            // Bytecode:
            // 000: aload 2
            // 001: instanceof kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            // 004: ifeq 02b
            // 007: aload 2
            // 008: checkcast kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            // 00b: astore 4
            // 00d: aload 4
            // 00f: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 012: ldc -2147483648
            // 014: iand
            // 015: ifeq 02b
            // 018: aload 4
            // 01a: aload 4
            // 01c: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 01f: ldc -2147483648
            // 021: iadd
            // 022: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 025: aload 4
            // 027: astore 2
            // 028: goto 035
            // 02b: new kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            // 02e: dup
            // 02f: aload 0
            // 030: aload 2
            // 031: invokespecial kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.<init> (Lkotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1;Lkotlin/coroutines/Continuation;)V
            // 034: astore 2
            // 035: aload 2
            // 036: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.result Ljava/lang/Object;
            // 039: astore 7
            // 03b: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
            // 03e: astore 6
            // 040: aload 2
            // 041: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 044: istore 3
            // 045: iload 3
            // 046: ifeq 0b1
            // 049: iload 3
            // 04a: bipush 1
            // 04b: if_icmpeq 087
            // 04e: iload 3
            // 04f: bipush 2
            // 050: if_icmpeq 076
            // 053: iload 3
            // 054: bipush 3
            // 055: if_icmpne 06c
            // 058: aload 2
            // 059: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 05c: checkcast kotlinx/coroutines/flow/internal/SafeCollector
            // 05f: astore 1
            // 060: aload 7
            // 062: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 065: goto 122
            // 068: astore 2
            // 069: goto 12b
            // 06c: new java/lang/IllegalStateException
            // 06f: dup
            // 070: ldc "call to 'resume' before 'invoke' with coroutine"
            // 072: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
            // 075: athrow
            // 076: aload 2
            // 077: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 07a: checkcast java/lang/Throwable
            // 07d: astore 4
            // 07f: aload 7
            // 081: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 084: goto 16c
            // 087: aload 2
            // 088: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 08b: checkcast kotlinx/coroutines/flow/FlowCollector
            // 08e: astore 5
            // 090: aload 2
            // 091: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 094: checkcast kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1
            // 097: astore 1
            // 098: aload 7
            // 09a: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 09d: aload 1
            // 09e: astore 4
            // 0a0: aload 5
            // 0a2: astore 1
            // 0a3: goto 0e9
            // 0a6: astore 5
            // 0a8: aload 1
            // 0a9: astore 4
            // 0ab: aload 5
            // 0ad: astore 1
            // 0ae: goto 135
            // 0b1: aload 7
            // 0b3: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 0b6: aload 2
            // 0b7: checkcast kotlin/coroutines/Continuation
            // 0ba: astore 4
            // 0bc: aload 0
            // 0bd: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.$this_onCompletion$inlined Lkotlinx/coroutines/flow/Flow;
            // 0c0: astore 4
            // 0c2: aload 2
            // 0c3: aload 0
            // 0c4: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 0c7: aload 2
            // 0c8: aload 1
            // 0c9: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 0cc: aload 2
            // 0cd: bipush 1
            // 0ce: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 0d1: aload 4
            // 0d3: aload 1
            // 0d4: aload 2
            // 0d5: invokeinterface kotlinx/coroutines/flow/Flow.collect (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 3
            // 0da: astore 4
            // 0dc: aload 4
            // 0de: aload 6
            // 0e0: if_acmpne 0e6
            // 0e3: aload 6
            // 0e5: areturn
            // 0e6: aload 0
            // 0e7: astore 4
            // 0e9: new kotlinx/coroutines/flow/internal/SafeCollector
            // 0ec: dup
            // 0ed: aload 1
            // 0ee: aload 2
            // 0ef: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
            // 0f4: invokespecial kotlinx/coroutines/flow/internal/SafeCollector.<init> (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V
            // 0f7: astore 1
            // 0f8: aload 4
            // 0fa: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.$action$inlined Lkotlin/jvm/functions/Function3;
            // 0fd: astore 4
            // 0ff: aload 2
            // 100: aload 1
            // 101: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 104: aload 2
            // 105: aconst_null
            // 106: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 109: aload 2
            // 10a: bipush 3
            // 10b: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 10e: aload 4
            // 110: aload 1
            // 111: aconst_null
            // 112: aload 2
            // 113: invokeinterface kotlin/jvm/functions/Function3.invoke (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 4
            // 118: astore 2
            // 119: aload 2
            // 11a: aload 6
            // 11c: if_acmpne 122
            // 11f: aload 6
            // 121: areturn
            // 122: aload 1
            // 123: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
            // 126: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 129: areturn
            // 12a: astore 2
            // 12b: aload 1
            // 12c: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
            // 12f: aload 2
            // 130: athrow
            // 131: astore 1
            // 132: aload 0
            // 133: astore 4
            // 135: new kotlinx/coroutines/flow/ThrowingCollector
            // 138: dup
            // 139: aload 1
            // 13a: invokespecial kotlinx/coroutines/flow/ThrowingCollector.<init> (Ljava/lang/Throwable;)V
            // 13d: checkcast kotlinx/coroutines/flow/FlowCollector
            // 140: astore 5
            // 142: aload 4
            // 144: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.$action$inlined Lkotlin/jvm/functions/Function3;
            // 147: astore 7
            // 149: aload 2
            // 14a: aload 1
            // 14b: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 14e: aload 2
            // 14f: aconst_null
            // 150: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 153: aload 2
            // 154: bipush 2
            // 155: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1.label I
            // 158: aload 1
            // 159: astore 4
            // 15b: aload 5
            // 15d: aload 7
            // 15f: aload 1
            // 160: aload 2
            // 161: invokestatic kotlinx/coroutines/flow/FlowKt__EmittersKt.access$invokeSafely$FlowKt__EmittersKt (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function3;Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
            // 164: aload 6
            // 166: if_acmpne 16c
            // 169: aload 6
            // 16b: areturn
            // 16c: aload 4
            // 16e: athrow
         }
      };
   }

   @JvmStatic
   public fun <T> Flow<T>.onEmpty(action: (FlowCollector<T>, Continuation<Unit>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function2 $action$inlined;
         final Flow $this_onEmpty$inlined;

         {
            this.$this_onEmpty$inlined = var1;
            this.$action$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super T> param1, Continuation<? super Unit> param2) {
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
            // 000: aload 2
            // 001: instanceof kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            // 004: ifeq 02b
            // 007: aload 2
            // 008: checkcast kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            // 00b: astore 4
            // 00d: aload 4
            // 00f: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.label I
            // 012: ldc -2147483648
            // 014: iand
            // 015: ifeq 02b
            // 018: aload 4
            // 01a: aload 4
            // 01c: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.label I
            // 01f: ldc -2147483648
            // 021: iadd
            // 022: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.label I
            // 025: aload 4
            // 027: astore 2
            // 028: goto 035
            // 02b: new kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            // 02e: dup
            // 02f: aload 0
            // 030: aload 2
            // 031: invokespecial kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.<init> (Lkotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1;Lkotlin/coroutines/Continuation;)V
            // 034: astore 2
            // 035: aload 2
            // 036: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.result Ljava/lang/Object;
            // 039: astore 7
            // 03b: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
            // 03e: astore 6
            // 040: aload 2
            // 041: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.label I
            // 044: istore 3
            // 045: iload 3
            // 046: ifeq 097
            // 049: iload 3
            // 04a: bipush 1
            // 04b: if_icmpeq 075
            // 04e: iload 3
            // 04f: bipush 2
            // 050: if_icmpne 06b
            // 053: aload 2
            // 054: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 057: checkcast kotlinx/coroutines/flow/internal/SafeCollector
            // 05a: astore 5
            // 05c: aload 5
            // 05e: astore 1
            // 05f: aload 7
            // 061: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 064: goto 14a
            // 067: astore 2
            // 068: goto 152
            // 06b: new java/lang/IllegalStateException
            // 06e: dup
            // 06f: ldc "call to 'resume' before 'invoke' with coroutine"
            // 071: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
            // 074: athrow
            // 075: aload 2
            // 076: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$2 Ljava/lang/Object;
            // 079: checkcast kotlin/jvm/internal/Ref$BooleanRef
            // 07c: astore 5
            // 07e: aload 2
            // 07f: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 082: checkcast kotlinx/coroutines/flow/FlowCollector
            // 085: astore 1
            // 086: aload 2
            // 087: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 08a: checkcast kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1
            // 08d: astore 4
            // 08f: aload 7
            // 091: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 094: goto 0f0
            // 097: aload 7
            // 099: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
            // 09c: aload 2
            // 09d: checkcast kotlin/coroutines/Continuation
            // 0a0: astore 4
            // 0a2: new kotlin/jvm/internal/Ref$BooleanRef
            // 0a5: dup
            // 0a6: invokespecial kotlin/jvm/internal/Ref$BooleanRef.<init> ()V
            // 0a9: astore 5
            // 0ab: aload 5
            // 0ad: bipush 1
            // 0ae: putfield kotlin/jvm/internal/Ref$BooleanRef.element Z
            // 0b1: aload 0
            // 0b2: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.$this_onEmpty$inlined Lkotlinx/coroutines/flow/Flow;
            // 0b5: astore 4
            // 0b7: new kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$1$1
            // 0ba: dup
            // 0bb: aload 5
            // 0bd: aload 1
            // 0be: invokespecial kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$1$1.<init> (Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/flow/FlowCollector;)V
            // 0c1: checkcast kotlinx/coroutines/flow/FlowCollector
            // 0c4: astore 7
            // 0c6: aload 2
            // 0c7: aload 0
            // 0c8: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 0cb: aload 2
            // 0cc: aload 1
            // 0cd: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 0d0: aload 2
            // 0d1: aload 5
            // 0d3: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$2 Ljava/lang/Object;
            // 0d6: aload 2
            // 0d7: bipush 1
            // 0d8: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.label I
            // 0db: aload 4
            // 0dd: aload 7
            // 0df: aload 2
            // 0e0: invokeinterface kotlinx/coroutines/flow/Flow.collect (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 3
            // 0e5: aload 6
            // 0e7: if_acmpne 0ed
            // 0ea: aload 6
            // 0ec: areturn
            // 0ed: aload 0
            // 0ee: astore 4
            // 0f0: aload 5
            // 0f2: getfield kotlin/jvm/internal/Ref$BooleanRef.element Z
            // 0f5: ifeq 158
            // 0f8: new kotlinx/coroutines/flow/internal/SafeCollector
            // 0fb: dup
            // 0fc: aload 1
            // 0fd: aload 2
            // 0fe: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
            // 103: invokespecial kotlinx/coroutines/flow/internal/SafeCollector.<init> (Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V
            // 106: astore 5
            // 108: aload 5
            // 10a: astore 1
            // 10b: aload 4
            // 10d: getfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.$action$inlined Lkotlin/jvm/functions/Function2;
            // 110: astore 4
            // 112: aload 5
            // 114: astore 1
            // 115: aload 2
            // 116: aload 5
            // 118: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$0 Ljava/lang/Object;
            // 11b: aload 5
            // 11d: astore 1
            // 11e: aload 2
            // 11f: aconst_null
            // 120: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$1 Ljava/lang/Object;
            // 123: aload 5
            // 125: astore 1
            // 126: aload 2
            // 127: aconst_null
            // 128: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.L$2 Ljava/lang/Object;
            // 12b: aload 5
            // 12d: astore 1
            // 12e: aload 2
            // 12f: bipush 2
            // 130: putfield kotlinx/coroutines/flow/FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1.label I
            // 133: aload 5
            // 135: astore 1
            // 136: aload 4
            // 138: aload 5
            // 13a: aload 2
            // 13b: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
            // 140: astore 2
            // 141: aload 2
            // 142: aload 6
            // 144: if_acmpne 14a
            // 147: aload 6
            // 149: areturn
            // 14a: aload 5
            // 14c: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
            // 14f: goto 158
            // 152: aload 1
            // 153: invokevirtual kotlinx/coroutines/flow/internal/SafeCollector.releaseIntercepted ()V
            // 156: aload 2
            // 157: athrow
            // 158: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 15b: areturn
         }
      };
   }

   @JvmStatic
   public fun <T> Flow<T>.onStart(action: (FlowCollector<T>, Continuation<Unit>) -> Any?): Flow<T> {
      return new Flow<T>(var1, var0) {
         final Function2 $action$inlined;
         final Flow $this_onStart$inlined;

         {
            this.$action$inlined = var1;
            this.$this_onStart$inlined = var2;
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label52: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label52;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  Object L$0;
                  Object L$1;
                  Object L$2;
                  int label;
                  Object result;
                  final <unrepresentable> this$0;

                  {
                     super(var2x);
                     this.this$0 = var1;
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     this.result = var1;
                     this.label |= Integer.MIN_VALUE;
                     return this.this$0.collect(null, this);
                  }
               };
            }

            label122: {
               var var6: Any = var2.result;
               val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               val var5: <unrepresentable>;
               val var17: FlowCollector;
               if (var2.label != 0) {
                  if (var2.label != 1) {
                     if (var2.label != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var6);
                     return Unit.INSTANCE;
                  }

                  var1 = var2.L$2 as SafeCollector;
                  var17 = var2.L$1 as FlowCollector;
                  var5 = var2.L$0 as <unrepresentable>;

                  try {
                     ResultKt.throwOnFailure(var6);
                  } catch (var9: java.lang.Throwable) {
                     break label122;
                  }
               } else {
                  ResultKt.throwOnFailure(var6);
                  val var18: Continuation = var2;
                  val var19: SafeCollector = new SafeCollector(var1, var2.getContext());

                  try {
                     val var20: Function2 = this.$action$inlined;
                     var2.L$0 = this;
                     var2.L$1 = var1;
                     var2.L$2 = var19;
                     var2.label = 1;
                     var21 = var20.invoke(var19, var2);
                  } catch (var8: java.lang.Throwable) {
                     var2 = var8;
                     var1 = var19;
                     break label122;
                  }

                  if (var21 === var7) {
                     return var7;
                  }

                  var5 = this;
                  var6 = var19;
                  var17 = var1;
               }

               ((SafeCollector)var6).releaseIntercepted();
               val var15: Flow = var5.$this_onStart$inlined;
               var2.L$0 = null;
               var2.L$1 = null;
               var2.L$2 = null;
               var2.label = 2;
               if (var15.collect(var17, var2) === var7) {
                  return var7;
               }

               return Unit.INSTANCE;
            }

            var1.releaseIntercepted();
            throw var2;
         }
      };
   }

   @JvmStatic
   public inline fun <T, R> Flow<T>.transform(crossinline transform: (FlowCollector<R>, T, Continuation<Unit>) -> Any?): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Flow<T> $this_transform;
         final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> $transform;
         private Object L$0;
         int label;

         {
            super(2, var3x);
            this.$this_transform = var1;
            this.$transform = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_transform, this.$transform, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               var var4: FlowCollector = this.L$0 as FlowCollector;
               var1 = this.$this_transform;
               var4 = new FlowCollector(this.$transform, var4) {
                  final FlowCollector<R> $$this$flow;
                  final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> $transform;

                  {
                     this.$transform = var1;
                     this.$$this$flow = var2x;
                  }

                  @Override
                  public final Object emit(T var1, Continuation<? super Unit> var2x) {
                     label23: {
                        if (var2x is <unrepresentable>) {
                           val var4x: <unrepresentable> = var2x as <unrepresentable>;
                           if ((var2x.label and Integer.MIN_VALUE) != 0) {
                              var4x.label += Integer.MIN_VALUE;
                              var2x = var4x;
                              break label23;
                           }
                        }

                        var2x = new ContinuationImpl(this, var2x) {
                           int label;
                           Object result;
                           final <unrepresentable><T> this$0;

                           {
                              super(var2x);
                              this.this$0 = var1;
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              this.result = var1;
                              this.label |= Integer.MIN_VALUE;
                              return this.this$0.emit(null, this);
                           }
                        };
                     }

                     var var5: Any = var2x.result;
                     val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (var2x.label != 0) {
                        if (var2x.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                     } else {
                        ResultKt.throwOnFailure(var5);
                        val var6: Function3 = this.$transform;
                        var5 = this.$$this$flow;
                        var2x.label = 1;
                        if (var6.invoke(var5, var1, var2x) === var8) {
                           return var8;
                        }
                     }

                     return Unit.INSTANCE;
                  }

                  public final Object emit$$forInline(T var1, Continuation<? super Unit> var2x) {
                     new ContinuationImpl(this, var2x) {
                        int label;
                        Object result;
                        final <unrepresentable><T> this$0;

                        {
                           super(var2x);
                           this.this$0 = var1;
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           this.result = var1;
                           this.label |= Integer.MIN_VALUE;
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
                        }
                     };
                     this.$transform.invoke(this.$$this$flow, (T)var1, var2x);
                     return Unit.INSTANCE;
                  }
               };
               val var5: Continuation = this;
               this.label = 1;
               if (var1.collect(var4, var5) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }

         public final Object invokeSuspend$$forInline(Object var1) {
            this.$this_transform.collect(new FlowCollector(this.$transform, this.L$0 as FlowCollector) {
               final FlowCollector<R> $$this$flow;
               final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> $transform;

               {
                  this.$transform = var1;
                  this.$$this$flow = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label23: {
                     if (var2x is <unrepresentable>) {
                        val var4x: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4x.label += Integer.MIN_VALUE;
                           var2x = var4x;
                           break label23;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        int label;
                        Object result;
                        final <unrepresentable><T> this$0;

                        {
                           super(var2x);
                           this.this$0 = var1;
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           this.result = var1;
                           this.label |= Integer.MIN_VALUE;
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
                        }
                     };
                  }

                  var var5: Any = var2x.result;
                  val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     val var6: Function3 = this.$transform;
                     var5 = this.$$this$flow;
                     var2x.label = 1;
                     if (var6.invoke(var5, var1, var2x) === var8) {
                        return var8;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(T var1, Continuation<? super Unit> var2x) {
                  new ContinuationImpl(this, var2x) {
                     int label;
                     Object result;
                     final <unrepresentable><T> this$0;

                     {
                        super(var2x);
                        this.this$0 = var1;
                     }

                     @Override
                     public final Object invokeSuspend(Object var1) {
                        this.result = var1;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  this.$transform.invoke(this.$$this$flow, (T)var1, var2x);
                  return Unit.INSTANCE;
               }
            }, this as Continuation<? super Unit>);
            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   internal inline fun <T, R> Flow<T>.unsafeTransform(crossinline transform: (FlowCollector<R>, T, Continuation<Unit>) -> Any?): Flow<R> {
      return new Flow<R>(var0, var1) {
         final Flow $this_unsafeTransform$inlined;
         final Function3 $transform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$transform$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super R> var1, Continuation<? super Unit> var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(this.$transform$inlined, var1) {
               final FlowCollector<R> $this_unsafeFlow;
               final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> $transform;

               {
                  this.$transform = var1;
                  this.$this_unsafeFlow = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label23: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label23;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        int label;
                        Object result;
                        final <unrepresentable><T> this$0;

                        {
                           super(var2x);
                           this.this$0 = var1;
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           this.result = var1;
                           this.label |= Integer.MIN_VALUE;
                           return this.this$0.emit(null, this);
                        }
                     };
                  }

                  var var5: Any = var2x.result;
                  val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     val var6: Function3 = this.$transform;
                     var5 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     if (var6.invoke(var5, var1, var2x) === var8) {
                        return var8;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(T var1, Continuation<? super Unit> var2x) {
                  new ContinuationImpl(this, var2x) {
                     int label;
                     Object result;
                     final <unrepresentable><T> this$0;

                     {
                        super(var2x);
                        this.this$0 = var1;
                     }

                     @Override
                     public final Object invokeSuspend(Object var1) {
                        this.result = var1;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  this.$transform.invoke(this.$this_unsafeFlow, (T)var1, var2x);
                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }

         public Object collect$$forInline(FlowCollector var1, Continuation var2) {
            new ContinuationImpl(this, var2) {
               int label;
               Object result;
               final <unrepresentable> this$0;

               {
                  super(var2x);
                  this.this$0 = var1;
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  this.result = var1;
                  this.label |= Integer.MIN_VALUE;
                  return this.this$0.collect(null, this);
               }
            };
            this.$this_unsafeTransform$inlined.collect(new FlowCollector(this.$transform$inlined, var1) {
               final FlowCollector<R> $this_unsafeFlow;
               final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> $transform;

               {
                  this.$transform = var1;
                  this.$this_unsafeFlow = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label23: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label23;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        int label;
                        Object result;
                        final <unrepresentable><T> this$0;

                        {
                           super(var2x);
                           this.this$0 = var1;
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           this.result = var1;
                           this.label |= Integer.MIN_VALUE;
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
                        }
                     };
                  }

                  var var5: Any = var2x.result;
                  val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     val var6: Function3 = this.$transform;
                     var5 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     if (var6.invoke(var5, var1, var2x) === var8) {
                        return var8;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(T var1, Continuation<? super Unit> var2x) {
                  new ContinuationImpl(this, var2x) {
                     int label;
                     Object result;
                     final <unrepresentable><T> this$0;

                     {
                        super(var2x);
                        this.this$0 = var1;
                     }

                     @Override
                     public final Object invokeSuspend(Object var1) {
                        this.result = var1;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  this.$transform.invoke(this.$this_unsafeFlow, (T)var1, var2x);
                  return Unit.INSTANCE;
               }
            }, var2);
            return Unit.INSTANCE;
         }
      };
   }
}
