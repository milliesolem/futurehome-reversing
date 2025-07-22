package okhttp3.internal.platform

import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.Arrays
import javax.net.ssl.SSLSocket
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol

public class Jdk8WithJettyBootPlatform(putMethod: Method, getMethod: Method, removeMethod: Method, clientProviderClass: Class<*>, serverProviderClass: Class<*>)
   : Platform {
   private final val clientProviderClass: Class<*>
   private final val getMethod: Method
   private final val putMethod: Method
   private final val removeMethod: Method
   private final val serverProviderClass: Class<*>

   init {
      Intrinsics.checkParameterIsNotNull(var1, "putMethod");
      Intrinsics.checkParameterIsNotNull(var2, "getMethod");
      Intrinsics.checkParameterIsNotNull(var3, "removeMethod");
      Intrinsics.checkParameterIsNotNull(var4, "clientProviderClass");
      Intrinsics.checkParameterIsNotNull(var5, "serverProviderClass");
      super();
      this.putMethod = var1;
      this.getMethod = var2;
      this.removeMethod = var3;
      this.clientProviderClass = var4;
      this.serverProviderClass = var5;
   }

   public override fun afterHandshake(sslSocket: SSLSocket) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");

      try {
         this.removeMethod.invoke(null, var1);
      } catch (var2: IllegalAccessException) {
         throw (new AssertionError("failed to remove ALPN", var2)) as java.lang.Throwable;
      } catch (var3: InvocationTargetException) {
         throw (new AssertionError("failed to remove ALPN", var3)) as java.lang.Throwable;
      }
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      val var5: java.util.List = Platform.Companion.alpnProtocolNames(var3);

      try {
         this.putMethod
            .invoke(
               null,
               var1,
               Proxy.newProxyInstance(
                  Platform.class.getClassLoader(),
                  new Class[]{this.clientProviderClass, this.serverProviderClass},
                  new Jdk8WithJettyBootPlatform.AlpnProvider(var5)
               )
            );
      } catch (var7: InvocationTargetException) {
         throw (new AssertionError("failed to set ALPN", var7)) as java.lang.Throwable;
      } catch (var8: IllegalAccessException) {
         throw (new AssertionError("failed to set ALPN", var8)) as java.lang.Throwable;
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");

      var var3: Method;
      try {
         var3 = this.getMethod;
      } catch (var12: InvocationTargetException) {
         throw (new AssertionError("failed to get ALPN selected protocol", var12)) as java.lang.Throwable;
      } catch (var13: IllegalAccessException) {
         throw (new AssertionError("failed to get ALPN selected protocol", var13)) as java.lang.Throwable;
      }

      val var2: Any = null;

      try {
         var16 = Proxy.getInvocationHandler(var3.invoke(null, var1));
      } catch (var10: InvocationTargetException) {
         throw (new AssertionError("failed to get ALPN selected protocol", var10)) as java.lang.Throwable;
      } catch (var11: IllegalAccessException) {
         throw (new AssertionError("failed to get ALPN selected protocol", var11)) as java.lang.Throwable;
      }

      if (var16 != null) {
         try {
            var18 = var16 as Jdk8WithJettyBootPlatform.AlpnProvider;
            if (!(var16 as Jdk8WithJettyBootPlatform.AlpnProvider).getUnsupported$okhttp()
               && (var16 as Jdk8WithJettyBootPlatform.AlpnProvider).getSelected$okhttp() == null) {
               Platform.log$default(this, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", 0, null, 6, null);
               return null;
            }
         } catch (var6: InvocationTargetException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var6)) as java.lang.Throwable;
         } catch (var7: IllegalAccessException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var7)) as java.lang.Throwable;
         }

         try {
            if (var18.getUnsupported$okhttp()) {
               return (java.lang.String)var2;
            }
         } catch (var14: InvocationTargetException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var14)) as java.lang.Throwable;
         } catch (var15: IllegalAccessException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var15)) as java.lang.Throwable;
         }

         try {
            return var18.getSelected$okhttp();
         } catch (var4: InvocationTargetException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var4)) as java.lang.Throwable;
         } catch (var5: IllegalAccessException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var5)) as java.lang.Throwable;
         }
      } else {
         try {
            throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.platform.Jdk8WithJettyBootPlatform.AlpnProvider");
         } catch (var8: InvocationTargetException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var8)) as java.lang.Throwable;
         } catch (var9: IllegalAccessException) {
            throw (new AssertionError("failed to get ALPN selected protocol", var9)) as java.lang.Throwable;
         }
      }
   }

   private class AlpnProvider internal constructor(protocols: List<String>) : InvocationHandler {
      private final val protocols: List<String>
      internal final var selected: String?
      internal final var unsupported: Boolean

      init {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         super();
         this.protocols = var1;
      }

      @Throws(java/lang/Throwable::class)
      public override operator fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any? {
         Intrinsics.checkParameterIsNotNull(var1, "proxy");
         Intrinsics.checkParameterIsNotNull(var2, "method");
         if (var3 == null) {
            var3 = new Object[0];
         }

         var1 = var2.getName();
         var var6: Class = var2.getReturnType();
         if (var1 == "supports" && java.lang.Boolean::class.javaPrimitiveType == var6) {
            return true;
         } else if (var1 == "unsupported" && Void::class.javaPrimitiveType == var6) {
            this.unsupported = true;
            return null;
         } else if (var1 == "protocols" && var3.length == 0) {
            return this.protocols;
         } else {
            if ((var1 == "selectProtocol" || var1 == "select") && java.lang.String::class.java == var6 && var3.length == 1) {
               var6 = (Class)var3[0];
               if (var3[0] is java.util.List) {
                  if (var6 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<*>");
                  }

                  var1 = var6 as java.util.List;
                  val var5: Int = (var6 as java.util.List).size();
                  if (var5 >= 0) {
                     var var4: Int = 0;

                     while (true) {
                        var var11: Any = var1.get(var4);
                        if (var11 == null) {
                           throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                        }

                        var11 = var11 as java.lang.String;
                        if (this.protocols.contains(var11 as java.lang.String)) {
                           this.selected = (java.lang.String)var11;
                           return var11;
                        }

                        if (var4 == var5) {
                           break;
                        }

                        var4++;
                     }
                  }

                  var1 = this.protocols.get(0);
                  this.selected = var1;
                  return var1;
               }
            }

            if ((var1 == "protocolSelected" || var1 == "selected") && var3.length == 1) {
               var1 = var3[0];
               if (var3[0] != null) {
                  this.selected = var1 as java.lang.String;
                  return null;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
               }
            } else {
               return var2.invoke(this, Arrays.copyOf(var3, var3.length));
            }
         }
      }
   }

   public companion object {
      public fun buildIfSupported(): Platform? {
         val var2: java.lang.String = System.getProperty("java.specification.version", "unknown");

         label19: {
            var var1: Int;
            try {
               Intrinsics.checkExpressionValueIsNotNull(var2, "jvmVersion");
               var1 = Integer.parseInt(var2);
            } catch (var9: NumberFormatException) {
               break label19;
            }

            if (var1 >= 9) {
               return null;
            }
         }

         try {
            val var6: Class = Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
            val var4: Class = Class.forName("org.eclipse.jetty.alpn.ALPN$Provider", true, null);
            val var3: Class = Class.forName("org.eclipse.jetty.alpn.ALPN$ClientProvider", true, null);
            val var10: Class = Class.forName("org.eclipse.jetty.alpn.ALPN$ServerProvider", true, null);
            val var11: Method = var6.getMethod("put", SSLSocket.class, var4);
            val var5: Method = var6.getMethod("get", SSLSocket.class);
            val var7: Method = var6.getMethod("remove", SSLSocket.class);
            Intrinsics.checkExpressionValueIsNotNull(var11, "putMethod");
            Intrinsics.checkExpressionValueIsNotNull(var5, "getMethod");
            Intrinsics.checkExpressionValueIsNotNull(var7, "removeMethod");
            Intrinsics.checkExpressionValueIsNotNull(var3, "clientProviderClass");
            Intrinsics.checkExpressionValueIsNotNull(var10, "serverProviderClass");
            return new Jdk8WithJettyBootPlatform(var11, var5, var7, var3, var10);
         } catch (NoSuchMethodException | var8: ClassNotFoundException) {
            return null;
         }
      }
   }
}
