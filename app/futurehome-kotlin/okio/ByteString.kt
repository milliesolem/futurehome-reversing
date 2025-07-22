package okio

import java.io.EOFException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.io.Serializable
import java.lang.reflect.Field
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.util.Arrays
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import okio.internal._ByteString

public open class ByteString internal constructor(data: ByteArray) : Serializable, java.lang.Comparable<ByteString> {
   internal final val data: ByteArray
   internal final var hashCode: Int

   public final val size: Int
      public final get() {
         return this.getSize$okio();
      }


   internal final var utf8: String?

   init {
      this.data = var1;
   }

   @Throws(java/io/IOException::class)
   private fun readObject(`in`: ObjectInputStream) {
      val var3: ByteString = Companion.read(var1, var1.readInt());
      val var4: Field = ByteString.class.getDeclaredField("data");
      var4.setAccessible(true);
      var4.set(this, var3.data);
   }

   @Throws(java/io/IOException::class)
   private fun writeObject(out: ObjectOutputStream) {
      var1.writeInt(this.data.length);
      var1.write(this.data);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to operator function", replaceWith = @ReplaceWith(expression = "this[index]", imports = []))
   public fun getByte(index: Int): Byte {
      return this.getByte(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = []))
   public fun size(): Int {
      return this.size();
   }

   public open fun asByteBuffer(): ByteBuffer {
      val var1: ByteBuffer = ByteBuffer.wrap(this.data).asReadOnlyBuffer();
      return var1;
   }

   public open fun base64(): String {
      return _Base64.encodeBase64$default(this.getData$okio(), null, 1, null);
   }

   public open fun base64Url(): String {
      return _Base64.encodeBase64(this.getData$okio(), _Base64.getBASE64_URL_SAFE());
   }

   public open operator fun compareTo(other: ByteString): Int {
      val var5: Int = this.size();
      val var4: Int = var1.size();
      val var6: Int = Math.min(var5, var4);
      var var2: Int = 0;

      while (true) {
         if (var2 < var6) {
            val var8: Int = this.getByte(var2) and 255;
            val var7: Int = var1.getByte(var2) and 255;
            if (var8 == var7) {
               var2++;
               continue;
            }

            if (var8 < var7) {
               break;
            }
         } else {
            if (var5 == var4) {
               return 0;
            }

            if (var5 < var4) {
               break;
            }
         }

         return 1;
      }

      return -1;
   }

   public open fun copyInto(offset: Int = 0, target: ByteArray, targetOffset: Int = 0, byteCount: Int) {
      ArraysKt.copyInto(this.getData$okio(), var2, var3, var1, var4 + var1);
   }

   internal open fun digest(algorithm: String): ByteString {
      val var2: MessageDigest = MessageDigest.getInstance(var1);
      var2.update(this.data, 0, this.size());
      val var3: ByteArray = var2.digest();
      return new ByteString(var3);
   }

   public fun endsWith(suffix: ByteString): Boolean {
      return this.rangeEquals(this.size() - var1.size(), var1, 0, var1.size());
   }

   public fun endsWith(suffix: ByteArray): Boolean {
      return this.rangeEquals(this.size() - var1.length, var1, 0, var1.length);
   }

   public override operator fun equals(other: Any?): Boolean {
      var var2: Boolean = true;
      if (var1 != this) {
         if (var1 is ByteString
            && (var1 as ByteString).size() == this.getData$okio().length
            && (var1 as ByteString).rangeEquals(0, this.getData$okio(), 0, this.getData$okio().length)) {
            return true;
         }

         var2 = false;
      }

      return var2;
   }

   public operator fun get(index: Int): Byte {
      return this.internalGet$okio(var1);
   }

   internal open fun getSize(): Int {
      return this.getData$okio().length;
   }

   public override fun hashCode(): Int {
      var var1: Int = this.getHashCode$okio();
      if (var1 == 0) {
         var1 = Arrays.hashCode(this.getData$okio());
         this.setHashCode$okio(var1);
      }

      return var1;
   }

   public open fun hex(): String {
      val var7: CharArray = new char[this.getData$okio().length * 2];
      val var6: ByteArray = this.getData$okio();
      val var4: Int = var6.length;
      var var2: Int = 0;
      var var1: Int = 0;

      while (true) {
         val var3: Int = var1;
         if (var2 >= var4) {
            return StringsKt.concatToString(var7);
         }

         val var5: Byte = var6[var2];
         var7[var1] = _ByteString.getHEX_DIGIT_CHARS()[var6[var2] shr 4 and 15];
         var1 += 2;
         var7[var3 + 1] = _ByteString.getHEX_DIGIT_CHARS()[var5 and 15];
         var2++;
      }
   }

   internal open fun hmac(algorithm: String, key: ByteString): ByteString {
      try {
         val var3: Mac = Mac.getInstance(var1);
         var3.init(new SecretKeySpec(var2.toByteArray(), var1));
         val var6: ByteArray = var3.doFinal(this.data);
         return new ByteString(var6);
      } catch (var5: InvalidKeyException) {
         throw new IllegalArgumentException(var5);
      }
   }

   public open fun hmacSha1(key: ByteString): ByteString {
      return this.hmac$okio("HmacSHA1", var1);
   }

   public open fun hmacSha256(key: ByteString): ByteString {
      return this.hmac$okio("HmacSHA256", var1);
   }

   public open fun hmacSha512(key: ByteString): ByteString {
      return this.hmac$okio("HmacSHA512", var1);
   }

   fun indexOf(var1: ByteString): Int {
      return indexOf$default(this, var1, 0, 2, null);
   }

   public fun indexOf(other: ByteString, fromIndex: Int = 0): Int {
      return this.indexOf(var1.internalArray$okio(), var2);
   }

   fun indexOf(var1: ByteArray): Int {
      return indexOf$default(this, var1, 0, 2, null);
   }

   public open fun indexOf(other: ByteArray, fromIndex: Int = 0): Int {
      val var3: Int = this.getData$okio().length - var1.length;
      var2 = Math.max(var2, 0);
      if (var2 <= var3) {
         while (true) {
            if (_SegmentedByteString.arrayRangeEquals(this.getData$okio(), var2, var1, 0, var1.length)) {
               return var2;
            }

            if (var2 == var3) {
               break;
            }

            var2++;
         }
      }

      return -1;
   }

   internal open fun internalArray(): ByteArray {
      return this.getData$okio();
   }

   internal open fun internalGet(pos: Int): Byte {
      return this.getData$okio()[var1];
   }

   fun lastIndexOf(var1: ByteString): Int {
      return lastIndexOf$default(this, var1, 0, 2, null);
   }

   public fun lastIndexOf(other: ByteString, fromIndex: Int = _SegmentedByteString.getDEFAULT__ByteString_size()): Int {
      return this.lastIndexOf(var1.internalArray$okio(), var2);
   }

   fun lastIndexOf(var1: ByteArray): Int {
      return lastIndexOf$default(this, var1, 0, 2, null);
   }

   public open fun lastIndexOf(other: ByteArray, fromIndex: Int = _SegmentedByteString.getDEFAULT__ByteString_size()): Int {
      var2 = Math.min(_SegmentedByteString.resolveDefaultParameter(this, var2), this.getData$okio().length - var1.length);

      while (true) {
         if (-1 >= var2) {
            var2 = -1;
            break;
         }

         if (_SegmentedByteString.arrayRangeEquals(this.getData$okio(), var2, var1, 0, var1.length)) {
            break;
         }

         var2--;
      }

      return var2;
   }

   public fun md5(): ByteString {
      return this.digest$okio("MD5");
   }

   public open fun rangeEquals(offset: Int, other: ByteString, otherOffset: Int, byteCount: Int): Boolean {
      return var2.rangeEquals(var3, this.getData$okio(), var1, var4);
   }

   public open fun rangeEquals(offset: Int, other: ByteArray, otherOffset: Int, byteCount: Int): Boolean {
      val var5: Boolean;
      if (var1 >= 0
         && var1 <= this.getData$okio().length - var4
         && var3 >= 0
         && var3 <= var2.length - var4
         && _SegmentedByteString.arrayRangeEquals(this.getData$okio(), var1, var2, var3, var4)) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public fun sha1(): ByteString {
      return this.digest$okio("SHA-1");
   }

   public fun sha256(): ByteString {
      return this.digest$okio("SHA-256");
   }

   public fun sha512(): ByteString {
      return this.digest$okio("SHA-512");
   }

   public fun startsWith(prefix: ByteString): Boolean {
      return this.rangeEquals(0, var1, 0, var1.size());
   }

   public fun startsWith(prefix: ByteArray): Boolean {
      return this.rangeEquals(0, var1, 0, var1.length);
   }

   public open fun string(charset: Charset): String {
      return new java.lang.String(this.data, var1);
   }

   fun substring(): ByteString {
      return substring$default(this, 0, 0, 3, null);
   }

   fun substring(var1: Int): ByteString {
      return substring$default(this, var1, 0, 2, null);
   }

   public open fun substring(beginIndex: Int = 0, endIndex: Int = _SegmentedByteString.getDEFAULT__ByteString_size()): ByteString {
      var2 = _SegmentedByteString.resolveDefaultParameter(this, var2);
      if (var1 < 0) {
         throw new IllegalArgumentException("beginIndex < 0".toString());
      } else if (var2 <= this.getData$okio().length) {
         if (var2 - var1 < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex".toString());
         } else {
            val var5: ByteString;
            if (var1 == 0 && var2 == this.getData$okio().length) {
               var5 = this;
            } else {
               var5 = new ByteString(ArraysKt.copyOfRange(this.getData$okio(), var1, var2));
            }

            return var5;
         }
      } else {
         val var3: StringBuilder = new StringBuilder("endIndex > length(");
         var3.append(this.getData$okio().length);
         var3.append(')');
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   public open fun toAsciiLowercase(): ByteString {
      var var1: Int = 0;

      var var8: ByteString;
      while (true) {
         if (var1 >= this.getData$okio().length) {
            var8 = this;
            break;
         }

         val var3: Byte = this.getData$okio()[var1];
         if (var3 >= 65 && var3 <= 90) {
            val var4: ByteArray = this.getData$okio();
            val var7: ByteArray = Arrays.copyOf(var4, var4.length);
            val var2: Int = var1 + 1;
            var7[var1] = (byte)(var3 + 32);

            for (int var5 = var2; var5 < var7.length; var5++) {
               val var6: Byte = var7[var5];
               if (var7[var5] >= 65 && var7[var5] <= 90) {
                  var7[var5] = (byte)(var6 + 32);
               }
            }

            var8 = new ByteString(var7);
            break;
         }

         var1++;
      }

      return var8;
   }

   public open fun toAsciiUppercase(): ByteString {
      var var1: Int = 0;

      var var8: ByteString;
      while (true) {
         if (var1 >= this.getData$okio().length) {
            var8 = this;
            break;
         }

         val var3: Byte = this.getData$okio()[var1];
         if (var3 >= 97 && var3 <= 122) {
            val var4: ByteArray = this.getData$okio();
            val var7: ByteArray = Arrays.copyOf(var4, var4.length);
            val var2: Int = var1 + 1;
            var7[var1] = (byte)(var3 - 32);

            for (int var5 = var2; var5 < var7.length; var5++) {
               val var6: Byte = var7[var5];
               if (var7[var5] >= 97 && var7[var5] <= 122) {
                  var7[var5] = (byte)(var6 - 32);
               }
            }

            var8 = new ByteString(var7);
            break;
         }

         var1++;
      }

      return var8;
   }

   public open fun toByteArray(): ByteArray {
      var var1: ByteArray = this.getData$okio();
      var1 = Arrays.copyOf(var1, var1.length);
      return var1;
   }

   public override fun toString(): String {
      var var2: java.lang.String;
      if (this.getData$okio().length == 0) {
         var2 = "[size=0]";
      } else {
         var var1: Int = _ByteString.access$codePointIndexToCharIndex(this.getData$okio(), 64);
         if (var1 == -1) {
            if (this.getData$okio().length <= 64) {
               val var5: StringBuilder = new StringBuilder("[hex=");
               var5.append(this.hex());
               var5.append(']');
               var2 = var5.toString();
            } else {
               val var3: StringBuilder = new StringBuilder("[size=");
               var3.append(this.getData$okio().length);
               var3.append(" hex=");
               var1 = _SegmentedByteString.resolveDefaultParameter(this, 64);
               if (var1 > this.getData$okio().length) {
                  val var7: StringBuilder = new StringBuilder("endIndex > length(");
                  var7.append(this.getData$okio().length);
                  var7.append(')');
                  throw new IllegalArgumentException(var7.toString().toString());
               }

               if (var1 < 0) {
                  throw new IllegalArgumentException("endIndex < beginIndex".toString());
               }

               val var6: ByteString;
               if (var1 == this.getData$okio().length) {
                  var6 = this;
               } else {
                  var6 = new ByteString(ArraysKt.copyOfRange(this.getData$okio(), 0, var1));
               }

               var3.append(var6.hex());
               var3.append("…]");
               var2 = var3.toString();
            }
         } else {
            val var10: java.lang.String = this.utf8();
            var2 = var10.substring(0, var1);
            var2 = StringsKt.replace$default(
               StringsKt.replace$default(StringsKt.replace$default(var2, "\\", "\\\\", false, 4, null), "\n", "\\n", false, 4, null),
               "\r",
               "\\r",
               false,
               4,
               null
            );
            if (var1 < var10.length()) {
               val var11: StringBuilder = new StringBuilder("[size=");
               var11.append(this.getData$okio().length);
               var11.append(" text=");
               var11.append(var2);
               var11.append("…]");
               var2 = var11.toString();
            } else {
               val var12: StringBuilder = new StringBuilder("[text=");
               var12.append(var2);
               var12.append(']');
               var2 = var12.toString();
            }
         }
      }

      return var2;
   }

   public open fun utf8(): String {
      val var2: java.lang.String = this.getUtf8$okio();
      var var1: java.lang.String = var2;
      if (var2 == null) {
         var1 = _JvmPlatformKt.toUtf8String(this.internalArray$okio());
         this.setUtf8$okio(var1);
      }

      return var1;
   }

   @Throws(java/io/IOException::class)
   public open fun write(out: OutputStream) {
      var1.write(this.data);
   }

   internal open fun write(buffer: Buffer, offset: Int, byteCount: Int) {
      _ByteString.commonWrite(this, var1, var2, var3);
   }

   public companion object {
      public final val EMPTY: ByteString
      private const val serialVersionUID: Long

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.decodeBase64()", imports = ["okio.ByteString.Companion.decodeBase64"]))
      public fun decodeBase64(string: String): ByteString? {
         return this.decodeBase64(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.decodeHex()", imports = ["okio.ByteString.Companion.decodeHex"]))
      public fun decodeHex(string: String): ByteString {
         return this.decodeHex(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.encode(charset)", imports = ["okio.ByteString.Companion.encode"]))
      public fun encodeString(string: String, charset: Charset): ByteString {
         return this.encodeString(var1, var2);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.encodeUtf8()", imports = ["okio.ByteString.Companion.encodeUtf8"]))
      public fun encodeUtf8(string: String): ByteString {
         return this.encodeUtf8(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "buffer.toByteString()", imports = ["okio.ByteString.Companion.toByteString"]))
      public fun of(buffer: ByteBuffer): ByteString {
         return this.of(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "array.toByteString(offset, byteCount)", imports = ["okio.ByteString.Companion.toByteString"]))
      public fun of(array: ByteArray, offset: Int, byteCount: Int): ByteString {
         return this.of(var1, var2, var3);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "inputstream.readByteString(byteCount)", imports = ["okio.ByteString.Companion.readByteString"]))
      public fun read(inputstream: InputStream, byteCount: Int): ByteString {
         return this.read(var1, var2);
      }

      public fun String.decodeBase64(): ByteString? {
         val var2: ByteArray = _Base64.decodeBase64ToArray(var1);
         val var3: ByteString;
         if (var2 != null) {
            var3 = new ByteString(var2);
         } else {
            var3 = null;
         }

         return var3;
      }

      public fun String.decodeHex(): ByteString {
         if (var1.length() % 2 != 0) {
            val var6: StringBuilder = new StringBuilder("Unexpected hex string: ");
            var6.append(var1);
            throw new IllegalArgumentException(var6.toString().toString());
         } else {
            val var3: Int = var1.length() / 2;
            val var5: ByteArray = new byte[var3];

            for (int var2 = 0; var2 < var3; var2++) {
               var5[var2] = (byte)(
                  (_ByteString.access$decodeHexDigit(var1.charAt(var2 * 2)) shl 4) + _ByteString.access$decodeHexDigit(var1.charAt(var2 * 2 + 1))
               );
            }

            return new ByteString(var5);
         }
      }

      public fun String.encode(charset: Charset = ...): ByteString {
         val var3: ByteArray = var1.getBytes(var2);
         return new ByteString(var3);
      }

      public fun String.encodeUtf8(): ByteString {
         val var2: ByteString = new ByteString(_JvmPlatformKt.asUtf8ToByteArray(var1));
         var2.setUtf8$okio(var1);
         return var2;
      }

      public fun ByteBuffer.toByteString(): ByteString {
         val var2: ByteArray = new byte[var1.remaining()];
         var1.get(var2);
         return new ByteString(var2);
      }

      public fun of(data: ByteArray): ByteString {
         var1 = Arrays.copyOf(var1, var1.length);
         return new ByteString(var1);
      }

      public fun ByteArray.toByteString(offset: Int = ..., byteCount: Int = ...): ByteString {
         var3 = _SegmentedByteString.resolveDefaultParameter(var1, var3);
         _SegmentedByteString.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
         return new ByteString(ArraysKt.copyOfRange(var1, var2, var3 + var2));
      }

      @Throws(java/io/IOException::class)
      public fun InputStream.readByteString(byteCount: Int): ByteString {
         if (var2 >= 0) {
            val var5: ByteArray = new byte[var2];
            var var3: Int = 0;

            while (var3 < var2) {
               val var4: Int = var1.read(var5, var3, var2 - var3);
               if (var4 == -1) {
                  throw new EOFException();
               }

               var3 += var4;
            }

            return new ByteString(var5);
         } else {
            val var6: StringBuilder = new StringBuilder("byteCount < 0: ");
            var6.append(var2);
            throw new IllegalArgumentException(var6.toString().toString());
         }
      }
   }
}
