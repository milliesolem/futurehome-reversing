package kotlinx.coroutines

import java.io.Closeable
import java.util.concurrent.Executor
import kotlin.coroutines.AbstractCoroutineContextKey

public abstract class ExecutorCoroutineDispatcher : CoroutineDispatcher, Closeable {
   public abstract val executor: Executor

   public abstract override fun close() {
   }

   public companion object Key : AbstractCoroutineContextKey(CoroutineDispatcher.Key, <unrepresentable>.INSTANCE)
}
