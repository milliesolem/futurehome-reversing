package kotlinx.coroutines

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal data class CoroutineId(id: Long) : AbstractCoroutineContextElement(Key), ThreadContextElement<java.lang.String> {
   public final val id: Long

   init {
      this.id = var1;
   }

   public operator fun component1(): Long {
      return this.id;
   }

   public fun copy(id: Long = var0.id): CoroutineId {
      return new CoroutineId(var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is CoroutineId) {
         return false;
      } else {
         return this.id == (var1 as CoroutineId).id;
      }
   }

   public override fun hashCode(): Int {
      return UByte$$ExternalSyntheticBackport0.m(this.id);
   }

   public open fun restoreThreadContext(context: CoroutineContext, oldState: String) {
      Thread.currentThread().setName(var2);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CoroutineId(");
      var1.append(this.id);
      var1.append(')');
      return var1.toString();
   }

   public open fun updateThreadContext(context: CoroutineContext): String {
      label15: {
         val var8: CoroutineName = var1.get(CoroutineName.Key);
         if (var8 != null) {
            val var4: java.lang.String = var8.getName();
            var9 = var4;
            if (var4 != null) {
               break label15;
            }
         }

         var9 = "coroutine";
      }

      val var5: Thread = Thread.currentThread();
      val var11: java.lang.String = var5.getName();
      val var3: Int = StringsKt.lastIndexOf$default(var11, " @", 0, false, 6, null);
      var var2: Int = var3;
      if (var3 < 0) {
         var2 = var11.length();
      }

      val var7: StringBuilder = new StringBuilder(var9.length() + var2 + 10);
      val var6: java.lang.String = var11.substring(0, var2);
      var7.append(var6);
      var7.append(" @");
      var7.append(var9);
      var7.append('#');
      var7.append(this.id);
      val var10: java.lang.String = var7.toString();
      var5.setName(var10);
      return var11;
   }

   public companion object Key : CoroutineContext.Key<CoroutineId>
}
