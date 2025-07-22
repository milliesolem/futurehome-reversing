package kotlinx.coroutines

import kotlinx.coroutines.internal.Symbol

private final val COMPLETING_ALREADY: Symbol = new Symbol("COMPLETING_ALREADY")
private final val COMPLETING_RETRY: Symbol = new Symbol("COMPLETING_RETRY")
internal final val COMPLETING_WAITING_CHILDREN: Symbol = new Symbol("COMPLETING_WAITING_CHILDREN")
private final val EMPTY_ACTIVE: Empty = new Empty(true)
private final val EMPTY_NEW: Empty = new Empty(false)
private const val FALSE: Int = 0
private const val RETRY: Int = -1
private final val SEALED: Symbol = new Symbol("SEALED")
private final val TOO_LATE_TO_CANCEL: Symbol = new Symbol("TOO_LATE_TO_CANCEL")
private const val TRUE: Int = 1

@JvmSynthetic
fun `access$getCOMPLETING_ALREADY$p`(): Symbol {
   return COMPLETING_ALREADY;
}

@JvmSynthetic
fun `access$getCOMPLETING_RETRY$p`(): Symbol {
   return COMPLETING_RETRY;
}

@JvmSynthetic
fun `access$getEMPTY_ACTIVE$p`(): Empty {
   return EMPTY_ACTIVE;
}

@JvmSynthetic
fun `access$getEMPTY_NEW$p`(): Empty {
   return EMPTY_NEW;
}

@JvmSynthetic
fun `access$getSEALED$p`(): Symbol {
   return SEALED;
}

@JvmSynthetic
fun `access$getTOO_LATE_TO_CANCEL$p`(): Symbol {
   return TOO_LATE_TO_CANCEL;
}

internal fun Any?.boxIncomplete(): Any? {
   var var1: Any = var0;
   if (var0 is Incomplete) {
      var1 = new IncompleteStateBox(var0 as Incomplete);
   }

   return var1;
}

internal fun Any?.unboxState(): Any? {
   val var1: IncompleteStateBox;
   if (var0 is IncompleteStateBox) {
      var1 = var0 as IncompleteStateBox;
   } else {
      var1 = null;
   }

   var var2: Any = var0;
   if (var1 != null) {
      var2 = var1.state;
      if (var1.state == null) {
         var2 = var0;
      }
   }

   return var2;
}
