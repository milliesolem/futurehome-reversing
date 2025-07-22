package kotlin.sequences

import java.util.NoSuchElementException

internal class TakeSequence<T>(sequence: Sequence<Any>, count: Int) : Sequence<T>, DropTakeSequence<T> {
   private final val sequence: Sequence<Any>
   private final val count: Int

   init {
      this.sequence = var1;
      this.count = var2;
      if (var2 < 0) {
         val var3: StringBuilder = new StringBuilder("count must be non-negative, but was ");
         var3.append(var2);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   public override fun drop(n: Int): Sequence<Any> {
      val var2: Sequence;
      if (var1 >= this.count) {
         var2 = SequencesKt.emptySequence();
      } else {
         var2 = new SubSequence<>(this.sequence, var1, this.count);
      }

      return var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<T>(this) {
         private final java.util.Iterator<T> iterator;
         private int left;

         {
            this.left = TakeSequence.access$getCount$p(var1);
            this.iterator = TakeSequence.access$getSequence$p(var1).iterator();
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getLeft() {
            return this.left;
         }

         @Override
         public boolean hasNext() {
            val var1: Boolean;
            if (this.left > 0 && this.iterator.hasNext()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public T next() {
            if (this.left != 0) {
               this.left--;
               return this.iterator.next();
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setLeft(int var1) {
            this.left = var1;
         }
      };
   }

   public override fun take(n: Int): Sequence<Any> {
      val var2: Sequence;
      if (var1 >= this.count) {
         var2 = this;
      } else {
         var2 = new TakeSequence<>(this.sequence, var1);
      }

      return var2;
   }
}
