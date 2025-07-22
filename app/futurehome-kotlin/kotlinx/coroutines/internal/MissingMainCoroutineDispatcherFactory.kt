package kotlinx.coroutines.internal

import kotlinx.coroutines.MainCoroutineDispatcher

public object MissingMainCoroutineDispatcherFactory : MainDispatcherFactory {
   public open val loadPriority: Int
      public open get() {
         return -1;
      }


   public override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher {
      return new MissingMainCoroutineDispatcher(null, null, 2, null);
   }

   override fun hintOnError(): java.lang.String {
      return MainDispatcherFactory.DefaultImpls.hintOnError(this);
   }
}
