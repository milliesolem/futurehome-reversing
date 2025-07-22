package okhttp3.internal.tls

import java.security.cert.CertificateParsingException
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Locale
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLException
import javax.net.ssl.SSLSession
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.HostnamesKt
import okhttp3.internal.Util

public object OkHostnameVerifier : HostnameVerifier {
   private const val ALT_DNS_NAME: Int = 2
   private const val ALT_IPA_NAME: Int = 7

   private fun getSubjectAltNames(certificate: X509Certificate, type: Int): List<String> {
      var var3: java.util.Collection;
      try {
         var3 = var1.getSubjectAlternativeNames();
      } catch (var10: CertificateParsingException) {
         return CollectionsKt.emptyList();
      }

      if (var3 == null) {
         try {
            return CollectionsKt.emptyList();
         } catch (var9: CertificateParsingException) {
            return CollectionsKt.emptyList();
         }
      } else {
         try {
            var15 = new ArrayList();
            var17 = var3.iterator();
         } catch (var8: CertificateParsingException) {
            return CollectionsKt.emptyList();
         }

         while (true) {
            var var4: java.util.List;
            try {
               if (!var17.hasNext()) {
                  return var15;
               }

               var4 = var17.next() as java.util.List;
            } catch (var13: CertificateParsingException) {
               return CollectionsKt.emptyList();
            }

            if (var4 != null) {
               try {
                  if (var4.size() < 2) {
                     continue;
                  }
               } catch (var12: CertificateParsingException) {
                  return CollectionsKt.emptyList();
               }

               try {
                  if (!(var4.get(0) == var2)) {
                     continue;
                  }
               } catch (var11: CertificateParsingException) {
                  return CollectionsKt.emptyList();
               }

               try {
                  var18 = var4.get(1);
               } catch (var7: CertificateParsingException) {
                  return CollectionsKt.emptyList();
               }

               if (var18 != null) {
                  if (var18 == null) {
                     try {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                     } catch (var5: CertificateParsingException) {
                        return CollectionsKt.emptyList();
                     }
                  }

                  try {
                     var15.add(var18 as java.lang.String);
                  } catch (var6: CertificateParsingException) {
                     return CollectionsKt.emptyList();
                  }
               }
            }
         }
      }
   }

   private fun verifyHostname(hostname: String?, pattern: String?): Boolean {
      label70:
      if (var1 != null
         && var1.length() != 0
         && !StringsKt.startsWith$default(var1, ".", false, 2, null)
         && !StringsKt.endsWith$default(var1, "..", false, 2, null)
         && var2 != null
         && var2.length() != 0
         && !StringsKt.startsWith$default(var2, ".", false, 2, null)
         && !StringsKt.endsWith$default(var2, "..", false, 2, null)) {
         var var12: java.lang.String = var1;
         if (!StringsKt.endsWith$default(var1, ".", false, 2, null)) {
            val var13: StringBuilder = new StringBuilder();
            var13.append(var1);
            var13.append(".");
            var12 = var13.toString();
         }

         var1 = var2;
         if (!StringsKt.endsWith$default(var2, ".", false, 2, null)) {
            val var6: StringBuilder = new StringBuilder();
            var6.append(var2);
            var6.append(".");
            var1 = var6.toString();
         }

         val var9: Locale = Locale.US;
         Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
         if (var1 != null) {
            var1 = var1.toLowerCase(var9);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).toLowerCase(locale)");
            val var10: java.lang.CharSequence = var1;
            if (!StringsKt.contains$default(var1, "*", false, 2, null)) {
               return var12 == var1;
            } else if (!StringsKt.startsWith$default(var1, "*.", false, 2, null) || StringsKt.indexOf$default(var10, '*', 1, false, 4, null) != -1) {
               return false;
            } else if (var12.length() < var1.length()) {
               return false;
            } else if ("*." == var1) {
               return false;
            } else if (var1 != null) {
               var1 = var1.substring(1);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               if (!StringsKt.endsWith$default(var12, var1, false, 2, null)) {
                  return false;
               } else {
                  val var3: Int = var12.length() - var1.length();
                  return var3 <= 0 || StringsKt.lastIndexOf$default(var12, '.', var3 - 1, false, 4, null) == -1;
               }
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      } else {
         return false;
      }
   }

   private fun verifyHostname(hostname: String, certificate: X509Certificate): Boolean {
      val var5: Locale = Locale.US;
      Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var1 = var1.toLowerCase(var5);
         Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).toLowerCase(locale)");
         val var7: java.lang.Iterable = this.getSubjectAltNames(var2, 2);
         var var3: Boolean = var7 is java.util.Collection;
         val var4: Boolean = false;
         if (var3 && (var7 as java.util.Collection).isEmpty()) {
            var3 = false;
         } else {
            val var8: java.util.Iterator = var7.iterator();

            while (true) {
               var3 = var4;
               if (!var8.hasNext()) {
                  break;
               }

               if (INSTANCE.verifyHostname(var1, var8.next() as java.lang.String)) {
                  var3 = true;
                  break;
               }
            }
         }

         return var3;
      }
   }

   private fun verifyIpAddress(ipAddress: String, certificate: X509Certificate): Boolean {
      var1 = HostnamesKt.toCanonicalHost(var1);
      val var6: java.lang.Iterable = this.getSubjectAltNames(var2, 7);
      var var3: Boolean = var6 is java.util.Collection;
      val var4: Boolean = false;
      if (var3 && (var6 as java.util.Collection).isEmpty()) {
         var3 = false;
      } else {
         val var7: java.util.Iterator = var6.iterator();

         while (true) {
            var3 = var4;
            if (!var7.hasNext()) {
               break;
            }

            if (var1 == HostnamesKt.toCanonicalHost(var7.next() as java.lang.String)) {
               var3 = true;
               break;
            }
         }
      }

      return var3;
   }

   public fun allSubjectAltNames(certificate: X509Certificate): List<String> {
      Intrinsics.checkParameterIsNotNull(var1, "certificate");
      return CollectionsKt.plus(this.getSubjectAltNames(var1, 7), this.getSubjectAltNames(var1, 2));
   }

   public fun verify(host: String, certificate: X509Certificate): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "host");
      Intrinsics.checkParameterIsNotNull(var2, "certificate");
      val var3: Boolean;
      if (Util.canParseAsIpAddress(var1)) {
         var3 = this.verifyIpAddress(var1, var2);
      } else {
         var3 = this.verifyHostname(var1, var2);
      }

      return var3;
   }

   public override fun verify(host: String, session: SSLSession): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "host");
      Intrinsics.checkParameterIsNotNull(var2, "session");
      var var3: Boolean = false;

      try {
         var9 = var2.getPeerCertificates()[0];
      } catch (var7: SSLException) {
         return false;
      }

      if (var9 != null) {
         var var4: Boolean;
         try {
            var4 = this.verify(var1, var9 as X509Certificate);
         } catch (var5: SSLException) {
            return false;
         }

         var3 = var4;
      } else {
         try {
            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
         } catch (var6: SSLException) {
         }
      }

      return var3;
   }
}
