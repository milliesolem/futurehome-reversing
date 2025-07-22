package okhttp3.internal.http1

import kotlin.jvm.internal.Intrinsics
import okhttp3.Headers
import okio.BufferedSource

public class HeadersReader(source: BufferedSource) {
   private final var headerLimit: Long
   public final val source: BufferedSource

   init {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      super();
      this.source = var1;
      this.headerLimit = 262144;
   }

   public fun readHeaders(): Headers {
      val var2: Headers.Builder = new Headers.Builder();

      while (true) {
         val var1: java.lang.String = this.readLine();
         if (var1.length() == 0) {
            return var2.build();
         }

         var2.addLenient$okhttp(var1);
      }
   }

   public fun readLine(): String {
      val var1: java.lang.String = this.source.readUtf8LineStrict(this.headerLimit);
      this.headerLimit = this.headerLimit - var1.length();
      return var1;
   }

   public companion object {
      private const val HEADER_LIMIT: Int
   }
}
