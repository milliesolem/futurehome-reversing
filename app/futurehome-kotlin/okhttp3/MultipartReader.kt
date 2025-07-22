package okhttp3

import java.io.Closeable
import java.net.ProtocolException
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.http1.HeadersReader
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.Okio
import okio.Options
import okio.Source
import okio.Timeout

public class MultipartReader(source: BufferedSource, boundary: String) : Closeable {
   public final val boundary: String
   private final var closed: Boolean
   private final val crlfDashDashBoundary: ByteString
   private final var currentPart: okhttp3.MultipartReader.PartSource?
   private final val dashDashBoundary: ByteString
   private final var noMoreParts: Boolean
   private final var partCount: Int
   private final val source: BufferedSource

   public constructor(response: ResponseBody) : Intrinsics.checkParameterIsNotNull(var1, "response") {
      val var2: BufferedSource = var1.source();
      val var3: MediaType = var1.contentType();
      if (var3 != null) {
         val var4: java.lang.String = var3.parameter("boundary");
         if (var4 != null) {
            this(var2, var4);
            return;
         }
      }

      throw (new ProtocolException("expected the Content-Type to have a boundary parameter")) as java.lang.Throwable;
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "boundary");
      super();
      this.source = var1;
      this.boundary = var2;
      this.dashDashBoundary = new Buffer().writeUtf8("--").writeUtf8(var2).readByteString();
      this.crlfDashDashBoundary = new Buffer().writeUtf8("\r\n--").writeUtf8(var2).readByteString();
   }

   private fun currentPartBytesRemaining(maxResult: Long): Long {
      this.source.require((long)this.crlfDashDashBoundary.size());
      val var3: Long = this.source.getBuffer().indexOf(this.crlfDashDashBoundary);
      if (var3 == -1L) {
         var1 = Math.min(var1, this.source.getBuffer().size() - (long)this.crlfDashDashBoundary.size() + 1L);
      } else {
         var1 = Math.min(var1, var3);
      }

      return var1;
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      if (!this.closed) {
         this.closed = true;
         val var1: MultipartReader.PartSource = null as MultipartReader.PartSource;
         this.currentPart = null;
         this.source.close();
      }
   }

   @Throws(java/io/IOException::class)
   public fun nextPart(): okhttp3.MultipartReader.Part? {
      if (!this.closed) {
         if (this.noMoreParts) {
            return null;
         } else {
            if (this.partCount == 0 && this.source.rangeEquals(0L, this.dashDashBoundary)) {
               this.source.skip((long)this.dashDashBoundary.size());
            } else {
               while (true) {
                  val var3: Long = this.currentPartBytesRemaining(8192L);
                  if (var3 == 0L) {
                     this.source.skip((long)this.crlfDashDashBoundary.size());
                     break;
                  }

                  this.source.skip(var3);
               }
            }

            var var1: Boolean = false;

            while (true) {
               val var2: Int = this.source.select(afterBoundaryOptions);
               if (var2 == -1) {
                  throw (new ProtocolException("unexpected characters after boundary")) as java.lang.Throwable;
               }

               if (var2 == 0) {
                  this.partCount++;
                  val var5: Headers = new HeadersReader(this.source).readHeaders();
                  val var6: MultipartReader.PartSource = new MultipartReader.PartSource(this);
                  this.currentPart = var6;
                  return new MultipartReader.Part(var5, Okio.buffer(var6));
               }

               if (var2 == 1) {
                  if (!var1) {
                     if (this.partCount != 0) {
                        this.noMoreParts = true;
                        return null;
                     }

                     throw (new ProtocolException("expected at least 1 part")) as java.lang.Throwable;
                  }

                  throw (new ProtocolException("unexpected characters after boundary")) as java.lang.Throwable;
               }

               if (var2 == 2 || var2 == 3) {
                  var1 = true;
               }
            }
         }
      } else {
         throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
      }
   }

   internal companion object {
      public final val afterBoundaryOptions: Options
   }

   public class Part(headers: Headers, body: BufferedSource) : Closeable {
      public final val body: BufferedSource
      public final val headers: Headers

      init {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         Intrinsics.checkParameterIsNotNull(var2, "body");
         super();
         this.headers = var1;
         this.body = var2;
      }

      public override fun close() {
         this.body.close();
      }
   }

   private inner class PartSource : Source {
      private final val timeout: Timeout

      init {
         this.this$0 = var1;
         this.timeout = new Timeout();
      }

      public override fun close() {
         val var1: MultipartReader.PartSource = MultipartReader.access$getCurrentPart$p(this.this$0);
         var var2: MultipartReader.PartSource = this;
         if (var1 == this) {
            var2 = null as MultipartReader.PartSource;
            MultipartReader.access$setCurrentPart$p(this.this$0, null);
         }
      }

      public override fun read(sink: Buffer, byteCount: Long): Long {
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
         // 017: ifeq 16f
         // 01a: aload 0
         // 01b: getfield okhttp3/MultipartReader$PartSource.this$0 Lokhttp3/MultipartReader;
         // 01e: invokestatic okhttp3/MultipartReader.access$getCurrentPart$p (Lokhttp3/MultipartReader;)Lokhttp3/MultipartReader$PartSource;
         // 021: astore 10
         // 023: aload 0
         // 024: checkcast okhttp3/MultipartReader$PartSource
         // 027: astore 9
         // 029: aload 10
         // 02b: aload 0
         // 02c: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 02f: ifeq 15f
         // 032: aload 0
         // 033: getfield okhttp3/MultipartReader$PartSource.this$0 Lokhttp3/MultipartReader;
         // 036: invokestatic okhttp3/MultipartReader.access$getSource$p (Lokhttp3/MultipartReader;)Lokio/BufferedSource;
         // 039: invokeinterface okio/BufferedSource.timeout ()Lokio/Timeout; 1
         // 03e: astore 9
         // 040: aload 0
         // 041: getfield okhttp3/MultipartReader$PartSource.timeout Lokio/Timeout;
         // 044: astore 10
         // 046: aload 9
         // 048: invokevirtual okio/Timeout.timeoutNanos ()J
         // 04b: lstore 5
         // 04d: aload 9
         // 04f: getstatic okio/Timeout.Companion Lokio/Timeout$Companion;
         // 052: aload 10
         // 054: invokevirtual okio/Timeout.timeoutNanos ()J
         // 057: aload 9
         // 059: invokevirtual okio/Timeout.timeoutNanos ()J
         // 05c: invokevirtual okio/Timeout$Companion.minTimeout (JJ)J
         // 05f: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
         // 062: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
         // 065: pop
         // 066: aload 9
         // 068: invokevirtual okio/Timeout.hasDeadline ()Z
         // 06b: ifeq 0f0
         // 06e: aload 9
         // 070: invokevirtual okio/Timeout.deadlineNanoTime ()J
         // 073: lstore 7
         // 075: aload 10
         // 077: invokevirtual okio/Timeout.hasDeadline ()Z
         // 07a: ifeq 090
         // 07d: aload 9
         // 07f: aload 9
         // 081: invokevirtual okio/Timeout.deadlineNanoTime ()J
         // 084: aload 10
         // 086: invokevirtual okio/Timeout.deadlineNanoTime ()J
         // 089: invokestatic java/lang/Math.min (JJ)J
         // 08c: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
         // 08f: pop
         // 090: aload 0
         // 091: getfield okhttp3/MultipartReader$PartSource.this$0 Lokhttp3/MultipartReader;
         // 094: lload 2
         // 095: invokestatic okhttp3/MultipartReader.access$currentPartBytesRemaining (Lokhttp3/MultipartReader;J)J
         // 098: lstore 2
         // 099: lload 2
         // 09a: lconst_0
         // 09b: lcmp
         // 09c: ifne 0a6
         // 09f: ldc2_w -1
         // 0a2: lstore 2
         // 0a3: goto 0b5
         // 0a6: aload 0
         // 0a7: getfield okhttp3/MultipartReader$PartSource.this$0 Lokhttp3/MultipartReader;
         // 0aa: invokestatic okhttp3/MultipartReader.access$getSource$p (Lokhttp3/MultipartReader;)Lokio/BufferedSource;
         // 0ad: aload 1
         // 0ae: lload 2
         // 0af: invokeinterface okio/BufferedSource.read (Lokio/Buffer;J)J 4
         // 0b4: lstore 2
         // 0b5: aload 9
         // 0b7: lload 5
         // 0b9: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
         // 0bc: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
         // 0bf: pop
         // 0c0: aload 10
         // 0c2: invokevirtual okio/Timeout.hasDeadline ()Z
         // 0c5: ifeq 0d0
         // 0c8: aload 9
         // 0ca: lload 7
         // 0cc: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
         // 0cf: pop
         // 0d0: lload 2
         // 0d1: lreturn
         // 0d2: astore 1
         // 0d3: aload 9
         // 0d5: lload 5
         // 0d7: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
         // 0da: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
         // 0dd: pop
         // 0de: aload 10
         // 0e0: invokevirtual okio/Timeout.hasDeadline ()Z
         // 0e3: ifeq 0ee
         // 0e6: aload 9
         // 0e8: lload 7
         // 0ea: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
         // 0ed: pop
         // 0ee: aload 1
         // 0ef: athrow
         // 0f0: aload 10
         // 0f2: invokevirtual okio/Timeout.hasDeadline ()Z
         // 0f5: ifeq 103
         // 0f8: aload 9
         // 0fa: aload 10
         // 0fc: invokevirtual okio/Timeout.deadlineNanoTime ()J
         // 0ff: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
         // 102: pop
         // 103: aload 0
         // 104: getfield okhttp3/MultipartReader$PartSource.this$0 Lokhttp3/MultipartReader;
         // 107: lload 2
         // 108: invokestatic okhttp3/MultipartReader.access$currentPartBytesRemaining (Lokhttp3/MultipartReader;J)J
         // 10b: lstore 2
         // 10c: lload 2
         // 10d: lconst_0
         // 10e: lcmp
         // 10f: ifne 119
         // 112: ldc2_w -1
         // 115: lstore 2
         // 116: goto 128
         // 119: aload 0
         // 11a: getfield okhttp3/MultipartReader$PartSource.this$0 Lokhttp3/MultipartReader;
         // 11d: invokestatic okhttp3/MultipartReader.access$getSource$p (Lokhttp3/MultipartReader;)Lokio/BufferedSource;
         // 120: aload 1
         // 121: lload 2
         // 122: invokeinterface okio/BufferedSource.read (Lokio/Buffer;J)J 4
         // 127: lstore 2
         // 128: aload 9
         // 12a: lload 5
         // 12c: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
         // 12f: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
         // 132: pop
         // 133: aload 10
         // 135: invokevirtual okio/Timeout.hasDeadline ()Z
         // 138: ifeq 141
         // 13b: aload 9
         // 13d: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
         // 140: pop
         // 141: lload 2
         // 142: lreturn
         // 143: astore 1
         // 144: aload 9
         // 146: lload 5
         // 148: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
         // 14b: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
         // 14e: pop
         // 14f: aload 10
         // 151: invokevirtual okio/Timeout.hasDeadline ()Z
         // 154: ifeq 15d
         // 157: aload 9
         // 159: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
         // 15c: pop
         // 15d: aload 1
         // 15e: athrow
         // 15f: new java/lang/IllegalStateException
         // 162: dup
         // 163: ldc "closed"
         // 165: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 168: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 16b: checkcast java/lang/Throwable
         // 16e: athrow
         // 16f: new java/lang/StringBuilder
         // 172: dup
         // 173: ldc "byteCount < 0: "
         // 175: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 178: astore 1
         // 179: aload 1
         // 17a: lload 2
         // 17b: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
         // 17e: pop
         // 17f: new java/lang/IllegalArgumentException
         // 182: dup
         // 183: aload 1
         // 184: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 187: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 18a: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
         // 18d: checkcast java/lang/Throwable
         // 190: athrow
      }

      public override fun timeout(): Timeout {
         return this.timeout;
      }
   }
}
