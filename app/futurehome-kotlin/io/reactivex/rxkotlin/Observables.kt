package io.reactivex.rxkotlin

import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5
import io.reactivex.functions.Function6
import io.reactivex.functions.Function7
import io.reactivex.functions.Function8
import io.reactivex.functions.Function9
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics

public object Observables {
   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T1 : Any, T2 : Any> combineLatest(source1: Observable<T1>, source2: Observable<T2>): Observable<Pair<T1, T2>> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      var1 = Observable.combineLatest(var1, var2, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…> { t1, t2 -> t1 to t2 })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T1 : Any, T2 : Any, T3 : Any> combineLatest(source1: Observable<T1>, source2: Observable<T2>, source3: Observable<T3>): Observable<
         Triple<T1, T2, T3>
      > {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      var1 = Observable.combineLatest(var1, var2, var3, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest… -> Triple(t1, t2, t3) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, T9 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      source7: Observable<T7>,
      source8: Observable<T8>,
      source9: Observable<T9>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "source7");
      Intrinsics.checkParameterIsNotNull(var8, "source8");
      Intrinsics.checkParameterIsNotNull(var9, "source9");
      Intrinsics.checkParameterIsNotNull(var10, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, var4, var5, var6, var7, var8, var9, new Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>(var10) {
         final kotlin.jvm.functions.Function9 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            Intrinsics.checkParameterIsNotNull(var7, "t7");
            Intrinsics.checkParameterIsNotNull(var8, "t8");
            Intrinsics.checkParameterIsNotNull(var9, "t9");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6, var7, var8, var9);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…4, t5, t6, t7, t8, t9) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      source7: Observable<T7>,
      source8: Observable<T8>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7, T8) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "source7");
      Intrinsics.checkParameterIsNotNull(var8, "source8");
      Intrinsics.checkParameterIsNotNull(var9, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, var4, var5, var6, var7, var8, new Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>(var9) {
         final kotlin.jvm.functions.Function8 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            Intrinsics.checkParameterIsNotNull(var7, "t7");
            Intrinsics.checkParameterIsNotNull(var8, "t8");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6, var7, var8);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…3, t4, t5, t6, t7, t8) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      source7: Observable<T7>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "source7");
      Intrinsics.checkParameterIsNotNull(var8, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, var4, var5, var6, var7, new Function7<T1, T2, T3, T4, T5, T6, T7, R>(var8) {
         final kotlin.jvm.functions.Function7 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            Intrinsics.checkParameterIsNotNull(var7, "t7");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6, var7);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…2, t3, t4, t5, t6, t7) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, var4, var5, var6, new Function6<T1, T2, T3, T4, T5, T6, R>(var7) {
         final kotlin.jvm.functions.Function6 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…1, t2, t3, t4, t5, t6) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      crossinline combineFunction: (T1, T2, T3, T4, T5) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, var4, var5, new Function5<T1, T2, T3, T4, T5, R>(var6) {
         final kotlin.jvm.functions.Function5 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…on(t1, t2, t3, t4, t5) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      crossinline combineFunction: (T1, T2, T3, T4) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, var4, new Function4<T1, T2, T3, T4, R>(var5) {
         final kotlin.jvm.functions.Function4 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…nction(t1, t2, t3, t4) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> combineLatest(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      crossinline combineFunction: (T1, T2, T3) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, var3, new Function3<T1, T2, T3, R>(var4) {
         final kotlin.jvm.functions.Function3 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            return (R)this.$combineFunction.invoke(var1, var2, var3);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…neFunction(t1, t2, t3) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, R : Any> combineLatest(source1: Observable<T1>, source2: Observable<T2>, crossinline combineFunction: (T1, T2) -> R): Observable<
         R
      > {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "combineFunction");
      var1 = Observable.combineLatest(var1, var2, new BiFunction<T1, T2, R>(var3) {
         final Function2 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            return (R)this.$combineFunction.invoke(var1, var2);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.combineLatest…ombineFunction(t1, t2) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T1 : Any, T2 : Any> zip(source1: Observable<T1>, source2: Observable<T2>): Observable<Pair<T1, T2>> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      var1 = Observable.zip(var1, var2, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …> { t1, t2 -> t1 to t2 })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T1 : Any, T2 : Any, T3 : Any> zip(source1: Observable<T1>, source2: Observable<T2>, source3: Observable<T3>): Observable<Triple<T1, T2, T3>> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      var1 = Observable.zip(var1, var2, var3, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, … -> Triple(t1, t2, t3) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, T9 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      source7: Observable<T7>,
      source8: Observable<T8>,
      source9: Observable<T9>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "source7");
      Intrinsics.checkParameterIsNotNull(var8, "source8");
      Intrinsics.checkParameterIsNotNull(var9, "source9");
      Intrinsics.checkParameterIsNotNull(var10, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, var4, var5, var6, var7, var8, var9, new Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>(var10) {
         final kotlin.jvm.functions.Function9 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            Intrinsics.checkParameterIsNotNull(var7, "t7");
            Intrinsics.checkParameterIsNotNull(var8, "t8");
            Intrinsics.checkParameterIsNotNull(var9, "t9");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6, var7, var8, var9);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …4, t5, t6, t7, t8, t9) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      source7: Observable<T7>,
      source8: Observable<T8>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7, T8) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "source7");
      Intrinsics.checkParameterIsNotNull(var8, "source8");
      Intrinsics.checkParameterIsNotNull(var9, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, var4, var5, var6, var7, var8, new Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>(var9) {
         final kotlin.jvm.functions.Function8 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            Intrinsics.checkParameterIsNotNull(var7, "t7");
            Intrinsics.checkParameterIsNotNull(var8, "t8");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6, var7, var8);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …3, t4, t5, t6, t7, t8) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      source7: Observable<T7>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "source7");
      Intrinsics.checkParameterIsNotNull(var8, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, var4, var5, var6, var7, new Function7<T1, T2, T3, T4, T5, T6, T7, R>(var8) {
         final kotlin.jvm.functions.Function7 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            Intrinsics.checkParameterIsNotNull(var7, "t7");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6, var7);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …2, t3, t4, t5, t6, t7) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      source6: Observable<T6>,
      crossinline combineFunction: (T1, T2, T3, T4, T5, T6) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "source6");
      Intrinsics.checkParameterIsNotNull(var7, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, var4, var5, var6, new Function6<T1, T2, T3, T4, T5, T6, R>(var7) {
         final kotlin.jvm.functions.Function6 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5, var6);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …1, t2, t3, t4, t5, t6) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      source5: Observable<T5>,
      crossinline combineFunction: (T1, T2, T3, T4, T5) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "source5");
      Intrinsics.checkParameterIsNotNull(var6, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, var4, var5, new Function5<T1, T2, T3, T4, T5, R>(var6) {
         final kotlin.jvm.functions.Function5 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4, var5);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …on(t1, t2, t3, t4, t5) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      source4: Observable<T4>,
      crossinline combineFunction: (T1, T2, T3, T4) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "source4");
      Intrinsics.checkParameterIsNotNull(var5, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, var4, new Function4<T1, T2, T3, T4, R>(var5) {
         final kotlin.jvm.functions.Function4 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            return (R)this.$combineFunction.invoke(var1, var2, var3, var4);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …nction(t1, t2, t3, t4) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> zip(
      source1: Observable<T1>,
      source2: Observable<T2>,
      source3: Observable<T3>,
      crossinline combineFunction: (T1, T2, T3) -> R
   ): Observable<R> {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "source3");
      Intrinsics.checkParameterIsNotNull(var4, "combineFunction");
      var1 = Observable.zip(var1, var2, var3, new Function3<T1, T2, T3, R>(var4) {
         final kotlin.jvm.functions.Function3 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            return (R)this.$combineFunction.invoke(var1, var2, var3);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …neFunction(t1, t2, t3) })");
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, R : Any> zip(source1: Observable<T1>, source2: Observable<T2>, crossinline combineFunction: (T1, T2) -> R): Observable<
         R
      > {
      Intrinsics.checkParameterIsNotNull(var1, "source1");
      Intrinsics.checkParameterIsNotNull(var2, "source2");
      Intrinsics.checkParameterIsNotNull(var3, "combineFunction");
      var1 = Observable.zip(var1, var2, new BiFunction<T1, T2, R>(var3) {
         final Function2 $combineFunction;

         {
            this.$combineFunction = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            return (R)this.$combineFunction.invoke(var1, var2);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var1, "Observable.zip(source1, …ombineFunction(t1, t2) })");
      return var1;
   }
}
