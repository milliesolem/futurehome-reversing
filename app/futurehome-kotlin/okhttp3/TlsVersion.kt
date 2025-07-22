package okhttp3

import kotlin.jvm.internal.Intrinsics

public enum class TlsVersion(javaName: String) {
   SSL_3_0,
   TLS_1_0,
   TLS_1_1,
   TLS_1_2,
   TLS_1_3
   public final val javaName: String
   @JvmStatic
   private TlsVersion[] $VALUES;
   @JvmStatic
   public TlsVersion.Companion Companion = new TlsVersion.Companion(null);

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun {
      val var1: TlsVersion = new TlsVersion("TLSv1.3");
      TLS_1_3 = var1;
      val var3: TlsVersion = new TlsVersion("TLSv1.2");
      TLS_1_2 = var3;
      val var4: TlsVersion = new TlsVersion("TLSv1.1");
      TLS_1_1 = var4;
      val var2: TlsVersion = new TlsVersion("TLSv1");
      TLS_1_0 = var2;
      val var0: TlsVersion = new TlsVersion("SSLv3");
      SSL_3_0 = var0;
      $VALUES = new TlsVersion[]{var1, var3, var4, var2, var0};
   }

   init {
      this.javaName = var3;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "javaName", imports = []))
   public fun javaName(): String {
      return this.javaName;
   }

   public companion object {
      public fun forJavaName(javaName: String): TlsVersion {
         Intrinsics.checkParameterIsNotNull(var1, "javaName");
         val var2: Int = var1.hashCode();
         if (var2 != 79201641) {
            if (var2 != 79923350) {
               switch (var2) {
                  case -503070503:
                     if (var1.equals("TLSv1.1")) {
                        return TlsVersion.TLS_1_1;
                     }
                     break;
                  case -503070502:
                     if (var1.equals("TLSv1.2")) {
                        return TlsVersion.TLS_1_2;
                     }
                     break;
                  case -503070501:
                     if (var1.equals("TLSv1.3")) {
                        return TlsVersion.TLS_1_3;
                     }
                  default:
               }
            } else if (var1.equals("TLSv1")) {
               return TlsVersion.TLS_1_0;
            }
         } else if (var1.equals("SSLv3")) {
            return TlsVersion.SSL_3_0;
         }

         val var3: StringBuilder = new StringBuilder("Unexpected TLS version: ");
         var3.append(var1);
         throw (new IllegalArgumentException(var3.toString())) as java.lang.Throwable;
      }
   }
}
