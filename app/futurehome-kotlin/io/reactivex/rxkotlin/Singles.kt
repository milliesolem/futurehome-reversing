package io.reactivex.rxkotlin

import io.reactivex.Single
import io.reactivex.SingleSource
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

public object Singles {
   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T : Any, U : Any> zip(s1: SingleSource<T>, s2: SingleSource<U>): Single<Pair<T, U>> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      val var3: Single = Single.zip(var1, var2, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var3, "Single.zip(s1, s2, BiFun…n { t, u -> Pair(t, u) })");
      return var3;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T1 : Any, T2 : Any, T3 : Any> zip(s1: SingleSource<T1>, s2: SingleSource<T2>, s3: SingleSource<T3>): Single<Triple<T1, T2, T3>> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      val var4: Single = Single.zip(var1, var2, var3, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var4, "Single.zip(s1, s2, s3, F… -> Triple(t1, t2, t3) })");
      return var4;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, T9 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      s4: SingleSource<T4>,
      s5: SingleSource<T5>,
      s6: SingleSource<T6>,
      s7: SingleSource<T7>,
      s8: SingleSource<T8>,
      s9: SingleSource<T9>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "s7");
      Intrinsics.checkParameterIsNotNull(var8, "s8");
      Intrinsics.checkParameterIsNotNull(var9, "s9");
      Intrinsics.checkParameterIsNotNull(var10, "zipper");
      val var11: Single = Single.zip(var1, var2, var3, var4, var5, var6, var7, var8, var9, new Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>(var10) {
         final kotlin.jvm.functions.Function9 $zipper;

         {
            this.$zipper = var1;
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
            return (R)this.$zipper.invoke(var1, var2, var3, var4, var5, var6, var7, var8, var9);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var11, "Single.zip(s1, s2, s3, s…4, t5, t6, t7, t8, t9) })");
      return var11;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      s4: SingleSource<T4>,
      s5: SingleSource<T5>,
      s6: SingleSource<T6>,
      s7: SingleSource<T7>,
      s8: SingleSource<T8>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6, T7, T8) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "s7");
      Intrinsics.checkParameterIsNotNull(var8, "s8");
      Intrinsics.checkParameterIsNotNull(var9, "zipper");
      val var10: Single = Single.zip(var1, var2, var3, var4, var5, var6, var7, var8, new Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>(var9) {
         final kotlin.jvm.functions.Function8 $zipper;

         {
            this.$zipper = var1;
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
            return (R)this.$zipper.invoke(var1, var2, var3, var4, var5, var6, var7, var8);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var10, "Single.zip(s1, s2, s3, s…3, t4, t5, t6, t7, t8) })");
      return var10;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      s4: SingleSource<T4>,
      s5: SingleSource<T5>,
      s6: SingleSource<T6>,
      s7: SingleSource<T7>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6, T7) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "s7");
      Intrinsics.checkParameterIsNotNull(var8, "zipper");
      val var9: Single = Single.zip(var1, var2, var3, var4, var5, var6, var7, new Function7<T1, T2, T3, T4, T5, T6, T7, R>(var8) {
         final kotlin.jvm.functions.Function7 $zipper;

         {
            this.$zipper = var1;
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
            return (R)this.$zipper.invoke(var1, var2, var3, var4, var5, var6, var7);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var9, "Single.zip(s1, s2, s3, s…2, t3, t4, t5, t6, t7) })");
      return var9;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      s4: SingleSource<T4>,
      s5: SingleSource<T5>,
      s6: SingleSource<T6>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "zipper");
      val var8: Single = Single.zip(var1, var2, var3, var4, var5, var6, new Function6<T1, T2, T3, T4, T5, T6, R>(var7) {
         final kotlin.jvm.functions.Function6 $zipper;

         {
            this.$zipper = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            Intrinsics.checkParameterIsNotNull(var6, "t6");
            return (R)this.$zipper.invoke(var1, var2, var3, var4, var5, var6);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var8, "Single.zip(s1, s2, s3, s…1, t2, t3, t4, t5, t6) })");
      return var8;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      s4: SingleSource<T4>,
      s5: SingleSource<T5>,
      crossinline zipper: (T1, T2, T3, T4, T5) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "zipper");
      val var7: Single = Single.zip(var1, var2, var3, var4, var5, new Function5<T1, T2, T3, T4, T5, R>(var6) {
         final kotlin.jvm.functions.Function5 $zipper;

         {
            this.$zipper = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            Intrinsics.checkParameterIsNotNull(var5, "t5");
            return (R)this.$zipper.invoke(var1, var2, var3, var4, var5);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var7, "Single.zip(s1, s2, s3, s…ke(t1, t2, t3, t4, t5) })");
      return var7;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      s4: SingleSource<T4>,
      crossinline zipper: (T1, T2, T3, T4) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "zipper");
      val var6: Single = Single.zip(var1, var2, var3, var4, new Function4<T1, T2, T3, T4, R>(var5) {
         final kotlin.jvm.functions.Function4 $zipper;

         {
            this.$zipper = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3, T4 var4) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            Intrinsics.checkParameterIsNotNull(var4, "t4");
            return (R)this.$zipper.invoke(var1, var2, var3, var4);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var6, "Single.zip(s1, s2, s3, s…invoke(t1, t2, t3, t4) })");
      return var6;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> zip(
      s1: SingleSource<T1>,
      s2: SingleSource<T2>,
      s3: SingleSource<T3>,
      crossinline zipper: (T1, T2, T3) -> R
   ): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "zipper");
      val var5: Single = Single.zip(var1, var2, var3, new Function3<T1, T2, T3, R>(var4) {
         final kotlin.jvm.functions.Function3 $zipper;

         {
            this.$zipper = var1;
         }

         @Override
         public final R apply(T1 var1, T2 var2, T3 var3) {
            Intrinsics.checkParameterIsNotNull(var1, "t1");
            Intrinsics.checkParameterIsNotNull(var2, "t2");
            Intrinsics.checkParameterIsNotNull(var3, "t3");
            return (R)this.$zipper.invoke(var1, var2, var3);
         }
      });
      Intrinsics.checkExpressionValueIsNotNull(var5, "Single.zip(s1, s2, s3, F…per.invoke(t1, t2, t3) })");
      return var5;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T : Any, U : Any, R : Any> zip(s1: SingleSource<T>, s2: SingleSource<U>, crossinline zipper: (T, U) -> R): Single<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "zipper");
      val var4: Single = Single.zip(var1, var2, new BiFunction<T, U, R>(var3) {
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
      Intrinsics.checkExpressionValueIsNotNull(var4, "Single.zip(s1, s2, BiFun…-> zipper.invoke(t, u) })");
      return var4;
   }
}
