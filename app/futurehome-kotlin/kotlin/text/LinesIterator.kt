package kotlin.text

import java.util.NoSuchElementException
import kotlin.jvm.internal.markers.KMappedMarker

private class LinesIterator(string: CharSequence) : java.util.Iterator<java.lang.String>, KMappedMarker {
   private final val string: CharSequence
   private final var state: Int
   private final var tokenStartIndex: Int
   private final var delimiterStartIndex: Int
   private final var delimiterLength: Int

   init {
      this.string = var1;
   }

   public override operator fun hasNext(): Boolean {
      var var6: Boolean = false;
      if (this.state != 0) {
         if (this.state == 1) {
            var6 = true;
         }

         return var6;
      } else {
         var var2: Byte = 2;
         if (this.delimiterLength < 0) {
            this.state = 2;
            return false;
         } else {
            val var3: Int = this.string.length();
            var var8: Int = this.tokenStartIndex;
            val var4: Int = this.string.length();

            label40:
            while (true) {
               if (var8 < var4) {
                  val var5: Char = this.string.charAt(var8);
                  if (var5 != '\n' && var5 != '\r') {
                     var8++;
                     continue;
                  }

                  if (var5 != '\r' || var8 + 1 >= this.string.length() || this.string.charAt(var8 + 1) != '\n') {
                     var2 = 1;
                     break label40;
                  }
                  break;
               }

               var2 = -1;
               var8 = var3;
               break;
            }

            this.state = 1;
            this.delimiterLength = var2;
            this.delimiterStartIndex = var8;
            return true;
         }
      }
   }

   public open operator fun next(): String {
      if (this.hasNext()) {
         this.state = 0;
         val var2: Int = this.delimiterStartIndex;
         val var1: Int = this.tokenStartIndex;
         this.tokenStartIndex = this.delimiterLength + this.delimiterStartIndex;
         return this.string.subSequence(var1, var2).toString();
      } else {
         throw new NoSuchElementException();
      }
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   private companion object State {
      public const val UNKNOWN: Int
      public const val HAS_NEXT: Int
      public const val EXHAUSTED: Int
   }
}
