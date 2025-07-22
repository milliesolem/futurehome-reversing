package okhttp3.internal.http

import java.io.FileNotFoundException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.Proxy.Type
import java.security.cert.CertificateException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.jvm.internal.Intrinsics
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.Interceptor.Chain
import okhttp3.internal.Util
import okhttp3.internal.connection.Exchange
import okhttp3.internal.connection.RealCall
import okhttp3.internal.connection.RealConnection

public class RetryAndFollowUpInterceptor(client: OkHttpClient) : Interceptor {
   private final val client: OkHttpClient

   init {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      super();
      this.client = var1;
   }

   private fun buildRedirectRequest(userResponse: Response, method: String): Request? {
      val var5: Boolean = this.client.followRedirects();
      var var6: RequestBody = null;
      if (!var5) {
         return null;
      } else {
         val var7: java.lang.String = Response.header$default(var1, "Location", null, 2, null);
         if (var7 != null) {
            val var8: HttpUrl = var1.request().url().resolve(var7);
            if (var8 != null) {
               if (!(var8.scheme() == var1.request().url().scheme()) && !this.client.followSslRedirects()) {
                  return null;
               }

               val var9: Request.Builder = var1.request().newBuilder();
               if (HttpMethod.permitsRequestBody(var2)) {
                  val var4: Int = var1.code();
                  val var3: Boolean;
                  if (!HttpMethod.INSTANCE.redirectsWithBody(var2) && var4 != 308 && var4 != 307) {
                     var3 = false;
                  } else {
                     var3 = true;
                  }

                  if (HttpMethod.INSTANCE.redirectsToGet(var2) && var4 != 308 && var4 != 307) {
                     var9.method("GET", null);
                  } else {
                     if (var3) {
                        var6 = var1.request().body();
                     }

                     var9.method(var2, var6);
                  }

                  if (!var3) {
                     var9.removeHeader("Transfer-Encoding");
                     var9.removeHeader("Content-Length");
                     var9.removeHeader("Content-Type");
                  }
               }

               if (!Util.canReuseConnectionFor(var1.request().url(), var8)) {
                  var9.removeHeader("Authorization");
               }

               return var9.url(var8).build();
            }
         }

         return null;
      }
   }

