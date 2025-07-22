package io.flutter.plugins.firebase.messaging;

import android.app.Activity;
import androidx.core.app.ActivityCompat;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;

class FlutterFirebasePermissionManager implements PluginRegistry.RequestPermissionsResultListener {
   private final int permissionCode = 240;
   private boolean requestInProgress = false;
   private FlutterFirebasePermissionManager.RequestPermissionsSuccessCallback successCallback;

   @Override
   public boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      boolean var5 = this.requestInProgress;
      byte var4 = 0;
      if (var5 && var1 == 240) {
         FlutterFirebasePermissionManager.RequestPermissionsSuccessCallback var7 = this.successCallback;
         if (var7 != null) {
            this.requestInProgress = false;
            byte var6 = var4;
            if (var3.length > 0) {
               var6 = var4;
               if (var3[0] == 0) {
                  var6 = 1;
               }
            }

            var7.onSuccess(var6);
            return true;
         }
      }

      return false;
   }

   public void requestPermissions(Activity var1, FlutterFirebasePermissionManager.RequestPermissionsSuccessCallback var2, ErrorCallback var3) {
      if (this.requestInProgress) {
         var3.onError("A request for permissions is already running, please wait for it to finish before doing another request.");
      } else if (var1 == null) {
         var3.onError("Unable to detect current Android Activity.");
      } else {
         this.successCallback = var2;
         ArrayList var4 = new ArrayList();
         var4.add("android.permission.POST_NOTIFICATIONS");
         String[] var5 = var4.toArray(new String[0]);
         if (!this.requestInProgress) {
            ActivityCompat.requestPermissions(var1, var5, 240);
            this.requestInProgress = true;
         }
      }
   }

   @FunctionalInterface
   interface RequestPermissionsSuccessCallback {
      void onSuccess(int var1);
   }
}
