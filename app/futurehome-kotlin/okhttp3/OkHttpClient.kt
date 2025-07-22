package okhttp3

import j..time.Duration
import java.net.Proxy
import java.net.ProxySelector
import java.util.ArrayList
import java.util.Collections
import java.util.Random
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics
import okhttp3.EventListener.Factory
import okhttp3.Interceptor.Chain
import okhttp3.internal.Util
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.connection.RealCall
import okhttp3.internal.connection.RouteDatabase
import okhttp3.internal.platform.Platform
import okhttp3.internal.proxy.NullProxySelector
import okhttp3.internal.tls.CertificateChainCleaner
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.internal.ws.RealWebSocket

public open class OkHttpClient internal constructor(builder: okhttp3.OkHttpClient.Builder) : Cloneable, Call.Factory, WebSocket.Factory {
   public final val authenticator: Authenticator
      public final get() {
         return this.authenticator;
      }


   public final val cache: Cache?
      public final get() {
         return this.cache;
      }


   public final val callTimeoutMillis: Int
      public final get() {
         return this.callTimeoutMillis;
      }


   public final val certificateChainCleaner: CertificateChainCleaner?
      public final get() {
         return this.certificateChainCleaner;
      }


   public final val certificatePinner: CertificatePinner
      public final get() {
         return this.certificatePinner;
      }


   public final val connectTimeoutMillis: Int
      public final get() {
         return this.connectTimeoutMillis;
      }


   public final val connectionPool: ConnectionPool
      public final get() {
         return this.connectionPool;
      }


   public final val connectionSpecs: List<ConnectionSpec>
      public final get() {
         return this.connectionSpecs;
      }


   public final val cookieJar: CookieJar
      public final get() {
         return this.cookieJar;
      }


   public final val dispatcher: Dispatcher
      public final get() {
         return this.dispatcher;
      }


   public final val dns: Dns
      public final get() {
         return this.dns;
      }


   public final val eventListenerFactory: Factory
      public final get() {
         return this.eventListenerFactory;
      }


   public final val followRedirects: Boolean
      public final get() {
         return this.followRedirects;
      }


   public final val followSslRedirects: Boolean
      public final get() {
         return this.followSslRedirects;
      }


   public final val hostnameVerifier: HostnameVerifier
      public final get() {
         return this.hostnameVerifier;
      }


   public final val interceptors: List<Interceptor>
      public final get() {
         return this.interceptors;
      }


   public final val minWebSocketMessageToCompress: Long
      public final get() {
         return this.minWebSocketMessageToCompress;
      }


   public final val networkInterceptors: List<Interceptor>
      public final get() {
         return this.networkInterceptors;
      }


   public final val pingIntervalMillis: Int
      public final get() {
         return this.pingIntervalMillis;
      }


   public final val protocols: List<Protocol>
      public final get() {
         return this.protocols;
      }


   public final val proxy: Proxy?
      public final get() {
         return this.proxy;
      }


   public final val proxyAuthenticator: Authenticator
      public final get() {
         return this.proxyAuthenticator;
      }


   public final val proxySelector: ProxySelector
      public final get() {
         return this.proxySelector;
      }


   public final val readTimeoutMillis: Int
      public final get() {
         return this.readTimeoutMillis;
      }


   public final val retryOnConnectionFailure: Boolean
      public final get() {
         return this.retryOnConnectionFailure;
      }


   public final val routeDatabase: RouteDatabase

   public final val socketFactory: SocketFactory
      public final get() {
         return this.socketFactory;
      }


   public final val sslSocketFactory: SSLSocketFactory
      public final get() {
         if (this.sslSocketFactoryOrNull != null) {
            return this.sslSocketFactoryOrNull;
         } else {
            throw (new IllegalStateException("CLEARTEXT-only client")) as java.lang.Throwable;
         }
      }


   private final val sslSocketFactoryOrNull: SSLSocketFactory?

