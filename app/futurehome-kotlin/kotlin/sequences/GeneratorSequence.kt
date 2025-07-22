package kotlin.sequences

import java.util.NoSuchElementException
import kotlin.jvm.functions.Function1

private class GeneratorSequence<T>(getInitialValue: () -> Any?, getNextValue: (Any) -> Any?) : Sequence<T> {
   private final val getInitialValue: () -> Any?
   private final val getNextValue: (Any) -> Any?

   init {
      this.getInitialValue = var1;
      this.getNextValue = var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<T>(this) {
         private T nextItem;
         private int nextState;
         final GeneratorSequence<T> this$0;

         {
            this.this$0 = var1;
            this.nextState = -2;
         }

         private final void calcNext() {
            var var2: Any;
            if (this.nextState == -2) {
               var2 = (Function1)GeneratorSequence.access$getGetInitialValue$p(this.this$0).invoke();
            } else {
               var2 = GeneratorSequence.access$getGetNextValue$p(this.this$0);
               val var3: Any = this.nextItem;
               var2 = (Function1)var2.invoke(var3);
            }

            this.nextItem = (T)var2;
            val var1: Byte;
            if (var2 == null) {
               var1 = 0;
            } else {
               var1 = 1;
            }

            this.nextState = var1;
         }

         public final T getNextItem() {
            return this.nextItem;
         }

         public final int getNextState() {
            return this.nextState;
         }

         @Override
         public boolean hasNext() {
            if (this.nextState < 0) {
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
            if (this.nextState < 0) {
               this.calcNext();
            }

            if (this.nextState != 0) {
               val var1: Any = this.nextItem;
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
