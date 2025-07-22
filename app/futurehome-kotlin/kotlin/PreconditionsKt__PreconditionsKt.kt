package kotlin

internal class PreconditionsKt__PreconditionsKt : PreconditionsKt__AssertionsJVMKt {
   open fun PreconditionsKt__PreconditionsKt() {
   }

   @JvmStatic
   public inline fun check(value: Boolean) {
      contract {
         returns() implies (value)
      }

      if (!var0) {
         throw new IllegalStateException("Check failed.");
      }
   }

   @JvmStatic
   public inline fun check(value: Boolean, lazyMessage: () -> Any) {
      contract {
         returns() implies (value)
      }

      if (!var0) {
         throw new IllegalStateException(var1.invoke().toString());
      }
   }

   @JvmStatic
   public inline fun <T : Any> checkNotNull(value: T?): T {
      contract {
         returns() implies (value != null)
      }

      if (var0 != null) {
         return (T)var0;
      } else {
         throw new IllegalStateException("Required value was null.".toString());
      }
   }

   @JvmStatic
   public inline fun <T : Any> checkNotNull(value: T?, lazyMessage: () -> Any): T {
      contract {
         returns() implies (value != null)
      }

      if (var0 != null) {
         return (T)var0;
      } else {
         throw new IllegalStateException(var1.invoke().toString());
      }
   }

   @JvmStatic
   public inline fun error(message: Any): Nothing {
      throw new IllegalStateException(var0.toString());
   }

   @JvmStatic
   public inline fun require(value: Boolean) {
      contract {
         returns() implies (value)
      }

      if (!var0) {
         throw new IllegalArgumentException("Failed requirement.".toString());
      }
   }

   @JvmStatic
   public inline fun require(value: Boolean, lazyMessage: () -> Any) {
      contract {
         returns() implies (value)
      }

      if (!var0) {
         throw new IllegalArgumentException(var1.invoke().toString());
      }
   }

   @JvmStatic
   public inline fun <T : Any> requireNotNull(value: T?): T {
      contract {
         returns() implies (value != null)
      }

      if (var0 != null) {
         return (T)var0;
      } else {
         throw new IllegalArgumentException("Required value was null.".toString());
      }
   }

   @JvmStatic
   public inline fun <T : Any> requireNotNull(value: T?, lazyMessage: () -> Any): T {
      contract {
         returns() implies (value != null)
      }

      if (var0 != null) {
         return (T)var0;
      } else {
         throw new IllegalArgumentException(var1.invoke().toString());
      }
   }
}
