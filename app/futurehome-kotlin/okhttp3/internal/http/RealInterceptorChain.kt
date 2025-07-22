package okhttp3.internal.http

import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Interceptor.Chain
import okhttp3.internal.Util
import okhttp3.internal.connection.Exchange
import okhttp3.internal.connection.RealCall
import okhttp3.internal.connection.RealConnection

public class RealInterceptorChain(call: RealCall,
      interceptors: List<Interceptor>,
      index: Int,
      exchange: Exchange?,
      request: Request,
      connectTimeoutMillis: Int,
      readTimeoutMillis: Int,
      writeTimeoutMillis: Int
   ) :
   Interceptor.Chain {
   internal final val call: RealCall
   private final var calls: Int
   internal final val connectTimeoutMillis: Int
   internal final val exchange: Exchange?
   private final val index: Int
   private final val interceptors: List<Interceptor>
   internal final val readTimeoutMillis: Int
   internal final val request: Request
   internal final val writeTimeoutMillis: Int

   init {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "interceptors");
      Intrinsics.checkParameterIsNotNull(var5, "request");
      super();
      this.call = var1;
      this.interceptors = var2;
      this.index = var3;
      this.exchange = var4;
      this.request = var5;
      this.connectTimeoutMillis = var6;
      this.readTimeoutMillis = var7;
      this.writeTimeoutMillis = var8;
   }

   public override fun call(): Call {
      return this.call;
   }

   public override fun connectTimeoutMillis(): Int {
      return this.connectTimeoutMillis;
   }

   public override fun connection(): Connection? {
      val var2: RealConnection;
      if (this.exchange != null) {
         var2 = this.exchange.getConnection$okhttp();
      } else {
         var2 = null;
      }

      return var2;
   }

   internal fun copy(
      index: Int = ...,
      exchange: Exchange? = ...,
      request: Request = ...,
      connectTimeoutMillis: Int = ...,
      readTimeoutMillis: Int = ...,
      writeTimeoutMillis: Int = ...
   ): RealInterceptorChain {
      Intrinsics.checkParameterIsNotNull(var3, "request");
      return new RealInterceptorChain(this.call, this.interceptors, var1, var2, var3, var4, var5, var6);
   }

   @Throws(java/io/IOException::class)
   public override fun proceed(request: Request): Response {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      var var10: Boolean;
      if (this.index < this.interceptors.size()) {
         var10 = true;
      } else {
         var10 = false;
      }

      if (!var10) {
         throw (new IllegalStateException("Check failed.".toString())) as java.lang.Throwable;
      } else {
         this.calls++;
         if (this.exchange != null) {
            if (!this.exchange.getFinder$okhttp().sameHostAndPort(var1.url())) {
               val var9: StringBuilder = new StringBuilder("network interceptor ");
               var9.append(this.interceptors.get(this.index - 1));
               var9.append(" must retain the same host and port");
               throw (new IllegalStateException(var9.toString().toString())) as java.lang.Throwable;
            }

            if (this.calls == 1) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (!var10) {
               val var8: StringBuilder = new StringBuilder("network interceptor ");
               var8.append(this.interceptors.get(this.index - 1));
               var8.append(" must call proceed() exactly once");
               throw (new IllegalStateException(var8.toString().toString())) as java.lang.Throwable;
            }
         }

         val var14: RealInterceptorChain = copy$okhttp$default(this, this.index + 1, null, var1, 0, 0, 0, 58, null);
         val var7: Interceptor = this.interceptors.get(this.index);
         val var6: Response = var7.intercept(var14);
         if (var6 == null) {
            val var17: StringBuilder = new StringBuilder("interceptor ");
            var17.append(var7);
            var17.append(" returned null");
            throw (new NullPointerException(var17.toString())) as java.lang.Throwable;
         } else {
            if (this.exchange != null) {
               if (this.index + 1 < this.interceptors.size() && var14.calls != 1) {
                  var10 = false;
               } else {
                  var10 = true;
               }

               if (!var10) {
                  val var16: StringBuilder = new StringBuilder("network interceptor ");
                  var16.append(var7);
                  var16.append(" must call proceed() exactly once");
                  throw (new IllegalStateException(var16.toString().toString())) as java.lang.Throwable;
               }
            }

            var10 = false;
            if (var6.body() != null) {
               var10 = true;
            }

            if (var10) {
               return var6;
            } else {
               val var15: StringBuilder = new StringBuilder("interceptor ");
               var15.append(var7);
               var15.append(" returned a response with no body");
               throw (new IllegalStateException(var15.toString().toString())) as java.lang.Throwable;
            }
         }
      }
   }

   public override fun readTimeoutMillis(): Int {
      return this.readTimeoutMillis;
   }

   public override fun request(): Request {
      return this.request;
   }

   public override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Chain {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      val var3: Boolean;
      if (this.exchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return copy$okhttp$default(this, 0, null, null, Util.checkDuration("connectTimeout", (long)var1, var2), 0, 0, 55, null);
      } else {
         throw (new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString())) as java.lang.Throwable;
      }
   }

   public override fun withReadTimeout(timeout: Int, unit: TimeUnit): Chain {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      val var3: Boolean;
      if (this.exchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return copy$okhttp$default(this, 0, null, null, 0, Util.checkDuration("readTimeout", (long)var1, var2), 0, 47, null);
      } else {
         throw (new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString())) as java.lang.Throwable;
      }
   }

   public override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Chain {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      val var3: Boolean;
      if (this.exchange == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return copy$okhttp$default(this, 0, null, null, 0, 0, Util.checkDuration("writeTimeout", (long)var1, var2), 31, null);
      } else {
         throw (new IllegalStateException("Timeouts can't be adjusted in a network interceptor".toString())) as java.lang.Throwable;
      }
   }

   public override fun writeTimeoutMillis(): Int {
      return this.writeTimeoutMillis;
   }
}
