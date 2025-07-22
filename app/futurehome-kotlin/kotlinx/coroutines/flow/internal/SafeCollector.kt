package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.JobKt
import kotlinx.coroutines.flow.FlowCollector

internal class SafeCollector<T>(collector: FlowCollector<Any>, collectContext: CoroutineContext) : ContinuationImpl(
         NoOpContinuation.INSTANCE, EmptyCoroutineContext.INSTANCE
      ),
   FlowCollector<T>,
   CoroutineStackFrame {
   public open val callerFrame: CoroutineStackFrame?
      public open get() {
         val var2: CoroutineStackFrame;
         if (this.completion is CoroutineStackFrame) {
            var2 = this.completion as CoroutineStackFrame;
         } else {
            var2 = null;
         }

         return var2;
      }


   internal final val collectContext: CoroutineContext
   internal final val collectContextSize: Int
   internal final val collector: FlowCollector<Any>
   private final var completion: Continuation<Unit>?

   public open val context: CoroutineContext
      public open get() {
         var var1: CoroutineContext = this.lastEmissionContext;
         if (this.lastEmissionContext == null) {
            var1 = EmptyCoroutineContext.INSTANCE;
         }

         return var1;
      }


   private final var lastEmissionContext: CoroutineContext?

   init {
      this.collector = var1;
      this.collectContext = var2;
      this.collectContextSize = var2.fold(0, <unrepresentable>.INSTANCE).intValue();
   }

   private fun checkContext(currentContext: CoroutineContext, previousContext: CoroutineContext?, value: Any) {
      if (var2 is DownstreamExceptionContext) {
         this.exceptionTransparencyViolated(var2 as DownstreamExceptionContext, var3);
      }

      SafeCollector_commonKt.checkContext(this, var1);
   }

   private fun emit(uCont: Continuation<Unit>, value: Any): Any? {
      val var4: CoroutineContext = var1.getContext();
      JobKt.ensureActive(var4);
      if (this.lastEmissionContext != var4) {
         this.checkContext(var4, this.lastEmissionContext, (T)var2);
         this.lastEmissionContext = var4;
      }

      this.completion = var1;
      val var7: Function3 = SafeCollectorKt.access$getEmitFun$p();
      val var5: FlowCollector = this.collector;
      val var6: Any = var7.invoke(var5, var2, this);
      if (!(var6 == IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
         this.completion = null;
      }

      return var6;
   }

   private fun exceptionTransparencyViolated(exception: DownstreamExceptionContext, value: Any?) {
      val var3: StringBuilder = new StringBuilder(
         "\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception "
      );
      var3.append(var1.e);
      var3.append(", but then emission attempt of value '");
      var3.append(var2);
      var3.append(
         "' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            "
      );
      throw new IllegalStateException(StringsKt.trimIndent(var3.toString()).toString());
   }

   public override suspend fun emit(value: Any) {
      try {
         var1 = this.emit(var2, (T)var1);
      } catch (var3: java.lang.Throwable) {
         this.lastEmissionContext = new DownstreamExceptionContext(var3, var2.getContext());
         throw var3;
      }

      if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return null;
   }

   public override fun invokeSuspend(result: Result<Any?>): Any {
      val var2: java.lang.Throwable = Result.exceptionOrNull-impl(var1);
      if (var2 != null) {
         this.lastEmissionContext = new DownstreamExceptionContext(var2, this.getContext());
      }

      if (this.completion != null) {
         this.completion.resumeWith(var1);
      }

      return IntrinsicsKt.getCOROUTINE_SUSPENDED();
   }

   public override fun releaseIntercepted() {
      super.releaseIntercepted();
   }
}
