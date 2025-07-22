package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function2

private const val FINISHED: Int = 1
private const val INTERRUPTED: Int = 3
private const val INTERRUPTING: Int = 2
private const val WORKING: Int = 0

@JvmSynthetic
fun `access$runInterruptibleInExpectedContext`(var0: CoroutineContext, var1: Function0): Any {
   return runInterruptibleInExpectedContext(var0, var1);
}

public suspend fun <T> runInterruptible(context: CoroutineContext = ..., block: () -> T): T {
   return BuildersKt.withContext(var0, (new Function2<CoroutineScope, Continuation<? super T>, Object>(var1, null) {
      final Function0<T> $block;
      private Object L$0;
      int label;

      {
         super(2, var2x);
         this.$block = var1;
      }

      @Override
      public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
         val var3: Function2 = new <anonymous constructor>(this.$block, var2);
         var3.L$0 = var1;
         return var3 as Continuation<Unit>;
      }

      public final Object invoke(CoroutineScope var1, Continuation<? super T> var2x) {
         return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
      }

      @Override
      public final Object invokeSuspend(Object var1) {
         IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (this.label == 0) {
            ResultKt.throwOnFailure(var1);
            return InterruptibleKt.access$runInterruptibleInExpectedContext((this.L$0 as CoroutineScope).getCoroutineContext(), this.$block);
         } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }
      }
   }) as Function2, var2);
}

@JvmSynthetic
fun `runInterruptible$default`(var0: CoroutineContext, var1: Function0, var2: Continuation, var3: Int, var4: Any): Any {
   if ((var3 and 1) != 0) {
      var0 = EmptyCoroutineContext.INSTANCE;
   }

   return runInterruptible(var0, var1, var2);
}

private fun <T> runInterruptibleInExpectedContext(coroutineContext: CoroutineContext, block: () -> T): T {
   label25: {
      var var2: ThreadState;
      try {
         var2 = new ThreadState(JobKt.getJob(var0));
         var2.setup();
      } catch (var6: InterruptedException) {
         throw new CancellationException("Blocking call was interrupted due to parent cancellation").initCause(var6);
      }

      try {
         val var10: Any = var1.invoke();
      } catch (var5: java.lang.Throwable) {
         try {
            var2.clearInterrupt();
         } catch (var3: InterruptedException) {
            throw new CancellationException("Blocking call was interrupted due to parent cancellation").initCause(var3);
         }
      }

      try {
         var2.clearInterrupt();
      } catch (var4: InterruptedException) {
         throw new CancellationException("Blocking call was interrupted due to parent cancellation").initCause(var4);
      }
   }
}
