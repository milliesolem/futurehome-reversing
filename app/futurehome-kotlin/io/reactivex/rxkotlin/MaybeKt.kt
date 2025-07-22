package io.reactivex.rxkotlin

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.Observable
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import kotlin.jvm.internal.Intrinsics

@CheckReturnValue
@SchedulerSupport("none")
@JvmSynthetic
public inline fun <reified R : Any> Maybe<*>.cast(): Maybe<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$cast");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.cast(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "cast(R::class.java)");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<MaybeSource<T>>.concatAll(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   val var1: Flowable = Maybe.concat(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Maybe.concat(this)");
   return var1;
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<Maybe<T>>.mergeAllMaybes(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAllMaybes");
   var0 = var0.flatMapMaybe(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMapMaybe { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<Maybe<T>>.mergeAllMaybes(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAllMaybes");
   var0 = var0.flatMapMaybe(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMapMaybe { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
@JvmSynthetic
public inline fun <reified R : Any> Maybe<*>.ofType(): Maybe<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$ofType");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.ofType(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "ofType(R::class.java)");
   return var0;
}
