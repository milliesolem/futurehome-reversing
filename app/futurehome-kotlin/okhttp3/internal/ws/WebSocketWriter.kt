package okhttp3.internal.ws

import java.io.Closeable
import java.io.IOException
import java.util.Random
import kotlin.jvm.internal.Intrinsics
import okio.Buffer
import okio.BufferedSink
import okio.ByteString
import okio.Buffer.UnsafeCursor

public class WebSocketWriter(isClient: Boolean,
      sink: BufferedSink,
      random: Random,
      perMessageDeflate: Boolean,
      noContextTakeover: Boolean,
      minimumDeflateSize: Long
   ) :
   Closeable {
   private final val isClient: Boolean
   private final val maskCursor: UnsafeCursor?
   private final val maskKey: ByteArray?
   private final val messageBuffer: Buffer
   private final var messageDeflater: MessageDeflater?
   private final val minimumDeflateSize: Long
   private final val noContextTakeover: Boolean
   private final val perMessageDeflate: Boolean
   public final val random: Random
   public final val sink: BufferedSink
   private final val sinkBuffer: Buffer
   private final var writerClosed: Boolean

   init {
      Intrinsics.checkParameterIsNotNull(var2, "sink");
      Intrinsics.checkParameterIsNotNull(var3, "random");
      super();
      this.isClient = var1;
      this.sink = var2;
      this.random = var3;
      this.perMessageDeflate = var4;
      this.noContextTakeover = var5;
      this.minimumDeflateSize = var6;
      this.messageBuffer = new Buffer();
      this.sinkBuffer = var2.getBuffer();
      val var8: ByteArray;
      if (var1) {
         var8 = new byte[4];
      } else {
         var8 = null;
      }

      this.maskKey = var8;
      var var9: Buffer.UnsafeCursor = null;
      if (var1) {
         var9 = new Buffer.UnsafeCursor();
      }

      this.maskCursor = var9;
   }

   @Throws(java/io/IOException::class)
   private fun writeControlFrame(opcode: Int, payload: ByteString) {
      if (!this.writerClosed) {
         val var4: Int = var2.size();
         val var3: Boolean;
         if (var4 <= 125L) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.sinkBuffer.writeByte(var1 or 128);
            if (this.isClient) {
               this.sinkBuffer.writeByte(var4 or 128);
               if (this.maskKey == null) {
                  Intrinsics.throwNpe();
               }

               this.random.nextBytes(this.maskKey);
               this.sinkBuffer.write(this.maskKey);
               if (var4 > 0) {
                  val var5: Long = this.sinkBuffer.size();
                  this.sinkBuffer.write(var2);
                  if (this.maskCursor == null) {
                     Intrinsics.throwNpe();
                  }

                  this.sinkBuffer.readAndWriteUnsafe(this.maskCursor);
                  this.maskCursor.seek(var5);
                  WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
                  this.maskCursor.close();
               }
            } else {
               this.sinkBuffer.writeByte(var4);
               this.sinkBuffer.write(var2);
            }

            this.sink.flush();
         } else {
            throw (new IllegalArgumentException("Payload size must be less than or equal to 125".toString())) as java.lang.Throwable;
         }
      } else {
         throw (new IOException("closed")) as java.lang.Throwable;
      }
   }

   public override fun close() {
      if (this.messageDeflater != null) {
         this.messageDeflater.close();
      }
   }

   @Throws(java/io/IOException::class)
   public fun writeClose(code: Int, reason: ByteString?) {
      label46: {
         var var3: ByteString = ByteString.EMPTY;
         if (var1 != 0 || var2 != null) {
            if (var1 != 0) {
               WebSocketProtocol.INSTANCE.validateCloseCode(var1);
            }

            val var6: Buffer = new Buffer();
            var6.writeShort(var1);
            if (var2 != null) {
               var6.write(var2);
            }

            var3 = var6.readByteString();
         }

         try {
            this.writeControlFrame(8, var3);
         } catch (var4: java.lang.Throwable) {
            this.writerClosed = true;
         }

         this.writerClosed = true;
      }
   }

   @Throws(java/io/IOException::class)
   public fun writeMessageFrame(formatOpcode: Int, data: ByteString) {
      Intrinsics.checkParameterIsNotNull(var2, "data");
      if (!this.writerClosed) {
         this.messageBuffer.write(var2);
         val var4: Int = var1 or 128;
         var var3: Int = var1 or 128;
         if (this.perMessageDeflate) {
            var3 = var4;
            if (var2.size() >= this.minimumDeflateSize) {
               var var9: MessageDeflater = this.messageDeflater;
               if (this.messageDeflater == null) {
                  var9 = new MessageDeflater(this.noContextTakeover);
                  this.messageDeflater = var9;
               }

               var9.deflate(this.messageBuffer);
               var3 = var1 or 192;
            }
         }

         val var5: Long = this.messageBuffer.size();
         this.sinkBuffer.writeByte(var3);
         val var8: Short;
         if (this.isClient) {
            var8 = 128;
         } else {
            var8 = 0;
         }

         if (var5 <= 125L) {
            this.sinkBuffer.writeByte(var8 or (int)var5);
         } else if (var5 <= 65535L) {
            this.sinkBuffer.writeByte(var8 or 126);
            this.sinkBuffer.writeShort((int)var5);
         } else {
            this.sinkBuffer.writeByte(var8 or 127);
            this.sinkBuffer.writeLong(var5);
         }

         if (this.isClient) {
            if (this.maskKey == null) {
               Intrinsics.throwNpe();
            }

            this.random.nextBytes(this.maskKey);
            this.sinkBuffer.write(this.maskKey);
            if (var5 > 0L) {
               if (this.maskCursor == null) {
                  Intrinsics.throwNpe();
               }

               this.messageBuffer.readAndWriteUnsafe(this.maskCursor);
               this.maskCursor.seek(0L);
               WebSocketProtocol.INSTANCE.toggleMask(this.maskCursor, this.maskKey);
               this.maskCursor.close();
            }
         }

         this.sinkBuffer.write(this.messageBuffer, var5);
         this.sink.emit();
      } else {
         throw (new IOException("closed")) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun writePing(payload: ByteString) {
      Intrinsics.checkParameterIsNotNull(var1, "payload");
      this.writeControlFrame(9, var1);
   }

   @Throws(java/io/IOException::class)
   public fun writePong(payload: ByteString) {
      Intrinsics.checkParameterIsNotNull(var1, "payload");
      this.writeControlFrame(10, var1);
   }
}
