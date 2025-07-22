package com.signify.hue.flutterreactiveble.utils

import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleClient.State
import com.signify.hue.flutterreactiveble.ble.BleStatus
import com.signify.hue.flutterreactiveble.ble.ConnectionPriority

public fun State.toBleState(): BleStatus {
   val var1: Int = BleWrapperExtensionsKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()];
   val var2: BleStatus;
   if (var1 != 1) {
      if (var1 != 2) {
         if (var1 != 3) {
            if (var1 != 4) {
               if (var1 != 5) {
                  throw new NoWhenBranchMatchedException();
               }

               var2 = BleStatus.READY;
            } else {
               var2 = BleStatus.LOCATION_SERVICES_DISABLED;
            }
         } else {
            var2 = BleStatus.POWERED_OFF;
         }
      } else {
         var2 = BleStatus.UNAUTHORIZED;
      }
   } else {
      var2 = BleStatus.UNSUPPORTED;
   }

   return var2;
}

public fun Int.toConnectionPriority(): ConnectionPriority {
   val var1: ConnectionPriority;
   if (var0 != 0) {
      if (var0 != 1) {
         if (var0 != 2) {
            var1 = ConnectionPriority.BALANCED;
         } else {
            var1 = ConnectionPriority.LOW_POWER;
         }
      } else {
         var1 = ConnectionPriority.HIGH_PERFORMACE;
      }
   } else {
      var1 = ConnectionPriority.BALANCED;
   }

   return var1;
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   public int[] $EnumSwitchMapping$0;

   @JvmStatic
   fun {
      val var0: IntArray = new int[RxBleClient.State.values().length];

      try {
         var0[RxBleClient.State.BLUETOOTH_NOT_AVAILABLE.ordinal()] = 1;
      } catch (var6: NoSuchFieldError) {
      }

      try {
         var0[RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED.ordinal()] = 2;
      } catch (var5: NoSuchFieldError) {
      }

      try {
         var0[RxBleClient.State.BLUETOOTH_NOT_ENABLED.ordinal()] = 3;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED.ordinal()] = 4;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[RxBleClient.State.READY.ordinal()] = 5;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
