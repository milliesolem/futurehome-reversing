package okhttp3.internal.connection

import java.io.IOException
import java.lang.ref.Reference
import java.net.ConnectException
import java.net.ProtocolException
import java.net.Proxy
import java.net.Socket
import java.net.UnknownServiceException
import java.net.Proxy.Type
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.jvm.internal.Intrinsics
import okhttp3.Address
import okhttp3.Call
import okhttp3.Connection
import okhttp3.ConnectionSpec
import okhttp3.EventListener
import okhttp3.Handshake
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.internal.Util
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.http.ExchangeCodec
import okhttp3.internal.http.RealInterceptorChain
import okhttp3.internal.http1.Http1ExchangeCodec
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.Http2Connection
import okhttp3.internal.http2.Http2ExchangeCodec
import okhttp3.internal.http2.Http2Stream
import okhttp3.internal.http2.Settings
import okhttp3.internal.platform.Platform
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.internal.ws.RealWebSocket
import okhttp3.internal.ws.RealWebSocket.Streams
import okio.BufferedSink
import okio.BufferedSource
import okio.Okio

public class RealConnection(connectionPool: RealConnectionPool, route: Route) : Http2Connection.Listener, Connection {
   private final var allocationLimit: Int
   public final val calls: MutableList<Reference<RealCall>>
   public final val connectionPool: RealConnectionPool
   private final var handshake: Handshake?
   private final var http2Connection: Http2Connection?
   internal final var idleAtNs: Long

