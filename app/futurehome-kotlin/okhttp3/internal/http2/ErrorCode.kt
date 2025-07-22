package okhttp3.internal.http2

public enum class ErrorCode(httpCode: Int) {
   CANCEL,
   COMPRESSION_ERROR,
   CONNECT_ERROR,
   ENHANCE_YOUR_CALM,
   FLOW_CONTROL_ERROR,
   FRAME_SIZE_ERROR,
   HTTP_1_1_REQUIRED,
   INADEQUATE_SECURITY,
   INTERNAL_ERROR,
   NO_ERROR,
   PROTOCOL_ERROR,
   REFUSED_STREAM,
   SETTINGS_TIMEOUT,
   STREAM_CLOSED
   public final val httpCode: Int
   @JvmStatic
   private ErrorCode[] $VALUES;
   @JvmStatic
   public ErrorCode.Companion Companion = new ErrorCode.Companion(null);

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun {
      val var4: ErrorCode = new ErrorCode(0);
      NO_ERROR = var4;
      val var2: ErrorCode = new ErrorCode(1);
      PROTOCOL_ERROR = var2;
      val var9: ErrorCode = new ErrorCode(2);
      INTERNAL_ERROR = var9;
      val var7: ErrorCode = new ErrorCode(3);
      FLOW_CONTROL_ERROR = var7;
      val var1: ErrorCode = new ErrorCode(4);
      SETTINGS_TIMEOUT = var1;
      val var11: ErrorCode = new ErrorCode(5);
      STREAM_CLOSED = var11;
      val var8: ErrorCode = new ErrorCode(6);
      FRAME_SIZE_ERROR = var8;
      val var12: ErrorCode = new ErrorCode(7);
      REFUSED_STREAM = var12;
      val var10: ErrorCode = new ErrorCode(8);
      CANCEL = var10;
      val var3: ErrorCode = new ErrorCode(9);
      COMPRESSION_ERROR = var3;
      val var5: ErrorCode = new ErrorCode(10);
      CONNECT_ERROR = var5;
      val var0: ErrorCode = new ErrorCode(11);
      ENHANCE_YOUR_CALM = var0;
      val var6: ErrorCode = new ErrorCode(12);
      INADEQUATE_SECURITY = var6;
      val var13: ErrorCode = new ErrorCode(13);
      HTTP_1_1_REQUIRED = var13;
      $VALUES = new ErrorCode[]{var4, var2, var9, var7, var1, var11, var8, var12, var10, var3, var5, var0, var6, var13};
   }

   init {
      this.httpCode = var3;
   }

   public companion object {
      public fun fromHttp2(code: Int): ErrorCode? {
         val var5: Array<ErrorCode> = ErrorCode.values();
         val var3: Int = var5.length;
         var var2: Int = 0;

         var var4: ErrorCode;
         while (true) {
            if (var2 >= var3) {
               var4 = null;
               break;
            }

            var4 = var5[var2];
            if (var5[var2].getHttpCode() == var1) {
               break;
            }

            var2++;
         }

         return var4;
      }
   }
}
