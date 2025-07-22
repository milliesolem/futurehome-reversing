package kotlin.collections.builders

import java.io.NotSerializableException
import java.io.Serializable
import java.util.Arrays
import java.util.ConcurrentModificationException
import java.util.NoSuchElementException
import kotlin.collections.MutableMap.MutableEntry
import kotlin.jvm.internal.markers.KMutableIterator
import kotlin.jvm.internal.markers.KMutableMap

internal class MapBuilder<K, V> private constructor(vararg keysArray: Any,
      vararg valuesArray: Any,
      presenceArray: IntArray,
      hashArray: IntArray,
      maxProbeDistance: Int,
      length: Int
   ) :
   java.util.Map<K, V>,
   Serializable,
   KMutableMap {
   private final var keysArray: Array<Any>
   private final var valuesArray: Array<Any>?
   private final var presenceArray: IntArray
   private final var hashArray: IntArray
   private final var maxProbeDistance: Int
   private final var length: Int
   private final var hashShift: Int
   private final var modCount: Int

   public open var size: Int
      private set

   private final var keysView: MapBuilderKeys<Any>?
   private final var valuesView: MapBuilderValues<Any>?
   private final var entriesView: MapBuilderEntries<Any, Any>?

   internal final var isReadOnly: Boolean
      private set

   public open val keys: MutableSet<Any>
      public open get() {
         val var3: java.util.Set;
         if (this.keysView == null) {
            val var2: MapBuilderKeys = new MapBuilderKeys<>(this);
            this.keysView = var2;
            var3 = var2;
         } else {
            var3 = this.keysView;
         }

         return var3;
      }


   public open val values: MutableCollection<Any>
      public open get() {
         val var3: java.util.Collection;
         if (this.valuesView == null) {
            val var2: MapBuilderValues = new MapBuilderValues<>(this);
            this.valuesView = var2;
            var3 = var2;
         } else {
            var3 = this.valuesView;
         }

         return var3;
      }


   public open val entries: MutableSet<MutableEntry<Any, Any>>
      public open get() {
         if (this.entriesView == null) {
            val var2: MapBuilderEntries = new MapBuilderEntries<>(this);
            this.entriesView = var2;
            return var2;
         } else {
            return this.entriesView as MutableSet<MutableMap.MutableEntry<K, V>>;
         }
      }


   internal final val capacity: Int
      internal final get() {
         return this.keysArray.length;
      }


   private final val hashSize: Int
      private final get() {
         return this.hashArray.length;
      }


   @JvmStatic
   fun {
      val var0: MapBuilder = new MapBuilder(0);
      var0.isReadOnly = true;
      Empty = var0;
   }

   public constructor() : this(8)
   public constructor(initialCapacity: Int) : this(
         (K[])ListBuilderKt.arrayOfUninitializedElements(var1),
         null,
         new int[var1],
         new int[MapBuilder.Companion.access$computeHashSize(Companion, var1)],
         2,
         0
      )
   init {
      this.keysArray = (K[])var1;
      this.valuesArray = (V[])var2;
      this.presenceArray = var3;
      this.hashArray = var4;
      this.maxProbeDistance = var5;
      this.length = var6;
      this.hashShift = MapBuilder.Companion.access$computeShift(Companion, this.getHashSize());
   }

   private fun allocateValuesArray(): Array<Any> {
      if (this.valuesArray != null) {
         return this.valuesArray;
      } else {
         val var2: Array<Any> = ListBuilderKt.arrayOfUninitializedElements(this.getCapacity$kotlin_stdlib());
         this.valuesArray = (V[])var2;
         return (V[])var2;
      }
   }

   private fun compact(updateHashArray: Boolean) {
      val var7: Array<Any> = this.valuesArray;
      var var2: Int = 0;
      var var3: Int = 0;

      while (true) {
         if (var2 >= this.length) {
            ListBuilderKt.resetRange(this.keysArray, var3, this.length);
            if (var7 != null) {
               ListBuilderKt.resetRange(var7, var3, this.length);
            }

            this.length = var3;
            return;
         }

         val var5: Int = this.presenceArray[var2];
         var var9: Int = var3;
         if (var5 >= 0) {
            this.keysArray[var3] = this.keysArray[var2];
            if (var7 != null) {
               var7[var3] = var7[var2];
            }

            if (var1) {
               this.presenceArray[var3] = var5;
               this.hashArray[var5] = var3 + 1;
            }

            var9 = var3 + 1;
         }

         var2++;
         var3 = var9;
      }
   }

   private fun contentEquals(other: Map<*, *>): Boolean {
      val var2: Boolean;
      if (this.size() == var1.size() && this.containsAllEntries$kotlin_stdlib(var1.entrySet())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private fun ensureCapacity(minCapacity: Int) {
      if (var1 >= 0) {
         if (var1 > this.getCapacity$kotlin_stdlib()) {
            var1 = AbstractList.Companion.newCapacity$kotlin_stdlib(this.getCapacity$kotlin_stdlib(), var1);
            this.keysArray = ListBuilderKt.copyOfUninitializedElements(this.keysArray, var1);
            val var5: Array<Any>;
            if (this.valuesArray != null) {
               var5 = ListBuilderKt.copyOfUninitializedElements(this.valuesArray, var1);
            } else {
               var5 = null;
            }

            this.valuesArray = (V[])var5;
            val var6: IntArray = Arrays.copyOf(this.presenceArray, var1);
            this.presenceArray = var6;
            var1 = MapBuilder.Companion.access$computeHashSize(Companion, var1);
            if (var1 > this.getHashSize()) {
               this.rehash(var1);
            }
         }
      } else {
         throw new OutOfMemoryError();
      }
   }

   private fun ensureExtraCapacity(n: Int) {
      if (this.shouldCompact(var1)) {
         this.compact(true);
      } else {
         this.ensureCapacity(this.length + var1);
      }
   }

   private fun findKey(key: Any): Int {
      var var2: Int = this.hash((K)var1);
      val var3: Int = this.maxProbeDistance;

      while (true) {
         val var4: Int = this.hashArray[var2];
         if (this.hashArray[var2] == 0) {
            return -1;
         }

         if (var4 > 0) {
            if (this.keysArray[--var4] == var1) {
               return var4;
            }
         }

         if (--var3 < 0) {
            return -1;
         }

         if (var2 == 0) {
            var2 = this.getHashSize() - 1;
         } else {
            var2--;
         }
      }
   }

   private fun findValue(value: Any): Int {
      var var2: Int = this.length;

      while (true) {
         val var3: Int = var2 - 1;
         if (var2 - 1 < 0) {
            return -1;
         }

         var2 = var3;
         if (this.presenceArray[var3] >= 0) {
            val var4: Array<Any> = this.valuesArray;
            var2 = var3;
            if (var4[var3] == var1) {
               return var3;
            }
         }
      }
   }

   private fun hash(key: Any): Int {
      val var2: Int;
      if (var1 != null) {
         var2 = var1.hashCode();
      } else {
         var2 = 0;
      }

      return var2 * -1640531527 ushr this.hashShift;
   }

   private fun putAllEntries(from: Collection<kotlin.collections.Map.Entry<Any, Any>>): Boolean {
      val var3: Boolean = var1.isEmpty();
      var var2: Boolean = false;
      if (var3) {
         return false;
      } else {
         this.ensureExtraCapacity(var1.size());
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            if (this.putEntry(var4.next() as MutableMap.MutableEntry<K, V>)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   private fun putEntry(entry: kotlin.collections.Map.Entry<Any, Any>): Boolean {
      var var2: Int = this.addKey$kotlin_stdlib((K)var1.getKey());
      val var4: Array<Any> = this.allocateValuesArray();
      if (var2 >= 0) {
         var4[var2] = var1.getValue();
         return true;
      } else {
         var2 = -var2 - 1;
         if (!(var1.getValue() == var4[-var2 - 1])) {
            var4[var2] = var1.getValue();
            return true;
         } else {
            return false;
         }
      }
   }

   private fun putRehash(i: Int): Boolean {
      var var2: Int = this.hash(this.keysArray[var1]);
      val var3: Int = this.maxProbeDistance;

      while (true) {
         if (this.hashArray[var2] == 0) {
            this.hashArray[var2] = var1 + 1;
            this.presenceArray[var1] = var2;
            return true;
         }

         if (--var3 < 0) {
            return false;
         }

         if (var2 == 0) {
            var2 = this.getHashSize() - 1;
         } else {
            var2--;
         }
      }
   }

   private fun registerModification() {
      this.modCount++;
   }

   private fun rehash(newHashSize: Int) {
      this.registerModification();
      if (this.length > this.size()) {
         this.compact(false);
      }

      this.hashArray = new int[var1];
      this.hashShift = MapBuilder.Companion.access$computeShift(Companion, var1);

      for (int var5 = 0; var5 < this.length; var5++) {
         if (!this.putRehash(var5)) {
            throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
         }
      }
   }

   private fun removeEntryAt(index: Int) {
      ListBuilderKt.resetAt(this.keysArray, var1);
      if (this.valuesArray != null) {
         ListBuilderKt.resetAt(this.valuesArray, var1);
      }

      this.removeHashAt(this.presenceArray[var1]);
      this.presenceArray[var1] = -1;
      this.size = this.size() - 1;
      this.registerModification();
   }

   private fun removeHashAt(removedHash: Int) {
      var var3: Int = RangesKt.coerceAtMost(this.maxProbeDistance * 2, this.getHashSize() / 2);
      var var2: Int = 0;
      var var4: Int = var1;

      var var5: Int;
      var var10: Int;
      do {
         if (var1 == 0) {
            var1 = this.getHashSize() - 1;
         } else {
            var1--;
         }

         var10 = var2 + 1;
         if (var2 + 1 > this.maxProbeDistance) {
            this.hashArray[var4] = 0;
            return;
         }

         val var7: Int = this.hashArray[var1];
         if (this.hashArray[var1] == 0) {
            this.hashArray[var4] = 0;
            return;
         }

         label30: {
            if (var7 < 0) {
               this.hashArray[var4] = -1;
            } else {
               val var8: Int = var7 - 1;
               var5 = var4;
               var2 = var10;
               if ((this.hash(this.keysArray[var8]) - var1 and this.getHashSize() - 1) < var10) {
                  break label30;
               }

               this.hashArray[var4] = var7;
               this.presenceArray[var8] = var4;
            }

            var5 = var1;
            var2 = 0;
         }

         var10 = var3 - 1;
         var4 = var5;
         var3 = var10;
      } while (var10 >= 0);

      this.hashArray[var5] = -1;
   }

   private fun shouldCompact(extraCapacity: Int): Boolean {
      val var5: Int = this.getCapacity$kotlin_stdlib() - this.length;
      val var6: Int = this.length - this.size();
      val var4: Boolean;
      if (var5 < var1 && var5 + var6 >= var1 && var6 >= this.getCapacity$kotlin_stdlib() / 4) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private fun writeReplace(): Any {
      if (this.isReadOnly) {
         return new SerializedMap(this);
      } else {
         throw new NotSerializableException("The map cannot be serialized while it is being built.");
      }
   }

   internal fun addKey(key: Any): Int {
      this.checkIsMutable$kotlin_stdlib();

      while (true) {
         var var2: Int = this.hash((K)var1);
         var var4: Int = RangesKt.coerceAtMost(this.maxProbeDistance * 2, this.getHashSize() / 2);
         val var3: Int = 0;

         while (true) {
            var var5: Int = this.hashArray[var2];
            if (this.hashArray[var2] <= 0) {
               if (this.length < this.getCapacity$kotlin_stdlib()) {
                  var4 = this.length;
                  var5 = this.length + 1;
                  this.length++;
                  this.keysArray[var4] = (K)var1;
                  this.presenceArray[var4] = var2;
                  this.hashArray[var2] = var5;
                  this.size = this.size() + 1;
                  this.registerModification();
                  if (var3 > this.maxProbeDistance) {
                     this.maxProbeDistance = var3;
                  }

                  return var4;
               }

               this.ensureExtraCapacity(1);
               break;
            }

            if (this.keysArray[var5 - 1] == var1) {
               return -var5;
            }

            if (++var3 > var4) {
               this.rehash(this.getHashSize() * 2);
               break;
            }

            if (var2 == 0) {
               var2 = this.getHashSize() - 1;
            } else {
               var2--;
            }
         }
      }
   }

   public fun build(): Map<Any, Any> {
      this.checkIsMutable$kotlin_stdlib();
      this.isReadOnly = true;
      val var1: java.util.Map;
      if (this.size() > 0) {
         var1 = this;
      } else {
         val var2: MapBuilder = Empty;
         var1 = var2;
      }

      return var1;
   }

   internal fun checkIsMutable() {
      if (this.isReadOnly) {
         throw new UnsupportedOperationException();
      }
   }

   public override fun clear() {
      this.checkIsMutable$kotlin_stdlib();
      val var2: Int = this.length - 1;
      if (this.length - 1 >= 0) {
         var var1: Int = 0;

         while (true) {
            val var3: Int = this.presenceArray[var1];
            if (this.presenceArray[var1] >= 0) {
               this.hashArray[var3] = 0;
               this.presenceArray[var1] = -1;
            }

            if (var1 == var2) {
               break;
            }

            var1++;
         }
      }

      ListBuilderKt.resetRange(this.keysArray, 0, this.length);
      if (this.valuesArray != null) {
         ListBuilderKt.resetRange(this.valuesArray, 0, this.length);
      }

      this.size = 0;
      this.length = 0;
      this.registerModification();
   }

   internal fun containsAllEntries(m: Collection<*>): Boolean {
      val var3: java.util.Iterator = var1.iterator();

      val var2: Boolean;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         val var5: Any = var3.next();
         if (var5 == null) {
            break;
         }

         try {
            var2 = this.containsEntry$kotlin_stdlib(var5 as MutableMap.MutableEntry<K, V>);
         } catch (var4: ClassCastException) {
            break;
         }
      } while (var2);

      return false;
   }

   internal fun containsEntry(entry: kotlin.collections.Map.Entry<Any, Any>): Boolean {
      val var2: Int = this.findKey((K)var1.getKey());
      if (var2 < 0) {
         return false;
      } else {
         val var3: Array<Any> = this.valuesArray;
         return var3[var2] == var1.getValue();
      }
   }

   public override fun containsKey(key: Any): Boolean {
      val var2: Boolean;
      if (this.findKey((K)var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun containsValue(value: Any): Boolean {
      val var2: Boolean;
      if (this.findValue((V)var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   internal fun entriesIterator(): kotlin.collections.builders.MapBuilder.EntriesItr<Any, Any> {
      return new MapBuilder.EntriesItr<>(this);
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 === this || var1 is java.util.Map && this.contentEquals(var1 as MutableMap<*, *>)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override operator fun get(key: Any): Any? {
      val var2: Int = this.findKey((K)var1);
      if (var2 < 0) {
         return null;
      } else {
         var1 = this.valuesArray;
         return (V)var1[var2];
      }
   }

   public override fun hashCode(): Int {
      val var2: MapBuilder.EntriesItr = this.entriesIterator$kotlin_stdlib();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += var2.nextHashCode$kotlin_stdlib();
      }

      return var1;
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

   internal fun keysIterator(): kotlin.collections.builders.MapBuilder.KeysItr<Any, Any> {
      return new MapBuilder.KeysItr<>(this);
   }

   public override fun put(key: Any, value: Any): Any? {
      this.checkIsMutable$kotlin_stdlib();
      val var3: Int = this.addKey$kotlin_stdlib((K)var1);
      var1 = this.allocateValuesArray();
      if (var3 < 0) {
         val var4: Any = var1[-var3 - 1];
         var1[-var3 - 1] = var2;
         return (V)var4;
      } else {
         var1[var3] = var2;
         return null;
      }
   }

   public override fun putAll(from: Map<out Any, Any>) {
      this.checkIsMutable$kotlin_stdlib();
      this.putAllEntries(var1.entrySet());
   }

   public override fun remove(key: Any): Any? {
      this.checkIsMutable$kotlin_stdlib();
      val var2: Int = this.findKey((K)var1);
      if (var2 < 0) {
         return null;
      } else {
         var1 = this.valuesArray;
         var1 = var1[var2];
         this.removeEntryAt(var2);
         return (V)var1;
      }
   }

   internal fun removeEntry(entry: kotlin.collections.Map.Entry<Any, Any>): Boolean {
      this.checkIsMutable$kotlin_stdlib();
      val var2: Int = this.findKey((K)var1.getKey());
      if (var2 < 0) {
         return false;
      } else {
         val var3: Array<Any> = this.valuesArray;
         if (!(var3[var2] == var1.getValue())) {
            return false;
         } else {
            this.removeEntryAt(var2);
            return true;
         }
      }
   }

   internal fun removeKey(key: Any): Boolean {
      this.checkIsMutable$kotlin_stdlib();
      val var2: Int = this.findKey((K)var1);
      if (var2 < 0) {
         return false;
      } else {
         this.removeEntryAt(var2);
         return true;
      }
   }

   internal fun removeValue(element: Any): Boolean {
      this.checkIsMutable$kotlin_stdlib();
      val var2: Int = this.findValue((V)var1);
      if (var2 < 0) {
         return false;
      } else {
         this.removeEntryAt(var2);
         return true;
      }
   }

   public override fun toString(): String {
      val var2: StringBuilder = new StringBuilder(this.size() * 3 + 2);
      var2.append("{");
      val var3: MapBuilder.EntriesItr = this.entriesIterator$kotlin_stdlib();

      for (int var1 = 0; var3.hasNext(); var1++) {
         if (var1 > 0) {
            var2.append(", ");
         }

         var3.nextAppendString(var2);
      }

      var2.append("}");
      val var4: java.lang.String = var2.toString();
      return var4;
   }

   internal fun valuesIterator(): kotlin.collections.builders.MapBuilder.ValuesItr<Any, Any> {
      return new MapBuilder.ValuesItr<>(this);
   }

   internal companion object {
      private const val MAGIC: Int
      private const val INITIAL_CAPACITY: Int
      private const val INITIAL_MAX_PROBE_DISTANCE: Int
      private const val TOMBSTONE: Int
      internal final val Empty: MapBuilder<Nothing, Nothing>

      private fun computeHashSize(capacity: Int): Int {
         return Integer.highestOneBit(RangesKt.coerceAtLeast(var1, 1) * 3);
      }

      private fun computeShift(hashSize: Int): Int {
         return Integer.numberOfLeadingZeros(var1) + 1;
      }
   }

   internal class EntriesItr<K, V>(map: MapBuilder<Any, Any>) : MapBuilder.Itr(var1), java.util.Iterator<java.util.Map.Entry<K, V>>, KMutableIterator {
      public open operator fun next(): kotlin.collections.builders.MapBuilder.EntryRef<Any, Any> {
         this.checkForComodification$kotlin_stdlib();
         if (this.getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
            val var1: Int = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(var1 + 1);
            this.setLastIndex$kotlin_stdlib(var1);
            val var2: MapBuilder.EntryRef = new MapBuilder.EntryRef<>(this.getMap$kotlin_stdlib(), this.getLastIndex$kotlin_stdlib());
            this.initNext$kotlin_stdlib();
            return var2;
         } else {
            throw new NoSuchElementException();
         }
      }

      public fun nextAppendString(sb: StringBuilder) {
         if (this.getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
            val var2: Int = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(var2 + 1);
            this.setLastIndex$kotlin_stdlib(var2);
            var var3: Any = MapBuilder.access$getKeysArray$p(this.getMap$kotlin_stdlib())[this.getLastIndex$kotlin_stdlib()];
            if (var3 === this.getMap$kotlin_stdlib()) {
               var1.append("(this Map)");
            } else {
               var1.append(var3);
            }

            var1.append('=');
            var3 = MapBuilder.access$getValuesArray$p(this.getMap$kotlin_stdlib());
            var3 = ((Object[])var3)[this.getLastIndex$kotlin_stdlib()];
            if (var3 === this.getMap$kotlin_stdlib()) {
               var1.append("(this Map)");
            } else {
               var1.append(var3);
            }

            this.initNext$kotlin_stdlib();
         } else {
            throw new NoSuchElementException();
         }
      }

      internal fun nextHashCode(): Int {
         if (this.getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
            var var1: Int = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(var1 + 1);
            this.setLastIndex$kotlin_stdlib(var1);
            var var3: Any = MapBuilder.access$getKeysArray$p(this.getMap$kotlin_stdlib())[this.getLastIndex$kotlin_stdlib()];
            var var2: Int = 0;
            if (var3 != null) {
               var1 = var3.hashCode();
            } else {
               var1 = 0;
            }

            var3 = MapBuilder.access$getValuesArray$p(this.getMap$kotlin_stdlib());
            var3 = ((Object[])var3)[this.getLastIndex$kotlin_stdlib()];
            if (var3 != null) {
               var2 = var3.hashCode();
            }

            this.initNext$kotlin_stdlib();
            return var1 xor var2;
         } else {
            throw new NoSuchElementException();
         }
      }
   }

   internal class EntryRef<K, V>(map: MapBuilder<Any, Any>, index: Int) : java.util.Map.Entry<K, V>, KMutableMap.Entry {
      private final val map: MapBuilder<Any, Any>
      private final val index: Int
      private final val expectedModCount: Int

      public open val key: Any
         public open get() {
            this.checkForComodification();
            return (K)MapBuilder.access$getKeysArray$p(this.map)[this.index];
         }


      public open val value: Any
         public open get() {
            this.checkForComodification();
            val var1: Array<Any> = MapBuilder.access$getValuesArray$p(this.map);
            return (V)var1[this.index];
         }


      init {
         this.map = var1;
         this.index = var2;
         this.expectedModCount = MapBuilder.access$getModCount$p(var1);
      }

      private fun checkForComodification() {
         if (MapBuilder.access$getModCount$p(this.map) != this.expectedModCount) {
            throw new ConcurrentModificationException("The backing map has been modified after this entry was obtained.");
         }
      }

      public override operator fun equals(other: Any?): Boolean {
         return var1 is java.util.Map.Entry
            && (var1 as java.util.Map.Entry).getKey() == this.getKey()
            && (var1 as java.util.Map.Entry).getValue() == this.getValue();
      }

      public override fun hashCode(): Int {
         var var3: Any = this.getKey();
         var var2: Int = 0;
         val var1: Int;
         if (var3 != null) {
            var1 = var3.hashCode();
         } else {
            var1 = 0;
         }

         var3 = this.getValue();
         if (var3 != null) {
            var2 = var3.hashCode();
         }

         return var1 xor var2;
      }

      public override fun setValue(newValue: Any): Any {
         this.checkForComodification();
         this.map.checkIsMutable$kotlin_stdlib();
         val var4: Array<Any> = MapBuilder.access$allocateValuesArray(this.map);
         val var3: Any = var4[this.index];
         var4[this.index] = var1;
         return (V)var3;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder();
         var1.append(this.getKey());
         var1.append('=');
         var1.append(this.getValue());
         return var1.toString();
      }
   }

   internal open class Itr<K, V>(map: MapBuilder<Any, Any>) {
      internal final val map: MapBuilder<Any, Any>
      internal final var index: Int
      internal final var lastIndex: Int
      private final var expectedModCount: Int

      init {
         this.map = var1;
         this.lastIndex = -1;
         this.expectedModCount = MapBuilder.access$getModCount$p(var1);
         this.initNext$kotlin_stdlib();
      }

      internal fun checkForComodification() {
         if (MapBuilder.access$getModCount$p(this.map) != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      public fun hasNext(): Boolean {
         val var1: Boolean;
         if (this.index < MapBuilder.access$getLength$p(this.map)) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      internal fun initNext() {
         while (this.index < MapBuilder.access$getLength$p(this.map)) {
            if (MapBuilder.access$getPresenceArray$p(this.map)[this.index] < 0) {
               this.index += 1;
               continue;
            }
            break;
         }
      }

      public fun remove() {
         this.checkForComodification$kotlin_stdlib();
         if (this.lastIndex != -1) {
            this.map.checkIsMutable$kotlin_stdlib();
            MapBuilder.access$removeEntryAt(this.map, this.lastIndex);
            this.lastIndex = -1;
            this.expectedModCount = MapBuilder.access$getModCount$p(this.map);
         } else {
            throw new IllegalStateException("Call next() before removing element from the iterator.".toString());
         }
      }
   }

   internal class KeysItr<K, V>(map: MapBuilder<Any, Any>) : MapBuilder.Itr(var1), java.util.Iterator<K>, KMutableIterator {
      public override operator fun next(): Any {
         this.checkForComodification$kotlin_stdlib();
         if (this.getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
            val var1: Int = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(var1 + 1);
            this.setLastIndex$kotlin_stdlib(var1);
            val var2: Any = MapBuilder.access$getKeysArray$p(this.getMap$kotlin_stdlib())[this.getLastIndex$kotlin_stdlib()];
            this.initNext$kotlin_stdlib();
            return (K)var2;
         } else {
            throw new NoSuchElementException();
         }
      }
   }

   internal class ValuesItr<K, V>(map: MapBuilder<Any, Any>) : MapBuilder.Itr(var1), java.util.Iterator<V>, KMutableIterator {
      public override operator fun next(): Any {
         this.checkForComodification$kotlin_stdlib();
         if (this.getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
            val var1: Int = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(var1 + 1);
            this.setLastIndex$kotlin_stdlib(var1);
            var var2: Array<Any> = MapBuilder.access$getValuesArray$p(this.getMap$kotlin_stdlib());
            var2 = (Object[])var2[this.getLastIndex$kotlin_stdlib()];
            this.initNext$kotlin_stdlib();
            return (V)var2;
         } else {
            throw new NoSuchElementException();
         }
      }
   }
}
