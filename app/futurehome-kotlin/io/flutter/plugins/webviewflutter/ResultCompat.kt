package io.flutter.plugins.webviewflutter

import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.TypeIntrinsics

public class ResultCompat<T>(result: Result<Any>) {
   public final val result: Result<Any>
   private final val value: Any?
   private final val exception: Throwable?
   public final val isSuccess: Boolean
   public final val isFailure: Boolean

   init {
      this.result = var1;
      val var2: Any;
      if (Result.isFailure-impl(var1)) {
         var2 = null;
      } else {
         var2 = var1;
      }

      this.value = (T)var2;
      this.exception = Result.exceptionOrNull-impl(var1);
      this.isSuccess = Result.isSuccess-impl(var1);
      this.isFailure = Result.isFailure-impl(var1);
   }

   public fun exceptionOrNull(): Throwable? {
      return this.exception;
   }

   public fun getOrNull(): Any? {
      return this.value;
   }

   public companion object {
      @JvmStatic
      fun `asCompatCallback$lambda$0`(var0: Function1, var1: Result): Unit {
         var0.invoke(new ResultCompat(var1.unbox-impl()));
         return Unit.INSTANCE;
      }

      public fun <T> asCompatCallback(result: (ResultCompat<T>) -> Unit): (Result<T>) -> Unit {
         return new ResultCompat$Companion$$ExternalSyntheticLambda0(var1);
      }

      public fun <T> success(value: T, callback: Any) {
         val var3: Function1 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 1) as Function1;
         var2 = Result.Companion;
         var3.invoke(Result.box-impl(Result.constructor-impl(var1)));
      }
   }
}
