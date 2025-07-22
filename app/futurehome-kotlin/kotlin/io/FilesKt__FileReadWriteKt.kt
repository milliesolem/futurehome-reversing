package kotlin.io

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.Reader
import java.io.Writer
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetEncoder
import java.nio.charset.CodingErrorAction
import java.util.ArrayList
import kotlin.contracts.InvocationKind
import kotlin.internal.PlatformImplementationsKt

internal class FilesKt__FileReadWriteKt : FilesKt__FilePathComponentsKt {
   open fun FilesKt__FileReadWriteKt() {
   }

   @JvmStatic
   public fun File.appendBytes(array: ByteArray) {
      label18: {
         val var9: Closeable = new FileOutputStream(var0, true);

         try {
            (var9 as FileOutputStream).write(var1);
         } catch (var4: java.lang.Throwable) {
            val var2: java.lang.Throwable = var4;

            try {
               throw var2;
            } catch (var3: java.lang.Throwable) {
               CloseableKt.closeFinally(var9, var4);
            }
         }

         CloseableKt.closeFinally(var9, null);
      }
   }

   @JvmStatic
   public fun File.appendText(text: String, charset: Charset = Charsets.UTF_8) {
      label18: {
         val var9: Closeable = new FileOutputStream(var0, true);

         try {
            FilesKt.writeTextImpl(var9 as FileOutputStream, var1, var2);
         } catch (var4: java.lang.Throwable) {
            val var11: java.lang.Throwable = var4;

            try {
               throw var11;
            } catch (var3: java.lang.Throwable) {
               CloseableKt.closeFinally(var9, var4);
            }
         }

         CloseableKt.closeFinally(var9, null);
      }
   }

   @JvmStatic
   public inline fun File.bufferedReader(charset: Charset = Charsets.UTF_8, bufferSize: Int = 8192): BufferedReader {
      val var3: Reader = new InputStreamReader(new FileInputStream(var0), var1);
      val var4: BufferedReader;
      if (var3 is BufferedReader) {
         var4 = var3 as BufferedReader;
      } else {
         var4 = new BufferedReader(var3, var2);
      }

      return var4;
   }

   @JvmStatic
   public inline fun File.bufferedWriter(charset: Charset = Charsets.UTF_8, bufferSize: Int = 8192): BufferedWriter {
      val var3: Writer = new OutputStreamWriter(new FileOutputStream(var0), var1);
      val var4: BufferedWriter;
      if (var3 is BufferedWriter) {
         var4 = var3 as BufferedWriter;
      } else {
         var4 = new BufferedWriter(var3, var2);
      }

      return var4;
   }

   @JvmStatic
   internal fun byteBufferForEncoding(chunkSize: Int, encoder: CharsetEncoder): ByteBuffer {
      val var2: ByteBuffer = ByteBuffer.allocate(var0 * (int)((float)Math.ceil((double)var1.maxBytesPerChar())));
      return var2;
   }

   @JvmStatic
   public fun File.forEachBlock(blockSize: Int, action: (ByteArray, Int) -> Unit) {
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
      // 00: aload 0
      // 01: ldc "<this>"
      // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 06: aload 2
      // 07: ldc "action"
      // 09: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 0c: iload 1
      // 0d: sipush 512
      // 10: invokestatic kotlin/ranges/RangesKt.coerceAtLeast (II)I
      // 13: newarray 8
      // 15: astore 3
      // 16: new java/io/FileInputStream
      // 19: dup
      // 1a: aload 0
      // 1b: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 1e: checkcast java/io/Closeable
      // 21: astore 0
      // 22: aload 0
      // 23: checkcast java/io/FileInputStream
      // 26: astore 4
      // 28: aload 4
      // 2a: aload 3
      // 2b: invokevirtual java/io/FileInputStream.read ([B)I
      // 2e: istore 1
      // 2f: iload 1
      // 30: ifgt 3d
      // 33: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 36: astore 2
      // 37: aload 0
      // 38: aconst_null
      // 39: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 3c: return
      // 3d: aload 2
      // 3e: aload 3
      // 3f: iload 1
      // 40: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 43: invokeinterface kotlin/jvm/functions/Function2.invoke (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 48: pop
      // 49: goto 28
      // 4c: astore 3
      // 4d: aload 3
      // 4e: athrow
      // 4f: astore 2
      // 50: aload 0
      // 51: aload 3
      // 52: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 55: aload 2
      // 56: athrow
   }

