package kotlin.collections

public data class IndexedValue<T>(index: Int, value: Any) {
   public final val index: Int
   public final val value: Any

   init {
      this.index = var1;
      this.value = (T)var2;
   }

   public operator fun component1(): Int {
      return this.index;
   }

   public operator fun component2(): Any {
      return this.value;
   }

   public fun copy(index: Int = var0.index, value: Any = var0.value): IndexedValue<Any> {
      return new IndexedValue<>(var1, (T)var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is IndexedValue) {
         return false;
      } else {
         var1 = var1;
         if (this.index != var1.index) {
            return false;
         } else {
            return this.value == var1.value;
         }
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.value == null) {
         var1 = 0;
      } else {
         var1 = this.value.hashCode();
      }

      return this.index * 31 + var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("IndexedValue(index=");
      var1.append(this.index);
      var1.append(", value=");
      var1.append(this.value);
      var1.append(')');
      return var1.toString();
   }
}
