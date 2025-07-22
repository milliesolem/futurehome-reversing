package okhttp3.internal.connection

import java.io.IOException
import java.io.InterruptedIOException
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import kotlin.jvm.internal.Intrinsics
import okhttp3.Address
import okhttp3.Call
import okhttp3.Callback
import okhttp3.CertificatePinner
import okhttp3.Dispatcher
import okhttp3.EventListener
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.Util
import okhttp3.internal.http.RealInterceptorChain
import okhttp3.internal.platform.Platform
import okio.AsyncTimeout

public class RealCall(client: OkHttpClient, originalRequest: Request, forWebSocket: Boolean) : Call {
   private final var callStackTrace: Any?
   private final var canceled: Boolean
   public final val client: OkHttpClient

   public final var connection: RealConnection?
      private set

   private final val connectionPool: RealConnectionPool

   public final var connectionToCancel: RealConnection?
      internal set

   internal final val eventListener: EventListener
   private final var exchange: Exchange?
   private final var exchangeFinder: ExchangeFinder?
   private final val executed: AtomicBoolean
   private final var expectMoreExchanges: Boolean
   public final val forWebSocket: Boolean

   internal final var interceptorScopedExchange: Exchange?
      private set

   public final val originalRequest: Request
   private final var requestBodyOpen: Boolean
   private final var responseBodyOpen: Boolean
   private final val timeout: <unrepresentable>
   private final var timeoutEarlyExit: Boolean

