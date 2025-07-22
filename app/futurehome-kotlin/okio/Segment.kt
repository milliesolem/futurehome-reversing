package okio

import java.util.Arrays

internal class Segment {
   public final val data: ByteArray

   public final var limit: Int
      private set

   public final var next: Segment?
      private set

   public final var owner: Boolean
      private set

   public final var pos: Int
      private set

   public final var prev: Segment?
      private set

   public final var shared: Boolean
      private set

   public constructor()  {
      this.data = new byte[8192];
      this.owner = true;
      this.shared = false;
   }

   public constructor(data: ByteArray, pos: Int, limit: Int, shared: Boolean, owner: Boolean)  {
      this.data = var1;
      this.pos = var2;
      this.limit = var3;
      this.shared = var4;
      this.owner = var5;
   }

   public fun compact() {
      var var4: Segment = this.prev;
      if (this.prev != this) {
         if (var4.owner) {
            val var2: Int = this.limit - this.pos;
            var4 = this.prev;
            val var3: Int = var4.limit;
            var4 = this.prev;
            val var1: Int;
            if (var4.shared) {
               var1 = 0;
            } else {
               var4 = this.prev;
               var1 = var4.pos;
            }

            if (var2 <= 8192 - var3 + var1) {
               var4 = this.prev;
               this.writeTo(var4, var2);
               this.pop();
               SegmentPool.recycle(this);
            }
         }
      } else {
         throw new IllegalStateException("cannot compact".toString());
      }
   }

   public fun pop(): Segment? {
      var var1: Segment = this.next;
      if (this.next === this) {
         var1 = null;
      }

      var var2: Segment = this.prev;
      var2.next = this.next;
      var2 = this.next;
      var2.prev = this.prev;
      this.next = null;
      this.prev = null;
      return var1;
   }

   public fun push(segment: Segment): Segment {
      var1.prev = this;
      var1.next = this.next;
      val var2: Segment = this.next;
      var2.prev = var1;
      this.next = var1;
      return var1;
   }

   public fun sharedCopy(): Segment {
      this.shared = true;
      return new Segment(this.data, this.pos, this.limit, true, false);
   }

   public fun split(byteCount: Int): Segment {
      if (var1 > 0 && var1 <= this.limit - this.pos) {
         val var3: Segment;
         if (var1 >= 1024) {
            var3 = this.sharedCopy();
         } else {
            var3 = SegmentPool.take();
            ArraysKt.copyInto$default(this.data, var3.data, 0, this.pos, this.pos + var1, 2, null);
         }

         var3.limit = var3.pos + var1;
         this.pos += var1;
         val var6: Segment = this.prev;
         var6.push(var3);
         return var3;
      } else {
         throw new IllegalArgumentException("byteCount out of range".toString());
      }
   }

   public fun unsharedCopy(): Segment {
      val var2: ByteArray = Arrays.copyOf(this.data, this.data.length);
      return new Segment(var2, this.pos, this.limit, false, true);
   }

   public fun writeTo(sink: Segment, byteCount: Int) {
      if (var1.owner) {
         if (var1.limit + var2 > 8192) {
            if (var1.shared) {
               throw new IllegalArgumentException();
            }

            if (var1.limit + var2 - var1.pos > 8192) {
               throw new IllegalArgumentException();
            }

            ArraysKt.copyInto$default(var1.data, var1.data, 0, var1.pos, var1.limit, 2, null);
            var1.limit = var1.limit - var1.pos;
            var1.pos = 0;
         }

         ArraysKt.copyInto(this.data, var1.data, var1.limit, this.pos, this.pos + var2);
         var1.limit += var2;
         this.pos += var2;
      } else {
         throw new IllegalStateException("only owner can write".toString());
      }
   }

   public companion object {
      public const val SHARE_MINIMUM: Int
      public const val SIZE: Int
   }
}
