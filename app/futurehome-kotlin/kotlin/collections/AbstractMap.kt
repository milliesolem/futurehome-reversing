package kotlin.collections

import java.util.Map.Entry
import kotlin.jvm.internal.markers.KMappedMarker

public abstract class AbstractMap<K, V> : java.util.Map<K, V>, KMappedMarker {
   public open val size: Int
      public open get() {
         return this.entrySet().size();
      }


   public open val keys: Set<Any>
      public open get() {
         if (this._keys == null) {
            this._keys = new AbstractSet<K>(this) {
               final AbstractMap<K, V> this$0;

               {
                  this.this$0 = var1;
               }

               @Override
               public boolean contains(Object var1) {
                  return this.this$0.containsKey(var1);
               }

               @Override
               public int getSize() {
                  return this.this$0.size();
               }

               @Override
               public java.util.Iterator<K> iterator() {
                  return new java.util.Iterator<K>(this.this$0.entrySet().iterator()) {
                     final java.util.Iterator<Entry<K, V>> $entryIterator;

                     {
                        this.$entryIterator = var1;
                     }

                     @Override
                     public boolean hasNext() {
                        return this.$entryIterator.hasNext();
                     }

                     @Override
                     public K next() {
                        return this.$entryIterator.next().getKey();
                     }

                     @Override
                     public void remove() {
                        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                     }
                  };
               }
            };
         }

         val var1: java.util.Set = this._keys;
         return var1;
      }


   private final var _keys: Set<Any>?

   public open val values: Collection<Any>
      public open get() {
         if (this._values == null) {
            this._values = new AbstractCollection<V>(this) {
               final AbstractMap<K, V> this$0;

               {
                  this.this$0 = var1;
               }

               @Override
               public boolean contains(Object var1) {
                  return this.this$0.containsValue(var1);
               }

               @Override
               public int getSize() {
                  return this.this$0.size();
               }

               @Override
               public java.util.Iterator<V> iterator() {
                  return new java.util.Iterator<V>(this.this$0.entrySet().iterator()) {
                     final java.util.Iterator<Entry<K, V>> $entryIterator;

                     {
                        this.$entryIterator = var1;
                     }

                     @Override
                     public boolean hasNext() {
                        return this.$entryIterator.hasNext();
                     }

                     @Override
                     public V next() {
                        return this.$entryIterator.next().getValue();
                     }

                     @Override
                     public void remove() {
                        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                     }
                  };
               }
            };
         }

         val var1: java.util.Collection = this._values;
         return var1;
      }


   private final var _values: Collection<Any>?

   open fun AbstractMap() {
   }

   private fun implFindEntry(key: Any): kotlin.collections.Map.Entry<Any, Any>? {
      val var3: java.util.Iterator = this.entrySet().iterator();

      while (true) {
         if (var3.hasNext()) {
            val var2: Any = var3.next();
            if (!((var2 as Entry).getKey() == var1)) {
               continue;
            }

            var1 = var2;
            break;
         }

         var1 = null;
         break;
      }

      return var1 as MutableMap.MutableEntry<K, V>;
   }

   private fun toString(o: Any?): String {
      if (var1 === this) {
         var1 = "(this Map)";
      } else {
         var1 = java.lang.String.valueOf(var1);
      }

      return var1;
   }

   private fun toString(entry: kotlin.collections.Map.Entry<Any, Any>): String {
      val var2: StringBuilder = new StringBuilder();
      var2.append(this.toString(var1.getKey()));
      var2.append('=');
      var2.append(this.toString(var1.getValue()));
      return var2.toString();
   }

   @JvmStatic
   fun `toString$lambda$2`(var0: AbstractMap, var1: Entry): java.lang.CharSequence {
      return var0.toString(var1);
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   internal fun containsEntry(entry: kotlin.collections.Map.Entry<*, *>?): Boolean {
      if (var1 == null) {
         return false;
      } else {
         val var2: Any = var1.getKey();
         val var4: Any = var1.getValue();
         val var3: java.util.Map = this;
         val var5: Any = var3.get(var2);
         if (!(var4 == var5)) {
            return false;
         } else {
            if (var5 == null) {
               if (!var3.containsKey(var2)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public override fun containsKey(key: Any): Boolean {
      val var2: Boolean;
      if (this.implFindEntry((K)var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun containsValue(value: Any): Boolean {
      val var4: java.lang.Iterable = this.entrySet();
      var var2: Boolean = var4 is java.util.Collection;
      val var3: Boolean = false;
      if (var2 && (var4 as java.util.Collection).isEmpty()) {
         var2 = false;
      } else {
         val var6: java.util.Iterator = var4.iterator();

         while (true) {
            var2 = var3;
            if (!var6.hasNext()) {
               break;
            }

            if ((var6.next() as Entry).getValue() == var1) {
               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var4: Boolean = true;
      if (var1 === this) {
         return true;
      } else if (var1 !is java.util.Map) {
         return false;
      } else {
         val var2: Int = this.size();
         var1 = var1;
         if (var2 != var1.size()) {
            return false;
         } else {
            val var6: java.lang.Iterable = var1.entrySet();
            var var3: Boolean;
            if (var6 is java.util.Collection && (var6 as java.util.Collection).isEmpty()) {
               var3 = true;
            } else {
               val var7: java.util.Iterator = var6.iterator();

               while (true) {
                  var3 = var4;
                  if (!var7.hasNext()) {
                     break;
                  }

                  if (!this.containsEntry$kotlin_stdlib(var7.next() as MutableMap.MutableEntry<*, *>)) {
                     var3 = false;
                     break;
                  }
               }
            }

            return var3;
         }
      }
   }

   public override operator fun get(key: Any): Any? {
      var1 = this.implFindEntry((K)var1);
      if (var1 != null) {
         var1 = var1.getValue();
      } else {
         var1 = null;
      }

      return (V)var1;
   }

   abstract fun getEntries(): java.util.Set

   public override fun hashCode(): Int {
      return this.entrySet().hashCode();
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   override fun put(var1: K, var2: V): V {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun putAll(var1: MutableMap<K, V>) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun remove(var1: Any): V {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun toString(): String {
      return CollectionsKt.joinToString$default(this.entrySet(), ", ", "{", "}", 0, null, new AbstractMap$$ExternalSyntheticLambda0(this), 24, null);
   }

   internal companion object {
      internal fun entryEquals(e: kotlin.collections.Map.Entry<*, *>, other: Any?): Boolean {
         if (var2 !is Entry) {
            return false;
         } else {
            val var5: Any = var1.getKey();
            var2 = var2;
            var var7: Boolean = false;
            if (var5 == var2.getKey()) {
               var7 = false;
               if (var1.getValue() == var2.getValue()) {
                  var7 = true;
               }
            }

            return var7;
         }
      }

      internal fun entryHashCode(e: kotlin.collections.Map.Entry<*, *>): Int {
         val var4: Any = var1.getKey();
         var var3: Int = 0;
         val var2: Int;
         if (var4 != null) {
            var2 = var4.hashCode();
         } else {
            var2 = 0;
         }

         val var5: Any = var1.getValue();
         if (var5 != null) {
            var3 = var5.hashCode();
         }

         return var2 xor var3;
      }

      internal fun entryToString(e: kotlin.collections.Map.Entry<*, *>): String {
         val var2: StringBuilder = new StringBuilder();
         var2.append(var1.getKey());
         var2.append('=');
         var2.append(var1.getValue());
         return var2.toString();
      }
   }
}
