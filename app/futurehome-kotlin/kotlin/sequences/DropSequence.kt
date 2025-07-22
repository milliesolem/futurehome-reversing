package kotlin.sequences

internal class DropSequence<T>(sequence: Sequence<Any>, count: Int) : Sequence<T>, DropTakeSequence<T> {
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
      val var2: Int = this.count + var1;
      val var3: DropSequence;
      if (this.count + var1 < 0) {
         var3 = new DropSequence<>(this, var1);
      } else {
         var3 = new DropSequence<>(this.sequence, var2);
      }

      return var3;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<T>(this) {
         private final java.util.Iterator<T> iterator;
         private int left;

         {
            this.iterator = DropSequence.access$getSequence$p(var1).iterator();
            this.left = DropSequence.access$getCount$p(var1);
         }

         private final void drop() {
            while (this.left > 0 && this.iterator.hasNext()) {
               this.iterator.next();
               this.left--;
            }
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getLeft() {
            return this.left;
         }

         @Override
         public boolean hasNext() {
            this.drop();
            return this.iterator.hasNext();
         }

         @Override
         public T next() {
            this.drop();
            return this.iterator.next();
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
      val var2: Int = this.count + var1;
      val var3: DropTakeSequence;
      if (this.count + var1 < 0) {
         var3 = new TakeSequence<>(this, var1);
      } else {
         var3 = new SubSequence<>(this.sequence, this.count, var2);
      }

      return var3;
   }
}
