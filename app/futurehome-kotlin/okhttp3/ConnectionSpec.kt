package okhttp3

import java.util.ArrayList
import java.util.Arrays
import java.util.Objects
import javax.net.ssl.SSLSocket
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class ConnectionSpec internal constructor(isTls: Boolean,
   supportsTlsExtensions: Boolean,
   vararg cipherSuitesAsString: Any,
   vararg tlsVersionsAsString: Any
) {
   public final val cipherSuites: List<CipherSuite>?
      public final get() {
         val var4: Array<java.lang.String> = this.cipherSuitesAsString;
         val var6: java.util.List;
         if (this.cipherSuitesAsString != null) {
            val var3: java.util.Collection = new ArrayList(this.cipherSuitesAsString.length);
            val var2: Int = var4.length;

            for (int var1 = 0; var1 < var2; var1++) {
               var3.add(CipherSuite.Companion.forJavaName(var4[var1]));
            }

            var6 = CollectionsKt.toList(var3 as java.util.List);
         } else {
            var6 = null;
         }

         return var6;
      }


   private final val cipherSuitesAsString: Array<String>?
   public final val isTls: Boolean
   public final val supportsTlsExtensions: Boolean

   public final val tlsVersions: List<TlsVersion>?
      public final get() {
         val var3: Array<java.lang.String> = this.tlsVersionsAsString;
         val var6: java.util.List;
         if (this.tlsVersionsAsString != null) {
            val var4: java.util.Collection = new ArrayList(this.tlsVersionsAsString.length);
            val var2: Int = var3.length;

            for (int var1 = 0; var1 < var2; var1++) {
               var4.add(TlsVersion.Companion.forJavaName(var3[var1]));
            }

            var6 = CollectionsKt.toList(var4 as java.util.List);
         } else {
            var6 = null;
         }

         return var6;
      }


   private final val tlsVersionsAsString: Array<String>?

   @JvmStatic
   fun {
      val var0: Array<CipherSuite> = new CipherSuite[]{
         CipherSuite.TLS_AES_128_GCM_SHA256,
         CipherSuite.TLS_AES_256_GCM_SHA384,
         CipherSuite.TLS_CHACHA20_POLY1305_SHA256,
         CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
         CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
         CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
         CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
         CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
         CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256
      };
      RESTRICTED_CIPHER_SUITES = var0;
      val var1: Array<CipherSuite> = new CipherSuite[]{
         CipherSuite.TLS_AES_128_GCM_SHA256,
         CipherSuite.TLS_AES_256_GCM_SHA384,
         CipherSuite.TLS_CHACHA20_POLY1305_SHA256,
         CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
         CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
         CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
         CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
         CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
         CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256,
         CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
         CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
         CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256,
         CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384,
         CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
         CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA,
         CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA
      };
      APPROVED_CIPHER_SUITES = var1;
      RESTRICTED_TLS = new ConnectionSpec.Builder(true)
         .cipherSuites(Arrays.copyOf(var0, var0.length))
         .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2)
         .supportsTlsExtensions(true)
         .build();
      MODERN_TLS = new ConnectionSpec.Builder(true)
         .cipherSuites(Arrays.copyOf(var1, var1.length))
         .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2)
         .supportsTlsExtensions(true)
         .build();
      COMPATIBLE_TLS = new ConnectionSpec.Builder(true)
         .cipherSuites(Arrays.copyOf(var1, var1.length))
         .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
         .supportsTlsExtensions(true)
         .build();
   }

   init {
      this.isTls = var1;
      this.supportsTlsExtensions = var2;
      this.cipherSuitesAsString = var3;
      this.tlsVersionsAsString = var4;
   }

   private fun supportedSpec(sslSocket: SSLSocket, isFallback: Boolean): ConnectionSpec {
      var var10: Array<java.lang.String>;
      if (this.cipherSuitesAsString != null) {
         var10 = var1.getEnabledCipherSuites();
         Intrinsics.checkExpressionValueIsNotNull(var10, "sslSocket.enabledCipherSuites");
         var10 = Util.intersect(var10, this.cipherSuitesAsString, CipherSuite.Companion.getORDER_BY_NAME$okhttp());
      } else {
         var10 = var1.getEnabledCipherSuites();
      }

      var var12: Array<java.lang.String>;
      if (this.tlsVersionsAsString != null) {
         var12 = var1.getEnabledProtocols();
         Intrinsics.checkExpressionValueIsNotNull(var12, "sslSocket.enabledProtocols");
         var12 = Util.intersect(var12, this.tlsVersionsAsString, ComparisonsKt.naturalOrder());
      } else {
         var12 = var1.getEnabledProtocols();
      }

      val var6: Array<java.lang.String> = var1.getSupportedCipherSuites();
      Intrinsics.checkExpressionValueIsNotNull(var6, "supportedCipherSuites");
      val var3: Int = Util.indexOf(var6, "TLS_FALLBACK_SCSV", CipherSuite.Companion.getORDER_BY_NAME$okhttp());
      var var7: Array<java.lang.String> = var10;
      if (var2) {
         var7 = var10;
         if (var3 != -1) {
            Intrinsics.checkExpressionValueIsNotNull(var10, "cipherSuitesIntersection");
            val var8: java.lang.String = var6[var3];
            Intrinsics.checkExpressionValueIsNotNull(var6[var3], "supportedCipherSuites[indexOfFallbackScsv]");
            var7 = Util.concat(var10, var8);
         }
      }

      val var11: ConnectionSpec.Builder = new ConnectionSpec.Builder(this);
      Intrinsics.checkExpressionValueIsNotNull(var7, "cipherSuitesIntersection");
      val var9: ConnectionSpec.Builder = var11.cipherSuites(Arrays.copyOf(var7, var7.length));
      Intrinsics.checkExpressionValueIsNotNull(var12, "tlsVersionsIntersection");
      return var9.tlsVersions(Arrays.copyOf(var12, var12.length)).build();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cipherSuites", imports = []))
   public fun cipherSuites(): List<CipherSuite>? {
      return this.cipherSuites();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "supportsTlsExtensions", imports = []))
   public fun supportsTlsExtensions(): Boolean {
      return this.supportsTlsExtensions;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "tlsVersions", imports = []))
   public fun tlsVersions(): List<TlsVersion>? {
      return this.tlsVersions();
   }

   internal fun apply(sslSocket: SSLSocket, isFallback: Boolean) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var3: ConnectionSpec = this.supportedSpec(var1, var2);
      if (var3.tlsVersions() != null) {
         var1.setEnabledProtocols(var3.tlsVersionsAsString);
      }

      if (var3.cipherSuites() != null) {
         var1.setEnabledCipherSuites(var3.cipherSuitesAsString);
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 !is ConnectionSpec) {
         return false;
      } else {
         val var3: ConnectionSpec = this;
         if (var1 === this) {
            return true;
         } else {
            var1 = var1;
            if (this.isTls != var1.isTls) {
               return false;
            } else {
               if (this.isTls) {
                  if (!Arrays.equals((Object[])this.cipherSuitesAsString, (Object[])var1.cipherSuitesAsString)) {
                     return false;
                  }

                  if (!Arrays.equals((Object[])this.tlsVersionsAsString, (Object[])var1.tlsVersionsAsString)) {
                     return false;
                  }

                  if (this.supportsTlsExtensions != var1.supportsTlsExtensions) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public override fun hashCode(): Int {
      var var4: Int;
      if (this.isTls) {
         var var2: Int = 0;
         if (this.cipherSuitesAsString != null) {
            var4 = Arrays.hashCode((Object[])this.cipherSuitesAsString);
         } else {
            var4 = 0;
         }

         if (this.tlsVersionsAsString != null) {
            var2 = Arrays.hashCode((Object[])this.tlsVersionsAsString);
         }

         var4 = ((527 + var4) * 31 + var2) * 31 + (this.supportsTlsExtensions xor 1);
      } else {
         var4 = 17;
      }

      return var4;
   }

   public fun isCompatible(socket: SSLSocket): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      if (!this.isTls) {
         return false;
      } else if (this.tlsVersionsAsString != null && !Util.hasIntersection(this.tlsVersionsAsString, var1.getEnabledProtocols(), ComparisonsKt.naturalOrder())) {
         return false;
      } else {
         return this.cipherSuitesAsString == null
            || Util.hasIntersection(this.cipherSuitesAsString, var1.getEnabledCipherSuites(), CipherSuite.Companion.getORDER_BY_NAME$okhttp());
      }
   }

   public override fun toString(): String {
      if (!this.isTls) {
         return "ConnectionSpec()";
      } else {
         val var1: StringBuilder = new StringBuilder("ConnectionSpec(cipherSuites=");
         var1.append(Objects.toString(this.cipherSuites(), "[all enabled]"));
         var1.append(", tlsVersions=");
         var1.append(Objects.toString(this.tlsVersions(), "[all enabled]"));
         var1.append(", supportsTlsExtensions=");
         var1.append(this.supportsTlsExtensions);
         var1.append(')');
         return var1.toString();
      }
   }

   public class Builder {
      internal final var cipherSuites: Array<String>?
      internal final var supportsTlsExtensions: Boolean
      internal final var tls: Boolean
      internal final var tlsVersions: Array<String>?

      public constructor(connectionSpec: ConnectionSpec) : Intrinsics.checkParameterIsNotNull(var1, "connectionSpec") {
         super();
         this.tls = var1.isTls();
         this.cipherSuites = ConnectionSpec.access$getCipherSuitesAsString$p(var1);
         this.tlsVersions = ConnectionSpec.access$getTlsVersionsAsString$p(var1);
         this.supportsTlsExtensions = var1.supportsTlsExtensions();
      }

      internal constructor(tls: Boolean)  {
         this.tls = var1;
      }

      public fun allEnabledCipherSuites(): okhttp3.ConnectionSpec.Builder {
         val var1: ConnectionSpec.Builder = this;
         if (this.tls) {
            val var2: Array<java.lang.String> = null as Array<java.lang.String>;
            this.cipherSuites = null;
            return this;
         } else {
            throw (new IllegalArgumentException("no cipher suites for cleartext connections".toString())) as java.lang.Throwable;
         }
      }

      public fun allEnabledTlsVersions(): okhttp3.ConnectionSpec.Builder {
         val var1: ConnectionSpec.Builder = this;
         if (this.tls) {
            val var2: Array<java.lang.String> = null as Array<java.lang.String>;
            this.tlsVersions = null;
            return this;
         } else {
            throw (new IllegalArgumentException("no TLS versions for cleartext connections".toString())) as java.lang.Throwable;
         }
      }

      public fun build(): ConnectionSpec {
         return new ConnectionSpec(this.tls, this.supportsTlsExtensions, this.cipherSuites, this.tlsVersions);
      }

      public fun cipherSuites(vararg cipherSuites: String): okhttp3.ConnectionSpec.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "cipherSuites");
         val var3: ConnectionSpec.Builder = this;
         if (this.tls) {
            val var2: Boolean;
            if (var1.length == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               val var4: Any = var1.clone();
               if (var4 != null) {
                  this.cipherSuites = var4 as Array<java.lang.String>;
                  return this;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
               }
            } else {
               throw (new IllegalArgumentException("At least one cipher suite is required".toString())) as java.lang.Throwable;
            }
         } else {
            throw (new IllegalArgumentException("no cipher suites for cleartext connections".toString())) as java.lang.Throwable;
         }
      }

      public fun cipherSuites(vararg cipherSuites: CipherSuite): okhttp3.ConnectionSpec.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "cipherSuites");
         val var4: ConnectionSpec.Builder = this;
         if (!this.tls) {
            throw (new IllegalArgumentException("no cipher suites for cleartext connections".toString())) as java.lang.Throwable;
         } else {
            val var7: java.util.Collection = new ArrayList(var1.length);
            val var3: Int = var1.length;

            for (int var2 = 0; var2 < var3; var2++) {
               var7.add(var1[var2].javaName());
            }

            val var5: Array<Any> = (var7 as java.util.List).toArray(new java.lang.String[0]);
            if (var5 != null) {
               return this.cipherSuites(Arrays.copyOf(var5 as Array<java.lang.String>, (var5 as Array<java.lang.String>).length));
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
         }
      }

      @Deprecated(message = "since OkHttp 3.13 all TLS-connections are expected to support TLS extensions.\nIn a future release setting this to true will be unnecessary and setting it to false\nwill have no effect.")
      public fun supportsTlsExtensions(supportsTlsExtensions: Boolean): okhttp3.ConnectionSpec.Builder {
         val var2: ConnectionSpec.Builder = this;
         if (this.tls) {
            this.supportsTlsExtensions = var1;
            return this;
         } else {
            throw (new IllegalArgumentException("no TLS extensions for cleartext connections".toString())) as java.lang.Throwable;
         }
      }

      public fun tlsVersions(vararg tlsVersions: String): okhttp3.ConnectionSpec.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "tlsVersions");
         val var3: ConnectionSpec.Builder = this;
         if (this.tls) {
            val var2: Boolean;
            if (var1.length == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               val var4: Any = var1.clone();
               if (var4 != null) {
                  this.tlsVersions = var4 as Array<java.lang.String>;
                  return this;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
               }
            } else {
               throw (new IllegalArgumentException("At least one TLS version is required".toString())) as java.lang.Throwable;
            }
         } else {
            throw (new IllegalArgumentException("no TLS versions for cleartext connections".toString())) as java.lang.Throwable;
         }
      }

      public fun tlsVersions(vararg tlsVersions: TlsVersion): okhttp3.ConnectionSpec.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "tlsVersions");
         val var4: ConnectionSpec.Builder = this;
         if (!this.tls) {
            throw (new IllegalArgumentException("no TLS versions for cleartext connections".toString())) as java.lang.Throwable;
         } else {
            val var7: java.util.Collection = new ArrayList(var1.length);
            val var3: Int = var1.length;

            for (int var2 = 0; var2 < var3; var2++) {
               var7.add(var1[var2].javaName());
            }

            val var5: Array<Any> = (var7 as java.util.List).toArray(new java.lang.String[0]);
            if (var5 != null) {
               return this.tlsVersions(Arrays.copyOf(var5 as Array<java.lang.String>, (var5 as Array<java.lang.String>).length));
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
         }
      }
   }

   public companion object {
      private final val APPROVED_CIPHER_SUITES: Array<CipherSuite>
      public final val CLEARTEXT: ConnectionSpec
      public final val COMPATIBLE_TLS: ConnectionSpec
      public final val MODERN_TLS: ConnectionSpec
      private final val RESTRICTED_CIPHER_SUITES: Array<CipherSuite>
      public final val RESTRICTED_TLS: ConnectionSpec
   }
}
