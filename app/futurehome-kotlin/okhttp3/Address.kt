package okhttp3

import java.net.Proxy
import java.net.ProxySelector
import java.util.Objects
import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class Address(uriHost: String,
   uriPort: Int,
   dns: Dns,
   socketFactory: SocketFactory,
   sslSocketFactory: SSLSocketFactory?,
   hostnameVerifier: HostnameVerifier?,
   certificatePinner: CertificatePinner?,
   proxyAuthenticator: Authenticator,
   proxy: Proxy?,
   protocols: List<Protocol>,
   connectionSpecs: List<ConnectionSpec>,
   proxySelector: ProxySelector
) {
   public final val certificatePinner: CertificatePinner?

   public final val connectionSpecs: List<ConnectionSpec>
      public final get() {
         return this.connectionSpecs;
      }


   public final val dns: Dns
   public final val hostnameVerifier: HostnameVerifier?

   public final val protocols: List<Protocol>
      public final get() {
         return this.protocols;
      }


   public final val proxy: Proxy?
   public final val proxyAuthenticator: Authenticator
   public final val proxySelector: ProxySelector
   public final val socketFactory: SocketFactory
   public final val sslSocketFactory: SSLSocketFactory?

   public final val url: HttpUrl
      public final get() {
         return this.url;
      }


   init {
      Intrinsics.checkParameterIsNotNull(var1, "uriHost");
      Intrinsics.checkParameterIsNotNull(var3, "dns");
      Intrinsics.checkParameterIsNotNull(var4, "socketFactory");
      Intrinsics.checkParameterIsNotNull(var8, "proxyAuthenticator");
      Intrinsics.checkParameterIsNotNull(var10, "protocols");
      Intrinsics.checkParameterIsNotNull(var11, "connectionSpecs");
      Intrinsics.checkParameterIsNotNull(var12, "proxySelector");
      super();
      this.dns = var3;
      this.socketFactory = var4;
      this.sslSocketFactory = var5;
      this.hostnameVerifier = var6;
      this.certificatePinner = var7;
      this.proxyAuthenticator = var8;
      this.proxy = var9;
      this.proxySelector = var12;
      val var14: HttpUrl.Builder = new HttpUrl.Builder();
      val var13: java.lang.String;
      if (var5 != null) {
         var13 = "https";
      } else {
         var13 = "http";
      }

      this.url = var14.scheme(var13).host(var1).port(var2).build();
      this.protocols = Util.toImmutableList(var10);
      this.connectionSpecs = Util.toImmutableList(var11);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "certificatePinner", imports = []))
   public fun certificatePinner(): CertificatePinner? {
      return this.certificatePinner;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionSpecs", imports = []))
   public fun connectionSpecs(): List<ConnectionSpec> {
      return this.connectionSpecs;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dns", imports = []))
   public fun dns(): Dns {
      return this.dns;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hostnameVerifier", imports = []))
   public fun hostnameVerifier(): HostnameVerifier? {
      return this.hostnameVerifier;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "protocols", imports = []))
   public fun protocols(): List<Protocol> {
      return this.protocols;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxy", imports = []))
   public fun proxy(): Proxy? {
      return this.proxy;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxyAuthenticator", imports = []))
   public fun proxyAuthenticator(): Authenticator {
      return this.proxyAuthenticator;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxySelector", imports = []))
   public fun proxySelector(): ProxySelector {
      return this.proxySelector;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "socketFactory", imports = []))
   public fun socketFactory(): SocketFactory {
      return this.socketFactory;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sslSocketFactory", imports = []))
   public fun sslSocketFactory(): SSLSocketFactory? {
      return this.sslSocketFactory;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "url", imports = []))
   public fun url(): HttpUrl {
      return this.url;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is Address && this.url == (var1 as Address).url && this.equalsNonHost$okhttp(var1 as Address);
   }

   internal fun equalsNonHost(that: Address): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "that");
      val var2: Boolean;
      if (this.dns == var1.dns
         && this.proxyAuthenticator == var1.proxyAuthenticator
         && this.protocols == var1.protocols
         && this.connectionSpecs == var1.connectionSpecs
         && this.proxySelector == var1.proxySelector
         && this.proxy == var1.proxy
         && this.sslSocketFactory == var1.sslSocketFactory
         && this.hostnameVerifier == var1.hostnameVerifier
         && this.certificatePinner == var1.certificatePinner
         && this.url.port() == var1.url.port()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return (
               (
                        (
                                 (
                                          (
                                                   (
                                                            (((527 + this.url.hashCode()) * 31 + this.dns.hashCode()) * 31 + this.proxyAuthenticator.hashCode())
                                                                  * 31
                                                               + this.protocols.hashCode()
                                                         )
                                                         * 31
                                                      + this.connectionSpecs.hashCode()
                                                )
                                                * 31
                                             + this.proxySelector.hashCode()
                                       )
                                       * 31
                                    + Objects.hashCode(this.proxy)
                              )
                              * 31
                           + Objects.hashCode(this.sslSocketFactory)
                     )
                     * 31
                  + Objects.hashCode(this.hostnameVerifier)
            )
            * 31
         + Objects.hashCode(this.certificatePinner);
   }

   public override fun toString(): String {
      val var3: StringBuilder = new StringBuilder("Address{");
      var3.append(this.url.host());
      var3.append(':');
      var3.append(this.url.port());
      var3.append(", ");
      val var1: Any;
      val var2: StringBuilder;
      if (this.proxy != null) {
         var2 = new StringBuilder("proxy=");
         var1 = this.proxy;
      } else {
         var2 = new StringBuilder("proxySelector=");
         var1 = this.proxySelector;
      }

      var2.append(var1);
      var3.append(var2.toString());
      var3.append("}");
      return var3.toString();
   }
}
