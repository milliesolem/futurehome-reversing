package kotlinx.coroutines

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

public data class CoroutineName(name: String) : AbstractCoroutineContextElement(Key) {
   public final val name: String

   init {
      this.name = var1;
   }

   public operator fun component1(): String {
      return this.name;
   }

   public fun copy(name: String = var0.name): CoroutineName {
      return new CoroutineName(var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is CoroutineName) {
         return false;
      } else {
         return this.name == (var1 as CoroutineName).name;
      }
   }

   public override fun hashCode(): Int {
      return this.name.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CoroutineName(");
      var1.append(this.name);
      var1.append(')');
      return var1.toString();
   }

   public companion object Key : CoroutineContext.Key<CoroutineName>
}
