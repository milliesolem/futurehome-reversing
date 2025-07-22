package kotlin

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.BaseContinuationImpl
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.TypeIntrinsics

private class DeepRecursiveScopeImpl<T, R>(block: (DeepRecursiveScope<Any, Any>, Any, Continuation<Any>) -> Any?, value: Any) : DeepRecursiveScope(),
   Continuation<R> {
   private final var function: (DeepRecursiveScope<*, *>, Any?, Continuation<Any?>) -> Any?
   private final var value: Any?
   private final var cont: Continuation<Any?>?
   private final var result: Result<Any?>

   public open val context: CoroutineContext
      public open get() {
         return EmptyCoroutineContext.INSTANCE;
      }


   init {
      this.function = var1;
      this.value = var2;
      this.cont = this as Continuation<Object>;
      this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
   }

   private fun crossFunctionCompletion(currentFunction: (DeepRecursiveScope<*, *>, Any?, Continuation<Any?>) -> Any?, cont: Continuation<Any?>): Continuation<
         Any?
      > {
      return new Continuation<Object>(EmptyCoroutineContext.INSTANCE, this, var1, var2) {
         final Continuation $cont$inlined;
         final CoroutineContext $context;
         final Function3 $currentFunction$inlined;
         final DeepRecursiveScopeImpl this$0;

         {
            this.$context = var1;
            this.this$0 = var2;
            this.$currentFunction$inlined = var3;
            this.$cont$inlined = var4;
         }

         @Override
         public CoroutineContext getContext() {
            return this.$context;
         }

         @Override
         public void resumeWith(Object var1) {
            DeepRecursiveScopeImpl.access$setFunction$p(this.this$0, this.$currentFunction$inlined);
            DeepRecursiveScopeImpl.access$setCont$p(this.this$0, this.$cont$inlined);
            DeepRecursiveScopeImpl.access$setResult$p(this.this$0, var1);
         }
      };
   }

   public override suspend fun callRecursive(value: Any): Any {
      this.cont = var2;
      this.value = var1;
      var1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return var1;
   }

   public override suspend fun <U, S> DeepRecursiveFunction<U, S>.callRecursive(value: U): S {
      val var5: Function3 = var1.getBlock$kotlin_stdlib();
      val var4: DeepRecursiveScopeImpl = this;
      val var7: Function3 = this.function;
      if (var5 != this.function) {
         this.function = var5;
         this.cont = this.crossFunctionCompletion(var7, var3);
      } else {
         this.cont = var3;
      }

      this.value = var2;
      val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var6 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var3);
      }

      return var6;
   }

   public override fun resumeWith(result: Result<Any>) {
      this.cont = null;
      this.result = var1;
   }

   public fun runCallLoop(): Any {
      while (true) {
         var var1: Any = this.result;
         val var2: Continuation = this.cont;
         if (this.cont == null) {
            ResultKt.throwOnFailure(this.result);
            return (R)var1;
         }

         if (Result.equals-impl0(DeepRecursiveKt.access$getUNDEFINED_RESULT$p(), var1)) {
            try {
               var1 = this.value;
               if (this.function !is BaseContinuationImpl) {
                  var1 = IntrinsicsKt.wrapWithContinuationImpl(
                     (Function3<? super DeepRecursiveScopeImpl<T, R>, ? super Object, ? super Continuation<? super Object>, ? extends Object>)this.function,
                     this,
                     this.value,
                     var2
                  );
               } else {
                  var1 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.function, 3) as Function3).invoke(this, var1, var2);
               }
            } catch (var4: java.lang.Throwable) {
               val var3: Result.Companion = Result.Companion;
               var2.resumeWith(Result.constructor-impl(ResultKt.createFailure(var4)));
               continue;
            }

            if (var1 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               val var9: Result.Companion = Result.Companion;
               var2.resumeWith(Result.constructor-impl(var1));
            }
         } else {
            this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
            var2.resumeWith(var1);
         }
      }
   }
}
