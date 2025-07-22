package okhttp3.internal.authenticator

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.PasswordAuthentication
import java.net.Proxy
import java.net.SocketAddress
import java.net.Authenticator.RequestorType
import java.net.Proxy.Type
import kotlin.jvm.internal.Intrinsics
import okhttp3.Address
import okhttp3.Authenticator
import okhttp3.Challenge
import okhttp3.Credentials
import okhttp3.Dns
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

public class JavaNetAuthenticator(defaultDns: Dns = Dns.SYSTEM) : Authenticator {
   private final val defaultDns: Dns

   fun JavaNetAuthenticator() {
      this(null, 1, null);
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "defaultDns");
      super();
      this.defaultDns = var1;
   }

   @Throws(java/io/IOException::class)
   private fun Proxy.connectToInetAddress(url: HttpUrl, dns: Dns): InetAddress {
      val var4: Type = var1.type();
      val var6: InetAddress;
      if (var4 != null && JavaNetAuthenticator$WhenMappings.$EnumSwitchMapping$0[var4.ordinal()] == 1) {
         var6 = CollectionsKt.first(var3.lookup(var2.host()));
      } else {
         val var5: SocketAddress = var1.address();
         if (var5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
         }

         var6 = (var5 as InetSocketAddress).getAddress();
         Intrinsics.checkExpressionValueIsNotNull(var6, "(address() as InetSocketAddress).address");
      }

      return var6;
   }

   @Throws(java/io/IOException::class)
   public override fun authenticate(route: Route?, response: Response): Request? {
      Intrinsics.checkParameterIsNotNull(var2, "response");
      val var4: java.util.List = var2.challenges();
      val var5: Request = var2.request();
      val var7: HttpUrl = var5.url();
      val var3: Boolean;
      if (var2.code() == 407) {
         var3 = true;
      } else {
         var3 = false;
      }

      label54: {
         if (var1 != null) {
            var12 = var1.proxy();
            if (var12 != null) {
               break label54;
            }
         }

         var12 = Proxy.NO_PROXY;
      }

      for (Challenge var6 : var4) {
         if (StringsKt.equals("Basic", var6.scheme(), true)) {
            label45: {
               if (var1 != null) {
                  val var15: Address = var1.address();
                  if (var15 != null) {
                     var16 = var15.dns();
                     if (var16 != null) {
                        break label45;
                     }
                  }
               }

               var16 = this.defaultDns;
            }

            val var17: PasswordAuthentication;
            if (var3) {
               val var9: SocketAddress = var12.address();
               if (var9 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
               }

               val var19: InetSocketAddress = var9 as InetSocketAddress;
               val var10: java.lang.String = (var9 as InetSocketAddress).getHostName();
               Intrinsics.checkExpressionValueIsNotNull(var12, "proxy");
               var17 = java.net.Authenticator.requestPasswordAuthentication(
                  var10,
                  this.connectToInetAddress(var12, var7, var16),
                  var19.getPort(),
                  var7.scheme(),
                  var6.realm(),
                  var6.scheme(),
                  var7.url(),
                  RequestorType.PROXY
               );
            } else {
               val var20: java.lang.String = var7.host();
               Intrinsics.checkExpressionValueIsNotNull(var12, "proxy");
               var17 = java.net.Authenticator.requestPasswordAuthentication(
                  var20,
                  this.connectToInetAddress(var12, var7, var16),
                  var7.port(),
                  var7.scheme(),
                  var6.realm(),
                  var6.scheme(),
                  var7.url(),
                  RequestorType.SERVER
               );
            }

            if (var17 != null) {
               val var11: java.lang.String;
               if (var3) {
                  var11 = "Proxy-Authorization";
               } else {
                  var11 = "Authorization";
               }

               val var13: java.lang.String = var17.getUserName();
               Intrinsics.checkExpressionValueIsNotNull(var13, "auth.userName");
               val var18: CharArray = var17.getPassword();
               Intrinsics.checkExpressionValueIsNotNull(var18, "auth.password");
               return var5.newBuilder().header(var11, Credentials.basic(var13, new java.lang.String(var18), var6.charset())).build();
            }
         }
      }

      return null;
   }
}
