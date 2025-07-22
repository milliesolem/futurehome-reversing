package kotlin

import kotlin.contracts.InvocationKind

internal class StandardKt__StandardKt {
   open fun StandardKt__StandardKt() {
   }

   @JvmStatic
   public inline fun TODO(): Nothing {
      throw new NotImplementedError(null, 1, null);
   }

   @JvmStatic
   public inline fun TODO(reason: String): Nothing {
      val var1: StringBuilder = new StringBuilder("An operation is not implemented: ");
      var1.append(var0);
      throw new NotImplementedError(var1.toString());
   }

   @JvmStatic
   public inline fun <T> T.also(block: (T) -> Unit): T {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      var1.invoke(var0);
      return (T)var0;
   }

   @JvmStatic
   public inline fun <T> T.apply(block: (T) -> Unit): T {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      var1.invoke(var0);
      return (T)var0;
   }

   @JvmStatic
   public inline fun <T, R> T.let(block: (T) -> R): R {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      return (R)var1.invoke(var0);
   }

   @JvmStatic
   public inline fun repeat(times: Int, action: (Int) -> Unit) {
      contract {
         callsInPlace(action)
      }

      for (int var2 = 0; var2 < var0; var2++) {
         var1.invoke(var2);
      }
   }

   @JvmStatic
   public inline fun <T, R> T.run(block: (T) -> R): R {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      return (R)var1.invoke(var0);
   }

   @JvmStatic
   public inline fun <R> run(block: () -> R): R {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      return (R)var0.invoke();
   }

   @JvmStatic
   public inline fun <T> T.takeIf(predicate: (T) -> Boolean): T? {
      contract {
         callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
      }

      if (!var1.invoke(var0) as java.lang.Boolean) {
         var0 = null;
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun <T> T.takeUnless(predicate: (T) -> Boolean): T? {
      contract {
         callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
      }

      if (var1.invoke(var0) as java.lang.Boolean) {
         var0 = null;
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun <T, R> with(receiver: T, block: (T) -> R): R {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      return (R)var1.invoke(var0);
   }
}
