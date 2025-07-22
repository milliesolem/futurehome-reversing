package okhttp3.internal.tls

import java.security.GeneralSecurityException
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.ArrayDeque
import java.util.ArrayList
import java.util.Deque
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.jvm.internal.Intrinsics

public class BasicCertificateChainCleaner(trustRootIndex: TrustRootIndex) : CertificateChainCleaner {
   private final val trustRootIndex: TrustRootIndex

   init {
      Intrinsics.checkParameterIsNotNull(var1, "trustRootIndex");
      super();
      this.trustRootIndex = var1;
   }

   private fun verifySignature(toVerify: X509Certificate, signingCert: X509Certificate): Boolean {
      if (!(var1.getIssuerDN() == var2.getSubjectDN())) {
         return false;
      } else {
         try {
            var1.verify(var2.getPublicKey());
         } catch (var5: GeneralSecurityException) {
            return false;
         }

         return true;
      }
   }

   @Throws(javax/net/ssl/SSLPeerUnverifiedException::class)
   public override fun clean(chain: List<Certificate>, hostname: String): List<Certificate> {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      Intrinsics.checkParameterIsNotNull(var2, "hostname");
      val var10: Deque = new ArrayDeque(var1);
      var1 = new ArrayList();
      var var5: Any = var10.removeFirst();
      Intrinsics.checkExpressionValueIsNotNull(var5, "queue.removeFirst()");
      var1.add(var5);
      var var3: Int = 0;

      for (boolean var4 = false; var3 < 9; var3++) {
         var5 = var1.get(var1.size() - 1);
         if (var5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
         }

         val var13: X509Certificate = var5 as X509Certificate;
         val var6: X509Certificate = this.trustRootIndex.findByIssuerAndSignature(var5 as X509Certificate);
         if (var6 == null) {
            val var14: java.util.Iterator = var10.iterator();
            Intrinsics.checkExpressionValueIsNotNull(var14, "queue.iterator()");

            val var7: Any;
            do {
               if (!var14.hasNext()) {
                  if (var4) {
                     return var1;
                  }

                  val var9: StringBuilder = new StringBuilder("Failed to find a trusted cert that signed ");
                  var9.append(var13);
                  throw (new SSLPeerUnverifiedException(var9.toString())) as java.lang.Throwable;
               }

               var7 = var14.next();
               if (var7 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
               }
            } while (!this.verifySignature(var13, (X509Certificate)var7));

            var14.remove();
            var1.add(var7 as X509Certificate);
         } else {
            if (var1.size() > 1 || !(var13 == var6)) {
               var1.add(var6);
            }

            if (this.verifySignature(var6, var6)) {
               return var1;
            }

            var4 = true;
         }
      }

      val var11: StringBuilder = new StringBuilder("Certificate chain too long: ");
      var11.append(var1);
      throw (new SSLPeerUnverifiedException(var11.toString())) as java.lang.Throwable;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var3: BasicCertificateChainCleaner = this;
      var var2: Boolean = true;
      if (var1 != this && (var1 !is BasicCertificateChainCleaner || !((var1 as BasicCertificateChainCleaner).trustRootIndex == this.trustRootIndex))) {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return this.trustRootIndex.hashCode();
   }

   public companion object {
      private const val MAX_SIGNERS: Int
   }
}
