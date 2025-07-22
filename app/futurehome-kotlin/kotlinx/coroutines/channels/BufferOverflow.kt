package kotlinx.coroutines.channels

public enum class BufferOverflow {
   DROP_LATEST,
   DROP_OLDEST,
   SUSPEND   @JvmStatic
   private BufferOverflow[] $VALUES = $values();
}