   public final val writeTimeoutMillis: Int
      public final get() {
         return this.writeTimeoutMillis;
      }


   public final val x509TrustManager: X509TrustManager?
      public final get() {
         return this.x509TrustManager;
      }


   public constructor() : this(new OkHttpClient.Builder())
   init {
      Intrinsics.checkParameterIsNotNull(var1, "builder");
      super();
      this.dispatcher = var1.getDispatcher$okhttp();
      this.connectionPool = var1.getConnectionPool$okhttp();
      this.interceptors = Util.toImmutableList(var1.getInterceptors$okhttp());
      this.networkInterceptors = Util.toImmutableList(var1.getNetworkInterceptors$okhttp());
      this.eventListenerFactory = var1.getEventListenerFactory$okhttp();
      this.retryOnConnectionFailure = var1.getRetryOnConnectionFailure$okhttp();
      this.authenticator = var1.getAuthenticator$okhttp();
      this.followRedirects = var1.getFollowRedirects$okhttp();
      this.followSslRedirects = var1.getFollowSslRedirects$okhttp();
      this.cookieJar = var1.getCookieJar$okhttp();
      this.cache = var1.getCache$okhttp();
      this.dns = var1.getDns$okhttp();
      this.proxy = var1.getProxy$okhttp();
      var var2: ProxySelector;
      if (var1.getProxy$okhttp() != null) {
         var2 = NullProxySelector.INSTANCE;
      } else {
         var2 = var1.getProxySelector$okhttp();
         if (var2 == null) {
            var2 = ProxySelector.getDefault();
         }

         if (var2 == null) {
            var2 = NullProxySelector.INSTANCE;
         }
      }

      this.proxySelector = var2;
      this.proxyAuthenticator = var1.getProxyAuthenticator$okhttp();
      this.socketFactory = var1.getSocketFactory$okhttp();
      val var3: java.util.List = var1.getConnectionSpecs$okhttp();
      this.connectionSpecs = var3;
      this.protocols = var1.getProtocols$okhttp();
      this.hostnameVerifier = var1.getHostnameVerifier$okhttp();
      this.callTimeoutMillis = var1.getCallTimeout$okhttp();
      this.connectTimeoutMillis = var1.getConnectTimeout$okhttp();
      this.readTimeoutMillis = var1.getReadTimeout$okhttp();
      this.writeTimeoutMillis = var1.getWriteTimeout$okhttp();
      this.pingIntervalMillis = var1.getPingInterval$okhttp();
      this.minWebSocketMessageToCompress = var1.getMinWebSocketMessageToCompress$okhttp();
      var var9: RouteDatabase = var1.getRouteDatabase$okhttp();
      if (var9 == null) {
         var9 = new RouteDatabase();
      }

      label76: {
         this.routeDatabase = var9;
         val var10: java.lang.Iterable = var3;
         if (var3 !is java.util.Collection || !(var3 as java.util.Collection).isEmpty()) {
            val var11: java.util.Iterator = var10.iterator();

            while (var11.hasNext()) {
               if ((var11.next() as ConnectionSpec).isTls()) {
                  if (var1.getSslSocketFactoryOrNull$okhttp() != null) {
                     this.sslSocketFactoryOrNull = var1.getSslSocketFactoryOrNull$okhttp();
                     val var12: CertificateChainCleaner = var1.getCertificateChainCleaner$okhttp();
                     if (var12 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.certificateChainCleaner = var12;
                     val var15: X509TrustManager = var1.getX509TrustManagerOrNull$okhttp();
                     if (var15 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.x509TrustManager = var15;
                     val var4: CertificatePinner = var1.getCertificatePinner$okhttp();
                     if (var12 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.certificatePinner = var4.withCertificateChainCleaner$okhttp(var12);
                  } else {
                     val var13: X509TrustManager = Platform.Companion.get().platformTrustManager();
                     this.x509TrustManager = var13;
                     val var16: Platform = Platform.Companion.get();
                     if (var13 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.sslSocketFactoryOrNull = var16.newSslSocketFactory(var13);
                     if (var13 == null) {
                        Intrinsics.throwNpe();
                     }

                     val var14: CertificateChainCleaner = CertificateChainCleaner.Companion.get(var13);
                     this.certificateChainCleaner = var14;
                     val var5: CertificatePinner = var1.getCertificatePinner$okhttp();
                     if (var14 == null) {
                        Intrinsics.throwNpe();
                     }

                     this.certificatePinner = var5.withCertificateChainCleaner$okhttp(var14);
                  }
                  break label76;
               }
            }
         }

         val var6: SSLSocketFactory = null as SSLSocketFactory;
         this.sslSocketFactoryOrNull = null;
         val var7: CertificateChainCleaner = null as CertificateChainCleaner;
         this.certificateChainCleaner = null;
         val var8: X509TrustManager = null as X509TrustManager;
         this.x509TrustManager = null;
         this.certificatePinner = CertificatePinner.DEFAULT;
      }

      this.verifyClientState();
   }

   private fun verifyClientState() {
      if (this.interceptors != null) {
         if (this.interceptors.contains(null)) {
            val var11: StringBuilder = new StringBuilder("Null interceptor: ");
            var11.append(this.interceptors);
            throw (new IllegalStateException(var11.toString().toString())) as java.lang.Throwable;
         } else if (this.networkInterceptors != null) {
            if (this.networkInterceptors.contains(null)) {
               val var10: StringBuilder = new StringBuilder("Null network interceptor: ");
               var10.append(this.networkInterceptors);
               throw (new IllegalStateException(var10.toString().toString())) as java.lang.Throwable;
            } else {
               val var7: java.lang.Iterable = this.connectionSpecs;
               if (this.connectionSpecs !is java.util.Collection || !this.connectionSpecs.isEmpty()) {
                  val var8: java.util.Iterator = var7.iterator();

                  while (var8.hasNext()) {
                     if ((var8.next() as ConnectionSpec).isTls()) {
                        if (this.sslSocketFactoryOrNull == null) {
                           throw (new IllegalStateException("sslSocketFactory == null".toString())) as java.lang.Throwable;
                        }

                        if (this.certificateChainCleaner == null) {
                           throw (new IllegalStateException("certificateChainCleaner == null".toString())) as java.lang.Throwable;
                        }

                        if (this.x509TrustManager == null) {
                           throw (new IllegalStateException("x509TrustManager == null".toString())) as java.lang.Throwable;
                        }

                        return;
                     }
                  }
               }

               var var1: Boolean;
               if (this.sslSocketFactoryOrNull == null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (!var1) {
                  throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
               } else {
                  if (this.certificateChainCleaner == null) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (!var1) {
                     throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
                  } else {
                     if (this.x509TrustManager == null) {
                        var1 = true;
                     } else {
                        var1 = false;
                     }

                     if (!var1) {
                        throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
                     } else if (!(this.certificatePinner == CertificatePinner.DEFAULT)) {
                        throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
                     }
                  }
               }
            }
         } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
         }
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Interceptor?>");
      }
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "authenticator", imports = []))
   public fun authenticator(): Authenticator {
      return this.authenticator;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cache", imports = []))
   public fun cache(): Cache? {
      return this.cache;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "callTimeoutMillis", imports = []))
   public fun callTimeoutMillis(): Int {
      return this.callTimeoutMillis;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "certificatePinner", imports = []))
   public fun certificatePinner(): CertificatePinner {
      return this.certificatePinner;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectTimeoutMillis", imports = []))
   public fun connectTimeoutMillis(): Int {
      return this.connectTimeoutMillis;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionPool", imports = []))
   public fun connectionPool(): ConnectionPool {
      return this.connectionPool;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionSpecs", imports = []))
   public fun connectionSpecs(): List<ConnectionSpec> {
      return this.connectionSpecs;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cookieJar", imports = []))
   public fun cookieJar(): CookieJar {
      return this.cookieJar;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dispatcher", imports = []))
   public fun dispatcher(): Dispatcher {
      return this.dispatcher;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dns", imports = []))
   public fun dns(): Dns {
      return this.dns;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "eventListenerFactory", imports = []))
   public fun eventListenerFactory(): Factory {
      return this.eventListenerFactory;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "followRedirects", imports = []))
   public fun followRedirects(): Boolean {
      return this.followRedirects;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "followSslRedirects", imports = []))
   public fun followSslRedirects(): Boolean {
      return this.followSslRedirects;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hostnameVerifier", imports = []))
   public fun hostnameVerifier(): HostnameVerifier {
      return this.hostnameVerifier;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "interceptors", imports = []))
   public fun interceptors(): List<Interceptor> {
      return this.interceptors;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "networkInterceptors", imports = []))
   public fun networkInterceptors(): List<Interceptor> {
      return this.networkInterceptors;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pingIntervalMillis", imports = []))
   public fun pingIntervalMillis(): Int {
      return this.pingIntervalMillis;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "protocols", imports = []))
   public fun protocols(): List<Protocol> {
      return this.protocols;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxy", imports = []))
   public fun proxy(): Proxy? {
      return this.proxy;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxyAuthenticator", imports = []))
   public fun proxyAuthenticator(): Authenticator {
      return this.proxyAuthenticator;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxySelector", imports = []))
   public fun proxySelector(): ProxySelector {
      return this.proxySelector;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "readTimeoutMillis", imports = []))
   public fun readTimeoutMillis(): Int {
      return this.readTimeoutMillis;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "retryOnConnectionFailure", imports = []))
   public fun retryOnConnectionFailure(): Boolean {
      return this.retryOnConnectionFailure;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "socketFactory", imports = []))
   public fun socketFactory(): SocketFactory {
      return this.socketFactory;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sslSocketFactory", imports = []))
   public fun sslSocketFactory(): SSLSocketFactory {
      return this.sslSocketFactory();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "writeTimeoutMillis", imports = []))
   public fun writeTimeoutMillis(): Int {
      return this.writeTimeoutMillis;
   }

   override fun clone(): Any {
      return super.clone();
   }

   public open fun newBuilder(): okhttp3.OkHttpClient.Builder {
      return new OkHttpClient.Builder(this);
   }

   public override fun newCall(request: Request): Call {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      return new RealCall(this, var1, false);
   }

   public override fun newWebSocket(request: Request, listener: WebSocketListener): WebSocket {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Intrinsics.checkParameterIsNotNull(var2, "listener");
      val var3: RealWebSocket = new RealWebSocket(
         TaskRunner.INSTANCE, var1, var2, new Random(), this.pingIntervalMillis, null, this.minWebSocketMessageToCompress
      );
      var3.connect(this);
      return var3;
   }

   public class Builder {
      internal final var dispatcher: Dispatcher = new Dispatcher()
      internal final var connectionPool: ConnectionPool
      internal final val interceptors: MutableList<Interceptor>
      internal final val networkInterceptors: MutableList<Interceptor>
      internal final var eventListenerFactory: Factory
      internal final var retryOnConnectionFailure: Boolean
      internal final var authenticator: Authenticator
      internal final var followRedirects: Boolean
      internal final var followSslRedirects: Boolean
      internal final var cookieJar: CookieJar
      internal final var cache: Cache?
      internal final var dns: Dns
      internal final var proxy: Proxy?
      internal final var proxySelector: ProxySelector?
      internal final var proxyAuthenticator: Authenticator
      internal final var socketFactory: SocketFactory
      internal final var sslSocketFactoryOrNull: SSLSocketFactory?
      internal final var x509TrustManagerOrNull: X509TrustManager?
      internal final var connectionSpecs: List<ConnectionSpec>
      internal final var protocols: List<Protocol>
      internal final var hostnameVerifier: HostnameVerifier
      internal final var certificatePinner: CertificatePinner
      internal final var certificateChainCleaner: CertificateChainCleaner?
      internal final var callTimeout: Int
      internal final var connectTimeout: Int
      internal final var readTimeout: Int
      internal final var writeTimeout: Int
      internal final var pingInterval: Int
      internal final var minWebSocketMessageToCompress: Long
      internal final var routeDatabase: RouteDatabase?

      init {
         this.connectionPool = new ConnectionPool();
         this.interceptors = new ArrayList<>();
         this.networkInterceptors = new ArrayList<>();
         this.eventListenerFactory = Util.asFactory(EventListener.NONE);
         this.retryOnConnectionFailure = true;
         this.authenticator = Authenticator.NONE;
         this.followRedirects = true;
         this.followSslRedirects = true;
         this.cookieJar = CookieJar.NO_COOKIES;
         this.dns = Dns.SYSTEM;
         this.proxyAuthenticator = Authenticator.NONE;
         val var1: SocketFactory = SocketFactory.getDefault();
         Intrinsics.checkExpressionValueIsNotNull(var1, "SocketFactory.getDefault()");
         this.socketFactory = var1;
         this.connectionSpecs = OkHttpClient.Companion.getDEFAULT_CONNECTION_SPECS$okhttp();
         this.protocols = OkHttpClient.Companion.getDEFAULT_PROTOCOLS$okhttp();
         this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
         this.certificatePinner = CertificatePinner.DEFAULT;
         this.connectTimeout = 10000;
         this.readTimeout = 10000;
         this.writeTimeout = 10000;
         this.minWebSocketMessageToCompress = 1024L;
      }

      internal constructor(okHttpClient: OkHttpClient) : Intrinsics.checkParameterIsNotNull(var1, "okHttpClient") {
         this();
         this.dispatcher = var1.dispatcher();
         this.connectionPool = var1.connectionPool();
         CollectionsKt.addAll(this.interceptors, var1.interceptors());
         CollectionsKt.addAll(this.networkInterceptors, var1.networkInterceptors());
         this.eventListenerFactory = var1.eventListenerFactory();
         this.retryOnConnectionFailure = var1.retryOnConnectionFailure();
         this.authenticator = var1.authenticator();
         this.followRedirects = var1.followRedirects();
         this.followSslRedirects = var1.followSslRedirects();
         this.cookieJar = var1.cookieJar();
         this.cache = var1.cache();
         this.dns = var1.dns();
         this.proxy = var1.proxy();
         this.proxySelector = var1.proxySelector();
         this.proxyAuthenticator = var1.proxyAuthenticator();
         this.socketFactory = var1.socketFactory();
         this.sslSocketFactoryOrNull = OkHttpClient.access$getSslSocketFactoryOrNull$p(var1);
         this.x509TrustManagerOrNull = var1.x509TrustManager();
         this.connectionSpecs = var1.connectionSpecs();
         this.protocols = var1.protocols();
         this.hostnameVerifier = var1.hostnameVerifier();
         this.certificatePinner = var1.certificatePinner();
         this.certificateChainCleaner = var1.certificateChainCleaner();
         this.callTimeout = var1.callTimeoutMillis();
         this.connectTimeout = var1.connectTimeoutMillis();
         this.readTimeout = var1.readTimeoutMillis();
         this.writeTimeout = var1.writeTimeoutMillis();
         this.pingInterval = var1.pingIntervalMillis();
         this.minWebSocketMessageToCompress = var1.minWebSocketMessageToCompress();
         this.routeDatabase = var1.getRouteDatabase();
      }

      public inline fun addInterceptor(crossinline block: (Chain) -> Response): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "block");
         val var2: Interceptor.Companion = Interceptor.Companion;
         return this.addInterceptor(new Interceptor(var1) {
            final Function1 $block$inlined;

            {
               this.$block$inlined = var1;
            }

            @Override
            public Response intercept(Interceptor.Chain var1) {
               Intrinsics.checkParameterIsNotNull(var1, "chain");
               return this.$block$inlined.invoke(var1) as Response;
            }
         });
      }

      public inline fun addNetworkInterceptor(crossinline block: (Chain) -> Response): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "block");
         val var2: Interceptor.Companion = Interceptor.Companion;
         return this.addNetworkInterceptor(new Interceptor(var1) {
            final Function1 $block$inlined;

            {
               this.$block$inlined = var1;
            }

            @Override
            public Response intercept(Interceptor.Chain var1) {
               Intrinsics.checkParameterIsNotNull(var1, "chain");
               return this.$block$inlined.invoke(var1) as Response;
            }
         });
      }

      public fun addInterceptor(interceptor: Interceptor): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "interceptor");
         val var2: OkHttpClient.Builder = this;
         this.interceptors.add(var1);
         return this;
      }

      public fun addNetworkInterceptor(interceptor: Interceptor): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "interceptor");
         val var2: OkHttpClient.Builder = this;
         this.networkInterceptors.add(var1);
         return this;
      }

      public fun authenticator(authenticator: Authenticator): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "authenticator");
         val var2: OkHttpClient.Builder = this;
         this.authenticator = var1;
         return this;
      }

      public fun build(): OkHttpClient {
         return new OkHttpClient(this);
      }

      public fun cache(cache: Cache?): okhttp3.OkHttpClient.Builder {
         val var2: OkHttpClient.Builder = this;
         this.cache = var1;
         return this;
      }

      public fun callTimeout(timeout: Long, unit: TimeUnit): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         val var4: OkHttpClient.Builder = this;
         this.callTimeout = Util.checkDuration("timeout", var1, var3);
         return this;
      }

      public fun callTimeout(duration: Duration): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         val var2: OkHttpClient.Builder = this;
         this.callTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return this;
      }

      public fun certificatePinner(certificatePinner: CertificatePinner): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "certificatePinner");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.certificatePinner)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.certificatePinner = var1;
         return this;
      }

      public fun connectTimeout(timeout: Long, unit: TimeUnit): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         val var4: OkHttpClient.Builder = this;
         this.connectTimeout = Util.checkDuration("timeout", var1, var3);
         return this;
      }

      public fun connectTimeout(duration: Duration): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         val var2: OkHttpClient.Builder = this;
         this.connectTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return this;
      }

      public fun connectionPool(connectionPool: ConnectionPool): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
         val var2: OkHttpClient.Builder = this;
         this.connectionPool = var1;
         return this;
      }

      public fun connectionSpecs(connectionSpecs: List<ConnectionSpec>): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "connectionSpecs");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.connectionSpecs)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.connectionSpecs = Util.toImmutableList(var1);
         return this;
      }

      public fun cookieJar(cookieJar: CookieJar): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "cookieJar");
         val var2: OkHttpClient.Builder = this;
         this.cookieJar = var1;
         return this;
      }

      public fun dispatcher(dispatcher: Dispatcher): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "dispatcher");
         val var2: OkHttpClient.Builder = this;
         this.dispatcher = var1;
         return this;
      }

      public fun dns(dns: Dns): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "dns");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.dns)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.dns = var1;
         return this;
      }

      public fun eventListener(eventListener: EventListener): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "eventListener");
         val var2: OkHttpClient.Builder = this;
         this.eventListenerFactory = Util.asFactory(var1);
         return this;
      }

      public fun eventListenerFactory(eventListenerFactory: Factory): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "eventListenerFactory");
         val var2: OkHttpClient.Builder = this;
         this.eventListenerFactory = var1;
         return this;
      }

      public fun followRedirects(followRedirects: Boolean): okhttp3.OkHttpClient.Builder {
         val var2: OkHttpClient.Builder = this;
         this.followRedirects = var1;
         return this;
      }

      public fun followSslRedirects(followProtocolRedirects: Boolean): okhttp3.OkHttpClient.Builder {
         val var2: OkHttpClient.Builder = this;
         this.followSslRedirects = var1;
         return this;
      }

      public fun hostnameVerifier(hostnameVerifier: HostnameVerifier): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "hostnameVerifier");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.hostnameVerifier)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.hostnameVerifier = var1;
         return this;
      }

      public fun interceptors(): MutableList<Interceptor> {
         return this.interceptors;
      }

      public fun minWebSocketMessageToCompress(bytes: Long): okhttp3.OkHttpClient.Builder {
         val var4: OkHttpClient.Builder = this;
         val var3: Boolean;
         if (var1 >= 0L) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.minWebSocketMessageToCompress = var1;
            return this;
         } else {
            val var5: StringBuilder = new StringBuilder("minWebSocketMessageToCompress must be positive: ");
            var5.append(var1);
            throw (new IllegalArgumentException(var5.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun networkInterceptors(): MutableList<Interceptor> {
         return this.networkInterceptors;
      }

      public fun pingInterval(interval: Long, unit: TimeUnit): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         val var4: OkHttpClient.Builder = this;
         this.pingInterval = Util.checkDuration("interval", var1, var3);
         return this;
      }

      public fun pingInterval(duration: Duration): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         val var2: OkHttpClient.Builder = this;
         this.pingInterval(var1.toMillis(), TimeUnit.MILLISECONDS);
         return this;
      }

      public fun protocols(protocols: List<Protocol>): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         val var5: OkHttpClient.Builder = this;
         var1 = CollectionsKt.toMutableList(var1);
         var var2: Boolean;
         if (!var1.contains(Protocol.H2_PRIOR_KNOWLEDGE) && !var1.contains(Protocol.HTTP_1_1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         if (!var2) {
            val var12: StringBuilder = new StringBuilder("protocols must contain h2_prior_knowledge or http/1.1: ");
            var12.append(var1);
            throw (new IllegalArgumentException(var12.toString().toString())) as java.lang.Throwable;
         } else {
            label39: {
               if (var1.contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
                  var2 = false;
                  if (var1.size() > 1) {
                     break label39;
                  }
               }

               var2 = true;
            }

            if (var2) {
               if (!var1.contains(Protocol.HTTP_1_0)) {
                  if (var1 != null) {
                     if (!var1.contains(null)) {
                        var1.remove(Protocol.SPDY_3);
                        if (!(var1 == this.protocols)) {
                           val var11: RouteDatabase = null as RouteDatabase;
                           this.routeDatabase = null;
                        }

                        var1 = Collections.unmodifiableList(var1);
                        Intrinsics.checkExpressionValueIsNotNull(var1, "Collections.unmodifiableList(protocolsCopy)");
                        this.protocols = var1;
                        return this;
                     } else {
                        throw (new IllegalArgumentException("protocols must not contain null".toString())) as java.lang.Throwable;
                     }
                  } else {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Protocol?>");
                  }
               } else {
                  val var10: StringBuilder = new StringBuilder("protocols must not contain http/1.0: ");
                  var10.append(var1);
                  throw (new IllegalArgumentException(var10.toString().toString())) as java.lang.Throwable;
               }
            } else {
               val var9: StringBuilder = new StringBuilder("protocols containing h2_prior_knowledge cannot use other protocols: ");
               var9.append(var1);
               throw (new IllegalArgumentException(var9.toString().toString())) as java.lang.Throwable;
            }
         }
      }

      public fun proxy(proxy: Proxy?): okhttp3.OkHttpClient.Builder {
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.proxy)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.proxy = var1;
         return this;
      }

      public fun proxyAuthenticator(proxyAuthenticator: Authenticator): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "proxyAuthenticator");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.proxyAuthenticator)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.proxyAuthenticator = var1;
         return this;
      }

      public fun proxySelector(proxySelector: ProxySelector): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "proxySelector");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.proxySelector)) {
            val var3: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.proxySelector = var1;
         return this;
      }

      public fun readTimeout(timeout: Long, unit: TimeUnit): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         val var4: OkHttpClient.Builder = this;
         this.readTimeout = Util.checkDuration("timeout", var1, var3);
         return this;
      }

      public fun readTimeout(duration: Duration): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         val var2: OkHttpClient.Builder = this;
         this.readTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return this;
      }

      public fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean): okhttp3.OkHttpClient.Builder {
         val var2: OkHttpClient.Builder = this;
         this.retryOnConnectionFailure = var1;
         return this;
      }

      public fun socketFactory(socketFactory: SocketFactory): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "socketFactory");
         val var2: OkHttpClient.Builder = this;
         if (var1 !is SSLSocketFactory) {
            if (!(var1 == this.socketFactory)) {
               val var3: RouteDatabase = null as RouteDatabase;
               this.routeDatabase = null;
            }

            this.socketFactory = var1;
            return this;
         } else {
            throw (new IllegalArgumentException("socketFactory instanceof SSLSocketFactory".toString())) as java.lang.Throwable;
         }
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Use the sslSocketFactory overload that accepts a X509TrustManager.")
      public fun sslSocketFactory(sslSocketFactory: SSLSocketFactory): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
         val var2: OkHttpClient.Builder = this;
         if (!(var1 == this.sslSocketFactoryOrNull)) {
            val var4: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.sslSocketFactoryOrNull = var1;
         val var5: X509TrustManager = Platform.Companion.get().trustManager(var1);
         if (var5 != null) {
            this.x509TrustManagerOrNull = var5;
            val var7: Platform = Platform.Companion.get();
            if (this.x509TrustManagerOrNull == null) {
               Intrinsics.throwNpe();
            }

            this.certificateChainCleaner = var7.buildCertificateChainCleaner(this.x509TrustManagerOrNull);
            return this;
         } else {
            val var6: StringBuilder = new StringBuilder("Unable to extract the trust manager on ");
            var6.append(Platform.Companion.get());
            var6.append(", sslSocketFactory is ");
            var6.append(var1.getClass());
            throw (new IllegalStateException(var6.toString())) as java.lang.Throwable;
         }
      }

      public fun sslSocketFactory(sslSocketFactory: SSLSocketFactory, trustManager: X509TrustManager): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
         Intrinsics.checkParameterIsNotNull(var2, "trustManager");
         val var3: OkHttpClient.Builder = this;
         if (!(var1 == this.sslSocketFactoryOrNull) || !(var2 == this.x509TrustManagerOrNull)) {
            val var4: RouteDatabase = null as RouteDatabase;
            this.routeDatabase = null;
         }

         this.sslSocketFactoryOrNull = var1;
         this.certificateChainCleaner = CertificateChainCleaner.Companion.get(var2);
         this.x509TrustManagerOrNull = var2;
         return this;
      }

      public fun writeTimeout(timeout: Long, unit: TimeUnit): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var3, "unit");
         val var4: OkHttpClient.Builder = this;
         this.writeTimeout = Util.checkDuration("timeout", var1, var3);
         return this;
      }

      public fun writeTimeout(duration: Duration): okhttp3.OkHttpClient.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "duration");
         val var2: OkHttpClient.Builder = this;
         this.writeTimeout(var1.toMillis(), TimeUnit.MILLISECONDS);
         return this;
      }
   }

   public companion object {
      internal final val DEFAULT_CONNECTION_SPECS: List<ConnectionSpec>
      internal final val DEFAULT_PROTOCOLS: List<Protocol>
   }
}
