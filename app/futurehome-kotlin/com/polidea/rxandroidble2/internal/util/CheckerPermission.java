package com.polidea.rxandroidble2.internal.util;

import android.content.Context;
import android.os.Process;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.ClientScope;
import java.util.HashSet;
import java.util.Set;

@ClientScope
public class CheckerPermission {
   private final Context context;
   private final Set<String> grantedPermissions = new HashSet<>();

   @Inject
   CheckerPermission(Context var1) {
      this.context = var1;
   }

   private boolean isPermissionGranted(String var1) {
      if (var1 != null) {
         boolean var3 = this.grantedPermissions.contains(var1);
         boolean var2 = true;
         if (var3) {
            return true;
         } else {
            if (this.context.checkPermission(var1, Process.myPid(), Process.myUid()) != 0) {
               var2 = false;
            }

            if (var2) {
               this.grantedPermissions.add(var1);
            }

            return var2;
         }
      } else {
         throw new IllegalArgumentException("permission is null");
      }
   }

   boolean isAnyPermissionGranted(String[] var1) {
      int var3 = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (this.isPermissionGranted(var1[var2])) {
            return true;
         }
      }

      return false;
   }
}
