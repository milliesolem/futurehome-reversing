package okhttp3.internal.tls

import java.security.cert.X509Certificate
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import javax.security.auth.x500.X500Principal
import kotlin.jvm.internal.Intrinsics

public class BasicTrustRootIndex(vararg caCerts: X509Certificate) : TrustRootIndex {
   private final val subjectToCaCerts: Map<X500Principal, Set<X509Certificate>>

   init {
      Intrinsics.checkParameterIsNotNull(var1, "caCerts");
      super();
      val var8: java.util.Map = new LinkedHashMap();
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var7: X509Certificate = var1[var2];
         val var6: X500Principal = var1[var2].getSubjectX500Principal();
         Intrinsics.checkExpressionValueIsNotNull(var6, "caCert.subjectX500Principal");
         val var5: Any = var8.get(var6);
         var var4: Any = var5;
         if (var5 == null) {
            var4 = new LinkedHashSet();
            var8.put(var6, var4);
         }

         (var4 as java.util.Set).add(var7);
      }

      this.subjectToCaCerts = var8;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var3: BasicTrustRootIndex = this;
      val var2: Boolean;
      if (var1 === this || var1 is BasicTrustRootIndex && (var1 as BasicTrustRootIndex).subjectToCaCerts == this.subjectToCaCerts) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun findByIssuerAndSignature(cert: X509Certificate): X509Certificate? {
      Intrinsics.checkParameterIsNotNull(var1, "cert");
      val var4: java.util.Set = this.subjectToCaCerts.get(var1.getIssuerX500Principal());
      var var7: X509Certificate = null;
      val var3: Any = null;
      if (var4 != null) {
         val var9: java.util.Iterator = var4.iterator();

         while (true) {
            var8 = var3;
            if (!var9.hasNext()) {
               break;
            }

            var8 = var9.next();
            val var5: X509Certificate = var8 as X509Certificate;

            try {
               var1.verify(var5.getPublicKey());
               break;
            } catch (var6: Exception) {
            }
         }

         var7 = var8 as X509Certificate;
      }

      return var7;
   }

   public override fun hashCode(): Int {
      return this.subjectToCaCerts.hashCode();
   }
}
