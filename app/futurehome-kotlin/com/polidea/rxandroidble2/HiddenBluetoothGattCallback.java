package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothGatt;

public interface HiddenBluetoothGattCallback {
   void onConnectionUpdated(BluetoothGatt var1, int var2, int var3, int var4, int var5);
}
