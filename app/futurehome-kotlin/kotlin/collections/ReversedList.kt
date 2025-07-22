package kotlin.collections

private class ReversedList<T>(delegate: MutableList<Any>) : AbstractMutableList<T> {
   private final val delegate: MutableList<Any>

   public open val size: Int
      public open get() {
         return this.delegate.size();
      }


   init {
      this.delegate = var1;
   }

   public override fun add(index: Int, element: Any) {
      this.delegate.add(CollectionsKt__ReversedViewsKt.access$reversePositionIndex(this, var1), (T)var2);
   }

   public override fun clear() {
      this.delegate.clear();
   }

   public override operator fun get(index: Int): Any {
      return this.delegate.get(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, var1));
   }

   public override operator fun iterator(): MutableIterator<Any> {
      return this.listIterator(0);
   }

   public override fun listIterator(): MutableListIterator<Any> {
      return this.listIterator(0);
   }

   public override fun listIterator(index: Int): MutableListIterator<Any> {
      return new java.util.ListIterator<T>(this, var1) {
         private final java.util.ListIterator<T> delegateIterator;
         final ReversedList<T> this$0;

         {
            this.this$0 = var1;
            this.delegateIterator = ReversedList.access$getDelegate$p(var1)
               .listIterator(CollectionsKt__ReversedViewsKt.access$reversePositionIndex(var1, var2));
         }

         @Override
         public void add(T var1) {
            this.delegateIterator.add((T)var1);
            this.delegateIterator.previous();
         }

         public final java.util.ListIterator<T> getDelegateIterator() {
            return this.delegateIterator;
         }

         @Override
         public boolean hasNext() {
            return this.delegateIterator.hasPrevious();
         }

         @Override
         public boolean hasPrevious() {
            return this.delegateIterator.hasNext();
         }

         @Override
         public T next() {
            return this.delegateIterator.previous();
         }

         @Override
         public int nextIndex() {
            return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(this.this$0, this.delegateIterator.previousIndex());
         }

         @Override
         public T previous() {
            return this.delegateIterator.next();
         }

         @Override
         public int previousIndex() {
            return CollectionsKt__ReversedViewsKt.access$reverseIteratorIndex(this.this$0, this.delegateIterator.nextIndex());
         }

         @Override
         public void remove() {
            this.delegateIterator.remove();
         }

         @Override
         public void set(T var1) {
            this.delegateIterator.set((T)var1);
         }
      };
   }

   public override fun removeAt(index: Int): Any {
      return this.delegate.remove(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, var1));
   }

   public override operator fun set(index: Int, element: Any): Any {
      return this.delegate.set(CollectionsKt__ReversedViewsKt.access$reverseElementIndex(this, var1), (T)var2);
   }
}
