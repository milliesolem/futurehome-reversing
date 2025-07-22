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
import org.conscrypt.Conscrypt
import org.conscrypt.Conscrypt.Version

public class ConscryptPlatform private constructor() : Platform {
   private final val provider: Provider

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @JvmStatic
   fun {
      val var3: ConscryptPlatform.Companion = new ConscryptPlatform.Companion(null);
      Companion = var3;

      var var0: Boolean;
      label30: {
         try {
            Class.forName("org.conscrypt.Conscrypt$Version", false, var3.getClass().getClassLoader());
         } catch (ClassNotFoundException | var5: NoClassDefFoundError) {
            var0 = false;
            break label30;
         }

         var0 = false;

         var var2: Boolean;
         try {
            if (!Conscrypt.isAvailable()) {
               break label30;
            }

            var2 = var3.atLeastVersion(2, 1, 0);
         } catch (ClassNotFoundException | var4: NoClassDefFoundError) {
            var0 = false;
            break label30;
         }

         var0 = false;
         if (var2) {
            var0 = true;
         }
      }

      isSupported = var0;
   }

   init {
      val var1: Provider = Conscrypt.newProviderBuilder().provideTrustManager(true).build();
      Intrinsics.checkExpressionValueIsNotNull(var1, "Conscrypt.newProviderBui…rustManager(true).build()");
      this.provider = var1;
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (Conscrypt.isConscrypt(var1)) {
         Conscrypt.setUseSessionTickets(var1, true);
         val var4: Array<Any> = Platform.Companion.alpnProtocolNames(var3).toArray(new java.lang.String[0]);
         if (var4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         Conscrypt.setApplicationProtocols(var1, var4 as Array<java.lang.String>);
      } else {
         super.configureTlsExtensions(var1, var2, var3);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var2: java.lang.String;
      if (Conscrypt.isConscrypt(var1)) {
         var2 = Conscrypt.getApplicationProtocol(var1);
      } else {
         var2 = super.getSelectedProtocol(var1);
      }

      return var2;
   }

   public override fun newSSLContext(): SSLContext {
      val var1: SSLContext = SSLContext.getInstance("TLS", this.provider);
      Intrinsics.checkExpressionValueIsNotNull(var1, "SSLContext.getInstance(\"TLS\", provider)");
      return var1;
   }

   public override fun newSslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      val var2: SSLContext = this.newSSLContext();
      var2.init(null, new TrustManager[]{var1}, null);
      val var3: SSLSocketFactory = var2.getSocketFactory();
      Conscrypt.setUseEngineSocket(var3, true);
      Intrinsics.checkExpressionValueIsNotNull(var3, "newSSLContext().apply {\n…ineSocket(it, true)\n    }");
      return var3;
   }

   public override fun platformTrustManager(): X509TrustManager {
      val var4: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      val var3: KeyStore = null as KeyStore;
      var4.init(null);
      Intrinsics.checkExpressionValueIsNotNull(var4, "TrustManagerFactory.getI…(null as KeyStore?)\n    }");
      val var8: Array<TrustManager> = var4.getTrustManagers();
      if (var8 == null) {
         Intrinsics.throwNpe();
      }

      val var2: Int = var8.length;
      var var1: Boolean = true;
      if (var2 != 1 || var8[0] !is X509TrustManager) {
         var1 = false;
      }

      if (var1) {
         val var6: TrustManager = var8[0];
         if (var8[0] != null) {
            val var7: X509TrustManager = var6 as X509TrustManager;
            Conscrypt.setHostnameVerifier(var6 as X509TrustManager, <unrepresentable>.INSTANCE);
            return var7;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
         }
      } else {
         val var5: StringBuilder = new StringBuilder("Unexpected default trust managers: ");
         val var9: java.lang.String = Arrays.toString((Object[])var8);
         Intrinsics.checkExpressionValueIsNotNull(var9, "java.util.Arrays.toString(this)");
         var5.append(var9);
         throw (new IllegalStateException(var5.toString().toString())) as java.lang.Throwable;
      }
   }

   public override fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return null;
   }

   public companion object {
      public final val isSupported: Boolean

      public fun atLeastVersion(major: Int, minor: Int = 0, patch: Int = 0): Boolean {
         val var8: Version = Conscrypt.version();
         val var4: Int = var8.major();
         var var5: Boolean = true;
         if (var4 != var1) {
            if (var8.major() <= var1) {
               var5 = false;
            }

            return var5;
         } else if (var8.minor() != var2) {
            if (var8.minor() > var2) {
               var5 = true;
            } else {
               var5 = false;
            }

            return var5;
         } else {
            if (var8.patch() >= var3) {
               var5 = true;
            } else {
               var5 = false;
            }

            return var5;
         }
      }

      public fun buildIfSupported(): ConscryptPlatform? {
         val var2: ConscryptPlatform.Companion = this;
         val var1: Boolean = this.isSupported();
         var var3: ConscryptPlatform = null;
         if (var1) {
            var3 = new ConscryptPlatform(null);
         }

         return var3;
      }
   }
}
