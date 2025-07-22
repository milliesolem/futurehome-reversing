package kotlin

import java.io.Serializable

public data class Pair<A, B>(first: Any, second: Any) : Serializable {
   public final val first: Any
   public final val second: Any

   init {
      this.first = (A)var1;
      this.second = (B)var2;
   }

   public operator fun component1(): Any {
      return this.first;
   }

   public operator fun component2(): Any {
      return this.second;
   }

   public fun copy(first: Any = var0.first, second: Any = var0.second): Pair<Any, Any> {
      return new Pair<>((A)var1, (B)var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is Pair) {
         return false;
      } else {
         var1 = var1;
         if (!(this.first == var1.first)) {
            return false;
         } else {
            return this.second == var1.second;
         }
      }
   }

   public override fun hashCode(): Int {
      var var2: Int = 0;
      val var1: Int;
      if (this.first == null) {
         var1 = 0;
      } else {
         var1 = this.first.hashCode();
      }

      if (this.second != null) {
         var2 = this.second.hashCode();
      }

      return var1 * 31 + var2;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("(");
      var1.append(this.first);
      var1.append(", ");
      var1.append(this.second);
      var1.append(')');
      return var1.toString();
   }
}
