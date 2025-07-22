package io.sentry.android.core.internal.util;

import io.sentry.protocol.Device;

public final class DeviceOrientations {
   private DeviceOrientations() {
   }

   public static Device.DeviceOrientation getOrientation(int var0) {
      if (var0 != 1) {
         return var0 != 2 ? null : Device.DeviceOrientation.LANDSCAPE;
      } else {
         return Device.DeviceOrientation.PORTRAIT;
      }
   }
}
