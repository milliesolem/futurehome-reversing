package kotlinx.coroutines.android

import android.os.Looper
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.internal.MainDispatcherFactory

internal class AndroidDispatcherFactory : MainDispatcherFactory {
   public open val loadPriority: Int
      public open get() {
         return 1073741823;
      }


   public override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher {
      val var2: Looper = Looper.getMainLooper();
      if (var2 != null) {
         return new HandlerContext(HandlerDispatcherKt.asHandler(var2, true), null, 2, null);
      } else {
         throw new IllegalStateException("The main looper is not available");
      }
   }

   public override fun hintOnError(): String {
      return "For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used";
   }
}
