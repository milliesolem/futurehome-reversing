package kotlin.coroutines

import kotlin.contracts.InvocationKind
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1

public final val coroutineContext: CoroutineContext
   public final inline get() {
      throw new NotImplementedError("Implemented as intrinsic");
   }


public inline fun <T> Continuation(context: CoroutineContext, crossinline resumeWith: (Result<T>) -> Unit): Continuation<T> {
   return new Continuation<T>(var0, var1) {
      final CoroutineContext $context;
      final Function1<Result<? extends T>, Unit> $resumeWith;

      {
         this.$context = var1;
         this.$resumeWith = var2;
      }

      @Override
      public CoroutineContext getContext() {
         return this.$context;
      }

      @Override
      public void resumeWith(Object var1) {
         this.$resumeWith.invoke(Result.box-impl(var1));
      }
   };
}

public fun <T> ((Continuation<T>) -> Any?).createCoroutine(completion: Continuation<T>): Continuation<Unit> {
   return new SafeContinuation<>(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(var0, var1)), IntrinsicsKt.getCOROUTINE_SUSPENDED());
}

public fun <R, T> ((R, Continuation<T>) -> Any?).createCoroutine(receiver: R, completion: Continuation<T>): Continuation<Unit> {
   return new SafeContinuation<>(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(var0, var1, var2)), IntrinsicsKt.getCOROUTINE_SUSPENDED());
}

public inline fun <T> Continuation<T>.resume(value: T) {
   val var2: Result.Companion = Result.Companion;
   var0.resumeWith(Result.constructor-impl(var1));
}

public inline fun <T> Continuation<T>.resumeWithException(exception: Throwable) {
   val var2: Result.Companion = Result.Companion;
   var0.resumeWith(Result.constructor-impl(ResultKt.createFailure(var1)));
}

public fun <T> ((Continuation<T>) -> Any?).startCoroutine(completion: Continuation<T>) {
   val var2: Continuation = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(var0, var1));
   val var3: Result.Companion = Result.Companion;
   var2.resumeWith(Result.constructor-impl(Unit.INSTANCE));
}

public fun <R, T> ((R, Continuation<T>) -> Any?).startCoroutine(receiver: R, completion: Continuation<T>) {
   var1 = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(var0, var1, var2));
   val var3: Result.Companion = Result.Companion;
   var1.resumeWith(Result.constructor-impl(Unit.INSTANCE));
}

public suspend inline fun <T> suspendCoroutine(crossinline block: (Continuation<T>) -> Unit): T {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var2: Continuation = var1;
   val var4: SafeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(var1));
   var0.invoke(var4);
   val var3: Any = var4.getOrThrow();
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var3;
}
