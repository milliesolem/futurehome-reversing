package kotlinx.coroutines

public const val IO_PARALLELISM_PROPERTY_NAME: String = "kotlinx.coroutines.io.parallelism"

@Deprecated(
   level = DeprecationLevel.HIDDEN,
   message = "Should not be used directly"
)
public final val IO: CoroutineDispatcher
   public final get() {
      return Dispatchers.getIO();
   }

