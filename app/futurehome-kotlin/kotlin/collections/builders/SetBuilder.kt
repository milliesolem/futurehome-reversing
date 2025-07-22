package kotlin.collections.builders

import java.io.NotSerializableException
import java.io.Serializable
import kotlin.jvm.internal.markers.KMutableSet

internal class SetBuilder<E> internal constructor(backing: MapBuilder<Any, *>) : AbstractMutableSet<E>, java.util.Set<E>, Serializable, KMutableSet {
   private final val backing: MapBuilder<Any, *>

   public open val size: Int
      public open get() {
         return this.backing.size();
      }


   public constructor() : this(new MapBuilder<>())
   public constructor(initialCapacity: Int) : this(new MapBuilder<>(var1))
   init {
      this.backing = var1;
   }

   private fun writeReplace(): Any {
      if (this.backing.isReadOnly$kotlin_stdlib()) {
         return new SerializedCollection(this, 1);
      } else {
         throw new NotSerializableException("The set cannot be serialized while it is being built.");
      }
   }

   public override fun add(element: Any): Boolean {
      val var2: Boolean;
      if (this.backing.addKey$kotlin_stdlib((E)var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun addAll(elements: Collection<Any>): Boolean {
      this.backing.checkIsMutable$kotlin_stdlib();
      return super.addAll(var1);
   }

   public fun build(): Set<Any> {
      this.backing.build();
      val var1: java.util.Set;
      if (this.size() > 0) {
         var1 = this;
      } else {
         var1 = Empty;
      }

      return var1;
   }

   public override fun clear() {
      this.backing.clear();
   }

   public override operator fun contains(element: Any): Boolean {
      return this.backing.containsKey(var1);
   }

   public override fun isEmpty(): Boolean {
      return this.backing.isEmpty();
   }

   public override operator fun iterator(): MutableIterator<Any> {
      return this.backing.keysIterator$kotlin_stdlib() as MutableIterator<E>;
   }

   public override fun remove(element: Any): Boolean {
      return this.backing.removeKey$kotlin_stdlib((E)var1);
   }

   public override fun removeAll(elements: Collection<Any>): Boolean {
      this.backing.checkIsMutable$kotlin_stdlib();
      return super.removeAll(var1);
   }

   public override fun retainAll(elements: Collection<Any>): Boolean {
      this.backing.checkIsMutable$kotlin_stdlib();
      return super.retainAll(var1);
   }

   private companion object {
      private final val Empty: SetBuilder<Nothing>
   }
}
