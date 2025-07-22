package kotlin.collections.builders

import java.util.Map.Entry
import kotlin.collections.MutableMap.MutableEntry

internal class MapBuilderEntries<K, V> internal constructor(backing: MapBuilder<Any, Any>) : AbstractMapBuilderEntrySet<Entry<K, V>, K, V> {
   public final val backing: MapBuilder<Any, Any>

   public open val size: Int
      public open get() {
         return this.backing.size();
      }


   init {
      this.backing = var1;
   }

   public open fun add(element: MutableEntry<Any, Any>): Boolean {
      throw new UnsupportedOperationException();
   }

   public override fun addAll(elements: Collection<MutableEntry<Any, Any>>): Boolean {
      throw new UnsupportedOperationException();
   }

   public override fun clear() {
      this.backing.clear();
   }

   public override fun containsAll(elements: Collection<MutableEntry<Any, Any>>): Boolean {
      return this.backing.containsAllEntries$kotlin_stdlib(var1);
   }

   public override fun containsEntry(element: kotlin.collections.Map.Entry<Any, Any>): Boolean {
      return this.backing.containsEntry$kotlin_stdlib(var1);
   }

   public override fun isEmpty(): Boolean {
      return this.backing.isEmpty();
   }

   public override operator fun iterator(): MutableIterator<MutableEntry<Any, Any>> {
      return this.backing.entriesIterator$kotlin_stdlib() as MutableIterator<MutableMap.MutableEntry<K, V>>;
   }

   public override fun remove(element: MutableEntry<Any, Any>): Boolean {
      return this.backing.removeEntry$kotlin_stdlib(var1);
   }

   public override fun removeAll(elements: Collection<MutableEntry<Any, Any>>): Boolean {
      this.backing.checkIsMutable$kotlin_stdlib();
      return super.removeAll(var1);
   }

   public override fun retainAll(elements: Collection<MutableEntry<Any, Any>>): Boolean {
      this.backing.checkIsMutable$kotlin_stdlib();
      return super.retainAll(var1);
   }
}
