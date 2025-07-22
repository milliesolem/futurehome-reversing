package kotlin.sequences

internal class DropWhileSequence<T>(sequence: Sequence<Any>, predicate: (Any) -> Boolean) : Sequence<T> {
   private final val sequence: Sequence<Any>
   private final val predicate: (Any) -> Boolean

   init {
      this.sequence = var1;
      this.predicate = var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<T>(this) {
         private int dropState;
         private final java.util.Iterator<T> iterator;
         private T nextItem;
         final DropWhileSequence<T> this$0;

         {
            this.this$0 = var1;
            this.iterator = DropWhileSequence.access$getSequence$p(var1).iterator();
            this.dropState = -1;
         }

         private final void drop() {
            while (this.iterator.hasNext()) {
               val var1: Any = this.iterator.next();
               if (!DropWhileSequence.access$getPredicate$p(this.this$0).invoke(var1) as java.lang.Boolean) {
                  this.nextItem = (T)var1;
                  this.dropState = 1;
                  return;
               }
            }

            this.dropState = 0;
         }

         public final int getDropState() {
            return this.dropState;
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         public final T getNextItem() {
            return this.nextItem;
         }

         @Override
         public boolean hasNext() {
            if (this.dropState == -1) {
               this.drop();
            }

            var var2: Boolean = true;
            if (this.dropState != 1) {
               if (this.iterator.hasNext()) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            }

            return var2;
         }

         @Override
         public T next() {
            if (this.dropState == -1) {
               this.drop();
            }

            if (this.dropState == 1) {
               val var1: Any = this.nextItem;
               this.nextItem = null;
               this.dropState = 0;
               return (T)var1;
            } else {
               return this.iterator.next();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setDropState(int var1) {
            this.dropState = var1;
         }

         public final void setNextItem(T var1) {
            this.nextItem = (T)var1;
         }
      };
   }
}