   internal final val isMultiplexed: Boolean
      internal final get() {
         val var1: Boolean;
         if (this.http2Connection != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final var noCoalescedConnections: Boolean

   public final var noNewExchanges: Boolean
      internal set

   private final var protocol: Protocol?
   private final var rawSocket: Socket?
   private final var refusedStreamCount: Int
   private final val route: Route
   internal final var routeFailureCount: Int
   private final var sink: BufferedSink?
   private final var socket: Socket?
   private final var source: BufferedSource?
   private final var successCount: Int

   init {
      Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
      Intrinsics.checkParameterIsNotNull(var2, "route");
      super();
      this.connectionPool = var1;
      this.route = var2;
      this.allocationLimit = 1;
      this.calls = new ArrayList<>();
      this.idleAtNs = java.lang.Long.MAX_VALUE;
   }

   private fun certificateSupportHost(url: HttpUrl, handshake: Handshake): Boolean {
      val var6: java.util.List = var2.peerCertificates();
      val var5: Boolean = var6.isEmpty();
      var var3: Boolean = false;
      if (!var5) {
         val var8: OkHostnameVerifier = OkHostnameVerifier.INSTANCE;
         val var7: java.lang.String = var1.host();
         val var9: Any = var6.get(0);
         if (var9 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
         }

         var3 = false;
         if (var8.verify(var7, var9 as X509Certificate)) {
            var3 = true;
         }
      }

      return var3;
   }

   @Throws(java/io/IOException::class)
   private fun connectSocket(connectTimeout: Int, readTimeout: Int, call: Call, eventListener: EventListener) {
      var var8: Proxy;
      var var13: Socket;
      label43: {
         var8 = this.route.proxy();
         val var7: Address = this.route.address();
         val var6: Type = var8.type();
         if (var6 != null) {
            val var5: Int = RealConnection$WhenMappings.$EnumSwitchMapping$0[var6.ordinal()];
            if (var5 == 1 || var5 == 2) {
               val var14: Socket = var7.socketFactory().createSocket();
               var13 = var14;
               if (var14 == null) {
                  Intrinsics.throwNpe();
                  var13 = var14;
               }
               break label43;
            }
         }

         var13 = new Socket(var8);
      }

      this.rawSocket = var13;
      var4.connectStart(var3, this.route.socketAddress(), var8);
      var13.setSoTimeout(var2);

      try {
         Platform.Companion.get().connectSocket(var13, this.route.socketAddress(), var1);
      } catch (var9: ConnectException) {
         val var11: StringBuilder = new StringBuilder("Failed to connect to ");
         var11.append(this.route.socketAddress());
         val var12: ConnectException = new ConnectException(var11.toString());
         var12.initCause(var9);
         throw var12 as java.lang.Throwable;
      }

      try {
         this.source = Okio.buffer(Okio.source(var13));
         this.sink = Okio.buffer(Okio.sink(var13));
      } catch (var10: NullPointerException) {
         if (var10.getMessage() == "throw with null exception") {
            throw (new IOException(var10)) as java.lang.Throwable;
         }
      }
   }

   @Throws(java/io/IOException::class)
   private fun connectTls(connectionSpecSelector: ConnectionSpecSelector) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield okhttp3/internal/connection/RealConnection.route Lokhttp3/Route;
      // 004: invokevirtual okhttp3/Route.address ()Lokhttp3/Address;
      // 007: astore 5
      // 009: aload 5
      // 00b: invokevirtual okhttp3/Address.sslSocketFactory ()Ljavax/net/ssl/SSLSocketFactory;
      // 00e: astore 2
      // 00f: aconst_null
      // 010: astore 4
      // 012: aconst_null
      // 013: astore 3
      // 014: aconst_null
      // 015: checkcast javax/net/ssl/SSLSocket
      // 018: astore 6
      // 01a: aload 2
      // 01b: ifnonnull 021
      // 01e: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 021: aload 2
      // 022: aload 0
      // 023: getfield okhttp3/internal/connection/RealConnection.rawSocket Ljava/net/Socket;
      // 026: aload 5
      // 028: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 02b: invokevirtual okhttp3/HttpUrl.host ()Ljava/lang/String;
      // 02e: aload 5
      // 030: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 033: invokevirtual okhttp3/HttpUrl.port ()I
      // 036: bipush 1
      // 037: invokevirtual javax/net/ssl/SSLSocketFactory.createSocket (Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
      // 03a: astore 2
      // 03b: aload 2
      // 03c: ifnull 261
      // 03f: aload 2
      // 040: checkcast javax/net/ssl/SSLSocket
      // 043: astore 2
      // 044: aload 1
      // 045: aload 2
      // 046: invokevirtual okhttp3/internal/connection/ConnectionSpecSelector.configureSecureSocket (Ljavax/net/ssl/SSLSocket;)Lokhttp3/ConnectionSpec;
      // 049: astore 4
      // 04b: aload 4
      // 04d: invokevirtual okhttp3/ConnectionSpec.supportsTlsExtensions ()Z
      // 050: ifeq 06a
      // 053: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
      // 056: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
      // 059: aload 2
      // 05a: aload 5
      // 05c: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 05f: invokevirtual okhttp3/HttpUrl.host ()Ljava/lang/String;
      // 062: aload 5
      // 064: invokevirtual okhttp3/Address.protocols ()Ljava/util/List;
      // 067: invokevirtual okhttp3/internal/platform/Platform.configureTlsExtensions (Ljavax/net/ssl/SSLSocket;Ljava/lang/String;Ljava/util/List;)V
      // 06a: aload 2
      // 06b: invokevirtual javax/net/ssl/SSLSocket.startHandshake ()V
      // 06e: aload 2
      // 06f: invokevirtual javax/net/ssl/SSLSocket.getSession ()Ljavax/net/ssl/SSLSession;
      // 072: astore 1
      // 073: getstatic okhttp3/Handshake.Companion Lokhttp3/Handshake$Companion;
      // 076: astore 6
      // 078: aload 1
      // 079: ldc_w "sslSocketSession"
      // 07c: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07f: aload 6
      // 081: aload 1
      // 082: invokevirtual okhttp3/Handshake$Companion.get (Ljavax/net/ssl/SSLSession;)Lokhttp3/Handshake;
      // 085: astore 6
      // 087: aload 5
      // 089: invokevirtual okhttp3/Address.hostnameVerifier ()Ljavax/net/ssl/HostnameVerifier;
      // 08c: astore 7
      // 08e: aload 7
      // 090: ifnonnull 096
      // 093: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 096: aload 7
      // 098: aload 5
      // 09a: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 09d: invokevirtual okhttp3/HttpUrl.host ()Ljava/lang/String;
      // 0a0: aload 1
      // 0a1: invokeinterface javax/net/ssl/HostnameVerifier.verify (Ljava/lang/String;Ljavax/net/ssl/SSLSession;)Z 3
      // 0a6: ifne 191
      // 0a9: aload 6
      // 0ab: invokevirtual okhttp3/Handshake.peerCertificates ()Ljava/util/List;
      // 0ae: astore 1
      // 0af: aload 1
      // 0b0: checkcast java/util/Collection
      // 0b3: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 0b8: ifne 160
      // 0bb: aload 1
      // 0bc: bipush 0
      // 0bd: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 0c2: astore 1
      // 0c3: aload 1
      // 0c4: ifnonnull 0d3
      // 0c7: new kotlin/TypeCastException
      // 0ca: astore 1
      // 0cb: aload 1
      // 0cc: ldc "null cannot be cast to non-null type java.security.cert.X509Certificate"
      // 0ce: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 0d1: aload 1
      // 0d2: athrow
      // 0d3: aload 1
      // 0d4: checkcast java/security/cert/X509Certificate
      // 0d7: astore 4
      // 0d9: new javax/net/ssl/SSLPeerUnverifiedException
      // 0dc: astore 3
      // 0dd: new java/lang/StringBuilder
      // 0e0: astore 1
      // 0e1: aload 1
      // 0e2: ldc_w "\n              |Hostname "
      // 0e5: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0e8: aload 1
      // 0e9: aload 5
      // 0eb: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 0ee: invokevirtual okhttp3/HttpUrl.host ()Ljava/lang/String;
      // 0f1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f4: pop
      // 0f5: aload 1
      // 0f6: ldc_w " not verified:\n              |    certificate: "
      // 0f9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0fc: pop
      // 0fd: aload 1
      // 0fe: getstatic okhttp3/CertificatePinner.Companion Lokhttp3/CertificatePinner$Companion;
      // 101: aload 4
      // 103: checkcast java/security/cert/Certificate
      // 106: invokevirtual okhttp3/CertificatePinner$Companion.pin (Ljava/security/cert/Certificate;)Ljava/lang/String;
      // 109: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 10c: pop
      // 10d: aload 1
      // 10e: ldc_w "\n              |    DN: "
      // 111: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 114: pop
      // 115: aload 4
      // 117: invokevirtual java/security/cert/X509Certificate.getSubjectDN ()Ljava/security/Principal;
      // 11a: astore 5
      // 11c: aload 5
      // 11e: ldc_w "cert.subjectDN"
      // 121: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 124: aload 1
      // 125: aload 5
      // 127: invokeinterface java/security/Principal.getName ()Ljava/lang/String; 1
      // 12c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12f: pop
      // 130: aload 1
      // 131: ldc_w "\n              |    subjectAltNames: "
      // 134: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 137: pop
      // 138: aload 1
      // 139: getstatic okhttp3/internal/tls/OkHostnameVerifier.INSTANCE Lokhttp3/internal/tls/OkHostnameVerifier;
      // 13c: aload 4
      // 13e: invokevirtual okhttp3/internal/tls/OkHostnameVerifier.allSubjectAltNames (Ljava/security/cert/X509Certificate;)Ljava/util/List;
      // 141: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 144: pop
      // 145: aload 1
      // 146: ldc_w "\n              "
      // 149: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 14c: pop
      // 14d: aload 3
      // 14e: aload 1
      // 14f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 152: aconst_null
      // 153: bipush 1
      // 154: aconst_null
      // 155: invokestatic kotlin/text/StringsKt.trimMargin$default (Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 158: invokespecial javax/net/ssl/SSLPeerUnverifiedException.<init> (Ljava/lang/String;)V
      // 15b: aload 3
      // 15c: checkcast java/lang/Throwable
      // 15f: athrow
      // 160: new javax/net/ssl/SSLPeerUnverifiedException
      // 163: astore 3
      // 164: new java/lang/StringBuilder
      // 167: astore 1
      // 168: aload 1
      // 169: ldc_w "Hostname "
      // 16c: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 16f: aload 1
      // 170: aload 5
      // 172: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 175: invokevirtual okhttp3/HttpUrl.host ()Ljava/lang/String;
      // 178: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 17b: pop
      // 17c: aload 1
      // 17d: ldc_w " not verified (no certificates)"
      // 180: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 183: pop
      // 184: aload 3
      // 185: aload 1
      // 186: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 189: invokespecial javax/net/ssl/SSLPeerUnverifiedException.<init> (Ljava/lang/String;)V
      // 18c: aload 3
      // 18d: checkcast java/lang/Throwable
      // 190: athrow
      // 191: aload 5
      // 193: invokevirtual okhttp3/Address.certificatePinner ()Lokhttp3/CertificatePinner;
      // 196: astore 1
      // 197: aload 1
      // 198: ifnonnull 19e
      // 19b: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 19e: new okhttp3/Handshake
      // 1a1: astore 8
      // 1a3: aload 6
      // 1a5: invokevirtual okhttp3/Handshake.tlsVersion ()Lokhttp3/TlsVersion;
      // 1a8: astore 7
      // 1aa: aload 6
      // 1ac: invokevirtual okhttp3/Handshake.cipherSuite ()Lokhttp3/CipherSuite;
      // 1af: astore 11
      // 1b1: aload 6
      // 1b3: invokevirtual okhttp3/Handshake.localCertificates ()Ljava/util/List;
      // 1b6: astore 10
      // 1b8: new okhttp3/internal/connection/RealConnection$connectTls$1
      // 1bb: astore 9
      // 1bd: aload 9
      // 1bf: aload 1
      // 1c0: aload 6
      // 1c2: aload 5
      // 1c4: invokespecial okhttp3/internal/connection/RealConnection$connectTls$1.<init> (Lokhttp3/CertificatePinner;Lokhttp3/Handshake;Lokhttp3/Address;)V
      // 1c7: aload 8
      // 1c9: aload 7
      // 1cb: aload 11
      // 1cd: aload 10
      // 1cf: aload 9
      // 1d1: checkcast kotlin/jvm/functions/Function0
      // 1d4: invokespecial okhttp3/Handshake.<init> (Lokhttp3/TlsVersion;Lokhttp3/CipherSuite;Ljava/util/List;Lkotlin/jvm/functions/Function0;)V
      // 1d7: aload 0
      // 1d8: aload 8
      // 1da: putfield okhttp3/internal/connection/RealConnection.handshake Lokhttp3/Handshake;
      // 1dd: aload 5
      // 1df: invokevirtual okhttp3/Address.url ()Lokhttp3/HttpUrl;
      // 1e2: invokevirtual okhttp3/HttpUrl.host ()Ljava/lang/String;
      // 1e5: astore 6
      // 1e7: new okhttp3/internal/connection/RealConnection$connectTls$2
      // 1ea: astore 5
      // 1ec: aload 5
      // 1ee: aload 0
      // 1ef: invokespecial okhttp3/internal/connection/RealConnection$connectTls$2.<init> (Lokhttp3/internal/connection/RealConnection;)V
      // 1f2: aload 1
      // 1f3: aload 6
      // 1f5: aload 5
      // 1f7: checkcast kotlin/jvm/functions/Function0
      // 1fa: invokevirtual okhttp3/CertificatePinner.check$okhttp (Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V
      // 1fd: aload 3
      // 1fe: astore 1
      // 1ff: aload 4
      // 201: invokevirtual okhttp3/ConnectionSpec.supportsTlsExtensions ()Z
      // 204: ifeq 212
      // 207: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
      // 20a: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
      // 20d: aload 2
      // 20e: invokevirtual okhttp3/internal/platform/Platform.getSelectedProtocol (Ljavax/net/ssl/SSLSocket;)Ljava/lang/String;
      // 211: astore 1
      // 212: aload 0
      // 213: aload 2
      // 214: checkcast java/net/Socket
      // 217: putfield okhttp3/internal/connection/RealConnection.socket Ljava/net/Socket;
      // 21a: aload 0
      // 21b: aload 2
      // 21c: checkcast java/net/Socket
      // 21f: invokestatic okio/Okio.source (Ljava/net/Socket;)Lokio/Source;
      // 222: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
      // 225: putfield okhttp3/internal/connection/RealConnection.source Lokio/BufferedSource;
      // 228: aload 0
      // 229: aload 2
      // 22a: checkcast java/net/Socket
      // 22d: invokestatic okio/Okio.sink (Ljava/net/Socket;)Lokio/Sink;
      // 230: invokestatic okio/Okio.buffer (Lokio/Sink;)Lokio/BufferedSink;
      // 233: putfield okhttp3/internal/connection/RealConnection.sink Lokio/BufferedSink;
      // 236: aload 1
      // 237: ifnull 245
      // 23a: getstatic okhttp3/Protocol.Companion Lokhttp3/Protocol$Companion;
      // 23d: aload 1
      // 23e: invokevirtual okhttp3/Protocol$Companion.get (Ljava/lang/String;)Lokhttp3/Protocol;
      // 241: astore 1
      // 242: goto 249
      // 245: getstatic okhttp3/Protocol.HTTP_1_1 Lokhttp3/Protocol;
      // 248: astore 1
      // 249: aload 0
      // 24a: aload 1
      // 24b: putfield okhttp3/internal/connection/RealConnection.protocol Lokhttp3/Protocol;
      // 24e: aload 2
      // 24f: ifnull 25c
      // 252: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
      // 255: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
      // 258: aload 2
      // 259: invokevirtual okhttp3/internal/platform/Platform.afterHandshake (Ljavax/net/ssl/SSLSocket;)V
      // 25c: return
      // 25d: astore 1
      // 25e: goto 272
      // 261: new kotlin/TypeCastException
      // 264: astore 1
      // 265: aload 1
      // 266: ldc_w "null cannot be cast to non-null type javax.net.ssl.SSLSocket"
      // 269: invokespecial kotlin/TypeCastException.<init> (Ljava/lang/String;)V
      // 26c: aload 1
      // 26d: athrow
      // 26e: astore 1
      // 26f: aload 4
      // 271: astore 2
      // 272: aload 2
      // 273: ifnull 280
      // 276: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
      // 279: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
      // 27c: aload 2
      // 27d: invokevirtual okhttp3/internal/platform/Platform.afterHandshake (Ljavax/net/ssl/SSLSocket;)V
      // 280: aload 2
      // 281: ifnull 28b
      // 284: aload 2
      // 285: checkcast java/net/Socket
      // 288: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/net/Socket;)V
      // 28b: aload 1
      // 28c: athrow
   }

   @Throws(java/io/IOException::class)
   private fun connectTunnel(connectTimeout: Int, readTimeout: Int, writeTimeout: Int, call: Call, eventListener: EventListener) {
      var var7: Request = this.createTunnelRequest();
      val var8: HttpUrl = var7.url();

      for (int var6 = 0; var6 < 21; var6++) {
         this.connectSocket(var1, var2, var4, var5);
         var7 = this.createTunnel(var2, var3, var7, var8);
         if (var7 == null) {
            break;
         }

         if (this.rawSocket != null) {
            Util.closeQuietly(this.rawSocket);
         }

         val var10: Socket = null as Socket;
         this.rawSocket = null;
         val var11: BufferedSink = null as BufferedSink;
         this.sink = null;
         val var12: BufferedSource = null as BufferedSource;
         this.source = null;
         var5.connectEnd(var4, this.route.socketAddress(), this.route.proxy(), null);
      }
   }

   @Throws(java/io/IOException::class)
   private fun createTunnel(readTimeout: Int, writeTimeout: Int, tunnelRequest: Request, url: HttpUrl): Request? {
      val var6: StringBuilder = new StringBuilder("CONNECT ");
      var6.append(Util.toHostHeader(var4, true));
      var6.append(" HTTP/1.1");
      val var11: java.lang.String = var6.toString();

      val var13: Response;
      do {
         val var8: BufferedSource = this.source;
         if (this.source == null) {
            Intrinsics.throwNpe();
         }

         val var12: BufferedSink = this.sink;
         if (this.sink == null) {
            Intrinsics.throwNpe();
         }

         val var9: Http1ExchangeCodec = new Http1ExchangeCodec(null, this, this.source, this.sink);
         var8.timeout().timeout((long)var1, TimeUnit.MILLISECONDS);
         var12.timeout().timeout((long)var2, TimeUnit.MILLISECONDS);
         var9.writeRequest(var3.headers(), var11);
         var9.finishRequest();
         val var7: Response.Builder = var9.readResponseHeaders(false);
         if (var7 == null) {
            Intrinsics.throwNpe();
         }

         var13 = var7.request(var3).build();
         var9.skipConnectBody(var13);
         val var5: Int = var13.code();
         if (var5 == 200) {
            if (var8.getBuffer().exhausted() && var12.getBuffer().exhausted()) {
               return null;
            }

            throw (new IOException("TLS tunnel buffered too many bytes!")) as java.lang.Throwable;
         }

         if (var5 != 407) {
            val var10: StringBuilder = new StringBuilder("Unexpected response code for CONNECT: ");
            var10.append(var13.code());
            throw (new IOException(var10.toString())) as java.lang.Throwable;
         }

         var3 = this.route.address().proxyAuthenticator().authenticate(this.route, var13);
         if (var3 == null) {
            throw (new IOException("Failed to authenticate with proxy")) as java.lang.Throwable;
         }
      } while (!StringsKt.equals("close", Response.header$default(var13, "Connection", null, 2, null), true));

      return var3;
   }

   @Throws(java/io/IOException::class)
   private fun createTunnelRequest(): Request {
      var var1: Request = new Request.Builder()
         .url(this.route.address().url())
         .method("CONNECT", null)
         .header("Host", Util.toHostHeader(this.route.address().url(), true))
         .header("Proxy-Connection", "Keep-Alive")
         .header("User-Agent", "okhttp/4.8.0")
         .build();
      val var3: Request = this.route
         .address()
         .proxyAuthenticator()
         .authenticate(
            this.route,
            new Response.Builder()
               .request(var1)
               .protocol(Protocol.HTTP_1_1)
               .code(407)
               .message("Preemptive Authenticate")
               .body(Util.EMPTY_RESPONSE)
               .sentRequestAtMillis(-1L)
               .receivedResponseAtMillis(-1L)
               .header("Proxy-Authenticate", "OkHttp-Preemptive")
               .build()
         );
      if (var3 != null) {
         var1 = var3;
      }

      return var1;
   }

   @Throws(java/io/IOException::class)
   private fun establishProtocol(connectionSpecSelector: ConnectionSpecSelector, pingIntervalMillis: Int, call: Call, eventListener: EventListener) {
      if (this.route.address().sslSocketFactory() == null) {
         if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            this.socket = this.rawSocket;
            this.protocol = Protocol.H2_PRIOR_KNOWLEDGE;
            this.startHttp2(var2);
         } else {
            this.socket = this.rawSocket;
            this.protocol = Protocol.HTTP_1_1;
         }
      } else {
         var4.secureConnectStart(var3);
         this.connectTls(var1);
         var4.secureConnectEnd(var3, this.handshake);
         if (this.protocol === Protocol.HTTP_2) {
            this.startHttp2(var2);
         }
      }
   }

