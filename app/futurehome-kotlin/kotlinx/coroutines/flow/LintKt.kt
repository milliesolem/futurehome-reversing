package kotlinx.coroutines.flow

import java.util.concurrent.CancellationException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.jvm.functions.Function2

@Deprecated(
   level = DeprecationLevel.ERROR,
   message = "coroutineContext is resolved into the property of outer CoroutineScope which is likely to be an error.Use currentCoroutineContext() instead or specify the receiver of coroutineContext explicitly",
   replaceWith = @ReplaceWith(
      expression = "currentCoroutineContext()",
      imports = {}
   )
)
public final val coroutineContext: CoroutineContext
   public final get() {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }


@Deprecated(
   level = DeprecationLevel.ERROR,
   message = "isActive is resolved into the extension of outer CoroutineScope which is likely to be an error.Use currentCoroutineContext().isActive or cancellable() operator instead or specify the receiver of isActive explicitly. Additionally, flow {} builder emissions are cancellable by default.",
   replaceWith = @ReplaceWith(
      expression = "currentCoroutineContext().isActive",
      imports = {}
   )
)
public final val isActive: Boolean
   public final get() {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }


@Deprecated(level = DeprecationLevel.ERROR, message = "cancel() is resolved into the extension of outer CoroutineScope which is likely to be an error.Use currentCoroutineContext().cancel() instead or specify the receiver of cancel() explicitly", replaceWith = @ReplaceWith(expression = "currentCoroutineContext().cancel(cause)", imports = []))
public fun FlowCollector<*>.cancel(cause: CancellationException? = null) {
   FlowKt.noImpl();
   throw new KotlinNothingValueException();
}

@JvmSynthetic
fun `cancel$default`(var0: FlowCollector, var1: CancellationException, var2: Int, var3: Any) {
   if ((var2 and 1) != 0) {
      var1 = null;
   }

   cancel(var0, var1);
}

@Deprecated(level = DeprecationLevel.ERROR, message = "Applying 'cancellable' to a SharedFlow has no effect. See the SharedFlow documentation on Operator Fusion.", replaceWith = @ReplaceWith(expression = "this", imports = []))
public fun <T> SharedFlow<T>.cancellable(): Flow<T> {
   FlowKt.noImpl();
   throw new KotlinNothingValueException();
}

@Deprecated(level = DeprecationLevel.WARNING, message = "SharedFlow never completes, so this operator typically has not effect, it can only catch exceptions from 'onSubscribe' operator", replaceWith = @ReplaceWith(expression = "this", imports = []))
public inline fun <T> SharedFlow<T>.catch(noinline action: (FlowCollector<T>, Throwable, Continuation<Unit>) -> Any?): Flow<T> {
   return FlowKt.catch(var0, var1);
}

@Deprecated(level = DeprecationLevel.ERROR, message = "Applying 'conflate' to StateFlow has no effect. See the StateFlow documentation on Operator Fusion.", replaceWith = @ReplaceWith(expression = "this", imports = []))
public fun <T> StateFlow<T>.conflate(): Flow<T> {
   FlowKt.noImpl();
   throw new KotlinNothingValueException();
}

@Deprecated(level = DeprecationLevel.WARNING, message = "SharedFlow never completes, so this terminal operation never completes.")
public suspend inline fun <T> SharedFlow<T>.count(): Int {
   return FlowKt.count(var0, var1);
}

@Deprecated(level = DeprecationLevel.ERROR, message = "Applying 'distinctUntilChanged' to StateFlow has no effect. See the StateFlow documentation on Operator Fusion.", replaceWith = @ReplaceWith(expression = "this", imports = []))
public fun <T> StateFlow<T>.distinctUntilChanged(): Flow<T> {
   FlowKt.noImpl();
   throw new KotlinNothingValueException();
}

@Deprecated(level = DeprecationLevel.ERROR, message = "Applying 'flowOn' to SharedFlow has no effect. See the SharedFlow documentation on Operator Fusion.", replaceWith = @ReplaceWith(expression = "this", imports = []))
public fun <T> SharedFlow<T>.flowOn(context: CoroutineContext): Flow<T> {
   FlowKt.noImpl();
   throw new KotlinNothingValueException();
}

@Deprecated(level = DeprecationLevel.WARNING, message = "SharedFlow never completes, so this operator has no effect.", replaceWith = @ReplaceWith(expression = "this", imports = []))
public inline fun <T> SharedFlow<T>.retry(
   retries: Long = java.lang.Long.MAX_VALUE,
   noinline predicate: (Throwable, Continuation<Boolean>) -> Any? = (
         new Function2<java.lang.Throwable, Continuation<? super java.lang.Boolean>, Object>(null) {
            int label;

            {
               super(2, var1);
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               return new <anonymous constructor>(var2);
            }

            public final Object invoke(java.lang.Throwable var1, Continuation<? super java.lang.Boolean> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label == 0) {
                  ResultKt.throwOnFailure(var1);
                  return Boxing.boxBoolean(true);
               } else {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }
            }
         }
      ) as Function2
): Flow<T> {
   return FlowKt.retry(var0, var1, var3);
}

@JvmSynthetic
fun `retry$default`(var0: SharedFlow, var1: Long, var3: Function2, var4: Int, var5: Any): Flow {
   if ((var4 and 1) != 0) {
      var1 = java.lang.Long.MAX_VALUE;
   }

   if ((var4 and 2) != 0) {
      var3 = (new Function2<java.lang.Throwable, Continuation<? super java.lang.Boolean>, Object>(null) {
         int label;

         {
            super(2, var1);
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return (new <anonymous constructor>(var2)) as Continuation<Unit>;
         }

         public final Object invoke(java.lang.Throwable var1, Continuation<? super java.lang.Boolean> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
               ResultKt.throwOnFailure(var1);
               return Boxing.boxBoolean(true);
            } else {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
         }
      }) as Function2;
   }

   return FlowKt.retry(var0, var1, var3);
}

@Deprecated(level = DeprecationLevel.WARNING, message = "SharedFlow never completes, so this operator has no effect.", replaceWith = @ReplaceWith(expression = "this", imports = []))
public inline fun <T> SharedFlow<T>.retryWhen(noinline predicate: (FlowCollector<T>, Throwable, Long, Continuation<Boolean>) -> Any?): Flow<T> {
   return FlowKt.retryWhen(var0, var1);
}

public suspend inline fun <T> SharedFlow<T>.toList(destination: MutableList<T>): Nothing {
   FlowKt.toList(var0, var1, var2);
   throw new IllegalStateException("this code is supposed to be unreachable");
}

@Deprecated(level = DeprecationLevel.WARNING, message = "SharedFlow never completes, so this terminal operation never completes.")
public suspend inline fun <T> SharedFlow<T>.toList(): List<T> {
   return FlowKt.toList$default(var0, null, var1, 1, null);
}

public suspend inline fun <T> SharedFlow<T>.toSet(destination: MutableSet<T>): Nothing {
   FlowKt.toSet(var0, var1, var2);
   throw new IllegalStateException("this code is supposed to be unreachable");
}

@Deprecated(level = DeprecationLevel.WARNING, message = "SharedFlow never completes, so this terminal operation never completes.")
public suspend inline fun <T> SharedFlow<T>.toSet(): Set<T> {
   return FlowKt.toSet$default(var0, null, var1, 1, null);
}
