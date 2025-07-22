package io.flutter.plugin.common;

public class FlutterException extends RuntimeException {
   private static final String TAG = "FlutterException#";
   public final String code;
   public final Object details;

   FlutterException(String var1, String var2, Object var3) {
      super(var2);
      this.code = var1;
      this.details = var3;
   }
}
