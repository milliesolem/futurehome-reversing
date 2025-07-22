package io.reactivex.rxkotlin

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.util.ArrayList
import java.util.Arrays
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Intrinsics
import org.reactivestreams.Publisher

@BackpressureSupport(BackpressureKind.PASS_THROUGH)
@CheckReturnValue
@SchedulerSupport("none")
@JvmSynthetic
public inline fun <reified R : Any> Flowable<*>.cast(): Flowable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$cast");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.cast(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "cast(R::class.java)");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any, R : Any> Flowable<T>.combineLatest(flowable: Flowable<R>): Flowable<Pair<T, R>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$combineLatest");
   Intrinsics.checkParameterIsNotNull(var1, "flowable");
   val var2: Publisher = var0;
   val var3: Publisher = var1;
   val var6: Function2 = <unrepresentable>.INSTANCE;
   var var4: Flowable = <unrepresentable>.INSTANCE;
   if (var6 != null) {
      var4 = new FlowableKt$sam$io_reactivex_functions_BiFunction$0(var6);
   }

   var4 = Flowable.combineLatest(var2, var3, var4 as BiFunction);
   Intrinsics.checkExpressionValueIsNotNull(var4, "Flowable.combineLatest(t…able, BiFunction(::Pair))");
   return var4;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any, R : Any, U : Any> Flowable<T>.combineLatest(flowable1: Flowable<R>, flowable2: Flowable<U>): Flowable<Triple<T, R, U>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$combineLatest");
   Intrinsics.checkParameterIsNotNull(var1, "flowable1");
   Intrinsics.checkParameterIsNotNull(var2, "flowable2");
   val var3: Publisher = var0;
   val var4: Publisher = var1;
   val var8: Publisher = var2;
   val var7: Function3 = <unrepresentable>.INSTANCE;
   var var5: Flowable = <unrepresentable>.INSTANCE;
   if (var7 != null) {
      var5 = new FlowableKt$sam$io_reactivex_functions_Function3$0(var7);
   }

   var5 = Flowable.combineLatest(var3, var4, var8, var5 as io.reactivex.functions.Function3);
   Intrinsics.checkExpressionValueIsNotNull(var5, "Flowable.combineLatest(t…le2, Function3(::Triple))");
   return var5;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, R : Any> Iterable<Flowable<T>>.combineLatest(crossinline combineFunction: (List<T>) -> R): Flowable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$combineLatest");
   Intrinsics.checkParameterIsNotNull(var1, "combineFunction");
   val var2: Flowable = Flowable.combineLatest(var0, new Function<Object[], R>(var1) {
      final Function1 $combineFunction;

      {
         this.$combineFunction = var1;
      }

      public final R apply(Object[] var1) {
         Intrinsics.checkParameterIsNotNull(var1, "it");
         val var2: Function1 = this.$combineFunction;
         val var3: java.lang.Iterable = ArraysKt.asList(var1);
         val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));

         for (var3 : var3) {
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type T");
            }

            var5.add(var3);
         }

         return (R)var2.invoke(var5 as java.util.List);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var2, "Flowable.combineLatest(t…List().map { it as T }) }");
   return var2;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<Flowable<T>>.concatAll(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   var0 = var0.concatMap(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "concatMap { it }");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<Publisher<T>>.concatAll(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   val var1: Flowable = Flowable.concat(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.concat(this)");
   return var1;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, R : Any> Flowable<T>.flatMapSequence(crossinline body: (T) -> Sequence<R>): Flowable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$flatMapSequence");
   Intrinsics.checkParameterIsNotNull(var1, "body");
   var0 = var0.flatMap(new Function<T, Publisher<? extends R>>(var1) {
      final Function1 $body;

      {
         this.$body = var1;
      }

      public final Flowable<R> apply(T var1) {
         Intrinsics.checkParameterIsNotNull(var1, "it");
         return FlowableKt.toFlowable(this.$body.invoke(var1) as Sequence<? extends T>);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMap { body(it).toFlowable() }");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<Flowable<out T>>.merge(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$merge");
   val var1: Flowable = Flowable.merge(toFlowable(var0));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.merge(this.toFlowable())");
   return var1;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<Flowable<T>>.mergeAll(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAll");
   var0 = var0.flatMap(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMap { it }");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<Flowable<out T>>.mergeDelayError(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeDelayError");
   val var1: Flowable = Flowable.mergeDelayError(toFlowable(var0));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.mergeDelayError(this.toFlowable())");
   return var1;
}

@BackpressureSupport(BackpressureKind.PASS_THROUGH)
@CheckReturnValue
@SchedulerSupport("none")
@JvmSynthetic
public inline fun <reified R : Any> Flowable<*>.ofType(): Flowable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$ofType");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.ofType(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "ofType(R::class.java)");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<Flowable<T>>.switchLatest(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$switchLatest");
   var0 = var0.switchMap(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "switchMap { it }");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<Flowable<T>>.switchOnNext(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$switchOnNext");
   var0 = Flowable.switchOnNext(var0);
   Intrinsics.checkExpressionValueIsNotNull(var0, "Flowable.switchOnNext(this)");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<T>.toFlowable(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   val var1: Flowable = Flowable.fromIterable(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.fromIterable(this)");
   return var1;
}

public fun <T : Any> Iterator<T>.toFlowable(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(toIterable(var0));
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun IntProgression.toFlowable(): Flowable<Int> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   val var1: Flowable;
   if (var0.getStep() == 1 && (long)var0.getLast() - var0.getFirst() < Integer.MAX_VALUE) {
      var1 = Flowable.range(var0.getFirst(), Math.max(0, var0.getLast() - var0.getFirst() + 1));
      Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.range(first, Ma…max(0, last - first + 1))");
   } else {
      var1 = Flowable.fromIterable(var0);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.fromIterable(this)");
   }

   return var1;
}

public fun <T : Any> Sequence<T>.toFlowable(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(SequencesKt.asIterable(var0));
}

@CheckReturnValue
public fun ByteArray.toFlowable(): Flowable<Byte> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
public fun CharArray.toFlowable(): Flowable<Char> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
public fun DoubleArray.toFlowable(): Flowable<Double> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
public fun FloatArray.toFlowable(): Flowable<Float> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
public fun IntArray.toFlowable(): Flowable<Int> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
public fun LongArray.toFlowable(): Flowable<Long> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Array<T>.toFlowable(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   val var1: Flowable = Flowable.fromArray(Arrays.copyOf(var0, var0.length));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Flowable.fromArray(*this)");
   return var1;
}

@CheckReturnValue
public fun ShortArray.toFlowable(): Flowable<Short> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
public fun BooleanArray.toFlowable(): Flowable<Boolean> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toFlowable");
   return toFlowable(ArraysKt.asIterable(var0));
}

private fun <T : Any> Iterator<T>.toIterable(): Iterable<T> {
   return new java.lang.Iterable<T>(var0) {
      final java.util.Iterator $this_toIterable;

      {
         this.$this_toIterable = var1;
      }

      @Override
      public java.util.Iterator<T> iterator() {
         return this.$this_toIterable;
      }
   };
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@CheckReturnValue
@SchedulerSupport("none")
public fun <A : Any, B : Any> Flowable<Pair<A, B>>.toMap(): Single<MutableMap<A, B>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
   val var1: Single = var0.toMap(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var1, "toMap({ it.first }, { it.second })");
   return var1;
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@CheckReturnValue
@SchedulerSupport("none")
public fun <A : Any, B : Any> Flowable<Pair<A, B>>.toMultimap(): Single<MutableMap<A, MutableCollection<B>>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toMultimap");
   val var1: Single = var0.toMultimap(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var1, "toMultimap({ it.first }, { it.second })");
   return var1;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, R : Any> Iterable<Flowable<T>>.zip(crossinline zipFunction: (List<T>) -> R): Flowable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$zip");
   Intrinsics.checkParameterIsNotNull(var1, "zipFunction");
   val var2: Flowable = Flowable.zip(var0, new Function<Object[], R>(var1) {
      final Function1 $zipFunction;

      {
         this.$zipFunction = var1;
      }

      public final R apply(Object[] var1) {
         Intrinsics.checkParameterIsNotNull(var1, "it");
         val var2: Function1 = this.$zipFunction;
         val var3: java.lang.Iterable = ArraysKt.asList(var1);
         val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));

         for (Object var4 : var3) {
            if (var4 == null) {
               throw new TypeCastException("null cannot be cast to non-null type T");
            }

            var5.add(var4);
         }

         return (R)var2.invoke(var5 as java.util.List);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var2, "Flowable.zip(this) { zip…List().map { it as T }) }");
   return var2;
}
