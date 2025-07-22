package io.reactivex.rxkotlin

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.BiFunction
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any, U : Any> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$zipWith");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   var0 = var0.zipWith(var1, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "zipWith(other, BiFunction { t, u -> Pair(t, u) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, U : Any, R : Any> Single<T>.zipWith(other: SingleSource<U>, crossinline zipper: (T, U) -> R): Single<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$zipWith");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   Intrinsics.checkParameterIsNotNull(var2, "zipper");
   var0 = var0.zipWith(var1, new BiFunction<T, U, R>(var2) {
      final Function2 $zipper;

      {
         this.$zipper = var1;
      }

      @Override
      public final R apply(T var1, U var2) {
         Intrinsics.checkParameterIsNotNull(var1, "t");
         Intrinsics.checkParameterIsNotNull(var2, "u");
         return (R)this.$zipper.invoke(var1, var2);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "zipWith(other, BiFunctio…-> zipper.invoke(t, u) })");
   return var0;
}
