package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.internal.ThreadContextKt

@JvmSynthetic
fun `access$withUndispatchedContextCollector`(var0: FlowCollector, var1: CoroutineContext): FlowCollector {
   return withUndispatchedContextCollector(var0, var1);
}

internal fun <T> Flow<T>.asChannelFlow(): ChannelFlow<T> {
   val var1: ChannelFlow;
   if (var0 is ChannelFlow) {
      var1 = var0 as ChannelFlow;
   } else {
      var1 = null;
   }

   var var2: ChannelFlow = var1;
   if (var1 == null) {
      var2 = new ChannelFlowOperatorImpl(var0, null, 0, null, 14, null);
   }

   return var2;
}

internal suspend fun <T, V> withContextUndispatched(newContext: CoroutineContext, value: V, countOrElement: Any = ..., block: (V, Continuation<T>) -> Any?): T {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
   //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
   //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
   //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
   //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
   //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
   //
   // Bytecode:
   // 00: aload 0
   // 01: aload 2
   // 02: invokestatic kotlinx/coroutines/internal/ThreadContextKt.updateThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
   // 05: astore 2
   // 06: new kotlinx/coroutines/flow/internal/StackFrameContinuation
   // 09: astore 5
   // 0b: aload 5
   // 0d: aload 4
   // 0f: aload 0
   // 10: invokespecial kotlinx/coroutines/flow/internal/StackFrameContinuation.<init> (Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;)V
   // 13: aload 5
   // 15: checkcast kotlin/coroutines/Continuation
   // 18: astore 5
   // 1a: aload 3
   // 1b: bipush 2
   // 1c: invokestatic kotlin/jvm/internal/TypeIntrinsics.beforeCheckcastToFunctionOfArity (Ljava/lang/Object;I)Ljava/lang/Object;
   // 1f: checkcast kotlin/jvm/functions/Function2
   // 22: aload 1
   // 23: aload 5
   // 25: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
   // 2a: astore 1
   // 2b: aload 0
   // 2c: aload 2
   // 2d: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
   // 30: aload 1
   // 31: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
   // 34: if_acmpne 3c
   // 37: aload 4
   // 39: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
   // 3c: aload 1
   // 3d: areturn
   // 3e: astore 1
   // 3f: aload 0
   // 40: aload 2
   // 41: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
   // 44: aload 1
   // 45: athrow
}

@JvmSynthetic
fun `withContextUndispatched$default`(var0: CoroutineContext, var1: Any, var2: Any, var3: Function2, var4: Continuation, var5: Int, var6: Any): Any {
   if ((var5 and 4) != 0) {
      var2 = ThreadContextKt.threadContextElements(var0);
   }

   return withContextUndispatched(var0, var1, var2, var3, var4);
}

private fun <T> FlowCollector<T>.withUndispatchedContextCollector(emitContext: CoroutineContext): FlowCollector<T> {
   val var2: Boolean;
   if (var0 is SendingCollector) {
      var2 = true;
   } else {
      var2 = var0 is NopCollector;
   }

   if (!var2) {
      var0 = new UndispatchedContextCollector(var0, var1);
   }

   return var0;
}
