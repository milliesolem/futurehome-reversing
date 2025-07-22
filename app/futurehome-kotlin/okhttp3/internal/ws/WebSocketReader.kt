package okhttp3.internal.ws

import java.io.Closeable
import java.io.IOException
import java.net.ProtocolException
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.Buffer.UnsafeCursor

public class WebSocketReader(isClient: Boolean,
      source: BufferedSource,
      frameCallback: okhttp3.internal.ws.WebSocketReader.FrameCallback,
      perMessageDeflate: Boolean,
      noContextTakeover: Boolean
   ) :
   Closeable {
   private final var closed: Boolean
   private final val controlFrameBuffer: Buffer
   private final val frameCallback: okhttp3.internal.ws.WebSocketReader.FrameCallback
   private final var frameLength: Long
   private final val isClient: Boolean
   private final var isControlFrame: Boolean
   private final var isFinalFrame: Boolean
   private final val maskCursor: UnsafeCursor?
   private final val maskKey: ByteArray?
   private final val messageFrameBuffer: Buffer
   private final var messageInflater: MessageInflater?
   private final val noContextTakeover: Boolean
   private final var opcode: Int
   private final val perMessageDeflate: Boolean
   private final var readingCompressedMessage: Boolean
   public final val source: BufferedSource

   init {
      Intrinsics.checkParameterIsNotNull(var2, "source");
      Intrinsics.checkParameterIsNotNull(var3, "frameCallback");
      super();
      this.isClient = var1;
      this.source = var2;
      this.frameCallback = var3;
      this.perMessageDeflate = var4;
      this.noContextTakeover = var5;
      this.controlFrameBuffer = new Buffer();
      this.messageFrameBuffer = new Buffer();
      val var6: ByteArray;
      if (var1) {
         var6 = null;
      } else {
         var6 = new byte[4];
      }

      this.maskKey = var6;
      val var7: Buffer.UnsafeCursor;
      if (var1) {
         var7 = null;
      } else {
         var7 = new Buffer.UnsafeCursor();
      }

      this.maskCursor = var7;
   }

   @Throws(java/io/IOException::class)
   private fun readControlFrame() {
      if (this.frameLength > 0L) {
         this.source.readFully(this.controlFrameBuffer, this.frameLength);
         if (!this.isClient) {
            if (this.maskCursor == null) {
               Intrinsics.throwNpe();
            }

            this.controlFrameBuffer.readAndWriteUnsafe(this.maskCursor);
            this.maskCursor.seek(0L);
            if (this.maskKey == null) {
               Intrinsics.throwNpe();
            }

            WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
            this.maskCursor.close();
         }
      }

      switch (this.opcode) {
         case 8:
            val var7: Long = this.controlFrameBuffer.size();
            if (var7 == 1L) {
               throw (new ProtocolException("Malformed close payload length of 1.")) as java.lang.Throwable;
            }

            val var1: Short;
            val var10: java.lang.String;
            if (var7 != 0L) {
               var1 = this.controlFrameBuffer.readShort();
               var10 = this.controlFrameBuffer.readUtf8();
               val var12: java.lang.String = WebSocketProtocol.INSTANCE.closeCodeExceptionMessage(var1);
               if (var12 != null) {
                  throw (new ProtocolException(var12)) as java.lang.Throwable;
               }
            } else {
               var1 = 1005;
               var10 = "";
            }

            this.frameCallback.onReadClose(var1, var10);
            this.closed = true;
            break;
         case 9:
            this.frameCallback.onReadPing(this.controlFrameBuffer.readByteString());
            break;
         case 10:
            this.frameCallback.onReadPong(this.controlFrameBuffer.readByteString());
            break;
         default:
            val var9: StringBuilder = new StringBuilder("Unknown control opcode: ");
            var9.append(Util.toHexString(this.opcode));
            throw (new ProtocolException(var9.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class, java/net/ProtocolException::class)
   private fun readHeader() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield okhttp3/internal/ws/WebSocketReader.closed Z
      // 004: ifne 236
      // 007: aload 0
      // 008: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 00b: invokeinterface okio/BufferedSource.timeout ()Lokio/Timeout; 1
      // 010: invokevirtual okio/Timeout.timeoutNanos ()J
      // 013: lstore 7
      // 015: aload 0
      // 016: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 019: invokeinterface okio/BufferedSource.timeout ()Lokio/Timeout; 1
      // 01e: invokevirtual okio/Timeout.clearTimeout ()Lokio/Timeout;
      // 021: pop
      // 022: aload 0
      // 023: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 026: invokeinterface okio/BufferedSource.readByte ()B 1
      // 02b: sipush 255
      // 02e: invokestatic okhttp3/internal/Util.and (BI)I
      // 031: istore 3
      // 032: aload 0
      // 033: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 036: invokeinterface okio/BufferedSource.timeout ()Lokio/Timeout; 1
      // 03b: lload 7
      // 03d: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
      // 040: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
      // 043: pop
      // 044: iload 3
      // 045: bipush 15
      // 047: iand
      // 048: istore 2
      // 049: aload 0
      // 04a: iload 2
      // 04b: putfield okhttp3/internal/ws/WebSocketReader.opcode I
      // 04e: bipush 0
      // 04f: istore 6
      // 051: iload 3
      // 052: sipush 128
      // 055: iand
      // 056: ifeq 05f
      // 059: bipush 1
      // 05a: istore 4
      // 05c: goto 062
      // 05f: bipush 0
      // 060: istore 4
      // 062: aload 0
      // 063: iload 4
      // 065: putfield okhttp3/internal/ws/WebSocketReader.isFinalFrame Z
      // 068: iload 3
      // 069: bipush 8
      // 06b: iand
      // 06c: ifeq 075
      // 06f: bipush 1
      // 070: istore 5
      // 072: goto 078
      // 075: bipush 0
      // 076: istore 5
      // 078: aload 0
      // 079: iload 5
      // 07b: putfield okhttp3/internal/ws/WebSocketReader.isControlFrame Z
      // 07e: iload 5
      // 080: ifeq 098
      // 083: iload 4
      // 085: ifeq 08b
      // 088: goto 098
      // 08b: new java/net/ProtocolException
      // 08e: dup
      // 08f: ldc "Control frames must be final."
      // 091: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 094: checkcast java/lang/Throwable
      // 097: athrow
      // 098: iload 3
      // 099: bipush 64
      // 09b: iand
      // 09c: ifeq 0a4
      // 09f: bipush 1
      // 0a0: istore 1
      // 0a1: goto 0a6
      // 0a4: bipush 0
      // 0a5: istore 1
      // 0a6: iload 2
      // 0a7: bipush 1
      // 0a8: if_icmpeq 0c4
      // 0ab: iload 2
      // 0ac: bipush 2
      // 0ad: if_icmpeq 0c4
      // 0b0: iload 1
      // 0b1: ifne 0b7
      // 0b4: goto 0e9
      // 0b7: new java/net/ProtocolException
      // 0ba: dup
      // 0bb: ldc "Unexpected rsv1 flag"
      // 0bd: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 0c0: checkcast java/lang/Throwable
      // 0c3: athrow
      // 0c4: iload 1
      // 0c5: ifeq 0e4
      // 0c8: aload 0
      // 0c9: getfield okhttp3/internal/ws/WebSocketReader.perMessageDeflate Z
      // 0cc: ifeq 0d7
      // 0cf: aload 0
      // 0d0: bipush 1
      // 0d1: putfield okhttp3/internal/ws/WebSocketReader.readingCompressedMessage Z
      // 0d4: goto 0e9
      // 0d7: new java/net/ProtocolException
      // 0da: dup
      // 0db: ldc "Unexpected rsv1 flag"
      // 0dd: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 0e0: checkcast java/lang/Throwable
      // 0e3: athrow
      // 0e4: aload 0
      // 0e5: bipush 0
      // 0e6: putfield okhttp3/internal/ws/WebSocketReader.readingCompressedMessage Z
      // 0e9: iload 3
      // 0ea: bipush 32
      // 0ec: iand
      // 0ed: ifne 211
      // 0f0: iload 3
      // 0f1: bipush 16
      // 0f3: iand
      // 0f4: ifne 203
      // 0f7: aload 0
      // 0f8: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 0fb: invokeinterface okio/BufferedSource.readByte ()B 1
      // 100: sipush 255
      // 103: invokestatic okhttp3/internal/Util.and (BI)I
      // 106: istore 1
      // 107: iload 6
      // 109: istore 4
      // 10b: iload 1
      // 10c: sipush 128
      // 10f: iand
      // 110: ifeq 116
      // 113: bipush 1
      // 114: istore 4
      // 116: iload 4
      // 118: aload 0
      // 119: getfield okhttp3/internal/ws/WebSocketReader.isClient Z
      // 11c: if_icmpne 13e
      // 11f: aload 0
      // 120: getfield okhttp3/internal/ws/WebSocketReader.isClient Z
      // 123: ifeq 12d
      // 126: ldc "Server-sent frames must not be masked."
      // 128: astore 9
      // 12a: goto 131
      // 12d: ldc "Client-sent frames must be masked."
      // 12f: astore 9
      // 131: new java/net/ProtocolException
      // 134: dup
      // 135: aload 9
      // 137: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 13a: checkcast java/lang/Throwable
      // 13d: athrow
      // 13e: iload 1
      // 13f: bipush 127
      // 141: iand
      // 142: i2l
      // 143: lstore 7
      // 145: aload 0
      // 146: lload 7
      // 148: putfield okhttp3/internal/ws/WebSocketReader.frameLength J
      // 14b: lload 7
      // 14d: bipush 126
      // 14f: i2l
      // 150: lcmp
      // 151: ifne 16a
      // 154: aload 0
      // 155: aload 0
      // 156: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 159: invokeinterface okio/BufferedSource.readShort ()S 1
      // 15e: ldc 65535
      // 160: invokestatic okhttp3/internal/Util.and (SI)I
      // 163: i2l
      // 164: putfield okhttp3/internal/ws/WebSocketReader.frameLength J
      // 167: goto 1be
      // 16a: lload 7
      // 16c: bipush 127
      // 16e: i2l
      // 16f: lcmp
      // 170: ifne 1be
      // 173: aload 0
      // 174: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 177: invokeinterface okio/BufferedSource.readLong ()J 1
      // 17c: lstore 7
      // 17e: aload 0
      // 17f: lload 7
      // 181: putfield okhttp3/internal/ws/WebSocketReader.frameLength J
      // 184: lload 7
      // 186: lconst_0
      // 187: lcmp
      // 188: iflt 18e
      // 18b: goto 1be
      // 18e: new java/lang/StringBuilder
      // 191: dup
      // 192: ldc "Frame length 0x"
      // 194: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 197: astore 9
      // 199: aload 9
      // 19b: aload 0
      // 19c: getfield okhttp3/internal/ws/WebSocketReader.frameLength J
      // 19f: invokestatic okhttp3/internal/Util.toHexString (J)Ljava/lang/String;
      // 1a2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1a5: pop
      // 1a6: aload 9
      // 1a8: ldc " > 0x7FFFFFFFFFFFFFFF"
      // 1aa: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ad: pop
      // 1ae: new java/net/ProtocolException
      // 1b1: dup
      // 1b2: aload 9
      // 1b4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1b7: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 1ba: checkcast java/lang/Throwable
      // 1bd: athrow
      // 1be: aload 0
      // 1bf: getfield okhttp3/internal/ws/WebSocketReader.isControlFrame Z
      // 1c2: ifeq 1e0
      // 1c5: aload 0
      // 1c6: getfield okhttp3/internal/ws/WebSocketReader.frameLength J
      // 1c9: ldc2_w 125
      // 1cc: lcmp
      // 1cd: ifgt 1d3
      // 1d0: goto 1e0
      // 1d3: new java/net/ProtocolException
      // 1d6: dup
      // 1d7: ldc "Control frame must be less than 125B."
      // 1d9: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 1dc: checkcast java/lang/Throwable
      // 1df: athrow
      // 1e0: iload 4
      // 1e2: ifeq 202
      // 1e5: aload 0
      // 1e6: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 1e9: astore 9
      // 1eb: aload 0
      // 1ec: getfield okhttp3/internal/ws/WebSocketReader.maskKey [B
      // 1ef: astore 10
      // 1f1: aload 10
      // 1f3: ifnonnull 1f9
      // 1f6: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 1f9: aload 9
      // 1fb: aload 10
      // 1fd: invokeinterface okio/BufferedSource.readFully ([B)V 2
      // 202: return
      // 203: new java/net/ProtocolException
      // 206: dup
      // 207: ldc_w "Unexpected rsv3 flag"
      // 20a: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 20d: checkcast java/lang/Throwable
      // 210: athrow
      // 211: new java/net/ProtocolException
      // 214: dup
      // 215: ldc_w "Unexpected rsv2 flag"
      // 218: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 21b: checkcast java/lang/Throwable
      // 21e: athrow
      // 21f: astore 9
      // 221: aload 0
      // 222: getfield okhttp3/internal/ws/WebSocketReader.source Lokio/BufferedSource;
      // 225: invokeinterface okio/BufferedSource.timeout ()Lokio/Timeout; 1
      // 22a: lload 7
      // 22c: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
      // 22f: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
      // 232: pop
      // 233: aload 9
      // 235: athrow
      // 236: new java/io/IOException
      // 239: dup
      // 23a: ldc_w "closed"
      // 23d: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 240: checkcast java/lang/Throwable
      // 243: athrow
   }

   @Throws(java/io/IOException::class)
   private fun readMessage() {
      while (!this.closed) {
         if (this.frameLength > 0L) {
            this.source.readFully(this.messageFrameBuffer, this.frameLength);
            if (!this.isClient) {
               if (this.maskCursor == null) {
                  Intrinsics.throwNpe();
               }

               this.messageFrameBuffer.readAndWriteUnsafe(this.maskCursor);
               this.maskCursor.seek(this.messageFrameBuffer.size() - this.frameLength);
               if (this.maskKey == null) {
                  Intrinsics.throwNpe();
               }

               WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
               this.maskCursor.close();
            }
         }

         if (this.isFinalFrame) {
            return;
         }

         this.readUntilNonControlFrame();
         if (this.opcode != 0) {
            val var7: StringBuilder = new StringBuilder("Expected continuation opcode. Got: ");
            var7.append(Util.toHexString(this.opcode));
            throw (new ProtocolException(var7.toString())) as java.lang.Throwable;
         }
      }

      throw (new IOException("closed")) as java.lang.Throwable;
   }

   @Throws(java/io/IOException::class)
   private fun readMessageFrame() {
      val var1: Int = this.opcode;
      if (this.opcode != 1 && this.opcode != 2) {
         val var3: StringBuilder = new StringBuilder("Unknown opcode: ");
         var3.append(Util.toHexString(var1));
         throw (new ProtocolException(var3.toString())) as java.lang.Throwable;
      } else {
         this.readMessage();
         if (this.readingCompressedMessage) {
            var var2: MessageInflater = this.messageInflater;
            if (this.messageInflater == null) {
               var2 = new MessageInflater(this.noContextTakeover);
               this.messageInflater = var2;
            }

            var2.inflate(this.messageFrameBuffer);
         }

         if (var1 == 1) {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.readUtf8());
         } else {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.readByteString());
         }
      }
   }

   @Throws(java/io/IOException::class)
   private fun readUntilNonControlFrame() {
      while (!this.closed) {
         this.readHeader();
         if (this.isControlFrame) {
            this.readControlFrame();
            continue;
         }
         break;
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      if (this.messageInflater != null) {
         this.messageInflater.close();
      }
   }

   @Throws(java/io/IOException::class)
   public fun processNextFrame() {
      this.readHeader();
      if (this.isControlFrame) {
         this.readControlFrame();
      } else {
         this.readMessageFrame();
      }
   }

   public interface FrameCallback {
      public abstract fun onReadClose(code: Int, reason: String) {
      }

      @Throws(java/io/IOException::class)
      public abstract fun onReadMessage(text: String) {
      }

      @Throws(java/io/IOException::class)
      public abstract fun onReadMessage(bytes: ByteString) {
      }

      public abstract fun onReadPing(payload: ByteString) {
      }

      public abstract fun onReadPong(payload: ByteString) {
      }
   }
}
