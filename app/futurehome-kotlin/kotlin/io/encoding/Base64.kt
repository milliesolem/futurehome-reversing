package kotlin.io.encoding

import java.nio.charset.Charset
import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public open class Base64 private constructor(isUrlSafe: Boolean, isMimeScheme: Boolean, paddingOption: kotlin.io.encoding.Base64.PaddingOption) {
   internal final val isUrlSafe: Boolean
   internal final val isMimeScheme: Boolean
   internal final val paddingOption: kotlin.io.encoding.Base64.PaddingOption

   init {
      this.isUrlSafe = var1;
      this.isMimeScheme = var2;
      this.paddingOption = var3;
      if (var1 && var2) {
         throw new IllegalArgumentException("Failed requirement.".toString());
      }
   }

   private fun checkDestinationBounds(destinationSize: Int, destinationOffset: Int, capacityNeeded: Int) {
      if (var2 >= 0 && var2 <= var1) {
         if (var2 + var3 < 0 || var2 + var3 > var1) {
            val var6: StringBuilder = new StringBuilder("The destination array does not have enough capacity, destination offset: ");
            var6.append(var2);
            var6.append(", destination size: ");
            var6.append(var1);
            var6.append(", capacity needed: ");
            var6.append(var3);
            throw new IndexOutOfBoundsException(var6.toString());
         }
      } else {
         val var5: StringBuilder = new StringBuilder("destination offset: ");
         var5.append(var2);
         var5.append(", destination size: ");
         var5.append(var1);
         throw new IndexOutOfBoundsException(var5.toString());
      }
   }

   private fun checkPaddingIsAllowed(padIndex: Int) {
      if (this.paddingOption === Base64.PaddingOption.ABSENT) {
         val var2: StringBuilder = new StringBuilder("The padding option is set to ABSENT, but the input has a pad character at index ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private fun decodeImpl(source: ByteArray, destination: ByteArray, destinationOffset: Int, startIndex: Int, endIndex: Int): Int {
      val var11: IntArray;
      if (this.isUrlSafe) {
         var11 = Base64Kt.access$getBase64UrlDecodeMap$p();
      } else {
         var11 = Base64Kt.access$getBase64DecodeMap$p();
      }

      var var8: Int = var3;
      var var7: Int = -8;
      var var6: Int = 0;

      var var21: Int;
      while (true) {
         if (var4 >= var5) {
            var21 = var4;
            var17 = false;
            break;
         }

         if (var7 == -8 && var4 + 3 < var5) {
            val var10: Int = var11[var1[var4 + 1] and 255] shl 12 or var11[var1[var4] and 255] shl 18 or var11[var1[var4 + 2] and 255] shl 6 or var11[var1[var4
               + 3] and 255];
            if ((
                  var11[var1[var4 + 1] and 255] shl 12 or var11[var1[var4] and 255] shl 18 or var11[var1[var4 + 2] and 255] shl 6 or var11[var1[var4 + 3] and 255]
               )
               >= 0) {
               var2[var8] = (byte)(var10 shr 16);
               var2[var8 + 1] = (byte)(var10 shr 8);
               var21 = var8 + 3;
               var2[var8 + 2] = (byte)var10;
               var4 += 4;
               var8 = var21;
               continue;
            }
         }

         val var23: Int = var1[var4] and 255;
         var21 = var11[var1[var4] and 255];
         if (var11[var1[var4] and 255] < 0) {
            if (var21 == -2) {
               var21 = this.handlePaddingSymbol(var1, var4, var5, var7);
               var17 = true;
               break;
            }

            if (!this.isMimeScheme) {
               val var14: StringBuilder = new StringBuilder("Invalid symbol '");
               var14.append((char)var23);
               var14.append("'(");
               val var12: java.lang.String = Integer.toString(var23, CharsKt.checkRadix(8));
               var14.append(var12);
               var14.append(") at index ");
               var14.append(var4);
               throw new IllegalArgumentException(var14.toString());
            }

            var4++;
         } else {
            var4++;
            var6 = var6 shl 6 or var21;
            var21 = var7 + 6;
            if (var7 + 6 >= 0) {
               var2[var8] = (byte)(var6 ushr var21);
               var6 &= (1 shl var21) - 1;
               var7 -= 2;
               var8++;
            } else {
               var7 = var21;
            }
         }
      }

      if (var7 != -2) {
         if (var7 != -8 && !var17 && this.paddingOption === Base64.PaddingOption.PRESENT) {
            throw new IllegalArgumentException("The padding option is set to PRESENT, but the input is not properly padded");
         } else if (var6 == 0) {
            var4 = this.skipIllegalSymbolsIfMime(var1, var21, var5);
            if (var4 >= var5) {
               return var8 - var3;
            } else {
               var3 = var1[var4] and 255;
               val var15: StringBuilder = new StringBuilder("Symbol '");
               var15.append((char)var3);
               var15.append("'(");
               val var13: java.lang.String = Integer.toString(var3, CharsKt.checkRadix(8));
               var15.append(var13);
               var15.append(") at index ");
               var15.append(var4 - 1);
               var15.append(" is prohibited after the pad character");
               throw new IllegalArgumentException(var15.toString());
            }
         } else {
            throw new IllegalArgumentException("The pad bits must be zeros");
         }
      } else {
         throw new IllegalArgumentException("The last unit of input does not have enough bits");
      }
   }

   private fun handlePaddingSymbol(source: ByteArray, padIndex: Int, endIndex: Int, byteStart: Int): Int {
      if (var4 == -8) {
         val var6: StringBuilder = new StringBuilder("Redundant pad character at index ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      } else {
         if (var4 != -6) {
            if (var4 != -4) {
               if (var4 != -2) {
                  throw new IllegalStateException("Unreachable".toString());
               }
            } else {
               this.checkPaddingIsAllowed(var2);
               var2 = this.skipIllegalSymbolsIfMime(var1, var2 + 1, var3);
               if (var2 == var3 || var1[var2] != 61) {
                  val var5: StringBuilder = new StringBuilder("Missing one pad character at index ");
                  var5.append(var2);
                  throw new IllegalArgumentException(var5.toString());
               }
            }
         } else {
            this.checkPaddingIsAllowed(var2);
         }

         return var2 + 1;
      }
   }

   private fun shouldPadOnEncode(): Boolean {
      val var1: Boolean;
      if (this.paddingOption != Base64.PaddingOption.PRESENT && this.paddingOption != Base64.PaddingOption.PRESENT_OPTIONAL) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private fun skipIllegalSymbolsIfMime(source: ByteArray, startIndex: Int, endIndex: Int): Int {
      var var4: Int = var2;
      if (!this.isMimeScheme) {
         return var2;
      } else {
         while (var4 < var3) {
            if (Base64Kt.access$getBase64DecodeMap$p()[var1[var4] and 255] != -1) {
               return var4;
            }

            var4++;
         }

         return var4;
      }
   }

   internal fun bytesToStringImpl(source: ByteArray): String {
      val var4: StringBuilder = new StringBuilder(var1.length);
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.append((char)var1[var2]);
      }

      return var4.toString();
   }

   internal fun charsToBytesImpl(source: CharSequence, startIndex: Int, endIndex: Int): ByteArray {
      this.checkSourceBounds$kotlin_stdlib(var1.length(), var2, var3);
      val var6: ByteArray = new byte[var3 - var2];
      var var4: Int = var2;

      for (int var7 = 0; var4 < var3; var4++) {
         val var8: Char = var1.charAt(var4);
         if (var8 <= 255) {
            var6[var7] = (byte)var8;
            var7++;
         } else {
            var6[var7] = 63;
            var7++;
         }
      }

      return var6;
   }

   internal fun checkSourceBounds(sourceSize: Int, startIndex: Int, endIndex: Int) {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var2, var3, var1);
   }

   public fun decode(source: CharSequence, startIndex: Int = 0, endIndex: Int = var1.length()): ByteArray {
      val var7: ByteArray;
      if (var1 is java.lang.String) {
         val var5: java.lang.String = var1 as java.lang.String;
         this.checkSourceBounds$kotlin_stdlib((var1 as java.lang.String).length(), var2, var3);
         val var6: java.lang.String = var5.substring(var2, var3);
         val var4: Charset = Charsets.ISO_8859_1;
         var7 = var6.getBytes(var4);
      } else {
         var7 = this.charsToBytesImpl$kotlin_stdlib(var1, var2, var3);
      }

      return decode$default(this, var7, 0, 0, 6, null);
   }

   public fun decode(source: ByteArray, startIndex: Int = 0, endIndex: Int = var1.length): ByteArray {
      this.checkSourceBounds$kotlin_stdlib(var1.length, var2, var3);
      val var4: Int = this.decodeSize$kotlin_stdlib(var1, var2, var3);
      val var5: ByteArray = new byte[var4];
      if (this.decodeImpl(var1, var5, 0, var2, var3) == var4) {
         return var5;
      } else {
         throw new IllegalStateException("Check failed.");
      }
   }

   public fun decodeIntoByteArray(source: CharSequence, destination: ByteArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var1.length()): Int {
      val var9: ByteArray;
      if (var1 is java.lang.String) {
         val var7: java.lang.String = var1 as java.lang.String;
         this.checkSourceBounds$kotlin_stdlib((var1 as java.lang.String).length(), var4, var5);
         val var6: java.lang.String = var7.substring(var4, var5);
         val var8: Charset = Charsets.ISO_8859_1;
         var9 = var6.getBytes(var8);
      } else {
         var9 = this.charsToBytesImpl$kotlin_stdlib(var1, var4, var5);
      }

      return decodeIntoByteArray$default(this, var9, var2, var3, 0, 0, 24, null);
   }

   public fun decodeIntoByteArray(source: ByteArray, destination: ByteArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var1.length): Int {
      this.checkSourceBounds$kotlin_stdlib(var1.length, var4, var5);
      this.checkDestinationBounds(var2.length, var3, this.decodeSize$kotlin_stdlib(var1, var4, var5));
      return this.decodeImpl(var1, var2, var3, var4, var5);
   }

   internal fun decodeSize(source: ByteArray, startIndex: Int, endIndex: Int): Int {
      var var4: Int = var3 - var2;
      if (var3 - var2 == 0) {
         return 0;
      } else if (var4 == 1) {
         val var7: StringBuilder = new StringBuilder("Input should have at least 2 symbols for Base64 decoding, startIndex: ");
         var7.append(var2);
         var7.append(", endIndex: ");
         var7.append(var3);
         throw new IllegalArgumentException(var7.toString());
      } else {
         if (this.isMimeScheme) {
            var var5: Int = var2;

            while (true) {
               var2 = var4;
               if (var5 >= var3) {
                  break;
               }

               val var6: Int = Base64Kt.access$getBase64DecodeMap$p()[var1[var5] and 255];
               var2 = var4;
               if (var6 < 0) {
                  if (var6 == -2) {
                     var2 = var4 - (var3 - var5);
                     break;
                  }

                  var2 = var4 - 1;
               }

               var5++;
               var4 = var2;
            }
         } else {
            var2 = var4;
            if (var1[var3 - 1] == 61) {
               if (var1[var3 - 2] == 61) {
                  var2 = var4 - 2;
               } else {
                  var2 = var4 - 1;
               }
            }
         }

         return (int)((long)var2 * 6 / 8);
      }
   }

   public fun encode(source: ByteArray, startIndex: Int = 0, endIndex: Int = var1.length): String {
      return new java.lang.String(this.encodeToByteArrayImpl$kotlin_stdlib(var1, var2, var3), Charsets.ISO_8859_1);
   }

   public fun encodeIntoByteArray(source: ByteArray, destination: ByteArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var1.length): Int {
      return this.encodeIntoByteArrayImpl$kotlin_stdlib(var1, var2, var3, var4, var5);
   }

   internal fun encodeIntoByteArrayImpl(source: ByteArray, destination: ByteArray, destinationOffset: Int, startIndex: Int, endIndex: Int): Int {
      var var6: Int = var4;
      this.checkSourceBounds$kotlin_stdlib(var1.length, var4, var5);
      this.checkDestinationBounds(var2.length, var3, this.encodeSize$kotlin_stdlib(var5 - var4));
      val var13: ByteArray;
      if (this.isUrlSafe) {
         var13 = Base64Kt.access$getBase64UrlEncodeMap$p();
      } else {
         var13 = Base64Kt.access$getBase64EncodeMap$p();
      }

      var var7: Int;
      if (this.isMimeScheme) {
         var7 = 19;
      } else {
         var7 = Integer.MAX_VALUE;
      }

      var4 = var3;

      while (var6 + 2 < var5) {
         val var11: Int = Math.min((var5 - var6) / 3, var7);
         var var8: Int = 0;

         while (true) {
            val var9: Int = var4;
            val var10: Int = var6;
            if (var8 >= var11) {
               var6 = var6;
               var4 = var4;
               if (var11 == var7) {
                  var6 = var10;
                  var4 = var9;
                  if (var10 != var5) {
                     var2[var9] = mimeLineSeparatorSymbols[0];
                     var4 = var9 + 2;
                     var2[var9 + 1] = mimeLineSeparatorSymbols[1];
                     var6 = var10;
                  }
               }
               break;
            }

            val var12: Byte = var1[var6];
            val var16: Byte = var1[var6 + 1];
            var6 += 3;
            val var26: Int = (var16 and 255) shl 8 or (var12 and 255) shl 16 or var1[var10 + 2] and 255;
            var2[var4] = var13[((var16 and 255) shl 8 or (var12 and 255) shl 16 or var1[var10 + 2] and 255) ushr 18];
            var2[var4 + 1] = var13[((var16 and 255) shl 8 or (var12 and 255) shl 16 or var1[var10 + 2] and 255) ushr 12 and 63];
            var2[var4 + 2] = var13[var26 ushr 6 and 63];
            var4 += 4;
            var2[var9 + 3] = var13[var26 and 63];
            var8++;
         }
      }

      var7 = var5 - var6;
      if (var5 - var6 != 1) {
         if (var7 == 2) {
            val var22: Byte = var1[var6];
            var7 = var6 + 2;
            val var23: Int = (var1[var6 + 1] and 255) shl 2 or (var22 and 255) shl 10;
            var2[var4] = var13[((var1[var6 + 1] and 255) shl 2 or (var22 and 255) shl 10) ushr 12];
            var2[var4 + 1] = var13[((var1[var6 + 1] and 255) shl 2 or (var22 and 255) shl 10) ushr 6 and 63];
            var6 = var4 + 3;
            var2[var4 + 2] = var13[var23 and 63];
            if (this.shouldPadOnEncode()) {
               var4 += 4;
               var2[var6] = 61;
               var6 = var7;
            } else {
               var4 = var6;
               var6 = var7;
            }
         }
      } else {
         var7 = var6 + 1;
         val var18: Int = (var1[var6] and 255) shl 4;
         var2[var4] = var13[((var1[var6] and 255) shl 4) ushr 6];
         var var24: Int = var4 + 2;
         var2[var4 + 1] = var13[var18 and 63];
         if (this.shouldPadOnEncode()) {
            var2[var24] = 61;
            var24 = var4 + 4;
            var2[var4 + 3] = 61;
            var6 = var7;
            var4 = var24;
         } else {
            var6 = var7;
            var4 = var24;
         }
      }

      if (var6 == var5) {
         return var4 - var3;
      } else {
         throw new IllegalStateException("Check failed.");
      }
   }

   internal fun encodeSize(sourceSize: Int): Int {
      var var2: Int = var1 / 3;
      val var4: Int = var1 % 3;
      val var7: Int = var1 / 3 * 4;
      var1 = var2 * 4;
      if (var4 != 0) {
         if (this.shouldPadOnEncode()) {
            var1 = 4;
         } else {
            var1 = var4 + 1;
         }

         var1 = var7 + var1;
      }

      var2 = var1;
      if (this.isMimeScheme) {
         var2 = var1 + (var1 - 1) / 76 * 2;
      }

      if (var2 >= 0) {
         return var2;
      } else {
         throw new IllegalArgumentException("Input is too big");
      }
   }

   public fun <A : Appendable> encodeToAppendable(source: ByteArray, destination: A, startIndex: Int = ..., endIndex: Int = ...): A {
      var2.append(new java.lang.String(this.encodeToByteArrayImpl$kotlin_stdlib(var1, var3, var4), Charsets.ISO_8859_1));
      return (A)var2;
   }

   public fun encodeToByteArray(source: ByteArray, startIndex: Int = 0, endIndex: Int = var1.length): ByteArray {
      return this.encodeToByteArrayImpl$kotlin_stdlib(var1, var2, var3);
   }

   internal fun encodeToByteArrayImpl(source: ByteArray, startIndex: Int, endIndex: Int): ByteArray {
      this.checkSourceBounds$kotlin_stdlib(var1.length, var2, var3);
      val var4: ByteArray = new byte[this.encodeSize$kotlin_stdlib(var3 - var2)];
      this.encodeIntoByteArrayImpl$kotlin_stdlib(var1, var4, 0, var2, var3);
      return var4;
   }

   public fun withPadding(option: kotlin.io.encoding.Base64.PaddingOption): Base64 {
      val var2: Base64;
      if (this.paddingOption === var1) {
         var2 = this;
      } else {
         var2 = new Base64(this.isUrlSafe, this.isMimeScheme, var1);
      }

      return var2;
   }

   public companion object Default : Base64(false, false, Base64.PaddingOption.PRESENT) {
      private const val bitsPerByte: Int
      private const val bitsPerSymbol: Int
      internal const val bytesPerGroup: Int
      internal const val symbolsPerGroup: Int
      internal const val padSymbol: Byte
      internal const val mimeLineLength: Int
      private const val mimeGroupsPerLine: Int
      internal final val mimeLineSeparatorSymbols: ByteArray
      public final val UrlSafe: Base64
      public final val Mime: Base64
   }

   public enum class PaddingOption {
      ABSENT,
      ABSENT_OPTIONAL,
      PRESENT,
      PRESENT_OPTIONAL      @JvmStatic
      private EnumEntries $ENTRIES;
      @JvmStatic
      private Base64.PaddingOption[] $VALUES;

      @JvmStatic
      fun {
         val var0: Array<Base64.PaddingOption> = $values();
         $VALUES = var0;
         $ENTRIES = EnumEntriesKt.enumEntries(var0);
      }

      @JvmStatic
      fun getEntries(): EnumEntries<Base64.PaddingOption> {
         return $ENTRIES;
      }
   }
}
