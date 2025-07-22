package kotlinx.coroutines.debug.internal

import kotlinx.coroutines.internal.Symbol

private const val MAGIC: Int = -1640531527
private final val MARKED_NULL: Marked = new Marked(null)
private final val MARKED_TRUE: Marked = new Marked(true)
private const val MIN_CAPACITY: Int = 16
private final val REHASH: Symbol = new Symbol("REHASH")

@JvmSynthetic
fun `access$getREHASH$p`(): Symbol {
   return REHASH;
}

@JvmSynthetic
fun `access$mark`(var0: Any): Marked {
   return mark(var0);
}

@JvmSynthetic
fun `access$noImpl`(): Void {
   return noImpl();
}

private fun Any?.mark(): Marked {
   if (var0 == null) {
      var0 = MARKED_NULL;
   } else if (var0 == true) {
      var0 = MARKED_TRUE;
   } else {
      var0 = new Marked(var0);
   }

   return var0;
}

private fun noImpl(): Nothing {
   throw new UnsupportedOperationException("not implemented");
}
