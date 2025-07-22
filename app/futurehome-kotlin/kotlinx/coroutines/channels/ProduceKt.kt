package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.CoroutineContextKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart

public suspend fun ProducerScope<*>.awaitClose(block: () -> Unit = ...) {
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
   // 001: instanceof kotlinx/coroutines/channels/ProduceKt$awaitClose$1
   // 004: ifeq 028
   // 007: aload 2
   // 008: checkcast kotlinx/coroutines/channels/ProduceKt$awaitClose$1
   // 00b: astore 4
   // 00d: aload 4
   // 00f: getfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.label I
   // 012: ldc -2147483648
   // 014: iand
   // 015: ifeq 028
   // 018: aload 4
   // 01a: aload 4
   // 01c: getfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.label I
   // 01f: ldc -2147483648
   // 021: iadd
   // 022: putfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.label I
   // 025: goto 032
   // 028: new kotlinx/coroutines/channels/ProduceKt$awaitClose$1
   // 02b: dup
   // 02c: aload 2
   // 02d: invokespecial kotlinx/coroutines/channels/ProduceKt$awaitClose$1.<init> (Lkotlin/coroutines/Continuation;)V
   // 030: astore 4
   // 032: aload 4
   // 034: getfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.result Ljava/lang/Object;
   // 037: astore 6
   // 039: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
   // 03c: astore 5
   // 03e: aload 4
   // 040: getfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.label I
   // 043: istore 3
   // 044: iload 3
   // 045: ifeq 077
   // 048: iload 3
   // 049: bipush 1
   // 04a: if_icmpne 06d
   // 04d: aload 4
   // 04f: getfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.L$1 Ljava/lang/Object;
   // 052: checkcast kotlin/jvm/functions/Function0
   // 055: astore 0
   // 056: aload 4
   // 058: getfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.L$0 Ljava/lang/Object;
   // 05b: checkcast kotlinx/coroutines/channels/ProducerScope
   // 05e: astore 1
   // 05f: aload 0
   // 060: astore 2
   // 061: aload 6
   // 063: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
   // 066: goto 11d
   // 069: astore 0
   // 06a: goto 128
   // 06d: new java/lang/IllegalStateException
   // 070: dup
   // 071: ldc "call to 'resume' before 'invoke' with coroutine"
   // 073: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
   // 076: athrow
   // 077: aload 6
   // 079: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
   // 07c: aload 4
   // 07e: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
   // 083: getstatic kotlinx/coroutines/Job.Key Lkotlinx/coroutines/Job$Key;
   // 086: checkcast kotlin/coroutines/CoroutineContext$Key
   // 089: invokeinterface kotlin/coroutines/CoroutineContext.get (Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element; 2
   // 08e: aload 0
   // 08f: if_acmpne 131
   // 092: aload 1
   // 093: astore 2
   // 094: aload 4
   // 096: aload 0
   // 097: putfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.L$0 Ljava/lang/Object;
   // 09a: aload 1
   // 09b: astore 2
   // 09c: aload 4
   // 09e: aload 1
   // 09f: putfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.L$1 Ljava/lang/Object;
   // 0a2: aload 1
   // 0a3: astore 2
   // 0a4: aload 4
   // 0a6: bipush 1
   // 0a7: putfield kotlinx/coroutines/channels/ProduceKt$awaitClose$1.label I
   // 0aa: aload 1
   // 0ab: astore 2
   // 0ac: aload 4
   // 0ae: checkcast kotlin/coroutines/Continuation
   // 0b1: astore 7
   // 0b3: aload 1
   // 0b4: astore 2
   // 0b5: new kotlinx/coroutines/CancellableContinuationImpl
   // 0b8: astore 6
   // 0ba: aload 1
   // 0bb: astore 2
   // 0bc: aload 6
   // 0be: aload 7
   // 0c0: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
   // 0c3: bipush 1
   // 0c4: invokespecial kotlinx/coroutines/CancellableContinuationImpl.<init> (Lkotlin/coroutines/Continuation;I)V
   // 0c7: aload 1
   // 0c8: astore 2
   // 0c9: aload 6
   // 0cb: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.initCancellability ()V
   // 0ce: aload 1
   // 0cf: astore 2
   // 0d0: aload 6
   // 0d2: checkcast kotlinx/coroutines/CancellableContinuation
   // 0d5: astore 7
   // 0d7: aload 1
   // 0d8: astore 2
   // 0d9: new kotlinx/coroutines/channels/ProduceKt$awaitClose$4$1
   // 0dc: astore 8
   // 0de: aload 1
   // 0df: astore 2
   // 0e0: aload 8
   // 0e2: aload 7
   // 0e4: invokespecial kotlinx/coroutines/channels/ProduceKt$awaitClose$4$1.<init> (Lkotlinx/coroutines/CancellableContinuation;)V
   // 0e7: aload 1
   // 0e8: astore 2
   // 0e9: aload 0
   // 0ea: aload 8
   // 0ec: checkcast kotlin/jvm/functions/Function1
   // 0ef: invokeinterface kotlinx/coroutines/channels/ProducerScope.invokeOnClose (Lkotlin/jvm/functions/Function1;)V 2
   // 0f4: aload 1
   // 0f5: astore 2
   // 0f6: aload 6
   // 0f8: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
   // 0fb: astore 6
   // 0fd: aload 1
   // 0fe: astore 2
   // 0ff: aload 6
   // 101: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
   // 104: if_acmpne 111
   // 107: aload 1
   // 108: astore 2
   // 109: aload 4
   // 10b: checkcast kotlin/coroutines/Continuation
   // 10e: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
   // 111: aload 1
   // 112: astore 0
   // 113: aload 6
   // 115: aload 5
   // 117: if_acmpne 11d
   // 11a: aload 5
   // 11c: areturn
   // 11d: aload 0
   // 11e: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
   // 123: pop
   // 124: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 127: areturn
   // 128: aload 2
   // 129: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
   // 12e: pop
   // 12f: aload 0
   // 130: athrow
   // 131: new java/lang/IllegalStateException
   // 134: dup
   // 135: ldc "awaitClose() can only be invoked from the producer context"
   // 137: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
   // 13a: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
   // 13d: athrow
}

