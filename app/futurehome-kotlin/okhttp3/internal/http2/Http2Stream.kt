package okhttp3.internal.http2

import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.util.ArrayDeque
import kotlin.jvm.internal.Intrinsics
import okhttp3.Headers
import okhttp3.internal.Util
import okio.AsyncTimeout
import okio.Buffer
import okio.BufferedSource
import okio.Sink
import okio.Source
import okio.Timeout

public class Http2Stream internal constructor(id: Int, connection: Http2Connection, outFinished: Boolean, inFinished: Boolean, headers: Headers?) {
   public final val connection: Http2Connection

   internal final var errorCode: ErrorCode?
      internal final get() {
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
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
         // 6: astore 1
         // 7: aload 0
         // 8: monitorexit
         // 9: aload 1
         // a: areturn
         // b: astore 1
         // c: aload 0
         // d: monitorexit
         // e: aload 1
         // f: athrow
      }


   internal final var errorException: IOException?
   private final var hasResponseHeaders: Boolean
   private final val headersQueue: ArrayDeque<Headers>
   public final val id: Int

   public final val isLocallyInitiated: Boolean
      public final get() {
         var var2: Boolean;
         if ((this.id and 1) == 1) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (this.connection.getClient$okhttp() == var2) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }


   public final val isOpen: Boolean
      public final get() {
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
         // 03: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
         // 06: astore 2
         // 07: aload 2
         // 08: ifnull 0f
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: bipush 0
         // 0e: ireturn
         // 0f: aload 0
         // 10: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
         // 13: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getFinished$okhttp ()Z
         // 16: ifne 23
         // 19: aload 0
         // 1a: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
         // 1d: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getClosed$okhttp ()Z
         // 20: ifeq 44
         // 23: aload 0
         // 24: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
         // 27: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.getFinished ()Z
         // 2a: ifne 37
         // 2d: aload 0
         // 2e: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
         // 31: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.getClosed ()Z
         // 34: ifeq 44
         // 37: aload 0
         // 38: getfield okhttp3/internal/http2/Http2Stream.hasResponseHeaders Z
         // 3b: istore 1
         // 3c: iload 1
         // 3d: ifeq 44
         // 40: aload 0
         // 41: monitorexit
         // 42: bipush 0
         // 43: ireturn
         // 44: aload 0
         // 45: monitorexit
         // 46: bipush 1
         // 47: ireturn
         // 48: astore 2
         // 49: aload 0
         // 4a: monitorexit
         // 4b: aload 2
         // 4c: athrow
      }


   public final var readBytesAcknowledged: Long
      internal final set(<set-?>) {
         this.readBytesAcknowledged = var1;
      }


   public final var readBytesTotal: Long
      internal final set(<set-?>) {
         this.readBytesTotal = var1;
      }


   internal final val readTimeout: okhttp3.internal.http2.Http2Stream.StreamTimeout
   internal final val sink: okhttp3.internal.http2.Http2Stream.FramingSink
   internal final val source: okhttp3.internal.http2.Http2Stream.FramingSource

   public final var writeBytesMaximum: Long
      internal final set(<set-?>) {
         this.writeBytesMaximum = var1;
      }


   public final var writeBytesTotal: Long
      internal final set(<set-?>) {
         this.writeBytesTotal = var1;
      }


   internal final val writeTimeout: okhttp3.internal.http2.Http2Stream.StreamTimeout

