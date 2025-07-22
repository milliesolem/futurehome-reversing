package okhttp3

import java.security.Principal
import java.security.PublicKey
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.ArrayList
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.TypeIntrinsics
import okhttp3.internal.HostnamesKt
import okhttp3.internal.tls.CertificateChainCleaner
import okio.ByteString

public class CertificatePinner internal constructor(pins: Set<okhttp3.CertificatePinner.Pin>, certificateChainCleaner: CertificateChainCleaner? = null) {
   internal final val certificateChainCleaner: CertificateChainCleaner?
   public final val pins: Set<okhttp3.CertificatePinner.Pin>

   init {
      Intrinsics.checkParameterIsNotNull(var1, "pins");
      super();
      this.pins = var1;
      this.certificateChainCleaner = var2;
   }

   @Throws(javax/net/ssl/SSLPeerUnverifiedException::class)
   public fun check(hostname: String, peerCertificates: List<Certificate>) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Intrinsics.checkParameterIsNotNull(var2, "peerCertificates");
      this.check$okhttp(var1, (new Function0<java.util.List<? extends X509Certificate>>(this, var2, var1) {
         final java.lang.String $hostname;
         final java.util.List $peerCertificates;
         final CertificatePinner this$0;

         {
            super(0);
            this.this$0 = var1;
            this.$peerCertificates = var2;
            this.$hostname = var3;
         }

         public final java.util.List<X509Certificate> invoke() {
            var var4: java.util.List;
            label23: {
               val var1: CertificateChainCleaner = this.this$0.getCertificateChainCleaner$okhttp();
               if (var1 != null) {
                  var4 = var1.clean(this.$peerCertificates, this.$hostname);
                  if (var4 != null) {
                     break label23;
                  }
               }

               var4 = this.$peerCertificates;
            }

            val var2: java.lang.Iterable = var4;
            val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

            for (Certificate var6 : var2) {
               if (var6 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
               }

               var5.add(var6 as X509Certificate);
            }

            return var5 as MutableList<X509Certificate>;
         }
      }) as () -> MutableList<X509Certificate>);
   }

   @Deprecated(message = "replaced with {@link #check(String, List)}.", replaceWith = @ReplaceWith(expression = "check(hostname, peerCertificates.toList())", imports = []))
   @Throws(javax/net/ssl/SSLPeerUnverifiedException::class)
   public fun check(hostname: String, vararg peerCertificates: Certificate) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Intrinsics.checkParameterIsNotNull(var2, "peerCertificates");
      this.check(var1, ArraysKt.toList(var2));
   }

   internal fun check(hostname: String, cleanedPeerCertificatesFn: () -> List<X509Certificate>) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Intrinsics.checkParameterIsNotNull(var2, "cleanedPeerCertificatesFn");
      val var6: java.util.List = this.findMatchingPins(var1);
      if (!var6.isEmpty()) {
         val var7: java.util.List = var2.invoke() as java.util.List;
         val var8: java.util.Iterator = var7.iterator();

         val var11: CertificatePinner.Pin;
         label64:
         while (true) {
            if (!var8.hasNext()) {
               val var17: StringBuilder = new StringBuilder("Certificate pinning failure!\n  Peer certificate chain:");

               for (X509Certificate var22 : var7) {
                  var17.append("\n    ");
                  var17.append(Companion.pin(var22));
                  var17.append(": ");
                  val var23: Principal = var22.getSubjectDN();
                  Intrinsics.checkExpressionValueIsNotNull(var23, "element.subjectDN");
                  var17.append(var23.getName());
               }

               var17.append("\n  Pinned certificates for ");
               var17.append(var1);
               var17.append(":");

               for (CertificatePinner.Pin var13 : var6) {
                  var17.append("\n    ");
                  var17.append(var13);
               }

               var1 = var17.toString();
               Intrinsics.checkExpressionValueIsNotNull(var1, "StringBuilder().apply(builderAction).toString()");
               throw (new SSLPeerUnverifiedException(var1)) as java.lang.Throwable;
            }

            val var9: X509Certificate = var8.next() as X509Certificate;
            var var4: ByteString = null;
            val var15: ByteString = null as ByteString;
            val var10: java.util.Iterator = var6.iterator();
            var var16: ByteString = null;

            while (var10.hasNext()) {
               var11 = var10.next() as CertificatePinner.Pin;
               val var5: java.lang.String = var11.getHashAlgorithm();
               val var3: Int = var5.hashCode();
               if (var3 != -903629273) {
                  if (var3 != 3528965 || !var5.equals("sha1")) {
                     break label64;
                  }

                  var var20: ByteString = var16;
                  if (var16 == null) {
                     var20 = Companion.sha1Hash(var9);
                  }

                  var16 = var20;
                  if (var11.getHash() == var20) {
                     return;
                  }
               } else {
                  if (!var5.equals("sha256")) {
                     break label64;
                  }

                  var var21: ByteString = var4;
                  if (var4 == null) {
                     var21 = Companion.sha256Hash(var9);
                  }

                  var4 = var21;
                  if (var11.getHash() == var21) {
                     return;
                  }
               }
            }
         }

         val var12: StringBuilder = new StringBuilder("unsupported hashAlgorithm: ");
         var12.append(var11.getHashAlgorithm());
         throw (new AssertionError(var12.toString())) as java.lang.Throwable;
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is CertificatePinner
         && (var1 as CertificatePinner).pins == this.pins
         && (var1 as CertificatePinner).certificateChainCleaner == this.certificateChainCleaner;
   }

   public fun findMatchingPins(hostname: String): List<okhttp3.CertificatePinner.Pin> {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      val var3: java.lang.Iterable = this.pins;
      var var2: java.util.List = CollectionsKt.emptyList();

      for (Object var5 : var3) {
         if ((var5 as CertificatePinner.Pin).matchesHostname(var1)) {
            var var6: java.util.List = var2;
            if (var2.isEmpty()) {
               var6 = new ArrayList();
            }

            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
            }

            TypeIntrinsics.asMutableList(var6).add(var5);
            var2 = var6;
         }
      }

      return var2;
   }

   public override fun hashCode(): Int {
      val var2: Int = this.pins.hashCode();
      val var1: Int;
      if (this.certificateChainCleaner != null) {
         var1 = this.certificateChainCleaner.hashCode();
      } else {
         var1 = 0;
      }

      return (1517 + var2) * 41 + var1;
   }

   internal fun withCertificateChainCleaner(certificateChainCleaner: CertificateChainCleaner): CertificatePinner {
      Intrinsics.checkParameterIsNotNull(var1, "certificateChainCleaner");
      val var2: CertificatePinner;
      if (this.certificateChainCleaner == var1) {
         var2 = this;
      } else {
         var2 = new CertificatePinner(this.pins, var1);
      }

      return var2;
   }

   public class Builder {
      public final val pins: MutableList<okhttp3.CertificatePinner.Pin> = (new ArrayList()) as java.util.List

      public fun add(pattern: String, vararg pins: String): okhttp3.CertificatePinner.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "pattern");
         Intrinsics.checkParameterIsNotNull(var2, "pins");
         val var5: CertificatePinner.Builder = this;
         val var4: Int = var2.length;

         for (int var3 = 0; var3 < var4; var3++) {
            this.pins.add(new CertificatePinner.Pin(var1, var2[var3]));
         }

         return this;
      }

      public fun build(): CertificatePinner {
         return new CertificatePinner(CollectionsKt.toSet(this.pins), null, 2, null);
      }
   }

   public companion object {
      public final val DEFAULT: CertificatePinner

      public fun pin(certificate: Certificate): String {
         Intrinsics.checkParameterIsNotNull(var1, "certificate");
         if (var1 is X509Certificate) {
            val var3: StringBuilder = new StringBuilder("sha256/");
            val var2: CertificatePinner.Companion = this;
            var3.append(this.sha256Hash(var1 as X509Certificate).base64());
            return var3.toString();
         } else {
            throw (new IllegalArgumentException("Certificate pinning requires X509 certificates".toString())) as java.lang.Throwable;
         }
      }

      public fun X509Certificate.sha1Hash(): ByteString {
         Intrinsics.checkParameterIsNotNull(var1, "$this$sha1Hash");
         val var2: ByteString.Companion = ByteString.Companion;
         val var3: PublicKey = var1.getPublicKey();
         Intrinsics.checkExpressionValueIsNotNull(var3, "publicKey");
         val var4: ByteArray = var3.getEncoded();
         Intrinsics.checkExpressionValueIsNotNull(var4, "publicKey.encoded");
         return ByteString.Companion.of$default(var2, var4, 0, 0, 3, null).sha1();
      }

      public fun X509Certificate.sha256Hash(): ByteString {
         Intrinsics.checkParameterIsNotNull(var1, "$this$sha256Hash");
         val var2: ByteString.Companion = ByteString.Companion;
         val var3: PublicKey = var1.getPublicKey();
         Intrinsics.checkExpressionValueIsNotNull(var3, "publicKey");
         val var4: ByteArray = var3.getEncoded();
         Intrinsics.checkExpressionValueIsNotNull(var4, "publicKey.encoded");
         return ByteString.Companion.of$default(var2, var4, 0, 0, 3, null).sha256();
      }
   }

   public class Pin(pattern: String, pin: String) {
      public final val hash: ByteString
      public final val hashAlgorithm: String
      public final val pattern: String

      init {
         Intrinsics.checkParameterIsNotNull(var1, "pattern");
         Intrinsics.checkParameterIsNotNull(var2, "pin");
         super();
         val var3: Boolean;
         if ((!StringsKt.startsWith$default(var1, "*.", false, 2, null) || StringsKt.indexOf$default(var1, "*", 1, false, 4, null) != -1)
            && (!StringsKt.startsWith$default(var1, "**.", false, 2, null) || StringsKt.indexOf$default(var1, "*", 2, false, 4, null) != -1)
            && StringsKt.indexOf$default(var1, "*", 0, false, 6, null) != -1) {
            var3 = false;
         } else {
            var3 = true;
         }

         if (var3) {
            var var4: java.lang.String = HostnamesKt.toCanonicalHost(var1);
            if (var4 != null) {
               this.pattern = var4;
               if (StringsKt.startsWith$default(var2, "sha1/", false, 2, null)) {
                  this.hashAlgorithm = "sha1";
                  val var5: ByteString.Companion = ByteString.Companion;
                  var4 = var2.substring(5);
                  Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.String).substring(startIndex)");
                  val var6: ByteString = var5.decodeBase64(var4);
                  if (var6 == null) {
                     val var7: StringBuilder = new StringBuilder("Invalid pin hash: ");
                     var7.append(var2);
                     throw (new IllegalArgumentException(var7.toString())) as java.lang.Throwable;
                  }

                  this.hash = var6;
               } else {
                  if (!StringsKt.startsWith$default(var2, "sha256/", false, 2, null)) {
                     val var11: StringBuilder = new StringBuilder("pins must start with 'sha256/' or 'sha1/': ");
                     var11.append(var2);
                     throw (new IllegalArgumentException(var11.toString())) as java.lang.Throwable;
                  }

                  this.hashAlgorithm = "sha256";
                  val var15: ByteString.Companion = ByteString.Companion;
                  var1 = var2.substring(7);
                  Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
                  val var9: ByteString = var15.decodeBase64(var1);
                  if (var9 == null) {
                     val var10: StringBuilder = new StringBuilder("Invalid pin hash: ");
                     var10.append(var2);
                     throw (new IllegalArgumentException(var10.toString())) as java.lang.Throwable;
                  }

                  this.hash = var9;
               }
            } else {
               val var13: StringBuilder = new StringBuilder("Invalid pattern: ");
               var13.append(var1);
               throw (new IllegalArgumentException(var13.toString())) as java.lang.Throwable;
            }
         } else {
            val var12: StringBuilder = new StringBuilder("Unexpected pattern: ");
            var12.append(var1);
            throw (new IllegalArgumentException(var12.toString().toString())) as java.lang.Throwable;
         }
      }

      public override operator fun equals(other: Any?): Boolean {
         val var2: CertificatePinner.Pin = this;
         if (this === var1) {
            return true;
         } else if (var1 !is CertificatePinner.Pin) {
            return false;
         } else {
            var1 = var1;
            if (!(this.pattern == var1.pattern)) {
               return false;
            } else if (!(this.hashAlgorithm == var1.hashAlgorithm)) {
               return false;
            } else {
               return this.hash == var1.hash;
            }
         }
      }

      public override fun hashCode(): Int {
         return (this.pattern.hashCode() * 31 + this.hashAlgorithm.hashCode()) * 31 + this.hash.hashCode();
      }

      public fun matchesCertificate(certificate: X509Certificate): Boolean {
         Intrinsics.checkParameterIsNotNull(var1, "certificate");
         val var4: java.lang.String = this.hashAlgorithm;
         val var2: Int = this.hashAlgorithm.hashCode();
         if (var2 != -903629273) {
            if (var2 == 3528965 && var4.equals("sha1")) {
               return this.hash == CertificatePinner.Companion.sha1Hash(var1);
            }
         } else if (var4.equals("sha256")) {
            return this.hash == CertificatePinner.Companion.sha256Hash(var1);
         }

         return false;
      }

      public fun matchesHostname(hostname: String): Boolean {
         Intrinsics.checkParameterIsNotNull(var1, "hostname");
         if (StringsKt.startsWith$default(this.pattern, "**.", false, 2, null)) {
            val var2: Int = this.pattern.length() - 3;
            val var3: Int = var1.length() - var2;
            if (!StringsKt.regionMatches$default(var1, var1.length() - var2, this.pattern, 3, var2, false, 16, null)) {
               return false;
            }

            if (var3 != 0 && var1.charAt(var3 - 1) != '.') {
               return false;
            }
         } else {
            if (!StringsKt.startsWith$default(this.pattern, "*.", false, 2, null)) {
               return var1 == this.pattern;
            }

            val var7: Int = this.pattern.length() - 1;
            val var8: Int = var1.length();
            if (!StringsKt.regionMatches$default(var1, var1.length() - var7, this.pattern, 1, var7, false, 16, null)) {
               return false;
            }

            if (StringsKt.lastIndexOf$default(var1, '.', var8 - var7 - 1, false, 4, null) != -1) {
               return false;
            }
         }

         return true;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder();
         var1.append(this.hashAlgorithm);
         var1.append('/');
         var1.append(this.hash.base64());
         return var1.toString();
      }
   }
}
