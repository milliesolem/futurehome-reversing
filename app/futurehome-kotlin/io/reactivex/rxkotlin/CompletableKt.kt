package io.reactivex.rxkotlin

import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.Action
import java.util.concurrent.Callable
import java.util.concurrent.Future
import kotlin.jvm.internal.Intrinsics

@CheckReturnValue
@SchedulerSupport("none")
public fun Iterable<CompletableSource>.concatAll(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concatAll");
   val var1: Completable = Completable.concat(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Completable.concat(this)");
   return var1;
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@CheckReturnValue
@SchedulerSupport("none")
public fun Flowable<Completable>.mergeAllCompletables(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAllCompletables");
   val var1: Completable = var0.flatMapCompletable(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var1, "flatMapCompletable { it }");
   return var1;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun Observable<Completable>.mergeAllCompletables(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$mergeAllCompletables");
   val var1: Completable = var0.flatMapCompletable(<unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var1, "flatMapCompletable { it }");
   return var1;
}

public fun Action.toCompletable(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toCompletable");
   val var1: Completable = Completable.fromAction(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Completable.fromAction(this)");
   return var1;
}

public fun Callable<out Any>.toCompletable(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toCompletable");
   val var1: Completable = Completable.fromCallable(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Completable.fromCallable(this)");
   return var1;
}

public fun Future<out Any>.toCompletable(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toCompletable");
   val var1: Completable = Completable.fromFuture(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Completable.fromFuture(this)");
   return var1;
}

public fun (() -> Any).toCompletable(): Completable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toCompletable");
   val var1: Completable = Completable.fromCallable(new CompletableKt$sam$java_util_concurrent_Callable$0(var0));
   Intrinsics.checkExpressionValueIsNotNull(var1, "Completable.fromCallable(this)");
   return var1;
}
