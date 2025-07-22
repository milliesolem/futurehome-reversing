package okio

import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal class SegmentedByteString internal constructor(vararg segments: Any, directory: IntArray) : ByteString(ByteString.EMPTY.getData$okio()) {
   internal final val directory: IntArray
   internal final val segments: Array<ByteArray>

   init {
      this.segments = var1;
      this.directory = var2;
   }

   private fun toByteString(): ByteString {
      return new ByteString(this.toByteArray());
   }

   private fun writeReplace(): Object {
      val var1: ByteString = this.toByteString();
      return var1;
   }

   public override fun asByteBuffer(): ByteBuffer {
      val var1: ByteBuffer = ByteBuffer.wrap(this.toByteArray()).asReadOnlyBuffer();
      return var1;
   }

   public override fun base64(): String {
      return this.toByteString().base64();
   }

   public override fun base64Url(): String {
      return this.toByteString().base64Url();
   }

   public override fun copyInto(offset: Int, target: ByteArray, targetOffset: Int, byteCount: Int) {
      val var9: Long = this.size();
      val var13: Long = var1;
      val var11: Long = var4;
      _SegmentedByteString.checkOffsetAndCount(var9, var13, (long)var4);
      _SegmentedByteString.checkOffsetAndCount((long)var2.length, (long)var3, var11);
      val var6: Int = var4 + var1;

      for (int var15 = okio.internal._SegmentedByteString.segment(this, var1); var1 < var6; var15++) {
         val var5: Int;
         if (var15 == 0) {
            var5 = 0;
         } else {
            var5 = this.getDirectory$okio()[var15 - 1];
         }

         var var7: Int = this.getDirectory$okio()[var15];
         val var8: Int = this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length + var15];
         var7 = Math.min(var6, var7 - var5 + var5) - var1;
         ArraysKt.copyInto(this.getSegments$okio()[var15], var2, var3, var8 + (var1 - var5), var8 + (var1 - var5) + var7);
         var3 += var7;
         var1 += var7;
      }
   }

   internal override fun digest(algorithm: String): ByteString {
      val var7: MessageDigest = MessageDigest.getInstance(var1);
      val var5: Int = (this.getSegments$okio() as Array<Any>).length;
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         val var6: Int = this.getDirectory$okio()[var5 + var2];
         val var4: Int = this.getDirectory$okio()[var2];
         var7.update(this.getSegments$okio()[var2], var6, var4 - var3);
         var2++;
         var3 = var4;
      }

      val var8: ByteArray = var7.digest();
      return new ByteString(var8);
   }

   public override operator fun equals(other: Any?): Boolean {
      var var2: Boolean = true;
      if (var1 != this) {
         if (var1 is ByteString && (var1 as ByteString).size() == this.size() && this.rangeEquals(0, var1 as ByteString, 0, this.size())) {
            return true;
         }

         var2 = false;
      }

      return var2;
   }

   internal override fun getSize(): Int {
      return this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length - 1];
   }

   public override fun hashCode(): Int {
      var var1: Int = this.getHashCode$okio();
      if (var1 == 0) {
         val var7: Int = (this.getSegments$okio() as Array<Any>).length;
         var var4: Int = 0;
         var1 = 1;
         var var3: Int = 0;

         while (var4 < var7) {
            val var6: Int = this.getDirectory$okio()[var7 + var4];
            val var5: Int = this.getDirectory$okio()[var4];
            val var8: ByteArray = this.getSegments$okio()[var4];

            for (int var2 = var6; var2 < var5 - var3 + var6; var2++) {
               var1 = var1 * 31 + var8[var2];
            }

            var4++;
            var3 = var5;
         }

         this.setHashCode$okio(var1);
      }

      return var1;
   }

   public override fun hex(): String {
      return this.toByteString().hex();
   }

   internal override fun hmac(algorithm: String, key: ByteString): ByteString {
      var var6: Int;
      var var9: Mac;
      try {
         var9 = Mac.getInstance(var1);
         var9.init(new SecretKeySpec(var2.toByteArray(), var1));
         var6 = (this.getSegments$okio() as Array<Any>).length;
      } catch (var12: InvalidKeyException) {
         throw new IllegalArgumentException(var12);
      }

      var var4: Int = 0;
      var var3: Int = 0;

      while (var4 < var6) {
         var var5: Int;
         try {
            val var7: Int = this.getDirectory$okio()[var6 + var4];
            var5 = this.getDirectory$okio()[var4];
            var9.update(this.getSegments$okio()[var4], var7, var5 - var3);
         } catch (var11: InvalidKeyException) {
            throw new IllegalArgumentException(var11);
         }

         var4++;
         var3 = var5;
      }

      try {
         val var13: ByteArray = var9.doFinal();
         return new ByteString(var13);
      } catch (var10: InvalidKeyException) {
         throw new IllegalArgumentException(var10);
      }
   }

   public override fun indexOf(other: ByteArray, fromIndex: Int): Int {
      return this.toByteString().indexOf(var1, var2);
   }

   internal override fun internalArray(): ByteArray {
      return this.toByteArray();
   }

   internal override fun internalGet(pos: Int): Byte {
      _SegmentedByteString.checkOffsetAndCount((long)this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length - 1], (long)var1, 1L);
      val var3: Int = okio.internal._SegmentedByteString.segment(this, var1);
      val var2: Int;
      if (var3 == 0) {
         var2 = 0;
      } else {
         var2 = this.getDirectory$okio()[var3 - 1];
      }

      return this.getSegments$okio()[var3][var1 - var2 + this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length + var3]];
   }

   public override fun lastIndexOf(other: ByteArray, fromIndex: Int): Int {
      return this.toByteString().lastIndexOf(var1, var2);
   }

   public override fun rangeEquals(offset: Int, other: ByteString, otherOffset: Int, byteCount: Int): Boolean {
      var var9: Boolean = false;
      if (var1 >= 0) {
         if (var1 > this.size() - var4) {
            var9 = false;
         } else {
            val var6: Int = var4 + var1;
            var var5: Int = okio.internal._SegmentedByteString.segment(this, var1);
            var4 = var3;

            for (int var11 = var5; var1 < var6; var11++) {
               if (var11 == 0) {
                  var5 = 0;
               } else {
                  var5 = this.getDirectory$okio()[var11 - 1];
               }

               var var7: Int = this.getDirectory$okio()[var11];
               val var8: Int = this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length + var11];
               var7 = Math.min(var6, var7 - var5 + var5) - var1;
               if (!var2.rangeEquals(var4, this.getSegments$okio()[var11], var8 + (var1 - var5), var7)) {
                  return false;
               }

               var4 += var7;
               var1 += var7;
            }

            var9 = true;
         }
      }

      return var9;
   }

   public override fun rangeEquals(offset: Int, other: ByteArray, otherOffset: Int, byteCount: Int): Boolean {
      var var9: Boolean = false;
      if (var1 >= 0) {
         var9 = false;
         if (var1 <= this.size() - var4) {
            var9 = false;
            if (var3 >= 0) {
               if (var3 > var2.length - var4) {
                  var9 = false;
               } else {
                  val var6: Int = var4 + var1;
                  var var5: Int = okio.internal._SegmentedByteString.segment(this, var1);
                  var4 = var3;

                  for (int var11 = var5; var1 < var6; var11++) {
                     if (var11 == 0) {
                        var5 = 0;
                     } else {
                        var5 = this.getDirectory$okio()[var11 - 1];
                     }

                     var var7: Int = this.getDirectory$okio()[var11];
                     val var8: Int = this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length + var11];
                     var7 = Math.min(var6, var7 - var5 + var5) - var1;
                     if (!_SegmentedByteString.arrayRangeEquals(this.getSegments$okio()[var11], var8 + (var1 - var5), var2, var4, var7)) {
                        return false;
                     }

                     var4 += var7;
                     var1 += var7;
                  }

                  var9 = true;
               }
            }
         }
      }

      return var9;
   }

   public override fun string(charset: Charset): String {
      return this.toByteString().string(var1);
   }

   public override fun substring(beginIndex: Int, endIndex: Int): ByteString {
      var var8: ByteString = this;
      var2 = _SegmentedByteString.resolveDefaultParameter(this, var2);
      if (var1 < 0) {
         val var18: StringBuilder = new StringBuilder("beginIndex=");
         var18.append(var1);
         var18.append(" < 0");
         throw new IllegalArgumentException(var18.toString().toString());
      } else if (var2 <= this.size()) {
         val var6: Int = var2 - var1;
         if (var2 - var1 < 0) {
            val var17: StringBuilder = new StringBuilder("endIndex=");
            var17.append(var2);
            var17.append(" < beginIndex=");
            var17.append(var1);
            throw new IllegalArgumentException(var17.toString().toString());
         } else {
            if (var1 != 0 || var2 != this.size()) {
               if (var1 == var2) {
                  var8 = ByteString.EMPTY;
               } else {
                  val var4: Int = okio.internal._SegmentedByteString.segment(this, var1);
                  val var7: Int = okio.internal._SegmentedByteString.segment(this, var2 - 1);
                  val var16: Array<ByteArray> = ArraysKt.copyOfRange((byte[][])(this.getSegments$okio() as Array<Any>), var4, var7 + 1);
                  val var10: Array<Any> = var16 as Array<Any>;
                  val var9: IntArray = new int[(var16 as Array<Any>).length * 2];
                  if (var4 <= var7) {
                     var var3: Int = var4;
                     var2 = 0;

                     while (true) {
                        var9[var2] = Math.min(this.getDirectory$okio()[var3] - var1, var6);
                        var9[var2 + var10.length] = this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length + var3];
                        if (var3 == var7) {
                           break;
                        }

                        var3++;
                        var2++;
                     }
                  }

                  if (var4 == 0) {
                     var2 = 0;
                  } else {
                     var2 = this.getDirectory$okio()[var4 - 1];
                  }

                  var9[var10.length] = var9[var10.length] + (var1 - var2);
                  var8 = new SegmentedByteString(var16, var9);
               }
            }

            return var8;
         }
      } else {
         val var15: StringBuilder = new StringBuilder("endIndex=");
         var15.append(var2);
         var15.append(" > length(");
         var15.append(this.size());
         var15.append(')');
         throw new IllegalArgumentException(var15.toString().toString());
      }
   }

   public override fun toAsciiLowercase(): ByteString {
      return this.toByteString().toAsciiLowercase();
   }

   public override fun toAsciiUppercase(): ByteString {
      return this.toByteString().toAsciiUppercase();
   }

   public override fun toByteArray(): ByteArray {
      val var8: ByteArray = new byte[this.size()];
      val var5: Int = (this.getSegments$okio() as Array<Any>).length;
      var var2: Int = 0;
      var var3: Int = 0;
      var var1: Int = 0;

      while (var2 < var5) {
         val var6: Int = this.getDirectory$okio()[var5 + var2];
         val var4: Int = this.getDirectory$okio()[var2];
         val var7: ByteArray = this.getSegments$okio()[var2];
         val var9: Int = var4 - var3;
         ArraysKt.copyInto(var7, var8, var1, var6, var6 + (var4 - var3));
         var1 += var9;
         var2++;
         var3 = var4;
      }

      return var8;
   }

   public override fun toString(): String {
      return this.toByteString().toString();
   }

   @Throws(java/io/IOException::class)
   public override fun write(out: OutputStream) {
      val var5: Int = (this.getSegments$okio() as Array<Any>).length;
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         val var6: Int = this.getDirectory$okio()[var5 + var2];
         val var4: Int = this.getDirectory$okio()[var2];
         var1.write(this.getSegments$okio()[var2], var6, var4 - var3);
         var2++;
         var3 = var4;
      }
   }

   internal override fun write(buffer: Buffer, offset: Int, byteCount: Int) {
      val var6: Int = var2 + var3;

      for (int var4 = okio.internal._SegmentedByteString.segment(this, var2); var2 < var6; var4++) {
         val var5: Int;
         if (var4 == 0) {
            var5 = 0;
         } else {
            var5 = this.getDirectory$okio()[var4 - 1];
         }

         var var7: Int = this.getDirectory$okio()[var4];
         val var8: Int = this.getDirectory$okio()[(this.getSegments$okio() as Array<Any>).length + var4];
         var7 = Math.min(var6, var7 - var5 + var5) - var2;
         val var9: Segment = new Segment(this.getSegments$okio()[var4], var8 + (var2 - var5), var8 + (var2 - var5) + var7, true, false);
         if (var1.head == null) {
            var9.prev = var9;
            var9.next = var9.prev;
            var1.head = var9.next;
         } else {
            val var10: Segment = var1.head;
            val var13: Segment = var10.prev;
            var13.push(var9);
         }

         var2 += var7;
      }

      var1.setSize$okio(var1.size() + (long)var3);
   }
}
