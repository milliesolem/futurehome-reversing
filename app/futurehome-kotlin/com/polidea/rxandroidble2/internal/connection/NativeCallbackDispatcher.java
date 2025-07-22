package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.HiddenBluetoothGattCallback;

class NativeCallbackDispatcher {
   private BluetoothGattCallback nativeCallback;
   private HiddenBluetoothGattCallback nativeCallbackHidden;

   void notifyNativeChangedCallback(BluetoothGatt var1, BluetoothGattCharacteristic var2) {
      BluetoothGattCallback var3 = this.nativeCallback;
      if (var3 != null) {
         var3.onCharacteristicChanged(var1, var2);
      }
   }

   void notifyNativeConnectionStateCallback(BluetoothGatt var1, int var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onConnectionStateChange(var1, var2, var3);
      }
   }

   void notifyNativeDescriptorReadCallback(BluetoothGatt var1, BluetoothGattDescriptor var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onDescriptorRead(var1, var2, var3);
      }
   }

   void notifyNativeDescriptorWriteCallback(BluetoothGatt var1, BluetoothGattDescriptor var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onDescriptorWrite(var1, var2, var3);
      }
   }

   void notifyNativeMtuChangedCallback(BluetoothGatt var1, int var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onMtuChanged(var1, var2, var3);
      }
   }

   void notifyNativeParamsUpdateCallback(BluetoothGatt var1, int var2, int var3, int var4, int var5) {
      HiddenBluetoothGattCallback var6 = this.nativeCallbackHidden;
      if (var6 != null) {
         var6.onConnectionUpdated(var1, var2, var3, var4, var5);
      }
   }

   void notifyNativeReadCallback(BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onCharacteristicRead(var1, var2, var3);
      }
   }

   void notifyNativeReadRssiCallback(BluetoothGatt var1, int var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onReadRemoteRssi(var1, var2, var3);
      }
   }

   void notifyNativeReliableWriteCallback(BluetoothGatt var1, int var2) {
      BluetoothGattCallback var3 = this.nativeCallback;
      if (var3 != null) {
         var3.onReliableWriteCompleted(var1, var2);
      }
   }

   void notifyNativeServicesDiscoveredCallback(BluetoothGatt var1, int var2) {
      BluetoothGattCallback var3 = this.nativeCallback;
      if (var3 != null) {
         var3.onServicesDiscovered(var1, var2);
      }
   }

   void notifyNativeWriteCallback(BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3) {
      BluetoothGattCallback var4 = this.nativeCallback;
      if (var4 != null) {
         var4.onCharacteristicWrite(var1, var2, var3);
      }
   }

   void setNativeCallback(BluetoothGattCallback var1) {
      this.nativeCallback = var1;
   }

   void setNativeCallbackHidden(HiddenBluetoothGattCallback var1) {
      this.nativeCallbackHidden = var1;
   }
}
