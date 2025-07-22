package kotlin.text

import java.nio.charset.Charset

public object Charsets {
   public final val UTF_8: Charset
   public final val UTF_16: Charset
   public final val UTF_16BE: Charset
   public final val UTF_16LE: Charset
   public final val US_ASCII: Charset
   public final val ISO_8859_1: Charset

   public final val UTF_32: Charset
      public final get() {
         var var1: Charset = utf_32;
         if (utf_32 == null) {
            val var3: Charsets = this;
            var1 = Charset.forName("UTF-32");
            utf_32 = var1;
         }

         return var1;
      }


   private final var utf_32: Charset?

   public final val UTF_32LE: Charset
      public final get() {
         var var1: Charset = utf_32le;
         if (utf_32le == null) {
            val var3: Charsets = this;
            var1 = Charset.forName("UTF-32LE");
            utf_32le = var1;
         }

         return var1;
      }


   private final var utf_32le: Charset?

   public final val UTF_32BE: Charset
      public final get() {
         var var1: Charset = utf_32be;
         if (utf_32be == null) {
            val var3: Charsets = this;
            var1 = Charset.forName("UTF-32BE");
            utf_32be = var1;
         }

         return var1;
      }


   private final var utf_32be: Charset?

   @JvmStatic
   fun {
      var var0: Charset = Charset.forName("UTF-8");
      UTF_8 = var0;
      var0 = Charset.forName("UTF-16");
      UTF_16 = var0;
      var0 = Charset.forName("UTF-16BE");
      UTF_16BE = var0;
      var0 = Charset.forName("UTF-16LE");
      UTF_16LE = var0;
      var0 = Charset.forName("US-ASCII");
      US_ASCII = var0;
      var0 = Charset.forName("ISO-8859-1");
      ISO_8859_1 = var0;
   }
}
