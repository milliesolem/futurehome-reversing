package kotlinx.coroutines.selects

internal enum class TrySelectDetailedResult {
   ALREADY_SELECTED,
   CANCELLED,
   REREGISTER,
   SUCCESSFUL   @JvmStatic
   private TrySelectDetailedResult[] $VALUES = $values();
}