   private fun routeMatchesAny(candidates: List<Route>): Boolean {
      val var5: java.lang.Iterable = var1;
      var var2: Boolean = var1 is java.util.Collection;
      val var3: Boolean = false;
      if (var2 && (var5 as java.util.Collection).isEmpty()) {
         var2 = false;
      } else {
         val var4: java.util.Iterator = var5.iterator();

         while (true) {
            var2 = var3;
            if (!var4.hasNext()) {
               break;
            }

            val var6: Route = var4.next() as Route;
            if (var6.proxy().type() === Type.DIRECT && this.route.proxy().type() === Type.DIRECT && this.route.socketAddress() == var6.socketAddress()) {
               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   @Throws(java/io/IOException::class)
   private fun startHttp2(pingIntervalMillis: Int) {
      val var2: Socket = this.socket;
      if (this.socket == null) {
         Intrinsics.throwNpe();
      }

      val var4: BufferedSource = this.source;
      if (this.source == null) {
         Intrinsics.throwNpe();
      }

      val var3: BufferedSink = this.sink;
      if (this.sink == null) {
         Intrinsics.throwNpe();
      }

      this.socket.setSoTimeout(0);
      val var5: Http2Connection = new Http2Connection.Builder(true, TaskRunner.INSTANCE)
         .socket(var2, this.route.address().url().host(), var4, var3)
         .listener(this)
         .pingIntervalMillis(var1)
         .build();
      this.http2Connection = var5;
      this.allocationLimit = Http2Connection.Companion.getDEFAULT_SETTINGS().getMaxConcurrentStreams();
      Http2Connection.start$default(var5, false, null, 3, null);
   }

   private fun supportsUrl(url: HttpUrl): Boolean {
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var7: StringBuilder = new StringBuilder("Thread ");
         val var9: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var9, "Thread.currentThread()");
         var7.append(var9.getName());
         var7.append(" MUST hold lock on ");
         var7.append(this);
         throw (new AssertionError(var7.toString())) as java.lang.Throwable;
      } else {
         val var6: HttpUrl = this.route.address().url();
         if (var1.port() != var6.port()) {
            return false;
         } else if (var1.host() == var6.host()) {
            return true;
         } else {
            var var4: Boolean = false;
            if (!this.noCoalescedConnections) {
               var4 = false;
               if (this.handshake != null) {
                  if (this.handshake == null) {
                     Intrinsics.throwNpe();
                  }

                  var4 = false;
                  if (this.certificateSupportHost(var1, this.handshake)) {
                     var4 = true;
                  }
               }
            }

            return var4;
         }
      }
   }

   public fun cancel() {
      if (this.rawSocket != null) {
         Util.closeQuietly(this.rawSocket);
      }
   }

   public fun connect(
      connectTimeout: Int,
      readTimeout: Int,
      writeTimeout: Int,
      pingIntervalMillis: Int,
      connectionRetryEnabled: Boolean,
      call: Call,
      eventListener: EventListener
   ) {
      Intrinsics.checkParameterIsNotNull(var6, "call");
      Intrinsics.checkParameterIsNotNull(var7, "eventListener");
      val var8: Boolean;
      if (this.protocol == null) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (!var8) {
         throw (new IllegalStateException("already connected".toString())) as java.lang.Throwable;
      } else {
         val var9: RouteException = null as RouteException;
         val var17: java.util.List = this.route.address().connectionSpecs();
         val var11: ConnectionSpecSelector = new ConnectionSpecSelector(var17);
         if (this.route.address().sslSocketFactory() == null) {
            if (!var17.contains(ConnectionSpec.CLEARTEXT)) {
               throw (new RouteException(new UnknownServiceException("CLEARTEXT communication not enabled for client"))) as java.lang.Throwable;
            }

            val var18: java.lang.String = this.route.address().url().host();
            if (!Platform.Companion.get().isCleartextTrafficPermitted(var18)) {
               val var16: StringBuilder = new StringBuilder("CLEARTEXT communication to ");
               var16.append(var18);
               var16.append(" not permitted by network security policy");
               throw (new RouteException(new UnknownServiceException(var16.toString()))) as java.lang.Throwable;
            }
         } else if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            throw (new RouteException(new UnknownServiceException("H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"))) as java.lang.Throwable;
         }

         var var10: RouteException = null;

         while (true) {
            label84: {
               label83: {
                  label82: {
                     try {
                        if (this.route.requiresTunnel()) {
                           this.connectTunnel(var1, var2, var3, var6, var7);
                           var20 = this.rawSocket;
                           break label82;
                        }
                     } catch (var15: IOException) {
                        var19 = var15;
                        break label84;
                     }

                     try {
                        this.connectSocket(var1, var2, var6, var7);
                        break label83;
                     } catch (var14: IOException) {
                        var19 = var14;
                        break label84;
                     }
                  }

                  if (var20 == null) {
                     break;
                  }
               }

               try {
                  this.establishProtocol(var11, var4, var6, var7);
                  var7.connectEnd(var6, this.route.socketAddress(), this.route.proxy(), this.protocol);
                  break;
               } catch (var13: IOException) {
                  var19 = var13;
               }
            }

            if (this.socket != null) {
               Util.closeQuietly(this.socket);
            }

            if (this.rawSocket != null) {
               Util.closeQuietly(this.rawSocket);
            }

            val var22: Socket = null as Socket;
            this.socket = null;
            this.rawSocket = null;
            val var23: BufferedSource = null as BufferedSource;
            this.source = null;
            val var24: BufferedSink = null as BufferedSink;
            this.sink = null;
            val var25: Handshake = null as Handshake;
            this.handshake = null;
            val var26: Protocol = null as Protocol;
            this.protocol = null;
            val var27: Http2Connection = null as Http2Connection;
            this.http2Connection = null;
            this.allocationLimit = 1;
            var7.connectFailed(var6, this.route.socketAddress(), this.route.proxy(), null, var19);
            if (var10 == null) {
               var10 = new RouteException(var19);
            } else {
               var10.addConnectException(var19);
            }

            if (!var5 || !var11.connectionFailed(var19)) {
               throw var10 as java.lang.Throwable;
            }
         }

         if (this.route.requiresTunnel() && this.rawSocket == null) {
            throw (new RouteException(new ProtocolException("Too many tunnel connections attempted: 21"))) as java.lang.Throwable;
         } else {
            this.idleAtNs = System.nanoTime();
         }
      }
   }

   internal fun connectFailed(client: OkHttpClient, failedRoute: Route, failure: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "failedRoute");
      Intrinsics.checkParameterIsNotNull(var3, "failure");
      if (var2.proxy().type() != Type.DIRECT) {
         val var4: Address = var2.address();
         var4.proxySelector().connectFailed(var4.url().uri(), var2.proxy().address(), var3);
      }

      var1.getRouteDatabase().failed(var2);
   }

