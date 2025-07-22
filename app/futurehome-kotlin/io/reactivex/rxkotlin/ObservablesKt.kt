package io.reactivex.rxkotlin

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any, U : Any> Observable<T>.withLatestFrom(other: ObservableSource<U>): Observable<Pair<T, U>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withLatestFrom");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   var0 = var0.withLatestFrom(var1, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "withLatestFrom(other, Bi…n { t, u -> Pair(t, u) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any, T1 : Any, T2 : Any> Observable<T>.withLatestFrom(o1: ObservableSource<T1>, o2: ObservableSource<T2>): Observable<Triple<T, T1, T2>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withLatestFrom");
   Intrinsics.checkParameterIsNotNull(var1, "o1");
   Intrinsics.checkParameterIsNotNull(var2, "o2");
   var0 = var0.withLatestFrom(var1, var2, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "withLatestFrom(o1, o2, F…2 -> Triple(t, t1, t2) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> Observable<T>.withLatestFrom(
   o1: ObservableSource<T1>,
   o2: ObservableSource<T2>,
   o3: ObservableSource<T3>,
   o4: ObservableSource<T4>,
   crossinline combiner: (T, T1, T2, T3, T4) -> R
): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withLatestFrom");
   Intrinsics.checkParameterIsNotNull(var1, "o1");
   Intrinsics.checkParameterIsNotNull(var2, "o2");
   Intrinsics.checkParameterIsNotNull(var3, "o3");
   Intrinsics.checkParameterIsNotNull(var4, "o4");
   Intrinsics.checkParameterIsNotNull(var5, "combiner");
   var0 = var0.withLatestFrom(var1, var2, var3, var4, new Function5<T, T1, T2, T3, T4, R>(var5) {
      final kotlin.jvm.functions.Function5 $combiner;

      {
         this.$combiner = var1;
      }

      @Override
      public final R apply(T var1, T1 var2, T2 var3, T3 var4, T4 var5) {
         Intrinsics.checkParameterIsNotNull(var1, "t");
         Intrinsics.checkParameterIsNotNull(var2, "t1");
         Intrinsics.checkParameterIsNotNull(var3, "t2");
         Intrinsics.checkParameterIsNotNull(var4, "t3");
         Intrinsics.checkParameterIsNotNull(var5, "t4");
         return (R)this.$combiner.invoke(var1, var2, var3, var4, var5);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "withLatestFrom(o1, o2, o…oke(t, t1, t2, t3, t4) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, T1 : Any, T2 : Any, T3 : Any, R : Any> Observable<T>.withLatestFrom(
   o1: ObservableSource<T1>,
   o2: ObservableSource<T2>,
   o3: ObservableSource<T3>,
   crossinline combiner: (T, T1, T2, T3) -> R
): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withLatestFrom");
   Intrinsics.checkParameterIsNotNull(var1, "o1");
   Intrinsics.checkParameterIsNotNull(var2, "o2");
   Intrinsics.checkParameterIsNotNull(var3, "o3");
   Intrinsics.checkParameterIsNotNull(var4, "combiner");
   var0 = var0.withLatestFrom(var1, var2, var3, new Function4<T, T1, T2, T3, R>(var4) {
      final kotlin.jvm.functions.Function4 $combiner;

      {
         this.$combiner = var1;
      }

      @Override
      public final R apply(T var1, T1 var2, T2 var3, T3 var4) {
         Intrinsics.checkParameterIsNotNull(var1, "t");
         Intrinsics.checkParameterIsNotNull(var2, "t1");
         Intrinsics.checkParameterIsNotNull(var3, "t2");
         Intrinsics.checkParameterIsNotNull(var4, "t3");
         return (R)this.$combiner.invoke(var1, var2, var3, var4);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "withLatestFrom(o1, o2, o….invoke(t, t1, t2, t3) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, T1 : Any, T2 : Any, R : Any> Observable<T>.withLatestFrom(
   o1: ObservableSource<T1>,
   o2: ObservableSource<T2>,
   crossinline combiner: (T, T1, T2) -> R
): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withLatestFrom");
   Intrinsics.checkParameterIsNotNull(var1, "o1");
   Intrinsics.checkParameterIsNotNull(var2, "o2");
   Intrinsics.checkParameterIsNotNull(var3, "combiner");
   var0 = var0.withLatestFrom(var1, var2, new Function3<T, T1, T2, R>(var3) {
      final kotlin.jvm.functions.Function3 $combiner;

      {
         this.$combiner = var1;
      }

      @Override
      public final R apply(T var1, T1 var2, T2 var3) {
         Intrinsics.checkParameterIsNotNull(var1, "t");
         Intrinsics.checkParameterIsNotNull(var2, "t1");
         Intrinsics.checkParameterIsNotNull(var3, "t2");
         return (R)this.$combiner.invoke(var1, var2, var3);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "withLatestFrom(o1, o2, F…iner.invoke(t, t1, t2) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, U : Any, R : Any> Observable<T>.withLatestFrom(other: ObservableSource<U>, crossinline combiner: (T, U) -> R): Observable<R> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withLatestFrom");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   Intrinsics.checkParameterIsNotNull(var2, "combiner");
   var0 = var0.withLatestFrom(var1, new BiFunction<T, U, R>(var2) {
      final Function2 $combiner;

      {
         this.$combiner = var1;
      }

      @Override
      public final R apply(T var1, U var2) {
         Intrinsics.checkParameterIsNotNull(var1, "t");
         Intrinsics.checkParameterIsNotNull(var2, "u");
         return (R)this.$combiner.invoke(var1, var2);
      }
   });
   Intrinsics.checkExpressionValueIsNotNull(var0, "withLatestFrom(other, Bi… combiner.invoke(t, u) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any, U : Any> Observable<T>.zipWith(other: ObservableSource<U>): Observable<Pair<T, U>> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$zipWith");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   var0 = var0.zipWith(var1, <unrepresentable>.INSTANCE);
   Intrinsics.checkExpressionValueIsNotNull(var0, "zipWith(other, BiFunction { t, u -> Pair(t, u) })");
   return var0;
}

@CheckReturnValue
@SchedulerSupport("none")
public inline fun <T : Any, U : Any, R : Any> Observable<T>.zipWith(other: ObservableSource<U>, crossinline zipper: (T, U) -> R): Observable<R> {
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
