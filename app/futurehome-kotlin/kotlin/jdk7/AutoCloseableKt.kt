package kotlin.jdk7

import kotlin.contracts.InvocationKind
import kotlin.jvm.functions.Function0

public inline fun AutoCloseable(crossinline closeAction: () -> Unit): AutoCloseable {
   return new AutoCloseable(var0) {
      final Function0<Unit> $closeAction;

      {
         this.$closeAction = var1;
      }

      @Override
      public final void close() {
         this.$closeAction.invoke();
      }
   };
}

@JvmSynthetic
fun `AutoCloseable$annotations`() {
}

internal fun AutoCloseable?.closeFinally(cause: Throwable?) {
   if (var0 != null) {
      if (var1 == null) {
         var0.close();
      } else {
         try {
            var0.close();
         } catch (var2: java.lang.Throwable) {
            ExceptionsKt.addSuppressed(var1, var2);
            return;
         }
      }
   }
}

public inline fun <T : AutoCloseable?, R> T.use(block: (T) -> R): R {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   label18: {
      try {
         var9 = var1.invoke(var0);
      } catch (var4: java.lang.Throwable) {
         val var2: java.lang.Throwable = var4;

         try {
            throw var2;
         } catch (var3: java.lang.Throwable) {
            closeFinally(var0, var4);
         }
      }

      closeFinally(var0, null);
      return (R)var9;
   }
}
