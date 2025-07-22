package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.DelayKt

@JvmSynthetic
internal class FlowKt__MigrationKt {
   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'cache()' is 'shareIn' with unlimited replay and 'started = SharingStared.Lazily' argument'", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, Int.MAX_VALUE, started = SharingStared.Lazily)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.cache(): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "this.combine(other, transform)", imports = []))
   @JvmStatic
   public fun <T1, T2, R> Flow<T1>.combineLatest(other: Flow<T2>, transform: (T1, T2, Continuation<R>) -> Any?): Flow<R> {
      return FlowKt.combine(var0, var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, transform)", imports = []))
   @JvmStatic
   public fun <T1, T2, T3, R> Flow<T1>.combineLatest(other: Flow<T2>, other2: Flow<T3>, transform: (T1, T2, T3, Continuation<R>) -> Any?): Flow<R> {
      return FlowKt.combine(var0, var1, var2, var3);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = []))
   @JvmStatic
   public fun <T1, T2, T3, T4, R> Flow<T1>.combineLatest(
      other: Flow<T2>,
      other2: Flow<T3>,
      other3: Flow<T4>,
      transform: (T1, T2, T3, T4, Continuation<R>) -> Any?
   ): Flow<R> {
      return FlowKt.combine(var0, var1, var2, var3, var4);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = []))
   @JvmStatic
   public fun <T1, T2, T3, T4, T5, R> Flow<T1>.combineLatest(
      other: Flow<T2>,
      other2: Flow<T3>,
      other3: Flow<T4>,
      other4: Flow<T5>,
      transform: (T1, T2, T3, T4, T5, Continuation<R>) -> Any?
   ): Flow<R> {
      return FlowKt.combine(var0, var1, var2, var3, var4, var5);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'compose' is 'let'", replaceWith = @ReplaceWith(expression = "let(transformer)", imports = []))
   @JvmStatic
   public fun <T, R> Flow<T>.compose(transformer: (Flow<T>) -> Flow<R>): Flow<R> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatMap' is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = []))
   @JvmStatic
   public fun <T, R> Flow<T>.concatMap(mapper: (T) -> Flow<R>): Flow<R> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emit(value) }'", replaceWith = @ReplaceWith(expression = "onCompletion { emit(value) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.concatWith(value: T): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { if (it == null) emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onCompletion { if (it == null) emitAll(other) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.concatWith(other: Flow<T>): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'onEach { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onEach { delay(timeMillis) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.delayEach(timeMillis: Long): Flow<T> {
      return FlowKt.onEach(var0, (new Function2<T, Continuation<? super Unit>, Object>(var1, null) {
         final long $timeMillis;
         int label;

         {
            super(2, var3);
            this.$timeMillis = var1;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$timeMillis, var2);
         }

         public final Object invoke(T var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var3: Long = this.$timeMillis;
               var1 = this;
               this.label = 1;
               if (DelayKt.delay(var3, var1) === var5) {
                  return var5;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'onStart { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onStart { delay(timeMillis) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.delayFlow(timeMillis: Long): Flow<T> {
      return FlowKt.onStart(var0, (new Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object>(var1, null) {
         final long $timeMillis;
         int label;

         {
            super(2, var3);
            this.$timeMillis = var1;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$timeMillis, var2);
         }

         public final Object invoke(FlowCollector<? super T> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var3: Long = this.$timeMillis;
               var1 = this;
               this.label = 1;
               if (DelayKt.delay(var3, var1) === var5) {
                  return var5;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = []))
   @JvmStatic
   public fun <T, R> Flow<T>.flatMap(mapper: (T, Continuation<Flow<R>>) -> Any?): Flow<R> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'flatten' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = []))
   @JvmStatic
   public fun <T> Flow<Flow<T>>.flatten(): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'forEach' is 'collect'", replaceWith = @ReplaceWith(expression = "collect(action)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.forEach(action: (T, Continuation<Unit>) -> Any?) {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'merge' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = []))
   @JvmStatic
   public fun <T> Flow<Flow<T>>.merge(): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @JvmStatic
   internal fun noImpl(): Nothing {
      throw new UnsupportedOperationException("Not implemented, should not be called");
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
   @JvmStatic
   public fun <T> Flow<T>.observeOn(context: CoroutineContext): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.onErrorResume(fallback: Flow<T>): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.onErrorResumeNext(fallback: Flow<T>): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emit(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emit(fallback) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.onErrorReturn(fallback: T): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { e -> if (predicate(e)) emit(fallback) else throw e }'", replaceWith = @ReplaceWith(expression = "catch { e -> if (predicate(e)) emit(fallback) else throw e }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.onErrorReturn(fallback: T, predicate: (Throwable) -> Boolean = <unrepresentable>.INSTANCE as Function1): Flow<T> {
      return FlowKt.catch(var0, (new Function3<FlowCollector<? super T>, java.lang.Throwable, Continuation<? super Unit>, Object>(var2, var1, null) {
         final T $fallback;
         final Function1<java.lang.Throwable, java.lang.Boolean> $predicate;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(3, var3x);
            this.$predicate = var1;
            this.$fallback = (T)var2x;
         }

         public final Object invoke(FlowCollector<? super T> var1, java.lang.Throwable var2, Continuation<? super Unit> var3) {
            val var4: Function3 = new <anonymous constructor>(this.$predicate, this.$fallback, var3);
            var4.L$0 = var1;
            var4.L$1 = var2;
            return var4.invokeSuspend(Unit.INSTANCE);
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
               var1 = this.L$0 as FlowCollector;
               var var4x: java.lang.Throwable = this.L$1 as java.lang.Throwable;
               if (!this.$predicate.invoke(this.L$1 as java.lang.Throwable)) {
                  throw var4x;
               }

               var4x = this.$fallback;
               val var5: Continuation = this;
               this.L$0 = null;
               this.label = 1;
               if (var1.emit(var4x, var5) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function3);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'publish()' is 'shareIn'. \npublish().connect() is the default strategy (no extra call is needed), \npublish().autoConnect() translates to 'started = SharingStared.Lazily' argument, \npublish().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, 0)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.publish(): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'publish(bufferSize)' is 'buffer' followed by 'shareIn'. \npublish().connect() is the default strategy (no extra call is needed), \npublish().autoConnect() translates to 'started = SharingStared.Lazily' argument, \npublish().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.buffer(bufferSize).shareIn(scope, 0)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.publish(bufferSize: Int): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
   @JvmStatic
   public fun <T> Flow<T>.publishOn(context: CoroutineContext): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'replay()' is 'shareIn' with unlimited replay. \nreplay().connect() is the default strategy (no extra call is needed), \nreplay().autoConnect() translates to 'started = SharingStared.Lazily' argument, \nreplay().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, Int.MAX_VALUE)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.replay(): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'replay(bufferSize)' is 'shareIn' with the specified replay parameter. \nreplay().connect() is the default strategy (no extra call is needed), \nreplay().autoConnect() translates to 'started = SharingStared.Lazily' argument, \nreplay().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, bufferSize)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.replay(bufferSize: Int): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow has less verbose 'scan' shortcut", replaceWith = @ReplaceWith(expression = "scan(initial, operation)", imports = []))
   @JvmStatic
   public fun <T, R> Flow<T>.scanFold(initial: R, operation: (R, T, Continuation<R>) -> Any?): Flow<R> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "'scanReduce' was renamed to 'runningReduce' to be consistent with Kotlin standard library", replaceWith = @ReplaceWith(expression = "runningReduce(operation)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.scanReduce(operation: (T, T, Continuation<T>) -> Any?): Flow<T> {
      return FlowKt.runningReduce(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'skip' is 'drop'", replaceWith = @ReplaceWith(expression = "drop(count)", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.skip(count: Int): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emit(value) }'", replaceWith = @ReplaceWith(expression = "onStart { emit(value) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.startWith(value: T): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onStart { emitAll(other) }", imports = []))
   @JvmStatic
   public fun <T> Flow<T>.startWith(other: Flow<T>): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
   @JvmStatic
   public fun <T> Flow<T>.subscribe() {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
   @JvmStatic
   public fun <T> Flow<T>.subscribe(onEach: (T, Continuation<Unit>) -> Any?) {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
   @JvmStatic
   public fun <T> Flow<T>.subscribe(onEach: (T, Continuation<Unit>) -> Any?, onError: (Throwable, Continuation<Unit>) -> Any?) {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'flowOn' instead")
   @JvmStatic
   public fun <T> Flow<T>.subscribeOn(context: CoroutineContext): Flow<T> {
      FlowKt.noImpl();
      throw new KotlinNothingValueException();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogues of 'switchMap' are 'transformLatest', 'flatMapLatest' and 'mapLatest'", replaceWith = @ReplaceWith(expression = "this.flatMapLatest(transform)", imports = []))
   @JvmStatic
   public fun <T, R> Flow<T>.switchMap(transform: (T, Continuation<Flow<R>>) -> Any?): Flow<R> {
      return FlowKt.transformLatest(var0, (new Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object>(var1, null) {
         final Function2 $transform;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(3, var2x);
            this.$transform = var1;
         }

         public final Object invoke(FlowCollector<? super R> var1, T var2, Continuation<? super Unit> var3) {
            val var4: Function3 = new <anonymous constructor>(this.$transform, var3);
            var4.L$0 = var1;
            var4.L$1 = var2;
            return var4.invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var3x: FlowCollector;
            if (this.label != 0) {
               if (this.label != 1) {
                  if (this.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var1);
                  return Unit.INSTANCE;
               }

               var3x = this.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               var3x = this.L$0 as FlowCollector;
               var1 = this.L$1;
               val var4x: Function2 = this.$transform;
               this.L$0 = var3x;
               this.label = 1;
               val var8: Any = var4x.invoke(var1, this);
               var1 = (Continuation)var8;
               if (var8 === var5) {
                  return var5;
               }
            }

            val var9: Flow = var1 as Flow;
            var1 = this;
            this.L$0 = null;
            this.label = 2;
            return if (FlowKt.emitAll(var3x, var9, var1) === var5) var5 else Unit.INSTANCE;
         }
      }) as Function3);
   }
}
