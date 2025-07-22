package kotlin.sequences

private object EmptySequence : Sequence, DropTakeSequence {
   public open fun drop(n: Int): EmptySequence {
      return INSTANCE;
   }

   public override operator fun iterator(): Iterator<Nothing> {
      return EmptyIterator.INSTANCE;
   }

   public open fun take(n: Int): EmptySequence {
      return INSTANCE;
   }
}
