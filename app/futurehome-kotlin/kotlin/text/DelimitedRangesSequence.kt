package kotlin.text

import java.util.NoSuchElementException

private class DelimitedRangesSequence(input: CharSequence, startIndex: Int, limit: Int, getNextMatch: (CharSequence, Int) -> Pair<Int, Int>?) :
   Sequence<IntRange> {
   private final val input: CharSequence
   private final val startIndex: Int
   private final val limit: Int
   private final val getNextMatch: (CharSequence, Int) -> Pair<Int, Int>?

   init {
      this.input = var1;
      this.startIndex = var2;
      this.limit = var3;
      this.getNextMatch = var4;
   }

   public override operator fun iterator(): Iterator<IntRange> {
      return new java.util.Iterator<IntRange>(this) {
         private int counter;
         private int currentStartIndex;
         private IntRange nextItem;
         private int nextSearchIndex;
         private int nextState;
         final DelimitedRangesSequence this$0;

         {
            this.this$0 = var1;
            this.nextState = -1;
            val var2: Int = RangesKt.coerceIn(DelimitedRangesSequence.access$getStartIndex$p(var1), 0, DelimitedRangesSequence.access$getInput$p(var1).length());
            this.currentStartIndex = var2;
            this.nextSearchIndex = var2;
         }

         private final void calcNext() {
            var var1: Byte = 0;
            if (this.nextSearchIndex < 0) {
               this.nextState = 0;
               this.nextItem = null;
            } else {
               label26: {
                  label25: {
                     if (DelimitedRangesSequence.access$getLimit$p(this.this$0) > 0) {
                        val var5: Int = this.counter + 1;
                        this.counter += 1;
                        if (var5 >= DelimitedRangesSequence.access$getLimit$p(this.this$0)) {
                           break label25;
                        }
                     }

                     if (this.nextSearchIndex <= DelimitedRangesSequence.access$getInput$p(this.this$0).length()) {
                        val var4: Pair = DelimitedRangesSequence.access$getGetNextMatch$p(this.this$0)
                           .invoke(DelimitedRangesSequence.access$getInput$p(this.this$0), this.nextSearchIndex) as Pair;
                        if (var4 == null) {
                           this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this.this$0)));
                           this.nextSearchIndex = -1;
                        } else {
                           val var6: Int = (var4.component1() as java.lang.Number).intValue();
                           val var3: Int = (var4.component2() as java.lang.Number).intValue();
                           this.nextItem = RangesKt.until(this.currentStartIndex, var6);
                           val var7: Int = var6 + var3;
                           this.currentStartIndex = var6 + var3;
                           if (var3 == 0) {
                              var1 = 1;
                           }

                           this.nextSearchIndex = var7 + var1;
                        }
                        break label26;
                     }
                  }

                  this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.access$getInput$p(this.this$0)));
                  this.nextSearchIndex = -1;
               }

               this.nextState = 1;
            }
         }

         public final int getCounter() {
            return this.counter;
         }

         public final int getCurrentStartIndex() {
            return this.currentStartIndex;
         }

         public final IntRange getNextItem() {
            return this.nextItem;
         }

         public final int getNextSearchIndex() {
            return this.nextSearchIndex;
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

         public IntRange next() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            if (this.nextState != 0) {
               val var1: IntRange = this.nextItem;
               this.nextItem = null;
               this.nextState = -1;
               return var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setCounter(int var1) {
            this.counter = var1;
         }

         public final void setCurrentStartIndex(int var1) {
            this.currentStartIndex = var1;
         }

         public final void setNextItem(IntRange var1) {
            this.nextItem = var1;
         }

         public final void setNextSearchIndex(int var1) {
            this.nextSearchIndex = var1;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }
      };
   }
}
