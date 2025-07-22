package io.reactivex.rxkotlin

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import kotlin.jvm.internal.Intrinsics

@JvmSynthetic
public inline fun <reified R : Any> Single<*>.cast(): Single<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$cast");
   Intrinsics.reifiedOperationMarker(4, "R");
   var0 = var0.cast(Object.class);
   Intrinsics.checkExpressionValueIsNotNull(var0, "cast(R::class.java)");
   return var0;
}

@BackpressureSupport(BackpressureKind.FULL)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Iterable<SingleSource<T>>.concatAll(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   val var1: Flowable = Single.concat(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Single.concat(this)");
   return var1;
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<Single<T>>.mergeAllSingles(): Flowable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAllSingles");
   var0 = var0.flatMapSingle(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMapSingle { it }");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<Single<T>>.mergeAllSingles(): Observable<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAllSingles");
   var0 = var0.flatMapSingle(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "flatMapSingle { it }");
   return var0;
}