   init {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "originalRequest");
      super();
      this.client = var1;
      this.originalRequest = var2;
      this.forWebSocket = var3;
      this.connectionPool = var1.connectionPool().getDelegate$okhttp();
      this.eventListener = var1.eventListenerFactory().create(this);
      val var4: AsyncTimeout = new AsyncTimeout(this) {
         final RealCall this$0;

         {
            this.this$0 = var1;
         }

         @Override
         protected void timedOut() {
            this.this$0.cancel();
         }
      };
      var4.timeout((long)var1.callTimeoutMillis(), TimeUnit.MILLISECONDS);
      this.timeout = var4;
      this.executed = new AtomicBoolean();
      this.expectMoreExchanges = true;
   }

   private fun <E : IOException?> callDone(e: E): E {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 000: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 003: ifeq 049
      // 006: aload 0
      // 007: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 00a: ifne 010
      // 00d: goto 049
      // 010: new java/lang/StringBuilder
      // 013: dup
      // 014: ldc "Thread "
      // 016: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 019: astore 3
      // 01a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 01d: astore 1
      // 01e: aload 1
      // 01f: ldc "Thread.currentThread()"
      // 021: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 024: aload 3
      // 025: aload 1
      // 026: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 029: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 02c: pop
      // 02d: aload 3
      // 02e: ldc " MUST NOT hold lock on "
      // 030: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 033: pop
      // 034: aload 3
      // 035: aload 0
      // 036: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 039: pop
      // 03a: new java/lang/AssertionError
      // 03d: dup
      // 03e: aload 3
      // 03f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 042: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 045: checkcast java/lang/Throwable
      // 048: athrow
      // 049: aload 0
      // 04a: getfield okhttp3/internal/connection/RealCall.connection Lokhttp3/internal/connection/RealConnection;
      // 04d: astore 3
      // 04e: aload 3
      // 04f: ifnull 0f4
      // 052: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 055: ifeq 09e
      // 058: aload 3
      // 059: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 05c: ifne 062
      // 05f: goto 09e
      // 062: new java/lang/StringBuilder
      // 065: dup
      // 066: ldc "Thread "
      // 068: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 06b: astore 1
      // 06c: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 06f: astore 4
      // 071: aload 4
      // 073: ldc "Thread.currentThread()"
      // 075: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 078: aload 1
      // 079: aload 4
      // 07b: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 07e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 081: pop
      // 082: aload 1
      // 083: ldc " MUST NOT hold lock on "
      // 085: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 088: pop
      // 089: aload 1
      // 08a: aload 3
      // 08b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 08e: pop
      // 08f: new java/lang/AssertionError
      // 092: dup
      // 093: aload 1
      // 094: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 097: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 09a: checkcast java/lang/Throwable
      // 09d: athrow
      // 09e: aload 3
      // 09f: monitorenter
      // 0a0: aload 0
      // 0a1: invokevirtual okhttp3/internal/connection/RealCall.releaseConnectionNoEvents$okhttp ()Ljava/net/Socket;
      // 0a4: astore 4
      // 0a6: aload 3
      // 0a7: monitorexit
      // 0a8: aload 0
      // 0a9: getfield okhttp3/internal/connection/RealCall.connection Lokhttp3/internal/connection/RealConnection;
      // 0ac: ifnonnull 0cb
      // 0af: aload 4
      // 0b1: ifnull 0b9
      // 0b4: aload 4
      // 0b6: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/net/Socket;)V
      // 0b9: aload 0
      // 0ba: getfield okhttp3/internal/connection/RealCall.eventListener Lokhttp3/EventListener;
      // 0bd: aload 0
      // 0be: checkcast okhttp3/Call
      // 0c1: aload 3
      // 0c2: checkcast okhttp3/Connection
      // 0c5: invokevirtual okhttp3/EventListener.connectionReleased (Lokhttp3/Call;Lokhttp3/Connection;)V
      // 0c8: goto 0f4
      // 0cb: aload 4
      // 0cd: ifnonnull 0d5
      // 0d0: bipush 1
      // 0d1: istore 2
      // 0d2: goto 0d7
      // 0d5: bipush 0
      // 0d6: istore 2
      // 0d7: iload 2
      // 0d8: ifeq 0de
      // 0db: goto 0f4
      // 0de: new java/lang/IllegalStateException
      // 0e1: dup
      // 0e2: ldc_w "Check failed."
      // 0e5: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 0e8: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 0eb: checkcast java/lang/Throwable
      // 0ee: athrow
      // 0ef: astore 1
      // 0f0: aload 3
      // 0f1: monitorexit
      // 0f2: aload 1
      // 0f3: athrow
      // 0f4: aload 0
      // 0f5: aload 1
      // 0f6: invokespecial okhttp3/internal/connection/RealCall.timeoutExit (Ljava/io/IOException;)Ljava/io/IOException;
      // 0f9: astore 3
      // 0fa: aload 1
      // 0fb: ifnull 11a
      // 0fe: aload 0
      // 0ff: getfield okhttp3/internal/connection/RealCall.eventListener Lokhttp3/EventListener;
      // 102: astore 1
      // 103: aload 0
      // 104: checkcast okhttp3/Call
      // 107: astore 4
      // 109: aload 3
      // 10a: ifnonnull 110
      // 10d: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 110: aload 1
      // 111: aload 4
      // 113: aload 3
      // 114: invokevirtual okhttp3/EventListener.callFailed (Lokhttp3/Call;Ljava/io/IOException;)V
      // 117: goto 125
      // 11a: aload 0
      // 11b: getfield okhttp3/internal/connection/RealCall.eventListener Lokhttp3/EventListener;
      // 11e: aload 0
      // 11f: checkcast okhttp3/Call
      // 122: invokevirtual okhttp3/EventListener.callEnd (Lokhttp3/Call;)V
      // 125: aload 3
      // 126: areturn
   }

   private fun callStart() {
      this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
      this.eventListener.callStart(this);
   }

   private fun createAddress(url: HttpUrl): Address {
      var var2: SSLSocketFactory = null as SSLSocketFactory;
      val var5: HostnameVerifier = null as HostnameVerifier;
      val var6: CertificatePinner = null as CertificatePinner;
      val var3: HostnameVerifier;
      val var4: SSLSocketFactory;
      if (var1.isHttps()) {
         var4 = this.client.sslSocketFactory();
         var3 = this.client.hostnameVerifier();
         var2 = this.client.certificatePinner();
      } else {
         var4 = null;
         var3 = null;
         var2 = null;
      }

      return new Address(
         var1.host(),
         var1.port(),
         this.client.dns(),
         this.client.socketFactory(),
         var4,
         var3,
         var2,
         this.client.proxyAuthenticator(),
         this.client.proxy(),
         this.client.protocols(),
         this.client.connectionSpecs(),
         this.client.proxySelector()
      );
   }

   private fun <E : IOException?> timeoutExit(cause: E): E {
      if (this.timeoutEarlyExit) {
         return (E)var1;
      } else if (!this.timeout.exit()) {
         return (E)var1;
      } else {
         val var2: InterruptedIOException = new InterruptedIOException("timeout");
         if (var1 != null) {
            var2.initCause(var1);
         }

         return (E)(var2 as IOException);
      }
   }

   private fun toLoggableString(): String {
      val var2: StringBuilder = new StringBuilder();
      var var1: java.lang.String;
      if (this.isCanceled()) {
         var1 = "canceled ";
      } else {
         var1 = "";
      }

      var2.append(var1);
      if (this.forWebSocket) {
         var1 = "web socket";
      } else {
         var1 = "call";
      }

      var2.append(var1);
      var2.append(" to ");
      var2.append(this.redactedUrl$okhttp());
      return var2.toString();
   }

   public fun acquireConnectionNoEvents(connection: RealConnection) {
      Intrinsics.checkParameterIsNotNull(var1, "connection");
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         val var3: StringBuilder = new StringBuilder("Thread ");
         val var4: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var4, "Thread.currentThread()");
         var3.append(var4.getName());
         var3.append(" MUST hold lock on ");
         var3.append(var1);
         throw (new AssertionError(var3.toString())) as java.lang.Throwable;
      } else {
         val var2: Boolean;
         if (this.connection == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            this.connection = var1;
            var1.getCalls().add(new RealCall.CallReference(this, this.callStackTrace));
         } else {
            throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
         }
      }
   }

   public override fun cancel() {
      if (!this.canceled) {
         this.canceled = true;
         if (this.exchange != null) {
            this.exchange.cancel();
         }

         if (this.connectionToCancel != null) {
            this.connectionToCancel.cancel();
         }

         this.eventListener.canceled(this);
      }
   }

   public open fun clone(): RealCall {
      return new RealCall(this.client, this.originalRequest, this.forWebSocket);
   }

   public override fun enqueue(responseCallback: Callback) {
      Intrinsics.checkParameterIsNotNull(var1, "responseCallback");
      if (this.executed.compareAndSet(false, true)) {
         this.callStart();
         this.client.dispatcher().enqueue$okhttp(new RealCall.AsyncCall(this, var1));
      } else {
         throw (new IllegalStateException("Already Executed".toString())) as java.lang.Throwable;
      }
   }

   public fun enterNetworkInterceptorExchange(request: Request, newExchangeFinder: Boolean) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      val var3: Boolean;
      if (this.interceptorScopedExchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         synchronized (this) {
            if (this.responseBodyOpen) {
               throw (
                  new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString())
               ) as java.lang.Throwable;
            }

            if (this.requestBodyOpen) {
               throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
            }
         }

         if (var2) {
            this.exchangeFinder = new ExchangeFinder(this.connectionPool, this.createAddress(var1.url()), this, this.eventListener);
         }
      } else {
         throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
      }
   }

   public override fun execute(): Response {
      label15:
      if (this.executed.compareAndSet(false, true)) {
         this.timeout.enter();
         this.callStart();

         try {
            this.client.dispatcher().executed$okhttp(this);
            val var1: Response = this.getResponseWithInterceptorChain$okhttp();
         } catch (var2: java.lang.Throwable) {
            this.client.dispatcher().finished$okhttp(this);
         }

         this.client.dispatcher().finished$okhttp(this);
      } else {
         throw (new IllegalStateException("Already Executed".toString())) as java.lang.Throwable;
      }
   }

   internal fun exitNetworkInterceptorExchange(closeExchange: Boolean) {
      synchronized (this) {
         if (!this.expectMoreExchanges) {
            throw (new IllegalStateException("released".toString())) as java.lang.Throwable;
         }
      }

      if (var1 && this.exchange != null) {
         this.exchange.detachWithViolence();
      }

      val var5: Exchange = null as Exchange;
      this.interceptorScopedExchange = null;
   }

   @Throws(java/io/IOException::class)
   internal fun getResponseWithInterceptorChain(): Response {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 000: new java/util/ArrayList
      // 003: dup
      // 004: invokespecial java/util/ArrayList.<init> ()V
      // 007: checkcast java/util/List
      // 00a: astore 4
      // 00c: aload 4
      // 00e: checkcast java/util/Collection
      // 011: astore 5
      // 013: aload 5
      // 015: aload 0
      // 016: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 019: invokevirtual okhttp3/OkHttpClient.interceptors ()Ljava/util/List;
      // 01c: checkcast java/lang/Iterable
      // 01f: invokestatic kotlin/collections/CollectionsKt.addAll (Ljava/util/Collection;Ljava/lang/Iterable;)Z
      // 022: pop
      // 023: aload 5
      // 025: new okhttp3/internal/http/RetryAndFollowUpInterceptor
      // 028: dup
      // 029: aload 0
      // 02a: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 02d: invokespecial okhttp3/internal/http/RetryAndFollowUpInterceptor.<init> (Lokhttp3/OkHttpClient;)V
      // 030: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 035: pop
      // 036: aload 5
      // 038: new okhttp3/internal/http/BridgeInterceptor
      // 03b: dup
      // 03c: aload 0
      // 03d: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 040: invokevirtual okhttp3/OkHttpClient.cookieJar ()Lokhttp3/CookieJar;
      // 043: invokespecial okhttp3/internal/http/BridgeInterceptor.<init> (Lokhttp3/CookieJar;)V
      // 046: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 04b: pop
      // 04c: aload 5
      // 04e: new okhttp3/internal/cache/CacheInterceptor
      // 051: dup
      // 052: aload 0
      // 053: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 056: invokevirtual okhttp3/OkHttpClient.cache ()Lokhttp3/Cache;
      // 059: invokespecial okhttp3/internal/cache/CacheInterceptor.<init> (Lokhttp3/Cache;)V
      // 05c: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 061: pop
      // 062: aload 5
      // 064: getstatic okhttp3/internal/connection/ConnectInterceptor.INSTANCE Lokhttp3/internal/connection/ConnectInterceptor;
      // 067: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 06c: pop
      // 06d: aload 0
      // 06e: getfield okhttp3/internal/connection/RealCall.forWebSocket Z
      // 071: ifne 084
      // 074: aload 5
      // 076: aload 0
      // 077: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 07a: invokevirtual okhttp3/OkHttpClient.networkInterceptors ()Ljava/util/List;
      // 07d: checkcast java/lang/Iterable
      // 080: invokestatic kotlin/collections/CollectionsKt.addAll (Ljava/util/Collection;Ljava/lang/Iterable;)Z
      // 083: pop
      // 084: aload 5
      // 086: new okhttp3/internal/http/CallServerInterceptor
      // 089: dup
      // 08a: aload 0
      // 08b: getfield okhttp3/internal/connection/RealCall.forWebSocket Z
      // 08e: invokespecial okhttp3/internal/http/CallServerInterceptor.<init> (Z)V
      // 091: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 096: pop
      // 097: new okhttp3/internal/http/RealInterceptorChain
      // 09a: dup
      // 09b: aload 0
      // 09c: aload 4
      // 09e: bipush 0
      // 09f: aconst_null
      // 0a0: aload 0
      // 0a1: getfield okhttp3/internal/connection/RealCall.originalRequest Lokhttp3/Request;
      // 0a4: aload 0
      // 0a5: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 0a8: invokevirtual okhttp3/OkHttpClient.connectTimeoutMillis ()I
      // 0ab: aload 0
      // 0ac: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 0af: invokevirtual okhttp3/OkHttpClient.readTimeoutMillis ()I
      // 0b2: aload 0
      // 0b3: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 0b6: invokevirtual okhttp3/OkHttpClient.writeTimeoutMillis ()I
      // 0b9: invokespecial okhttp3/internal/http/RealInterceptorChain.<init> (Lokhttp3/internal/connection/RealCall;Ljava/util/List;ILokhttp3/internal/connection/Exchange;Lokhttp3/Request;III)V
      // 0bc: astore 4
      // 0be: bipush 0
      // 0bf: istore 2
      // 0c0: iload 2
      // 0c1: istore 1
      // 0c2: aload 4
      // 0c4: aload 0
      // 0c5: getfield okhttp3/internal/connection/RealCall.originalRequest Lokhttp3/Request;
      // 0c8: invokevirtual okhttp3/internal/http/RealInterceptorChain.proceed (Lokhttp3/Request;)Lokhttp3/Response;
      // 0cb: astore 4
      // 0cd: iload 2
      // 0ce: istore 1
      // 0cf: aload 0
      // 0d0: invokevirtual okhttp3/internal/connection/RealCall.isCanceled ()Z
      // 0d3: istore 3
      // 0d4: iload 3
      // 0d5: ifne 0e1
      // 0d8: aload 0
      // 0d9: aconst_null
      // 0da: invokevirtual okhttp3/internal/connection/RealCall.noMoreExchanges$okhttp (Ljava/io/IOException;)Ljava/io/IOException;
      // 0dd: pop
      // 0de: aload 4
      // 0e0: areturn
      // 0e1: iload 2
      // 0e2: istore 1
      // 0e3: aload 4
      // 0e5: checkcast java/io/Closeable
      // 0e8: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 0eb: iload 2
      // 0ec: istore 1
      // 0ed: new java/io/IOException
      // 0f0: astore 4
      // 0f2: iload 2
      // 0f3: istore 1
      // 0f4: aload 4
      // 0f6: ldc_w "Canceled"
      // 0f9: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 0fc: iload 2
      // 0fd: istore 1
      // 0fe: aload 4
      // 100: checkcast java/lang/Throwable
      // 103: athrow
      // 104: astore 4
      // 106: goto 13a
      // 109: astore 4
      // 10b: bipush 1
      // 10c: istore 2
      // 10d: iload 2
      // 10e: istore 1
      // 10f: aload 0
      // 110: aload 4
      // 112: invokevirtual okhttp3/internal/connection/RealCall.noMoreExchanges$okhttp (Ljava/io/IOException;)Ljava/io/IOException;
      // 115: astore 4
      // 117: aload 4
      // 119: ifnonnull 132
      // 11c: iload 2
      // 11d: istore 1
      // 11e: new kotlin/TypeCastException
      // 121: astore 4
      // 123: iload 2
      // 124: istore 1
      // 125: aload 4
      // 127: ldc_w "null cannot be cast to non-null type kotlin.Throwable"
      // 12a: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 12d: iload 2
      // 12e: istore 1
      // 12f: aload 4
      // 131: athrow
      // 132: iload 2
      // 133: istore 1
      // 134: aload 4
      // 136: checkcast java/lang/Throwable
      // 139: athrow
      // 13a: iload 1
      // 13b: ifne 144
      // 13e: aload 0
      // 13f: aconst_null
      // 140: invokevirtual okhttp3/internal/connection/RealCall.noMoreExchanges$okhttp (Ljava/io/IOException;)Ljava/io/IOException;
      // 143: pop
      // 144: aload 4
      // 146: athrow
   }

   internal fun initExchange(chain: RealInterceptorChain): Exchange {
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
      // 00: aload 1
      // 01: ldc_w "chain"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield okhttp3/internal/connection/RealCall.expectMoreExchanges Z
      // 0d: ifeq a7
      // 10: aload 0
      // 11: getfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 14: ifne 94
      // 17: aload 0
      // 18: getfield okhttp3/internal/connection/RealCall.requestBodyOpen Z
      // 1b: ifne 81
      // 1e: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 21: astore 2
      // 22: aload 0
      // 23: monitorexit
      // 24: aload 0
      // 25: getfield okhttp3/internal/connection/RealCall.exchangeFinder Lokhttp3/internal/connection/ExchangeFinder;
      // 28: astore 2
      // 29: aload 2
      // 2a: ifnonnull 30
      // 2d: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 30: aload 2
      // 31: aload 0
      // 32: getfield okhttp3/internal/connection/RealCall.client Lokhttp3/OkHttpClient;
      // 35: aload 1
      // 36: invokevirtual okhttp3/internal/connection/ExchangeFinder.find (Lokhttp3/OkHttpClient;Lokhttp3/internal/http/RealInterceptorChain;)Lokhttp3/internal/http/ExchangeCodec;
      // 39: astore 1
      // 3a: new okhttp3/internal/connection/Exchange
      // 3d: dup
      // 3e: aload 0
      // 3f: aload 0
      // 40: getfield okhttp3/internal/connection/RealCall.eventListener Lokhttp3/EventListener;
      // 43: aload 2
      // 44: aload 1
      // 45: invokespecial okhttp3/internal/connection/Exchange.<init> (Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;Lokhttp3/internal/connection/ExchangeFinder;Lokhttp3/internal/http/ExchangeCodec;)V
      // 48: astore 1
      // 49: aload 0
      // 4a: aload 1
      // 4b: putfield okhttp3/internal/connection/RealCall.interceptorScopedExchange Lokhttp3/internal/connection/Exchange;
      // 4e: aload 0
      // 4f: aload 1
      // 50: putfield okhttp3/internal/connection/RealCall.exchange Lokhttp3/internal/connection/Exchange;
      // 53: aload 0
      // 54: monitorenter
      // 55: aload 0
      // 56: bipush 1
      // 57: putfield okhttp3/internal/connection/RealCall.requestBodyOpen Z
      // 5a: aload 0
      // 5b: bipush 1
      // 5c: putfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 5f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 62: astore 2
      // 63: aload 0
      // 64: monitorexit
      // 65: aload 0
      // 66: getfield okhttp3/internal/connection/RealCall.canceled Z
      // 69: ifne 6e
      // 6c: aload 1
      // 6d: areturn
      // 6e: new java/io/IOException
      // 71: dup
      // 72: ldc_w "Canceled"
      // 75: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 78: checkcast java/lang/Throwable
      // 7b: athrow
      // 7c: astore 1
      // 7d: aload 0
      // 7e: monitorexit
      // 7f: aload 1
      // 80: athrow
      // 81: new java/lang/IllegalStateException
      // 84: astore 1
      // 85: aload 1
      // 86: ldc_w "Check failed."
      // 89: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 8c: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 8f: aload 1
      // 90: checkcast java/lang/Throwable
      // 93: athrow
      // 94: new java/lang/IllegalStateException
      // 97: astore 1
      // 98: aload 1
      // 99: ldc_w "Check failed."
      // 9c: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 9f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // a2: aload 1
      // a3: checkcast java/lang/Throwable
      // a6: athrow
      // a7: new java/lang/IllegalStateException
      // aa: astore 1
      // ab: aload 1
      // ac: ldc_w "released"
      // af: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // b2: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // b5: aload 1
      // b6: checkcast java/lang/Throwable
      // b9: athrow
      // ba: astore 1
      // bb: aload 0
      // bc: monitorexit
      // bd: aload 1
      // be: athrow
   }

   public override fun isCanceled(): Boolean {
      return this.canceled;
   }

   public override fun isExecuted(): Boolean {
      return this.executed.get();
   }

   internal fun <E : IOException?> messageDone(exchange: Exchange, requestDone: Boolean, responseDone: Boolean, e: E): E {
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
      // 01: ldc_w "exchange"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 1
      // 08: aload 0
      // 09: getfield okhttp3/internal/connection/RealCall.exchange Lokhttp3/internal/connection/Exchange;
      // 0c: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f: ifne 15
      // 12: aload 4
      // 14: areturn
      // 15: aload 0
      // 16: monitorenter
      // 17: bipush 0
      // 18: istore 5
      // 1a: bipush 0
      // 1b: istore 7
      // 1d: iload 2
      // 1e: ifeq 2f
      // 21: aload 0
      // 22: getfield okhttp3/internal/connection/RealCall.requestBodyOpen Z
      // 25: ifne 3a
      // 28: goto 2f
      // 2b: astore 1
      // 2c: goto bd
      // 2f: iload 3
      // 30: ifeq 89
      // 33: aload 0
      // 34: getfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 37: ifeq 89
      // 3a: iload 2
      // 3b: ifeq 43
      // 3e: aload 0
      // 3f: bipush 0
      // 40: putfield okhttp3/internal/connection/RealCall.requestBodyOpen Z
      // 43: iload 3
      // 44: ifeq 4c
      // 47: aload 0
      // 48: bipush 0
      // 49: putfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 4c: aload 0
      // 4d: getfield okhttp3/internal/connection/RealCall.requestBodyOpen Z
      // 50: istore 2
      // 51: iload 2
      // 52: ifne 62
      // 55: aload 0
      // 56: getfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 59: ifne 62
      // 5c: bipush 1
      // 5d: istore 5
      // 5f: goto 65
      // 62: bipush 0
      // 63: istore 5
      // 65: iload 7
      // 67: istore 6
      // 69: iload 2
      // 6a: ifne 86
      // 6d: iload 7
      // 6f: istore 6
      // 71: aload 0
      // 72: getfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 75: ifne 86
      // 78: iload 7
      // 7a: istore 6
      // 7c: aload 0
      // 7d: getfield okhttp3/internal/connection/RealCall.expectMoreExchanges Z
      // 80: ifne 86
      // 83: bipush 1
      // 84: istore 6
      // 86: goto 8c
      // 89: bipush 0
      // 8a: istore 6
      // 8c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 8f: astore 1
      // 90: aload 0
      // 91: monitorexit
      // 92: iload 5
      // 94: ifeq ae
      // 97: aconst_null
      // 98: checkcast okhttp3/internal/connection/Exchange
      // 9b: astore 1
      // 9c: aload 0
      // 9d: aconst_null
      // 9e: putfield okhttp3/internal/connection/RealCall.exchange Lokhttp3/internal/connection/Exchange;
      // a1: aload 0
      // a2: getfield okhttp3/internal/connection/RealCall.connection Lokhttp3/internal/connection/RealConnection;
      // a5: astore 1
      // a6: aload 1
      // a7: ifnull ae
      // aa: aload 1
      // ab: invokevirtual okhttp3/internal/connection/RealConnection.incrementSuccessCount$okhttp ()V
      // ae: iload 6
      // b0: ifeq ba
      // b3: aload 0
      // b4: aload 4
      // b6: invokespecial okhttp3/internal/connection/RealCall.callDone (Ljava/io/IOException;)Ljava/io/IOException;
      // b9: areturn
      // ba: aload 4
      // bc: areturn
      // bd: aload 0
      // be: monitorexit
      // bf: aload 1
      // c0: athrow
   }

   internal fun noMoreExchanges(e: IOException?): IOException? {
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
      // 03: getfield okhttp3/internal/connection/RealCall.expectMoreExchanges Z
      // 06: istore 4
      // 08: bipush 0
      // 09: istore 3
      // 0a: iload 3
      // 0b: istore 2
      // 0c: iload 4
      // 0e: ifeq 2a
      // 11: aload 0
      // 12: bipush 0
      // 13: putfield okhttp3/internal/connection/RealCall.expectMoreExchanges Z
      // 16: iload 3
      // 17: istore 2
      // 18: aload 0
      // 19: getfield okhttp3/internal/connection/RealCall.requestBodyOpen Z
      // 1c: ifne 2a
      // 1f: iload 3
      // 20: istore 2
      // 21: aload 0
      // 22: getfield okhttp3/internal/connection/RealCall.responseBodyOpen Z
      // 25: ifne 2a
      // 28: bipush 1
      // 29: istore 2
      // 2a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 2d: astore 5
      // 2f: aload 0
      // 30: monitorexit
      // 31: aload 1
      // 32: astore 5
      // 34: iload 2
      // 35: ifeq 3f
      // 38: aload 0
      // 39: aload 1
      // 3a: invokespecial okhttp3/internal/connection/RealCall.callDone (Ljava/io/IOException;)Ljava/io/IOException;
      // 3d: astore 5
      // 3f: aload 5
      // 41: areturn
      // 42: astore 1
      // 43: aload 0
      // 44: monitorexit
      // 45: aload 1
      // 46: athrow
   }

   internal fun redactedUrl(): String {
      return this.originalRequest.url().redact();
   }

   internal fun releaseConnectionNoEvents(): Socket? {
      val var3: RealConnection = this.connection;
      if (this.connection == null) {
         Intrinsics.throwNpe();
      }

      if (Util.assertionsEnabled && !Thread.holdsLock(this.connection)) {
         val var10: StringBuilder = new StringBuilder("Thread ");
         val var8: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var8, "Thread.currentThread()");
         var10.append(var8.getName());
         var10.append(" MUST hold lock on ");
         var10.append(var3);
         throw (new AssertionError(var10.toString())) as java.lang.Throwable;
      } else {
         val var4: java.util.List = this.connection.getCalls();
         val var5: java.util.Iterator = var4.iterator();
         var var2: Boolean = false;
         var var1: Int = 0;

         while (true) {
            if (!var5.hasNext()) {
               var1 = -1;
               break;
            }

            val var7: RealCall = (var5.next() as Reference).get() as RealCall;
            val var6: RealCall = this;
            if (var7 == this) {
               break;
            }

            var1++;
         }

         if (var1 != -1) {
            var2 = true;
         }

         if (var2) {
            var4.remove(var1);
            val var9: RealConnection = null as RealConnection;
            this.connection = null;
            if (var4.isEmpty()) {
               var3.setIdleAtNs$okhttp(System.nanoTime());
               if (this.connectionPool.connectionBecameIdle(var3)) {
                  return var3.socket();
               }
            }

            return null;
         } else {
            throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
         }
      }
   }

   public override fun request(): Request {
      return this.originalRequest;
   }

   public fun retryAfterFailure(): Boolean {
      if (this.exchangeFinder == null) {
         Intrinsics.throwNpe();
      }

      return this.exchangeFinder.retryAfterFailure();
   }

   public open fun timeout(): AsyncTimeout {
      return this.timeout;
   }

   public fun timeoutEarlyExit() {
      if (!this.timeoutEarlyExit) {
         this.timeoutEarlyExit = true;
         this.timeout.exit();
      } else {
         throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
      }
   }

   internal inner class AsyncCall(responseCallback: Callback) : Runnable {
      public final val call: RealCall
         public final get() {
            return this.this$0;
         }


      public final var callsPerHost: AtomicInteger
         private set

      public final val host: String
         public final get() {
            return this.this$0.getOriginalRequest().url().host();
         }


      public final val request: Request
         public final get() {
            return this.this$0.getOriginalRequest();
         }


      private final val responseCallback: Callback

      init {
         Intrinsics.checkParameterIsNotNull(var2, "responseCallback");
         this.this$0 = var1;
         super();
         this.responseCallback = var2;
         this.callsPerHost = new AtomicInteger(0);
      }

      public fun executeOn(executorService: ExecutorService) {
         Intrinsics.checkParameterIsNotNull(var1, "executorService");
         val var2: Dispatcher = this.this$0.getClient().dispatcher();
         if (Util.assertionsEnabled && Thread.holdsLock(var2)) {
            val var9: StringBuilder = new StringBuilder("Thread ");
            val var3: Thread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
            var9.append(var3.getName());
            var9.append(" MUST NOT hold lock on ");
            var9.append(var2);
            throw (new AssertionError(var9.toString())) as java.lang.Throwable;
         } else {
            label49: {
               try {
                  try {
                     return;
                  } catch (var4: RejectedExecutionException) {
                     val var10: InterruptedIOException = new InterruptedIOException("executor rejected");
                     var10.initCause(var1 as java.lang.Throwable);
                     this.this$0.noMoreExchanges$okhttp(var10);
                     this.responseCallback.onFailure(this.this$0, var10);
                  }
               } catch (var5: java.lang.Throwable) {
                  this.this$0.getClient().dispatcher().finished$okhttp(this);
               }

               this.this$0.getClient().dispatcher().finished$okhttp(this);
            }
         }
      }

      public fun reuseCallsPerHostFrom(other: okhttp3.internal.connection.RealCall.AsyncCall) {
         Intrinsics.checkParameterIsNotNull(var1, "other");
         this.callsPerHost = var1.callsPerHost;
      }

      public override fun run() {
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
         // 000: new java/lang/StringBuilder
         // 003: dup
         // 004: ldc "OkHttp "
         // 006: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 009: astore 3
         // 00a: aload 3
         // 00b: aload 0
         // 00c: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 00f: invokevirtual okhttp3/internal/connection/RealCall.redactedUrl$okhttp ()Ljava/lang/String;
         // 012: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 015: pop
         // 016: aload 3
         // 017: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 01a: astore 3
         // 01b: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
         // 01e: astore 4
         // 020: aload 4
         // 022: ldc "currentThread"
         // 024: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 027: aload 4
         // 029: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
         // 02c: astore 5
         // 02e: aload 4
         // 030: aload 3
         // 031: invokevirtual java/lang/Thread.setName (Ljava/lang/String;)V
         // 034: aload 0
         // 035: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 038: invokestatic okhttp3/internal/connection/RealCall.access$getTimeout$p (Lokhttp3/internal/connection/RealCall;)Lokhttp3/internal/connection/RealCall$timeout$1;
         // 03b: invokevirtual okhttp3/internal/connection/RealCall$timeout$1.enter ()V
         // 03e: bipush 0
         // 03f: istore 2
         // 040: bipush 0
         // 041: istore 1
         // 042: aload 0
         // 043: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 046: invokevirtual okhttp3/internal/connection/RealCall.getResponseWithInterceptorChain$okhttp ()Lokhttp3/Response;
         // 049: astore 3
         // 04a: aload 0
         // 04b: getfield okhttp3/internal/connection/RealCall$AsyncCall.responseCallback Lokhttp3/Callback;
         // 04e: aload 0
         // 04f: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 052: checkcast okhttp3/Call
         // 055: aload 3
         // 056: invokeinterface okhttp3/Callback.onResponse (Lokhttp3/Call;Lokhttp3/Response;)V 3
         // 05b: aload 0
         // 05c: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 05f: invokevirtual okhttp3/internal/connection/RealCall.getClient ()Lokhttp3/OkHttpClient;
         // 062: invokevirtual okhttp3/OkHttpClient.dispatcher ()Lokhttp3/Dispatcher;
         // 065: astore 3
         // 066: aload 3
         // 067: aload 0
         // 068: invokevirtual okhttp3/Dispatcher.finished$okhttp (Lokhttp3/internal/connection/RealCall$AsyncCall;)V
         // 06b: goto 11f
         // 06e: astore 3
         // 06f: bipush 1
         // 070: istore 1
         // 071: goto 07b
         // 074: astore 3
         // 075: bipush 1
         // 076: istore 1
         // 077: goto 0c9
         // 07a: astore 3
         // 07b: aload 0
         // 07c: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 07f: invokevirtual okhttp3/internal/connection/RealCall.cancel ()V
         // 082: iload 1
         // 083: ifne 0c0
         // 086: new java/io/IOException
         // 089: astore 7
         // 08b: new java/lang/StringBuilder
         // 08e: astore 6
         // 090: aload 6
         // 092: ldc "canceled due to "
         // 094: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 097: aload 6
         // 099: aload 3
         // 09a: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
         // 09d: pop
         // 09e: aload 7
         // 0a0: aload 6
         // 0a2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 0a5: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
         // 0a8: aload 7
         // 0aa: aload 3
         // 0ab: invokevirtual java/io/IOException.addSuppressed (Ljava/lang/Throwable;)V
         // 0ae: aload 0
         // 0af: getfield okhttp3/internal/connection/RealCall$AsyncCall.responseCallback Lokhttp3/Callback;
         // 0b2: aload 0
         // 0b3: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 0b6: checkcast okhttp3/Call
         // 0b9: aload 7
         // 0bb: invokeinterface okhttp3/Callback.onFailure (Lokhttp3/Call;Ljava/io/IOException;)V 3
         // 0c0: aload 3
         // 0c1: athrow
         // 0c2: astore 3
         // 0c3: goto 127
         // 0c6: astore 3
         // 0c7: iload 2
         // 0c8: istore 1
         // 0c9: iload 1
         // 0ca: ifeq 100
         // 0cd: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
         // 0d0: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
         // 0d3: astore 6
         // 0d5: new java/lang/StringBuilder
         // 0d8: astore 7
         // 0da: aload 7
         // 0dc: ldc "Callback failure for "
         // 0de: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 0e1: aload 7
         // 0e3: aload 0
         // 0e4: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 0e7: invokestatic okhttp3/internal/connection/RealCall.access$toLoggableString (Lokhttp3/internal/connection/RealCall;)Ljava/lang/String;
         // 0ea: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 0ed: pop
         // 0ee: aload 6
         // 0f0: aload 7
         // 0f2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 0f5: bipush 4
         // 0f6: aload 3
         // 0f7: checkcast java/lang/Throwable
         // 0fa: invokevirtual okhttp3/internal/platform/Platform.log (Ljava/lang/String;ILjava/lang/Throwable;)V
         // 0fd: goto 111
         // 100: aload 0
         // 101: getfield okhttp3/internal/connection/RealCall$AsyncCall.responseCallback Lokhttp3/Callback;
         // 104: aload 0
         // 105: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 108: checkcast okhttp3/Call
         // 10b: aload 3
         // 10c: invokeinterface okhttp3/Callback.onFailure (Lokhttp3/Call;Ljava/io/IOException;)V 3
         // 111: aload 0
         // 112: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 115: invokevirtual okhttp3/internal/connection/RealCall.getClient ()Lokhttp3/OkHttpClient;
         // 118: invokevirtual okhttp3/OkHttpClient.dispatcher ()Lokhttp3/Dispatcher;
         // 11b: astore 3
         // 11c: goto 066
         // 11f: aload 4
         // 121: aload 5
         // 123: invokevirtual java/lang/Thread.setName (Ljava/lang/String;)V
         // 126: return
         // 127: aload 0
         // 128: getfield okhttp3/internal/connection/RealCall$AsyncCall.this$0 Lokhttp3/internal/connection/RealCall;
         // 12b: invokevirtual okhttp3/internal/connection/RealCall.getClient ()Lokhttp3/OkHttpClient;
         // 12e: invokevirtual okhttp3/OkHttpClient.dispatcher ()Lokhttp3/Dispatcher;
         // 131: aload 0
         // 132: invokevirtual okhttp3/Dispatcher.finished$okhttp (Lokhttp3/internal/connection/RealCall$AsyncCall;)V
         // 135: aload 3
         // 136: athrow
         // 137: astore 3
         // 138: aload 4
         // 13a: aload 5
         // 13c: invokevirtual java/lang/Thread.setName (Ljava/lang/String;)V
         // 13f: aload 3
         // 140: athrow
      }
   }

   internal class CallReference(referent: RealCall, callStackTrace: Any?) : WeakReference<RealCall> {
      public final val callStackTrace: Any?

      init {
         Intrinsics.checkParameterIsNotNull(var1, "referent");
         super(var1);
         this.callStackTrace = var2;
      }
   }
}
