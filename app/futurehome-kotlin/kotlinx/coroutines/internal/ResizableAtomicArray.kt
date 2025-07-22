package kotlinx.coroutines.internal

import java.util.concurrent.atomic.AtomicReferenceArray

internal class ResizableAtomicArray<T>(initialLength: Int) {
   private final var array: AtomicReferenceArray<Any>

   init {
      this.array = new AtomicReferenceArray<>(var1);
   }

   public fun currentLength(): Int {
      return this.array.length();
   }

   public operator fun get(index: Int): Any? {
      val var2: AtomicReferenceArray = this.array;
      val var3: Any;
      if (var1 < this.array.length()) {
         var3 = var2.get(var1);
      } else {
         var3 = null;
      }

      return (T)var3;
   }

   public fun setSynchronized(index: Int, value: Any?) {
      val var6: AtomicReferenceArray = this.array;
      val var4: Int = this.array.length();
      if (var1 < var4) {
         var6.set(var1, var2);
      } else {
         val var5: AtomicReferenceArray = new AtomicReferenceArray(RangesKt.coerceAtLeast(var1 + 1, var4 * 2));

         for (int var3 = 0; var3 < var4; var3++) {
            var5.set(var3, var6.get(var3));
         }

         var5.set(var1, var2);
         this.array = var5;
      }
   }
}
