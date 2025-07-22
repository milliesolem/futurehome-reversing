package kotlin

import java.io.Serializable

@JvmInline
public inline class Result<T> : Serializable {
   internal final val value: Any?

   public final val isSuccess: Boolean
      public final get() {
         return var0 is Result.Failure xor true;
      }


   public final val isFailure: Boolean
      public final get() {
         return var0 is Result.Failure;
      }


   @JvmStatic
   fun <T> `constructor-impl`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   fun `equals-impl`(var0: Any, var1: Any): Boolean {
      if (var1 !is Result) {
         return false;
      } else {
         return var0 == (var1 as Result).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Any, var1: Any): Boolean {
      return var0 == var1;
   }

   @JvmStatic
   public fun exceptionOrNull(): Throwable? {
      if (var0 is Result.Failure) {
         var0 = (var0 as Result.Failure).exception;
      } else {
         var0 = null;
      }

      return var0;
   }

   @JvmStatic
   public inline fun getOrNull(): Any? {
      var var1: Any = var0;
      if (isFailure-impl(var0)) {
         var1 = null;
      }

      return (T)var1;
   }

   @JvmStatic
   fun `hashCode-impl`(var0: Any): Int {
      val var1: Int;
      if (var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.hashCode();
      }

      return var1;
   }

   @JvmStatic
   public open fun toString(): String {
      if (var0 is Result.Failure) {
         var0 = (var0 as Result.Failure).toString();
      } else {
         val var1: StringBuilder = new StringBuilder("Success(");
         var1.append((Object)var0);
         var1.append(')');
         var0 = var1.toString();
      }

      return var0;
   }

   public override operator fun equals(other: Any?): Boolean {
      return equals-impl(this.value, var1);
   }

   public override fun hashCode(): Int {
      return hashCode-impl(this.value);
   }

   override fun toString(): java.lang.String {
      return toString-impl(this.value);
   }

   public companion object {
      public inline fun <T> failure(exception: Throwable): Result<T> {
         return Result.constructor-impl(ResultKt.createFailure(var1));
      }

      public inline fun <T> success(value: T): Result<T> {
         return Result.constructor-impl(var1);
      }
   }

   internal class Failure(exception: Throwable) : Serializable {
      public final val exception: Throwable

      init {
         this.exception = var1;
      }

      public override operator fun equals(other: Any?): Boolean {
         val var2: Boolean;
         if (var1 is Result.Failure && this.exception == (var1 as Result.Failure).exception) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public override fun hashCode(): Int {
         return this.exception.hashCode();
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("Failure(");
         var1.append(this.exception);
         var1.append(')');
         return var1.toString();
      }
   }
}