@JvmSynthetic
fun `awaitClose$default`(var0: ProducerScope, var1: Function0, var2: Continuation, var3: Int, var4: Any): Any {
   if ((var3 and 1) != 0) {
      var1 = <unrepresentable>.INSTANCE;
   }

   return awaitClose(var0, var1, var2);
}

public fun <E> CoroutineScope.produce(
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = 0,
   block: (ProducerScope<E>, Continuation<Unit>) -> Any?
): ReceiveChannel<E> {
   return produce(var0, var1, var2, BufferOverflow.SUSPEND, CoroutineStart.DEFAULT, null, var3);
}

public fun <E> CoroutineScope.produce(
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = 0,
   start: CoroutineStart = CoroutineStart.DEFAULT,
   onCompletion: ((Throwable?) -> Unit)? = null,
   block: (ProducerScope<E>, Continuation<Unit>) -> Any?
): ReceiveChannel<E> {
   return produce(var0, var1, var2, BufferOverflow.SUSPEND, var3, var4, var5);
}

internal fun <E> CoroutineScope.produce(
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = 0,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
   start: CoroutineStart = CoroutineStart.DEFAULT,
   onCompletion: ((Throwable?) -> Unit)? = null,
   block: (ProducerScope<E>, Continuation<Unit>) -> Any?
): ReceiveChannel<E> {
   val var7: ProducerCoroutine = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(var0, var1), ChannelKt.Channel$default(var2, var3, null, 4, null));
   if (var5 != null) {
      var7.invokeOnCompletion(var5);
   }

   var7.start(var4, var7, var6);
   return var7;
}

@JvmSynthetic
fun `produce$default`(var0: CoroutineScope, var1: CoroutineContext, var2: Int, var3: Function2, var4: Int, var5: Any): ReceiveChannel {
   if ((var4 and 1) != 0) {
      var1 = EmptyCoroutineContext.INSTANCE;
   }

   if ((var4 and 2) != 0) {
      var2 = 0;
   }

   return produce(var0, var1, var2, var3);
}

@JvmSynthetic
fun `produce$default`(var0: CoroutineScope, var1: CoroutineContext, var2: Int, var3: CoroutineStart, var4: Function1, var5: Function2, var6: Int, var7: Any): ReceiveChannel {
   if ((var6 and 1) != 0) {
      var1 = EmptyCoroutineContext.INSTANCE;
   }

   if ((var6 and 2) != 0) {
      var2 = 0;
   }

   if ((var6 and 4) != 0) {
      var3 = CoroutineStart.DEFAULT;
   }

   if ((var6 and 8) != 0) {
      var4 = null;
   }

   return produce(var0, var1, var2, var3, var4, var5);
}

@JvmSynthetic
fun `produce$default`(
   var0: CoroutineScope, var1: CoroutineContext, var2: Int, var3: BufferOverflow, var4: CoroutineStart, var5: Function1, var6: Function2, var7: Int, var8: Any
): ReceiveChannel {
   if ((var7 and 1) != 0) {
      var1 = EmptyCoroutineContext.INSTANCE;
   }

   if ((var7 and 2) != 0) {
      var2 = 0;
   }

   if ((var7 and 4) != 0) {
      var3 = BufferOverflow.SUSPEND;
   }

   if ((var7 and 8) != 0) {
      var4 = CoroutineStart.DEFAULT;
   }

   if ((var7 and 16) != 0) {
      var5 = null;
   }

   return produce(var0, var1, var2, var3, var4, var5, var6);
}
