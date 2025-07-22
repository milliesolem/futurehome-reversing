package kotlinx.coroutines.flow.internal

import kotlinx.coroutines.flow.FlowCollector

internal inline fun checkIndexOverflow(index: Int): Int {
   if (var0 >= 0) {
      return var0;
   } else {
      throw new ArithmeticException("Index overflow has happened");
   }
}

internal fun AbortFlowException.checkOwnership(owner: FlowCollector<*>) {
   if (var0.owner != var1) {
      throw var0;
   }
}
