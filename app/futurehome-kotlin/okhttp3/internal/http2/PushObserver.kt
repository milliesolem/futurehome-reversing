package okhttp3.internal.http2

import kotlin.jvm.internal.Intrinsics
import okio.BufferedSource

public interface PushObserver {
   @Throws(java/io/IOException::class)
   public abstract fun onData(streamId: Int, source: BufferedSource, byteCount: Int, last: Boolean): Boolean {
   }

   public abstract fun onHeaders(streamId: Int, responseHeaders: List<Header>, last: Boolean): Boolean {
   }

   public abstract fun onRequest(streamId: Int, requestHeaders: List<Header>): Boolean {
   }

   public abstract fun onReset(streamId: Int, errorCode: ErrorCode) {
   }

   public companion object {
      public final val CANCEL: PushObserver

      private class PushObserverCancel : PushObserver {
         @Throws(java/io/IOException::class)
         public override fun onData(streamId: Int, source: BufferedSource, byteCount: Int, last: Boolean): Boolean {
            Intrinsics.checkParameterIsNotNull(var2, "source");
            var2.skip((long)var3);
            return true;
         }

         public override fun onHeaders(streamId: Int, responseHeaders: List<Header>, last: Boolean): Boolean {
            Intrinsics.checkParameterIsNotNull(var2, "responseHeaders");
            return true;
         }

         public override fun onRequest(streamId: Int, requestHeaders: List<Header>): Boolean {
            Intrinsics.checkParameterIsNotNull(var2, "requestHeaders");
            return true;
         }

         public override fun onReset(streamId: Int, errorCode: ErrorCode) {
            Intrinsics.checkParameterIsNotNull(var2, "errorCode");
         }
      }
   }
}
