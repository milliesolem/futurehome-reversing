package okhttp3.internal.platform

import java.security.KeyStore
import java.security.Provider
import java.util.Arrays
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import org.bouncycastle.jsse.BCSSLParameters
import org.bouncycastle.jsse.BCSSLSocket
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider

public class BouncyCastlePlatform private constructor() : Platform {
   private final val provider: Provider = (new BouncyCastleJsseProvider()) as Provider

   @JvmStatic
   fun {
      val var1: BouncyCastlePlatform.Companion = new BouncyCastlePlatform.Companion(null);
      Companion = var1;
      var var0: Boolean = false;

      label13: {
         try {
            Class.forName("org.bouncycastle.jsse.provider.BouncyCastleJsseProvider", false, var1.getClass().getClassLoader());
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
      if (var1 is BCSSLSocket) {
         val var5: BCSSLSocket = var1 as BCSSLSocket;
         val var4: BCSSLParameters = (var1 as BCSSLSocket).getParameters();
         var3 = Platform.Companion.alpnProtocolNames(var3);
         Intrinsics.checkExpressionValueIsNotNull(var4, "sslParameters");
         val var7: Array<Any> = var3.toArray(new java.lang.String[0]);
         if (var7 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         var4.setApplicationProtocols(var7 as Array<java.lang.String>);
         var5.setParameters(var4);
      } else {
         super.configureTlsExtensions(var1, var2, var3);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var3: java.lang.String;
      if (var1 is BCSSLSocket) {
         val var2: java.lang.String = (var1 as BCSSLSocket).getApplicationProtocol();
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
      val var1: SSLContext = SSLContext.getInstance("TLS", this.provider);
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLS\", provider)");
      return var1;
   }

   public override fun platformTrustManager(): X509TrustManager {
      val var4: TrustManagerFactory = TrustManagerFactory.getInstance("PKIX", "BCJSSE");
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
      throw (new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported with BouncyCastle")) as java.lang.Throwable;
   }

   public companion object {
      public final val isSupported: Boolean

      public fun buildIfSupported(): BouncyCastlePlatform? {
         val var2: BouncyCastlePlatform.Companion = this;
         val var1: Boolean = this.isSupported();
         var var3: BouncyCastlePlatform = null;
         if (var1) {
            var3 = new BouncyCastlePlatform(null);
         }

         return var3;
      }
   }
}
