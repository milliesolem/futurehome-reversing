package kotlinx.coroutines.internal

import kotlinx.coroutines.MainCoroutineDispatcher

public interface MainDispatcherFactory {
   public val loadPriority: Int

   public abstract fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher {
   }

   public open fun hintOnError(): String? {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun hintOnError(var0: MainDispatcherFactory): java.lang.String {
         return null;
      }
   }
}
