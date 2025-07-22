package kotlinx.coroutines.internal

public interface ThreadSafeHeapNode {
   public var heap: ThreadSafeHeap<*>?
      internal final set

   public var index: Int
      internal final set
}
