package kotlin.io

import java.io.InputStream
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CoderResult
import kotlin.jvm.internal.Intrinsics

internal object LineReader {
   private const val BUFFER_SIZE: Int = 32
   private final lateinit var decoder: CharsetDecoder
   private final var directEOL: Boolean
   private final val bytes: ByteArray
   private final val chars: CharArray
   private final val byteBuf: ByteBuffer
   private final val charBuf: CharBuffer
   private final val sb: StringBuilder = new StringBuilder()

   @JvmStatic
   fun {
      val var1: ByteArray = new byte[32];
      bytes = var1;
      val var0: CharArray = new char[32];
      chars = var0;
      val var3: ByteBuffer = ByteBuffer.wrap(var1);
      byteBuf = var3;
      val var2: CharBuffer = CharBuffer.wrap(var0);
      charBuf = var2;
   }

   private fun compactBytes(): Int {
      val var2: ByteBuffer = byteBuf;
      byteBuf.compact();
      val var1: Int = var2.position();
      ((Buffer)var2).position(0);
      return var1;
   }

   private fun decode(endOfInput: Boolean): Int {
      while (true) {
         var var3: CharsetDecoder = decoder;
         if (decoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            var3 = null;
         }

         val var9: CharBuffer = charBuf;
         val var7: CoderResult = var3.decode(byteBuf, charBuf, var1);
         if (var7.isError()) {
            this.resetAll();
            var7.throwException();
         }

         var var2: Int = var9.position();
         if (!var7.isOverflow()) {
            return var2;
         }

         val var8: CharArray = chars;
         sb.append(chars, 0, --var2);
         ((Buffer)var9).position(0);
         ((Buffer)var9).limit(32);
         var9.put(var8[var2]);
      }
   }

   private fun decodeEndOfInput(nBytes: Int, nChars: Int): Int {
      val var5: ByteBuffer = byteBuf;
      ((Buffer)byteBuf).limit(var1);
      ((Buffer)charBuf).position(var2);
      var1 = this.decode(true);
      var var3: CharsetDecoder = decoder;
      if (decoder == null) {
         Intrinsics.throwUninitializedPropertyAccessException("decoder");
         var3 = null;
      }

      var3.reset();
      ((Buffer)var5).position(0);
      return var1;
   }

   private fun resetAll() {
      var var1: CharsetDecoder = decoder;
      if (decoder == null) {
         Intrinsics.throwUninitializedPropertyAccessException("decoder");
         var1 = null;
      }

      var1.reset();
      ((Buffer)byteBuf).position(0);
      sb.setLength(0);
   }

   private fun trimStringBuilder() {
      val var1: StringBuilder = sb;
      sb.setLength(32);
      var1.trimToSize();
   }

   private fun updateCharset(charset: Charset) {
      decoder = var1.newDecoder();
      val var5: ByteBuffer = byteBuf;
      ((Buffer)byteBuf).clear();
      val var6: CharBuffer = charBuf;
      ((Buffer)charBuf).clear();
      var5.put((byte)10);
      ((Buffer)var5).flip();
      var var7: CharsetDecoder = decoder;
      if (decoder == null) {
         Intrinsics.throwUninitializedPropertyAccessException("decoder");
         var7 = null;
      }

      var7.decode(var5, var6, false);
      var var2: Boolean = false;
      if (var6.position() == 1) {
         var2 = false;
         if (var6.get(0) == '\n') {
            var2 = true;
         }
      }

      directEOL = var2;
      this.resetAll();
   }