   init {
      Intrinsics.checkParameterIsNotNull(var2, "connection");
      super();
      this.id = var1;
      this.connection = var2;
      this.writeBytesMaximum = var2.getPeerSettings().getInitialWindowSize();
      val var6: ArrayDeque = new ArrayDeque();
      this.headersQueue = var6;
      this.source = new Http2Stream.FramingSource((long)this, (boolean)var2.getOkHttpSettings().getInitialWindowSize(), var4);
      this.sink = new Http2Stream.FramingSink((boolean)this, var3);
      this.readTimeout = new Http2Stream.StreamTimeout(this);
      this.writeTimeout = new Http2Stream.StreamTimeout(this);
      if (var5 != null) {
         if (this.isLocallyInitiated()) {
            throw (new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString())) as java.lang.Throwable;
         }

         var6.add(var5);
      } else if (!this.isLocallyInitiated()) {
         throw (new IllegalStateException("remotely-initiated streams should have headers".toString())) as java.lang.Throwable;
      }
   }

   private fun closeInternal(errorCode: ErrorCode, errorException: IOException?): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 03: ifeq 49
      // 06: aload 0
      // 07: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 0a: ifne 10
      // 0d: goto 49
      // 10: new java/lang/StringBuilder
      // 13: dup
      // 14: ldc "Thread "
      // 16: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 19: astore 1
      // 1a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 1d: astore 2
      // 1e: aload 2
      // 1f: ldc "Thread.currentThread()"
      // 21: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 24: aload 1
      // 25: aload 2
      // 26: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 29: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2c: pop
      // 2d: aload 1
      // 2e: ldc " MUST NOT hold lock on "
      // 30: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 33: pop
      // 34: aload 1
      // 35: aload 0
      // 36: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 39: pop
      // 3a: new java/lang/AssertionError
      // 3d: dup
      // 3e: aload 1
      // 3f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 42: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 45: checkcast java/lang/Throwable
      // 48: athrow
      // 49: aload 0
      // 4a: monitorenter
      // 4b: aload 0
      // 4c: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 4f: astore 4
      // 51: aload 4
      // 53: ifnull 5a
      // 56: aload 0
      // 57: monitorexit
      // 58: bipush 0
      // 59: ireturn
      // 5a: aload 0
      // 5b: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 5e: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getFinished$okhttp ()Z
      // 61: ifeq 74
      // 64: aload 0
      // 65: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 68: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.getFinished ()Z
      // 6b: istore 3
      // 6c: iload 3
      // 6d: ifeq 74
      // 70: aload 0
      // 71: monitorexit
      // 72: bipush 0
      // 73: ireturn
      // 74: aload 0
      // 75: aload 1
      // 76: putfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 79: aload 0
      // 7a: aload 2
      // 7b: putfield okhttp3/internal/http2/Http2Stream.errorException Ljava/io/IOException;
      // 7e: aload 0
      // 7f: checkcast java/lang/Object
      // 82: invokevirtual java/lang/Object.notifyAll ()V
      // 85: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 88: astore 1
      // 89: aload 0
      // 8a: monitorexit
      // 8b: aload 0
      // 8c: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // 8f: aload 0
      // 90: getfield okhttp3/internal/http2/Http2Stream.id I
      // 93: invokevirtual okhttp3/internal/http2/Http2Connection.removeStream$okhttp (I)Lokhttp3/internal/http2/Http2Stream;
      // 96: pop
      // 97: bipush 1
      // 98: ireturn
      // 99: astore 1
      // 9a: aload 0
      // 9b: monitorexit
      // 9c: aload 1
      // 9d: athrow
   }

   public fun addBytesToWriteWindow(delta: Long) {
      this.writeBytesMaximum += var1;
      if (var1 > 0L) {
         this.notifyAll();
      }
   }

   @Throws(java/io/IOException::class)
   internal fun cancelStreamIfNecessary() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 03: ifeq 4c
      // 06: aload 0
      // 07: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 0a: ifne 10
      // 0d: goto 4c
      // 10: new java/lang/StringBuilder
      // 13: dup
      // 14: ldc "Thread "
      // 16: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 19: astore 3
      // 1a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 1d: astore 4
      // 1f: aload 4
      // 21: ldc "Thread.currentThread()"
      // 23: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 26: aload 3
      // 27: aload 4
      // 29: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 2c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2f: pop
      // 30: aload 3
      // 31: ldc " MUST NOT hold lock on "
      // 33: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 36: pop
      // 37: aload 3
      // 38: aload 0
      // 39: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 3c: pop
      // 3d: new java/lang/AssertionError
      // 40: dup
      // 41: aload 3
      // 42: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 45: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 48: checkcast java/lang/Throwable
      // 4b: athrow
      // 4c: aload 0
      // 4d: monitorenter
      // 4e: aload 0
      // 4f: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 52: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getFinished$okhttp ()Z
      // 55: ifne 7b
      // 58: aload 0
      // 59: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 5c: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getClosed$okhttp ()Z
      // 5f: ifeq 7b
      // 62: aload 0
      // 63: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 66: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.getFinished ()Z
      // 69: ifne 76
      // 6c: aload 0
      // 6d: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 70: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.getClosed ()Z
      // 73: ifeq 7b
      // 76: bipush 1
      // 77: istore 1
      // 78: goto 7d
      // 7b: bipush 0
      // 7c: istore 1
      // 7d: aload 0
      // 7e: invokevirtual okhttp3/internal/http2/Http2Stream.isOpen ()Z
      // 81: istore 2
      // 82: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 85: astore 3
      // 86: aload 0
      // 87: monitorexit
      // 88: iload 1
      // 89: ifeq 97
      // 8c: aload 0
      // 8d: getstatic okhttp3/internal/http2/ErrorCode.CANCEL Lokhttp3/internal/http2/ErrorCode;
      // 90: aconst_null
      // 91: invokevirtual okhttp3/internal/http2/Http2Stream.close (Lokhttp3/internal/http2/ErrorCode;Ljava/io/IOException;)V
      // 94: goto a7
      // 97: iload 2
      // 98: ifne a7
      // 9b: aload 0
      // 9c: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // 9f: aload 0
      // a0: getfield okhttp3/internal/http2/Http2Stream.id I
      // a3: invokevirtual okhttp3/internal/http2/Http2Connection.removeStream$okhttp (I)Lokhttp3/internal/http2/Http2Stream;
      // a6: pop
      // a7: return
      // a8: astore 3
      // a9: aload 0
      // aa: monitorexit
      // ab: aload 3
      // ac: athrow
   }

   @Throws(java/io/IOException::class)
   internal fun checkOutNotClosed() {
      if (!this.sink.getClosed()) {
         if (!this.sink.getFinished()) {
            if (this.errorCode != null) {
               var var1: Any = this.errorException;
               if (this.errorException == null) {
                  if (this.errorCode == null) {
                     Intrinsics.throwNpe();
                  }

                  var1 = new StreamResetException(this.errorCode);
               }

               throw var1 as java.lang.Throwable;
            }
         } else {
            throw (new IOException("stream finished")) as java.lang.Throwable;
         }
      } else {
         throw (new IOException("stream closed")) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun close(rstStatusCode: ErrorCode, errorException: IOException?) {
      Intrinsics.checkParameterIsNotNull(var1, "rstStatusCode");
      if (this.closeInternal(var1, var2)) {
         this.connection.writeSynReset$okhttp(this.id, var1);
      }
   }

   public fun closeLater(errorCode: ErrorCode) {
      Intrinsics.checkParameterIsNotNull(var1, "errorCode");
      if (this.closeInternal(var1, null)) {
         this.connection.writeSynResetLater$okhttp(this.id, var1);
      }
   }

   public fun enqueueTrailers(trailers: Headers) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 1
      // 01: ldc_w "trailers"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 0d: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.getFinished ()Z
      // 10: ifne 47
      // 13: aload 1
      // 14: invokevirtual okhttp3/Headers.size ()I
      // 17: ifeq 1f
      // 1a: bipush 1
      // 1b: istore 2
      // 1c: goto 21
      // 1f: bipush 0
      // 20: istore 2
      // 21: iload 2
      // 22: ifeq 34
      // 25: aload 0
      // 26: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 29: aload 1
      // 2a: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.setTrailers (Lokhttp3/Headers;)V
      // 2d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 30: astore 1
      // 31: aload 0
      // 32: monitorexit
      // 33: return
      // 34: new java/lang/IllegalArgumentException
      // 37: astore 1
      // 38: aload 1
      // 39: ldc_w "trailers.size() == 0"
      // 3c: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 3f: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 42: aload 1
      // 43: checkcast java/lang/Throwable
      // 46: athrow
      // 47: new java/lang/IllegalStateException
      // 4a: astore 1
      // 4b: aload 1
      // 4c: ldc_w "already finished"
      // 4f: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 52: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 55: aload 1
      // 56: checkcast java/lang/Throwable
      // 59: athrow
      // 5a: astore 1
      // 5b: aload 0
      // 5c: monitorexit
      // 5d: aload 1
      // 5e: athrow
   }

   public fun getSink(): Sink {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/http2/Http2Stream.hasResponseHeaders Z
      // 06: ifne 18
      // 09: aload 0
      // 0a: invokevirtual okhttp3/internal/http2/Http2Stream.isLocallyInitiated ()Z
      // 0d: ifeq 13
      // 10: goto 18
      // 13: bipush 0
      // 14: istore 1
      // 15: goto 1a
      // 18: bipush 1
      // 19: istore 1
      // 1a: iload 1
      // 1b: ifeq 2c
      // 1e: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 21: astore 2
      // 22: aload 0
      // 23: monitorexit
      // 24: aload 0
      // 25: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 28: checkcast okio/Sink
      // 2b: areturn
      // 2c: new java/lang/IllegalStateException
      // 2f: astore 2
      // 30: aload 2
      // 31: ldc_w "reply before requesting the sink"
      // 34: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 37: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 3a: aload 2
      // 3b: checkcast java/lang/Throwable
      // 3e: athrow
      // 3f: astore 2
      // 40: aload 0
      // 41: monitorexit
      // 42: aload 2
      // 43: athrow
   }

   public fun getSource(): Source {
      return this.source;
   }

   public fun readTimeout(): Timeout {
      return this.readTimeout;
   }

   @Throws(java/io/IOException::class)
   public fun receiveData(source: BufferedSource, length: Int) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (Util.assertionsEnabled && Thread.holdsLock(this)) {
         val var4: StringBuilder = new StringBuilder("Thread ");
         val var3: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var4.append(var3.getName());
         var4.append(" MUST NOT hold lock on ");
         var4.append(this);
         throw (new AssertionError(var4.toString())) as java.lang.Throwable;
      } else {
         this.source.receive$okhttp(var1, (long)var2);
      }
   }

   public fun receiveHeaders(headers: Headers, inFinished: Boolean) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 1
      // 01: ldc_w "headers"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 0a: ifeq 50
      // 0d: aload 0
      // 0e: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 11: ifne 17
      // 14: goto 50
      // 17: new java/lang/StringBuilder
      // 1a: dup
      // 1b: ldc "Thread "
      // 1d: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 20: astore 3
      // 21: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 24: astore 1
      // 25: aload 1
      // 26: ldc "Thread.currentThread()"
      // 28: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 2b: aload 3
      // 2c: aload 1
      // 2d: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 30: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 33: pop
      // 34: aload 3
      // 35: ldc " MUST NOT hold lock on "
      // 37: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3a: pop
      // 3b: aload 3
      // 3c: aload 0
      // 3d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 40: pop
      // 41: new java/lang/AssertionError
      // 44: dup
      // 45: aload 3
      // 46: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 49: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 4c: checkcast java/lang/Throwable
      // 4f: athrow
      // 50: aload 0
      // 51: monitorenter
      // 52: aload 0
      // 53: getfield okhttp3/internal/http2/Http2Stream.hasResponseHeaders Z
      // 56: ifeq 6b
      // 59: iload 2
      // 5a: ifne 60
      // 5d: goto 6b
      // 60: aload 0
      // 61: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 64: aload 1
      // 65: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.setTrailers (Lokhttp3/Headers;)V
      // 68: goto 7e
      // 6b: aload 0
      // 6c: bipush 1
      // 6d: putfield okhttp3/internal/http2/Http2Stream.hasResponseHeaders Z
      // 70: aload 0
      // 71: getfield okhttp3/internal/http2/Http2Stream.headersQueue Ljava/util/ArrayDeque;
      // 74: checkcast java/util/Collection
      // 77: aload 1
      // 78: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 7d: pop
      // 7e: iload 2
      // 7f: ifeq 8a
      // 82: aload 0
      // 83: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 86: bipush 1
      // 87: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.setFinished$okhttp (Z)V
      // 8a: aload 0
      // 8b: invokevirtual okhttp3/internal/http2/Http2Stream.isOpen ()Z
      // 8e: istore 2
      // 8f: aload 0
      // 90: checkcast java/lang/Object
      // 93: invokevirtual java/lang/Object.notifyAll ()V
      // 96: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 99: astore 1
      // 9a: aload 0
      // 9b: monitorexit
      // 9c: iload 2
      // 9d: ifne ac
      // a0: aload 0
      // a1: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // a4: aload 0
      // a5: getfield okhttp3/internal/http2/Http2Stream.id I
      // a8: invokevirtual okhttp3/internal/http2/Http2Connection.removeStream$okhttp (I)Lokhttp3/internal/http2/Http2Stream;
      // ab: pop
      // ac: return
      // ad: astore 1
      // ae: aload 0
      // af: monitorexit
      // b0: aload 1
      // b1: athrow
   }

   public fun receiveRstStream(errorCode: ErrorCode) {
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
      // 03: ldc_w "errorCode"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 0d: ifnonnull 1c
      // 10: aload 0
      // 11: aload 1
      // 12: putfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 15: aload 0
      // 16: checkcast java/lang/Object
      // 19: invokevirtual java/lang/Object.notifyAll ()V
      // 1c: aload 0
      // 1d: monitorexit
      // 1e: return
      // 1f: astore 1
      // 20: aload 0
      // 21: monitorexit
      // 22: aload 1
      // 23: athrow
   }

   @Throws(java/io/IOException::class)
   public fun takeHeaders(): Headers {
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
      // 03: getfield okhttp3/internal/http2/Http2Stream.readTimeout Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
      // 06: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.enter ()V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Stream.headersQueue Ljava/util/ArrayDeque;
      // 0d: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 10: ifeq 21
      // 13: aload 0
      // 14: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 17: ifnonnull 21
      // 1a: aload 0
      // 1b: invokevirtual okhttp3/internal/http2/Http2Stream.waitForIo$okhttp ()V
      // 1e: goto 09
      // 21: aload 0
      // 22: getfield okhttp3/internal/http2/Http2Stream.readTimeout Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
      // 25: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
      // 28: aload 0
      // 29: getfield okhttp3/internal/http2/Http2Stream.headersQueue Ljava/util/ArrayDeque;
      // 2c: checkcast java/util/Collection
      // 2f: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 34: ifne 4f
      // 37: aload 0
      // 38: getfield okhttp3/internal/http2/Http2Stream.headersQueue Ljava/util/ArrayDeque;
      // 3b: invokevirtual java/util/ArrayDeque.removeFirst ()Ljava/lang/Object;
      // 3e: astore 1
      // 3f: aload 1
      // 40: ldc_w "headersQueue.removeFirst()"
      // 43: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 46: aload 1
      // 47: checkcast okhttp3/Headers
      // 4a: astore 1
      // 4b: aload 0
      // 4c: monitorexit
      // 4d: aload 1
      // 4e: areturn
      // 4f: aload 0
      // 50: getfield okhttp3/internal/http2/Http2Stream.errorException Ljava/io/IOException;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 6f
      // 5a: new okhttp3/internal/http2/StreamResetException
      // 5d: astore 1
      // 5e: aload 0
      // 5f: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 62: astore 2
      // 63: aload 2
      // 64: ifnonnull 6a
      // 67: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 6a: aload 1
      // 6b: aload 2
      // 6c: invokespecial okhttp3/internal/http2/StreamResetException.<init> (Lokhttp3/internal/http2/ErrorCode;)V
      // 6f: aload 1
      // 70: checkcast java/lang/Throwable
      // 73: athrow
      // 74: astore 1
      // 75: aload 0
      // 76: getfield okhttp3/internal/http2/Http2Stream.readTimeout Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
      // 79: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
      // 7c: aload 1
      // 7d: athrow
      // 7e: astore 1
      // 7f: aload 0
      // 80: monitorexit
      // 81: aload 1
      // 82: athrow
   }

   @Throws(java/io/IOException::class)
   public fun trailers(): Headers {
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
      // 03: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 06: ifnull 2e
      // 09: aload 0
      // 0a: getfield okhttp3/internal/http2/Http2Stream.errorException Ljava/io/IOException;
      // 0d: astore 3
      // 0e: aload 3
      // 0f: astore 2
      // 10: aload 3
      // 11: ifnonnull 29
      // 14: new okhttp3/internal/http2/StreamResetException
      // 17: astore 2
      // 18: aload 0
      // 19: getfield okhttp3/internal/http2/Http2Stream.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 1c: astore 3
      // 1d: aload 3
      // 1e: ifnonnull 24
      // 21: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 24: aload 2
      // 25: aload 3
      // 26: invokespecial okhttp3/internal/http2/StreamResetException.<init> (Lokhttp3/internal/http2/ErrorCode;)V
      // 29: aload 2
      // 2a: checkcast java/lang/Throwable
      // 2d: athrow
      // 2e: aload 0
      // 2f: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 32: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getFinished$okhttp ()Z
      // 35: ifeq 57
      // 38: aload 0
      // 39: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 3c: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getReceiveBuffer ()Lokio/Buffer;
      // 3f: invokevirtual okio/Buffer.exhausted ()Z
      // 42: ifeq 57
      // 45: aload 0
      // 46: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 49: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getReadBuffer ()Lokio/Buffer;
      // 4c: invokevirtual okio/Buffer.exhausted ()Z
      // 4f: ifeq 57
      // 52: bipush 1
      // 53: istore 1
      // 54: goto 59
      // 57: bipush 0
      // 58: istore 1
      // 59: iload 1
      // 5a: ifeq 74
      // 5d: aload 0
      // 5e: getfield okhttp3/internal/http2/Http2Stream.source Lokhttp3/internal/http2/Http2Stream$FramingSource;
      // 61: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSource.getTrailers ()Lokhttp3/Headers;
      // 64: astore 2
      // 65: aload 2
      // 66: ifnull 6c
      // 69: goto 70
      // 6c: getstatic okhttp3/internal/Util.EMPTY_HEADERS Lokhttp3/Headers;
      // 6f: astore 2
      // 70: aload 0
      // 71: monitorexit
      // 72: aload 2
      // 73: areturn
      // 74: new java/lang/IllegalStateException
      // 77: astore 2
      // 78: aload 2
      // 79: ldc_w "too early; can't read the trailers yet"
      // 7c: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 7f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 82: aload 2
      // 83: checkcast java/lang/Throwable
      // 86: athrow
      // 87: astore 2
      // 88: aload 0
      // 89: monitorexit
      // 8a: aload 2
      // 8b: athrow
   }

   @Throws(java/io/InterruptedIOException::class)
   internal fun waitForIo() {
      try {
         this.wait();
      } catch (var2: InterruptedException) {
         Thread.currentThread().interrupt();
         throw (new InterruptedIOException()) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun writeHeaders(responseHeaders: List<Header>, outFinished: Boolean, flushHeaders: Boolean) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 1
      // 01: ldc_w "responseHeaders"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 0a: ifeq 53
      // 0d: aload 0
      // 0e: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 11: ifne 17
      // 14: goto 53
      // 17: new java/lang/StringBuilder
      // 1a: dup
      // 1b: ldc "Thread "
      // 1d: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 20: astore 1
      // 21: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 24: astore 6
      // 26: aload 6
      // 28: ldc "Thread.currentThread()"
      // 2a: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 2d: aload 1
      // 2e: aload 6
      // 30: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 33: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 36: pop
      // 37: aload 1
      // 38: ldc " MUST NOT hold lock on "
      // 3a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3d: pop
      // 3e: aload 1
      // 3f: aload 0
      // 40: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 43: pop
      // 44: new java/lang/AssertionError
      // 47: dup
      // 48: aload 1
      // 49: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4c: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 4f: checkcast java/lang/Throwable
      // 52: athrow
      // 53: aload 0
      // 54: monitorenter
      // 55: bipush 1
      // 56: istore 5
      // 58: aload 0
      // 59: bipush 1
      // 5a: putfield okhttp3/internal/http2/Http2Stream.hasResponseHeaders Z
      // 5d: iload 2
      // 5e: ifeq 69
      // 61: aload 0
      // 62: getfield okhttp3/internal/http2/Http2Stream.sink Lokhttp3/internal/http2/Http2Stream$FramingSink;
      // 65: bipush 1
      // 66: invokevirtual okhttp3/internal/http2/Http2Stream$FramingSink.setFinished (Z)V
      // 69: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 6c: astore 6
      // 6e: aload 0
      // 6f: monitorexit
      // 70: iload 3
      // 71: istore 4
      // 73: iload 3
      // 74: ifne ae
      // 77: aload 0
      // 78: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // 7b: astore 6
      // 7d: aload 6
      // 7f: monitorenter
      // 80: aload 0
      // 81: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // 84: invokevirtual okhttp3/internal/http2/Http2Connection.getWriteBytesTotal ()J
      // 87: aload 0
      // 88: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // 8b: invokevirtual okhttp3/internal/http2/Http2Connection.getWriteBytesMaximum ()J
      // 8e: lcmp
      // 8f: iflt 98
      // 92: iload 5
      // 94: istore 3
      // 95: goto 9a
      // 98: bipush 0
      // 99: istore 3
      // 9a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 9d: astore 7
      // 9f: aload 6
      // a1: monitorexit
      // a2: iload 3
      // a3: istore 4
      // a5: goto ae
      // a8: astore 1
      // a9: aload 6
      // ab: monitorexit
      // ac: aload 1
      // ad: athrow
      // ae: aload 0
      // af: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // b2: aload 0
      // b3: getfield okhttp3/internal/http2/Http2Stream.id I
      // b6: iload 2
      // b7: aload 1
      // b8: invokevirtual okhttp3/internal/http2/Http2Connection.writeHeaders$okhttp (IZLjava/util/List;)V
      // bb: iload 4
      // bd: ifeq c7
      // c0: aload 0
      // c1: getfield okhttp3/internal/http2/Http2Stream.connection Lokhttp3/internal/http2/Http2Connection;
      // c4: invokevirtual okhttp3/internal/http2/Http2Connection.flush ()V
      // c7: return
      // c8: astore 1
      // c9: aload 0
      // ca: monitorexit
      // cb: aload 1
      // cc: athrow
   }

   public fun writeTimeout(): Timeout {
      return this.writeTimeout;
   }

   public companion object {
      internal const val EMIT_BUFFER_SIZE: Long
   }

   internal inner class FramingSink(finished: Boolean = ...) : Sink {
      public final var closed: Boolean
         internal set

      public final var finished: Boolean
         internal set

      private final val sendBuffer: Buffer

      public final var trailers: Headers?
         internal set

      init {
         this.this$0 = var1;
         this.finished = var2;
         this.sendBuffer = new Buffer();
      }

      @Throws(java/io/IOException::class)
      private fun emitFrame(outFinishedOnLastFrame: Boolean) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 04: astore 4
         // 06: aload 4
         // 08: monitorenter
         // 09: aload 0
         // 0a: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0d: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // 10: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.enter ()V
         // 13: aload 0
         // 14: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 17: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteBytesTotal ()J
         // 1a: aload 0
         // 1b: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 1e: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteBytesMaximum ()J
         // 21: lcmp
         // 22: iflt 47
         // 25: aload 0
         // 26: getfield okhttp3/internal/http2/Http2Stream$FramingSink.finished Z
         // 29: ifne 47
         // 2c: aload 0
         // 2d: getfield okhttp3/internal/http2/Http2Stream$FramingSink.closed Z
         // 30: ifne 47
         // 33: aload 0
         // 34: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 37: invokevirtual okhttp3/internal/http2/Http2Stream.getErrorCode$okhttp ()Lokhttp3/internal/http2/ErrorCode;
         // 3a: ifnonnull 47
         // 3d: aload 0
         // 3e: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 41: invokevirtual okhttp3/internal/http2/Http2Stream.waitForIo$okhttp ()V
         // 44: goto 13
         // 47: aload 0
         // 48: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 4b: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // 4e: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
         // 51: aload 0
         // 52: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 55: invokevirtual okhttp3/internal/http2/Http2Stream.checkOutNotClosed$okhttp ()V
         // 58: aload 0
         // 59: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 5c: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteBytesMaximum ()J
         // 5f: aload 0
         // 60: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 63: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteBytesTotal ()J
         // 66: lsub
         // 67: aload 0
         // 68: getfield okhttp3/internal/http2/Http2Stream$FramingSink.sendBuffer Lokio/Buffer;
         // 6b: invokevirtual okio/Buffer.size ()J
         // 6e: invokestatic java/lang/Math.min (JJ)J
         // 71: lstore 2
         // 72: aload 0
         // 73: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 76: astore 5
         // 78: aload 5
         // 7a: aload 5
         // 7c: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteBytesTotal ()J
         // 7f: lload 2
         // 80: ladd
         // 81: invokevirtual okhttp3/internal/http2/Http2Stream.setWriteBytesTotal$okhttp (J)V
         // 84: iload 1
         // 85: ifeq a3
         // 88: lload 2
         // 89: aload 0
         // 8a: getfield okhttp3/internal/http2/Http2Stream$FramingSink.sendBuffer Lokio/Buffer;
         // 8d: invokevirtual okio/Buffer.size ()J
         // 90: lcmp
         // 91: ifne a3
         // 94: aload 0
         // 95: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 98: invokevirtual okhttp3/internal/http2/Http2Stream.getErrorCode$okhttp ()Lokhttp3/internal/http2/ErrorCode;
         // 9b: ifnonnull a3
         // 9e: bipush 1
         // 9f: istore 1
         // a0: goto a5
         // a3: bipush 0
         // a4: istore 1
         // a5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // a8: astore 5
         // aa: aload 4
         // ac: monitorexit
         // ad: aload 0
         // ae: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // b1: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // b4: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.enter ()V
         // b7: aload 0
         // b8: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // bb: invokevirtual okhttp3/internal/http2/Http2Stream.getConnection ()Lokhttp3/internal/http2/Http2Connection;
         // be: aload 0
         // bf: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // c2: invokevirtual okhttp3/internal/http2/Http2Stream.getId ()I
         // c5: iload 1
         // c6: aload 0
         // c7: getfield okhttp3/internal/http2/Http2Stream$FramingSink.sendBuffer Lokio/Buffer;
         // ca: lload 2
         // cb: invokevirtual okhttp3/internal/http2/Http2Connection.writeData (IZLokio/Buffer;J)V
         // ce: aload 0
         // cf: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // d2: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // d5: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
         // d8: return
         // d9: astore 4
         // db: aload 0
         // dc: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // df: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // e2: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
         // e5: aload 4
         // e7: athrow
         // e8: astore 5
         // ea: aload 0
         // eb: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // ee: invokevirtual okhttp3/internal/http2/Http2Stream.getWriteTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // f1: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
         // f4: aload 5
         // f6: athrow
         // f7: astore 5
         // f9: aload 4
         // fb: monitorexit
         // fc: aload 5
         // fe: athrow
      }

      @Throws(java/io/IOException::class)
      public override fun close() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 000: aload 0
         // 001: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 004: astore 3
         // 005: getstatic okhttp3/internal/Util.assertionsEnabled Z
         // 008: ifeq 056
         // 00b: aload 3
         // 00c: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
         // 00f: ifne 015
         // 012: goto 056
         // 015: new java/lang/StringBuilder
         // 018: dup
         // 019: ldc "Thread "
         // 01b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 01e: astore 4
         // 020: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
         // 023: astore 5
         // 025: aload 5
         // 027: ldc "Thread.currentThread()"
         // 029: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 02c: aload 4
         // 02e: aload 5
         // 030: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
         // 033: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 036: pop
         // 037: aload 4
         // 039: ldc " MUST NOT hold lock on "
         // 03b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 03e: pop
         // 03f: aload 4
         // 041: aload 3
         // 042: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
         // 045: pop
         // 046: new java/lang/AssertionError
         // 049: dup
         // 04a: aload 4
         // 04c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 04f: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
         // 052: checkcast java/lang/Throwable
         // 055: athrow
         // 056: aload 0
         // 057: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 05a: astore 3
         // 05b: aload 3
         // 05c: monitorenter
         // 05d: aload 0
         // 05e: getfield okhttp3/internal/http2/Http2Stream$FramingSink.closed Z
         // 061: istore 2
         // 062: iload 2
         // 063: ifeq 069
         // 066: aload 3
         // 067: monitorexit
         // 068: return
         // 069: aload 0
         // 06a: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 06d: invokevirtual okhttp3/internal/http2/Http2Stream.getErrorCode$okhttp ()Lokhttp3/internal/http2/ErrorCode;
         // 070: ifnonnull 078
         // 073: bipush 1
         // 074: istore 2
         // 075: goto 07a
         // 078: bipush 0
         // 079: istore 2
         // 07a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 07d: astore 4
         // 07f: aload 3
         // 080: monitorexit
         // 081: aload 0
         // 082: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 085: invokevirtual okhttp3/internal/http2/Http2Stream.getSink$okhttp ()Lokhttp3/internal/http2/Http2Stream$FramingSink;
         // 088: getfield okhttp3/internal/http2/Http2Stream$FramingSink.finished Z
         // 08b: ifne 118
         // 08e: aload 0
         // 08f: getfield okhttp3/internal/http2/Http2Stream$FramingSink.sendBuffer Lokio/Buffer;
         // 092: invokevirtual okio/Buffer.size ()J
         // 095: lconst_0
         // 096: lcmp
         // 097: ifle 09f
         // 09a: bipush 1
         // 09b: istore 1
         // 09c: goto 0a1
         // 09f: bipush 0
         // 0a0: istore 1
         // 0a1: aload 0
         // 0a2: getfield okhttp3/internal/http2/Http2Stream$FramingSink.trailers Lokhttp3/Headers;
         // 0a5: ifnull 0e8
         // 0a8: aload 0
         // 0a9: getfield okhttp3/internal/http2/Http2Stream$FramingSink.sendBuffer Lokio/Buffer;
         // 0ac: invokevirtual okio/Buffer.size ()J
         // 0af: lconst_0
         // 0b0: lcmp
         // 0b1: ifle 0bc
         // 0b4: aload 0
         // 0b5: bipush 0
         // 0b6: invokespecial okhttp3/internal/http2/Http2Stream$FramingSink.emitFrame (Z)V
         // 0b9: goto 0a8
         // 0bc: aload 0
         // 0bd: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0c0: invokevirtual okhttp3/internal/http2/Http2Stream.getConnection ()Lokhttp3/internal/http2/Http2Connection;
         // 0c3: astore 3
         // 0c4: aload 0
         // 0c5: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0c8: invokevirtual okhttp3/internal/http2/Http2Stream.getId ()I
         // 0cb: istore 1
         // 0cc: aload 0
         // 0cd: getfield okhttp3/internal/http2/Http2Stream$FramingSink.trailers Lokhttp3/Headers;
         // 0d0: astore 4
         // 0d2: aload 4
         // 0d4: ifnonnull 0da
         // 0d7: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 0da: aload 3
         // 0db: iload 1
         // 0dc: iload 2
         // 0dd: aload 4
         // 0df: invokestatic okhttp3/internal/Util.toHeaderList (Lokhttp3/Headers;)Ljava/util/List;
         // 0e2: invokevirtual okhttp3/internal/http2/Http2Connection.writeHeaders$okhttp (IZLjava/util/List;)V
         // 0e5: goto 118
         // 0e8: iload 1
         // 0e9: ifeq 100
         // 0ec: aload 0
         // 0ed: getfield okhttp3/internal/http2/Http2Stream$FramingSink.sendBuffer Lokio/Buffer;
         // 0f0: invokevirtual okio/Buffer.size ()J
         // 0f3: lconst_0
         // 0f4: lcmp
         // 0f5: ifle 118
         // 0f8: aload 0
         // 0f9: bipush 1
         // 0fa: invokespecial okhttp3/internal/http2/Http2Stream$FramingSink.emitFrame (Z)V
         // 0fd: goto 0ec
         // 100: iload 2
         // 101: ifeq 118
         // 104: aload 0
         // 105: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 108: invokevirtual okhttp3/internal/http2/Http2Stream.getConnection ()Lokhttp3/internal/http2/Http2Connection;
         // 10b: aload 0
         // 10c: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 10f: invokevirtual okhttp3/internal/http2/Http2Stream.getId ()I
         // 112: bipush 1
         // 113: aconst_null
         // 114: lconst_0
         // 115: invokevirtual okhttp3/internal/http2/Http2Connection.writeData (IZLokio/Buffer;J)V
         // 118: aload 0
         // 119: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 11c: astore 3
         // 11d: aload 3
         // 11e: monitorenter
         // 11f: aload 0
         // 120: bipush 1
         // 121: putfield okhttp3/internal/http2/Http2Stream$FramingSink.closed Z
         // 124: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 127: astore 4
         // 129: aload 3
         // 12a: monitorexit
         // 12b: aload 0
         // 12c: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 12f: invokevirtual okhttp3/internal/http2/Http2Stream.getConnection ()Lokhttp3/internal/http2/Http2Connection;
         // 132: invokevirtual okhttp3/internal/http2/Http2Connection.flush ()V
         // 135: aload 0
         // 136: getfield okhttp3/internal/http2/Http2Stream$FramingSink.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 139: invokevirtual okhttp3/internal/http2/Http2Stream.cancelStreamIfNecessary$okhttp ()V
         // 13c: return
         // 13d: astore 4
         // 13f: aload 3
         // 140: monitorexit
         // 141: aload 4
         // 143: athrow
         // 144: astore 4
         // 146: aload 3
         // 147: monitorexit
         // 148: aload 4
         // 14a: athrow
      }

      @Throws(java/io/IOException::class)
      public override fun flush() {
         val var2: Http2Stream = this.this$0;
         if (Util.assertionsEnabled && Thread.holdsLock(this.this$0)) {
            val var5: StringBuilder = new StringBuilder("Thread ");
            val var3: Thread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
            var5.append(var3.getName());
            var5.append(" MUST NOT hold lock on ");
            var5.append(var2);
            throw (new AssertionError(var5.toString())) as java.lang.Throwable;
         } else {
            synchronized (this.this$0) {
               this.this$0.checkOutNotClosed$okhttp();
            }

            while (this.sendBuffer.size() > 0L) {
               this.emitFrame(false);
               this.this$0.getConnection().flush();
            }
         }
      }

      public override fun timeout(): Timeout {
         return this.this$0.getWriteTimeout$okhttp();
      }

      @Throws(java/io/IOException::class)
      public override fun write(source: Buffer, byteCount: Long) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         val var4: Http2Stream = this.this$0;
         if (Util.assertionsEnabled && Thread.holdsLock(this.this$0)) {
            val var5: StringBuilder = new StringBuilder("Thread ");
            val var6: Thread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var6, "Thread.currentThread()");
            var5.append(var6.getName());
            var5.append(" MUST NOT hold lock on ");
            var5.append(var4);
            throw (new AssertionError(var5.toString())) as java.lang.Throwable;
         } else {
            this.sendBuffer.write(var1, var2);

            while (this.sendBuffer.size() >= 16384L) {
               this.emitFrame(false);
            }
         }
      }
   }

   public inner class FramingSource internal constructor(maxByteCount: Long, finished: Boolean) : Source {
      internal final var closed: Boolean
      internal final var finished: Boolean
      private final val maxByteCount: Long
      public final val readBuffer: Buffer
      public final val receiveBuffer: Buffer

      public final var trailers: Headers?
         internal set

      init {
         this.this$0 = var1;
         this.maxByteCount = var2;
         this.finished = var4;
         this.receiveBuffer = new Buffer();
         this.readBuffer = new Buffer();
      }

      private fun updateConnectionFlowControl(read: Long) {
         val var4: Http2Stream = this.this$0;
         if (Util.assertionsEnabled && Thread.holdsLock(this.this$0)) {
            val var3: StringBuilder = new StringBuilder("Thread ");
            val var5: Thread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var5, "Thread.currentThread()");
            var3.append(var5.getName());
            var3.append(" MUST NOT hold lock on ");
            var3.append(var4);
            throw (new AssertionError(var3.toString())) as java.lang.Throwable;
         } else {
            this.this$0.getConnection().updateConnectionFlowControl$okhttp(var1);
         }
      }

      @Throws(java/io/IOException::class)
      public override fun close() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 04: astore 3
         // 05: aload 3
         // 06: monitorenter
         // 07: aload 0
         // 08: bipush 1
         // 09: putfield okhttp3/internal/http2/Http2Stream$FramingSource.closed Z
         // 0c: aload 0
         // 0d: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 10: invokevirtual okio/Buffer.size ()J
         // 13: lstore 1
         // 14: aload 0
         // 15: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 18: invokevirtual okio/Buffer.clear ()V
         // 1b: aload 0
         // 1c: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 1f: astore 4
         // 21: aload 4
         // 23: ifnull 48
         // 26: aload 4
         // 28: checkcast java/lang/Object
         // 2b: invokevirtual java/lang/Object.notifyAll ()V
         // 2e: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 31: astore 4
         // 33: aload 3
         // 34: monitorexit
         // 35: lload 1
         // 36: lconst_0
         // 37: lcmp
         // 38: ifle 40
         // 3b: aload 0
         // 3c: lload 1
         // 3d: invokespecial okhttp3/internal/http2/Http2Stream$FramingSource.updateConnectionFlowControl (J)V
         // 40: aload 0
         // 41: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 44: invokevirtual okhttp3/internal/http2/Http2Stream.cancelStreamIfNecessary$okhttp ()V
         // 47: return
         // 48: new kotlin/TypeCastException
         // 4b: astore 4
         // 4d: aload 4
         // 4f: ldc "null cannot be cast to non-null type java.lang.Object"
         // 51: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
         // 54: aload 4
         // 56: athrow
         // 57: astore 4
         // 59: aload 3
         // 5a: monitorexit
         // 5b: aload 4
         // 5d: athrow
      }

      @Throws(java/io/IOException::class)
      public override fun read(sink: Buffer, byteCount: Long): Long {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 000: aload 1
         // 001: ldc "sink"
         // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 006: lload 2
         // 007: lconst_0
         // 008: lcmp
         // 009: iflt 012
         // 00c: bipush 1
         // 00d: istore 4
         // 00f: goto 015
         // 012: bipush 0
         // 013: istore 4
         // 015: iload 4
         // 017: ifeq 19b
         // 01a: aconst_null
         // 01b: astore 11
         // 01d: aconst_null
         // 01e: checkcast java/io/IOException
         // 021: astore 12
         // 023: aload 0
         // 024: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 027: astore 12
         // 029: aload 12
         // 02b: monitorenter
         // 02c: aload 0
         // 02d: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 030: invokevirtual okhttp3/internal/http2/Http2Stream.getReadTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // 033: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.enter ()V
         // 036: aload 0
         // 037: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 03a: invokevirtual okhttp3/internal/http2/Http2Stream.getErrorCode$okhttp ()Lokhttp3/internal/http2/ErrorCode;
         // 03d: ifnull 075
         // 040: aload 0
         // 041: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 044: invokevirtual okhttp3/internal/http2/Http2Stream.getErrorException$okhttp ()Ljava/io/IOException;
         // 047: astore 11
         // 049: aload 11
         // 04b: ifnull 051
         // 04e: goto 075
         // 051: new okhttp3/internal/http2/StreamResetException
         // 054: astore 11
         // 056: aload 0
         // 057: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 05a: invokevirtual okhttp3/internal/http2/Http2Stream.getErrorCode$okhttp ()Lokhttp3/internal/http2/ErrorCode;
         // 05d: astore 13
         // 05f: aload 13
         // 061: ifnonnull 067
         // 064: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 067: aload 11
         // 069: aload 13
         // 06b: invokespecial okhttp3/internal/http2/StreamResetException.<init> (Lokhttp3/internal/http2/ErrorCode;)V
         // 06e: aload 11
         // 070: checkcast java/io/IOException
         // 073: astore 11
         // 075: aload 0
         // 076: getfield okhttp3/internal/http2/Http2Stream$FramingSource.closed Z
         // 079: ifne 179
         // 07c: aload 0
         // 07d: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 080: invokevirtual okio/Buffer.size ()J
         // 083: lconst_0
         // 084: lcmp
         // 085: ifle 110
         // 088: aload 0
         // 089: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 08c: astore 13
         // 08e: aload 13
         // 090: aload 1
         // 091: lload 2
         // 092: aload 13
         // 094: invokevirtual okio/Buffer.size ()J
         // 097: invokestatic java/lang/Math.min (JJ)J
         // 09a: invokevirtual okio/Buffer.read (Lokio/Buffer;J)J
         // 09d: lstore 7
         // 09f: aload 0
         // 0a0: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0a3: astore 13
         // 0a5: aload 13
         // 0a7: aload 13
         // 0a9: invokevirtual okhttp3/internal/http2/Http2Stream.getReadBytesTotal ()J
         // 0ac: lload 7
         // 0ae: ladd
         // 0af: invokevirtual okhttp3/internal/http2/Http2Stream.setReadBytesTotal$okhttp (J)V
         // 0b2: aload 0
         // 0b3: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0b6: invokevirtual okhttp3/internal/http2/Http2Stream.getReadBytesTotal ()J
         // 0b9: aload 0
         // 0ba: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0bd: invokevirtual okhttp3/internal/http2/Http2Stream.getReadBytesAcknowledged ()J
         // 0c0: lsub
         // 0c1: lstore 9
         // 0c3: lload 7
         // 0c5: lstore 5
         // 0c7: aload 11
         // 0c9: ifnonnull 133
         // 0cc: lload 7
         // 0ce: lstore 5
         // 0d0: lload 9
         // 0d2: aload 0
         // 0d3: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0d6: invokevirtual okhttp3/internal/http2/Http2Stream.getConnection ()Lokhttp3/internal/http2/Http2Connection;
         // 0d9: invokevirtual okhttp3/internal/http2/Http2Connection.getOkHttpSettings ()Lokhttp3/internal/http2/Settings;
         // 0dc: invokevirtual okhttp3/internal/http2/Settings.getInitialWindowSize ()I
         // 0df: bipush 2
         // 0e0: idiv
         // 0e1: i2l
         // 0e2: lcmp
         // 0e3: iflt 133
         // 0e6: aload 0
         // 0e7: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0ea: invokevirtual okhttp3/internal/http2/Http2Stream.getConnection ()Lokhttp3/internal/http2/Http2Connection;
         // 0ed: aload 0
         // 0ee: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0f1: invokevirtual okhttp3/internal/http2/Http2Stream.getId ()I
         // 0f4: lload 9
         // 0f6: invokevirtual okhttp3/internal/http2/Http2Connection.writeWindowUpdateLater$okhttp (IJ)V
         // 0f9: aload 0
         // 0fa: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0fd: astore 13
         // 0ff: aload 13
         // 101: aload 13
         // 103: invokevirtual okhttp3/internal/http2/Http2Stream.getReadBytesTotal ()J
         // 106: invokevirtual okhttp3/internal/http2/Http2Stream.setReadBytesAcknowledged$okhttp (J)V
         // 109: lload 7
         // 10b: lstore 5
         // 10d: goto 133
         // 110: aload 0
         // 111: getfield okhttp3/internal/http2/Http2Stream$FramingSource.finished Z
         // 114: ifne 12e
         // 117: aload 11
         // 119: ifnonnull 12e
         // 11c: aload 0
         // 11d: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 120: invokevirtual okhttp3/internal/http2/Http2Stream.waitForIo$okhttp ()V
         // 123: ldc2_w -1
         // 126: lstore 5
         // 128: bipush 1
         // 129: istore 4
         // 12b: goto 136
         // 12e: ldc2_w -1
         // 131: lstore 5
         // 133: bipush 0
         // 134: istore 4
         // 136: aload 0
         // 137: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 13a: invokevirtual okhttp3/internal/http2/Http2Stream.getReadTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // 13d: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
         // 140: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 143: astore 13
         // 145: aload 12
         // 147: monitorexit
         // 148: iload 4
         // 14a: ifeq 150
         // 14d: goto 01a
         // 150: lload 5
         // 152: ldc2_w -1
         // 155: lcmp
         // 156: ifeq 162
         // 159: aload 0
         // 15a: lload 5
         // 15c: invokespecial okhttp3/internal/http2/Http2Stream$FramingSource.updateConnectionFlowControl (J)V
         // 15f: lload 5
         // 161: lreturn
         // 162: aload 11
         // 164: ifnull 175
         // 167: aload 11
         // 169: ifnonnull 16f
         // 16c: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 16f: aload 11
         // 171: checkcast java/lang/Throwable
         // 174: athrow
         // 175: ldc2_w -1
         // 178: lreturn
         // 179: new java/io/IOException
         // 17c: astore 1
         // 17d: aload 1
         // 17e: ldc "stream closed"
         // 180: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
         // 183: aload 1
         // 184: checkcast java/lang/Throwable
         // 187: athrow
         // 188: astore 1
         // 189: aload 0
         // 18a: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 18d: invokevirtual okhttp3/internal/http2/Http2Stream.getReadTimeout$okhttp ()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;
         // 190: invokevirtual okhttp3/internal/http2/Http2Stream$StreamTimeout.exitAndThrowIfTimedOut ()V
         // 193: aload 1
         // 194: athrow
         // 195: astore 1
         // 196: aload 12
         // 198: monitorexit
         // 199: aload 1
         // 19a: athrow
         // 19b: new java/lang/StringBuilder
         // 19e: dup
         // 19f: ldc_w "byteCount < 0: "
         // 1a2: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 1a5: astore 1
         // 1a6: aload 1
         // 1a7: lload 2
         // 1a8: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
         // 1ab: pop
         // 1ac: new java/lang/IllegalArgumentException
         // 1af: dup
         // 1b0: aload 1
         // 1b1: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 1b4: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 1b7: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
         // 1ba: checkcast java/lang/Throwable
         // 1bd: athrow
      }

      @Throws(java/io/IOException::class)
      internal fun receive(source: BufferedSource, byteCount: Long) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 000: aload 1
         // 001: ldc_w "source"
         // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 007: aload 0
         // 008: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 00b: astore 11
         // 00d: lload 2
         // 00e: lstore 7
         // 010: getstatic okhttp3/internal/Util.assertionsEnabled Z
         // 013: ifeq 063
         // 016: aload 11
         // 018: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
         // 01b: ifne 024
         // 01e: lload 2
         // 01f: lstore 7
         // 021: goto 063
         // 024: new java/lang/StringBuilder
         // 027: dup
         // 028: ldc "Thread "
         // 02a: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 02d: astore 12
         // 02f: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
         // 032: astore 1
         // 033: aload 1
         // 034: ldc "Thread.currentThread()"
         // 036: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 039: aload 12
         // 03b: aload 1
         // 03c: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
         // 03f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 042: pop
         // 043: aload 12
         // 045: ldc " MUST NOT hold lock on "
         // 047: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 04a: pop
         // 04b: aload 12
         // 04d: aload 11
         // 04f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
         // 052: pop
         // 053: new java/lang/AssertionError
         // 056: dup
         // 057: aload 12
         // 059: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 05c: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
         // 05f: checkcast java/lang/Throwable
         // 062: athrow
         // 063: lload 7
         // 065: lconst_0
         // 066: lcmp
         // 067: ifle 18b
         // 06a: aload 0
         // 06b: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 06e: astore 11
         // 070: aload 11
         // 072: monitorenter
         // 073: aload 0
         // 074: getfield okhttp3/internal/http2/Http2Stream$FramingSource.finished Z
         // 077: istore 6
         // 079: aload 0
         // 07a: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 07d: invokevirtual okio/Buffer.size ()J
         // 080: lstore 9
         // 082: aload 0
         // 083: getfield okhttp3/internal/http2/Http2Stream$FramingSource.maxByteCount J
         // 086: lstore 2
         // 087: bipush 1
         // 088: istore 5
         // 08a: lload 9
         // 08c: lload 7
         // 08e: ladd
         // 08f: lload 2
         // 090: lcmp
         // 091: ifle 09a
         // 094: bipush 1
         // 095: istore 4
         // 097: goto 09d
         // 09a: bipush 0
         // 09b: istore 4
         // 09d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 0a0: astore 12
         // 0a2: aload 11
         // 0a4: monitorexit
         // 0a5: iload 4
         // 0a7: ifeq 0bd
         // 0aa: aload 1
         // 0ab: lload 7
         // 0ad: invokeinterface okio/BufferedSource.skip (J)V 3
         // 0b2: aload 0
         // 0b3: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0b6: getstatic okhttp3/internal/http2/ErrorCode.FLOW_CONTROL_ERROR Lokhttp3/internal/http2/ErrorCode;
         // 0b9: invokevirtual okhttp3/internal/http2/Http2Stream.closeLater (Lokhttp3/internal/http2/ErrorCode;)V
         // 0bc: return
         // 0bd: iload 6
         // 0bf: ifeq 0cb
         // 0c2: aload 1
         // 0c3: lload 7
         // 0c5: invokeinterface okio/BufferedSource.skip (J)V 3
         // 0ca: return
         // 0cb: aload 1
         // 0cc: aload 0
         // 0cd: getfield okhttp3/internal/http2/Http2Stream$FramingSource.receiveBuffer Lokio/Buffer;
         // 0d0: lload 7
         // 0d2: invokeinterface okio/BufferedSource.read (Lokio/Buffer;J)J 4
         // 0d7: lstore 2
         // 0d8: lload 2
         // 0d9: ldc2_w -1
         // 0dc: lcmp
         // 0dd: ifeq 17a
         // 0e0: lload 7
         // 0e2: lload 2
         // 0e3: lsub
         // 0e4: lstore 9
         // 0e6: aload 0
         // 0e7: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 0ea: astore 11
         // 0ec: aload 11
         // 0ee: monitorenter
         // 0ef: aload 0
         // 0f0: getfield okhttp3/internal/http2/Http2Stream$FramingSource.closed Z
         // 0f3: ifeq 108
         // 0f6: aload 0
         // 0f7: getfield okhttp3/internal/http2/Http2Stream$FramingSource.receiveBuffer Lokio/Buffer;
         // 0fa: invokevirtual okio/Buffer.size ()J
         // 0fd: lstore 2
         // 0fe: aload 0
         // 0ff: getfield okhttp3/internal/http2/Http2Stream$FramingSource.receiveBuffer Lokio/Buffer;
         // 102: invokevirtual okio/Buffer.clear ()V
         // 105: goto 156
         // 108: aload 0
         // 109: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 10c: invokevirtual okio/Buffer.size ()J
         // 10f: lconst_0
         // 110: lcmp
         // 111: ifne 11b
         // 114: iload 5
         // 116: istore 4
         // 118: goto 11e
         // 11b: bipush 0
         // 11c: istore 4
         // 11e: aload 0
         // 11f: getfield okhttp3/internal/http2/Http2Stream$FramingSource.readBuffer Lokio/Buffer;
         // 122: aload 0
         // 123: getfield okhttp3/internal/http2/Http2Stream$FramingSource.receiveBuffer Lokio/Buffer;
         // 126: checkcast okio/Source
         // 129: invokevirtual okio/Buffer.writeAll (Lokio/Source;)J
         // 12c: pop2
         // 12d: iload 4
         // 12f: ifeq 154
         // 132: aload 0
         // 133: getfield okhttp3/internal/http2/Http2Stream$FramingSource.this$0 Lokhttp3/internal/http2/Http2Stream;
         // 136: astore 12
         // 138: aload 12
         // 13a: ifnull 148
         // 13d: aload 12
         // 13f: checkcast java/lang/Object
         // 142: invokevirtual java/lang/Object.notifyAll ()V
         // 145: goto 154
         // 148: new kotlin/TypeCastException
         // 14b: astore 1
         // 14c: aload 1
         // 14d: ldc "null cannot be cast to non-null type java.lang.Object"
         // 14f: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
         // 152: aload 1
         // 153: athrow
         // 154: lconst_0
         // 155: lstore 2
         // 156: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 159: astore 12
         // 15b: aload 11
         // 15d: monitorexit
         // 15e: lload 9
         // 160: lstore 7
         // 162: lload 2
         // 163: lconst_0
         // 164: lcmp
         // 165: ifle 063
         // 168: aload 0
         // 169: lload 2
         // 16a: invokespecial okhttp3/internal/http2/Http2Stream$FramingSource.updateConnectionFlowControl (J)V
         // 16d: lload 9
         // 16f: lstore 7
         // 171: goto 063
         // 174: astore 1
         // 175: aload 11
         // 177: monitorexit
         // 178: aload 1
         // 179: athrow
         // 17a: new java/io/EOFException
         // 17d: dup
         // 17e: invokespecial java/io/EOFException.<init> ()V
         // 181: checkcast java/lang/Throwable
         // 184: athrow
         // 185: astore 1
         // 186: aload 11
         // 188: monitorexit
         // 189: aload 1
         // 18a: athrow
         // 18b: return
      }

      public override fun timeout(): Timeout {
         return this.this$0.getReadTimeout$okhttp();
      }
   }

   internal inner class StreamTimeout : AsyncTimeout {
      @Throws(java/io/IOException::class)
      public fun exitAndThrowIfTimedOut() {
         if (this.exit()) {
            throw this.newTimeoutException(null) as java.lang.Throwable;
         }
      }

      protected override fun newTimeoutException(cause: IOException?): IOException {
         val var2: SocketTimeoutException = new SocketTimeoutException("timeout");
         if (var1 != null) {
            var2.initCause(var1);
         }

         return var2;
      }

      protected override fun timedOut() {
         this.this$0.closeLater(ErrorCode.CANCEL);
         this.this$0.getConnection().sendDegradedPingLater$okhttp();
      }
   }
}
