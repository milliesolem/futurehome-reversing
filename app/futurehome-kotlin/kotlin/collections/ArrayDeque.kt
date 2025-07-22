package kotlin.collections

import java.util.NoSuchElementException

public class ArrayDeque<E> : AbstractMutableList<E>, j..util.List {
   private final var head: Int
   private final var elementData: Array<Any?>

   public open var size: Int
      private set

   public constructor()  {
      this.elementData = emptyElementData;
   }

   public constructor(initialCapacity: Int)  {
      val var2: Array<Any>;
      if (var1 == 0) {
         var2 = emptyElementData;
      } else {
         if (var1 <= 0) {
            val var3: StringBuilder = new StringBuilder("Illegal Capacity: ");
            var3.append(var1);
            throw new IllegalArgumentException(var3.toString());
         }

         var2 = new Object[var1];
      }

      this.elementData = var2;
   }

   public constructor(elements: Collection<Any>)  {
      val var2: Array<Any> = var1.toArray(new Object[0]);
      this.elementData = var2;
      this.size = var2.length;
      if (var2.length == 0) {
         this.elementData = emptyElementData;
      }
   }

   private fun copyCollectionElements(internalIndex: Int, elements: Collection<Any>) {
      val var4: java.util.Iterator = var2.iterator();
      var var3: Int = this.elementData.length;

      while (var1 < var3 && var4.hasNext()) {
         this.elementData[var1] = var4.next();
         var1++;
      }

      var3 = this.head;

      for (int var5 = 0; var5 < var3 && var4.hasNext(); var5++) {
         this.elementData[var5] = var4.next();
      }

      this.size = this.size() + var2.size();
   }

   private fun copyElements(newCapacity: Int) {
      val var3: Array<Any> = new Object[var1];
      ArraysKt.copyInto(this.elementData, var3, 0, this.head, this.elementData.length);
      ArraysKt.copyInto(this.elementData, var3, this.elementData.length - this.head, 0, this.head);
      this.head = 0;
      this.elementData = var3;
   }

   private fun decremented(index: Int): Int {
      if (var1 == 0) {
         var1 = ArraysKt.getLastIndex(this.elementData);
      } else {
         var1--;
      }

      return var1;
   }

   private fun ensureCapacity(minCapacity: Int) {
      if (var1 >= 0) {
         if (var1 > this.elementData.length) {
            if (this.elementData === emptyElementData) {
               this.elementData = new Object[RangesKt.coerceAtLeast(var1, 10)];
            } else {
               this.copyElements(AbstractList.Companion.newCapacity$kotlin_stdlib(this.elementData.length, var1));
            }
         }
      } else {
         throw new IllegalStateException("Deque is too big.");
      }
   }

   private inline fun filterInPlace(predicate: (Any) -> Boolean): Boolean {
      val var10: Boolean = this.isEmpty();
      var var8: Boolean = false;
      if (!var10) {
         if (this.elementData.length == 0) {
            var8 = false;
         } else {
            val var5: Int = this.positiveMod(this.head + this.size());
            var var2: Int = this.head;
            var var16: Boolean;
            if (this.head < var5) {
               var var15: Int = this.head;

               for (var16 = false; var2 < var5; var2++) {
                  val var18: Any = this.elementData[var2];
                  if (var1.invoke(this.elementData[var2]) as java.lang.Boolean) {
                     this.elementData[var15] = var18;
                     var15++;
                  } else {
                     var16 = true;
                  }
               }

               ArraysKt.fill(this.elementData, null, var15, var5);
               var2 = var15;
            } else {
               val var6: Int = this.elementData.length;
               var var3: Int = this.head;

               for (var16 = false; var2 < var6; var2++) {
                  val var11: Any = this.elementData[var2];
                  this.elementData[var2] = null;
                  if (var1.invoke(var11) as java.lang.Boolean) {
                     this.elementData[var3] = var11;
                     var3++;
                  } else {
                     var16 = true;
                  }
               }

               var2 = this.positiveMod(var3);

               for (int var14 = 0; var14 < var5; var14++) {
                  val var17: Any = this.elementData[var14];
                  this.elementData[var14] = null;
                  if (var1.invoke(var17) as java.lang.Boolean) {
                     this.elementData[var2] = var17;
                     var2 = this.incremented(var2);
                  } else {
                     var16 = true;
                  }
               }
            }

            var8 = var16;
            if (var16) {
               this.registerModification();
               this.size = this.negativeMod(var2 - this.head);
               var8 = var16;
            }
         }
      }

      return var8;
   }

