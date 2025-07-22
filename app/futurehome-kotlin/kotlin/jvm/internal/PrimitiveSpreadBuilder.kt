package kotlin.jvm.internal

public abstract class PrimitiveSpreadBuilder<T> {
   private final val size: Int

   protected final var position: Int
      internal set

   private final val spreads: Array<Any?>

   open fun PrimitiveSpreadBuilder(var1: Int) {
      this.size = var1;
      this.spreads = (T[])(new Object[var1]);
   }

   public fun addSpread(spreadArgument: Any) {
      this.spreads[this.position++] = (T)var1;
   }

   protected abstract fun Any.getSize(): Int {
   }

   protected fun size(): Int {
      val var4: Int = this.size - 1;
      var var3: Int = 0;
      var var2: Int = 0;
      if (var4 >= 0) {
         var var1: Int = 0;

         while (true) {
            val var5: Any = this.spreads[var1];
            if (this.spreads[var1] != null) {
               var3 = this.getSize((T)var5);
            } else {
               var3 = 1;
            }

            var2 += var3;
            var3 = var2;
            if (var1 == var4) {
               break;
            }

            var1++;
         }
      }

      return var3;
   }

   protected fun toArray(values: Any, result: Any): Any {
      val var8: Int = this.size - 1;
      var var3: Int = 0;
      var var4: Int;
      if (var8 >= 0) {
         var var6: Int = 0;
         var var7: Int = 0;
         var3 = 0;

         while (true) {
            val var9: Any = this.spreads[var6];
            var var5: Int = var7;
            var4 = var3;
            if (var9 != null) {
               var4 = var3;
               if (var7 < var6) {
                  var4 = var6 - var7;
                  System.arraycopy(var1, var7, var2, var3, var6 - var7);
                  var4 = var3 + var4;
               }

               var3 = this.getSize((T)var9);
               System.arraycopy(var9, 0, var2, var4, var3);
               var4 = var4 + var3;
               var5 = var6 + 1;
            }

            if (var6 == var8) {
               var3 = var5;
               break;
            }

            var6++;
            var7 = var5;
            var3 = var4;
         }
      } else {
         var4 = 0;
      }

      if (var3 < this.size) {
         System.arraycopy(var1, var3, var2, var4, this.size - var3);
      }

      return (T)var2;
   }
}
