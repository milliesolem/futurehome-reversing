package okhttp3.internal.platform

import java.security.KeyStore
import java.security.Provider
import java.util.Arrays
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLParameters
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import org.openjsse.net.ssl.OpenJSSE

public class OpenJSSEPlatform private constructor() : Platform {
   private final val provider: Provider = (new OpenJSSE()) as Provider

   @JvmStatic
   fun {
      val var1: OpenJSSEPlatform.Companion = new OpenJSSEPlatform.Companion(null);
      Companion = var1;
      var var0: Boolean = false;

      label13: {
         try {
            Class.forName("org.openjsse.net.ssl.OpenJSSE", false, var1.getClass().getClassLoader());
         } catch (var2: ClassNotFoundException) {
            break label13;
         }

         var0 = true;
      }

      isSupported = var0;
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (var1 is org.openjsse.javax.net.ssl.SSLSocket) {
         val var5: org.openjsse.javax.net.ssl.SSLSocket = var1 as org.openjsse.javax.net.ssl.SSLSocket;
         val var6: SSLParameters = (var1 as org.openjsse.javax.net.ssl.SSLSocket).getSSLParameters();
         if (var6 is org.openjsse.javax.net.ssl.SSLParameters) {
            val var4: java.util.List = Platform.Companion.alpnProtocolNames(var3);
            val var7: org.openjsse.javax.net.ssl.SSLParameters = var6 as org.openjsse.javax.net.ssl.SSLParameters;
            val var8: Array<Any> = var4.toArray(new java.lang.String[0]);
            if (var8 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            var7.setApplicationProtocols(var8 as Array<java.lang.String>);
            var5.setSSLParameters(var6);
         }
      } else {
         super.configureTlsExtensions(var1, var2, var3);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var3: java.lang.String;
      if (var1 is org.openjsse.javax.net.ssl.SSLSocket) {
         val var2: java.lang.String = (var1 as org.openjsse.javax.net.ssl.SSLSocket).getApplicationProtocol();
         if (var2 != null && !(var2 == "")) {
            return var2;
         }

         var3 = null;
      } else {
         var3 = super.getSelectedProtocol(var1);
      }

      return var3;
   }

   public override fun newSSLContext(): SSLContext {
      val var1: SSLContext = SSLContext.getInstance("TLSv1.3", this.provider);
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLSv1.3\", provider)");
      return var1;
   }

   public override fun platformTrustManager(): X509TrustManager {
      val var4: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm(), this.provider);
      val var3: KeyStore = null as KeyStore;
      var4.init(null);
      Intrinsics.checkExpressionValueIsNotNull(var4, "factory");
      val var7: Array<TrustManager> = var4.getTrustManagers();
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

   public override fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      throw (new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with OpenJSSE")) as java.lang.Throwable;
   }

   public companion object {
      public final val isSupported: Boolean

      public fun buildIfSupported(): OpenJSSEPlatform? {
         val var2: OpenJSSEPlatform.Companion = this;
         val var1: Boolean = this.isSupported();
         var var3: OpenJSSEPlatform = null;
         if (var1) {
            var3 = new OpenJSSEPlatform(null);
         }

         return var3;
      }
   }
}
