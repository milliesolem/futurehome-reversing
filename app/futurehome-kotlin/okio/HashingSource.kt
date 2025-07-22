package okio

import java.security.MessageDigest
import javax.crypto.Mac

public class HashingSource : ForwardingSource, Source {
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

   internal constructor(source: Source, algorithm: String)  {
      val var3: MessageDigest = MessageDigest.getInstance(var2);
      this(var1, var3);
   }

   internal constructor(source: Source, digest: MessageDigest) : super(var1) {
      this.messageDigest = var2;
      this.mac = null;
   }

   internal constructor(source: Source, mac: Mac) : super(var1) {
      this.mac = var2;
      this.messageDigest = null;
   }


   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hash", imports = []))
   public fun hash(): ByteString {
      return this.hash();
   }

   @Throws(java/io/IOException::class)
   public override fun read(sink: Buffer, byteCount: Long): Long {
      val var11: Long = super.read(var1, var2);
      if (var11 != -1L) {
         val var9: Long = var1.size() - var11;
         var2 = var1.size();
         var var13: Segment = var1.head;

         while (true) {
            var var5: Long = var9;
            var var7: Long = var2;
            var var14: Segment = var13;
            if (var2 <= var9) {
               while (var7 < var1.size()) {
                  val var4: Int = (int)(var14.pos + var5 - var7);
                  if (this.messageDigest != null) {
                     this.messageDigest.update(var14.data, var4, var14.limit - var4);
                  } else {
                     val var17: Mac = this.mac;
                     var17.update(var14.data, var4, var14.limit - var4);
                  }

                  var7 += var14.limit - var14.pos;
                  var14 = var14.next;
                  var5 = var7;
               }
               break;
            }

            var13 = var13.prev;
            var2 -= var13.limit - var13.pos;
         }
      }

      return var11;
   }

   public companion object {
      public fun hmacSha1(source: Source, key: ByteString): HashingSource {
         return new HashingSource(var1, var2, "HmacSHA1");
      }

      public fun hmacSha256(source: Source, key: ByteString): HashingSource {
         return new HashingSource(var1, var2, "HmacSHA256");
      }

      public fun hmacSha512(source: Source, key: ByteString): HashingSource {
         return new HashingSource(var1, var2, "HmacSHA512");
      }

      public fun md5(source: Source): HashingSource {
         return new HashingSource(var1, "MD5");
      }

      public fun sha1(source: Source): HashingSource {
         return new HashingSource(var1, "SHA-1");
      }

      public fun sha256(source: Source): HashingSource {
         return new HashingSource(var1, "SHA-256");
      }

      public fun sha512(source: Source): HashingSource {
         return new HashingSource(var1, "SHA-512");
      }
   }
}