   public override fun handshake(): Handshake? {
      return this.handshake;
   }

   internal fun incrementSuccessCount() {
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
      // 03: aload 0
      // 04: getfield okhttp3/internal/connection/RealConnection.successCount I
      // 07: bipush 1
      // 08: iadd
      // 09: putfield okhttp3/internal/connection/RealConnection.successCount I
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: return
      // 0f: astore 1
      // 10: aload 0
      // 11: monitorexit
      // 12: aload 1
      // 13: athrow
   }

   internal fun isEligible(address: Address, routes: List<Route>?): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
         val var10: StringBuilder = new StringBuilder("Thread ");
         val var12: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var12, "Thread.currentThread()");
         var10.append(var12.getName());
         var10.append(" MUST hold lock on ");
         var10.append(this);
         throw (new AssertionError(var10.toString())) as java.lang.Throwable;
      } else {
         if (this.calls.size() < this.allocationLimit && !this.noNewExchanges) {
            if (!this.route.address().equalsNonHost$okhttp(var1)) {
               return false;
            }

            if (var1.url().host() == this.route().address().url().host()) {
               return true;
            }

            if (this.http2Connection == null) {
               return false;
            }

            if (var2 != null && this.routeMatchesAny(var2)) {
               if (var1.hostnameVerifier() != OkHostnameVerifier.INSTANCE) {
                  return false;
               }

               if (!this.supportsUrl(var1.url())) {
                  return false;
               }

               try {
                  var11 = var1.certificatePinner();
               } catch (var8: SSLPeerUnverifiedException) {
                  return false;
               }

               if (var11 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (var7: SSLPeerUnverifiedException) {
                     return false;
                  }
               }

               var var3: Handshake;
               try {
                  var9 = var1.url().host();
                  var3 = this.handshake();
               } catch (var6: SSLPeerUnverifiedException) {
                  return false;
               }

               if (var3 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (var5: SSLPeerUnverifiedException) {
                     return false;
                  }
               }

               try {
                  var11.check(var9, var3.peerCertificates());
                  return true;
               } catch (var4: SSLPeerUnverifiedException) {
               }
            }
         }

         return false;
      }
   }

   public fun isHealthy(doExtensiveChecks: Boolean): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 03: ifeq 54
      // 06: aload 0
      // 07: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 0a: ifne 10
      // 0d: goto 54
      // 10: new java/lang/StringBuilder
      // 13: dup
      // 14: ldc_w "Thread "
      // 17: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 1a: astore 6
      // 1c: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 1f: astore 7
      // 21: aload 7
      // 23: ldc_w "Thread.currentThread()"
      // 26: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 29: aload 6
      // 2b: aload 7
      // 2d: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 30: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 33: pop
      // 34: aload 6
      // 36: ldc_w " MUST NOT hold lock on "
      // 39: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3c: pop
      // 3d: aload 6
      // 3f: aload 0
      // 40: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 43: pop
      // 44: new java/lang/AssertionError
      // 47: dup
      // 48: aload 6
      // 4a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4d: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 50: checkcast java/lang/Throwable
      // 53: athrow
      // 54: invokestatic java/lang/System.nanoTime ()J
      // 57: lstore 4
      // 59: aload 0
      // 5a: getfield okhttp3/internal/connection/RealConnection.rawSocket Ljava/net/Socket;
      // 5d: astore 8
      // 5f: aload 8
      // 61: ifnonnull 67
      // 64: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 67: aload 0
      // 68: getfield okhttp3/internal/connection/RealConnection.socket Ljava/net/Socket;
      // 6b: astore 7
      // 6d: aload 7
      // 6f: ifnonnull 75
      // 72: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 75: aload 0
      // 76: getfield okhttp3/internal/connection/RealConnection.source Lokio/BufferedSource;
      // 79: astore 6
      // 7b: aload 6
      // 7d: ifnonnull 83
      // 80: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 83: aload 8
      // 85: invokevirtual java/net/Socket.isClosed ()Z
      // 88: ifne e2
      // 8b: aload 7
      // 8d: invokevirtual java/net/Socket.isClosed ()Z
      // 90: ifne e2
      // 93: aload 7
      // 95: invokevirtual java/net/Socket.isInputShutdown ()Z
      // 98: ifne e2
      // 9b: aload 7
      // 9d: invokevirtual java/net/Socket.isOutputShutdown ()Z
      // a0: ifeq a6
      // a3: goto e2
      // a6: aload 0
      // a7: getfield okhttp3/internal/connection/RealConnection.http2Connection Lokhttp3/internal/http2/Http2Connection;
      // aa: astore 8
      // ac: aload 8
      // ae: ifnull b9
      // b1: aload 8
      // b3: lload 4
      // b5: invokevirtual okhttp3/internal/http2/Http2Connection.isHealthy (J)Z
      // b8: ireturn
      // b9: aload 0
      // ba: monitorenter
      // bb: aload 0
      // bc: getfield okhttp3/internal/connection/RealConnection.idleAtNs J
      // bf: lstore 2
      // c0: aload 0
      // c1: monitorexit
      // c2: lload 4
      // c4: lload 2
      // c5: lsub
      // c6: ldc2_w 10000000000
      // c9: lcmp
      // ca: iflt d9
      // cd: iload 1
      // ce: ifeq d9
      // d1: aload 7
      // d3: aload 6
      // d5: invokestatic okhttp3/internal/Util.isHealthy (Ljava/net/Socket;Lokio/BufferedSource;)Z
      // d8: ireturn
      // d9: bipush 1
      // da: ireturn
      // db: astore 6
      // dd: aload 0
      // de: monitorexit
      // df: aload 6
      // e1: athrow
      // e2: bipush 0
      // e3: ireturn
   }

   @Throws(java/net/SocketException::class)
   internal fun newCodec(client: OkHttpClient, chain: RealInterceptorChain): ExchangeCodec {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "chain");
      if (this.socket == null) {
         Intrinsics.throwNpe();
      }

      val var5: BufferedSource = this.source;
      if (this.source == null) {
         Intrinsics.throwNpe();
      }

      val var3: BufferedSink = this.sink;
      if (this.sink == null) {
         Intrinsics.throwNpe();
      }

      val var7: ExchangeCodec;
      if (this.http2Connection != null) {
         var7 = new Http2ExchangeCodec(var1, this, var2, this.http2Connection);
      } else {
         this.socket.setSoTimeout(var2.readTimeoutMillis());
         var5.timeout().timeout((long)var2.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
         var3.timeout().timeout((long)var2.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
         var7 = new Http1ExchangeCodec(var1, this, var5, var3);
      }

      return var7;
   }

   @Throws(java/net/SocketException::class)
   internal fun newWebSocketStreams(exchange: Exchange): Streams {
      Intrinsics.checkParameterIsNotNull(var1, "exchange");
      if (this.socket == null) {
         Intrinsics.throwNpe();
      }

      val var2: BufferedSource = this.source;
      if (this.source == null) {
         Intrinsics.throwNpe();
      }

      val var3: BufferedSink = this.sink;
      if (this.sink == null) {
         Intrinsics.throwNpe();
      }

      this.socket.setSoTimeout(0);
      this.noNewExchanges$okhttp();
      return new RealWebSocket.Streams(var1, var2, var3, true, var2, var3) {
         final Exchange $exchange;
         final BufferedSink $sink;
         final BufferedSource $source;

         {
            super(var4, var5, var6);
            this.$exchange = var1;
            this.$source = var2;
            this.$sink = var3;
         }

         @Override
         public void close() {
            this.$exchange.bodyComplete(-1L, true, true, null);
         }
      };
   }

   internal fun noCoalescedConnections() {
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
      // 3: bipush 1
      // 4: putfield okhttp3/internal/connection/RealConnection.noCoalescedConnections Z
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   internal fun noNewExchanges() {
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
      // 3: bipush 1
      // 4: putfield okhttp3/internal/connection/RealConnection.noNewExchanges Z
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public override fun onSettings(connection: Http2Connection, settings: Settings) {
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
      // 03: ldc_w "connection"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 2
      // 0a: ldc_w "settings"
      // 0d: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 10: aload 0
      // 11: aload 2
      // 12: invokevirtual okhttp3/internal/http2/Settings.getMaxConcurrentStreams ()I
      // 15: putfield okhttp3/internal/connection/RealConnection.allocationLimit I
      // 18: aload 0
      // 19: monitorexit
      // 1a: return
      // 1b: astore 1
      // 1c: aload 0
      // 1d: monitorexit
      // 1e: aload 1
      // 1f: athrow
   }

   @Throws(java/io/IOException::class)
   public override fun onStream(stream: Http2Stream) {
      Intrinsics.checkParameterIsNotNull(var1, "stream");
      var1.close(ErrorCode.REFUSED_STREAM, null);
   }

   public override fun protocol(): Protocol {
      if (this.protocol == null) {
         Intrinsics.throwNpe();
      }

      return this.protocol;
   }

   public override fun route(): Route {
      return this.route;
   }

   public override fun socket(): Socket {
      if (this.socket == null) {
         Intrinsics.throwNpe();
      }

      return this.socket;
   }

   public override fun toString(): String {
      var var2: StringBuilder;
      var var3: Any;
      label12: {
         var2 = new StringBuilder("Connection{");
         var2.append(this.route.address().url().host());
         var2.append(':');
         var2.append(this.route.address().url().port());
         var2.append(", proxy=");
         var2.append(this.route.proxy());
         var2.append(" hostAddress=");
         var2.append(this.route.socketAddress());
         var2.append(" cipherSuite=");
         if (this.handshake != null) {
            var3 = this.handshake.cipherSuite();
            if (var3 != null) {
               break label12;
            }
         }

         var3 = "none";
      }

      var2.append(var3);
      var2.append(" protocol=");
      var2.append(this.protocol);
      var2.append('}');
      return var2.toString();
   }

   internal fun trackFailure(call: RealCall, e: IOException?) {
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
      // 03: ldc_w "call"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 2
      // 0a: instanceof okhttp3/internal/http2/StreamResetException
      // 0d: ifeq 69
      // 10: aload 2
      // 11: checkcast okhttp3/internal/http2/StreamResetException
      // 14: getfield okhttp3/internal/http2/StreamResetException.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 17: getstatic okhttp3/internal/http2/ErrorCode.REFUSED_STREAM Lokhttp3/internal/http2/ErrorCode;
      // 1a: if_acmpne 40
      // 1d: aload 0
      // 1e: getfield okhttp3/internal/connection/RealConnection.refusedStreamCount I
      // 21: bipush 1
      // 22: iadd
      // 23: istore 3
      // 24: aload 0
      // 25: iload 3
      // 26: putfield okhttp3/internal/connection/RealConnection.refusedStreamCount I
      // 29: iload 3
      // 2a: bipush 1
      // 2b: if_icmple 9e
      // 2e: aload 0
      // 2f: bipush 1
      // 30: putfield okhttp3/internal/connection/RealConnection.noNewExchanges Z
      // 33: aload 0
      // 34: aload 0
      // 35: getfield okhttp3/internal/connection/RealConnection.routeFailureCount I
      // 38: bipush 1
      // 39: iadd
      // 3a: putfield okhttp3/internal/connection/RealConnection.routeFailureCount I
      // 3d: goto 9e
      // 40: aload 2
      // 41: checkcast okhttp3/internal/http2/StreamResetException
      // 44: getfield okhttp3/internal/http2/StreamResetException.errorCode Lokhttp3/internal/http2/ErrorCode;
      // 47: getstatic okhttp3/internal/http2/ErrorCode.CANCEL Lokhttp3/internal/http2/ErrorCode;
      // 4a: if_acmpne 57
      // 4d: aload 1
      // 4e: invokevirtual okhttp3/internal/connection/RealCall.isCanceled ()Z
      // 51: ifeq 57
      // 54: goto 9e
      // 57: aload 0
      // 58: bipush 1
      // 59: putfield okhttp3/internal/connection/RealConnection.noNewExchanges Z
      // 5c: aload 0
      // 5d: aload 0
      // 5e: getfield okhttp3/internal/connection/RealConnection.routeFailureCount I
      // 61: bipush 1
      // 62: iadd
      // 63: putfield okhttp3/internal/connection/RealConnection.routeFailureCount I
      // 66: goto 9e
      // 69: aload 0
      // 6a: invokevirtual okhttp3/internal/connection/RealConnection.isMultiplexed$okhttp ()Z
      // 6d: ifeq 77
      // 70: aload 2
      // 71: instanceof okhttp3/internal/http2/ConnectionShutdownException
      // 74: ifeq 9e
      // 77: aload 0
      // 78: bipush 1
      // 79: putfield okhttp3/internal/connection/RealConnection.noNewExchanges Z
      // 7c: aload 0
      // 7d: getfield okhttp3/internal/connection/RealConnection.successCount I
      // 80: ifne 9e
      // 83: aload 2
      // 84: ifnull 94
      // 87: aload 0
      // 88: aload 1
      // 89: invokevirtual okhttp3/internal/connection/RealCall.getClient ()Lokhttp3/OkHttpClient;
      // 8c: aload 0
      // 8d: getfield okhttp3/internal/connection/RealConnection.route Lokhttp3/Route;
      // 90: aload 2
      // 91: invokevirtual okhttp3/internal/connection/RealConnection.connectFailed$okhttp (Lokhttp3/OkHttpClient;Lokhttp3/Route;Ljava/io/IOException;)V
      // 94: aload 0
      // 95: aload 0
      // 96: getfield okhttp3/internal/connection/RealConnection.routeFailureCount I
      // 99: bipush 1
      // 9a: iadd
      // 9b: putfield okhttp3/internal/connection/RealConnection.routeFailureCount I
      // 9e: aload 0
      // 9f: monitorexit
      // a0: return
      // a1: astore 1
      // a2: aload 0
      // a3: monitorexit
      // a4: aload 1
      // a5: athrow
   }

   public companion object {
      internal const val IDLE_CONNECTION_HEALTHY_NS: Long
      private const val MAX_TUNNEL_ATTEMPTS: Int
      private const val NPE_THROW_WITH_NULL: String

      public fun newTestConnection(connectionPool: RealConnectionPool, route: Route, socket: Socket, idleAtNs: Long): RealConnection {
         Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
         Intrinsics.checkParameterIsNotNull(var2, "route");
         Intrinsics.checkParameterIsNotNull(var3, "socket");
         val var6: RealConnection = new RealConnection(var1, var2);
         RealConnection.access$setSocket$p(var6, var3);
         var6.setIdleAtNs$okhttp(var4);
         return var6;
      }
   }
}
