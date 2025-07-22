package kotlin.sequences

import java.util.NoSuchElementException

internal class FlatteningSequence<T, R, E>(sequence: Sequence<Any>, transformer: (Any) -> Any, iterator: (Any) -> Iterator<Any>) : Sequence<E> {
   private final val sequence: Sequence<Any>
   private final val transformer: (Any) -> Any
   private final val iterator: (Any) -> Iterator<Any>

   init {
      this.sequence = var1;
      this.transformer = var2;
      this.iterator = var3;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<E>(this) {
         private java.util.Iterator<? extends E> itemIterator;
         private final java.util.Iterator<T> iterator;
         private int state;
         final FlatteningSequence<T, R, E> this$0;

         {
            this.this$0 = var1;
            this.iterator = FlatteningSequence.access$getSequence$p(var1).iterator();
         }

         private final boolean ensureItemIterator() {
            if (this.itemIterator != null && this.itemIterator.hasNext()) {
               this.state = 1;
               return true;
            } else {
               while (this.iterator.hasNext()) {
                  val var3: java.util.Iterator = FlatteningSequence.access$getIterator$p(this.this$0)
                     .invoke(FlatteningSequence.access$getTransformer$p(this.this$0).invoke(this.iterator.next())) as java.util.Iterator;
                  if (var3.hasNext()) {
                     this.itemIterator = var3;
                     this.state = 1;
                     return true;
                  }
               }

               this.state = 2;
               this.itemIterator = null;
               return false;
            }
         }

         public final java.util.Iterator<E> getItemIterator() {
            return (java.util.Iterator<E>)this.itemIterator;
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getState() {
            return this.state;
         }

         @Override
         public boolean hasNext() {
            if (this.state == 1) {
               return true;
            } else {
               return this.state != 2 && this.ensureItemIterator();
            }
         }

         @Override
         public E next() {
            if (this.state != 2) {
               if (this.state == 0 && !this.ensureItemIterator()) {
                  throw new NoSuchElementException();
               } else {
                  this.state = 0;
                  val var2: java.util.Iterator = this.itemIterator;
                  return (E)var2.next();
               }
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setItemIterator(java.util.Iterator<? extends E> var1) {
            this.itemIterator = var1;
         }

         public final void setState(int var1) {
            this.state = var1;
         }
      };
   }

   private object State {
      public const val UNDEFINED: Int = 0
      public const val READY: Int = 1
      public const val DONE: Int = 2
   }
}
