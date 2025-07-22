package kotlin

import java.io.Serializable

public data class Triple<A, B, C>(first: Any, second: Any, third: Any) : Serializable {
   public final val first: Any
   public final val second: Any
   public final val third: Any

   init {
      this.first = (A)var1;
      this.second = (B)var2;
      this.third = (C)var3;
   }

   public operator fun component1(): Any {
      return this.first;
   }

   public operator fun component2(): Any {
      return this.second;
   }

   public operator fun component3(): Any {
      return this.third;
   }

   public fun copy(first: Any = var0.first, second: Any = var0.second, third: Any = var0.third): Triple<Any, Any, Any> {
      return new Triple<>((A)var1, (B)var2, (C)var3);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is Triple) {
         return false;
      } else {
         var1 = var1;
         if (!(this.first == var1.first)) {
            return false;
         } else if (!(this.second == var1.second)) {
            return false;
         } else {
            return this.third == var1.third;
         }
      }
   }

   public override fun hashCode(): Int {
      var var3: Int = 0;
      val var1: Int;
      if (this.first == null) {
         var1 = 0;
      } else {
         var1 = this.first.hashCode();
      }

      val var2: Int;
      if (this.second == null) {
         var2 = 0;
      } else {
         var2 = this.second.hashCode();
      }

      if (this.third != null) {
         var3 = this.third.hashCode();
      }

      return (var1 * 31 + var2) * 31 + var3;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("(");
      var1.append(this.first);
      var1.append(", ");
      var1.append(this.second);
      var1.append(", ");
      var1.append(this.third);
      var1.append(')');
      return var1.toString();
   }
}
