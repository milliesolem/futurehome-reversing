package kotlin.io

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Closeable
import java.io.InputStream
import java.io.Reader
import java.io.StringReader
import java.io.StringWriter
import java.io.Writer
import java.net.URL
import java.nio.charset.Charset
import java.util.ArrayList
import kotlin.contracts.InvocationKind
import kotlin.internal.PlatformImplementationsKt

@JvmSynthetic
fun `$r8$lambda$uJuWTzuh3RiWd3H-eZeBP1NitVI`(var0: ArrayList, var1: java.lang.String): Unit {
   return readLines$lambda$1(var0, var1);
}

public inline fun Reader.buffered(bufferSize: Int = 8192): BufferedReader {
   val var2: BufferedReader;
   if (var0 is BufferedReader) {
      var2 = var0 as BufferedReader;
   } else {
      var2 = new BufferedReader(var0, var1);
   }

   return var2;
}

public inline fun Writer.buffered(bufferSize: Int = 8192): BufferedWriter {
   val var2: BufferedWriter;
   if (var0 is BufferedWriter) {
      var2 = var0 as BufferedWriter;
   } else {
      var2 = new BufferedWriter(var0, var1);
   }

   return var2;
}

@JvmSynthetic
fun `buffered$default`(var0: Reader, var1: Int, var2: Int, var3: Any): BufferedReader {
   if ((var2 and 1) != 0) {
      var1 = 8192;
   }

   val var4: BufferedReader;
   if (var0 is BufferedReader) {
      var4 = var0 as BufferedReader;
   } else {
      var4 = new BufferedReader(var0, var1);
   }

   return var4;
}

@JvmSynthetic
fun `buffered$default`(var0: Writer, var1: Int, var2: Int, var3: Any): BufferedWriter {
   if ((var2 and 1) != 0) {
      var1 = 8192;
   }

   val var4: BufferedWriter;
   if (var0 is BufferedWriter) {
      var4 = var0 as BufferedWriter;
   } else {
      var4 = new BufferedWriter(var0, var1);
   }

   return var4;
}

public fun Reader.copyTo(out: Writer, bufferSize: Int = 8192): Long {
   val var5: CharArray = new char[var2];
   var2 = var0.read(var5);

   var var3: Long;
   for (var3 = 0L; var2 >= 0; var2 = var0.read(var5)) {
      var1.write(var5, 0, var2);
      var3 += var2;
   }

   return var3;
}

@JvmSynthetic
fun `copyTo$default`(var0: Reader, var1: Writer, var2: Int, var3: Int, var4: Any): Long {
   if ((var3 and 2) != 0) {
      var2 = 8192;
   }

   return copyTo(var0, var1, var2);
}

public fun Reader.forEachLine(action: (String) -> Unit) {
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
   // 06: aload 1
   // 07: ldc "action"
   // 09: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 0c: aload 0
   // 0d: instanceof java/io/BufferedReader
   // 10: ifeq 1b
   // 13: aload 0
   // 14: checkcast java/io/BufferedReader
   // 17: astore 0
   // 18: goto 27
   // 1b: new java/io/BufferedReader
   // 1e: dup
   // 1f: aload 0
   // 20: sipush 8192
   // 23: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;I)V
   // 26: astore 0
   // 27: aload 0
   // 28: checkcast java/io/Closeable
   // 2b: astore 0
   // 2c: aload 0
   // 2d: checkcast java/io/BufferedReader
   // 30: invokestatic kotlin/io/TextStreamsKt.lineSequence (Ljava/io/BufferedReader;)Lkotlin/sequences/Sequence;
   // 33: invokeinterface kotlin/sequences/Sequence.iterator ()Ljava/util/Iterator; 1
   // 38: astore 2
   // 39: aload 2
   // 3a: invokeinterface java/util/Iterator.hasNext ()Z 1
   // 3f: ifeq 52
   // 42: aload 1
   // 43: aload 2
   // 44: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
   // 49: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
   // 4e: pop
   // 4f: goto 39
   // 52: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 55: astore 1
   // 56: aload 0
   // 57: aconst_null
   // 58: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 5b: return
   // 5c: astore 1
   // 5d: aload 1
   // 5e: athrow
   // 5f: astore 2
   // 60: aload 0
   // 61: aload 1
   // 62: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 65: aload 2
   // 66: athrow
}

public fun BufferedReader.lineSequence(): Sequence<String> {
   return SequencesKt.constrainOnce(new LinesSequence(var0));
}

public fun URL.readBytes(): ByteArray {
   label18: {
      val var9: Closeable = var0.openStream();

      var var10: ByteArray;
      try {
         val var1: InputStream = var9 as InputStream;
         var10 = ByteStreamsKt.readBytes(var1);
      } catch (var4: java.lang.Throwable) {
         val var2: java.lang.Throwable = var4;

         try {
            throw var2;
         } catch (var3: java.lang.Throwable) {
            CloseableKt.closeFinally(var9, var4);
         }
      }

      CloseableKt.closeFinally(var9, null);
      return var10;
   }
}

public fun Reader.readLines(): List<String> {
   val var1: ArrayList = new ArrayList();
   forEachLine(var0, new TextStreamsKt$$ExternalSyntheticLambda0(var1));
   return var1;
}

fun `readLines$lambda$1`(var0: ArrayList, var1: java.lang.String): Unit {
   var0.add(var1);
   return Unit.INSTANCE;
}

public fun Reader.readText(): String {
   val var1: StringWriter = new StringWriter();
   copyTo$default(var0, var1, 0, 2, null);
   val var2: java.lang.String = var1.toString();
   return var2;
}

public inline fun URL.readText(charset: Charset = Charsets.UTF_8): String {
   return new java.lang.String(readBytes(var0), var1);
}

@JvmSynthetic
fun `readText$default`(var0: URL, var1: Charset, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = Charsets.UTF_8;
   }

   return new java.lang.String(readBytes(var0), var1);
}

public inline fun String.reader(): StringReader {
   return new StringReader(var0);
}

public inline fun <T> Reader.useLines(block: (Sequence<String>) -> T): T {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   label40: {
      val var15: BufferedReader;
      if (var0 is BufferedReader) {
         var15 = var0 as BufferedReader;
      } else {
         var15 = new BufferedReader(var0, 8192);
      }

      val var16: Closeable = var15;

      try {
         var17 = var1.invoke(lineSequence(var16 as BufferedReader));
      } catch (var5: java.lang.Throwable) {
         val var2: java.lang.Throwable = var5;

         try {
            throw var2;
         } catch (var4: java.lang.Throwable) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               try {
                  var16.close();
               } catch (var3: java.lang.Throwable) {
                  ;
               }
            } else {
               CloseableKt.closeFinally(var15, var5);
            }
         }
      }

      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var15, null);
      } else {
         var15.close();
      }

      return (T)var17;
   }
}
