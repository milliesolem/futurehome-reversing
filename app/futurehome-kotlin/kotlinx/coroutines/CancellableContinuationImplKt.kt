package kotlinx.coroutines

import kotlinx.coroutines.internal.Symbol

private const val DECISION_SHIFT: Int = 29
private const val INDEX_MASK: Int = 536870911
private const val NO_INDEX: Int = 536870911
private const val RESUMED: Int = 2
internal final val RESUME_TOKEN: Symbol = new Symbol("RESUME_TOKEN")
private const val SUSPENDED: Int = 1
private const val UNDECIDED: Int = 0

private final val decision: Int
   private final inline get() {
      return var0 shr 29;
   }


private final val index: Int
   private final inline get() {
      return var0 and 536870911;
   }


private inline fun decisionAndIndex(decision: Int, index: Int): Int {
   return (var0 shl 29) + var1;
}
