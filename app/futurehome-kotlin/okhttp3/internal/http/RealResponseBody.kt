package okhttp3.internal.http

import kotlin.jvm.internal.Intrinsics
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

public class RealResponseBody(contentTypeString: String?, contentLength: Long, source: BufferedSource) : ResponseBody {
   private final val contentLength: Long
   private final val contentTypeString: String?
   private final val source: BufferedSource

   init {
      Intrinsics.checkParameterIsNotNull(var4, "source");
      super();
      this.contentTypeString = var1;
      this.contentLength = var2;
      this.source = var4;
   }

   public override fun contentLength(): Long {
      return this.contentLength;
   }

   public override fun contentType(): MediaType? {
      val var2: MediaType;
      if (this.contentTypeString != null) {
         var2 = MediaType.Companion.parse(this.contentTypeString);
      } else {
         var2 = null;
      }

      return var2;
   }

   public override fun source(): BufferedSource {
      return this.source;
   }
}
