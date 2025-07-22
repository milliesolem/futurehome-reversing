package okio

import java.util.concurrent.atomic.AtomicReference

internal object SegmentPool {
   private final val HASH_BUCKET_COUNT: Int
   private final val LOCK: Segment = new Segment(new byte[0], 0, 0, false, false)
   public final val MAX_SIZE: Int = 65536

   public final val byteCount: Int
      public final get() {
         val var1: Segment = this.firstRef().get();
         return if (var1 == null) 0 else var1.limit;
      }


   private final val hashBuckets: Array<AtomicReference<Segment?>>

   @JvmStatic
   fun {
      var var0: Int = 0;
      val var1: Int = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() * 2 - 1);
      HASH_BUCKET_COUNT = var1;

      val var2: Array<AtomicReference>;
      for (var2 = new AtomicReference[var1]; var0 < var1; var0++) {
         var2[var0] = new AtomicReference();
      }

      hashBuckets = var2;
   }

   private fun firstRef(): AtomicReference<Segment?> {
      return hashBuckets[(int)(Thread.currentThread().getId() and HASH_BUCKET_COUNT - 1L)];
   }

   @JvmStatic
   public fun recycle(segment: Segment) {
      if (var0.next != null || var0.prev != null) {
         throw new IllegalArgumentException("Failed requirement.".toString());
      } else if (!var0.shared) {
         val var2: AtomicReference = INSTANCE.firstRef();
         val var4: Segment = LOCK;
         val var3: Segment = var2.getAndSet(LOCK);
         if (var3 != var4) {
            val var1: Int;
            if (var3 != null) {
               var1 = var3.limit;
            } else {
               var1 = 0;
            }

            if (var1 >= MAX_SIZE) {
               var2.set(var3);
            } else {
               var0.next = var3;
               var0.pos = 0;
               var0.limit = var1 + 8192;
               var2.set(var0);
            }
         }
      }
   }

   @JvmStatic
   public fun take(): Segment {
      val var0: AtomicReference = INSTANCE.firstRef();
      val var1: Segment = LOCK;
      val var2: Segment = var0.getAndSet(LOCK);
      if (var2 === var1) {
         return new Segment();
      } else if (var2 == null) {
         var0.set(null);
         return new Segment();
      } else {
         var0.set(var2.next);
         var2.next = null;
         var2.limit = 0;
         return var2;
      }
   }
}
