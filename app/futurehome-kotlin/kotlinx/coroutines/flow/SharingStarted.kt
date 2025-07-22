package kotlinx.coroutines.flow

public fun interface SharingStarted {
   public abstract fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> {
   }

   public companion object {
      public final val Eagerly: SharingStarted = (new StartedEagerly()) as SharingStarted
      public final val Lazily: SharingStarted = (new StartedLazily()) as SharingStarted

      public fun WhileSubscribed(stopTimeoutMillis: Long = 0L, replayExpirationMillis: Long = java.lang.Long.MAX_VALUE): SharingStarted {
         return new StartedWhileSubscribed(var1, var3);
      }
   }
}