   private fun incremented(index: Int): Int {
      if (var1 == ArraysKt.getLastIndex(this.elementData)) {
         var1 = 0;
      } else {
         var1++;
      }

      return var1;
   }

   private inline fun internalGet(internalIndex: Int): Any {
      return (E)this.elementData[var1];
   }

   private inline fun internalIndex(index: Int): Int {
      return this.positiveMod(this.head + var1);
   }

   private fun negativeMod(index: Int): Int {
      var var2: Int = var1;
      if (var1 < 0) {
         var2 = var1 + this.elementData.length;
      }

      return var2;
   }

   private fun nullifyNonEmpty(internalFromIndex: Int, internalToIndex: Int) {
      if (var1 < var2) {
         ArraysKt.fill(this.elementData, null, var1, var2);
      } else {
         ArraysKt.fill(this.elementData, null, var1, this.elementData.length);
         ArraysKt.fill(this.elementData, null, 0, var2);
      }
   }

   private fun positiveMod(index: Int): Int {
      var var2: Int = var1;
      if (var1 >= this.elementData.length) {
         var2 = var1 - this.elementData.length;
      }

      return var2;
   }

   private fun registerModification() {
      this.modCount++;
   }

   private fun removeRangeShiftPreceding(fromIndex: Int, toIndex: Int) {
      var var4: Int = this.positiveMod(this.head + (var1 - 1));
      var var3: Int = this.positiveMod(this.head + (var2 - 1));
      var2 = var4;

      while (var1 > 0) {
         val var5: Int = var2 + 1;
         var4 = Math.min(var1, Math.min(var2 + 1, var3 + 1));
         var3 = var3 - var4;
         val var8: Int = var2 - var4;
         ArraysKt.copyInto(this.elementData, this.elementData, var3 + 1, var2 - var4 + 1, var5);
         var2 = this.negativeMod(var8);
         var3 = this.negativeMod(var3);
         var1 -= var4;
      }
   }

   private fun removeRangeShiftSucceeding(fromIndex: Int, toIndex: Int) {
      var var5: Int = this.positiveMod(this.head + var2);
      var1 = this.positiveMod(this.head + var1);
      var var4: Int = this.size();
      var var3: Int = var2;
      var2 = var5;

      while (true) {
         var4 -= var3;
         if (var4 <= 0) {
            return;
         }

         var3 = Math.min(var4, Math.min(this.elementData.length - var2, this.elementData.length - var1));
         var5 = var2 + var3;
         ArraysKt.copyInto(this.elementData, this.elementData, var1, var2, var2 + var3);
         var2 = this.positiveMod(var5);
         var1 = this.positiveMod(var1 + var3);
      }
   }

   public override fun add(index: Int, element: Any) {
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.size());
      if (var1 == this.size()) {
         this.addLast((E)var2);
      } else if (var1 == 0) {
         this.addFirst((E)var2);
      } else {
         this.registerModification();
         this.ensureCapacity(this.size() + 1);
         var var3: Int = this.positiveMod(this.head + var1);
         if (var1 < this.size() + 1 shr 1) {
            var3 = this.decremented(var3);
            val var4: Int = this.decremented(this.head);
            if (var3 >= this.head) {
               this.elementData[var4] = this.elementData[this.head];
               ArraysKt.copyInto(this.elementData, this.elementData, this.head, this.head + 1, var3 + 1);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, this.head - 1, this.head, this.elementData.length);
               this.elementData[this.elementData.length - 1] = this.elementData[0];
               ArraysKt.copyInto(this.elementData, this.elementData, 0, 1, var3 + 1);
            }

            this.elementData[var3] = var2;
            this.head = var4;
         } else {
            var1 = this.positiveMod(this.head + this.size());
            if (var3 < var1) {
               ArraysKt.copyInto(this.elementData, this.elementData, var3 + 1, var3, var1);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, 1, 0, var1);
               this.elementData[0] = this.elementData[this.elementData.length - 1];
               ArraysKt.copyInto(this.elementData, this.elementData, var3 + 1, var3, this.elementData.length - 1);
            }