   public fun readLine(inputStream: InputStream, charset: Charset): String? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc "inputStream"
      // 005: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 008: aload 2
      // 009: ldc "charset"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: getstatic kotlin/io/LineReader.decoder Ljava/nio/charset/CharsetDecoder;
      // 011: astore 8
      // 013: aload 8
      // 015: ifnull 035
      // 018: aload 8
      // 01a: astore 7
      // 01c: aload 8
      // 01e: ifnonnull 029
      // 021: ldc "decoder"
      // 023: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 026: aconst_null
      // 027: astore 7
      // 029: aload 7
      // 02b: invokevirtual java/nio/charset/CharsetDecoder.charset ()Ljava/nio/charset/Charset;
      // 02e: aload 2
      // 02f: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 032: ifne 03a
      // 035: aload 0
      // 036: aload 2
      // 037: invokespecial kotlin/io/LineReader.updateCharset (Ljava/nio/charset/Charset;)V
      // 03a: bipush 0
      // 03b: istore 3
      // 03c: bipush 0
      // 03d: istore 4
      // 03f: aload 1
      // 040: invokevirtual java/io/InputStream.read ()I
      // 043: istore 6
      // 045: iload 6
      // 047: bipush -1
      // 048: if_icmpne 075
      // 04b: getstatic kotlin/io/LineReader.sb Ljava/lang/StringBuilder;
      // 04e: checkcast java/lang/CharSequence
      // 051: invokeinterface java/lang/CharSequence.length ()I 1
      // 056: istore 5
      // 058: iload 5
      // 05a: ifne 06a
      // 05d: iload 3
      // 05e: ifne 06a
      // 061: iload 4
      // 063: ifne 06a
      // 066: aload 0
      // 067: monitorexit
      // 068: aconst_null
      // 069: areturn
      // 06a: aload 0
      // 06b: iload 3
      // 06c: iload 4
      // 06e: invokespecial kotlin/io/LineReader.decodeEndOfInput (II)I
      // 071: istore 3
      // 072: goto 0d7
      // 075: getstatic kotlin/io/LineReader.bytes [B
      // 078: astore 2
      // 079: iload 3
      // 07a: bipush 1
      // 07b: iadd
      // 07c: istore 5
      // 07e: aload 2
      // 07f: iload 3
      // 080: iload 6
      // 082: i2b
      // 083: bastore
      // 084: iload 6
      // 086: bipush 10
      // 088: if_icmpeq 0a1
      // 08b: iload 5
      // 08d: bipush 32
      // 08f: if_icmpeq 0a1
      // 092: getstatic kotlin/io/LineReader.directEOL Z
      // 095: ifne 09b
      // 098: goto 0a1
      // 09b: iload 5
      // 09d: istore 3
      // 09e: goto 03f
      // 0a1: getstatic kotlin/io/LineReader.byteBuf Ljava/nio/ByteBuffer;
      // 0a4: astore 2
      // 0a5: aload 2
      // 0a6: iload 5
      // 0a8: invokevirtual java/nio/ByteBuffer.limit (I)Ljava/nio/Buffer;
      // 0ab: pop
      // 0ac: getstatic kotlin/io/LineReader.charBuf Ljava/nio/CharBuffer;
      // 0af: iload 4
      // 0b1: invokevirtual java/nio/CharBuffer.position (I)Ljava/nio/Buffer;
      // 0b4: pop
      // 0b5: aload 0
      // 0b6: bipush 0
      // 0b7: invokespecial kotlin/io/LineReader.decode (Z)I
      // 0ba: istore 4
      // 0bc: iload 4
      // 0be: ifle 159
      // 0c1: getstatic kotlin/io/LineReader.chars [C
      // 0c4: iload 4
      // 0c6: bipush 1
      // 0c7: isub
      // 0c8: caload
      // 0c9: bipush 10
      // 0cb: if_icmpne 159
      // 0ce: aload 2
      // 0cf: bipush 0
      // 0d0: invokevirtual java/nio/ByteBuffer.position (I)Ljava/nio/Buffer;
      // 0d3: pop
      // 0d4: iload 4
      // 0d6: istore 3
      // 0d7: iload 3
      // 0d8: istore 4
      // 0da: iload 3
      // 0db: ifle 10b
      // 0de: getstatic kotlin/io/LineReader.chars [C
      // 0e1: astore 1
      // 0e2: iload 3
      // 0e3: istore 4
      // 0e5: aload 1
      // 0e6: iload 3
      // 0e7: bipush 1
      // 0e8: isub
      // 0e9: caload
      // 0ea: bipush 10
      // 0ec: if_icmpne 10b
      // 0ef: iload 3
      // 0f0: bipush 1
      // 0f1: isub
      // 0f2: istore 4
      // 0f4: iload 4
      // 0f6: ifle 10b
      // 0f9: aload 1
      // 0fa: iload 3
      // 0fb: bipush 2
      // 0fc: isub
      // 0fd: caload
      // 0fe: bipush 13
      // 100: if_icmpne 10b
      // 103: iload 3
      // 104: bipush 2
      // 105: isub
      // 106: istore 4
      // 108: goto 10b
      // 10b: getstatic kotlin/io/LineReader.sb Ljava/lang/StringBuilder;
      // 10e: astore 1
      // 10f: aload 1
      // 110: checkcast java/lang/CharSequence
      // 113: invokeinterface java/lang/CharSequence.length ()I 1
      // 118: ifne 12d
      // 11b: new java/lang/String
      // 11e: dup
      // 11f: getstatic kotlin/io/LineReader.chars [C
      // 122: bipush 0
      // 123: iload 4
      // 125: invokespecial java/lang/String.<init> ([CII)V
      // 128: astore 1
      // 129: aload 0
      // 12a: monitorexit
      // 12b: aload 1
      // 12c: areturn
      // 12d: aload 1
      // 12e: getstatic kotlin/io/LineReader.chars [C
      // 131: bipush 0
      // 132: iload 4
      // 134: invokevirtual java/lang/StringBuilder.append ([CII)Ljava/lang/StringBuilder;
      // 137: pop
      // 138: aload 1
      // 139: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 13c: astore 2
      // 13d: aload 2
      // 13e: ldc "toString(...)"
      // 140: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 143: aload 1
      // 144: invokevirtual java/lang/StringBuilder.length ()I
      // 147: bipush 32
      // 149: if_icmple 150
      // 14c: aload 0
      // 14d: invokespecial kotlin/io/LineReader.trimStringBuilder ()V
      // 150: aload 1
      // 151: bipush 0
      // 152: invokevirtual java/lang/StringBuilder.setLength (I)V
      // 155: aload 0
      // 156: monitorexit
      // 157: aload 2
      // 158: areturn
      // 159: aload 0
      // 15a: invokespecial kotlin/io/LineReader.compactBytes ()I
      // 15d: istore 3
      // 15e: goto 03f
      // 161: astore 1
      // 162: aload 0
      // 163: monitorexit
      // 164: aload 1
      // 165: athrow
   }
}
