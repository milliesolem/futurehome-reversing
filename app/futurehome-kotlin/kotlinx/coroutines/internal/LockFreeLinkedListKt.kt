package kotlinx.coroutines.internal

internal final val CONDITION_FALSE: Any = new Symbol("CONDITION_FALSE")
internal const val FAILURE: Int = 2
internal const val SUCCESS: Int = 1
internal const val UNDECIDED: Int = 0

internal fun Any.unwrap(): LockFreeLinkedListNode {
   val var1: Removed;
   if (var0 is Removed) {
      var1 = var0 as Removed;
   } else {
      var1 = null;
   }

   if (var1 != null && var1.ref != null) {
      return var1.ref;
   } else {
      return var0 as LockFreeLinkedListNode;
   }
}