            this.elementData[var3] = var2;
         }

         this.size = this.size() + 1;
      }
   }

   public override fun add(element: Any): Boolean {
      this.addLast((E)var1);
      return true;
   }

   public override fun addAll(index: Int, elements: Collection<Any>): Boolean {
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.size());
      if (var2.isEmpty()) {
         return false;
      } else if (var1 == this.size()) {
         return this.addAll(var2);
      } else {
         this.registerModification();
         this.ensureCapacity(this.size() + var2.size());
         val var5: Int = this.positiveMod(this.head + this.size());
         val var3: Int = this.positiveMod(this.head + var1);
         var var4: Int = var2.size();
         if (var1 < this.size() + 1 shr 1) {
            var1 = this.head - var4;
            if (var3 >= this.head) {
               if (var1 >= 0) {
                  ArraysKt.copyInto(this.elementData, this.elementData, var1, this.head, var3);
               } else {
                  var1 += this.elementData.length;
                  val var6: Int = this.elementData.length - var1;
                  if (this.elementData.length - var1 >= var3 - this.head) {
                     ArraysKt.copyInto(this.elementData, this.elementData, var1, this.head, var3);
                  } else {
                     ArraysKt.copyInto(this.elementData, this.elementData, var1, this.head, this.head + var6);
                     ArraysKt.copyInto(this.elementData, this.elementData, 0, this.head + var6, var3);
                  }
               }
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, var1, this.head, this.elementData.length);
               if (var4 >= var3) {
                  ArraysKt.copyInto(this.elementData, this.elementData, this.elementData.length - var4, 0, var3);
               } else {
                  ArraysKt.copyInto(this.elementData, this.elementData, this.elementData.length - var4, 0, var4);
                  ArraysKt.copyInto(this.elementData, this.elementData, 0, var4, var3);
               }
            }

            this.head = var1;
            this.copyCollectionElements(this.negativeMod(var3 - var4), var2);
         } else {
            var1 = var3 + var4;
            if (var3 < var5) {
               var4 = var4 + var5;
               if (var4 + var5 <= this.elementData.length) {
                  ArraysKt.copyInto(this.elementData, this.elementData, var1, var3, var5);
               } else if (var1 >= this.elementData.length) {
                  ArraysKt.copyInto(this.elementData, this.elementData, var1 - this.elementData.length, var3, var5);
               } else {
                  val var11: Int = var5 - (var4 - this.elementData.length);
                  ArraysKt.copyInto(this.elementData, this.elementData, 0, var5 - (var4 - this.elementData.length), var5);
                  ArraysKt.copyInto(this.elementData, this.elementData, var1, var3, var11);
               }
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, var4, 0, var5);
               if (var1 >= this.elementData.length) {
                  ArraysKt.copyInto(this.elementData, this.elementData, var1 - this.elementData.length, var3, this.elementData.length);
               } else {
                  ArraysKt.copyInto(this.elementData, this.elementData, 0, this.elementData.length - var4, this.elementData.length);
                  ArraysKt.copyInto(this.elementData, this.elementData, var1, var3, this.elementData.length - var4);
               }
            }

            this.copyCollectionElements(var3, var2);
         }

         return true;
      }
   }

   public override fun addAll(elements: Collection<Any>): Boolean {
      if (var1.isEmpty()) {
         return false;
      } else {
         this.registerModification();
         this.ensureCapacity(this.size() + var1.size());
         this.copyCollectionElements(this.positiveMod(this.head + this.size()), var1);
         return true;
      }
   }

   public override fun addFirst(element: Any) {
      this.registerModification();
      this.ensureCapacity(this.size() + 1);
      val var2: Int = this.decremented(this.head);
      this.head = var2;
      this.elementData[var2] = var1;
      this.size = this.size() + 1;
   }

   public override fun addLast(element: Any) {
      this.registerModification();
      this.ensureCapacity(this.size() + 1);
      this.elementData[this.positiveMod(this.head + this.size())] = var1;
      this.size = this.size() + 1;
   }

   public override fun clear() {
      if (!this.isEmpty()) {
         this.registerModification();
         this.nullifyNonEmpty(this.head, this.positiveMod(this.head + this.size()));
      }

      this.head = 0;
      this.size = 0;
   }

   public override operator fun contains(element: Any): Boolean {
      val var2: Boolean;
      if (this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public fun first(): Any {
      if (!this.isEmpty()) {
         return (E)this.elementData[this.head];
      } else {
         throw new NoSuchElementException("ArrayDeque is empty.");
      }
   }

   public fun firstOrNull(): Any? {
      val var1: Any;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.elementData[this.head];
      }

      return (E)var1;
   }

   public override operator fun get(index: Int): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      return (E)this.elementData[this.positiveMod(this.head + var1)];
   }

   public override fun indexOf(element: Any): Int {
      val var3: Int = this.positiveMod(this.head + this.size());
      var var2: Int = this.head;
      if (this.head < var3) {
         while (var2 < var3) {
            if (var1 == this.elementData[var2]) {
               return var2 - this.head;
            }

            var2++;
         }
      } else if (this.head >= var3) {
         for (int var4 = this.elementData.length; var2 < var4; var2++) {
            if (var1 == this.elementData[var2]) {
               return var2 - this.head;
            }
         }

         for (int var5 = 0; var5 < var3; var5++) {
            if (var1 == this.elementData[var5]) {
               return var5 + this.elementData.length - this.head;
            }
         }
      }

      return -1;
   }

   internal fun internalStructure(structure: (Int, Array<Any?>) -> Unit) {
      var var4: Int;
      label12:
      if (!this.isEmpty() && this.head >= this.positiveMod(this.head + this.size())) {
         var4 = this.head - this.elementData.length;
         break label12;
      } else {
         var4 = this.head;
      }

      var1.invoke(var4, this.toArray());
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

   public fun last(): Any {
      if (!this.isEmpty()) {
         return (E)this.elementData[this.positiveMod(this.head + CollectionsKt.getLastIndex(this))];
      } else {
         throw new NoSuchElementException("ArrayDeque is empty.");
      }
   }

   public override fun lastIndexOf(element: Any): Int {
      var var2: Int = this.positiveMod(this.head + this.size());
      var var3: Int = this.head;
      if (this.head < var2) {
         if (this.head <= --var2) {
            while (true) {
               if (var1 == this.elementData[var2]) {
                  return var2 - this.head;
               }

               if (var2 == var3) {
                  break;
               }

               var2--;
            }
         }
      } else if (this.head > var2) {
         var2--;

         while (-1 < var2) {
            if (var1 == this.elementData[var2]) {
               return var2 + this.elementData.length - this.head;
            }

            var2--;
         }

         var2 = ArraysKt.getLastIndex(this.elementData);
         var3 = this.head;
         if (this.head <= var2) {
            while (!(var1 == this.elementData[var2])) {
               if (var2 == var3) {
                  return -1;
               }

               var2--;
            }

            return var2 - this.head;
         }
      }

      return -1;
   }

   public fun lastOrNull(): Any? {
      val var1: Any;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.elementData[this.positiveMod(this.head + CollectionsKt.getLastIndex(this))];
      }

      return (E)var1;
   }

   public override fun remove(element: Any): Boolean {
      val var2: Int = this.indexOf(var1);
      if (var2 == -1) {
         return false;
      } else {
         this.remove(var2);
         return true;
      }
   }

   public override fun removeAll(elements: Collection<Any>): Boolean {
      val var10: Boolean = this.isEmpty();
      var var7: Boolean = false;
      var var8: Boolean = false;
      if (!var10) {
         if (this.elementData.length == 0) {
            var8 = false;
         } else {
            val var5: Int = this.positiveMod(this.head + this.size());
            var var2: Int = this.head;
            if (this.head < var5) {
               var var15: Int;
               for (var15 = this.head; var2 < var5; var2++) {
                  val var17: Any = this.elementData[var2];
                  if (!var1.contains(this.elementData[var2])) {
                     this.elementData[var15] = var17;
                     var15++;
                  } else {
                     var7 = true;
                  }
               }

               ArraysKt.fill(this.elementData, null, var15, var5);
               var2 = var15;
            } else {
               val var6: Int = this.elementData.length;
               var var3: Int = this.head;

               for (var7 = false; var2 < var6; var2++) {
                  val var12: Any = this.elementData[var2];
                  this.elementData[var2] = null;
                  if (!var1.contains(var12)) {
                     this.elementData[var3] = var12;
                     var3++;
                  } else {
                     var7 = true;
                  }
               }

               var2 = this.positiveMod(var3);

               for (int var14 = 0; var14 < var5; var14++) {
                  val var18: Any = this.elementData[var14];
                  this.elementData[var14] = null;
                  if (!var1.contains(var18)) {
                     this.elementData[var2] = var18;
                     var2 = this.incremented(var2);
                  } else {
                     var7 = true;
                  }
               }
            }

            var8 = var7;
            if (var7) {
               this.registerModification();
               this.size = this.negativeMod(var2 - this.head);
               var8 = var7;
            }
         }
      }

      return var8;
   }

   public override fun removeAt(index: Int): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      val var4: java.util.List = this;
      if (var1 == CollectionsKt.getLastIndex(this)) {
         return this.removeLast();
      } else if (var1 == 0) {
         return this.removeFirst();
      } else {
         this.registerModification();
         val var2: Int = this.positiveMod(this.head + var1);
         val var3: Any = this.elementData[var2];
         if (var1 < this.size() shr 1) {
            if (var2 >= this.head) {
               ArraysKt.copyInto(this.elementData, this.elementData, this.head + 1, this.head, var2);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, 1, 0, var2);
               this.elementData[0] = this.elementData[this.elementData.length - 1];
               ArraysKt.copyInto(this.elementData, this.elementData, this.head + 1, this.head, this.elementData.length - 1);
            }

            this.elementData[this.head] = null;
            this.head = this.incremented(this.head);
         } else {
            var1 = this.positiveMod(this.head + CollectionsKt.getLastIndex(var4));
            if (var2 <= var1) {
               ArraysKt.copyInto(this.elementData, this.elementData, var2, var2 + 1, var1 + 1);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, var2, var2 + 1, this.elementData.length);
               this.elementData[this.elementData.length - 1] = this.elementData[0];
               ArraysKt.copyInto(this.elementData, this.elementData, 0, 1, var1 + 1);
            }

            this.elementData[var1] = null;
         }

         this.size = this.size() - 1;
         return (E)var3;
      }
   }

   public override fun removeFirst(): Any {
      if (!this.isEmpty()) {
         this.registerModification();
         val var2: Any = this.elementData[this.head];
         this.elementData[this.head] = null;
         this.head = this.incremented(this.head);
         this.size = this.size() - 1;
         return (E)var2;
      } else {
         throw new NoSuchElementException("ArrayDeque is empty.");
      }
   }

   public fun removeFirstOrNull(): Any? {
      val var1: Any;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.removeFirst();
      }

      return (E)var1;
   }

   public override fun removeLast(): Any {
      if (!this.isEmpty()) {
         this.registerModification();
         val var1: Int = this.positiveMod(this.head + CollectionsKt.getLastIndex(this));
         val var3: Any = this.elementData[var1];
         this.elementData[var1] = null;
         this.size = this.size() - 1;
         return (E)var3;
      } else {
         throw new NoSuchElementException("ArrayDeque is empty.");
      }
   }

   public fun removeLastOrNull(): Any? {
      val var1: Any;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.removeLast();
      }

      return (E)var1;
   }

   protected override fun removeRange(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, this.size());
      val var3: Int = var2 - var1;
      if (var2 - var1 != 0) {
         if (var3 == this.size()) {
            this.clear();
         } else if (var3 == 1) {
            this.remove(var1);
         } else {
            this.registerModification();
            if (var1 < this.size() - var2) {
               this.removeRangeShiftPreceding(var1, var2);
               var1 = this.positiveMod(this.head + var3);
               this.nullifyNonEmpty(this.head, var1);
               this.head = var1;
            } else {
               this.removeRangeShiftSucceeding(var1, var2);
               var1 = this.positiveMod(this.head + this.size());
               this.nullifyNonEmpty(this.negativeMod(var1 - var3), var1);
            }

            this.size = this.size() - var3;
         }
      }
   }

   public override fun retainAll(elements: Collection<Any>): Boolean {
      val var10: Boolean = this.isEmpty();
      var var8: Boolean = false;
      if (!var10) {
         if (this.elementData.length == 0) {
            var8 = false;
         } else {
            val var5: Int = this.positiveMod(this.head + this.size());
            var var2: Int = this.head;
            var var16: Boolean;
            if (this.head < var5) {
               var var15: Int = this.head;

               for (var16 = false; var2 < var5; var2++) {
                  val var18: Any = this.elementData[var2];
                  if (var1.contains(this.elementData[var2])) {
                     this.elementData[var15] = var18;
                     var15++;
                  } else {
                     var16 = true;
                  }
               }

               ArraysKt.fill(this.elementData, null, var15, var5);
               var2 = var15;
            } else {
               val var6: Int = this.elementData.length;
               var var3: Int = this.head;

               for (var16 = false; var2 < var6; var2++) {
                  val var11: Any = this.elementData[var2];
                  this.elementData[var2] = null;
                  if (var1.contains(var11)) {
                     this.elementData[var3] = var11;
                     var3++;
                  } else {
                     var16 = true;
                  }
               }

               var2 = this.positiveMod(var3);

               for (int var14 = 0; var14 < var5; var14++) {
                  val var17: Any = this.elementData[var14];
                  this.elementData[var14] = null;
                  if (var1.contains(var17)) {
                     this.elementData[var2] = var17;
                     var2 = this.incremented(var2);
                  } else {
                     var16 = true;
                  }
               }
            }

            var8 = var16;
            if (var16) {
               this.registerModification();
               this.size = this.negativeMod(var2 - this.head);
               var8 = var16;
            }
         }
      }

      return var8;
   }

   public override operator fun set(index: Int, element: Any): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      var1 = this.positiveMod(this.head + var1);
      val var3: Any = this.elementData[var1];
      this.elementData[var1] = var2;
      return (E)var3;
   }

   internal fun testRemoveRange(fromIndex: Int, toIndex: Int) {
      this.removeRange(var1, var2);
   }

   internal fun testToArray(): Array<Any?> {
      return this.toArray();
   }

   internal fun <T> testToArray(array: Array<T>): Array<T> {
      return (T[])this.toArray(var1);
   }

   public override fun toArray(): Array<Any?> {
      return this.toArray(new Object[this.size()]);
   }

   public override fun <T> toArray(array: Array<T>): Array<T> {
      if (var1.length < this.size()) {
         var1 = ArraysKt.arrayOfNulls(var1, this.size());
      }

      val var2: Int = this.positiveMod(this.head + this.size());
      if (this.head < var2) {
         ArraysKt.copyInto$default(this.elementData, var1, 0, this.head, var2, 2, null);
      } else if (!this.isEmpty()) {
         ArraysKt.copyInto(this.elementData, var1, 0, this.head, this.elementData.length);
         ArraysKt.copyInto(this.elementData, var1, this.elementData.length - this.head, 0, var2);
      }

      return (T[])CollectionsKt.terminateCollectionToArray(this.size(), var1);
   }

   internal companion object {
      private final val emptyElementData: Array<Any?>
      private const val defaultMinCapacity: Int
   }
}
