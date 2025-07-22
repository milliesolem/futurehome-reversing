package okhttp3

import java.util.Comparator
import kotlin.jvm.internal.Intrinsics

public class CipherSuite private constructor(javaName: String) {
   public final val javaName: String

   @JvmStatic
   fun {
      val var0: CipherSuite.Companion = new CipherSuite.Companion(null);
      Companion = var0;
      TLS_RSA_WITH_NULL_MD5 = CipherSuite.Companion.access$init(var0, "SSL_RSA_WITH_NULL_MD5", 1);
      TLS_RSA_WITH_NULL_SHA = CipherSuite.Companion.access$init(var0, "SSL_RSA_WITH_NULL_SHA", 2);
      TLS_RSA_EXPORT_WITH_RC4_40_MD5 = CipherSuite.Companion.access$init(var0, "SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3);
      TLS_RSA_WITH_RC4_128_MD5 = CipherSuite.Companion.access$init(var0, "SSL_RSA_WITH_RC4_128_MD5", 4);
      TLS_RSA_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "SSL_RSA_WITH_RC4_128_SHA", 5);
      TLS_RSA_EXPORT_WITH_DES40_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8);
      TLS_RSA_WITH_DES_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_RSA_WITH_DES_CBC_SHA", 9);
      TLS_RSA_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10);
      TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17);
      TLS_DHE_DSS_WITH_DES_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DHE_DSS_WITH_DES_CBC_SHA", 18);
      TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
      TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20);
      TLS_DHE_RSA_WITH_DES_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DHE_RSA_WITH_DES_CBC_SHA", 21);
      TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
      TLS_DH_anon_EXPORT_WITH_RC4_40_MD5 = CipherSuite.Companion.access$init(var0, "SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23);
      TLS_DH_anon_WITH_RC4_128_MD5 = CipherSuite.Companion.access$init(var0, "SSL_DH_anon_WITH_RC4_128_MD5", 24);
      TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25);
      TLS_DH_anon_WITH_DES_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DH_anon_WITH_DES_CBC_SHA", 26);
      TLS_DH_anon_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27);
      TLS_KRB5_WITH_DES_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_KRB5_WITH_DES_CBC_SHA", 30);
      TLS_KRB5_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
      TLS_KRB5_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_KRB5_WITH_RC4_128_SHA", 32);
      TLS_KRB5_WITH_DES_CBC_MD5 = CipherSuite.Companion.access$init(var0, "TLS_KRB5_WITH_DES_CBC_MD5", 34);
      TLS_KRB5_WITH_3DES_EDE_CBC_MD5 = CipherSuite.Companion.access$init(var0, "TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
      TLS_KRB5_WITH_RC4_128_MD5 = CipherSuite.Companion.access$init(var0, "TLS_KRB5_WITH_RC4_128_MD5", 36);
      TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA = CipherSuite.Companion.access$init(var0, "TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
      TLS_KRB5_EXPORT_WITH_RC4_40_SHA = CipherSuite.Companion.access$init(var0, "TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
      TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5 = CipherSuite.Companion.access$init(var0, "TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
      TLS_KRB5_EXPORT_WITH_RC4_40_MD5 = CipherSuite.Companion.access$init(var0, "TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
      TLS_RSA_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_AES_128_CBC_SHA", 47);
      TLS_DHE_DSS_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
      TLS_DHE_RSA_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
      TLS_DH_anon_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DH_anon_WITH_AES_128_CBC_SHA", 52);
      TLS_RSA_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_AES_256_CBC_SHA", 53);
      TLS_DHE_DSS_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
      TLS_DHE_RSA_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
      TLS_DH_anon_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DH_anon_WITH_AES_256_CBC_SHA", 58);
      TLS_RSA_WITH_NULL_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_NULL_SHA256", 59);
      TLS_RSA_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
      TLS_RSA_WITH_AES_256_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
      TLS_DHE_DSS_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
      TLS_RSA_WITH_CAMELLIA_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
      TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
      TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
      TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103);
      TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106);
      TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107);
      TLS_DH_anon_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108);
      TLS_DH_anon_WITH_AES_256_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109);
      TLS_RSA_WITH_CAMELLIA_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", 132);
      TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", 135);
      TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", 136);
      TLS_PSK_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_PSK_WITH_RC4_128_SHA", 138);
      TLS_PSK_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_PSK_WITH_3DES_EDE_CBC_SHA", 139);
      TLS_PSK_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_PSK_WITH_AES_128_CBC_SHA", 140);
      TLS_PSK_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_PSK_WITH_AES_256_CBC_SHA", 141);
      TLS_RSA_WITH_SEED_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_SEED_CBC_SHA", 150);
      TLS_RSA_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_AES_128_GCM_SHA256", 156);
      TLS_RSA_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_RSA_WITH_AES_256_GCM_SHA384", 157);
      TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", 158);
      TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", 159);
      TLS_DHE_DSS_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", 162);
      TLS_DHE_DSS_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", 163);
      TLS_DH_anon_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166);
      TLS_DH_anon_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_DH_anon_WITH_AES_256_GCM_SHA384", 167);
      TLS_EMPTY_RENEGOTIATION_INFO_SCSV = CipherSuite.Companion.access$init(var0, "TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255);
      TLS_FALLBACK_SCSV = CipherSuite.Companion.access$init(var0, "TLS_FALLBACK_SCSV", 22016);
      TLS_ECDH_ECDSA_WITH_NULL_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153);
      TLS_ECDH_ECDSA_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154);
      TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155);
      TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156);
      TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157);
      TLS_ECDHE_ECDSA_WITH_NULL_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158);
      TLS_ECDHE_ECDSA_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159);
      TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160);
      TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161);
      TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162);
      TLS_ECDH_RSA_WITH_NULL_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_NULL_SHA", 49163);
      TLS_ECDH_RSA_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164);
      TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165);
      TLS_ECDH_RSA_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166);
      TLS_ECDH_RSA_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167);
      TLS_ECDHE_RSA_WITH_NULL_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_NULL_SHA", 49168);
      TLS_ECDHE_RSA_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169);
      TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170);
      TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171);
      TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172);
      TLS_ECDH_anon_WITH_NULL_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_anon_WITH_NULL_SHA", 49173);
      TLS_ECDH_anon_WITH_RC4_128_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_anon_WITH_RC4_128_SHA", 49174);
      TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175);
      TLS_ECDH_anon_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176);
      TLS_ECDH_anon_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177);
      TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187);
      TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188);
      TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189);
      TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190);
      TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191);
      TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192);
      TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193);
      TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194);
      TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195);
      TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196);
      TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197);
      TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198);
      TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199);
      TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200);
      TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201);
      TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202);
      TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", 49205);
      TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", 49206);
      TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52392);
      TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", 52393);
      TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52394);
      TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256", 52396);
      TLS_AES_128_GCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_AES_128_GCM_SHA256", 4865);
      TLS_AES_256_GCM_SHA384 = CipherSuite.Companion.access$init(var0, "TLS_AES_256_GCM_SHA384", 4866);
      TLS_CHACHA20_POLY1305_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_CHACHA20_POLY1305_SHA256", 4867);
      TLS_AES_128_CCM_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_AES_128_CCM_SHA256", 4868);
      TLS_AES_128_CCM_8_SHA256 = CipherSuite.Companion.access$init(var0, "TLS_AES_128_CCM_8_SHA256", 4869);
   }

   init {
      this.javaName = var1;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "javaName", imports = []))
   public fun javaName(): String {
      return this.javaName;
   }

   public override fun toString(): String {
      return this.javaName;
   }

   public companion object {
      private final val INSTANCES: MutableMap<String, CipherSuite>
      internal final val ORDER_BY_NAME: Comparator<String>
      public final val TLS_AES_128_CCM_8_SHA256: CipherSuite
      public final val TLS_AES_128_CCM_SHA256: CipherSuite
      public final val TLS_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_CHACHA20_POLY1305_SHA256: CipherSuite
      public final val TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA: CipherSuite
      public final val TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_DHE_DSS_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_DHE_DSS_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_DHE_DSS_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_DHE_DSS_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_DHE_DSS_WITH_AES_256_CBC_SHA256: CipherSuite
      public final val TLS_DHE_DSS_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA: CipherSuite
      public final val TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA: CipherSuite
      public final val TLS_DHE_DSS_WITH_DES_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_DHE_RSA_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_DHE_RSA_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_WITH_AES_256_CBC_SHA256: CipherSuite
      public final val TLS_DHE_RSA_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA: CipherSuite
      public final val TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256: CipherSuite
      public final val TLS_DHE_RSA_WITH_DES_CBC_SHA: CipherSuite
      public final val TLS_DH_anon_EXPORT_WITH_DES40_CBC_SHA: CipherSuite
      public final val TLS_DH_anon_EXPORT_WITH_RC4_40_MD5: CipherSuite
      public final val TLS_DH_anon_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_DH_anon_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_DH_anon_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_DH_anon_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_DH_anon_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_DH_anon_WITH_AES_256_CBC_SHA256: CipherSuite
      public final val TLS_DH_anon_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_DH_anon_WITH_DES_CBC_SHA: CipherSuite
      public final val TLS_DH_anon_WITH_RC4_128_MD5: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_NULL_SHA: CipherSuite
      public final val TLS_ECDHE_ECDSA_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_NULL_SHA: CipherSuite
      public final val TLS_ECDHE_RSA_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_NULL_SHA: CipherSuite
      public final val TLS_ECDH_ECDSA_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_ECDH_RSA_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_ECDH_RSA_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384: CipherSuite
      public final val TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_ECDH_RSA_WITH_NULL_SHA: CipherSuite
      public final val TLS_ECDH_RSA_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_ECDH_anon_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_ECDH_anon_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_ECDH_anon_WITH_NULL_SHA: CipherSuite
      public final val TLS_ECDH_anon_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_EMPTY_RENEGOTIATION_INFO_SCSV: CipherSuite
      public final val TLS_FALLBACK_SCSV: CipherSuite
      public final val TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5: CipherSuite
      public final val TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA: CipherSuite
      public final val TLS_KRB5_EXPORT_WITH_RC4_40_MD5: CipherSuite
      public final val TLS_KRB5_EXPORT_WITH_RC4_40_SHA: CipherSuite
      public final val TLS_KRB5_WITH_3DES_EDE_CBC_MD5: CipherSuite
      public final val TLS_KRB5_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_KRB5_WITH_DES_CBC_MD5: CipherSuite
      public final val TLS_KRB5_WITH_DES_CBC_SHA: CipherSuite
      public final val TLS_KRB5_WITH_RC4_128_MD5: CipherSuite
      public final val TLS_KRB5_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_PSK_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_PSK_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_PSK_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_PSK_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_RSA_EXPORT_WITH_DES40_CBC_SHA: CipherSuite
      public final val TLS_RSA_EXPORT_WITH_RC4_40_MD5: CipherSuite
      public final val TLS_RSA_WITH_3DES_EDE_CBC_SHA: CipherSuite
      public final val TLS_RSA_WITH_AES_128_CBC_SHA: CipherSuite
      public final val TLS_RSA_WITH_AES_128_CBC_SHA256: CipherSuite
      public final val TLS_RSA_WITH_AES_128_GCM_SHA256: CipherSuite
      public final val TLS_RSA_WITH_AES_256_CBC_SHA: CipherSuite
      public final val TLS_RSA_WITH_AES_256_CBC_SHA256: CipherSuite
      public final val TLS_RSA_WITH_AES_256_GCM_SHA384: CipherSuite
      public final val TLS_RSA_WITH_CAMELLIA_128_CBC_SHA: CipherSuite
      public final val TLS_RSA_WITH_CAMELLIA_256_CBC_SHA: CipherSuite
      public final val TLS_RSA_WITH_DES_CBC_SHA: CipherSuite
      public final val TLS_RSA_WITH_NULL_MD5: CipherSuite
      public final val TLS_RSA_WITH_NULL_SHA: CipherSuite
      public final val TLS_RSA_WITH_NULL_SHA256: CipherSuite
      public final val TLS_RSA_WITH_RC4_128_MD5: CipherSuite
      public final val TLS_RSA_WITH_RC4_128_SHA: CipherSuite
      public final val TLS_RSA_WITH_SEED_CBC_SHA: CipherSuite

      private fun init(javaName: String, value: Int): CipherSuite {
         val var3: CipherSuite = new CipherSuite(var1, null);
         CipherSuite.access$getINSTANCES$cp().put(var1, var3);
         return var3;
      }

      private fun secondaryName(javaName: String): String {
         var var5: java.lang.String;
         if (StringsKt.startsWith$default(var1, "TLS_", false, 2, null)) {
            val var2: StringBuilder = new StringBuilder("SSL_");
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var1 = var1.substring(4);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            var2.append(var1);
            var5 = var2.toString();
         } else {
            var5 = var1;
            if (StringsKt.startsWith$default(var1, "SSL_", false, 2, null)) {
               val var6: StringBuilder = new StringBuilder("TLS_");
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var1 = var1.substring(4);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               var6.append(var1);
               var5 = var6.toString();
            }
         }

         return var5;
      }

      public fun forJavaName(javaName: String): CipherSuite {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 1
         // 03: ldc "javaName"
         // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
         // 08: invokestatic okhttp3/CipherSuite.access$getINSTANCES$cp ()Ljava/util/Map;
         // 0b: aload 1
         // 0c: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 11: checkcast okhttp3/CipherSuite
         // 14: astore 3
         // 15: aload 3
         // 16: astore 2
         // 17: aload 3
         // 18: ifnonnull 4e
         // 1b: invokestatic okhttp3/CipherSuite.access$getINSTANCES$cp ()Ljava/util/Map;
         // 1e: astore 2
         // 1f: aload 0
         // 20: checkcast okhttp3/CipherSuite$Companion
         // 23: astore 3
         // 24: aload 2
         // 25: aload 0
         // 26: aload 1
         // 27: invokespecial okhttp3/CipherSuite$Companion.secondaryName (Ljava/lang/String;)Ljava/lang/String;
         // 2a: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 2f: checkcast okhttp3/CipherSuite
         // 32: astore 3
         // 33: aload 3
         // 34: astore 2
         // 35: aload 3
         // 36: ifnonnull 43
         // 39: new okhttp3/CipherSuite
         // 3c: astore 2
         // 3d: aload 2
         // 3e: aload 1
         // 3f: aconst_null
         // 40: invokespecial okhttp3/CipherSuite.<init> (Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V
         // 43: invokestatic okhttp3/CipherSuite.access$getINSTANCES$cp ()Ljava/util/Map;
         // 46: aload 1
         // 47: aload 2
         // 48: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
         // 4d: pop
         // 4e: aload 0
         // 4f: monitorexit
         // 50: aload 2
         // 51: areturn
         // 52: astore 1
         // 53: aload 0
         // 54: monitorexit
         // 55: aload 1
         // 56: athrow
      }
   }
}
