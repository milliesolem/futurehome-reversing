package kotlin.internal

import java.lang.reflect.Method
import java.util.regex.MatchResult
import kotlin.random.FallbackThreadLocalRandom
import kotlin.random.Random

internal open class PlatformImplementations {
   public open fun addSuppressed(cause: Throwable, exception: Throwable) {
      if (PlatformImplementations.ReflectThrowable.addSuppressed != null) {
         PlatformImplementations.ReflectThrowable.addSuppressed.invoke(var1, var2);
      }
   }

   public open fun defaultPlatformRandom(): Random {
      return new FallbackThreadLocalRandom();
   }

   public open fun getMatchResultNamedGroup(matchResult: MatchResult, name: String): MatchGroup? {
      throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
   }

   public open fun getSuppressed(exception: Throwable): List<Throwable> {
      if (PlatformImplementations.ReflectThrowable.getSuppressed != null) {
         val var3: Any = PlatformImplementations.ReflectThrowable.getSuppressed.invoke(var1, null);
         if (var3 != null) {
            val var5: java.util.List = ArraysKt.asList(var3 as Array<java.lang.Throwable>);
            if (var5 != null) {
               return var5;
            }
         }
      }

      return CollectionsKt.emptyList();
   }

   private object ReflectThrowable {
      public final val addSuppressed: Method?
      public final val getSuppressed: Method?

      @JvmStatic
      fun {
         val var5: Array<Method> = java.lang.Throwable.class.getMethods();
         var var2: Int = var5.length;
         var var0: Int = 0;

         var var3: Method;
         val var4: Any;
         while (true) {
            var4 = null;
            if (var0 >= var2) {
               var3 = null;
               break;
            }

            var3 = var5[var0];
            if (var5[var0].getName() == "addSuppressed") {
               val var6: Array<Class> = var3.getParameterTypes();
               if (ArraysKt.singleOrNull(var6) == java.lang.Throwable::class.java) {
                  break;
               }
            }

            var0++;
         }

         addSuppressed = var3;
         var2 = var5.length;
         var0 = 0;

         while (true) {
            var3 = (Method)var4;
            if (var0 >= var2) {
               break;
            }

            var3 = var5[var0];
            if (var5[var0].getName() == "getSuppressed") {
               break;
            }

            var0++;
         }

         getSuppressed = var3;
      }
   }
}
