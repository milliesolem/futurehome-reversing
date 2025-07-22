package kotlinx.coroutines.internal

internal fun Int.checkParallelism() {
   if (var0 < 1) {
      val var1: StringBuilder = new StringBuilder("Expected positive parallelism level, but got ");
      var1.append(var0);
      throw new IllegalArgumentException(var1.toString().toString());
   }
}
