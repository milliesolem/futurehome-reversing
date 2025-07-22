package okhttp3.internal.connection

import java.io.IOException
import kotlin.jvm.internal.Intrinsics

public class RouteException internal constructor(firstConnectException: IOException) : RuntimeException {
   public final val firstConnectException: IOException

   public final var lastConnectException: IOException
      private set

   init {
      Intrinsics.checkParameterIsNotNull(var1, "firstConnectException");
      super(var1);
      this.firstConnectException = var1;
      this.lastConnectException = var1;
   }

   public fun addConnectException(e: IOException) {
      Intrinsics.checkParameterIsNotNull(var1, "e");
      this.firstConnectException.addSuppressed(var1);
      this.lastConnectException = var1;
   }
}
