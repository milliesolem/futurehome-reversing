package kotlinx.coroutines.future

import java.util.concurrent.CancellationException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.ExecutionException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CompletableDeferredKt
import kotlinx.coroutines.CoroutineContextKt
import kotlinx.coroutines.CoroutineExceptionHandlerKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExceptionsKt
import kotlinx.coroutines.Job
import kotlinx.coroutines.JobKt
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

@JvmSynthetic
fun `$r8$lambda$CVsR10o-YJP4IN5741Ey9yO9s50`(var0: Function2, var1: Any, var2: java.lang.Throwable): Any {
   return asDeferred$lambda$4(var0, var1, var2);
}

@JvmSynthetic
fun `$r8$lambda$nhDb5E9NjAZTF31felWqlfP4Z1A`(var0: Job, var1: Any, var2: java.lang.Throwable): Unit {
   return setupCancellation$lambda$2(var0, var1, var2);
}

public fun <T> Deferred<T>.asCompletableFuture(): CompletableFuture<T> {
   val var1: CompletableFuture = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m();
   setupCancellation(var0, var1);
   var0.invokeOnCompletion(
      (
         new Function1<java.lang.Throwable, Unit>(var1, var0) {
            final CompletableFuture<T> $future;
            final Deferred<T> $this_asCompletableFuture;

            {
               super(1);
               this.$future = var1;
               this.$this_asCompletableFuture = var2;
            }

            // $VF: Could not inline inconsistent finally blocks
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            public final void invoke(java.lang.Throwable var1) {
               try {
                  NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.$future, this.$this_asCompletableFuture.getCompleted());
               } catch (var2: java.lang.Throwable) {
                  NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.$future, var2);
                  return;
               }
            }
         }
      ) as (java.lang.Throwable?) -> Unit
   );
   return var1;
}

public fun Job.asCompletableFuture(): CompletableFuture<Unit> {
   val var1: CompletableFuture = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m();
   setupCancellation(var0, var1);
   var0.invokeOnCompletion((new Function1<java.lang.Throwable, Unit>(var1) {
      final CompletableFuture<Unit> $future;

      {
         super(1);
         this.$future = var1;
      }

      public final void invoke(java.lang.Throwable var1) {
         if (var1 == null) {
            NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.$future, Unit.INSTANCE);
         } else {
            NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.$future, var1);
         }
      }
   }) as (java.lang.Throwable?) -> Unit);
   return var1;
}

public fun <T> CompletionStage<T>.asDeferred(): Deferred<T> {
   val var2: CompletableFuture = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var0);
   if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var2)) {
      try {
         var6 = CompletableDeferredKt.CompletableDeferred(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var2));
      } catch (var3: java.lang.Throwable) {
         val var8: ExecutionException;
         if (var3 is ExecutionException) {
            var8 = var3 as ExecutionException;
         } else {
            var8 = null;
         }

         var var7: java.lang.Throwable = var3;
         if (var8 != null) {
            var7 = var8.getCause();
            if (var7 == null) {
               var7 = var3;
            }
         }

         val var5: CompletableDeferred = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
         var5.completeExceptionally(var7);
         return var5;
      }

      return var6;
   } else {
      val var1: CompletableDeferred = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
      FutureKt$$ExternalSyntheticAPIConversion0.m(
         var0,
         new FutureKt$$ExternalSyntheticLambda6(
            (
               new Function2<T, java.lang.Throwable, Object>(var1) {
                  final CompletableDeferred<T> $result;

                  {
                     super(2);
                     this.$result = var1;
                  }

                  // $VF: Duplicated exception handlers to handle obfuscated exceptions
                  // $VF: Could not inline inconsistent finally blocks
                  // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
                  public final Object invoke(T var1, java.lang.Throwable var2) {
                     val var3: Boolean;
                     if (var2 == null) {
                        try {
                           var3 = this.$result.complete(var1);
                        } catch (var9: java.lang.Throwable) {
                           CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, var9);
                           return Unit.INSTANCE;
                        }
                     } else {
                        var var5: CompletableDeferred;
                        label49: {
                           try {
                              var5 = this.$result;
                              if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var2)) {
                                 var1 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var2);
                                 break label49;
                              }
                           } catch (var10: java.lang.Throwable) {
                              CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, var10);
                              return Unit.INSTANCE;
                           }

                           var1 = null;
                        }

                        var var4: java.lang.Throwable = var2;
                        if (var1 != null) {
                           try {
                              var4 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var1);
                           } catch (var8: java.lang.Throwable) {
                              CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, var8);
                              return Unit.INSTANCE;
                           }

                           if (var4 == null) {
                              var4 = var2;
                           }
                        }

                        try {
                           var3 = var5.completeExceptionally(var4);
                        } catch (var7: java.lang.Throwable) {
                           CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, var7);
                           return Unit.INSTANCE;
                        }
                     }

                     try {
                        var1 = var3;
                     } catch (var6: java.lang.Throwable) {
                        CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, var6);
                        return Unit.INSTANCE;
                     }

                     return var1;
                  }
               }
            ) as Function2
         )
      );
      JobKt.cancelFutureOnCompletion(var1, var2);
      return var1;
   }
}

