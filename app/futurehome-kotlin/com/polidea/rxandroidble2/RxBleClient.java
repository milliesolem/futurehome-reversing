package com.polidea.rxandroidble2;

import android.content.Context;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.scan.BackgroundScanner;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.Observable;
import java.util.Set;
import java.util.UUID;

public abstract class RxBleClient {
   public static RxBleClient create(Context var0) {
      return DaggerClientComponent.builder().applicationContext(var0.getApplicationContext()).build().rxBleClient();
   }

   @Deprecated
   public static void setLogLevel(int var0) {
      RxBleLog.setLogLevel(var0);
   }

   public static void updateLogOptions(LogOptions var0) {
      RxBleLog.updateLogOptions(var0);
   }

   public abstract BackgroundScanner getBackgroundScanner();

   public abstract RxBleDevice getBleDevice(String var1);

   public abstract Set<RxBleDevice> getBondedDevices();

   public abstract Set<RxBleDevice> getConnectedPeripherals();

   public abstract String[] getRecommendedConnectRuntimePermissions();

   public abstract String[] getRecommendedScanRuntimePermissions();

   public abstract RxBleClient.State getState();

   public abstract boolean isConnectRuntimePermissionGranted();

   public abstract boolean isScanRuntimePermissionGranted();

   public abstract Observable<RxBleClient.State> observeStateChanges();

   public abstract Observable<ScanResult> scanBleDevices(ScanSettings var1, ScanFilter... var2);

   @Deprecated
   public abstract Observable<RxBleScanResult> scanBleDevices(UUID... var1);

   public static enum State {
      BLUETOOTH_NOT_AVAILABLE,
      BLUETOOTH_NOT_ENABLED,
      LOCATION_PERMISSION_NOT_GRANTED,
      LOCATION_SERVICES_NOT_ENABLED,
      READY;
      private static final RxBleClient.State[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         RxBleClient.State var1 = new RxBleClient.State();
         BLUETOOTH_NOT_AVAILABLE = var1;
         RxBleClient.State var2 = new RxBleClient.State();
         LOCATION_PERMISSION_NOT_GRANTED = var2;
         RxBleClient.State var4 = new RxBleClient.State();
         BLUETOOTH_NOT_ENABLED = var4;
         RxBleClient.State var3 = new RxBleClient.State();
         LOCATION_SERVICES_NOT_ENABLED = var3;
         RxBleClient.State var0 = new RxBleClient.State();
         READY = var0;
         $VALUES = new RxBleClient.State[]{var1, var2, var4, var3, var0};
      }
   }
}
