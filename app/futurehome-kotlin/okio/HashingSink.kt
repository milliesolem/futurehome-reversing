package okio

import java.security.MessageDigest
import javax.crypto.Mac

public class HashingSink : ForwardingSink, Sink {
   public final val hash: ByteString
      public final get() {
         val var2: ByteArray;
         if (this.messageDigest != null) {
            var2 = this.messageDigest.digest();
         } else {
            val var3: Mac = this.mac;
            var2 = var3.doFinal();
         }

         return new ByteString(var2);
      }


   private final val mac: Mac?
   private final val messageDigest: MessageDigest?

   internal constructor(sink: Sink, algorithm: String)  {
      val var3: MessageDigest = MessageDigest.getInstance(var2);
      this(var1, var3);
   }

   internal constructor(sink: Sink, digest: MessageDigest) : super(var1) {
      this.messageDigest = var2;
      this.mac = null;
   }

   internal constructor(sink: Sink, mac: Mac) : super(var1) {
      this.mac = var2;
      this.messageDigest = null;
   }


   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hash", imports = []))
   public fun hash(): ByteString {
      return this.hash();
   }

   @Throws(java/io/IOException::class)
   public override fun write(source: Buffer, byteCount: Long) {
      _SegmentedByteString.checkOffsetAndCount(var1.size(), 0L, var2);
      var var7: Segment = var1.head;
      var var5: Long = 0L;

      while (var5 < var2) {
         val var4: Int = (int)Math.min(var2 - var5, (long)(var7.limit - var7.pos));
         if (this.messageDigest != null) {
            this.messageDigest.update(var7.data, var7.pos, var4);
         } else {
            val var9: Mac = this.mac;
            var9.update(var7.data, var7.pos, var4);
         }

         var5 += var4;
         var7 = var7.next;
      }

      super.write(var1, var2);
   }

   public companion object {
      public fun hmacSha1(sink: Sink, key: ByteString): HashingSink {
         return new HashingSink(var1, var2, "HmacSHA1");
      }

      public fun hmacSha256(sink: Sink, key: ByteString): HashingSink {
         return new HashingSink(var1, var2, "HmacSHA256");
      }

      public fun hmacSha512(sink: Sink, key: ByteString): HashingSink {
         return new HashingSink(var1, var2, "HmacSHA512");
      }

      public fun md5(sink: Sink): HashingSink {
         return new HashingSink(var1, "MD5");
      }

      public fun sha1(sink: Sink): HashingSink {
         return new HashingSink(var1, "SHA-1");
      }

      public fun sha256(sink: Sink): HashingSink {
         return new HashingSink(var1, "SHA-256");
      }

      public fun sha512(sink: Sink): HashingSink {
         return new HashingSink(var1, "SHA-512");
      }
   }
}
