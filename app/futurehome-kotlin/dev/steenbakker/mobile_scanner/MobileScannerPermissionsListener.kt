package dev.steenbakker.mobile_scanner

import dev.steenbakker.mobile_scanner.MobileScannerPermissions.ResultCallback
import io.flutter.plugin.common.PluginRegistry

internal class MobileScannerPermissionsListener(resultCallback: ResultCallback) : PluginRegistry.RequestPermissionsResultListener {
   private final val resultCallback: ResultCallback
   private final var alreadyCalled: Boolean

   init {
      this.resultCallback = var1;
   }

   public override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
      if (!this.alreadyCalled && var1 == 1926) {
         this.alreadyCalled = true;
         if (var3.length != 0 && var3[0] == 0) {
            this.resultCallback.onResult(null);
         } else {
            this.resultCallback.onResult("MOBILE_SCANNER_CAMERA_PERMISSION_DENIED");
         }

         return true;
      } else {
         return false;
      }
   }
}
