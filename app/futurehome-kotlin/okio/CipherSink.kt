package okio

import javax.crypto.Cipher

public class CipherSink(sink: BufferedSink, cipher: Cipher) : Sink {
   private final val blockSize: Int
   public final val cipher: Cipher
   private final var closed: Boolean
   private final val sink: BufferedSink

   init {
      this.sink = var1;
      this.cipher = var2;
      val var3: Int = var2.getBlockSize();
      this.blockSize = var3;
      if (var3 <= 0) {
         val var4: StringBuilder = new StringBuilder("Block cipher required ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   private fun doFinal(): Throwable? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield okio/CipherSink.cipher Ljavax/crypto/Cipher;
      // 04: bipush 0
      // 05: invokevirtual javax/crypto/Cipher.getOutputSize (I)I
      // 08: istore 1
      // 09: aconst_null
      // 0a: astore 2
      // 0b: iload 1
      // 0c: ifne 11
      // 0f: aconst_null
      // 10: areturn
      // 11: iload 1
      // 12: sipush 8192
      // 15: if_icmple 38
      // 18: aload 0
      // 19: getfield okio/CipherSink.sink Lokio/BufferedSink;
      // 1c: astore 2
      // 1d: aload 0
      // 1e: getfield okio/CipherSink.cipher Ljavax/crypto/Cipher;
      // 21: invokevirtual javax/crypto/Cipher.doFinal ()[B
      // 24: astore 3
      // 25: aload 3
      // 26: ldc "cipher.doFinal()"
      // 28: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 2b: aload 2
      // 2c: aload 3
      // 2d: invokeinterface okio/BufferedSink.write ([B)Lokio/BufferedSink; 2
      // 32: pop
      // 33: aconst_null
      // 34: areturn
      // 35: astore 2
      // 36: aload 2
      // 37: areturn
      // 38: aload 0
      // 39: getfield okio/CipherSink.sink Lokio/BufferedSink;
      // 3c: invokeinterface okio/BufferedSink.getBuffer ()Lokio/Buffer; 1
      // 41: astore 4
      // 43: aload 4
      // 45: iload 1
      // 46: invokevirtual okio/Buffer.writableSegment$okio (I)Lokio/Segment;
      // 49: astore 3
      // 4a: aload 0
      // 4b: getfield okio/CipherSink.cipher Ljavax/crypto/Cipher;
      // 4e: aload 3
      // 4f: getfield okio/Segment.data [B
      // 52: aload 3
      // 53: getfield okio/Segment.limit I
      // 56: invokevirtual javax/crypto/Cipher.doFinal ([BI)I
      // 59: istore 1
      // 5a: aload 3
      // 5b: aload 3
      // 5c: getfield okio/Segment.limit I
      // 5f: iload 1
      // 60: iadd
      // 61: putfield okio/Segment.limit I
      // 64: aload 4
      // 66: aload 4
      // 68: invokevirtual okio/Buffer.size ()J
      // 6b: iload 1
      // 6c: i2l
      // 6d: ladd
      // 6e: invokevirtual okio/Buffer.setSize$okio (J)V
      // 71: goto 75
      // 74: astore 2
      // 75: aload 3
      // 76: getfield okio/Segment.pos I
      // 79: aload 3
      // 7a: getfield okio/Segment.limit I
      // 7d: if_icmpne 8d
      // 80: aload 4
      // 82: aload 3
      // 83: invokevirtual okio/Segment.pop ()Lokio/Segment;
      // 86: putfield okio/Buffer.head Lokio/Segment;
      // 89: aload 3
      // 8a: invokestatic okio/SegmentPool.recycle (Lokio/Segment;)V
      // 8d: aload 2
      // 8e: areturn
   }

   private fun update(source: Buffer, remaining: Long): Int {
      val var6: Segment = var1.head;
      var var4: Int = (int)Math.min(var2, (long)(var6.limit - var6.pos));
      val var7: Buffer = this.sink.getBuffer();

      var var5: Int;
      for (var5 = this.cipher.getOutputSize(var4); var5 > 8192; var5 = this.cipher.getOutputSize(var4)) {
         if (var4 <= this.blockSize) {
            val var12: BufferedSink = this.sink;
            val var9: ByteArray = this.cipher.update(var1.readByteArray(var2));
            var12.write(var9);
            return (int)var2;
         }

         var4 -= this.blockSize;
      }

      val var8: Segment = var7.writableSegment$okio(var5);
      var5 = this.cipher.update(var6.data, var6.pos, var4, var8.data, var8.limit);
      var8.limit += var5;
      var7.setSize$okio(var7.size() + (long)var5);
      if (var8.pos == var8.limit) {
         var7.head = var8.pop();
         SegmentPool.recycle(var8);
      }

      this.sink.emitCompleteSegments();
      var1.setSize$okio(var1.size() - (long)var4);
      var6.pos += var4;
      if (var6.pos == var6.limit) {
         var1.head = var6.pop();
         SegmentPool.recycle(var6);
      }

      return var4;
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      if (!this.closed) {
         this.closed = true;
         val var2: java.lang.Throwable = this.doFinal();

         var var1: java.lang.Throwable;
         label23: {
            try {
               this.sink.close();
            } catch (var4: java.lang.Throwable) {
               var1 = var2;
               if (var2 == null) {
                  var1 = var4;
               }
               break label23;
            }

            var1 = var2;
         }

         if (var1 != null) {
            throw var1;
         }
      }
   }

   public override fun flush() {
      this.sink.flush();
   }

   public override fun timeout(): Timeout {
      return this.sink.timeout();
   }

   @Throws(java/io/IOException::class)
   public override fun write(source: Buffer, byteCount: Long) {
      _SegmentedByteString.checkOffsetAndCount(var1.size(), 0L, var2);
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         while (var2 > 0L) {
            var2 -= this.update(var1, var2);
         }
      }
   }
}