   @JvmStatic
   public fun File.forEachBlock(action: (ByteArray, Int) -> Unit) {
      FilesKt.forEachBlock(var0, 4096, var1);
   }

   @JvmStatic
   public fun File.forEachLine(charset: Charset = Charsets.UTF_8, action: (String) -> Unit) {
      TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader(new FileInputStream(var0), var1)), var2);
   }

   @JvmStatic
   public inline fun File.inputStream(): FileInputStream {
      return new FileInputStream(var0);
   }

   @JvmStatic
   internal fun Charset.newReplaceEncoder(): CharsetEncoder {
      return var0.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
   }

   @JvmStatic
   public inline fun File.outputStream(): FileOutputStream {
      return new FileOutputStream(var0);
   }

   @JvmStatic
   public inline fun File.printWriter(charset: Charset = Charsets.UTF_8): PrintWriter {
      val var2: Writer = new OutputStreamWriter(new FileOutputStream(var0), var1);
      val var3: BufferedWriter;
      if (var2 is BufferedWriter) {
         var3 = var2 as BufferedWriter;
      } else {
         var3 = new BufferedWriter(var2, 8192);
      }

      return new PrintWriter(var3);
   }

   @JvmStatic
   public fun File.readBytes(): ByteArray {
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
      // 001: ldc "<this>"
      // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 006: new java/io/FileInputStream
      // 009: dup
      // 00a: aload 0
      // 00b: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 00e: checkcast java/io/Closeable
      // 011: astore 8
      // 013: aload 8
      // 015: checkcast java/io/FileInputStream
      // 018: astore 10
      // 01a: aload 0
      // 01b: invokevirtual java/io/File.length ()J
      // 01e: lstore 5
      // 020: lload 5
      // 022: ldc2_w 2147483647
      // 025: lcmp
      // 026: ifgt 10e
      // 029: lload 5
      // 02b: l2i
      // 02c: istore 3
      // 02d: iload 3
      // 02e: newarray 8
      // 030: astore 7
      // 032: iload 3
      // 033: istore 2
      // 034: bipush 0
      // 035: istore 1
      // 036: iload 2
      // 037: ifle 057
      // 03a: aload 10
      // 03c: aload 7
      // 03e: iload 1
      // 03f: iload 2
      // 040: invokevirtual java/io/FileInputStream.read ([BII)I
      // 043: istore 4
      // 045: iload 4
      // 047: iflt 057
      // 04a: iload 2
      // 04b: iload 4
      // 04d: isub
      // 04e: istore 2
      // 04f: iload 1
      // 050: iload 4
      // 052: iadd
      // 053: istore 1
      // 054: goto 036
      // 057: iload 2
      // 058: ifle 06c
      // 05b: aload 7
      // 05d: iload 1
      // 05e: invokestatic java/util/Arrays.copyOf ([BI)[B
      // 061: astore 0
      // 062: aload 0
      // 063: ldc_w "copyOf(...)"
      // 066: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 069: goto 0d1
      // 06c: aload 10
      // 06e: invokevirtual java/io/FileInputStream.read ()I
      // 071: istore 1
      // 072: iload 1
      // 073: bipush -1
      // 074: if_icmpne 07d
      // 077: aload 7
      // 079: astore 0
      // 07a: goto 0d1
      // 07d: new kotlin/io/ExposingBufferByteArrayOutputStream
      // 080: astore 9
      // 082: aload 9
      // 084: sipush 8193
      // 087: invokespecial kotlin/io/ExposingBufferByteArrayOutputStream.<init> (I)V
      // 08a: aload 9
      // 08c: iload 1
      // 08d: invokevirtual kotlin/io/ExposingBufferByteArrayOutputStream.write (I)V
      // 090: aload 10
      // 092: checkcast java/io/InputStream
      // 095: aload 9
      // 097: checkcast java/io/OutputStream
      // 09a: bipush 0
      // 09b: bipush 2
      // 09c: aconst_null
      // 09d: invokestatic kotlin/io/ByteStreamsKt.copyTo$default (Ljava/io/InputStream;Ljava/io/OutputStream;IILjava/lang/Object;)J
      // 0a0: pop2
      // 0a1: aload 9
      // 0a3: invokevirtual kotlin/io/ExposingBufferByteArrayOutputStream.size ()I
      // 0a6: iload 3
      // 0a7: iadd
      // 0a8: istore 1
      // 0a9: iload 1
      // 0aa: iflt 0d9
      // 0ad: aload 9
      // 0af: invokevirtual kotlin/io/ExposingBufferByteArrayOutputStream.getBuffer ()[B
      // 0b2: astore 0
      // 0b3: aload 7
      // 0b5: iload 1
      // 0b6: invokestatic java/util/Arrays.copyOf ([BI)[B
      // 0b9: astore 7
      // 0bb: aload 7
      // 0bd: ldc_w "copyOf(...)"
      // 0c0: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 0c3: aload 0
      // 0c4: aload 7
      // 0c6: iload 3
      // 0c7: bipush 0
      // 0c8: aload 9
      // 0ca: invokevirtual kotlin/io/ExposingBufferByteArrayOutputStream.size ()I
      // 0cd: invokestatic kotlin/collections/ArraysKt.copyInto ([B[BIII)[B
      // 0d0: astore 0
      // 0d1: aload 8
      // 0d3: aconst_null
      // 0d4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0d7: aload 0
      // 0d8: areturn
      // 0d9: new java/lang/OutOfMemoryError
      // 0dc: astore 7
      // 0de: new java/lang/StringBuilder
      // 0e1: astore 9
      // 0e3: aload 9
      // 0e5: invokespecial java/lang/StringBuilder.<init> ()V
      // 0e8: aload 9
      // 0ea: ldc_w "File "
      // 0ed: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f0: pop
      // 0f1: aload 9
      // 0f3: aload 0
      // 0f4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 0f7: pop
      // 0f8: aload 9
      // 0fa: ldc_w " is too big to fit in memory."
      // 0fd: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 100: pop
      // 101: aload 7
      // 103: aload 9
      // 105: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 108: invokespecial java/lang/OutOfMemoryError.<init> (Ljava/lang/String;)V
      // 10b: aload 7
      // 10d: athrow
      // 10e: new java/lang/OutOfMemoryError
      // 111: astore 9
      // 113: new java/lang/StringBuilder
      // 116: astore 7
      // 118: aload 7
      // 11a: ldc_w "File "
      // 11d: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 120: aload 7
      // 122: aload 0
      // 123: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 126: pop
      // 127: aload 7
      // 129: ldc_w " is too big ("
      // 12c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12f: pop
      // 130: aload 7
      // 132: lload 5
      // 134: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 137: pop
      // 138: aload 7
      // 13a: ldc_w " bytes) to fit in memory."
      // 13d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 140: pop
      // 141: aload 9
      // 143: aload 7
      // 145: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 148: invokespecial java/lang/OutOfMemoryError.<init> (Ljava/lang/String;)V
      // 14b: aload 9
      // 14d: athrow
      // 14e: astore 0
      // 14f: aload 0
      // 150: athrow
      // 151: astore 7
      // 153: aload 8
      // 155: aload 0
      // 156: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 159: aload 7
      // 15b: athrow
   }

   @JvmStatic
   public fun File.readLines(charset: Charset = Charsets.UTF_8): List<String> {
      val var2: ArrayList = new ArrayList();
      FilesKt.forEachLine(var0, var1, new FilesKt__FileReadWriteKt$$ExternalSyntheticLambda0(var2));
      return var2;
   }

   @JvmStatic
   fun `readLines$lambda$9$FilesKt__FileReadWriteKt`(var0: ArrayList, var1: java.lang.String): Unit {
      var0.add(var1);
      return Unit.INSTANCE;
   }

   @JvmStatic
   public fun File.readText(charset: Charset = Charsets.UTF_8): String {
      label18: {
         val var9: Closeable = new InputStreamReader(new FileInputStream(var0), var1);

         try {
            var11 = TextStreamsKt.readText(var9 as InputStreamReader);
         } catch (var4: java.lang.Throwable) {
            val var10: java.lang.Throwable = var4;

            try {
               throw var10;
            } catch (var3: java.lang.Throwable) {
               CloseableKt.closeFinally(var9, var4);
            }
         }

         CloseableKt.closeFinally(var9, null);
         return var11;
      }
   }

   @JvmStatic
   public inline fun File.reader(charset: Charset = Charsets.UTF_8): InputStreamReader {
      return new InputStreamReader(new FileInputStream(var0), var1);
   }

   @JvmStatic
   public inline fun <T> File.useLines(charset: Charset = Charsets.UTF_8, block: (Sequence<String>) -> T): T {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      label40: {
         val var15: Reader = new InputStreamReader(new FileInputStream(var0), var1);
         val var16: BufferedReader;
         if (var15 is BufferedReader) {
            var16 = var15 as BufferedReader;
         } else {
            var16 = new BufferedReader(var15, 8192);
         }

         val var17: Closeable = var16;

         try {
            var18 = var2.invoke(TextStreamsKt.lineSequence(var17 as BufferedReader));
         } catch (var5: java.lang.Throwable) {
            val var19: java.lang.Throwable = var5;

            try {
               throw var19;
            } catch (var4: java.lang.Throwable) {
               if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                  try {
                     var17.close();
                  } catch (var3: java.lang.Throwable) {
                     ;
                  }
               } else {
                  CloseableKt.closeFinally(var16, var5);
               }
            }
         }

         if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
            CloseableKt.closeFinally(var16, null);
         } else {
            var16.close();
         }

         return (T)var18;
      }
   }

   @JvmStatic
   public fun File.writeBytes(array: ByteArray) {
      label18: {
         val var9: Closeable = new FileOutputStream(var0);

         try {
            (var9 as FileOutputStream).write(var1);
         } catch (var4: java.lang.Throwable) {
            val var10: java.lang.Throwable = var4;

            try {
               throw var10;
            } catch (var3: java.lang.Throwable) {
               CloseableKt.closeFinally(var9, var4);
            }
         }

         CloseableKt.closeFinally(var9, null);
      }
   }

   @JvmStatic
   public fun File.writeText(text: String, charset: Charset = Charsets.UTF_8) {
      label18: {
         val var9: Closeable = new FileOutputStream(var0);

         try {
            FilesKt.writeTextImpl(var9 as FileOutputStream, var1, var2);
         } catch (var4: java.lang.Throwable) {
            val var11: java.lang.Throwable = var4;

            try {
               throw var11;
            } catch (var3: java.lang.Throwable) {
               CloseableKt.closeFinally(var9, var4);
            }
         }

         CloseableKt.closeFinally(var9, null);
      }
   }

   @JvmStatic
   internal fun OutputStream.writeTextImpl(text: String, charset: Charset) {
      if (var1.length() < 16384) {
         val var11: ByteArray = var1.getBytes(var2);
         var0.write(var11);
      } else {
         val var9: CharsetEncoder = FilesKt.newReplaceEncoder(var2);
         val var10: CharBuffer = CharBuffer.allocate(8192);
         val var12: ByteBuffer = FilesKt.byteBufferForEncoding(8192, var9);
         var var4: Int = 0;
         var var3: Byte = 0;

         while (var4 < var1.length()) {
            val var6: Int = Math.min(8192 - var3, var1.length() - var4);
            val var5: Int = var4 + var6;
            val var8: CharArray = var10.array();
            var1.getChars(var4, var5, var8, var3);
            ((Buffer)var10).limit(var6 + var3);
            var4 = var1.length();
            var3 = 1;
            val var7: Boolean;
            if (var5 == var4) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (!var9.encode(var10, var12, var7).isUnderflow()) {
               throw new IllegalStateException("Check failed.");
            }

            var0.write(var12.array(), 0, var12.position());
            if (var10.position() != var10.limit()) {
               var10.put(0, var10.get());
            } else {
               var3 = 0;
            }

            ((Buffer)var10).clear();
            ((Buffer)var12).clear();
            var4 = var5;
         }
      }
   }

   @JvmStatic
   public inline fun File.writer(charset: Charset = Charsets.UTF_8): OutputStreamWriter {
      return new OutputStreamWriter(new FileOutputStream(var0), var1);
   }
}
