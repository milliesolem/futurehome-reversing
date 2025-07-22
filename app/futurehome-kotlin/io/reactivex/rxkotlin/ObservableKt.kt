package io.reactivex.rxkotlin

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.Function
import java.util.ArrayList
import java.util.Arrays
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics

@CheckReturnValue
@SchedulerSupport("none")
@JvmSynthetic
public inline fun <reified R : Any> Observable<*>.cast(): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$cast");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.cast(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "cast(R::class.java)");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, R : Any> Iterable<Observable<T>>.combineLatest(crossinline combineFunction: (List<T>) -> R): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$combineLatest");
   Intrinsics.checkParameterIsNotNull(var1, "combineFunction");
   val var2: Observable = Observable.combineLatest(var0, new Function<Object[], R>(var1) {
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
   Intrinsics.checkExpressionValueIsNotNull(var2, "Observable.combineLatest…List().map { it as T }) }");
   return var2;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<Observable<T>>.concatAll(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   var0 = var0.concatMap(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "concatMap { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<ObservableSource<T>>.concatAll(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   val var1: Observable = Observable.concat(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.concat(this)");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<out Iterable<T>>.concatMapIterable(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatMapIterable");
   var0 = var0.concatMapIterable(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "concatMapIterable { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<out Iterable<T>>.flatMapIterable(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$flatMapIterable");
   var0 = var0.flatMapIterable(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMapIterable { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, R : Any> Observable<T>.flatMapSequence(crossinline body: (T) -> Sequence<R>): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$flatMapSequence");
   Intrinsics.checkParameterIsNotNull(var1, "body");
   var0 = var0.flatMap(new Function<T, ObservableSource<? extends R>>(var1) {
      final Function1 $body;

      {
         this.$body = var1;
      }

      public final Observable<R> apply(T var1) {
         Intrinsics.checkParameterIsNotNull(var1, "it");
         return ObservableKt.toObservable(this.$body.invoke(var1) as Sequence<? extends T>);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMap { body(it).toObservable() }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<Observable<out T>>.merge(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$merge");
   val var1: Observable = Observable.merge(toObservable(var0));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.merge(this.toObservable())");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<Observable<T>>.mergeAll(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAll");
   var0 = var0.flatMap(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMap { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<Observable<out T>>.mergeDelayError(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeDelayError");
   val var1: Observable = Observable.mergeDelayError(toObservable(var0));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.mergeDelayError(this.toObservable())");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
@JvmSynthetic
public inline fun <reified R : Any> Observable<*>.ofType(): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$ofType");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.ofType(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "ofType(R::class.java)");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<Observable<T>>.switchLatest(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$switchLatest");
   var0 = var0.switchMap(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "switchMap { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<Observable<T>>.switchOnNext(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$switchOnNext");
   var0 = Observable.switchOnNext(var0);
   Intrinsics.checkExpressionValueIsNotNull(var0, "Observable.switchOnNext(this)");
   return var0;
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

@CheckReturnValue
@SchedulerSupport("none")
public fun <A : Any, B : Any> Observable<Pair<A, B>>.toMap(): Single<MutableMap<A, B>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toMap");
   val var1: Single = var0.toMap(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var1, "toMap({ it.first }, { it.second })");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <A : Any, B : Any> Observable<Pair<A, B>>.toMultimap(): Single<MutableMap<A, MutableCollection<B>>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toMultimap");
   val var1: Single = var0.toMultimap(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var1, "toMultimap({ it.first }, { it.second })");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<T>.toObservable(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   val var1: Observable = Observable.fromIterable(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.fromIterable(this)");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterator<T>.toObservable(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(toIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun IntProgression.toObservable(): Observable<Int> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   val var1: Observable;
   if (var0.getStep() == 1 && (long)var0.getLast() - var0.getFirst() < Integer.MAX_VALUE) {
      var1 = Observable.range(var0.getFirst(), Math.max(0, var0.getLast() - var0.getFirst() + 1));
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.range(first, …max(0, last - first + 1))");
   } else {
      var1 = Observable.fromIterable(var0);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.fromIterable(this)");
   }

   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Sequence<T>.toObservable(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(SequencesKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun ByteArray.toObservable(): Observable<Byte> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun CharArray.toObservable(): Observable<Char> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun DoubleArray.toObservable(): Observable<Double> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun FloatArray.toObservable(): Observable<Float> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun IntArray.toObservable(): Observable<Int> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun LongArray.toObservable(): Observable<Long> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Array<T>.toObservable(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   val var1: Observable = Observable.fromArray(Arrays.copyOf(var0, var0.length));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.fromArray(*this)");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun ShortArray.toObservable(): Observable<Short> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public fun BooleanArray.toObservable(): Observable<Boolean> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toObservable");
   return toObservable(ArraysKt.asIterable(var0));
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, R : Any> Iterable<Observable<T>>.zip(crossinline zipFunction: (List<T>) -> R): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$zip");
   Intrinsics.checkParameterIsNotNull(var1, "zipFunction");
   val var2: Observable = Observable.zip(var0, new Function<Object[], R>(var1) {
      final Function1 $zipFunction;

      {
         this.$zipFunction = var1;
      }

      public final R apply(Object[] var1) {
         Intrinsics.checkParameterIsNotNull(var1, "it");
         val var2: Function1 = this.$zipFunction;
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
   Intrinsics.checkExpressionValueIsNotNull(var2, "Observable.zip(this) { z…List().map { it as T }) }");
   return var2;
}
