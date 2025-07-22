package kotlin.sequences

import java.util.NoSuchElementException

internal class SubSequence<T>(sequence: Sequence<Any>, startIndex: Int, endIndex: Int) : Sequence<T>, DropTakeSequence<T> {
   private final val sequence: Sequence<Any>
   private final val startIndex: Int
   private final val endIndex: Int

   private final val count: Int
      private final get() {
         return this.endIndex - this.startIndex;
      }


   init {
      this.sequence = var1;
      this.startIndex = var2;
      this.endIndex = var3;
      if (var2 >= 0) {
         if (var3 >= 0) {
            if (var3 < var2) {
               val var6: StringBuilder = new StringBuilder("endIndex should be not less than startIndex, but was ");
               var6.append(var3);
               var6.append(" < ");
               var6.append(var2);
               throw new IllegalArgumentException(var6.toString().toString());
            }
         } else {
            val var5: StringBuilder = new StringBuilder("endIndex should be non-negative, but is ");
            var5.append(var3);
            throw new IllegalArgumentException(var5.toString().toString());
         }
      } else {
         val var4: StringBuilder = new StringBuilder("startIndex should be non-negative, but is ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   public override fun drop(n: Int): Sequence<Any> {
      val var2: Sequence;
      if (var1 >= this.getCount()) {
         var2 = SequencesKt.emptySequence();
      } else {
         var2 = new SubSequence<>(this.sequence, this.startIndex + var1, this.endIndex);
      }

      return var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<T>(this) {
         private final java.util.Iterator<T> iterator;
         private int position;
         final SubSequence<T> this$0;

         {
            this.this$0 = var1;
            this.iterator = SubSequence.access$getSequence$p(var1).iterator();
         }

         private final void drop() {
            while (this.position < SubSequence.access$getStartIndex$p(this.this$0) && this.iterator.hasNext()) {
               this.iterator.next();
               this.position++;
            }
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getPosition() {
            return this.position;
         }

         @Override
         public boolean hasNext() {
            this.drop();
            val var1: Boolean;
            if (this.position < SubSequence.access$getEndIndex$p(this.this$0) && this.iterator.hasNext()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public T next() {
            this.drop();
            if (this.position < SubSequence.access$getEndIndex$p(this.this$0)) {
               this.position++;
               return this.iterator.next();
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setPosition(int var1) {
            this.position = var1;
         }
      };
   }

   public override fun take(n: Int): Sequence<Any> {
      val var3: Sequence;
      if (var1 >= this.getCount()) {
         var3 = this;
      } else {
         var3 = new SubSequence<>(this.sequence, this.startIndex, var1 + this.startIndex);
      }

      return var3;
   }
}
