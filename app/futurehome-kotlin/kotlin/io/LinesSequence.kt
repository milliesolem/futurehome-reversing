package kotlin.io

import java.io.BufferedReader
import java.util.NoSuchElementException

private class LinesSequence(reader: BufferedReader) : Sequence<java.lang.String> {
   private final val reader: BufferedReader

   init {
      this.reader = var1;
   }

   public override operator fun iterator(): Iterator<String> {
      return new java.util.Iterator<java.lang.String>(this) {
         private boolean done;
         private java.lang.String nextValue;
         final LinesSequence this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public boolean hasNext() {
            var var1: Boolean = true;
            if (this.nextValue == null && !this.done) {
               val var3: java.lang.String = LinesSequence.access$getReader$p(this.this$0).readLine();
               this.nextValue = var3;
               if (var3 == null) {
                  this.done = true;
               }
            }

            if (this.nextValue == null) {
               var1 = false;
            }

            return var1;
         }

         public java.lang.String next() {
            if (this.hasNext()) {
               val var1: java.lang.String = this.nextValue;
               this.nextValue = null;
               return var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
