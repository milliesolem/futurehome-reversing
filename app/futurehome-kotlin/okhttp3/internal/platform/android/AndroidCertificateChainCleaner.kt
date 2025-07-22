package okhttp3.internal.platform.android

import android.net.http.X509TrustManagerExtensions
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLPeerUnverifiedException
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.tls.CertificateChainCleaner

internal class AndroidCertificateChainCleaner(trustManager: X509TrustManager, x509TrustManagerExtensions: X509TrustManagerExtensions) : CertificateChainCleaner {
   private final val trustManager: X509TrustManager
   private final val x509TrustManagerExtensions: X509TrustManagerExtensions

   init {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      Intrinsics.checkParameterIsNotNull(var2, "x509TrustManagerExtensions");
      super();
      this.trustManager = var1;
      this.x509TrustManagerExtensions = var2;
   }

   @Throws(javax/net/ssl/SSLPeerUnverifiedException::class)
   public override fun clean(chain: List<Certificate>, hostname: String): List<Certificate> {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      Intrinsics.checkParameterIsNotNull(var2, "hostname");
      val var4: Array<Any> = var1.toArray(new X509Certificate[0]);
      if (var4 != null) {
         val var5: Array<X509Certificate> = var4 as Array<X509Certificate>;

         try {
            var1 = this.x509TrustManagerExtensions.checkServerTrusted(var5, "RSA", var2);
            Intrinsics.checkExpressionValueIsNotNull(var1, "x509TrustManagerExtensio…ficates, \"RSA\", hostname)");
            return var1;
         } catch (var3: CertificateException) {
            val var6: SSLPeerUnverifiedException = new SSLPeerUnverifiedException(var3.getMessage());
            var6.initCause(var3);
            throw var6 as java.lang.Throwable;
         }
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is AndroidCertificateChainCleaner && (var1 as AndroidCertificateChainCleaner).trustManager === this.trustManager) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return System.identityHashCode(this.trustManager);
   }

   public companion object {
      public fun buildIfSupported(trustManager: X509TrustManager): AndroidCertificateChainCleaner? {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         var var3: AndroidCertificateChainCleaner = null;

         var var2: X509TrustManagerExtensions;
         try {
            var2 = new X509TrustManagerExtensions(var1);
         } catch (var4: IllegalArgumentException) {
            var2 = null;
         }

         if (var2 != null) {
            var3 = new AndroidCertificateChainCleaner(var1, var2);
         }

         return var3;
      }
   }
}
