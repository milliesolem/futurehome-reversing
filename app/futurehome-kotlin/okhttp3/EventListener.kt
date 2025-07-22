package okhttp3

import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import kotlin.jvm.internal.Intrinsics

public abstract class EventListener {
   public open fun cacheConditionalHit(call: Call, cachedResponse: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "cachedResponse");
   }

   public open fun cacheHit(call: Call, response: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "response");
   }

   public open fun cacheMiss(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun callEnd(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun callFailed(call: Call, ioe: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "ioe");
   }

   public open fun callStart(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun canceled(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "inetSocketAddress");
      Intrinsics.checkParameterIsNotNull(var3, "proxy");
   }

   public open fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?, ioe: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "inetSocketAddress");
      Intrinsics.checkParameterIsNotNull(var3, "proxy");
      Intrinsics.checkParameterIsNotNull(var5, "ioe");
   }

   public open fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "inetSocketAddress");
      Intrinsics.checkParameterIsNotNull(var3, "proxy");
   }

   public open fun connectionAcquired(call: Call, connection: Connection) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "connection");
   }

   public open fun connectionReleased(call: Call, connection: Connection) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "connection");
   }

   public open fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "domainName");
      Intrinsics.checkParameterIsNotNull(var3, "inetAddressList");
   }

   public open fun dnsStart(call: Call, domainName: String) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "domainName");
   }

   public open fun proxySelectEnd(call: Call, url: HttpUrl, proxies: List<Proxy>) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "url");
      Intrinsics.checkParameterIsNotNull(var3, "proxies");
   }

   public open fun proxySelectStart(call: Call, url: HttpUrl) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "url");
   }

   public open fun requestBodyEnd(call: Call, byteCount: Long) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun requestBodyStart(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun requestFailed(call: Call, ioe: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "ioe");
   }

   public open fun requestHeadersEnd(call: Call, request: Request) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "request");
   }

   public open fun requestHeadersStart(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun responseBodyEnd(call: Call, byteCount: Long) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun responseBodyStart(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun responseFailed(call: Call, ioe: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "ioe");
   }

   public open fun responseHeadersEnd(call: Call, response: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "response");
   }

   public open fun responseHeadersStart(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun satisfactionFailure(call: Call, response: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "response");
   }

   public open fun secureConnectEnd(call: Call, handshake: Handshake?) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public open fun secureConnectStart(call: Call) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
   }

   public companion object {
      public final val NONE: EventListener
   }

   public interface Factory {
      public abstract fun create(call: Call): EventListener {
      }
   }
}