   @Throws(java/io/IOException::class)
   private fun followUpRequest(userResponse: Response, exchange: Exchange?): Request? {
      var var9: Route;
      label92: {
         if (var2 != null) {
            val var4: RealConnection = var2.getConnection$okhttp();
            if (var4 != null) {
               var9 = var4.route();
               break label92;
            }
         }

         var9 = null;
      }

      val var3: Int = var1.code();
      val var5: java.lang.String = var1.request().method();
      if (var3 == 307 || var3 == 308) {
         return this.buildRedirectRequest(var1, var5);
      } else if (var3 == 401) {
         return this.client.authenticator().authenticate(var9, var1);
      } else if (var3 == 421) {
         val var10: RequestBody = var1.request().body();
         if (var10 != null && var10.isOneShot()) {
            return null;
         } else if (var2 != null && var2.isCoalescedConnection$okhttp()) {
            var2.getConnection$okhttp().noCoalescedConnections$okhttp();
            return var1.request();
         } else {
            return null;
         }
      } else if (var3 == 503) {
         val var8: Response = var1.priorResponse();
         if (var8 != null && var8.code() == 503) {
            return null;
         } else {
            return if (this.retryAfter(var1, Integer.MAX_VALUE) == 0) var1.request() else null;
         }
      } else if (var3 == 407) {
         if (var9 == null) {
            Intrinsics.throwNpe();
         }

         if (var9.proxy().type() === Type.HTTP) {
            return this.client.proxyAuthenticator().authenticate(var9, var1);
         } else {
            throw (new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy")) as java.lang.Throwable;
         }
      } else if (var3 == 408) {
         if (!this.client.retryOnConnectionFailure()) {
            return null;
         } else {
            val var6: RequestBody = var1.request().body();
            if (var6 != null && var6.isOneShot()) {
               return null;
            } else {
               val var7: Response = var1.priorResponse();
               if (var7 != null && var7.code() == 408) {
                  return null;
               } else {
                  return if (this.retryAfter(var1, 0) > 0) null else var1.request();
               }
            }
         }
      } else {
         switch (var3) {
            case 300:
            case 301:
            case 302:
            case 303:
               return this.buildRedirectRequest(var1, var5);
            default:
               return null;
         }
      }
   }

   private fun isRecoverable(e: IOException, requestSendStarted: Boolean): Boolean {
      if (var1 is ProtocolException) {
         return false;
      } else if (var1 is InterruptedIOException) {
         var var5: Boolean = false;
         if (var1 is SocketTimeoutException) {
            var5 = false;
            if (!var2) {
               var5 = true;
            }
         }

         return var5;
      } else if (var1 is SSLHandshakeException && var1.getCause() is CertificateException) {
         return false;
      } else {
         return var1 !is SSLPeerUnverifiedException;
      }
   }

   private fun recover(e: IOException, call: RealCall, userRequest: Request, requestSendStarted: Boolean): Boolean {
      if (!this.client.retryOnConnectionFailure()) {
         return false;
      } else if (var4 && this.requestIsOneShot(var1, var3)) {
         return false;
      } else if (!this.isRecoverable(var1, var4)) {
         return false;
      } else {
         return var2.retryAfterFailure();
      }
   }

   private fun requestIsOneShot(e: IOException, userRequest: Request): Boolean {
      val var4: RequestBody = var2.body();
      val var3: Boolean;
      if ((var4 == null || !var4.isOneShot()) && var1 !is FileNotFoundException) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   private fun retryAfter(userResponse: Response, defaultDelay: Int): Int {
      val var3: java.lang.String = Response.header$default(var1, "Retry-After", null, 2, null);
      if (var3 != null) {
         if (new Regex("\\d+").matches(var3)) {
            val var5: Int = Integer.valueOf(var3);
            Intrinsics.checkExpressionValueIsNotNull(var5, "Integer.valueOf(header)");
            return var5;
         } else {
            return Integer.MAX_VALUE;
         }
      } else {
         return var2;
      }
   }

   @Throws(java/io/IOException::class)
   public override fun intercept(chain: Chain): Response {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 000: aload 1
      // 001: ldc_w "chain"
      // 004: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 007: aload 1
      // 008: checkcast okhttp3/internal/http/RealInterceptorChain
      // 00b: astore 9
      // 00d: aload 9
      // 00f: invokevirtual okhttp3/internal/http/RealInterceptorChain.getRequest$okhttp ()Lokhttp3/Request;
      // 012: astore 6
      // 014: aload 9
      // 016: invokevirtual okhttp3/internal/http/RealInterceptorChain.getCall$okhttp ()Lokhttp3/internal/connection/RealCall;
      // 019: astore 8
      // 01b: aconst_null
      // 01c: checkcast okhttp3/Response
      // 01f: astore 1
      // 020: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
      // 023: astore 4
      // 025: aconst_null
      // 026: astore 5
      // 028: bipush 1
      // 029: istore 3
      // 02a: bipush 0
      // 02b: istore 2
      // 02c: aload 8
      // 02e: aload 6
      // 030: iload 3
      // 031: invokevirtual okhttp3/internal/connection/RealCall.enterNetworkInterceptorExchange (Lokhttp3/Request;Z)V
      // 034: aload 8
      // 036: invokevirtual okhttp3/internal/connection/RealCall.isCanceled ()Z
      // 039: istore 3
      // 03a: iload 3
      // 03b: ifne 16b
      // 03e: aload 9
      // 040: aload 6
      // 042: invokevirtual okhttp3/internal/http/RealInterceptorChain.proceed (Lokhttp3/Request;)Lokhttp3/Response;
      // 045: astore 7
      // 047: aload 7
      // 049: astore 1
      // 04a: aload 5
      // 04c: ifnull 067
      // 04f: aload 7
      // 051: invokevirtual okhttp3/Response.newBuilder ()Lokhttp3/Response$Builder;
      // 054: aload 5
      // 056: invokevirtual okhttp3/Response.newBuilder ()Lokhttp3/Response$Builder;
      // 059: aconst_null
      // 05a: invokevirtual okhttp3/Response$Builder.body (Lokhttp3/ResponseBody;)Lokhttp3/Response$Builder;
      // 05d: invokevirtual okhttp3/Response$Builder.build ()Lokhttp3/Response;
      // 060: invokevirtual okhttp3/Response$Builder.priorResponse (Lokhttp3/Response;)Lokhttp3/Response$Builder;
      // 063: invokevirtual okhttp3/Response$Builder.build ()Lokhttp3/Response;
      // 066: astore 1
      // 067: aload 8
      // 069: invokevirtual okhttp3/internal/connection/RealCall.getInterceptorScopedExchange$okhttp ()Lokhttp3/internal/connection/Exchange;
      // 06c: astore 5
      // 06e: aload 0
      // 06f: aload 1
      // 070: aload 5
      // 072: invokespecial okhttp3/internal/http/RetryAndFollowUpInterceptor.followUpRequest (Lokhttp3/Response;Lokhttp3/internal/connection/Exchange;)Lokhttp3/Request;
      // 075: astore 6
      // 077: aload 6
      // 079: ifnonnull 096
      // 07c: aload 5
      // 07e: ifnull 08e
      // 081: aload 5
      // 083: invokevirtual okhttp3/internal/connection/Exchange.isDuplex$okhttp ()Z
      // 086: ifeq 08e
      // 089: aload 8
      // 08b: invokevirtual okhttp3/internal/connection/RealCall.timeoutEarlyExit ()V
      // 08e: aload 8
      // 090: bipush 0
      // 091: invokevirtual okhttp3/internal/connection/RealCall.exitNetworkInterceptorExchange$okhttp (Z)V
      // 094: aload 1
      // 095: areturn
      // 096: aload 6
      // 098: invokevirtual okhttp3/Request.body ()Lokhttp3/RequestBody;
      // 09b: astore 5
      // 09d: aload 5
      // 09f: ifnull 0b4
      // 0a2: aload 5
      // 0a4: invokevirtual okhttp3/RequestBody.isOneShot ()Z
      // 0a7: istore 3
      // 0a8: iload 3
      // 0a9: ifeq 0b4
      // 0ac: aload 8
      // 0ae: bipush 0
      // 0af: invokevirtual okhttp3/internal/connection/RealCall.exitNetworkInterceptorExchange$okhttp (Z)V
      // 0b2: aload 1
      // 0b3: areturn
      // 0b4: aload 1
      // 0b5: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 0b8: astore 5
      // 0ba: aload 5
      // 0bc: ifnull 0c7
      // 0bf: aload 5
      // 0c1: checkcast java/io/Closeable
      // 0c4: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/io/Closeable;)V
      // 0c7: iinc 2 1
      // 0ca: iload 2
      // 0cb: bipush 20
      // 0cd: if_icmpgt 0de
      // 0d0: aload 8
      // 0d2: bipush 1
      // 0d3: invokevirtual okhttp3/internal/connection/RealCall.exitNetworkInterceptorExchange$okhttp (Z)V
      // 0d6: bipush 1
      // 0d7: istore 3
      // 0d8: aload 1
      // 0d9: astore 5
      // 0db: goto 02c
      // 0de: new java/net/ProtocolException
      // 0e1: astore 4
      // 0e3: new java/lang/StringBuilder
      // 0e6: astore 1
      // 0e7: aload 1
      // 0e8: invokespecial java/lang/StringBuilder.<init> ()V
      // 0eb: aload 1
      // 0ec: ldc_w "Too many follow-up requests: "
      // 0ef: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0f2: pop
      // 0f3: aload 1
      // 0f4: iload 2
      // 0f5: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0f8: pop
      // 0f9: aload 4
      // 0fb: aload 1
      // 0fc: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0ff: invokespecial java/net/ProtocolException.<init> (Ljava/lang/String;)V
      // 102: aload 4
      // 104: checkcast java/lang/Throwable
      // 107: athrow
      // 108: astore 1
      // 109: aload 0
      // 10a: aload 1
      // 10b: aload 8
      // 10d: aload 6
      // 10f: aload 1
      // 110: instanceof okhttp3/internal/http2/ConnectionShutdownException
      // 113: bipush 1
      // 114: ixor
      // 115: invokespecial okhttp3/internal/http/RetryAndFollowUpInterceptor.recover (Ljava/io/IOException;Lokhttp3/internal/connection/RealCall;Lokhttp3/Request;Z)Z
      // 118: ifeq 128
      // 11b: aload 4
      // 11d: checkcast java/util/Collection
      // 120: aload 1
      // 121: invokestatic kotlin/collections/CollectionsKt.plus (Ljava/util/Collection;Ljava/lang/Object;)Ljava/util/List;
      // 124: astore 1
      // 125: goto 150
      // 128: aload 1
      // 129: checkcast java/lang/Exception
      // 12c: aload 4
      // 12e: invokestatic okhttp3/internal/Util.withSuppressed (Ljava/lang/Exception;Ljava/util/List;)Ljava/lang/Throwable;
      // 131: athrow
      // 132: astore 1
      // 133: aload 0
      // 134: aload 1
      // 135: invokevirtual okhttp3/internal/connection/RouteException.getLastConnectException ()Ljava/io/IOException;
      // 138: aload 8
      // 13a: aload 6
      // 13c: bipush 0
      // 13d: invokespecial okhttp3/internal/http/RetryAndFollowUpInterceptor.recover (Ljava/io/IOException;Lokhttp3/internal/connection/RealCall;Lokhttp3/Request;Z)Z
      // 140: ifeq 15e
      // 143: aload 4
      // 145: checkcast java/util/Collection
      // 148: aload 1
      // 149: invokevirtual okhttp3/internal/connection/RouteException.getFirstConnectException ()Ljava/io/IOException;
      // 14c: invokestatic kotlin/collections/CollectionsKt.plus (Ljava/util/Collection;Ljava/lang/Object;)Ljava/util/List;
      // 14f: astore 1
      // 150: aload 8
      // 152: bipush 1
      // 153: invokevirtual okhttp3/internal/connection/RealCall.exitNetworkInterceptorExchange$okhttp (Z)V
      // 156: bipush 0
      // 157: istore 3
      // 158: aload 1
      // 159: astore 4
      // 15b: goto 02c
      // 15e: aload 1
      // 15f: invokevirtual okhttp3/internal/connection/RouteException.getFirstConnectException ()Ljava/io/IOException;
      // 162: checkcast java/lang/Exception
      // 165: aload 4
      // 167: invokestatic okhttp3/internal/Util.withSuppressed (Ljava/lang/Exception;Ljava/util/List;)Ljava/lang/Throwable;
      // 16a: athrow
      // 16b: new java/io/IOException
      // 16e: astore 1
      // 16f: aload 1
      // 170: ldc_w "Canceled"
      // 173: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 176: aload 1
      // 177: checkcast java/lang/Throwable
      // 17a: athrow
      // 17b: astore 1
      // 17c: aload 8
      // 17e: bipush 1
      // 17f: invokevirtual okhttp3/internal/connection/RealCall.exitNetworkInterceptorExchange$okhttp (Z)V
      // 182: aload 1
      // 183: athrow
   }

   public companion object {
      private const val MAX_FOLLOW_UPS: Int
   }
}
