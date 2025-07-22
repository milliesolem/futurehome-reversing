package okhttp3.internal.http2

import kotlin.jvm.internal.Intrinsics

public class Settings {
   public final val headerTableSize: Int
      public final get() {
         val var1: Int;
         if ((this.set and 2) != 0) {
            var1 = this.values[1];
         } else {
            var1 = -1;
         }

         return var1;
      }


   public final val initialWindowSize: Int
      public final get() {
         val var1: Int;
         if ((this.set and 128) != 0) {
            var1 = this.values[7];
         } else {
            var1 = 65535;
         }

         return var1;
      }


   private final var set: Int
   private final val values: IntArray = new int[10]

   public fun clear() {
      this.set = 0;
      ArraysKt.fill$default(this.values, 0, 0, 0, 6, null);
   }

   public operator fun get(id: Int): Int {
      return this.values[var1];
   }

   public fun getEnablePush(defaultValue: Boolean): Boolean {
      if ((this.set and 4) != 0) {
         if (this.values[2] == 1) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public fun getMaxConcurrentStreams(): Int {
      val var1: Int;
      if ((this.set and 16) != 0) {
         var1 = this.values[4];
      } else {
         var1 = Integer.MAX_VALUE;
      }

      return var1;
   }

   public fun getMaxFrameSize(defaultValue: Int): Int {
      if ((this.set and 32) != 0) {
         var1 = this.values[5];
      }

      return var1;
   }

   public fun getMaxHeaderListSize(defaultValue: Int): Int {
      if ((this.set and 64) != 0) {
         var1 = this.values[6];
      }

      return var1;
   }

   public fun isSet(id: Int): Boolean {
      var var2: Boolean = true;
      if ((1 shl var1 and this.set) == 0) {
         var2 = false;
      }

      return var2;
   }

   public fun merge(other: Settings) {
      Intrinsics.checkParameterIsNotNull(var1, "other");

      for (int var2 = 0; var2 < 10; var2++) {
         if (var1.isSet(var2)) {
            this.set(var2, var1.get(var2));
         }
      }
   }

   public operator fun set(id: Int, value: Int): Settings {
      if (var1 >= 0) {
         val var3: IntArray = this.values;
         if (var1 < this.values.length) {
            this.set |= 1 shl var1;
            var3[var1] = var2;
         }
      }

      return this;
   }

   public fun size(): Int {
      return Integer.bitCount(this.set);
   }

   public companion object {
      public const val COUNT: Int
      public const val DEFAULT_INITIAL_WINDOW_SIZE: Int
      public const val ENABLE_PUSH: Int
      public const val HEADER_TABLE_SIZE: Int
      public const val INITIAL_WINDOW_SIZE: Int
      public const val MAX_CONCURRENT_STREAMS: Int
      public const val MAX_FRAME_SIZE: Int
      public const val MAX_HEADER_LIST_SIZE: Int
   }
}
