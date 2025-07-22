package kotlinx.coroutines

import kotlinx.coroutines.internal.MainDispatchersKt
import kotlinx.coroutines.internal.SystemPropsKt

internal final val DefaultDelay: Delay = initializeDefaultDelay()
private final val defaultMainDelayOptIn: Boolean = SystemPropsKt.systemProp("kotlinx.coroutines.main.delay", false)

private fun initializeDefaultDelay(): Delay {
   label10:
   if (!defaultMainDelayOptIn) {
      return DefaultExecutor.INSTANCE;
   } else {
      val var1: MainCoroutineDispatcher = Dispatchers.getMain();
      return if (!MainDispatchersKt.isMissing(var1) && var1 is Delay) var1 as Delay else DefaultExecutor.INSTANCE;
   }
}
