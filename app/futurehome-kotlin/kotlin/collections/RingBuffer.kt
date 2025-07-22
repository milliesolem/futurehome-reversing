package kotlin.collections

import java.util.Arrays
import java.util.RandomAccess

private class RingBuffer<T>(vararg buffer: Any, filledSize: Int) : AbstractList<T>, RandomAccess {
   private final val buffer: Array<Any?>
   private final val capacity: Int
   private final var startIndex: Int

   public open var size: Int
      private set

   public constructor(capacity: Int) : this(new Object[var1], 0)
   init {
      this.buffer = var1;
      if (var2 >= 0) {
         if (var2 <= var1.length) {
            this.capacity = var1.length;
            this.size = var2;
         } else {
            val var3: StringBuilder = new StringBuilder("ring buffer filled size: ");
            var3.append(var2);
            var3.append(" cannot be larger than the buffer size: ");
            var3.append(var1.length);
            throw new IllegalArgumentException(var3.toString().toString());
         }
      } else {
         val var4: StringBuilder = new StringBuilder("ring buffer filled size should not be negative but it is ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   private inline fun Int.forward(n: Int): Int {
      return (var1 + var2) % access$getCapacity$p(this);
   }

   public fun add(element: Any) {
      if (!this.isFull()) {
         this.buffer[(this.startIndex + this.size()) % access$getCapacity$p(this)] = var1;
         this.size = this.size() + 1;
      } else {
         throw new IllegalStateException("ring buffer is full");
      }
   }

   public fun expanded(maxCapacity: Int): RingBuffer<Any> {
      var1 = RangesKt.coerceAtMost(this.capacity + (this.capacity shr 1) + 1, var1);
      val var3: Array<Any>;
      if (this.startIndex == 0) {
         var3 = Arrays.copyOf(this.buffer, var1);
      } else {
         var3 = this.toArray(new Object[var1]);
      }

      return new RingBuffer<>(var3, this.size());
   }

   public override operator fun get(index: Int): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.size());
      return (T)this.buffer[(this.startIndex + var1) % access$getCapacity$p(this)];
   }

   public fun isFull(): Boolean {
      val var1: Boolean;
      if (this.size() == this.capacity) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new AbstractIterator<T>(this) {
         private int count;
         private int index;
         final RingBuffer<T> this$0;

         {
            this.this$0 = var1;
            this.count = var1.size();
            this.index = RingBuffer.access$getStartIndex$p(var1);
         }

         @Override
         protected void computeNext() {
            if (this.count == 0) {
               this.done();
            } else {
               this.setNext((T)RingBuffer.access$getBuffer$p(this.this$0)[this.index]);
               this.index = (this.index + 1) % RingBuffer.access$getCapacity$p(this.this$0);
               this.count--;
            }
         }
      };
   }

   public fun removeFirst(n: Int) {
      if (var1 >= 0) {
         if (var1 <= this.size()) {
            if (var1 > 0) {
               val var2: Int = this.startIndex;
               val var3: Int = (this.startIndex + var1) % access$getCapacity$p(this);
               if (var2 > var3) {
                  ArraysKt.fill(this.buffer, null, var2, this.capacity);
                  ArraysKt.fill(this.buffer, null, 0, var3);
               } else {
                  ArraysKt.fill(this.buffer, null, var2, var3);
               }

               this.startIndex = var3;
               this.size = this.size() - var1;
            }
         } else {
            val var5: StringBuilder = new StringBuilder("n shouldn't be greater than the buffer size: n = ");
            var5.append(var1);
            var5.append(", size = ");
            var5.append(this.size());
            throw new IllegalArgumentException(var5.toString().toString());
         }
      } else {
         val var4: StringBuilder = new StringBuilder("n shouldn't be negative but it is ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   protected override fun toArray(): Array<Any?> {
      return this.toArray(new Object[this.size()]);
   }

   protected override fun <T> toArray(array: Array<T>): Array<T> {
      var var8: Array<Any> = var1;
      if (var1.length < this.size()) {
         var8 = Arrays.copyOf(var1, this.size());
      }

      val var7: Int = this.size();
      var var3: Int = this.startIndex;
      val var6: Byte = 0;
      var var2: Int = 0;

      var var4: Int;
      var var5: Int;
      while (true) {
         var4 = var6;
         var5 = var2;
         if (var2 >= var7) {
            break;
         }

         var4 = var6;
         var5 = var2;
         if (var3 >= this.capacity) {
            break;
         }

         var8[var2] = this.buffer[var3];
         var2++;
         var3++;
      }

      while (var5 < var7) {
         var8[var5] = this.buffer[var4];
         var5++;
         var4++;
      }

      return (T[])CollectionsKt.terminateCollectionToArray(var7, var8);
   }
}
