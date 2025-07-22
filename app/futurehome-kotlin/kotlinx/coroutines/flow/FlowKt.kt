package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass
import kotlin.time.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel

// $VF: Class flags could not be determined
internal class FlowKt {
   @JvmStatic
   public java.lang.String DEFAULT_CONCURRENCY_PROPERTY_NAME = "kotlinx.coroutines.flow.defaultConcurrency";

   @JvmStatic
   fun <T> asFlow(var0: MutableIterable<T>): Flow<T> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun <T> asFlow(var0: MutableIterator<T>): Flow<T> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun <T> asFlow(var0: () -> T): Flow<T> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun <T> asFlow(var0: (Continuation<? super T>?) -> Any): Flow<T> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun asFlow(var0: IntRange): Flow<Integer> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun asFlow(var0: LongRange): Flow<java.lang.Long> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun <T> asFlow(var0: Sequence<? extends T>): Flow<T> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "'BroadcastChannel' is obsolete and all corresponding operators are deprecated in the favour of StateFlow and SharedFlow")
   @JvmStatic
   fun <T> asFlow(var0: BroadcastChannel<T>): Flow<T> {
      return FlowKt__ChannelsKt.asFlow(var0);
   }

   @JvmStatic
   fun asFlow(var0: IntArray): Flow<Integer> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun asFlow(var0: LongArray): Flow<java.lang.Long> {
      return FlowKt__BuildersKt.asFlow(var0);
   }

   @JvmStatic
   fun <T> asFlow(var0: Array<T>): Flow<T> {
      return FlowKt__BuildersKt.asFlow((T[])var0);
   }

   @JvmStatic
   fun <T> asSharedFlow(var0: MutableSharedFlow<T>): SharedFlow<T> {
      return FlowKt__ShareKt.asSharedFlow(var0);
   }

   @JvmStatic
   fun <T> asStateFlow(var0: MutableStateFlow<T>): StateFlow<T> {
      return FlowKt__ShareKt.asStateFlow(var0);
   }

   @JvmStatic
   fun <T> buffer(var0: Flow<? extends T>, var1: Int, var2: BufferOverflow): Flow<T> {
      return FlowKt__ContextKt.buffer(var0, var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'cache()' is 'shareIn' with unlimited replay and 'started = SharingStared.Lazily' argument'", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, Int.MAX_VALUE, started = SharingStared.Lazily)", imports = []))
   @JvmStatic
   fun <T> cache(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.cache(var0);
   }

   @JvmStatic
   fun <T> callbackFlow(var0: (ProducerScope<? super T>?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__BuildersKt.callbackFlow(var0);
   }

   @JvmStatic
   fun <T> cancellable(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__ContextKt.cancellable(var0);
   }

   @JvmStatic
   fun <T> catch(var0: Flow<? extends T>, var1: (FlowCollector<? super T>?, java.lang.Throwable?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__ErrorsKt.catch(var0, var1);
   }

   @JvmStatic
   fun <T> catchImpl(var0: Flow<? extends T>, var1: FlowCollector<? super T>, var2: Continuation<? super java.lang.Throwable>): Any {
      return FlowKt__ErrorsKt.catchImpl(var0, var1, var2);
   }

   @JvmStatic
   fun <T> channelFlow(var0: (ProducerScope<? super T>?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__BuildersKt.channelFlow(var0);
   }

   @JvmStatic
   fun collect(var0: Flow<?>, var1: Continuation<? super Unit>): Any {
      return FlowKt__CollectKt.collect(var0, var1);
   }

   @JvmStatic
   fun <T> collectIndexed(var0: Flow<? extends T>, var1: (Int?, T?, Continuation<? super Unit>?) -> Any, var2: Continuation<? super Unit>): Any {
      return FlowKt__CollectKt.collectIndexed(var0, var1, var2);
   }

   @JvmStatic
   fun <T> collectLatest(var0: Flow<? extends T>, var1: (T?, Continuation<? super Unit>?) -> Any, var2: Continuation<? super Unit>): Any {
      return FlowKt__CollectKt.collectLatest(var0, var1, var2);
   }

   @JvmStatic
   fun <T> collectWhile(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any, var2: Continuation<? super Unit>): Any {
      return FlowKt__LimitKt.collectWhile(var0, var1, var2);
   }

   @JvmStatic
   fun <T1, T2, R> combine(var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: (T1?, T2?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__ZipKt.combine(var0, var1, var2);
   }

   @JvmStatic
   fun <T1, T2, T3, R> combine(
      var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: Flow<? extends T3>, var3: (T1?, T2?, T3?, Continuation<? super R>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combine(var0, var1, var2, var3);
   }

   @JvmStatic
   fun <T1, T2, T3, T4, R> combine(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: Flow<? extends T4>,
      var4: (T1?, T2?, T3?, T4?, Continuation<? super R>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combine(var0, var1, var2, var3, var4);
   }

   @JvmStatic
   fun <T1, T2, T3, T4, T5, R> combine(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: Flow<? extends T4>,
      var4: Flow<? extends T5>,
      var5: (T1?, T2?, T3?, T4?, T5?, Continuation<? super R>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combine(var0, var1, var2, var3, var4, var5);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "this.combine(other, transform)", imports = []))
   @JvmStatic
   fun <T1, T2, R> combineLatest(var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: (T1?, T2?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__MigrationKt.combineLatest(var0, var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, transform)", imports = []))
   @JvmStatic
   fun <T1, T2, T3, R> combineLatest(
      var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: Flow<? extends T3>, var3: (T1?, T2?, T3?, Continuation<? super R>?) -> Any
   ): Flow<R> {
      return FlowKt__MigrationKt.combineLatest(var0, var1, var2, var3);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = []))
   @JvmStatic
   fun <T1, T2, T3, T4, R> combineLatest(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: Flow<? extends T4>,
      var4: (T1?, T2?, T3?, T4?, Continuation<? super R>?) -> Any
   ): Flow<R> {
      return FlowKt__MigrationKt.combineLatest(var0, var1, var2, var3, var4);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = []))
   @JvmStatic
   fun <T1, T2, T3, T4, T5, R> combineLatest(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: Flow<? extends T4>,
      var4: Flow<? extends T5>,
      var5: (T1?, T2?, T3?, T4?, T5?, Continuation<? super R>?) -> Any
   ): Flow<R> {
      return FlowKt__MigrationKt.combineLatest(var0, var1, var2, var3, var4, var5);
   }

   @JvmStatic
   fun <T1, T2, R> combineTransform(
      var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: (FlowCollector<? super R>?, T1?, T2?, Continuation<? super Unit>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combineTransform(var0, var1, var2);
   }

   @JvmStatic
   fun <T1, T2, T3, R> combineTransform(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: (FlowCollector<? super R>?, T1?, T2?, T3?, Continuation<? super Unit>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combineTransform(var0, var1, var2, var3);
   }

   @JvmStatic
   fun <T1, T2, T3, T4, R> combineTransform(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: Flow<? extends T4>,
      var4: (FlowCollector<? super R>?, T1?, T2?, T3?, T4?, Continuation<? super Unit>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combineTransform(var0, var1, var2, var3, var4);
   }

   @JvmStatic
   fun <T1, T2, T3, T4, T5, R> combineTransform(
      var0: Flow<? extends T1>,
      var1: Flow<? extends T2>,
      var2: Flow<? extends T3>,
      var3: Flow<? extends T4>,
      var4: Flow<? extends T5>,
      var5: (FlowCollector<? super R>?, T1?, T2?, T3?, T4?, T5?, Continuation<? super Unit>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.combineTransform(var0, var1, var2, var3, var4, var5);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'compose' is 'let'", replaceWith = @ReplaceWith(expression = "let(transformer)", imports = []))
   @JvmStatic
   fun <T, R> compose(var0: Flow<? extends T>, var1: (Flow<? extends T>?) -> Flow<? extends R>): Flow<R> {
      return FlowKt__MigrationKt.compose(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatMap' is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = []))
   @JvmStatic
   fun <T, R> concatMap(var0: Flow<? extends T>, var1: (T?) -> Flow<? extends R>): Flow<R> {
      return FlowKt__MigrationKt.concatMap(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emit(value) }'", replaceWith = @ReplaceWith(expression = "onCompletion { emit(value) }", imports = []))
   @JvmStatic
   fun <T> concatWith(var0: Flow<? extends T>, var1: T): Flow<T> {
      return FlowKt__MigrationKt.concatWith(var0, (T)var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { if (it == null) emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onCompletion { if (it == null) emitAll(other) }", imports = []))
   @JvmStatic
   fun <T> concatWith(var0: Flow<? extends T>, var1: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.concatWith(var0, var1);
   }

   @JvmStatic
   fun <T> conflate(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__ContextKt.conflate(var0);
   }

   @JvmStatic
   fun <T> consumeAsFlow(var0: ReceiveChannel<? extends T>): Flow<T> {
      return FlowKt__ChannelsKt.consumeAsFlow(var0);
   }

   @JvmStatic
   fun <T> count(var0: Flow<? extends T>, var1: Continuation<? super Integer>): Any {
      return FlowKt__CountKt.count(var0, var1);
   }

   @JvmStatic
   fun <T> count(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any, var2: Continuation<? super Integer>): Any {
      return FlowKt__CountKt.count(var0, var1, var2);
   }

   @JvmStatic
   fun <T> debounce(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__DelayKt.debounce(var0, var1);
   }

   @JvmStatic
   fun <T> debounce(var0: Flow<? extends T>, var1: (T?) -> java.lang.Long): Flow<T> {
      return FlowKt__DelayKt.debounce(var0, var1);
   }

   @JvmStatic
   fun <T> `debounce-HG0u8IE`(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__DelayKt.debounce-HG0u8IE(var0, var1);
   }

   @JvmStatic
   fun <T> debounceDuration(var0: Flow<? extends T>, var1: (T?) -> Duration): Flow<T> {
      return FlowKt__DelayKt.debounceDuration(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'onEach { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onEach { delay(timeMillis) }", imports = []))
   @JvmStatic
   fun <T> delayEach(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__MigrationKt.delayEach(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'onStart { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onStart { delay(timeMillis) }", imports = []))
   @JvmStatic
   fun <T> delayFlow(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__MigrationKt.delayFlow(var0, var1);
   }

   @JvmStatic
   fun <T> distinctUntilChanged(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__DistinctKt.distinctUntilChanged(var0);
   }

   @JvmStatic
   fun <T> distinctUntilChanged(var0: Flow<? extends T>, var1: (T?, T?) -> java.lang.Boolean): Flow<T> {
      return FlowKt__DistinctKt.distinctUntilChanged(var0, var1);
   }

   @JvmStatic
   fun <T, K> distinctUntilChangedBy(var0: Flow<? extends T>, var1: (T?) -> K): Flow<T> {
      return FlowKt__DistinctKt.distinctUntilChangedBy(var0, var1);
   }

   @JvmStatic
   fun <T> drop(var0: Flow<? extends T>, var1: Int): Flow<T> {
      return FlowKt__LimitKt.drop(var0, var1);
   }

   @JvmStatic
   fun <T> dropWhile(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any): Flow<T> {
      return FlowKt__LimitKt.dropWhile(var0, var1);
   }

   @JvmStatic
   fun <T> emitAll(var0: FlowCollector<? super T>, var1: ReceiveChannel<? extends T>, var2: Continuation<? super Unit>): Any {
      return FlowKt__ChannelsKt.emitAll(var0, var1, var2);
   }

   @JvmStatic
   fun <T> emitAll(var0: FlowCollector<? super T>, var1: Flow<? extends T>, var2: Continuation<? super Unit>): Any {
      return FlowKt__CollectKt.emitAll(var0, var1, var2);
   }

   @JvmStatic
   fun <T> emptyFlow(): Flow<T> {
      return FlowKt__BuildersKt.emptyFlow();
   }

   @JvmStatic
   fun ensureActive(var0: FlowCollector<?>) {
      FlowKt__EmittersKt.ensureActive(var0);
   }

   @JvmStatic
   fun <T> filter(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any): Flow<T> {
      return FlowKt__TransformKt.filter(var0, var1);
   }

   @JvmStatic
   fun <R> filterIsInstance(var0: Flow<?>, var1: KClass<R>): Flow<R> {
      return FlowKt__TransformKt.filterIsInstance(var0, var1);
   }

   @JvmStatic
   fun <T> filterNot(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any): Flow<T> {
      return FlowKt__TransformKt.filterNot(var0, var1);
   }

   @JvmStatic
   fun <T> filterNotNull(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__TransformKt.filterNotNull(var0);
   }

   @JvmStatic
   fun <T> first(var0: Flow<? extends T>, var1: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.first(var0, var1);
   }

   @JvmStatic
   fun <T> first(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any, var2: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.first(var0, var1, var2);
   }

   @JvmStatic
   fun <T> firstOrNull(var0: Flow<? extends T>, var1: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.firstOrNull(var0, var1);
   }

   @JvmStatic
   fun <T> firstOrNull(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any, var2: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.firstOrNull(var0, var1, var2);
   }

   @JvmStatic
   fun fixedPeriodTicker(var0: CoroutineScope, var1: Long, var3: Long): ReceiveChannel<Unit> {
      return FlowKt__DelayKt.fixedPeriodTicker(var0, var1, var3);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = []))
   @JvmStatic
   fun <T, R> flatMap(var0: Flow<? extends T>, var1: (T?, Continuation<? super Flow<? extends R>>?) -> Any): Flow<R> {
      return FlowKt__MigrationKt.flatMap(var0, var1);
   }

   @JvmStatic
   fun <T, R> flatMapConcat(var0: Flow<? extends T>, var1: (T?, Continuation<? super Flow<? extends R>>?) -> Any): Flow<R> {
      return FlowKt__MergeKt.flatMapConcat(var0, var1);
   }

   @JvmStatic
   fun <T, R> flatMapLatest(var0: Flow<? extends T>, var1: (T?, Continuation<? super Flow<? extends R>>?) -> Any): Flow<R> {
      return FlowKt__MergeKt.flatMapLatest(var0, var1);
   }

   @JvmStatic
   fun <T, R> flatMapMerge(var0: Flow<? extends T>, var1: Int, var2: (T?, Continuation<? super Flow<? extends R>>?) -> Any): Flow<R> {
      return FlowKt__MergeKt.flatMapMerge(var0, var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'flatten' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = []))
   @JvmStatic
   fun <T> flatten(var0: Flow<? extends Flow<? extends T>>): Flow<T> {
      return FlowKt__MigrationKt.flatten(var0);
   }

   @JvmStatic
   fun <T> flattenConcat(var0: Flow<? extends Flow<? extends T>>): Flow<T> {
      return FlowKt__MergeKt.flattenConcat(var0);
   }

   @JvmStatic
   fun <T> flattenMerge(var0: Flow<? extends Flow<? extends T>>, var1: Int): Flow<T> {
      return FlowKt__MergeKt.flattenMerge(var0, var1);
   }

   @JvmStatic
   fun <T> flow(var0: (FlowCollector<? super T>?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__BuildersKt.flow(var0);
   }

   @JvmStatic
   fun <T1, T2, R> flowCombine(var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: (T1?, T2?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__ZipKt.flowCombine(var0, var1, var2);
   }

   @JvmStatic
   fun <T1, T2, R> flowCombineTransform(
      var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: (FlowCollector<? super R>?, T1?, T2?, Continuation<? super Unit>?) -> Any
   ): Flow<R> {
      return FlowKt__ZipKt.flowCombineTransform(var0, var1, var2);
   }

   @JvmStatic
   fun <T> flowOf(var0: T): Flow<T> {
      return FlowKt__BuildersKt.flowOf((T)var0);
   }

   @JvmStatic
   fun <T> flowOf(vararg var0: T): Flow<T> {
      return FlowKt__BuildersKt.flowOf((T[])var0);
   }

   @JvmStatic
   fun <T> flowOn(var0: Flow<? extends T>, var1: CoroutineContext): Flow<T> {
      return FlowKt__ContextKt.flowOn(var0, var1);
   }

   @JvmStatic
   fun <T, R> fold(var0: Flow<? extends T>, var1: R, var2: (R?, T?, Continuation<? super R>?) -> Any, var3: Continuation<? super R>): Any {
      return FlowKt__ReduceKt.fold(var0, var1, var2, var3);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'forEach' is 'collect'", replaceWith = @ReplaceWith(expression = "collect(action)", imports = []))
   @JvmStatic
   fun <T> forEach(var0: Flow<? extends T>, var1: (T?, Continuation<? super Unit>?) -> Any) {
      FlowKt__MigrationKt.forEach(var0, var1);
   }

   @JvmStatic
   fun getDEFAULT_CONCURRENCY(): Int {
      return FlowKt__MergeKt.getDEFAULT_CONCURRENCY();
   }

   @JvmStatic
   fun <T> last(var0: Flow<? extends T>, var1: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.last(var0, var1);
   }

   @JvmStatic
   fun <T> lastOrNull(var0: Flow<? extends T>, var1: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.lastOrNull(var0, var1);
   }

   @JvmStatic
   fun <T> launchIn(var0: Flow<? extends T>, var1: CoroutineScope): Job {
      return FlowKt__CollectKt.launchIn(var0, var1);
   }

   @JvmStatic
   fun <T, R> map(var0: Flow<? extends T>, var1: (T?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__TransformKt.map(var0, var1);
   }

   @JvmStatic
   fun <T, R> mapLatest(var0: Flow<? extends T>, var1: (T?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__MergeKt.mapLatest(var0, var1);
   }

   @JvmStatic
   fun <T, R> mapNotNull(var0: Flow<? extends T>, var1: (T?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__TransformKt.mapNotNull(var0, var1);
   }

   @JvmStatic
   fun <T> merge(var0: MutableIterable<Flow<? extends T>>): Flow<T> {
      return FlowKt__MergeKt.merge(var0);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'merge' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = []))
   @JvmStatic
   fun <T> merge(var0: Flow<? extends Flow<? extends T>>): Flow<T> {
      return FlowKt__MigrationKt.merge(var0);
   }

   @JvmStatic
   fun <T> merge(vararg var0: Flow<? extends T>): Flow<T> {
      return FlowKt__MergeKt.merge(var0);
   }

   @JvmStatic
   fun noImpl(): Void {
      return FlowKt__MigrationKt.noImpl();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
   @JvmStatic
   fun <T> observeOn(var0: Flow<? extends T>, var1: CoroutineContext): Flow<T> {
      return FlowKt__MigrationKt.observeOn(var0, var1);
   }

   @JvmStatic
   fun <T> onCompletion(var0: Flow<? extends T>, var1: (FlowCollector<? super T>?, java.lang.Throwable?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__EmittersKt.onCompletion(var0, var1);
   }

   @JvmStatic
   fun <T> onEach(var0: Flow<? extends T>, var1: (T?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__TransformKt.onEach(var0, var1);
   }

   @JvmStatic
   fun <T> onEmpty(var0: Flow<? extends T>, var1: (FlowCollector<? super T>?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__EmittersKt.onEmpty(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = []))
   @JvmStatic
   fun <T> onErrorResume(var0: Flow<? extends T>, var1: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.onErrorResume(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = []))
   @JvmStatic
   fun <T> onErrorResumeNext(var0: Flow<? extends T>, var1: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.onErrorResumeNext(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emit(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emit(fallback) }", imports = []))
   @JvmStatic
   fun <T> onErrorReturn(var0: Flow<? extends T>, var1: T): Flow<T> {
      return FlowKt__MigrationKt.onErrorReturn(var0, (T)var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { e -> if (predicate(e)) emit(fallback) else throw e }'", replaceWith = @ReplaceWith(expression = "catch { e -> if (predicate(e)) emit(fallback) else throw e }", imports = []))
   @JvmStatic
   fun <T> onErrorReturn(var0: Flow<? extends T>, var1: T, var2: (java.lang.Throwable?) -> java.lang.Boolean): Flow<T> {
      return FlowKt__MigrationKt.onErrorReturn(var0, (T)var1, var2);
   }

   @JvmStatic
   fun <T> onStart(var0: Flow<? extends T>, var1: (FlowCollector<? super T>?, Continuation<? super Unit>?) -> Any): Flow<T> {
      return FlowKt__EmittersKt.onStart(var0, var1);
   }

   @JvmStatic
   fun <T> onSubscription(var0: SharedFlow<? extends T>, var1: (FlowCollector<? super T>?, Continuation<? super Unit>?) -> Any): SharedFlow<T> {
      return FlowKt__ShareKt.onSubscription(var0, var1);
   }

   @JvmStatic
   fun <T> produceIn(var0: Flow<? extends T>, var1: CoroutineScope): ReceiveChannel<T> {
      return FlowKt__ChannelsKt.produceIn(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'publish()' is 'shareIn'. \npublish().connect() is the default strategy (no extra call is needed), \npublish().autoConnect() translates to 'started = SharingStared.Lazily' argument, \npublish().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, 0)", imports = []))
   @JvmStatic
   fun <T> publish(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.publish(var0);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'publish(bufferSize)' is 'buffer' followed by 'shareIn'. \npublish().connect() is the default strategy (no extra call is needed), \npublish().autoConnect() translates to 'started = SharingStared.Lazily' argument, \npublish().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.buffer(bufferSize).shareIn(scope, 0)", imports = []))
   @JvmStatic
   fun <T> publish(var0: Flow<? extends T>, var1: Int): Flow<T> {
      return FlowKt__MigrationKt.publish(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
   @JvmStatic
   fun <T> publishOn(var0: Flow<? extends T>, var1: CoroutineContext): Flow<T> {
      return FlowKt__MigrationKt.publishOn(var0, var1);
   }

   @JvmStatic
   fun <T> receiveAsFlow(var0: ReceiveChannel<? extends T>): Flow<T> {
      return FlowKt__ChannelsKt.receiveAsFlow(var0);
   }

   @JvmStatic
   fun <S, T extends S> reduce(var0: Flow<? extends T>, var1: (S?, T?, Continuation<? super S>?) -> Any, var2: Continuation<? super S>): Any {
      return FlowKt__ReduceKt.reduce(var0, var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'replay()' is 'shareIn' with unlimited replay. \nreplay().connect() is the default strategy (no extra call is needed), \nreplay().autoConnect() translates to 'started = SharingStared.Lazily' argument, \nreplay().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, Int.MAX_VALUE)", imports = []))
   @JvmStatic
   fun <T> replay(var0: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.replay(var0);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'replay(bufferSize)' is 'shareIn' with the specified replay parameter. \nreplay().connect() is the default strategy (no extra call is needed), \nreplay().autoConnect() translates to 'started = SharingStared.Lazily' argument, \nreplay().refCount() translates to 'started = SharingStared.WhileSubscribed()' argument.", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, bufferSize)", imports = []))
   @JvmStatic
   fun <T> replay(var0: Flow<? extends T>, var1: Int): Flow<T> {
      return FlowKt__MigrationKt.replay(var0, var1);
   }

   @JvmStatic
   fun <T> retry(var0: Flow<? extends T>, var1: Long, var3: (java.lang.Throwable?, Continuation<? super java.lang.Boolean>?) -> Any): Flow<T> {
      return FlowKt__ErrorsKt.retry(var0, var1, var3);
   }

   @JvmStatic
   fun <T> retryWhen(
      var0: Flow<? extends T>, var1: (FlowCollector<? super T>?, java.lang.Throwable?, java.lang.Long?, Continuation<? super java.lang.Boolean>?) -> Any
   ): Flow<T> {
      return FlowKt__ErrorsKt.retryWhen(var0, var1);
   }

   @JvmStatic
   fun <T, R> runningFold(var0: Flow<? extends T>, var1: R, var2: (R?, T?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__TransformKt.runningFold(var0, (R)var1, var2);
   }

   @JvmStatic
   fun <T> runningReduce(var0: Flow<? extends T>, var1: (T?, T?, Continuation<? super T>?) -> Any): Flow<T> {
      return FlowKt__TransformKt.runningReduce(var0, var1);
   }

   @JvmStatic
   fun <T> sample(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__DelayKt.sample(var0, var1);
   }

   @JvmStatic
   fun <T> `sample-HG0u8IE`(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__DelayKt.sample-HG0u8IE(var0, var1);
   }

   @JvmStatic
   fun <T, R> scan(var0: Flow<? extends T>, var1: R, var2: (R?, T?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__TransformKt.scan(var0, (R)var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow has less verbose 'scan' shortcut", replaceWith = @ReplaceWith(expression = "scan(initial, operation)", imports = []))
   @JvmStatic
   fun <T, R> scanFold(var0: Flow<? extends T>, var1: R, var2: (R?, T?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__MigrationKt.scanFold(var0, (R)var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "'scanReduce' was renamed to 'runningReduce' to be consistent with Kotlin standard library", replaceWith = @ReplaceWith(expression = "runningReduce(operation)", imports = []))
   @JvmStatic
   fun <T> scanReduce(var0: Flow<? extends T>, var1: (T?, T?, Continuation<? super T>?) -> Any): Flow<T> {
      return FlowKt__MigrationKt.scanReduce(var0, var1);
   }

   @JvmStatic
   fun <T> shareIn(var0: Flow<? extends T>, var1: CoroutineScope, var2: SharingStarted, var3: Int): SharedFlow<T> {
      return FlowKt__ShareKt.shareIn(var0, var1, var2, var3);
   }

   @JvmStatic
   fun <T> single(var0: Flow<? extends T>, var1: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.single(var0, var1);
   }

   @JvmStatic
   fun <T> singleOrNull(var0: Flow<? extends T>, var1: Continuation<? super T>): Any {
      return FlowKt__ReduceKt.singleOrNull(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'skip' is 'drop'", replaceWith = @ReplaceWith(expression = "drop(count)", imports = []))
   @JvmStatic
   fun <T> skip(var0: Flow<? extends T>, var1: Int): Flow<T> {
      return FlowKt__MigrationKt.skip(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emit(value) }'", replaceWith = @ReplaceWith(expression = "onStart { emit(value) }", imports = []))
   @JvmStatic
   fun <T> startWith(var0: Flow<? extends T>, var1: T): Flow<T> {
      return FlowKt__MigrationKt.startWith(var0, (T)var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onStart { emitAll(other) }", imports = []))
   @JvmStatic
   fun <T> startWith(var0: Flow<? extends T>, var1: Flow<? extends T>): Flow<T> {
      return FlowKt__MigrationKt.startWith(var0, var1);
   }

   @JvmStatic
   fun <T> stateIn(var0: Flow<? extends T>, var1: CoroutineScope, var2: Continuation<? super StateFlow<? extends T>>): Any {
      return FlowKt__ShareKt.stateIn(var0, var1, var2);
   }

   @JvmStatic
   fun <T> stateIn(var0: Flow<? extends T>, var1: CoroutineScope, var2: SharingStarted, var3: T): StateFlow<T> {
      return FlowKt__ShareKt.stateIn(var0, var1, var2, (T)var3);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
   @JvmStatic
   fun <T> subscribe(var0: Flow<? extends T>) {
      FlowKt__MigrationKt.subscribe(var0);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
   @JvmStatic
   fun <T> subscribe(var0: Flow<? extends T>, var1: (T?, Continuation<? super Unit>?) -> Any) {
      FlowKt__MigrationKt.subscribe(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'launchIn' with 'onEach', 'onCompletion' and 'catch' instead")
   @JvmStatic
   fun <T> subscribe(var0: Flow<? extends T>, var1: (T?, Continuation<? super Unit>?) -> Any, var2: (java.lang.Throwable?, Continuation<? super Unit>?) -> Any) {
      FlowKt__MigrationKt.subscribe(var0, var1, var2);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'flowOn' instead")
   @JvmStatic
   fun <T> subscribeOn(var0: Flow<? extends T>, var1: CoroutineContext): Flow<T> {
      return FlowKt__MigrationKt.subscribeOn(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogues of 'switchMap' are 'transformLatest', 'flatMapLatest' and 'mapLatest'", replaceWith = @ReplaceWith(expression = "this.flatMapLatest(transform)", imports = []))
   @JvmStatic
   fun <T, R> switchMap(var0: Flow<? extends T>, var1: (T?, Continuation<? super Flow<? extends R>>?) -> Any): Flow<R> {
      return FlowKt__MigrationKt.switchMap(var0, var1);
   }

   @JvmStatic
   fun <T> take(var0: Flow<? extends T>, var1: Int): Flow<T> {
      return FlowKt__LimitKt.take(var0, var1);
   }

   @JvmStatic
   fun <T> takeWhile(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any): Flow<T> {
      return FlowKt__LimitKt.takeWhile(var0, var1);
   }

   @JvmStatic
   fun <T> `timeout-HG0u8IE`(var0: Flow<? extends T>, var1: Long): Flow<T> {
      return FlowKt__DelayKt.timeout-HG0u8IE(var0, var1);
   }

   @JvmStatic
   fun <T, C extends java.util.Collection<? super T>> toCollection(var0: Flow<? extends T>, var1: C, var2: Continuation<? super C>): Any {
      return FlowKt__CollectionKt.toCollection(var0, (C)var1, var2);
   }

   @JvmStatic
   fun <T> toList(var0: Flow<? extends T>, var1: MutableList<T>, var2: Continuation<? super java.utilList<? extends T>>): Any {
      return FlowKt__CollectionKt.toList(var0, var1, var2);
   }

   @JvmStatic
   fun <T> toSet(var0: Flow<? extends T>, var1: MutableSet<T>, var2: Continuation<? super java.utilSet<? extends T>>): Any {
      return FlowKt__CollectionKt.toSet(var0, var1, var2);
   }

   @JvmStatic
   fun <T, R> transform(var0: Flow<? extends T>, var1: (FlowCollector<? super R>?, T?, Continuation<? super Unit>?) -> Any): Flow<R> {
      return FlowKt__EmittersKt.transform(var0, var1);
   }

   @JvmStatic
   fun <T, R> transformLatest(var0: Flow<? extends T>, var1: (FlowCollector<? super R>?, T?, Continuation<? super Unit>?) -> Any): Flow<R> {
      return FlowKt__MergeKt.transformLatest(var0, var1);
   }

   @JvmStatic
   fun <T, R> transformWhile(var0: Flow<? extends T>, var1: (FlowCollector<? super R>?, T?, Continuation<? super java.lang.Boolean>?) -> Any): Flow<R> {
      return FlowKt__LimitKt.transformWhile(var0, var1);
   }

   @JvmStatic
   fun <T, R> unsafeTransform(var0: Flow<? extends T>, var1: (FlowCollector<? super R>?, T?, Continuation<? super Unit>?) -> Any): Flow<R> {
      return FlowKt__EmittersKt.unsafeTransform(var0, var1);
   }

   @JvmStatic
   fun <T> withIndex(var0: Flow<? extends T>): Flow<IndexedValue<T>> {
      return FlowKt__TransformKt.withIndex(var0);
   }

   @JvmStatic
   fun <T1, T2, R> zip(var0: Flow<? extends T1>, var1: Flow<? extends T2>, var2: (T1?, T2?, Continuation<? super R>?) -> Any): Flow<R> {
      return FlowKt__ZipKt.zip(var0, var1, var2);
   }
}
