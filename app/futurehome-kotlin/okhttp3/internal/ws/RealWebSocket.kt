package okhttp3.internal.ws

import java.io.Closeable
import java.io.IOException
import java.net.ProtocolException
import java.util.ArrayDeque
import java.util.Random
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.Call
import okhttp3.Callback
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.internal.Util
import okhttp3.internal.concurrent.Task
import okhttp3.internal.concurrent.TaskQueue
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.connection.Exchange
import okhttp3.internal.connection.RealCall
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString

public class RealWebSocket(taskRunner: TaskRunner,
      originalRequest: Request,
      listener: WebSocketListener,
      random: Random,
      pingIntervalMillis: Long,
      extensions: WebSocketExtensions?,
      minimumDeflateSize: Long
   ) :
   WebSocket,
   WebSocketReader.FrameCallback {
   private final var awaitingPong: Boolean
   private final var call: Call?
   private final var enqueuedClose: Boolean
   private final var extensions: WebSocketExtensions?
   private final var failed: Boolean
   private final val key: String
   internal final val listener: WebSocketListener
   private final val messageAndCloseQueue: ArrayDeque<Any>
   private final var minimumDeflateSize: Long
   private final var name: String?
   private final val originalRequest: Request
   private final val pingIntervalMillis: Long
   private final val pongQueue: ArrayDeque<ByteString>
   private final var queueSize: Long
   private final val random: Random
   private final var reader: WebSocketReader?
   private final var receivedCloseCode: Int
   private final var receivedCloseReason: String?
   private final var receivedPingCount: Int
   private final var receivedPongCount: Int
   private final var sentPingCount: Int
   private final var streams: okhttp3.internal.ws.RealWebSocket.Streams?
   private final var taskQueue: TaskQueue
   private final var writer: WebSocketWriter?
   private final var writerTask: Task?

   init {
      Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      Intrinsics.checkParameterIsNotNull(var2, "originalRequest");
      Intrinsics.checkParameterIsNotNull(var3, "listener");
      Intrinsics.checkParameterIsNotNull(var4, "random");
      super();
      this.originalRequest = var2;
      this.listener = var3;
      this.random = var4;
      this.pingIntervalMillis = var5;
      this.extensions = var7;
      this.minimumDeflateSize = var8;
      this.taskQueue = var1.newQueue();
      this.pongQueue = new ArrayDeque<>();
      this.messageAndCloseQueue = new ArrayDeque<>();
      this.receivedCloseCode = -1;
      if ("GET" == var2.method()) {
         val var12: ByteString.Companion = ByteString.Companion;
         val var11: ByteArray = new byte[16];
         var4.nextBytes(var11);
         this.key = ByteString.Companion.of$default(var12, var11, 0, 0, 3, null).base64();
      } else {
         val var10: StringBuilder = new StringBuilder("Request must be GET: ");
         var10.append(var2.method());
         throw (new IllegalArgumentException(var10.toString().toString())) as java.lang.Throwable;
      }
   }

   private fun WebSocketExtensions.isValid(): Boolean {
      if (var1.unknownValues) {
         return false;
      } else if (var1.clientMaxWindowBits != null) {
         return false;
      } else {
         if (var1.serverMaxWindowBits != null) {
            val var2: Int = var1.serverMaxWindowBits;
            if (8 > var2 || 15 < var2) {
               return false;
            }
         }

         return true;
      }
   }

   private fun runWriter() {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var3: StringBuilder = new StringBuilder("Thread ");
         val var2: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
         var3.append(var2.getName());
         var3.append(" MUST hold lock on ");
         var3.append(this);
         throw (new AssertionError(var3.toString())) as java.lang.Throwable;
      } else {
         if (this.writerTask != null) {
            TaskQueue.schedule$default(this.taskQueue, this.writerTask, 0L, 2, null);
         }
      }
   }

   private fun send(data: ByteString, formatOpcode: Int): Boolean {
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
      // 03: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 06: ifne 5f
      // 09: aload 0
      // 0a: getfield okhttp3/internal/ws/RealWebSocket.enqueuedClose Z
      // 0d: ifeq 13
      // 10: goto 5f
      // 13: aload 0
      // 14: getfield okhttp3/internal/ws/RealWebSocket.queueSize J
      // 17: aload 1
      // 18: invokevirtual okio/ByteString.size ()I
      // 1b: i2l
      // 1c: ladd
      // 1d: ldc2_w 16777216
      // 20: lcmp
      // 21: ifle 31
      // 24: aload 0
      // 25: sipush 1001
      // 28: aconst_null
      // 29: invokevirtual okhttp3/internal/ws/RealWebSocket.close (ILjava/lang/String;)Z
      // 2c: pop
      // 2d: aload 0
      // 2e: monitorexit
      // 2f: bipush 0
      // 30: ireturn
      // 31: aload 0
      // 32: aload 0
      // 33: getfield okhttp3/internal/ws/RealWebSocket.queueSize J
      // 36: aload 1
      // 37: invokevirtual okio/ByteString.size ()I
      // 3a: i2l
      // 3b: ladd
      // 3c: putfield okhttp3/internal/ws/RealWebSocket.queueSize J
      // 3f: aload 0
      // 40: getfield okhttp3/internal/ws/RealWebSocket.messageAndCloseQueue Ljava/util/ArrayDeque;
      // 43: astore 3
      // 44: new okhttp3/internal/ws/RealWebSocket$Message
      // 47: astore 4
      // 49: aload 4
      // 4b: iload 2
      // 4c: aload 1
      // 4d: invokespecial okhttp3/internal/ws/RealWebSocket$Message.<init> (ILokio/ByteString;)V
      // 50: aload 3
      // 51: aload 4
      // 53: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 56: pop
      // 57: aload 0
      // 58: invokespecial okhttp3/internal/ws/RealWebSocket.runWriter ()V
      // 5b: aload 0
      // 5c: monitorexit
      // 5d: bipush 1
      // 5e: ireturn
      // 5f: aload 0
      // 60: monitorexit
      // 61: bipush 0
      // 62: ireturn
      // 63: astore 1
      // 64: aload 0
      // 65: monitorexit
      // 66: aload 1
      // 67: athrow
   }

   @Throws(java/lang/InterruptedException::class)
   public fun awaitTermination(timeout: Long, timeUnit: TimeUnit) {
      Intrinsics.checkParameterIsNotNull(var3, "timeUnit");
      this.taskQueue.idleLatch().await(var1, var3);
   }

   public override fun cancel() {
      if (this.call == null) {
         Intrinsics.throwNpe();
      }

      this.call.cancel();
   }

   @Throws(java/io/IOException::class)
   internal fun checkUpgradeSuccess(response: Response, exchange: Exchange?) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      if (var1.code() == 101) {
         var var3: java.lang.String = Response.header$default(var1, "Connection", null, 2, null);
         if (StringsKt.equals("Upgrade", var3, true)) {
            var3 = Response.header$default(var1, "Upgrade", null, 2, null);
            if (StringsKt.equals("websocket", var3, true)) {
               val var7: java.lang.String = Response.header$default(var1, "Sec-WebSocket-Accept", null, 2, null);
               val var11: ByteString.Companion = ByteString.Companion;
               val var4: StringBuilder = new StringBuilder();
               var4.append(this.key);
               var4.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
               var3 = var11.encodeUtf8(var4.toString()).sha1().base64();
               if (var3 == var7) {
                  if (var2 == null) {
                     throw (new ProtocolException("Web Socket exchange missing: bad interceptor?")) as java.lang.Throwable;
                  }
               } else {
                  val var9: StringBuilder = new StringBuilder("Expected 'Sec-WebSocket-Accept' header value '");
                  var9.append(var3);
                  var9.append("' but was '");
                  var9.append(var7);
                  var9.append('\'');
                  throw (new ProtocolException(var9.toString())) as java.lang.Throwable;
               }
            } else {
               val var6: StringBuilder = new StringBuilder("Expected 'Upgrade' header value 'websocket' but was '");
               var6.append(var3);
               var6.append('\'');
               throw (new ProtocolException(var6.toString())) as java.lang.Throwable;
            }
         } else {
            val var5: StringBuilder = new StringBuilder("Expected 'Connection' header value 'Upgrade' but was '");
            var5.append(var3);
            var5.append('\'');
            throw (new ProtocolException(var5.toString())) as java.lang.Throwable;
         }
      } else {
         val var8: StringBuilder = new StringBuilder("Expected HTTP 101 response but was '");
         var8.append(var1.code());
         var8.append(' ');
         var8.append(var1.message());
         var8.append('\'');
         throw (new ProtocolException(var8.toString())) as java.lang.Throwable;
      }
   }

   public override fun close(code: Int, reason: String?): Boolean {
      return this.close(var1, var2, 60000L);
   }

   public fun close(code: Int, reason: String?, cancelAfterCloseMillis: Long): Boolean {
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
      // 02: getstatic okhttp3/internal/ws/WebSocketProtocol.INSTANCE Lokhttp3/internal/ws/WebSocketProtocol;
      // 05: iload 1
      // 06: invokevirtual okhttp3/internal/ws/WebSocketProtocol.validateCloseCode (I)V
      // 09: aconst_null
      // 0a: astore 6
      // 0c: aconst_null
      // 0d: checkcast okio/ByteString
      // 10: astore 7
      // 12: aload 2
      // 13: ifnull 6b
      // 16: getstatic okio/ByteString.Companion Lokio/ByteString$Companion;
      // 19: aload 2
      // 1a: invokevirtual okio/ByteString$Companion.encodeUtf8 (Ljava/lang/String;)Lokio/ByteString;
      // 1d: astore 6
      // 1f: aload 6
      // 21: invokevirtual okio/ByteString.size ()I
      // 24: i2l
      // 25: ldc2_w 123
      // 28: lcmp
      // 29: ifgt 32
      // 2c: bipush 1
      // 2d: istore 5
      // 2f: goto 35
      // 32: bipush 0
      // 33: istore 5
      // 35: iload 5
      // 37: ifeq 3d
      // 3a: goto 6b
      // 3d: new java/lang/StringBuilder
      // 40: astore 6
      // 42: aload 6
      // 44: ldc_w "reason.size() > 123: "
      // 47: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 4a: aload 6
      // 4c: aload 2
      // 4d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 50: pop
      // 51: aload 6
      // 53: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 56: astore 2
      // 57: new java/lang/IllegalArgumentException
      // 5a: astore 6
      // 5c: aload 6
      // 5e: aload 2
      // 5f: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 62: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 65: aload 6
      // 67: checkcast java/lang/Throwable
      // 6a: athrow
      // 6b: aload 0
      // 6c: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 6f: ifne a3
      // 72: aload 0
      // 73: getfield okhttp3/internal/ws/RealWebSocket.enqueuedClose Z
      // 76: ifeq 7c
      // 79: goto a3
      // 7c: aload 0
      // 7d: bipush 1
      // 7e: putfield okhttp3/internal/ws/RealWebSocket.enqueuedClose Z
      // 81: aload 0
      // 82: getfield okhttp3/internal/ws/RealWebSocket.messageAndCloseQueue Ljava/util/ArrayDeque;
      // 85: astore 2
      // 86: new okhttp3/internal/ws/RealWebSocket$Close
      // 89: astore 7
      // 8b: aload 7
      // 8d: iload 1
      // 8e: aload 6
      // 90: lload 3
      // 91: invokespecial okhttp3/internal/ws/RealWebSocket$Close.<init> (ILokio/ByteString;J)V
      // 94: aload 2
      // 95: aload 7
      // 97: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 9a: pop
      // 9b: aload 0
      // 9c: invokespecial okhttp3/internal/ws/RealWebSocket.runWriter ()V
      // 9f: aload 0
      // a0: monitorexit
      // a1: bipush 1
      // a2: ireturn
      // a3: aload 0
      // a4: monitorexit
      // a5: bipush 0
      // a6: ireturn
      // a7: astore 2
      // a8: aload 0
      // a9: monitorexit
      // aa: aload 2
      // ab: athrow
   }

   public fun connect(client: OkHttpClient) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      if (this.originalRequest.header("Sec-WebSocket-Extensions") != null) {
         this.failWebSocket(new ProtocolException("Request header not permitted: 'Sec-WebSocket-Extensions'"), null);
      } else {
         val var2: OkHttpClient = var1.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
         val var3: Request = this.originalRequest
            .newBuilder()
            .header("Upgrade", "websocket")
            .header("Connection", "Upgrade")
            .header("Sec-WebSocket-Key", this.key)
            .header("Sec-WebSocket-Version", "13")
            .header("Sec-WebSocket-Extensions", "permessage-deflate")
            .build();
         val var4: Call = new RealCall(var2, var3, true);
         this.call = var4;
         var4.enqueue(new Callback(this, var3) {
            final Request $request;
            final RealWebSocket this$0;

            {
               this.this$0 = var1;
               this.$request = var2;
            }

            @Override
            public void onFailure(Call var1, IOException var2) {
               Intrinsics.checkParameterIsNotNull(var1, "call");
               Intrinsics.checkParameterIsNotNull(var2, "e");
               this.this$0.failWebSocket(var2, null);
            }

            // $VF: Duplicated exception handlers to handle obfuscated exceptions
            @Override
            public void onResponse(Call var1, Response var2) {
               Intrinsics.checkParameterIsNotNull(var1, "call");
               Intrinsics.checkParameterIsNotNull(var2, "response");
               val var3: Exchange = var2.exchange();

               label50: {
                  label49: {
                     label54: {
                        try {
                           this.this$0.checkUpgradeSuccess$okhttp(var2, var3);
                        } catch (var8: IOException) {
                           var9 = var8;
                           if (var3 == null) {
                              break label49;
                           }
                           break label54;
                        }

                        if (var3 == null) {
                           try {
                              Intrinsics.throwNpe();
                           } catch (var7: IOException) {
                              var9 = var7;
                              if (var3 == null) {
                                 break label49;
                              }
                              break label54;
                           }
                        }

                        try {
                           var10 = var3.newWebSocketStreams();
                           break label50;
                        } catch (var6: IOException) {
                           var9 = var6;
                           if (var3 == null) {
                              break label49;
                           }
                        }
                     }

                     var3.webSocketUpgradeFailed();
                  }

                  this.this$0.failWebSocket(var9, var2);
                  Util.closeQuietly(var2);
                  return;
               }

               val var11: WebSocketExtensions = WebSocketExtensions.Companion.parse(var2.headers());
               RealWebSocket.access$setExtensions$p(this.this$0, var11);
               if (!RealWebSocket.access$isValid(this.this$0, var11)) {
                  synchronized (this.this$0) {
                     RealWebSocket.access$getMessageAndCloseQueue$p(this.this$0).clear();
                     this.this$0.close(1010, "unexpected Sec-WebSocket-Extensions in response header");
                  }
               }

               try {
                  val var13: StringBuilder = new StringBuilder();
                  var13.append(Util.okHttpName);
                  var13.append(" WebSocket ");
                  var13.append(this.$request.url().redact());
                  this.this$0.initReaderAndWriter(var13.toString(), var10);
                  this.this$0.getListener$okhttp().onOpen(this.this$0, var2);
                  this.this$0.loopReader();
               } catch (var4: Exception) {
                  this.this$0.failWebSocket(var4, null);
               }
            }
         });
      }
   }

   public fun failWebSocket(e: Exception, response: Response?) {
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
      // 01: ldc_w "e"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 0d: istore 3
      // 0e: iload 3
      // 0f: ifeq 15
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: aload 0
      // 16: bipush 1
      // 17: putfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 1a: aload 0
      // 1b: getfield okhttp3/internal/ws/RealWebSocket.streams Lokhttp3/internal/ws/RealWebSocket$Streams;
      // 1e: astore 4
      // 20: aconst_null
      // 21: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 24: astore 5
      // 26: aload 0
      // 27: aconst_null
      // 28: putfield okhttp3/internal/ws/RealWebSocket.streams Lokhttp3/internal/ws/RealWebSocket$Streams;
      // 2b: aload 0
      // 2c: getfield okhttp3/internal/ws/RealWebSocket.reader Lokhttp3/internal/ws/WebSocketReader;
      // 2f: astore 5
      // 31: aconst_null
      // 32: checkcast okhttp3/internal/ws/WebSocketReader
      // 35: astore 6
      // 37: aload 0
      // 38: aconst_null
      // 39: putfield okhttp3/internal/ws/RealWebSocket.reader Lokhttp3/internal/ws/WebSocketReader;
      // 3c: aload 0
      // 3d: getfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 40: astore 6
      // 42: aconst_null
      // 43: checkcast okhttp3/internal/ws/WebSocketWriter
      // 46: astore 7
      // 48: aload 0
      // 49: aconst_null
      // 4a: putfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 4d: aload 0
      // 4e: getfield okhttp3/internal/ws/RealWebSocket.taskQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 51: invokevirtual okhttp3/internal/concurrent/TaskQueue.shutdown ()V
      // 54: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 57: astore 7
      // 59: aload 0
      // 5a: monitorexit
      // 5b: aload 0
      // 5c: getfield okhttp3/internal/ws/RealWebSocket.listener Lokhttp3/WebSocketListener;
      // 5f: aload 0
      // 60: checkcast okhttp3/WebSocket
      // 63: aload 1
      // 64: checkcast java/lang/Throwable
      // 67: aload 2
      // 68: invokevirtual okhttp3/WebSocketListener.onFailure (Lokhttp3/WebSocket;Ljava/lang/Throwable;Lokhttp3/Response;)V
      // 6b: aload 4
      // 6d: ifnull 78
      // 70: aload 4
      // 72: checkcast java/io/Closeable
      // 75: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 78: aload 5
      // 7a: ifnull 85
      // 7d: aload 5
      // 7f: checkcast java/io/Closeable
      // 82: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 85: aload 6
      // 87: ifnull 92
      // 8a: aload 6
      // 8c: checkcast java/io/Closeable
      // 8f: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 92: return
      // 93: astore 1
      // 94: aload 4
      // 96: ifnull a1
      // 99: aload 4
      // 9b: checkcast java/io/Closeable
      // 9e: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // a1: aload 5
      // a3: ifnull ae
      // a6: aload 5
      // a8: checkcast java/io/Closeable
      // ab: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // ae: aload 6
      // b0: ifnull bb
      // b3: aload 6
      // b5: checkcast java/io/Closeable
      // b8: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // bb: aload 1
      // bc: athrow
      // bd: astore 1
      // be: aload 0
      // bf: monitorexit
      // c0: aload 1
      // c1: athrow
   }

   @Throws(java/io/IOException::class)
   public fun initReaderAndWriter(name: String, streams: okhttp3.internal.ws.RealWebSocket.Streams) {
      label29: {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "streams");
         val var5: WebSocketExtensions = this.extensions;
         if (this.extensions == null) {
            Intrinsics.throwNpe();
         }

         synchronized (this){} // $VF: monitorenter 

         try {
            this.name = var1;
            this.streams = var2;
            this.writer = new WebSocketWriter(
               var2.getClient(), var2.getSink(), this.random, var5.perMessageDeflate, var5.noContextTakeover(var2.getClient()), this.minimumDeflateSize
            );
            this.writerTask = new RealWebSocket.WriterTask(this);
            if (this.pingIntervalMillis != 0L) {
               val var3: Long = TimeUnit.MILLISECONDS.toNanos(this.pingIntervalMillis);
               val var13: TaskQueue = this.taskQueue;
               val var7: StringBuilder = new StringBuilder();
               var7.append(var1);
               var7.append(" ping");
               val var14: java.lang.String = var7.toString();
               var13.schedule(new Task(var14, var14, var3, this, var1, var2, var5) {
                  final WebSocketExtensions $extensions$inlined;
                  final java.lang.String $name;
                  final java.lang.String $name$inlined;
                  final long $pingIntervalNanos$inlined;
                  final RealWebSocket.Streams $streams$inlined;
                  final RealWebSocket this$0;

                  {
                     super(var2, false, 2, null);
                     this.$name = var1;
                     this.$pingIntervalNanos$inlined = var3;
                     this.this$0 = var5;
                     this.$name$inlined = var6;
                     this.$streams$inlined = var7;
                     this.$extensions$inlined = var8;
                  }

                  @Override
                  public long runOnce() {
                     this.this$0.writePingFrame$okhttp();
                     return this.$pingIntervalNanos$inlined;
                  }
               }, var3);
            }

            if (!this.messageAndCloseQueue.isEmpty()) {
               this.runWriter();
            }
         } catch (var9: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   @Throws(java/io/IOException::class)
   public fun loopReader() {
      while (this.receivedCloseCode == -1) {
         if (this.reader == null) {
            Intrinsics.throwNpe();
         }

         this.reader.processNextFrame();
      }
   }

   public override fun onReadClose(code: Int, reason: String) {
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
      // 000: aload 2
      // 001: ldc_w "reason"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: bipush 1
      // 008: istore 4
      // 00a: iload 1
      // 00b: bipush -1
      // 00c: if_icmpeq 014
      // 00f: bipush 1
      // 010: istore 3
      // 011: goto 016
      // 014: bipush 0
      // 015: istore 3
      // 016: iload 3
      // 017: ifeq 133
      // 01a: aconst_null
      // 01b: astore 6
      // 01d: aconst_null
      // 01e: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 021: astore 5
      // 023: aconst_null
      // 024: checkcast okhttp3/internal/ws/WebSocketReader
      // 027: astore 5
      // 029: aconst_null
      // 02a: checkcast okhttp3/internal/ws/WebSocketWriter
      // 02d: astore 5
      // 02f: aload 0
      // 030: monitorenter
      // 031: aload 0
      // 032: getfield okhttp3/internal/ws/RealWebSocket.receivedCloseCode I
      // 035: bipush -1
      // 036: if_icmpne 03f
      // 039: iload 4
      // 03b: istore 3
      // 03c: goto 041
      // 03f: bipush 0
      // 040: istore 3
      // 041: iload 3
      // 042: ifeq 11b
      // 045: aload 0
      // 046: iload 1
      // 047: putfield okhttp3/internal/ws/RealWebSocket.receivedCloseCode I
      // 04a: aload 0
      // 04b: aload 2
      // 04c: putfield okhttp3/internal/ws/RealWebSocket.receivedCloseReason Ljava/lang/String;
      // 04f: aload 0
      // 050: getfield okhttp3/internal/ws/RealWebSocket.enqueuedClose Z
      // 053: ifeq 09d
      // 056: aload 0
      // 057: getfield okhttp3/internal/ws/RealWebSocket.messageAndCloseQueue Ljava/util/ArrayDeque;
      // 05a: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 05d: ifeq 09d
      // 060: aload 0
      // 061: getfield okhttp3/internal/ws/RealWebSocket.streams Lokhttp3/internal/ws/RealWebSocket$Streams;
      // 064: astore 6
      // 066: aconst_null
      // 067: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 06a: astore 5
      // 06c: aload 0
      // 06d: aconst_null
      // 06e: putfield okhttp3/internal/ws/RealWebSocket.streams Lokhttp3/internal/ws/RealWebSocket$Streams;
      // 071: aload 0
      // 072: getfield okhttp3/internal/ws/RealWebSocket.reader Lokhttp3/internal/ws/WebSocketReader;
      // 075: astore 5
      // 077: aconst_null
      // 078: checkcast okhttp3/internal/ws/WebSocketReader
      // 07b: astore 7
      // 07d: aload 0
      // 07e: aconst_null
      // 07f: putfield okhttp3/internal/ws/RealWebSocket.reader Lokhttp3/internal/ws/WebSocketReader;
      // 082: aload 0
      // 083: getfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 086: astore 7
      // 088: aconst_null
      // 089: checkcast okhttp3/internal/ws/WebSocketWriter
      // 08c: astore 8
      // 08e: aload 0
      // 08f: aconst_null
      // 090: putfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 093: aload 0
      // 094: getfield okhttp3/internal/ws/RealWebSocket.taskQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 097: invokevirtual okhttp3/internal/concurrent/TaskQueue.shutdown ()V
      // 09a: goto 0a3
      // 09d: aconst_null
      // 09e: astore 5
      // 0a0: aconst_null
      // 0a1: astore 7
      // 0a3: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0a6: astore 8
      // 0a8: aload 0
      // 0a9: monitorexit
      // 0aa: aload 0
      // 0ab: getfield okhttp3/internal/ws/RealWebSocket.listener Lokhttp3/WebSocketListener;
      // 0ae: aload 0
      // 0af: checkcast okhttp3/WebSocket
      // 0b2: iload 1
      // 0b3: aload 2
      // 0b4: invokevirtual okhttp3/WebSocketListener.onClosing (Lokhttp3/WebSocket;ILjava/lang/String;)V
      // 0b7: aload 6
      // 0b9: ifnull 0c9
      // 0bc: aload 0
      // 0bd: getfield okhttp3/internal/ws/RealWebSocket.listener Lokhttp3/WebSocketListener;
      // 0c0: aload 0
      // 0c1: checkcast okhttp3/WebSocket
      // 0c4: iload 1
      // 0c5: aload 2
      // 0c6: invokevirtual okhttp3/WebSocketListener.onClosed (Lokhttp3/WebSocket;ILjava/lang/String;)V
      // 0c9: aload 6
      // 0cb: ifnull 0d6
      // 0ce: aload 6
      // 0d0: checkcast java/io/Closeable
      // 0d3: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 0d6: aload 5
      // 0d8: ifnull 0e3
      // 0db: aload 5
      // 0dd: checkcast java/io/Closeable
      // 0e0: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 0e3: aload 7
      // 0e5: ifnull 0f0
      // 0e8: aload 7
      // 0ea: checkcast java/io/Closeable
      // 0ed: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 0f0: return
      // 0f1: astore 2
      // 0f2: aload 6
      // 0f4: ifnull 0ff
      // 0f7: aload 6
      // 0f9: checkcast java/io/Closeable
      // 0fc: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 0ff: aload 5
      // 101: ifnull 10c
      // 104: aload 5
      // 106: checkcast java/io/Closeable
      // 109: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 10c: aload 7
      // 10e: ifnull 119
      // 111: aload 7
      // 113: checkcast java/io/Closeable
      // 116: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 119: aload 2
      // 11a: athrow
      // 11b: new java/lang/IllegalStateException
      // 11e: astore 2
      // 11f: aload 2
      // 120: ldc_w "already closed"
      // 123: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 126: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 129: aload 2
      // 12a: checkcast java/lang/Throwable
      // 12d: athrow
      // 12e: astore 2
      // 12f: aload 0
      // 130: monitorexit
      // 131: aload 2
      // 132: athrow
      // 133: new java/lang/IllegalArgumentException
      // 136: dup
      // 137: ldc_w "Failed requirement."
      // 13a: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 13d: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 140: checkcast java/lang/Throwable
      // 143: athrow
   }

   @Throws(java/io/IOException::class)
   public override fun onReadMessage(text: String) {
      Intrinsics.checkParameterIsNotNull(var1, "text");
      this.listener.onMessage(this, var1);
   }

   @Throws(java/io/IOException::class)
   public override fun onReadMessage(bytes: ByteString) {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      this.listener.onMessage(this, var1);
   }

   public override fun onReadPing(payload: ByteString) {
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
      // 03: ldc_w "payload"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 0d: ifne 3e
      // 10: aload 0
      // 11: getfield okhttp3/internal/ws/RealWebSocket.enqueuedClose Z
      // 14: ifeq 24
      // 17: aload 0
      // 18: getfield okhttp3/internal/ws/RealWebSocket.messageAndCloseQueue Ljava/util/ArrayDeque;
      // 1b: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 1e: ifeq 24
      // 21: goto 3e
      // 24: aload 0
      // 25: getfield okhttp3/internal/ws/RealWebSocket.pongQueue Ljava/util/ArrayDeque;
      // 28: aload 1
      // 29: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 2c: pop
      // 2d: aload 0
      // 2e: invokespecial okhttp3/internal/ws/RealWebSocket.runWriter ()V
      // 31: aload 0
      // 32: aload 0
      // 33: getfield okhttp3/internal/ws/RealWebSocket.receivedPingCount I
      // 36: bipush 1
      // 37: iadd
      // 38: putfield okhttp3/internal/ws/RealWebSocket.receivedPingCount I
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: return
      // 3e: aload 0
      // 3f: monitorexit
      // 40: return
      // 41: astore 1
      // 42: aload 0
      // 43: monitorexit
      // 44: aload 1
      // 45: athrow
   }

   public override fun onReadPong(payload: ByteString) {
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
      // 03: ldc_w "payload"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: aload 0
      // 0b: getfield okhttp3/internal/ws/RealWebSocket.receivedPongCount I
      // 0e: bipush 1
      // 0f: iadd
      // 10: putfield okhttp3/internal/ws/RealWebSocket.receivedPongCount I
      // 13: aload 0
      // 14: bipush 0
      // 15: putfield okhttp3/internal/ws/RealWebSocket.awaitingPong Z
      // 18: aload 0
      // 19: monitorexit
      // 1a: return
      // 1b: astore 1
      // 1c: aload 0
      // 1d: monitorexit
      // 1e: aload 1
      // 1f: athrow
   }

   public fun pong(payload: ByteString): Boolean {
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
      // 03: ldc_w "payload"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 0d: ifne 35
      // 10: aload 0
      // 11: getfield okhttp3/internal/ws/RealWebSocket.enqueuedClose Z
      // 14: ifeq 24
      // 17: aload 0
      // 18: getfield okhttp3/internal/ws/RealWebSocket.messageAndCloseQueue Ljava/util/ArrayDeque;
      // 1b: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 1e: ifeq 24
      // 21: goto 35
      // 24: aload 0
      // 25: getfield okhttp3/internal/ws/RealWebSocket.pongQueue Ljava/util/ArrayDeque;
      // 28: aload 1
      // 29: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 2c: pop
      // 2d: aload 0
      // 2e: invokespecial okhttp3/internal/ws/RealWebSocket.runWriter ()V
      // 31: aload 0
      // 32: monitorexit
      // 33: bipush 1
      // 34: ireturn
      // 35: aload 0
      // 36: monitorexit
      // 37: bipush 0
      // 38: ireturn
      // 39: astore 1
      // 3a: aload 0
      // 3b: monitorexit
      // 3c: aload 1
      // 3d: athrow
   }

   @Throws(java/io/IOException::class)
   public fun processNextFrame(): Boolean {
      var var2: Boolean = false;

      var var3: WebSocketReader;
      try {
         var3 = this.reader;
      } catch (var6: Exception) {
         this.failWebSocket(var6, null);
         return false;
      }

      if (var3 == null) {
         try {
            Intrinsics.throwNpe();
         } catch (var5: Exception) {
            this.failWebSocket(var5, null);
            return false;
         }
      }

      var var1: Int;
      try {
         var3.processNextFrame();
         var1 = this.receivedCloseCode;
      } catch (var4: Exception) {
         this.failWebSocket(var4, null);
         return false;
      }

      if (var1 == -1) {
         var2 = true;
      }

      return var2;
   }

   public override fun queueSize(): Long {
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
      // 3: getfield okhttp3/internal/ws/RealWebSocket.queueSize J
      // 6: lstore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: lload 1
      // a: lreturn
      // b: astore 3
      // c: aload 0
      // d: monitorexit
      // e: aload 3
      // f: athrow
   }

   public fun receivedPingCount(): Int {
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
      // 3: getfield okhttp3/internal/ws/RealWebSocket.receivedPingCount I
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   public fun receivedPongCount(): Int {
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
      // 3: getfield okhttp3/internal/ws/RealWebSocket.receivedPongCount I
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   public override fun request(): Request {
      return this.originalRequest;
   }

   public override fun send(text: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "text");
      return this.send(ByteString.Companion.encodeUtf8(var1), 1);
   }

   public override fun send(bytes: ByteString): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      return this.send(var1, 2);
   }

   public fun sentPingCount(): Int {
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
      // 3: getfield okhttp3/internal/ws/RealWebSocket.sentPingCount I
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   @Throws(java/lang/InterruptedException::class)
   public fun tearDown() {
      this.taskQueue.shutdown();
      this.taskQueue.idleLatch().await(10L, TimeUnit.SECONDS);
   }

   @Throws(java/io/IOException::class)
   internal fun writeOneFrame(): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 000: new kotlin/jvm/internal/Ref$ObjectRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 007: astore 11
      // 009: aload 11
      // 00b: aconst_null
      // 00c: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 00f: new kotlin/jvm/internal/Ref$IntRef
      // 012: dup
      // 013: invokespecial kotlin/jvm/internal/Ref$IntRef.<init> ()V
      // 016: astore 5
      // 018: aload 5
      // 01a: bipush -1
      // 01b: putfield kotlin/jvm/internal/Ref$IntRef.element I
      // 01e: new kotlin/jvm/internal/Ref$ObjectRef
      // 021: dup
      // 022: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 025: astore 9
      // 027: aconst_null
      // 028: checkcast java/lang/String
      // 02b: astore 6
      // 02d: aload 9
      // 02f: aconst_null
      // 030: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 033: new kotlin/jvm/internal/Ref$ObjectRef
      // 036: dup
      // 037: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 03a: astore 8
      // 03c: aconst_null
      // 03d: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 040: astore 6
      // 042: aload 8
      // 044: aconst_null
      // 045: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 048: new kotlin/jvm/internal/Ref$ObjectRef
      // 04b: dup
      // 04c: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 04f: astore 7
      // 051: aconst_null
      // 052: checkcast okhttp3/internal/ws/WebSocketReader
      // 055: astore 6
      // 057: aload 7
      // 059: aconst_null
      // 05a: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 05d: new kotlin/jvm/internal/Ref$ObjectRef
      // 060: dup
      // 061: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 064: astore 6
      // 066: aconst_null
      // 067: checkcast okhttp3/internal/ws/WebSocketWriter
      // 06a: astore 10
      // 06c: aload 6
      // 06e: aconst_null
      // 06f: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 072: aload 0
      // 073: monitorenter
      // 074: aload 0
      // 075: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 078: istore 4
      // 07a: iload 4
      // 07c: ifeq 083
      // 07f: aload 0
      // 080: monitorexit
      // 081: bipush 0
      // 082: ireturn
      // 083: aload 0
      // 084: getfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 087: astore 10
      // 089: aload 0
      // 08a: getfield okhttp3/internal/ws/RealWebSocket.pongQueue Ljava/util/ArrayDeque;
      // 08d: invokevirtual java/util/ArrayDeque.poll ()Ljava/lang/Object;
      // 090: checkcast okio/ByteString
      // 093: astore 12
      // 095: aload 12
      // 097: ifnonnull 1a8
      // 09a: aload 11
      // 09c: aload 0
      // 09d: getfield okhttp3/internal/ws/RealWebSocket.messageAndCloseQueue Ljava/util/ArrayDeque;
      // 0a0: invokevirtual java/util/ArrayDeque.poll ()Ljava/lang/Object;
      // 0a3: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0a6: aload 11
      // 0a8: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0ab: instanceof okhttp3/internal/ws/RealWebSocket$Close
      // 0ae: ifeq 198
      // 0b1: aload 5
      // 0b3: aload 0
      // 0b4: getfield okhttp3/internal/ws/RealWebSocket.receivedCloseCode I
      // 0b7: putfield kotlin/jvm/internal/Ref$IntRef.element I
      // 0ba: aload 9
      // 0bc: aload 0
      // 0bd: getfield okhttp3/internal/ws/RealWebSocket.receivedCloseReason Ljava/lang/String;
      // 0c0: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0c3: aload 5
      // 0c5: getfield kotlin/jvm/internal/Ref$IntRef.element I
      // 0c8: bipush -1
      // 0c9: if_icmpeq 112
      // 0cc: aload 8
      // 0ce: aload 0
      // 0cf: getfield okhttp3/internal/ws/RealWebSocket.streams Lokhttp3/internal/ws/RealWebSocket$Streams;
      // 0d2: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0d5: aconst_null
      // 0d6: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 0d9: astore 13
      // 0db: aload 0
      // 0dc: aconst_null
      // 0dd: putfield okhttp3/internal/ws/RealWebSocket.streams Lokhttp3/internal/ws/RealWebSocket$Streams;
      // 0e0: aload 7
      // 0e2: aload 0
      // 0e3: getfield okhttp3/internal/ws/RealWebSocket.reader Lokhttp3/internal/ws/WebSocketReader;
      // 0e6: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0e9: aconst_null
      // 0ea: checkcast okhttp3/internal/ws/WebSocketReader
      // 0ed: astore 13
      // 0ef: aload 0
      // 0f0: aconst_null
      // 0f1: putfield okhttp3/internal/ws/RealWebSocket.reader Lokhttp3/internal/ws/WebSocketReader;
      // 0f4: aload 6
      // 0f6: aload 0
      // 0f7: getfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 0fa: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0fd: aconst_null
      // 0fe: checkcast okhttp3/internal/ws/WebSocketWriter
      // 101: astore 13
      // 103: aload 0
      // 104: aconst_null
      // 105: putfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 108: aload 0
      // 109: getfield okhttp3/internal/ws/RealWebSocket.taskQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 10c: invokevirtual okhttp3/internal/concurrent/TaskQueue.shutdown ()V
      // 10f: goto 1a8
      // 112: aload 11
      // 114: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 117: astore 13
      // 119: aload 13
      // 11b: ifnull 188
      // 11e: aload 13
      // 120: checkcast okhttp3/internal/ws/RealWebSocket$Close
      // 123: invokevirtual okhttp3/internal/ws/RealWebSocket$Close.getCancelAfterCloseMillis ()J
      // 126: lstore 2
      // 127: aload 0
      // 128: getfield okhttp3/internal/ws/RealWebSocket.taskQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 12b: astore 13
      // 12d: new java/lang/StringBuilder
      // 130: astore 14
      // 132: aload 14
      // 134: invokespecial java/lang/StringBuilder.<init> ()V
      // 137: aload 14
      // 139: aload 0
      // 13a: getfield okhttp3/internal/ws/RealWebSocket.name Ljava/lang/String;
      // 13d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 140: pop
      // 141: aload 14
      // 143: ldc_w " cancel"
      // 146: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 149: pop
      // 14a: aload 14
      // 14c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 14f: astore 14
      // 151: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 154: lload 2
      // 155: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
      // 158: lstore 2
      // 159: new okhttp3/internal/ws/RealWebSocket$writeOneFrame$$inlined$synchronized$lambda$1
      // 15c: astore 15
      // 15e: aload 15
      // 160: aload 14
      // 162: bipush 1
      // 163: aload 14
      // 165: bipush 1
      // 166: aload 0
      // 167: aload 10
      // 169: aload 12
      // 16b: aload 11
      // 16d: aload 5
      // 16f: aload 9
      // 171: aload 8
      // 173: aload 7
      // 175: aload 6
      // 177: invokespecial okhttp3/internal/ws/RealWebSocket$writeOneFrame$$inlined$synchronized$lambda$1.<init> (Ljava/lang/String;ZLjava/lang/String;ZLokhttp3/internal/ws/RealWebSocket;Lokhttp3/internal/ws/WebSocketWriter;Lokio/ByteString;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$IntRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 17a: aload 13
      // 17c: aload 15
      // 17e: checkcast okhttp3/internal/concurrent/Task
      // 181: lload 2
      // 182: invokevirtual okhttp3/internal/concurrent/TaskQueue.schedule (Lokhttp3/internal/concurrent/Task;J)V
      // 185: goto 1a8
      // 188: new kotlin/TypeCastException
      // 18b: astore 5
      // 18d: aload 5
      // 18f: ldc_w "null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close"
      // 192: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 195: aload 5
      // 197: athrow
      // 198: aload 11
      // 19a: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 19d: astore 13
      // 19f: aload 13
      // 1a1: ifnonnull 1a8
      // 1a4: aload 0
      // 1a5: monitorexit
      // 1a6: bipush 0
      // 1a7: ireturn
      // 1a8: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1ab: astore 13
      // 1ad: aload 0
      // 1ae: monitorexit
      // 1af: aload 12
      // 1b1: ifnull 1c6
      // 1b4: aload 10
      // 1b6: ifnonnull 1bc
      // 1b9: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 1bc: aload 10
      // 1be: aload 12
      // 1c0: invokevirtual okhttp3/internal/ws/WebSocketWriter.writePong (Lokio/ByteString;)V
      // 1c3: goto 29e
      // 1c6: aload 11
      // 1c8: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1cb: instanceof okhttp3/internal/ws/RealWebSocket$Message
      // 1ce: ifeq 230
      // 1d1: aload 11
      // 1d3: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 1d6: astore 5
      // 1d8: aload 5
      // 1da: ifnull 220
      // 1dd: aload 5
      // 1df: checkcast okhttp3/internal/ws/RealWebSocket$Message
      // 1e2: astore 5
      // 1e4: aload 10
      // 1e6: ifnonnull 1ec
      // 1e9: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 1ec: aload 10
      // 1ee: aload 5
      // 1f0: invokevirtual okhttp3/internal/ws/RealWebSocket$Message.getFormatOpcode ()I
      // 1f3: aload 5
      // 1f5: invokevirtual okhttp3/internal/ws/RealWebSocket$Message.getData ()Lokio/ByteString;
      // 1f8: invokevirtual okhttp3/internal/ws/WebSocketWriter.writeMessageFrame (ILokio/ByteString;)V
      // 1fb: aload 0
      // 1fc: monitorenter
      // 1fd: aload 0
      // 1fe: aload 0
      // 1ff: getfield okhttp3/internal/ws/RealWebSocket.queueSize J
      // 202: aload 5
      // 204: invokevirtual okhttp3/internal/ws/RealWebSocket$Message.getData ()Lokio/ByteString;
      // 207: invokevirtual okio/ByteString.size ()I
      // 20a: i2l
      // 20b: lsub
      // 20c: putfield okhttp3/internal/ws/RealWebSocket.queueSize J
      // 20f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 212: astore 5
      // 214: aload 0
      // 215: monitorexit
      // 216: goto 1c3
      // 219: astore 5
      // 21b: aload 0
      // 21c: monitorexit
      // 21d: aload 5
      // 21f: athrow
      // 220: new kotlin/TypeCastException
      // 223: astore 5
      // 225: aload 5
      // 227: ldc_w "null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Message"
      // 22a: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 22d: aload 5
      // 22f: athrow
      // 230: aload 11
      // 232: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 235: instanceof okhttp3/internal/ws/RealWebSocket$Close
      // 238: ifeq 2fa
      // 23b: aload 11
      // 23d: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 240: astore 11
      // 242: aload 11
      // 244: ifnull 2ea
      // 247: aload 11
      // 249: checkcast okhttp3/internal/ws/RealWebSocket$Close
      // 24c: astore 11
      // 24e: aload 10
      // 250: ifnonnull 256
      // 253: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 256: aload 10
      // 258: aload 11
      // 25a: invokevirtual okhttp3/internal/ws/RealWebSocket$Close.getCode ()I
      // 25d: aload 11
      // 25f: invokevirtual okhttp3/internal/ws/RealWebSocket$Close.getReason ()Lokio/ByteString;
      // 262: invokevirtual okhttp3/internal/ws/WebSocketWriter.writeClose (ILokio/ByteString;)V
      // 265: aload 8
      // 267: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 26a: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 26d: ifnull 29e
      // 270: aload 0
      // 271: getfield okhttp3/internal/ws/RealWebSocket.listener Lokhttp3/WebSocketListener;
      // 274: astore 11
      // 276: aload 0
      // 277: checkcast okhttp3/WebSocket
      // 27a: astore 10
      // 27c: aload 5
      // 27e: getfield kotlin/jvm/internal/Ref$IntRef.element I
      // 281: istore 1
      // 282: aload 9
      // 284: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 287: checkcast java/lang/String
      // 28a: astore 5
      // 28c: aload 5
      // 28e: ifnonnull 294
      // 291: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 294: aload 11
      // 296: aload 10
      // 298: iload 1
      // 299: aload 5
      // 29b: invokevirtual okhttp3/WebSocketListener.onClosed (Lokhttp3/WebSocket;ILjava/lang/String;)V
      // 29e: aload 8
      // 2a0: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 2a3: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 2a6: astore 5
      // 2a8: aload 5
      // 2aa: ifnull 2b5
      // 2ad: aload 5
      // 2af: checkcast java/io/Closeable
      // 2b2: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 2b5: aload 7
      // 2b7: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 2ba: checkcast okhttp3/internal/ws/WebSocketReader
      // 2bd: astore 5
      // 2bf: aload 5
      // 2c1: ifnull 2cc
      // 2c4: aload 5
      // 2c6: checkcast java/io/Closeable
      // 2c9: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 2cc: aload 6
      // 2ce: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 2d1: checkcast okhttp3/internal/ws/WebSocketWriter
      // 2d4: astore 5
      // 2d6: aload 5
      // 2d8: ifnull 2e3
      // 2db: aload 5
      // 2dd: checkcast java/io/Closeable
      // 2e0: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 2e3: bipush 1
      // 2e4: ireturn
      // 2e5: astore 5
      // 2e7: goto 311
      // 2ea: new kotlin/TypeCastException
      // 2ed: astore 5
      // 2ef: aload 5
      // 2f1: ldc_w "null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close"
      // 2f4: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 2f7: aload 5
      // 2f9: athrow
      // 2fa: new java/lang/AssertionError
      // 2fd: astore 5
      // 2ff: aload 5
      // 301: invokespecial java/lang/AssertionError.<init> ()V
      // 304: aload 5
      // 306: checkcast java/lang/Throwable
      // 309: athrow
      // 30a: astore 5
      // 30c: goto 311
      // 30f: astore 5
      // 311: aload 8
      // 313: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 316: checkcast okhttp3/internal/ws/RealWebSocket$Streams
      // 319: astore 8
      // 31b: aload 8
      // 31d: ifnull 328
      // 320: aload 8
      // 322: checkcast java/io/Closeable
      // 325: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 328: aload 7
      // 32a: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 32d: checkcast okhttp3/internal/ws/WebSocketReader
      // 330: astore 7
      // 332: aload 7
      // 334: ifnull 33f
      // 337: aload 7
      // 339: checkcast java/io/Closeable
      // 33c: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 33f: aload 6
      // 341: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 344: checkcast okhttp3/internal/ws/WebSocketWriter
      // 347: astore 6
      // 349: aload 6
      // 34b: ifnull 356
      // 34e: aload 6
      // 350: checkcast java/io/Closeable
      // 353: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 356: aload 5
      // 358: athrow
      // 359: astore 5
      // 35b: aload 0
      // 35c: monitorexit
      // 35d: aload 5
      // 35f: athrow
   }

   internal fun writePingFrame() {
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
      // 03: getfield okhttp3/internal/ws/RealWebSocket.failed Z
      // 06: istore 2
      // 07: iload 2
      // 08: ifeq 0e
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: return
      // 0e: aload 0
      // 0f: getfield okhttp3/internal/ws/RealWebSocket.writer Lokhttp3/internal/ws/WebSocketWriter;
      // 12: astore 4
      // 14: aload 4
      // 16: ifnull 9a
      // 19: aload 0
      // 1a: getfield okhttp3/internal/ws/RealWebSocket.awaitingPong Z
      // 1d: ifeq 28
      // 20: aload 0
      // 21: getfield okhttp3/internal/ws/RealWebSocket.sentPingCount I
      // 24: istore 1
      // 25: goto 2a
      // 28: bipush -1
      // 29: istore 1
      // 2a: aload 0
      // 2b: aload 0
      // 2c: getfield okhttp3/internal/ws/RealWebSocket.sentPingCount I
      // 2f: bipush 1
      // 30: iadd
      // 31: putfield okhttp3/internal/ws/RealWebSocket.sentPingCount I
      // 34: aload 0
      // 35: bipush 1
      // 36: putfield okhttp3/internal/ws/RealWebSocket.awaitingPong Z
      // 39: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 3c: astore 3
      // 3d: aload 0
      // 3e: monitorexit
      // 3f: iload 1
      // 40: bipush -1
      // 41: if_icmpeq 84
      // 44: new java/lang/StringBuilder
      // 47: dup
      // 48: ldc_w "sent ping but didn't receive pong within "
      // 4b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 4e: astore 3
      // 4f: aload 3
      // 50: aload 0
      // 51: getfield okhttp3/internal/ws/RealWebSocket.pingIntervalMillis J
      // 54: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 57: pop
      // 58: aload 3
      // 59: ldc_w "ms (after "
      // 5c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5f: pop
      // 60: aload 3
      // 61: iload 1
      // 62: bipush 1
      // 63: isub
      // 64: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 67: pop
      // 68: aload 3
      // 69: ldc_w " successful ping/pongs)"
      // 6c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 6f: pop
      // 70: aload 0
      // 71: new java/net/SocketTimeoutException
      // 74: dup
      // 75: aload 3
      // 76: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 79: invokespecial java/net/SocketTimeoutException.<init> (Ljava/lang/String;)V
      // 7c: checkcast java/lang/Exception
      // 7f: aconst_null
      // 80: invokevirtual okhttp3/internal/ws/RealWebSocket.failWebSocket (Ljava/lang/Exception;Lokhttp3/Response;)V
      // 83: return
      // 84: aload 4
      // 86: getstatic okio/ByteString.EMPTY Lokio/ByteString;
      // 89: invokevirtual okhttp3/internal/ws/WebSocketWriter.writePing (Lokio/ByteString;)V
      // 8c: goto 99
      // 8f: astore 3
      // 90: aload 0
      // 91: aload 3
      // 92: checkcast java/lang/Exception
      // 95: aconst_null
      // 96: invokevirtual okhttp3/internal/ws/RealWebSocket.failWebSocket (Ljava/lang/Exception;Lokhttp3/Response;)V
      // 99: return
      // 9a: aload 0
      // 9b: monitorexit
      // 9c: return
      // 9d: astore 3
      // 9e: aload 0
      // 9f: monitorexit
      // a0: aload 3
      // a1: athrow
   }

   internal class Close(code: Int, reason: ByteString?, cancelAfterCloseMillis: Long) {
      public final val cancelAfterCloseMillis: Long
      public final val code: Int
      public final val reason: ByteString?

      init {
         this.code = var1;
         this.reason = var2;
         this.cancelAfterCloseMillis = var3;
      }
   }

   public companion object {
      private const val CANCEL_AFTER_CLOSE_MILLIS: Long
      public const val DEFAULT_MINIMUM_DEFLATE_SIZE: Long
      private const val MAX_QUEUE_SIZE: Long
      private final val ONLY_HTTP1: List<Protocol>
   }

   internal class Message(formatOpcode: Int, data: ByteString) {
      public final val data: ByteString
      public final val formatOpcode: Int

      init {
         Intrinsics.checkParameterIsNotNull(var2, "data");
         super();
         this.formatOpcode = var1;
         this.data = var2;
      }
   }

   public abstract class Streams : Closeable {
      public final val client: Boolean
      public final val sink: BufferedSink
      public final val source: BufferedSource

      open fun Streams(var1: Boolean, var2: BufferedSource, var3: BufferedSink) {
         Intrinsics.checkParameterIsNotNull(var2, "source");
         Intrinsics.checkParameterIsNotNull(var3, "sink");
         super();
         this.client = var1;
         this.source = var2;
         this.sink = var3;
      }
   }

   private inner class WriterTask : Task {
      init {
         this.this$0 = var1;
         val var2: StringBuilder = new StringBuilder();
         var2.append(RealWebSocket.access$getName$p(var1));
         var2.append(" writer");
         super(var2.toString(), false, 2, null);
      }

      public override fun runOnce(): Long {
         var var1: Boolean;
         try {
            var1 = this.this$0.writeOneFrame$okhttp();
         } catch (var3: IOException) {
            this.this$0.failWebSocket(var3, null);
            return -1L;
         }

         return if (var1) 0L else -1L;
      }
   }
}
