package okhttp3.internal.http2

import java.io.Closeable
import java.io.EOFException
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okhttp3.internal.http2.Hpack.Reader
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.Source
import okio.Timeout

public class Http2Reader(source: BufferedSource, client: Boolean) : Closeable {
   private final val client: Boolean
   private final val continuation: okhttp3.internal.http2.Http2Reader.ContinuationSource
   private final val hpackReader: Reader
   private final val source: BufferedSource

   @JvmStatic
   fun {
      val var0: Logger = Logger.getLogger(Http2.class.getName());
      Intrinsics.checkExpressionValueIsNotNull(var0, "Logger.getLogger(Http2::class.java.name)");
      logger = var0;
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      super();
      this.source = var1;
      this.client = var2;
      val var3: Http2Reader.ContinuationSource = new Http2Reader.ContinuationSource(var1);
      this.continuation = var3;
      this.hpackReader = new Hpack.Reader(var3, 4096, 0, 4, null);
   }

   @Throws(java/io/IOException::class)
   private fun readData(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var4 != 0) {
         var var5: Int = 0;
         val var6: Boolean;
         if ((var3 and 1) != 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if ((var3 and 32) == 0) {
            if ((var3 and 8) != 0) {
               var5 = Util.and(this.source.readByte(), 255);
            }

            var1.data(var6, var4, this.source, Companion.lengthWithoutPadding(var2, var3, var5));
            this.source.skip((long)var5);
         } else {
            throw (new IOException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA")) as java.lang.Throwable;
         }
      } else {
         throw (new IOException("PROTOCOL_ERROR: TYPE_DATA streamId == 0")) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readGoAway(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var2 >= 8) {
         if (var4 == 0) {
            var3 = this.source.readInt();
            var4 = this.source.readInt();
            var2 = var2 - 8;
            val var6: ErrorCode = ErrorCode.Companion.fromHttp2(var4);
            if (var6 != null) {
               var var5: ByteString = ByteString.EMPTY;
               if (var2 > 0) {
                  var5 = this.source.readByteString((long)var2);
               }

               var1.goAway(var3, var6, var5);
            } else {
               val var8: StringBuilder = new StringBuilder("TYPE_GOAWAY unexpected error code: ");
               var8.append(var4);
               throw (new IOException(var8.toString())) as java.lang.Throwable;
            }
         } else {
            throw (new IOException("TYPE_GOAWAY streamId != 0")) as java.lang.Throwable;
         }
      } else {
         val var7: StringBuilder = new StringBuilder("TYPE_GOAWAY length < 8: ");
         var7.append(var2);
         throw (new IOException(var7.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readHeaderBlock(length: Int, padding: Int, flags: Int, streamId: Int): List<Header> {
      this.continuation.setLeft(var1);
      this.continuation.setLength(this.continuation.getLeft());
      this.continuation.setPadding(var2);
      this.continuation.setFlags(var3);
      this.continuation.setStreamId(var4);
      this.hpackReader.readHeaders();
      return this.hpackReader.getAndResetHeaderList();
   }

   @Throws(java/io/IOException::class)
   private fun readHeaders(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var4 != 0) {
         var var5: Int = 0;
         val var7: Boolean;
         if ((var3 and 1) != 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if ((var3 and 8) != 0) {
            var5 = Util.and(this.source.readByte(), 255);
         }

         var var6: Int = var2;
         if ((var3 and 32) != 0) {
            this.readPriority(var1, var4);
            var6 = var2 - 5;
         }

         var1.headers(var7, var4, -1, this.readHeaderBlock(Companion.lengthWithoutPadding(var6, var3, var5), var5, var3, var4));
      } else {
         throw (new IOException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0")) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readPing(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var2 == 8) {
         if (var4 == 0) {
            var2 = this.source.readInt();
            var4 = this.source.readInt();
            var var5: Boolean = true;
            if ((var3 and 1) == 0) {
               var5 = false;
            }

            var1.ping(var5, var2, var4);
         } else {
            throw (new IOException("TYPE_PING streamId != 0")) as java.lang.Throwable;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("TYPE_PING length != 8: ");
         var6.append(var2);
         throw (new IOException(var6.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readPriority(handler: okhttp3.internal.http2.Http2Reader.Handler, streamId: Int) {
      val var3: Int = this.source.readInt();
      val var4: Boolean;
      if ((var3 and (int)2147483648L) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var1.priority(var2, var3 and Integer.MAX_VALUE, Util.and(this.source.readByte(), 255) + 1, var4);
   }

   @Throws(java/io/IOException::class)
   private fun readPriority(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var2 == 5) {
         if (var4 != 0) {
            this.readPriority(var1, var4);
         } else {
            throw (new IOException("TYPE_PRIORITY streamId == 0")) as java.lang.Throwable;
         }
      } else {
         val var5: StringBuilder = new StringBuilder("TYPE_PRIORITY length: ");
         var5.append(var2);
         var5.append(" != 5");
         throw (new IOException(var5.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readPushPromise(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var4 != 0) {
         val var5: Int;
         if ((var3 and 8) != 0) {
            var5 = Util.and(this.source.readByte(), 255);
         } else {
            var5 = 0;
         }

         var1.pushPromise(
            var4, this.source.readInt() and Integer.MAX_VALUE, this.readHeaderBlock(Companion.lengthWithoutPadding(var2 - 4, var3, var5), var5, var3, var4)
         );
      } else {
         throw (new IOException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0")) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readRstStream(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var2 == 4) {
         if (var4 != 0) {
            var2 = this.source.readInt();
            val var5: ErrorCode = ErrorCode.Companion.fromHttp2(var2);
            if (var5 != null) {
               var1.rstStream(var4, var5);
            } else {
               val var7: StringBuilder = new StringBuilder("TYPE_RST_STREAM unexpected error code: ");
               var7.append(var2);
               throw (new IOException(var7.toString())) as java.lang.Throwable;
            }
         } else {
            throw (new IOException("TYPE_RST_STREAM streamId == 0")) as java.lang.Throwable;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("TYPE_RST_STREAM length: ");
         var6.append(var2);
         var6.append(" != 4");
         throw (new IOException(var6.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun readSettings(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var4 != 0) {
         throw (new IOException("TYPE_SETTINGS streamId != 0")) as java.lang.Throwable;
      } else if ((var3 and 1) != 0) {
         if (var2 == 0) {
            var1.ackSettings();
         } else {
            throw (new IOException("FRAME_SIZE_ERROR ack frame should be empty!")) as java.lang.Throwable;
         }
      } else if (var2 % 6 != 0) {
         val var11: StringBuilder = new StringBuilder("TYPE_SETTINGS length % 6 != 0: ");
         var11.append(var2);
         throw (new IOException(var11.toString())) as java.lang.Throwable;
      } else {
         val var8: Settings = new Settings();
         val var9: IntProgression = RangesKt.step(RangesKt.until(0, var2), 6);
         var3 = var9.getFirst();
         val var5: Int = var9.getLast();
         val var6: Int = var9.getStep();
         if (if (var6 >= 0) var3 <= var5 else var3 >= var5) {
            while (true) {
               var4 = Util.and(this.source.readShort(), 65535);
               val var7: Int = this.source.readInt();
               if (var4 != 2) {
                  if (var4 != 3) {
                     if (var4 != 4) {
                        if (var4 != 5) {
                           var2 = var4;
                        } else {
                           if (var7 < 16384 || var7 > 16777215) {
                              val var10: StringBuilder = new StringBuilder("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: ");
                              var10.append(var7);
                              throw (new IOException(var10.toString())) as java.lang.Throwable;
                           }

                           var2 = var4;
                        }
                     } else {
                        if (var7 < 0) {
                           throw (new IOException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1")) as java.lang.Throwable;
                        }

                        var2 = 7;
                     }
                  } else {
                     var2 = 4;
                  }
               } else {
                  var2 = var4;
                  if (var7 != 0) {
                     if (var7 != 1) {
                        throw (new IOException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1")) as java.lang.Throwable;
                     }

                     var2 = var4;
                  }
               }

               var8.set(var2, var7);
               if (var3 == var5) {
                  break;
               }

               var3 += var6;
            }
         }

         var1.settings(false, var8);
      }
   }

   @Throws(java/io/IOException::class)
   private fun readWindowUpdate(handler: okhttp3.internal.http2.Http2Reader.Handler, length: Int, flags: Int, streamId: Int) {
      if (var2 == 4) {
         val var5: Long = Util.and(this.source.readInt(), 2147483647L);
         if (var5 != 0L) {
            var1.windowUpdate(var4, var5);
         } else {
            throw (new IOException("windowSizeIncrement was 0")) as java.lang.Throwable;
         }
      } else {
         val var7: StringBuilder = new StringBuilder("TYPE_WINDOW_UPDATE length !=4: ");
         var7.append(var2);
         throw (new IOException(var7.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.source.close();
   }

   @Throws(java/io/IOException::class)
   public fun nextFrame(requireSettings: Boolean, handler: okhttp3.internal.http2.Http2Reader.Handler): Boolean {
      Intrinsics.checkParameterIsNotNull(var2, "handler");

      try {
         this.source.require(9L);
      } catch (var8: EOFException) {
         return false;
      }

      val var5: Int = Util.readMedium(this.source);
      if (var5 <= 16384) {
         val var6: Int = Util.and(this.source.readByte(), 255);
         val var4: Int = Util.and(this.source.readByte(), 255);
         val var3: Int = this.source.readInt() and Integer.MAX_VALUE;
         val var7: Logger = logger;
         if (logger.isLoggable(Level.FINE)) {
            var7.fine(Http2.INSTANCE.frameLog(true, var3, var5, var6, var4));
         }

         if (var1 && var6 != 4) {
            val var10: StringBuilder = new StringBuilder("Expected a SETTINGS frame but was ");
            var10.append(Http2.INSTANCE.formattedType$okhttp(var6));
            throw (new IOException(var10.toString())) as java.lang.Throwable;
         } else {
            switch (var6) {
               case 0:
                  this.readData(var2, var5, var4, var3);
                  break;
               case 1:
                  this.readHeaders(var2, var5, var4, var3);
                  break;
               case 2:
                  this.readPriority(var2, var5, var4, var3);
                  break;
               case 3:
                  this.readRstStream(var2, var5, var4, var3);
                  break;
               case 4:
                  this.readSettings(var2, var5, var4, var3);
                  break;
               case 5:
                  this.readPushPromise(var2, var5, var4, var3);
                  break;
               case 6:
                  this.readPing(var2, var5, var4, var3);
                  break;
               case 7:
                  this.readGoAway(var2, var5, var4, var3);
                  break;
               case 8:
                  this.readWindowUpdate(var2, var5, var4, var3);
                  break;
               default:
                  this.source.skip((long)var5);
            }

            return true;
         }
      } else {
         val var9: StringBuilder = new StringBuilder("FRAME_SIZE_ERROR: ");
         var9.append(var5);
         throw (new IOException(var9.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun readConnectionPreface(handler: okhttp3.internal.http2.Http2Reader.Handler) {
      Intrinsics.checkParameterIsNotNull(var1, "handler");
      if (this.client) {
         if (!this.nextFrame(true, var1)) {
            throw (new IOException("Required SETTINGS preface not received")) as java.lang.Throwable;
         }
      } else {
         val var4: ByteString = this.source.readByteString((long)Http2.CONNECTION_PREFACE.size());
         val var3: Logger = logger;
         if (logger.isLoggable(Level.FINE)) {
            val var2: StringBuilder = new StringBuilder("<< CONNECTION ");
            var2.append(var4.hex());
            var3.fine(Util.format(var2.toString()));
         }

         if (!(Http2.CONNECTION_PREFACE == var4)) {
            val var5: StringBuilder = new StringBuilder("Expected a connection header but was ");
            var5.append(var4.utf8());
            throw (new IOException(var5.toString())) as java.lang.Throwable;
         }
      }
   }

   public companion object {
      public final val logger: Logger

      @Throws(java/io/IOException::class)
      public fun lengthWithoutPadding(length: Int, flags: Int, padding: Int): Int {
         var var4: Int = var1;
         if ((var2 and 8) != 0) {
            var4 = var1 - 1;
         }

         if (var3 <= var4) {
            return var4 - var3;
         } else {
            val var5: StringBuilder = new StringBuilder("PROTOCOL_ERROR padding ");
            var5.append(var3);
            var5.append(" > remaining length ");
            var5.append(var4);
            throw (new IOException(var5.toString())) as java.lang.Throwable;
         }
      }
   }

   internal class ContinuationSource(source: BufferedSource) : Source {
      public final var flags: Int
         internal set

      public final var left: Int
         internal set

      public final var length: Int
         internal set

      public final var padding: Int
         internal set

      private final val source: BufferedSource

      public final var streamId: Int
         internal set

      init {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         super();
         this.source = var1;
      }

      @Throws(java/io/IOException::class)
      private fun readContinuationHeader() {
         val var1: Int = this.streamId;
         var var2: Int = Util.readMedium(this.source);
         this.left = var2;
         this.length = var2;
         val var3: Int = Util.and(this.source.readByte(), 255);
         this.flags = Util.and(this.source.readByte(), 255);
         if (Http2Reader.Companion.getLogger().isLoggable(Level.FINE)) {
            Http2Reader.Companion.getLogger().fine(Http2.INSTANCE.frameLog(true, this.streamId, this.length, var3, this.flags));
         }

         var2 = this.source.readInt() and Integer.MAX_VALUE;
         this.streamId = var2;
         if (var3 == 9) {
            if (var2 != var1) {
               throw (new IOException("TYPE_CONTINUATION streamId changed")) as java.lang.Throwable;
            }
         } else {
            val var4: StringBuilder = new StringBuilder();
            var4.append(var3);
            var4.append(" != TYPE_CONTINUATION");
            throw (new IOException(var4.toString())) as java.lang.Throwable;
         }
      }

      @Throws(java/io/IOException::class)
      public override fun close() {
      }

      @Throws(java/io/IOException::class)
      public override fun read(sink: Buffer, byteCount: Long): Long {
         Intrinsics.checkParameterIsNotNull(var1, "sink");

         while (true) {
            if (this.left != 0) {
               var2 = this.source.read(var1, Math.min(var2, (long)this.left));
               if (var2 == -1L) {
                  return -1L;
               }

               this.left -= (int)var2;
               return var2;
            }

            this.source.skip((long)this.padding);
            this.padding = 0;
            if ((this.flags and 4) != 0) {
               return -1L;
            }

            this.readContinuationHeader();
         }
      }

      public override fun timeout(): Timeout {
         return this.source.timeout();
      }
   }

   public interface Handler {
      public abstract fun ackSettings() {
      }

      public abstract fun alternateService(streamId: Int, origin: String, protocol: ByteString, host: String, port: Int, maxAge: Long) {
      }

      @Throws(java/io/IOException::class)
      public abstract fun data(inFinished: Boolean, streamId: Int, source: BufferedSource, length: Int) {
      }

      public abstract fun goAway(lastGoodStreamId: Int, errorCode: ErrorCode, debugData: ByteString) {
      }

      public abstract fun headers(inFinished: Boolean, streamId: Int, associatedStreamId: Int, headerBlock: List<Header>) {
      }

      public abstract fun ping(ack: Boolean, payload1: Int, payload2: Int) {
      }

      public abstract fun priority(streamId: Int, streamDependency: Int, weight: Int, exclusive: Boolean) {
      }

      @Throws(java/io/IOException::class)
      public abstract fun pushPromise(streamId: Int, promisedStreamId: Int, requestHeaders: List<Header>) {
      }

      public abstract fun rstStream(streamId: Int, errorCode: ErrorCode) {
      }

      public abstract fun settings(clearPrevious: Boolean, settings: Settings) {
      }

      public abstract fun windowUpdate(streamId: Int, windowSizeIncrement: Long) {
      }
   }
}
