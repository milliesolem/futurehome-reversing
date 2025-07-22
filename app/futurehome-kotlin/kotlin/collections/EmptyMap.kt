package kotlin.collections

import java.io.Serializable
import kotlin.jvm.internal.markers.KMappedMarker

private object EmptyMap : java.util.Map, Serializable, KMappedMarker {
   private const val serialVersionUID: Long = 8246714829545688274L

   public open val size: Int
      public open get() {
         return 0;
      }


   public open val entries: Set<kotlin.collections.Map.Entry<Any?, Nothing>>
      public open get() {
         return EmptySet.INSTANCE;
      }


   public open val keys: Set<Any?>
      public open get() {
         return EmptySet.INSTANCE;
      }


   public open val values: Collection<Nothing>
      public open get() {
         return EmptyList.INSTANCE;
      }


   private fun readResolve(): Any {
      return INSTANCE;
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun containsKey(key: Any?): Boolean {
      return false;
   }

   public open fun containsValue(value: Nothing): Boolean {
      return false;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is java.util.Map && (var1 as java.util.Map).isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public open operator fun get(key: Any?): Nothing? {
      return null;
   }

   public override fun hashCode(): Int {
      return 0;
   }

   public override fun isEmpty(): Boolean {
      return true;
   }

   fun put(var1: Any, var2: Void): Void {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun putAll(var1: java.util.Map) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   fun remove(var1: Any): Void {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun toString(): String {
      return "{}";
   }
}
