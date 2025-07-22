package okhttp3.internal.cache2

import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import kotlin.jvm.internal.Intrinsics
import okio.Buffer
import okio.ByteString
import okio.Source
import okio.Timeout

public class Relay private constructor(file: RandomAccessFile?, upstream: Source?, upstreamPos: Long, metadata: ByteString, bufferMaxSize: Long) {
   public final val buffer: Buffer
   public final val bufferMaxSize: Long

   public final var complete: Boolean
      internal set

   public final var file: RandomAccessFile?
      internal set

   public final val isClosed: Boolean
      public final get() {
         val var1: Boolean;
         if (this.file == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final val metadata: ByteString

   public final var sourceCount: Int
      internal set

   public final var upstream: Source?
      internal set

   public final val upstreamBuffer: Buffer

   public final var upstreamPos: Long
      internal set

   public final var upstreamReader: Thread?
      internal set

   init {
      this.file = var1;
      this.upstream = var2;
      this.upstreamPos = var3;
      this.metadata = var5;
      this.bufferMaxSize = var6;
      this.upstreamBuffer = new Buffer();
      val var8: Boolean;
      if (this.upstream == null) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.complete = var8;
      this.buffer = new Buffer();
   }

   @Throws(java/io/IOException::class)
   private fun writeHeader(prefix: ByteString, upstreamSize: Long, metadataSize: Long) {
      val var7: Buffer = new Buffer();
      var7.write(var1);
      var7.writeLong(var2);
      var7.writeLong(var4);
      val var6: Boolean;
      if (var7.size() == 32L) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var6) {
         if (this.file == null) {
            Intrinsics.throwNpe();
         }

         val var9: FileChannel = this.file.getChannel();
         Intrinsics.checkExpressionValueIsNotNull(var9, "file!!.channel");
         new FileOperator(var9).write(0L, var7, 32L);
      } else {
         throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun writeMetadata(upstreamSize: Long) {
      val var3: Buffer = new Buffer();
      var3.write(this.metadata);
      if (this.file == null) {
         Intrinsics.throwNpe();
      }

      val var5: FileChannel = this.file.getChannel();
      Intrinsics.checkExpressionValueIsNotNull(var5, "file!!.channel");
      new FileOperator(var5).write(32L + var1, var3, (long)this.metadata.size());
   }

   @Throws(java/io/IOException::class)
   public fun commit(upstreamSize: Long) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 0
      // 01: lload 1
      // 02: invokespecial okhttp3/internal/cache2/Relay.writeMetadata (J)V
      // 05: aload 0
      // 06: getfield okhttp3/internal/cache2/Relay.file Ljava/io/RandomAccessFile;
      // 09: astore 3
      // 0a: aload 3
      // 0b: ifnonnull 11
      // 0e: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 11: aload 3
      // 12: invokevirtual java/io/RandomAccessFile.getChannel ()Ljava/nio/channels/FileChannel;
      // 15: bipush 0
      // 16: invokevirtual java/nio/channels/FileChannel.force (Z)V
      // 19: aload 0
      // 1a: getstatic okhttp3/internal/cache2/Relay.PREFIX_CLEAN Lokio/ByteString;
      // 1d: lload 1
      // 1e: aload 0
      // 1f: getfield okhttp3/internal/cache2/Relay.metadata Lokio/ByteString;
      // 22: invokevirtual okio/ByteString.size ()I
      // 25: i2l
      // 26: invokespecial okhttp3/internal/cache2/Relay.writeHeader (Lokio/ByteString;JJ)V
      // 29: aload 0
      // 2a: getfield okhttp3/internal/cache2/Relay.file Ljava/io/RandomAccessFile;
      // 2d: astore 3
      // 2e: aload 3
      // 2f: ifnonnull 35
      // 32: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 35: aload 3
      // 36: invokevirtual java/io/RandomAccessFile.getChannel ()Ljava/nio/channels/FileChannel;
      // 39: bipush 0
      // 3a: invokevirtual java/nio/channels/FileChannel.force (Z)V
      // 3d: aload 0
      // 3e: monitorenter
      // 3f: aload 0
      // 40: bipush 1
      // 41: putfield okhttp3/internal/cache2/Relay.complete Z
      // 44: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 47: astore 3
      // 48: aload 0
      // 49: monitorexit
      // 4a: aload 0
      // 4b: getfield okhttp3/internal/cache2/Relay.upstream Lokio/Source;
      // 4e: astore 3
      // 4f: aload 3
      // 50: ifnull 5a
      // 53: aload 3
      // 54: checkcast java/io/Closeable
      // 57: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 5a: aconst_null
      // 5b: checkcast okio/Source
      // 5e: astore 3
      // 5f: aload 0
      // 60: aconst_null
      // 61: putfield okhttp3/internal/cache2/Relay.upstream Lokio/Source;
      // 64: return
      // 65: astore 3
      // 66: aload 0
      // 67: monitorexit
      // 68: aload 3
      // 69: athrow
   }

   public fun metadata(): ByteString {
      return this.metadata;
   }

   public fun newSource(): Source? {
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
      // 03: getfield okhttp3/internal/cache2/Relay.file Ljava/io/RandomAccessFile;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 0f
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: aconst_null
      // 0e: areturn
      // 0f: aload 0
      // 10: aload 0
      // 11: getfield okhttp3/internal/cache2/Relay.sourceCount I
      // 14: bipush 1
      // 15: iadd
      // 16: putfield okhttp3/internal/cache2/Relay.sourceCount I
      // 19: aload 0
      // 1a: monitorexit
      // 1b: new okhttp3/internal/cache2/Relay$RelaySource
      // 1e: dup
      // 1f: aload 0
      // 20: invokespecial okhttp3/internal/cache2/Relay$RelaySource.<init> (Lokhttp3/internal/cache2/Relay;)V
      // 23: checkcast okio/Source
      // 26: areturn
      // 27: astore 1
      // 28: aload 0
      // 29: monitorexit
      // 2a: aload 1
      // 2b: athrow
   }

   public companion object {
      private const val FILE_HEADER_SIZE: Long
      public final val PREFIX_CLEAN: ByteString
      public final val PREFIX_DIRTY: ByteString
      private const val SOURCE_FILE: Int
      private const val SOURCE_UPSTREAM: Int

      @Throws(java/io/IOException::class)
      public fun edit(file: File, upstream: Source, metadata: ByteString, bufferMaxSize: Long): Relay {
         Intrinsics.checkParameterIsNotNull(var1, "file");
         Intrinsics.checkParameterIsNotNull(var2, "upstream");
         Intrinsics.checkParameterIsNotNull(var3, "metadata");
         val var6: RandomAccessFile = new RandomAccessFile(var1, "rw");
         val var7: Relay = new Relay(var6, var2, 0L, var3, var4, null);
         var6.setLength(0L);
         Relay.access$writeHeader(var7, Relay.PREFIX_DIRTY, -1L, -1L);
         return var7;
      }

      @Throws(java/io/IOException::class)
      public fun read(file: File): Relay {
         Intrinsics.checkParameterIsNotNull(var1, "file");
         val var8: RandomAccessFile = new RandomAccessFile(var1, "rw");
         val var6: FileChannel = var8.getChannel();
         Intrinsics.checkExpressionValueIsNotNull(var6, "randomAccessFile.channel");
         val var9: FileOperator = new FileOperator(var6);
         var var7: Buffer = new Buffer();
         var9.read(0L, var7, 32L);
         if (var7.readByteString((long)Relay.PREFIX_CLEAN.size()) == Relay.PREFIX_CLEAN) {
            val var2: Long = var7.readLong();
            val var4: Long = var7.readLong();
            var7 = new Buffer();
            var9.read(var2 + 32L, var7, var4);
            return new Relay(var8, null, var2, var7.readByteString(), 0L, null);
         } else {
            throw (new IOException("unreadable cache file")) as java.lang.Throwable;
         }
      }
   }

   internal inner class RelaySource : Source {
      private final var fileOperator: FileOperator?
      private final var sourcePos: Long
      private final val timeout: Timeout

      @Throws(java/io/IOException::class)
      public override fun close() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
         //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield okhttp3/internal/cache2/Relay$RelaySource.fileOperator Lokhttp3/internal/cache2/FileOperator;
         // 04: ifnonnull 08
         // 07: return
         // 08: aconst_null
         // 09: astore 1
         // 0a: aconst_null
         // 0b: checkcast okhttp3/internal/cache2/FileOperator
         // 0e: astore 2
         // 0f: aload 0
         // 10: aconst_null
         // 11: putfield okhttp3/internal/cache2/Relay$RelaySource.fileOperator Lokhttp3/internal/cache2/FileOperator;
         // 14: aconst_null
         // 15: checkcast java/io/RandomAccessFile
         // 18: astore 2
         // 19: aload 0
         // 1a: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1d: astore 2
         // 1e: aload 2
         // 1f: monitorenter
         // 20: aload 0
         // 21: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 24: astore 3
         // 25: aload 3
         // 26: aload 3
         // 27: invokevirtual okhttp3/internal/cache2/Relay.getSourceCount ()I
         // 2a: bipush 1
         // 2b: isub
         // 2c: invokevirtual okhttp3/internal/cache2/Relay.setSourceCount (I)V
         // 2f: aload 0
         // 30: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 33: invokevirtual okhttp3/internal/cache2/Relay.getSourceCount ()I
         // 36: ifne 52
         // 39: aload 0
         // 3a: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 3d: invokevirtual okhttp3/internal/cache2/Relay.getFile ()Ljava/io/RandomAccessFile;
         // 40: astore 1
         // 41: aload 0
         // 42: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 45: astore 4
         // 47: aconst_null
         // 48: checkcast java/io/RandomAccessFile
         // 4b: astore 3
         // 4c: aload 4
         // 4e: aconst_null
         // 4f: invokevirtual okhttp3/internal/cache2/Relay.setFile (Ljava/io/RandomAccessFile;)V
         // 52: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 55: astore 3
         // 56: aload 2
         // 57: monitorexit
         // 58: aload 1
         // 59: ifnull 63
         // 5c: aload 1
         // 5d: checkcast java/io/Closeable
         // 60: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
         // 63: return
         // 64: astore 1
         // 65: aload 2
         // 66: monitorexit
         // 67: aload 1
         // 68: athrow
      }

      @Throws(java/io/IOException::class)
      public override fun read(sink: Buffer, byteCount: Long): Long {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
         //
         // Bytecode:
         // 000: aload 1
         // 001: ldc "sink"
         // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 006: aload 0
         // 007: getfield okhttp3/internal/cache2/Relay$RelaySource.fileOperator Lokhttp3/internal/cache2/FileOperator;
         // 00a: astore 9
         // 00c: bipush 1
         // 00d: istore 5
         // 00f: aload 9
         // 011: ifnull 01a
         // 014: bipush 1
         // 015: istore 4
         // 017: goto 01d
         // 01a: bipush 0
         // 01b: istore 4
         // 01d: iload 4
         // 01f: ifeq 2f0
         // 022: aload 0
         // 023: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 026: astore 9
         // 028: aload 9
         // 02a: monitorenter
         // 02b: aload 0
         // 02c: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 02f: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 032: lstore 6
         // 034: aload 0
         // 035: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 038: lload 6
         // 03a: lcmp
         // 03b: ifeq 096
         // 03e: aload 0
         // 03f: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 042: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 045: aload 0
         // 046: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 049: invokevirtual okhttp3/internal/cache2/Relay.getBuffer ()Lokio/Buffer;
         // 04c: invokevirtual okio/Buffer.size ()J
         // 04f: lsub
         // 050: lstore 6
         // 052: aload 0
         // 053: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 056: lload 6
         // 058: lcmp
         // 059: ifge 062
         // 05c: bipush 2
         // 05d: istore 4
         // 05f: goto 0d1
         // 062: lload 2
         // 063: aload 0
         // 064: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 067: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 06a: aload 0
         // 06b: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 06e: lsub
         // 06f: invokestatic java/lang/Math.min (JJ)J
         // 072: lstore 2
         // 073: aload 0
         // 074: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 077: invokevirtual okhttp3/internal/cache2/Relay.getBuffer ()Lokio/Buffer;
         // 07a: aload 1
         // 07b: aload 0
         // 07c: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 07f: lload 6
         // 081: lsub
         // 082: lload 2
         // 083: invokevirtual okio/Buffer.copyTo (Lokio/Buffer;JJ)Lokio/Buffer;
         // 086: pop
         // 087: aload 0
         // 088: aload 0
         // 089: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 08c: lload 2
         // 08d: ladd
         // 08e: putfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 091: aload 9
         // 093: monitorexit
         // 094: lload 2
         // 095: lreturn
         // 096: aload 0
         // 097: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 09a: invokevirtual okhttp3/internal/cache2/Relay.getComplete ()Z
         // 09d: istore 8
         // 09f: iload 8
         // 0a1: ifeq 0ab
         // 0a4: aload 9
         // 0a6: monitorexit
         // 0a7: ldc2_w -1
         // 0aa: lreturn
         // 0ab: aload 0
         // 0ac: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 0af: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamReader ()Ljava/lang/Thread;
         // 0b2: ifnull 0c3
         // 0b5: aload 0
         // 0b6: getfield okhttp3/internal/cache2/Relay$RelaySource.timeout Lokio/Timeout;
         // 0b9: aload 0
         // 0ba: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 0bd: invokevirtual okio/Timeout.waitUntilNotified (Ljava/lang/Object;)V
         // 0c0: goto 02b
         // 0c3: aload 0
         // 0c4: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 0c7: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
         // 0ca: invokevirtual okhttp3/internal/cache2/Relay.setUpstreamReader (Ljava/lang/Thread;)V
         // 0cd: iload 5
         // 0cf: istore 4
         // 0d1: aload 9
         // 0d3: monitorexit
         // 0d4: iload 4
         // 0d6: bipush 2
         // 0d7: if_icmpne 114
         // 0da: lload 2
         // 0db: aload 0
         // 0dc: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 0df: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 0e2: aload 0
         // 0e3: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 0e6: lsub
         // 0e7: invokestatic java/lang/Math.min (JJ)J
         // 0ea: lstore 2
         // 0eb: aload 0
         // 0ec: getfield okhttp3/internal/cache2/Relay$RelaySource.fileOperator Lokhttp3/internal/cache2/FileOperator;
         // 0ef: astore 9
         // 0f1: aload 9
         // 0f3: ifnonnull 0f9
         // 0f6: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 0f9: aload 9
         // 0fb: aload 0
         // 0fc: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 0ff: ldc2_w 32
         // 102: ladd
         // 103: aload 1
         // 104: lload 2
         // 105: invokevirtual okhttp3/internal/cache2/FileOperator.read (JLokio/Buffer;J)V
         // 108: aload 0
         // 109: aload 0
         // 10a: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 10d: lload 2
         // 10e: ladd
         // 10f: putfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 112: lload 2
         // 113: lreturn
         // 114: aload 0
         // 115: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 118: invokevirtual okhttp3/internal/cache2/Relay.getUpstream ()Lokio/Source;
         // 11b: astore 9
         // 11d: aload 9
         // 11f: ifnonnull 125
         // 122: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 125: aload 9
         // 127: aload 0
         // 128: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 12b: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamBuffer ()Lokio/Buffer;
         // 12e: aload 0
         // 12f: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 132: invokevirtual okhttp3/internal/cache2/Relay.getBufferMaxSize ()J
         // 135: invokeinterface okio/Source.read (Lokio/Buffer;J)J 4
         // 13a: lstore 6
         // 13c: lload 6
         // 13e: ldc2_w -1
         // 141: lcmp
         // 142: ifne 19f
         // 145: aload 0
         // 146: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 149: astore 1
         // 14a: aload 1
         // 14b: aload 1
         // 14c: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 14f: invokevirtual okhttp3/internal/cache2/Relay.commit (J)V
         // 152: aload 0
         // 153: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 156: astore 1
         // 157: aload 1
         // 158: monitorenter
         // 159: aload 0
         // 15a: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 15d: astore 9
         // 15f: aconst_null
         // 160: checkcast java/lang/Thread
         // 163: astore 10
         // 165: aload 9
         // 167: aconst_null
         // 168: invokevirtual okhttp3/internal/cache2/Relay.setUpstreamReader (Ljava/lang/Thread;)V
         // 16b: aload 0
         // 16c: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 16f: astore 9
         // 171: aload 9
         // 173: ifnull 189
         // 176: aload 9
         // 178: checkcast java/lang/Object
         // 17b: invokevirtual java/lang/Object.notifyAll ()V
         // 17e: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 181: astore 9
         // 183: aload 1
         // 184: monitorexit
         // 185: ldc2_w -1
         // 188: lreturn
         // 189: new kotlin/TypeCastException
         // 18c: astore 9
         // 18e: aload 9
         // 190: ldc "null cannot be cast to non-null type java.lang.Object"
         // 192: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
         // 195: aload 9
         // 197: athrow
         // 198: astore 9
         // 19a: aload 1
         // 19b: monitorexit
         // 19c: aload 9
         // 19e: athrow
         // 19f: lload 6
         // 1a1: lload 2
         // 1a2: invokestatic java/lang/Math.min (JJ)J
         // 1a5: lstore 2
         // 1a6: aload 0
         // 1a7: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1aa: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamBuffer ()Lokio/Buffer;
         // 1ad: aload 1
         // 1ae: lconst_0
         // 1af: lload 2
         // 1b0: invokevirtual okio/Buffer.copyTo (Lokio/Buffer;JJ)Lokio/Buffer;
         // 1b3: pop
         // 1b4: aload 0
         // 1b5: aload 0
         // 1b6: getfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 1b9: lload 2
         // 1ba: ladd
         // 1bb: putfield okhttp3/internal/cache2/Relay$RelaySource.sourcePos J
         // 1be: aload 0
         // 1bf: getfield okhttp3/internal/cache2/Relay$RelaySource.fileOperator Lokhttp3/internal/cache2/FileOperator;
         // 1c2: astore 1
         // 1c3: aload 1
         // 1c4: ifnonnull 1ca
         // 1c7: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 1ca: aload 1
         // 1cb: aload 0
         // 1cc: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1cf: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 1d2: ldc2_w 32
         // 1d5: ladd
         // 1d6: aload 0
         // 1d7: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1da: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamBuffer ()Lokio/Buffer;
         // 1dd: invokevirtual okio/Buffer.clone ()Lokio/Buffer;
         // 1e0: lload 6
         // 1e2: invokevirtual okhttp3/internal/cache2/FileOperator.write (JLokio/Buffer;J)V
         // 1e5: aload 0
         // 1e6: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1e9: astore 1
         // 1ea: aload 1
         // 1eb: monitorenter
         // 1ec: aload 0
         // 1ed: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1f0: invokevirtual okhttp3/internal/cache2/Relay.getBuffer ()Lokio/Buffer;
         // 1f3: aload 0
         // 1f4: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 1f7: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamBuffer ()Lokio/Buffer;
         // 1fa: lload 6
         // 1fc: invokevirtual okio/Buffer.write (Lokio/Buffer;J)V
         // 1ff: aload 0
         // 200: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 203: invokevirtual okhttp3/internal/cache2/Relay.getBuffer ()Lokio/Buffer;
         // 206: invokevirtual okio/Buffer.size ()J
         // 209: aload 0
         // 20a: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 20d: invokevirtual okhttp3/internal/cache2/Relay.getBufferMaxSize ()J
         // 210: lcmp
         // 211: ifle 230
         // 214: aload 0
         // 215: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 218: invokevirtual okhttp3/internal/cache2/Relay.getBuffer ()Lokio/Buffer;
         // 21b: aload 0
         // 21c: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 21f: invokevirtual okhttp3/internal/cache2/Relay.getBuffer ()Lokio/Buffer;
         // 222: invokevirtual okio/Buffer.size ()J
         // 225: aload 0
         // 226: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 229: invokevirtual okhttp3/internal/cache2/Relay.getBufferMaxSize ()J
         // 22c: lsub
         // 22d: invokevirtual okio/Buffer.skip (J)V
         // 230: aload 0
         // 231: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 234: astore 9
         // 236: aload 9
         // 238: aload 9
         // 23a: invokevirtual okhttp3/internal/cache2/Relay.getUpstreamPos ()J
         // 23d: lload 6
         // 23f: ladd
         // 240: invokevirtual okhttp3/internal/cache2/Relay.setUpstreamPos (J)V
         // 243: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 246: astore 9
         // 248: aload 1
         // 249: monitorexit
         // 24a: aload 0
         // 24b: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 24e: astore 1
         // 24f: aload 1
         // 250: monitorenter
         // 251: aload 0
         // 252: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 255: astore 10
         // 257: aconst_null
         // 258: checkcast java/lang/Thread
         // 25b: astore 9
         // 25d: aload 10
         // 25f: aconst_null
         // 260: invokevirtual okhttp3/internal/cache2/Relay.setUpstreamReader (Ljava/lang/Thread;)V
         // 263: aload 0
         // 264: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 267: astore 9
         // 269: aload 9
         // 26b: ifnull 27f
         // 26e: aload 9
         // 270: checkcast java/lang/Object
         // 273: invokevirtual java/lang/Object.notifyAll ()V
         // 276: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 279: astore 9
         // 27b: aload 1
         // 27c: monitorexit
         // 27d: lload 2
         // 27e: lreturn
         // 27f: new kotlin/TypeCastException
         // 282: astore 9
         // 284: aload 9
         // 286: ldc "null cannot be cast to non-null type java.lang.Object"
         // 288: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
         // 28b: aload 9
         // 28d: athrow
         // 28e: astore 9
         // 290: aload 1
         // 291: monitorexit
         // 292: aload 9
         // 294: athrow
         // 295: astore 9
         // 297: aload 1
         // 298: monitorexit
         // 299: aload 9
         // 29b: athrow
         // 29c: astore 9
         // 29e: aload 0
         // 29f: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 2a2: astore 1
         // 2a3: aload 1
         // 2a4: monitorenter
         // 2a5: aload 0
         // 2a6: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 2a9: astore 11
         // 2ab: aconst_null
         // 2ac: checkcast java/lang/Thread
         // 2af: astore 10
         // 2b1: aload 11
         // 2b3: aconst_null
         // 2b4: invokevirtual okhttp3/internal/cache2/Relay.setUpstreamReader (Ljava/lang/Thread;)V
         // 2b7: aload 0
         // 2b8: getfield okhttp3/internal/cache2/Relay$RelaySource.this$0 Lokhttp3/internal/cache2/Relay;
         // 2bb: astore 10
         // 2bd: aload 10
         // 2bf: ifnonnull 2d1
         // 2c2: new kotlin/TypeCastException
         // 2c5: astore 9
         // 2c7: aload 9
         // 2c9: ldc "null cannot be cast to non-null type java.lang.Object"
         // 2cb: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
         // 2ce: aload 9
         // 2d0: athrow
         // 2d1: aload 10
         // 2d3: checkcast java/lang/Object
         // 2d6: invokevirtual java/lang/Object.notifyAll ()V
         // 2d9: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 2dc: astore 10
         // 2de: aload 1
         // 2df: monitorexit
         // 2e0: aload 9
         // 2e2: athrow
         // 2e3: astore 9
         // 2e5: aload 1
         // 2e6: monitorexit
         // 2e7: aload 9
         // 2e9: athrow
         // 2ea: astore 1
         // 2eb: aload 9
         // 2ed: monitorexit
         // 2ee: aload 1
         // 2ef: athrow
         // 2f0: new java/lang/IllegalStateException
         // 2f3: dup
         // 2f4: ldc "Check failed."
         // 2f6: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 2f9: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 2fc: checkcast java/lang/Throwable
         // 2ff: athrow
      }

      public override fun timeout(): Timeout {
         return this.timeout;
      }
   }
}
