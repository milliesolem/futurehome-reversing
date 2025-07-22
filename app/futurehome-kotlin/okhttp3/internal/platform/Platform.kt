package okhttp3.internal.platform

import java.net.InetSocketAddress
import java.net.Socket
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.Provider
import java.security.Security
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Arrays
import java.util.logging.Level
import java.util.logging.Logger
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.Util
import okhttp3.internal.platform.android.AndroidLog
import okhttp3.internal.tls.BasicCertificateChainCleaner
import okhttp3.internal.tls.BasicTrustRootIndex
import okhttp3.internal.tls.CertificateChainCleaner
import okhttp3.internal.tls.TrustRootIndex
import okio.Buffer

public open class Platform {
   @JvmStatic
   fun {
      val var0: Platform.Companion = new Platform.Companion(null);
      Companion = var0;
      platform = Platform.Companion.access$findPlatform(var0);
   }

   public open fun afterHandshake(sslSocket: SSLSocket) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
   }

   public open fun buildCertificateChainCleaner(trustManager: X509TrustManager): CertificateChainCleaner {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      return new BasicCertificateChainCleaner(this.buildTrustRootIndex(var1));
   }

   public open fun buildTrustRootIndex(trustManager: X509TrustManager): TrustRootIndex {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      val var2: Array<X509Certificate> = var1.getAcceptedIssuers();
      Intrinsics.checkExpressionValueIsNotNull(var2, "trustManager.acceptedIssuers");
      return new BasicTrustRootIndex(Arrays.copyOf(var2, var2.length));
   }

   public open fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
   }

   @Throws(java/io/IOException::class)
   public open fun connectSocket(socket: Socket, address: InetSocketAddress, connectTimeout: Int) {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      Intrinsics.checkParameterIsNotNull(var2, "address");
      var1.connect(var2, var3);
   }

   public fun getPrefix(): String {
      return "OkHttp";
   }

   public open fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return null;
   }

   public open fun getStackTraceForCloseable(closer: String): Any? {
      Intrinsics.checkParameterIsNotNull(var1, "closer");
      val var2: java.lang.Throwable;
      if (logger.isLoggable(Level.FINE)) {
         var2 = new java.lang.Throwable(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public open fun isCleartextTrafficPermitted(hostname: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      return true;
   }

   public open fun log(message: String, level: Int = 4, t: Throwable? = null) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      val var4: Level;
      if (var2 == 5) {
         var4 = Level.WARNING;
      } else {
         var4 = Level.INFO;
      }

      logger.log(var4, var1, var3);
   }

   public open fun logCloseableLeak(message: String, stackTrace: Any?) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      var var3: java.lang.String = var1;
      if (var2 == null) {
         val var4: StringBuilder = new StringBuilder();
         var4.append(var1);
         var4.append(
            " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);"
         );
         var3 = var4.toString();
      }

      this.log(var3, 5, var2 as java.lang.Throwable);
   }

   public open fun newSSLContext(): SSLContext {
      val var1: SSLContext = SSLContext.getInstance("TLS");
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLS\")");
      return var1;
   }

   public open fun newSslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");

      try {
         val var2: SSLContext = this.newSSLContext();
         var2.init(null, new TrustManager[]{var1}, null);
         val var5: SSLSocketFactory = var2.getSocketFactory();
         Intrinsics.checkExpressionValueIsNotNull(var5, "newSSLContext().apply {\n…ll)\n      }.socketFactory");
         return var5;
      } catch (var3: GeneralSecurityException) {
         val var4: StringBuilder = new StringBuilder("No System TLS: ");
         var4.append(var3);
         throw (new AssertionError(var4.toString(), var3)) as java.lang.Throwable;
      }
   }

   public open fun platformTrustManager(): X509TrustManager {
      val var3: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      val var4: KeyStore = null as KeyStore;
      var3.init(null);
      Intrinsics.checkExpressionValueIsNotNull(var3, "factory");
      val var7: Array<TrustManager> = var3.getTrustManagers();
      if (var7 == null) {
         Intrinsics.throwNpe();
      }

      val var2: Int = var7.length;
      var var1: Boolean = true;
      if (var2 != 1 || var7[0] !is X509TrustManager) {
         var1 = false;
      }

      if (var1) {
         val var6: TrustManager = var7[0];
         if (var7[0] != null) {
            return var6 as X509TrustManager;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
         }
      } else {
         val var5: StringBuilder = new StringBuilder("Unexpected default trust managers: ");
         val var8: java.lang.String = Arrays.toString((Object[])var7);
         Intrinsics.checkExpressionValueIsNotNull(var8, "java.util.Arrays.toString(this)");
         var5.append(var8);
         throw (new IllegalStateException(var5.toString().toString())) as java.lang.Throwable;
      }
   }

   public override fun toString(): String {
      val var1: java.lang.String = this.getClass().getSimpleName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "javaClass.simpleName");
      return var1;
   }

   public open fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");

      var var7: Any;
      try {
         var7 = Class.forName("sun.security.ssl.SSLContextImpl");
         Intrinsics.checkExpressionValueIsNotNull(var7, "sslContextClass");
         var7 = Util.readFieldOrNull(var1, (Class)var7, "context");
      } catch (var5: ClassNotFoundException) {
         return null;
      }

      var var6: X509TrustManager = null;
      if (var7 != null) {
         try {
            var6 = Util.readFieldOrNull(var7, X509TrustManager.class, "trustManager");
         } catch (var4: ClassNotFoundException) {
            var6 = null;
         }
      }

      return var6;
   }

   public companion object {
      public const val INFO: Int
      public const val WARN: Int

      public final val isAndroid: Boolean
         public final get() {
            return "Dalvik" == System.getProperty("java.vm.name");
         }


      private final val isBouncyCastlePreferred: Boolean
         private final get() {
            val var1: Provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(var1, "Security.getProviders()[0]");
            return "BC" == var1.getName();
         }


      private final val isConscryptPreferred: Boolean
         private final get() {
            val var1: Provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(var1, "Security.getProviders()[0]");
            return "Conscrypt" == var1.getName();
         }


      private final val isOpenJSSEPreferred: Boolean
         private final get() {
            val var1: Provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(var1, "Security.getProviders()[0]");
            return "OpenJSSE" == var1.getName();
         }


      private final val logger: Logger
      private final var platform: Platform

      private fun findAndroidPlatform(): Platform {
         AndroidLog.INSTANCE.enable();
         var var1: Platform = Android10Platform.Companion.buildIfSupported();
         if (var1 == null) {
            val var2: Platform = AndroidPlatform.Companion.buildIfSupported();
            var1 = var2;
            if (var2 == null) {
               Intrinsics.throwNpe();
               var1 = var2;
            }
         }

         return var1;
      }

      private fun findJvmPlatform(): Platform {
         val var1: Platform.Companion = this;
         if (this.isConscryptPreferred()) {
            val var2: ConscryptPlatform = ConscryptPlatform.Companion.buildIfSupported();
            if (var2 != null) {
               return var2;
            }
         }

         if (this.isBouncyCastlePreferred()) {
            val var3: BouncyCastlePlatform = BouncyCastlePlatform.Companion.buildIfSupported();
            if (var3 != null) {
               return var3;
            }
         }

         if (this.isOpenJSSEPreferred()) {
            val var4: OpenJSSEPlatform = OpenJSSEPlatform.Companion.buildIfSupported();
            if (var4 != null) {
               return var4;
            }
         }

         val var5: Jdk9Platform = Jdk9Platform.Companion.buildIfSupported();
         if (var5 != null) {
            return var5;
         } else {
            val var6: Platform = Jdk8WithJettyBootPlatform.Companion.buildIfSupported();
            return var6 ?: new Platform();
         }
      }

      private fun findPlatform(): Platform {
         val var1: Platform.Companion = this;
         val var2: Platform;
         if (this.isAndroid()) {
            var2 = this.findAndroidPlatform();
         } else {
            var2 = this.findJvmPlatform();
         }

         return var2;
      }

      public fun alpnProtocolNames(protocols: List<Protocol>): List<String> {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         val var2: java.lang.Iterable = var1;
         val var4: java.util.Collection = new ArrayList();

         for (Object var3 : var2) {
            if (var3 as Protocol != Protocol.HTTP_1_0) {
               var4.add(var3);
            }
         }

         val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4 as java.util.List, 10));
         val var8: java.util.Iterator = (var4 as java.util.List).iterator();

         while (var8.hasNext()) {
            var5.add((var8.next() as Protocol).toString());
         }

         return var5 as MutableList<java.lang.String>;
      }

      public fun concatLengthPrefixed(protocols: List<Protocol>): ByteArray {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         val var2: Buffer = new Buffer();
         val var3: Platform.Companion = this;

         for (java.lang.String var5 : this.alpnProtocolNames(var1)) {
            var2.writeByte(var5.length());
            var2.writeUtf8(var5);
         }

         return var2.readByteArray();
      }

      public fun get(): Platform {
         return Platform.access$getPlatform$cp();
      }

      public fun resetForTests(platform: Platform = var0.findPlatform()) {
         Intrinsics.checkParameterIsNotNull(var1, "platform");
         Platform.access$setPlatform$cp(var1);
      }
   }
}
