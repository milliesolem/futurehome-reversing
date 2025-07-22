package okhttp3

import java.util.concurrent.TimeUnit
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics

public interface Interceptor {
   @Throws(java/io/IOException::class)
   public abstract fun intercept(chain: okhttp3.Interceptor.Chain): Response {
   }

   public interface Chain {
      public abstract fun call(): Call {
      }

      public abstract fun connectTimeoutMillis(): Int {
      }

      public abstract fun connection(): Connection? {
      }

      @Throws(java/io/IOException::class)
      public abstract fun proceed(request: Request): Response {
      }

      public abstract fun readTimeoutMillis(): Int {
      }

      public abstract fun request(): Request {
      }

      public abstract fun withConnectTimeout(timeout: Int, unit: TimeUnit): okhttp3.Interceptor.Chain {
      }

      public abstract fun withReadTimeout(timeout: Int, unit: TimeUnit): okhttp3.Interceptor.Chain {
      }

      public abstract fun withWriteTimeout(timeout: Int, unit: TimeUnit): okhttp3.Interceptor.Chain {
      }

      public abstract fun writeTimeoutMillis(): Int {
      }
   }

   public companion object {
      public inline operator fun invoke(crossinline block: (okhttp3.Interceptor.Chain) -> Response): Interceptor {
         Intrinsics.checkParameterIsNotNull(var1, "block");
         return new Interceptor(var1) {
            final Function1 $block;

            {
               this.$block = var1;
            }

            @Override
            public Response intercept(Interceptor.Chain var1) {
               Intrinsics.checkParameterIsNotNull(var1, "chain");
               return this.$block.invoke(var1) as Response;
            }
         };
      }
   }
}
