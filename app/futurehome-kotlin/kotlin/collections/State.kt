package kotlin.collections

private object State {
   public const val NOT_READY: Int = 0
   public const val READY: Int = 1
   public const val DONE: Int = 2
   public const val FAILED: Int = 3
}
