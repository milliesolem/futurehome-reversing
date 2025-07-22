package okhttp3.internal.http2

import java.io.IOException
import kotlin.jvm.internal.Intrinsics

public class StreamResetException(errorCode: ErrorCode) : IOException {
   public final val errorCode: ErrorCode

   init {
      Intrinsics.checkParameterIsNotNull(var1, "errorCode");
      val var2: StringBuilder = new StringBuilder("stream was reset: ");
      var2.append(var1);
      super(var2.toString());
      this.errorCode = var1;
   }
}
