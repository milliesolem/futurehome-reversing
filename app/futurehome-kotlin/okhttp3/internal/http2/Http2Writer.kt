package okhttp3.internal.http2

import java.io.Closeable
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okhttp3.internal.http2.Hpack.Writer
import okio.Buffer
import okio.BufferedSink

public class Http2Writer(sink: BufferedSink, client: Boolean) : Closeable {
   private final val client: Boolean
   private final var closed: Boolean
   private final val hpackBuffer: Buffer
   public final val hpackWriter: Writer
   private final var maxFrameSize: Int
   private final val sink: BufferedSink

   init {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      super();
      this.sink = var1;
      this.client = var2;
      val var3: Buffer = new Buffer();
      this.hpackBuffer = var3;
      this.maxFrameSize = 16384;
      this.hpackWriter = new Hpack.Writer(0, false, var3, 3, null);
   }

   @Throws(java/io/IOException::class)
   private fun writeContinuationFrames(streamId: Int, byteCount: Long) {
      while (var2 > 0L) {
         val var6: Long = Math.min((long)this.maxFrameSize, var2);
         var2 -= var6;
         val var5: Int = (int)var6;
         val var4: Byte;
         if (var2 == 0L) {
            var4 = 4;
         } else {
            var4 = 0;
         }

         this.frameHeader(var1, var5, 9, var4);
         this.sink.write(this.hpackBuffer, var6);
      }
   }

   @Throws(java/io/IOException::class)
   public fun applyAndAckSettings(peerSettings: Settings) {
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
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc "peerSettings"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 0c: ifne 42
      // 0f: aload 0
      // 10: aload 1
      // 11: aload 0
      // 12: getfield okhttp3/internal/http2/Http2Writer.maxFrameSize I
      // 15: invokevirtual okhttp3/internal/http2/Settings.getMaxFrameSize (I)I
      // 18: putfield okhttp3/internal/http2/Http2Writer.maxFrameSize I
      // 1b: aload 1
      // 1c: invokevirtual okhttp3/internal/http2/Settings.getHeaderTableSize ()I
      // 1f: bipush -1
      // 20: if_icmpeq 2e
      // 23: aload 0
      // 24: getfield okhttp3/internal/http2/Http2Writer.hpackWriter Lokhttp3/internal/http2/Hpack$Writer;
      // 27: aload 1
      // 28: invokevirtual okhttp3/internal/http2/Settings.getHeaderTableSize ()I
      // 2b: invokevirtual okhttp3/internal/http2/Hpack$Writer.resizeHeaderTable (I)V
      // 2e: aload 0
      // 2f: bipush 0
      // 30: bipush 0
      // 31: bipush 4
      // 32: bipush 1
      // 33: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 36: aload 0
      // 37: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 3a: invokeinterface okio/BufferedSink.flush ()V 1
      // 3f: aload 0
      // 40: monitorexit
      // 41: return
      // 42: new java/io/IOException
      // 45: astore 1
      // 46: aload 1
      // 47: ldc "closed"
      // 49: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 4c: aload 1
      // 4d: checkcast java/lang/Throwable
      // 50: athrow
      // 51: astore 1
      // 52: aload 0
      // 53: monitorexit
      // 54: aload 1
      // 55: athrow
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: bipush 1
      // 04: putfield okhttp3/internal/http2/Http2Writer.closed Z
      // 07: aload 0
      // 08: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 0b: invokeinterface okio/BufferedSink.close ()V 1
      // 10: aload 0
      // 11: monitorexit
      // 12: return
      // 13: astore 1
      // 14: aload 0
      // 15: monitorexit
      // 16: aload 1
      // 17: athrow
   }

