package okhttp3

import java.io.IOException
import java.security.Principal
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Arrays
import javax.net.ssl.SSLPeerUnverifiedException
import javax.net.ssl.SSLSession
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class Handshake internal constructor(tlsVersion: TlsVersion,
   cipherSuite: CipherSuite,
   localCertificates: List<Certificate>,
   peerCertificatesFn: () -> List<Certificate>
) {
   public final val cipherSuite: CipherSuite
   public final val localCertificates: List<Certificate>

   public final val localPrincipal: Principal?
      public final get() {
         var var2: Any = CollectionsKt.firstOrNull(this.localCertificates);
         if (var2 !is X509Certificate) {
            var2 = null;
         }

         val var4: X509Certificate = var2 as X509Certificate;
         var2 = null;
         if (var4 != null) {
            var2 = var4.getSubjectX500Principal();
         }

         return var2 as Principal;
      }


   public final val peerCertificates: List<Certificate>
      public final get() {
         return this.peerCertificates$delegate.getValue() as MutableList<Certificate>;
      }


   public final val peerPrincipal: Principal?
      public final get() {
         var var2: Any = CollectionsKt.firstOrNull(this.peerCertificates());
         if (var2 !is X509Certificate) {
            var2 = null;
         }

         val var4: X509Certificate = var2 as X509Certificate;
         var2 = null;
         if (var4 != null) {
            var2 = var4.getSubjectX500Principal();
         }

         return var2 as Principal;
      }


   public final val tlsVersion: TlsVersion

   private final val name: String
      private final get() {
         val var2: java.lang.String;
         if (var1 is X509Certificate) {
            var2 = (var1 as X509Certificate).getSubjectDN().toString();
         } else {
            var2 = var1.getType();
            Intrinsics.checkExpressionValueIsNotNull(var2, "type");
         }

         return var2;
      }


   init {
      Intrinsics.checkParameterIsNotNull(var1, "tlsVersion");
      Intrinsics.checkParameterIsNotNull(var2, "cipherSuite");
      Intrinsics.checkParameterIsNotNull(var3, "localCertificates");
      Intrinsics.checkParameterIsNotNull(var4, "peerCertificatesFn");
      super();
      this.tlsVersion = var1;
      this.cipherSuite = var2;
      this.localCertificates = var3;
      this.peerCertificates$delegate = LazyKt.lazy((new Function0<java.util.List<? extends Certificate>>(var4) {
         final Function0 $peerCertificatesFn;

         {
            super(0);
            this.$peerCertificatesFn = var1;
         }

         public final java.util.List<Certificate> invoke() {
            var var1: java.util.List;
            try {
               var1 = this.$peerCertificatesFn.invoke() as java.util.List;
            } catch (var2: SSLPeerUnverifiedException) {
               var1 = CollectionsKt.emptyList();
            }

            return var1;
         }
      }) as Function0);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cipherSuite", imports = []))
   public fun cipherSuite(): CipherSuite {
      return this.cipherSuite;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "localCertificates", imports = []))
   public fun localCertificates(): List<Certificate> {
      return this.localCertificates;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "localPrincipal", imports = []))
   public fun localPrincipal(): Principal? {
      return this.localPrincipal();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "peerCertificates", imports = []))
   public fun peerCertificates(): List<Certificate> {
      return this.peerCertificates();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "peerPrincipal", imports = []))
   public fun peerPrincipal(): Principal? {
      return this.peerPrincipal();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "tlsVersion", imports = []))
   public fun tlsVersion(): TlsVersion {
      return this.tlsVersion;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is Handshake
         && (var1 as Handshake).tlsVersion === this.tlsVersion
         && (var1 as Handshake).cipherSuite == this.cipherSuite
         && (var1 as Handshake).peerCertificates() == this.peerCertificates()
         && (var1 as Handshake).localCertificates == this.localCertificates;
   }

   public override fun hashCode(): Int {
      return (((527 + this.tlsVersion.hashCode()) * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates().hashCode()) * 31
         + this.localCertificates.hashCode();
   }

   public override fun toString(): String {
      val var2: java.lang.Iterable = this.peerCertificates();
      val var1: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
      val var5: java.util.Iterator = var2.iterator();

      while (var5.hasNext()) {
         var1.add(this.getName(var5.next() as Certificate));
      }

      val var6: java.lang.String = (var1 as java.util.List).toString();
      val var4: StringBuilder = new StringBuilder("Handshake{tlsVersion=");
      var4.append(this.tlsVersion);
      var4.append(" cipherSuite=");
      var4.append(this.cipherSuite);
      var4.append(" peerCertificates=");
      var4.append(var6);
      var4.append(" localCertificates=");
      val var3: java.lang.Iterable = this.localCertificates;
      val var7: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.localCertificates, 10));
      val var8: java.util.Iterator = var3.iterator();

      while (var8.hasNext()) {
         var7.add(this.getName(var8.next() as Certificate));
      }

      var4.append(var7 as java.util.List);
      var4.append('}');
      return var4.toString();
   }

   public companion object {
      private fun Array<out Certificate>?.toImmutableList(): List<Certificate> {
         val var2: java.util.List;
         if (var1 != null) {
            var2 = Util.immutableListOf(Arrays.copyOf(var1, var1.length));
         } else {
            var2 = CollectionsKt.emptyList();
         }

         return var2;
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "sslSession.handshake()", imports = []))
      @Throws(java/io/IOException::class)
      public fun get(sslSession: SSLSession): Handshake {
         Intrinsics.checkParameterIsNotNull(var1, "sslSession");
         val var2: Handshake.Companion = this;
         return this.get(var1);
      }

      @Throws(java/io/IOException::class)
      public fun SSLSession.handshake(): Handshake {
         Intrinsics.checkParameterIsNotNull(var1, "$this$handshake");
         var var3: java.lang.String = var1.getCipherSuite();
         if (var3 == null) {
            throw (new IllegalStateException("cipherSuite == null".toString())) as java.lang.Throwable;
         } else {
            val var2: Int = var3.hashCode();
            if (if (var2 != 1019404634) var2 != 1208658923 || !var3.equals("SSL_NULL_WITH_NULL_NULL") else !var3.equals("TLS_NULL_WITH_NULL_NULL")) {
               val var4: CipherSuite = CipherSuite.Companion.forJavaName(var3);
               var3 = var1.getProtocol();
               if (var3 == null) {
                  throw (new IllegalStateException("tlsVersion == null".toString())) as java.lang.Throwable;
               } else if (!("NONE" == var3)) {
                  try {
                     val var11: Handshake.Companion = this;
                     var10 = this.toImmutableList(var1.getPeerCertificates());
                  } catch (var7: SSLPeerUnverifiedException) {
                     var10 = CollectionsKt.emptyList();
                  }

                  val var6: Handshake.Companion = this;
                  return new Handshake(
                     TlsVersion.Companion.forJavaName(var3),
                     var4,
                     this.toImmutableList(var1.getLocalCertificates()),
                     (new Function0<java.util.List<? extends Certificate>>(var10) {
                        final java.util.List $peerCertificatesCopy;

                        {
                           super(0);
                           this.$peerCertificatesCopy = var1;
                        }

                        public final java.util.List<Certificate> invoke() {
                           return this.$peerCertificatesCopy;
                        }
                     }) as () -> MutableList<Certificate>
                  );
               } else {
                  throw (new IOException("tlsVersion == NONE")) as java.lang.Throwable;
               }
            } else {
               val var8: StringBuilder = new StringBuilder("cipherSuite == ");
               var8.append(var3);
               throw (new IOException(var8.toString())) as java.lang.Throwable;
            }
         }
      }

      public fun get(tlsVersion: TlsVersion, cipherSuite: CipherSuite, peerCertificates: List<Certificate>, localCertificates: List<Certificate>): Handshake {
         Intrinsics.checkParameterIsNotNull(var1, "tlsVersion");
         Intrinsics.checkParameterIsNotNull(var2, "cipherSuite");
         Intrinsics.checkParameterIsNotNull(var3, "peerCertificates");
         Intrinsics.checkParameterIsNotNull(var4, "localCertificates");
         return new Handshake(var1, var2, Util.toImmutableList(var4), (new Function0<java.util.List<? extends Certificate>>(Util.toImmutableList(var3)) {
            final java.util.List $peerCertificatesCopy;

            {
               super(0);
               this.$peerCertificatesCopy = var1;
            }

            public final java.util.List<Certificate> invoke() {
               return this.$peerCertificatesCopy;
            }
         }) as () -> MutableList<Certificate>);
      }
   }
}
