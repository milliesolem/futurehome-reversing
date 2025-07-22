package kotlinx.coroutines.internal

private class Removed(ref: LockFreeLinkedListNode) {
   public final val ref: LockFreeLinkedListNode

   init {
      this.ref = var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("Removed[");
      var1.append(this.ref);
      var1.append(']');
      return var1.toString();
   }
}