   @Throws(java/io/IOException::class)
   public fun connectionPreface() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 06: ifne 60
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Writer.client Z
      // 0d: istore 1
      // 0e: iload 1
      // 0f: ifne 15
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: getstatic okhttp3/internal/http2/Http2Writer.logger Ljava/util/logging/Logger;
      // 18: astore 2
      // 19: aload 2
      // 1a: getstatic java/util/logging/Level.FINE Ljava/util/logging/Level;
      // 1d: invokevirtual java/util/logging/Logger.isLoggable (Ljava/util/logging/Level;)Z
      // 20: ifeq 47
      // 23: new java/lang/StringBuilder
      // 26: astore 3
      // 27: aload 3
      // 28: ldc ">> CONNECTION "
      // 2a: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 2d: aload 3
      // 2e: getstatic okhttp3/internal/http2/Http2.CONNECTION_PREFACE Lokio/ByteString;
      // 31: invokevirtual okio/ByteString.hex ()Ljava/lang/String;
      // 34: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 37: pop
      // 38: aload 2
      // 39: aload 3
      // 3a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3d: bipush 0
      // 3e: anewarray 4
      // 41: invokestatic okhttp3/internal/Util.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 44: invokevirtual java/util/logging/Logger.fine (Ljava/lang/String;)V
      // 47: aload 0
      // 48: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 4b: getstatic okhttp3/internal/http2/Http2.CONNECTION_PREFACE Lokio/ByteString;
      // 4e: invokeinterface okio/BufferedSink.write (Lokio/ByteString;)Lokio/BufferedSink; 2
      // 53: pop
      // 54: aload 0
      // 55: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 58: invokeinterface okio/BufferedSink.flush ()V 1
      // 5d: aload 0
      // 5e: monitorexit
      // 5f: return
      // 60: new java/io/IOException
      // 63: astore 2
      // 64: aload 2
      // 65: ldc "closed"
      // 67: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 6a: aload 2
      // 6b: checkcast java/lang/Throwable
      // 6e: athrow
      // 6f: astore 2
      // 70: aload 0
      // 71: monitorexit
      // 72: aload 2
      // 73: athrow
   }

   @Throws(java/io/IOException::class)
   public fun data(outFinished: Boolean, streamId: Int, source: Buffer?, byteCount: Int) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 06: ifne 15
      // 09: aload 0
      // 0a: iload 2
      // 0b: iload 1
      // 0c: aload 3
      // 0d: iload 4
      // 0f: invokevirtual okhttp3/internal/http2/Http2Writer.dataFrame (IILokio/Buffer;I)V
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: new java/io/IOException
      // 18: astore 3
      // 19: aload 3
      // 1a: ldc "closed"
      // 1c: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 1f: aload 3
      // 20: checkcast java/lang/Throwable
      // 23: athrow
      // 24: astore 3
      // 25: aload 0
      // 26: monitorexit
      // 27: aload 3
      // 28: athrow
   }

   @Throws(java/io/IOException::class)
   public fun dataFrame(streamId: Int, flags: Int, buffer: Buffer?, byteCount: Int) {
      this.frameHeader(var1, var4, 0, var2);
      if (var4 > 0) {
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         this.sink.write(var3, (long)var4);
      }
   }

   @Throws(java/io/IOException::class)
   public fun flush() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 06: ifne 15
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 0d: invokeinterface okio/BufferedSink.flush ()V 1
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: new java/io/IOException
      // 18: astore 1
      // 19: aload 1
      // 1a: ldc "closed"
      // 1c: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 1f: aload 1
      // 20: checkcast java/lang/Throwable
      // 23: athrow
      // 24: astore 1
      // 25: aload 0
      // 26: monitorexit
      // 27: aload 1
      // 28: athrow
   }

   @Throws(java/io/IOException::class)
   public fun frameHeader(streamId: Int, length: Int, type: Int, flags: Int) {
      val var7: Logger = logger;
      if (logger.isLoggable(Level.FINE)) {
         var7.fine(Http2.INSTANCE.frameLog(false, var1, var2, var3, var4));
      }

      var var8: Boolean;
      if (var2 <= this.maxFrameSize) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         if (((int)2147483648L and var1) == 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            Util.writeMedium(this.sink, var2);
            this.sink.writeByte(var3 and 255);
            this.sink.writeByte(var4 and 255);
            this.sink.writeInt(var1 and Integer.MAX_VALUE);
         } else {
            val var11: StringBuilder = new StringBuilder("reserved bit set: ");
            var11.append(var1);
            throw (new IllegalArgumentException(var11.toString().toString())) as java.lang.Throwable;
         }
      } else {
         val var10: StringBuilder = new StringBuilder("FRAME_SIZE_ERROR length > ");
         var10.append(this.maxFrameSize);
         var10.append(": ");
         var10.append(var2);
         throw (new IllegalArgumentException(var10.toString().toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun goAway(lastGoodStreamId: Int, errorCode: ErrorCode, debugData: ByteArray) {
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
      // 01: monitorenter
      // 02: aload 2
      // 03: ldc_w "errorCode"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 3
      // 0a: ldc_w "debugData"
      // 0d: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 10: aload 0
      // 11: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 14: ifne 95
      // 17: aload 2
      // 18: invokevirtual okhttp3/internal/http2/ErrorCode.getHttpCode ()I
      // 1b: istore 4
      // 1d: bipush 1
      // 1e: istore 5
      // 20: iload 4
      // 22: bipush -1
      // 23: if_icmpeq 2c
      // 26: bipush 1
      // 27: istore 4
      // 29: goto 2f
      // 2c: bipush 0
      // 2d: istore 4
      // 2f: iload 4
      // 31: ifeq 82
      // 34: aload 0
      // 35: bipush 0
      // 36: aload 3
      // 37: arraylength
      // 38: bipush 8
      // 3a: iadd
      // 3b: bipush 7
      // 3d: bipush 0
      // 3e: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 41: aload 0
      // 42: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 45: iload 1
      // 46: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 4b: pop
      // 4c: aload 0
      // 4d: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 50: aload 2
      // 51: invokevirtual okhttp3/internal/http2/ErrorCode.getHttpCode ()I
      // 54: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 59: pop
      // 5a: aload 3
      // 5b: arraylength
      // 5c: ifne 65
      // 5f: iload 5
      // 61: istore 1
      // 62: goto 67
      // 65: bipush 0
      // 66: istore 1
      // 67: iload 1
      // 68: ifne 76
      // 6b: aload 0
      // 6c: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 6f: aload 3
      // 70: invokeinterface okio/BufferedSink.write ([B)Lokio/BufferedSink; 2
      // 75: pop
      // 76: aload 0
      // 77: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 7a: invokeinterface okio/BufferedSink.flush ()V 1
      // 7f: aload 0
      // 80: monitorexit
      // 81: return
      // 82: new java/lang/IllegalArgumentException
      // 85: astore 2
      // 86: aload 2
      // 87: ldc_w "errorCode.httpCode == -1"
      // 8a: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 8d: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 90: aload 2
      // 91: checkcast java/lang/Throwable
      // 94: athrow
      // 95: new java/io/IOException
      // 98: astore 2
      // 99: aload 2
      // 9a: ldc "closed"
      // 9c: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 9f: aload 2
      // a0: checkcast java/lang/Throwable
      // a3: athrow
      // a4: astore 2
      // a5: aload 0
      // a6: monitorexit
      // a7: aload 2
      // a8: athrow
   }

   @Throws(java/io/IOException::class)
   public fun headers(outFinished: Boolean, streamId: Int, headerBlock: List<Header>) {
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
      // 01: monitorenter
      // 02: aload 3
      // 03: ldc_w "headerBlock"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 0d: ifne 7c
      // 10: aload 0
      // 11: getfield okhttp3/internal/http2/Http2Writer.hpackWriter Lokhttp3/internal/http2/Hpack$Writer;
      // 14: aload 3
      // 15: invokevirtual okhttp3/internal/http2/Hpack$Writer.writeHeaders (Ljava/util/List;)V
      // 18: aload 0
      // 19: getfield okhttp3/internal/http2/Http2Writer.hpackBuffer Lokio/Buffer;
      // 1c: invokevirtual okio/Buffer.size ()J
      // 1f: lstore 9
      // 21: aload 0
      // 22: getfield okhttp3/internal/http2/Http2Writer.maxFrameSize I
      // 25: i2l
      // 26: lload 9
      // 28: invokestatic java/lang/Math.min (JJ)J
      // 2b: lstore 7
      // 2d: lload 9
      // 2f: lload 7
      // 31: lcmp
      // 32: istore 6
      // 34: iload 6
      // 36: ifne 3f
      // 39: bipush 4
      // 3a: istore 4
      // 3c: goto 42
      // 3f: bipush 0
      // 40: istore 4
      // 42: iload 4
      // 44: istore 5
      // 46: iload 1
      // 47: ifeq 50
      // 4a: iload 4
      // 4c: bipush 1
      // 4d: ior
      // 4e: istore 5
      // 50: aload 0
      // 51: iload 2
      // 52: lload 7
      // 54: l2i
      // 55: bipush 1
      // 56: iload 5
      // 58: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 5b: aload 0
      // 5c: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 5f: aload 0
      // 60: getfield okhttp3/internal/http2/Http2Writer.hpackBuffer Lokio/Buffer;
      // 63: lload 7
      // 65: invokeinterface okio/BufferedSink.write (Lokio/Buffer;J)V 4
      // 6a: iload 6
      // 6c: ifle 79
      // 6f: aload 0
      // 70: iload 2
      // 71: lload 9
      // 73: lload 7
      // 75: lsub
      // 76: invokespecial okhttp3/internal/http2/Http2Writer.writeContinuationFrames (IJ)V
      // 79: aload 0
      // 7a: monitorexit
      // 7b: return
      // 7c: new java/io/IOException
      // 7f: astore 3
      // 80: aload 3
      // 81: ldc "closed"
      // 83: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 86: aload 3
      // 87: checkcast java/lang/Throwable
      // 8a: athrow
      // 8b: astore 3
      // 8c: aload 0
      // 8d: monitorexit
      // 8e: aload 3
      // 8f: athrow
   }

   public fun maxDataLength(): Int {
      return this.maxFrameSize;
   }

   @Throws(java/io/IOException::class)
   public fun ping(ack: Boolean, payload1: Int, payload2: Int) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 06: ifne 35
      // 09: aload 0
      // 0a: bipush 0
      // 0b: bipush 8
      // 0d: bipush 6
      // 0f: iload 1
      // 10: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 13: aload 0
      // 14: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 17: iload 2
      // 18: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 1d: pop
      // 1e: aload 0
      // 1f: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 22: iload 3
      // 23: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 28: pop
      // 29: aload 0
      // 2a: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 2d: invokeinterface okio/BufferedSink.flush ()V 1
      // 32: aload 0
      // 33: monitorexit
      // 34: return
      // 35: new java/io/IOException
      // 38: astore 4
      // 3a: aload 4
      // 3c: ldc "closed"
      // 3e: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 41: aload 4
      // 43: checkcast java/lang/Throwable
      // 46: athrow
      // 47: astore 4
      // 49: aload 0
      // 4a: monitorexit
      // 4b: aload 4
      // 4d: athrow
   }

   @Throws(java/io/IOException::class)
   public fun pushPromise(streamId: Int, promisedStreamId: Int, requestHeaders: List<Header>) {
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
      // 01: monitorenter
      // 02: aload 3
      // 03: ldc_w "requestHeaders"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 0d: ifne 87
      // 10: aload 0
      // 11: getfield okhttp3/internal/http2/Http2Writer.hpackWriter Lokhttp3/internal/http2/Hpack$Writer;
      // 14: aload 3
      // 15: invokevirtual okhttp3/internal/http2/Hpack$Writer.writeHeaders (Ljava/util/List;)V
      // 18: aload 0
      // 19: getfield okhttp3/internal/http2/Http2Writer.hpackBuffer Lokio/Buffer;
      // 1c: invokevirtual okio/Buffer.size ()J
      // 1f: lstore 7
      // 21: aload 0
      // 22: getfield okhttp3/internal/http2/Http2Writer.maxFrameSize I
      // 25: i2l
      // 26: ldc2_w 4
      // 29: lsub
      // 2a: lload 7
      // 2c: invokestatic java/lang/Math.min (JJ)J
      // 2f: l2i
      // 30: istore 6
      // 32: iload 6
      // 34: i2l
      // 35: lstore 9
      // 37: lload 7
      // 39: lload 9
      // 3b: lcmp
      // 3c: istore 5
      // 3e: iload 5
      // 40: ifne 49
      // 43: bipush 4
      // 44: istore 4
      // 46: goto 4c
      // 49: bipush 0
      // 4a: istore 4
      // 4c: aload 0
      // 4d: iload 1
      // 4e: iload 6
      // 50: bipush 4
      // 51: iadd
      // 52: bipush 5
      // 53: iload 4
      // 55: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 58: aload 0
      // 59: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 5c: iload 2
      // 5d: ldc 2147483647
      // 5f: iand
      // 60: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 65: pop
      // 66: aload 0
      // 67: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 6a: aload 0
      // 6b: getfield okhttp3/internal/http2/Http2Writer.hpackBuffer Lokio/Buffer;
      // 6e: lload 9
      // 70: invokeinterface okio/BufferedSink.write (Lokio/Buffer;J)V 4
      // 75: iload 5
      // 77: ifle 84
      // 7a: aload 0
      // 7b: iload 1
      // 7c: lload 7
      // 7e: lload 9
      // 80: lsub
      // 81: invokespecial okhttp3/internal/http2/Http2Writer.writeContinuationFrames (IJ)V
      // 84: aload 0
      // 85: monitorexit
      // 86: return
      // 87: new java/io/IOException
      // 8a: astore 3
      // 8b: aload 3
      // 8c: ldc "closed"
      // 8e: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 91: aload 3
      // 92: checkcast java/lang/Throwable
      // 95: athrow
      // 96: astore 3
      // 97: aload 0
      // 98: monitorexit
      // 99: aload 3
      // 9a: athrow
   }

   @Throws(java/io/IOException::class)
   public fun rstStream(streamId: Int, errorCode: ErrorCode) {
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
      // 01: monitorenter
      // 02: aload 2
      // 03: ldc_w "errorCode"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 0d: ifne 58
      // 10: aload 2
      // 11: invokevirtual okhttp3/internal/http2/ErrorCode.getHttpCode ()I
      // 14: bipush -1
      // 15: if_icmpeq 1d
      // 18: bipush 1
      // 19: istore 3
      // 1a: goto 1f
      // 1d: bipush 0
      // 1e: istore 3
      // 1f: iload 3
      // 20: ifeq 45
      // 23: aload 0
      // 24: iload 1
      // 25: bipush 4
      // 26: bipush 3
      // 27: bipush 0
      // 28: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 2b: aload 0
      // 2c: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 2f: aload 2
      // 30: invokevirtual okhttp3/internal/http2/ErrorCode.getHttpCode ()I
      // 33: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 38: pop
      // 39: aload 0
      // 3a: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 3d: invokeinterface okio/BufferedSink.flush ()V 1
      // 42: aload 0
      // 43: monitorexit
      // 44: return
      // 45: new java/lang/IllegalArgumentException
      // 48: astore 2
      // 49: aload 2
      // 4a: ldc_w "Failed requirement."
      // 4d: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 50: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 53: aload 2
      // 54: checkcast java/lang/Throwable
      // 57: athrow
      // 58: new java/io/IOException
      // 5b: astore 2
      // 5c: aload 2
      // 5d: ldc "closed"
      // 5f: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 62: aload 2
      // 63: checkcast java/lang/Throwable
      // 66: athrow
      // 67: astore 2
      // 68: aload 0
      // 69: monitorexit
      // 6a: aload 2
      // 6b: athrow
   }

   @Throws(java/io/IOException::class)
   public fun settings(settings: Settings) {
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
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc_w "settings"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 0d: ifne 76
      // 10: aload 1
      // 11: invokevirtual okhttp3/internal/http2/Settings.size ()I
      // 14: istore 3
      // 15: bipush 0
      // 16: istore 2
      // 17: aload 0
      // 18: bipush 0
      // 19: iload 3
      // 1a: bipush 6
      // 1c: imul
      // 1d: bipush 4
      // 1e: bipush 0
      // 1f: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 22: iload 2
      // 23: bipush 10
      // 25: if_icmpge 6a
      // 28: aload 1
      // 29: iload 2
      // 2a: invokevirtual okhttp3/internal/http2/Settings.isSet (I)Z
      // 2d: ifne 33
      // 30: goto 64
      // 33: iload 2
      // 34: bipush 4
      // 35: if_icmpeq 48
      // 38: iload 2
      // 39: bipush 7
      // 3b: if_icmpeq 43
      // 3e: iload 2
      // 3f: istore 3
      // 40: goto 4a
      // 43: bipush 4
      // 44: istore 3
      // 45: goto 4a
      // 48: bipush 3
      // 49: istore 3
      // 4a: aload 0
      // 4b: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 4e: iload 3
      // 4f: invokeinterface okio/BufferedSink.writeShort (I)Lokio/BufferedSink; 2
      // 54: pop
      // 55: aload 0
      // 56: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 59: aload 1
      // 5a: iload 2
      // 5b: invokevirtual okhttp3/internal/http2/Settings.get (I)I
      // 5e: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 63: pop
      // 64: iinc 2 1
      // 67: goto 22
      // 6a: aload 0
      // 6b: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 6e: invokeinterface okio/BufferedSink.flush ()V 1
      // 73: aload 0
      // 74: monitorexit
      // 75: return
      // 76: new java/io/IOException
      // 79: astore 1
      // 7a: aload 1
      // 7b: ldc "closed"
      // 7d: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 80: aload 1
      // 81: checkcast java/lang/Throwable
      // 84: athrow
      // 85: astore 1
      // 86: aload 0
      // 87: monitorexit
      // 88: aload 1
      // 89: athrow
   }

   @Throws(java/io/IOException::class)
   public fun windowUpdate(streamId: Int, windowSizeIncrement: Long) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/http2/Http2Writer.closed Z
      // 06: ifne 76
      // 09: lload 2
      // 0a: lconst_0
      // 0b: lcmp
      // 0c: ifeq 1d
      // 0f: lload 2
      // 10: ldc2_w 2147483647
      // 13: lcmp
      // 14: ifgt 1d
      // 17: bipush 1
      // 18: istore 4
      // 1a: goto 20
      // 1d: bipush 0
      // 1e: istore 4
      // 20: iload 4
      // 22: ifeq 46
      // 25: aload 0
      // 26: iload 1
      // 27: bipush 4
      // 28: bipush 8
      // 2a: bipush 0
      // 2b: invokevirtual okhttp3/internal/http2/Http2Writer.frameHeader (IIII)V
      // 2e: aload 0
      // 2f: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 32: lload 2
      // 33: l2i
      // 34: invokeinterface okio/BufferedSink.writeInt (I)Lokio/BufferedSink; 2
      // 39: pop
      // 3a: aload 0
      // 3b: getfield okhttp3/internal/http2/Http2Writer.sink Lokio/BufferedSink;
      // 3e: invokeinterface okio/BufferedSink.flush ()V 1
      // 43: aload 0
      // 44: monitorexit
      // 45: return
      // 46: new java/lang/StringBuilder
      // 49: astore 5
      // 4b: aload 5
      // 4d: ldc_w "windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: "
      // 50: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 53: aload 5
      // 55: lload 2
      // 56: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 59: pop
      // 5a: aload 5
      // 5c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 5f: astore 6
      // 61: new java/lang/IllegalArgumentException
      // 64: astore 5
      // 66: aload 5
      // 68: aload 6
      // 6a: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 6d: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 70: aload 5
      // 72: checkcast java/lang/Throwable
      // 75: athrow
      // 76: new java/io/IOException
      // 79: astore 5
      // 7b: aload 5
      // 7d: ldc "closed"
      // 7f: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 82: aload 5
      // 84: checkcast java/lang/Throwable
      // 87: athrow
      // 88: astore 5
      // 8a: aload 0
      // 8b: monitorexit
      // 8c: aload 5
      // 8e: athrow
   }

   public companion object {
      private final val logger: Logger
   }
}
