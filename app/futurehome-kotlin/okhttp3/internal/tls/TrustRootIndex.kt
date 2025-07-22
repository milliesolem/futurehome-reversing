package okhttp3.internal.tls

import java.security.cert.X509Certificate

public interface TrustRootIndex {
   public abstract fun findByIssuerAndSignature(cert: X509Certificate): X509Certificate? {
   }
}
