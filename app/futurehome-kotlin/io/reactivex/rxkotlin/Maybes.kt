package io.reactivex.rxkotlin

import io.reactivex.Maybe
import io.reactivex.MaybeSource
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

public object Maybes {
   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T : Any, U : Any> zip(s1: MaybeSource<T>, s2: MaybeSource<U>): Maybe<Pair<T, U>> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      val var3: Maybe = Maybe.zip(var1, var2, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var3, "Maybe.zip(s1, s2,\n      …n { t, u -> Pair(t, u) })");
      return var3;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public fun <T1 : Any, T2 : Any, T3 : Any> zip(s1: MaybeSource<T1>, s2: MaybeSource<T2>, s3: MaybeSource<T3>): Maybe<Triple<T1, T2, T3>> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      val var4: Maybe = Maybe.zip(var1, var2, var3, <unrepresentable>.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(var4, "Maybe.zip(s1, s2, s3,\n  … -> Triple(t1, t2, t3) })");
      return var4;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, T9 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      s4: MaybeSource<T4>,
      s5: MaybeSource<T5>,
      s6: MaybeSource<T6>,
      s7: MaybeSource<T7>,
      s8: MaybeSource<T8>,
      s9: MaybeSource<T9>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
   ): Maybe<R> {
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
      val var11: Maybe = Maybe.zip(var1, var2, var3, var4, var5, var6, var7, var8, var9, new Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>(var10) {
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
      Intrinsics.checkExpressionValueIsNotNull(var11, "Maybe.zip(s1, s2, s3, s4…4, t5, t6, t7, t8, t9) })");
      return var11;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, T8 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      s4: MaybeSource<T4>,
      s5: MaybeSource<T5>,
      s6: MaybeSource<T6>,
      s7: MaybeSource<T7>,
      s8: MaybeSource<T8>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6, T7, T8) -> R
   ): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "s7");
      Intrinsics.checkParameterIsNotNull(var8, "s8");
      Intrinsics.checkParameterIsNotNull(var9, "zipper");
      val var10: Maybe = Maybe.zip(var1, var2, var3, var4, var5, var6, var7, var8, new Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>(var9) {
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
      Intrinsics.checkExpressionValueIsNotNull(var10, "Maybe.zip(s1, s2, s3, s4…3, t4, t5, t6, t7, t8) })");
      return var10;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, T7 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      s4: MaybeSource<T4>,
      s5: MaybeSource<T5>,
      s6: MaybeSource<T6>,
      s7: MaybeSource<T7>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6, T7) -> R
   ): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "s7");
      Intrinsics.checkParameterIsNotNull(var8, "zipper");
      val var9: Maybe = Maybe.zip(var1, var2, var3, var4, var5, var6, var7, new Function7<T1, T2, T3, T4, T5, T6, T7, R>(var8) {
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
      Intrinsics.checkExpressionValueIsNotNull(var9, "Maybe.zip(s1, s2, s3, s4…2, t3, t4, t5, t6, t7) })");
      return var9;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, T6 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      s4: MaybeSource<T4>,
      s5: MaybeSource<T5>,
      s6: MaybeSource<T6>,
      crossinline zipper: (T1, T2, T3, T4, T5, T6) -> R
   ): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "s6");
      Intrinsics.checkParameterIsNotNull(var7, "zipper");
      val var8: Maybe = Maybe.zip(var1, var2, var3, var4, var5, var6, new Function6<T1, T2, T3, T4, T5, T6, R>(var7) {
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
      Intrinsics.checkExpressionValueIsNotNull(var8, "Maybe.zip(s1, s2, s3, s4…1, t2, t3, t4, t5, t6) })");
      return var8;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      s4: MaybeSource<T4>,
      s5: MaybeSource<T5>,
      crossinline zipper: (T1, T2, T3, T4, T5) -> R
   ): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "s5");
      Intrinsics.checkParameterIsNotNull(var6, "zipper");
      val var7: Maybe = Maybe.zip(var1, var2, var3, var4, var5, new Function5<T1, T2, T3, T4, T5, R>(var6) {
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
      Intrinsics.checkExpressionValueIsNotNull(var7, "Maybe.zip(s1, s2, s3, s4…ke(t1, t2, t3, t4, t5) })");
      return var7;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      s4: MaybeSource<T4>,
      crossinline zipper: (T1, T2, T3, T4) -> R
   ): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "s4");
      Intrinsics.checkParameterIsNotNull(var5, "zipper");
      val var6: Maybe = Maybe.zip(var1, var2, var3, var4, new Function4<T1, T2, T3, T4, R>(var5) {
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
      Intrinsics.checkExpressionValueIsNotNull(var6, "Maybe.zip(s1, s2, s3, s4…invoke(t1, t2, t3, t4) })");
      return var6;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> zip(
      s1: MaybeSource<T1>,
      s2: MaybeSource<T2>,
      s3: MaybeSource<T3>,
      crossinline zipper: (T1, T2, T3) -> R
   ): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "s3");
      Intrinsics.checkParameterIsNotNull(var4, "zipper");
      val var5: Maybe = Maybe.zip(var1, var2, var3, new Function3<T1, T2, T3, R>(var4) {
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
      Intrinsics.checkExpressionValueIsNotNull(var5, "Maybe.zip(s1, s2, s3,\n  …per.invoke(t1, t2, t3) })");
      return var5;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public inline fun <T : Any, U : Any, R : Any> zip(s1: MaybeSource<T>, s2: MaybeSource<U>, crossinline zipper: (T, U) -> R): Maybe<R> {
      Intrinsics.checkParameterIsNotNull(var1, "s1");
      Intrinsics.checkParameterIsNotNull(var2, "s2");
      Intrinsics.checkParameterIsNotNull(var3, "zipper");
      val var4: Maybe = Maybe.zip(var1, var2, new BiFunction<T, U, R>(var3) {
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
      Intrinsics.checkExpressionValueIsNotNull(var4, "Maybe.zip(s1, s2,\n      …-> zipper.invoke(t, u) })");
      return var4;
   }
}
