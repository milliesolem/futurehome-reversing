package okhttp3.internal.tls

import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.Arrays
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.platform.Platform

public abstract class CertificateChainCleaner {
   @Throws(javax/net/ssl/SSLPeerUnverifiedException::class)
   public abstract fun clean(chain: List<Certificate>, hostname: String): List<Certificate> {
   }

   public companion object {
      public fun get(trustManager: X509TrustManager): CertificateChainCleaner {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         return Platform.Companion.get().buildCertificateChainCleaner(var1);
      }

      public fun get(vararg caCerts: X509Certificate): CertificateChainCleaner {
         Intrinsics.checkParameterIsNotNull(var1, "caCerts");
         return new BasicCertificateChainCleaner(new BasicTrustRootIndex(Arrays.copyOf(var1, var1.length)));
      }
   }
}
