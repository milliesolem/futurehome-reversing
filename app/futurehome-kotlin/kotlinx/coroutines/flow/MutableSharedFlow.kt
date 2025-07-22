package kotlinx.coroutines.flow

public interface MutableSharedFlow<T> : SharedFlow<T>, FlowCollector<T> {
   public val subscriptionCount: StateFlow<Int>

   public abstract override suspend fun emit(value: Any) {
   }

   public abstract fun resetReplayCache() {
   }

   public abstract fun tryEmit(value: Any): Boolean {
   }
}
