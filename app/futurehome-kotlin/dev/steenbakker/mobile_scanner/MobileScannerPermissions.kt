package dev.steenbakker.mobile_scanner

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener

public class MobileScannerPermissions {
   private final var listener: RequestPermissionsResultListener?
   private final var ongoing: Boolean

   public fun getPermissionListener(): RequestPermissionsResultListener? {
      return this.listener;
   }

   public fun hasCameraPermission(activity: Activity): Int {
      val var2: Byte;
      if (ContextCompat.checkSelfPermission(var1 as Context, "android.permission.CAMERA") == 0) {
         var2 = 1;
      } else {
         var2 = 2;
      }

      return var2;
   }

   public fun requestPermission(
      activity: Activity,
      addPermissionListener: (RequestPermissionsResultListener) -> Unit,
      callback: dev.steenbakker.mobile_scanner.MobileScannerPermissions.ResultCallback
   ) {
      if (this.ongoing) {
         var3.onResult("MOBILE_SCANNER_CAMERA_PERMISSION_REQUEST_PENDING");
      } else if (this.hasCameraPermission(var1) == 1) {
         var3.onResult(null);
      } else {
         if (this.listener == null) {
            val var4: PluginRegistry.RequestPermissionsResultListener = new MobileScannerPermissionsListener(
               new MobileScannerPermissions.ResultCallback(this, var3) {
                  final MobileScannerPermissions.ResultCallback $callback;
                  final MobileScannerPermissions this$0;

                  {
                     this.this$0 = var1;
                     this.$callback = var2;
                  }

                  @Override
                  public void onResult(java.lang.String var1) {
                     MobileScannerPermissions.access$setOngoing$p(this.this$0, false);
                     MobileScannerPermissions.access$setListener$p(this.this$0, null);
                     this.$callback.onResult(var1);
                  }
               }
            );
            this.listener = var4;
            var2.invoke(var4);
         }

         this.ongoing = true;
         ActivityCompat.requestPermissions(var1, new java.lang.String[]{"android.permission.CAMERA"}, 1926);
      }
   }

   public companion object {
      public const val REQUEST_CODE: Int
   }

   public interface ResultCallback {
      public abstract fun onResult(errorCode: String?) {
      }
   }
}
