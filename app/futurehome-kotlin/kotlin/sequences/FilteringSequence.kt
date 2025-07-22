package kotlin.sequences

import java.util.NoSuchElementException

internal class FilteringSequence<T>(sequence: Sequence<Any>, sendWhen: Boolean = true, predicate: (Any) -> Boolean) : Sequence<T> {
   private final val sequence: Sequence<Any>
   private final val sendWhen: Boolean
   private final val predicate: (Any) -> Boolean

   init {
      this.sequence = var1;
      this.sendWhen = var2;
      this.predicate = var3;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<T>(this) {
         private final java.util.Iterator<T> iterator;
         private T nextItem;
         private int nextState;
         final FilteringSequence<T> this$0;

         {
            this.this$0 = var1;
            this.iterator = FilteringSequence.access$getSequence$p(var1).iterator();
            this.nextState = -1;
         }

         private final void calcNext() {
            while (this.iterator.hasNext()) {
               val var1: Any = this.iterator.next();
               if (FilteringSequence.access$getPredicate$p(this.this$0).invoke(var1) as java.lang.Boolean
                  == FilteringSequence.access$getSendWhen$p(this.this$0)) {
                  this.nextItem = (T)var1;
                  this.nextState = 1;
                  return;
               }
            }

            this.nextState = 0;
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         public final T getNextItem() {
            return this.nextItem;
         }

         public final int getNextState() {
            return this.nextState;
         }

         @Override
         public boolean hasNext() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            var var2: Boolean = true;
            if (this.nextState != 1) {
               var2 = false;
            }

            return var2;
         }

         @Override
         public T next() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            if (this.nextState != 0) {
               val var1: Any = this.nextItem;
               this.nextItem = null;
               this.nextState = -1;
               return (T)var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setNextItem(T var1) {
            this.nextItem = (T)var1;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }
      };
   }
}