fun `asDeferred$lambda$4`(var0: Function2, var1: Any, var2: java.lang.Throwable): Any {
   return var0.invoke(var1, var2);
}

public suspend fun <T> CompletionStage<T>.await(): T {
   val var5: CompletableFuture = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var0);
   if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var5)) {
      try {
         return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var5);
      } catch (var6: ExecutionException) {
         val var9: java.lang.Throwable = var6.getCause();
         var var8: java.lang.Throwable = var9;
         if (var9 == null) {
            var8 = var6;
         }

         throw var8;
      }
   } else {
      val var3: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
      var3.initCancellability();
      val var4: CancellableContinuation = var3;
      val var2: ContinuationHandler = new ContinuationHandler(var3);
      FutureKt$$ExternalSyntheticAPIConversion0.m(var0, var2);
      var4.invokeOnCancellation((new Function1<java.lang.Throwable, Unit>(var5, var2) {
         final ContinuationHandler<T> $consumer;
         final CompletableFuture<T> $future;

         {
            super(1);
            this.$future = var1;
            this.$consumer = var2;
         }

         public final void invoke(java.lang.Throwable var1) {
            NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.$future, false);
            this.$consumer.cont = null;
         }
      }) as (java.lang.Throwable?) -> Unit);
      val var7: Any = var3.getResult();
      if (var7 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return var7;
   }
}

public fun <T> CoroutineScope.future(
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   start: CoroutineStart = CoroutineStart.DEFAULT,
   block: (CoroutineScope, Continuation<T>) -> Any?
): CompletableFuture<T> {
   if (!var2.isLazy()) {
      var1 = CoroutineContextKt.newCoroutineContext(var0, var1);
      val var5: CompletableFuture = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m();
      val var7: CompletableFutureCoroutine = new CompletableFutureCoroutine(var1, var5);
      FutureKt$$ExternalSyntheticAPIConversion0.m(var5, var7);
      var7.start(var2, var7, var3);
      return var5;
   } else {
      val var4: StringBuilder = new StringBuilder();
      var4.append(var2);
      var4.append(" start is not supported");
      throw new IllegalArgumentException(var4.toString().toString());
   }
}

@JvmSynthetic
fun `future$default`(var0: CoroutineScope, var1: CoroutineContext, var2: CoroutineStart, var3: Function2, var4: Int, var5: Any): CompletableFuture {
   if ((var4 and 1) != 0) {
      var1 = EmptyCoroutineContext.INSTANCE;
   }

   if ((var4 and 2) != 0) {
      var2 = CoroutineStart.DEFAULT;
   }

   return future(var0, var1, var2, var3);
}

private fun Job.setupCancellation(future: CompletableFuture<*>) {
   FutureKt$$ExternalSyntheticAPIConversion0.m(var1, new FutureKt$$ExternalSyntheticLambda7(var0));
}

fun `setupCancellation$lambda$2`(var0: Job, var1: Any, var2: java.lang.Throwable): Unit {
   var1 = null;
   var var3: CancellationException = null;
   if (var2 != null) {
      if (var2 is CancellationException) {
         var3 = var2 as CancellationException;
      }

      var1 = var3;
      if (var3 == null) {
         var1 = ExceptionsKt.CancellationException("CompletableFuture was completed exceptionally", var2);
      }
   }

   var0.cancel(var1);
   return Unit.INSTANCE;
}
