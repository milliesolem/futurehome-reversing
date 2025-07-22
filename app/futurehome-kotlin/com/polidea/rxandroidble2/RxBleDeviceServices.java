package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import com.polidea.rxandroidble2.exceptions.BleCharacteristicNotFoundException;
import com.polidea.rxandroidble2.exceptions.BleDescriptorNotFoundException;
import com.polidea.rxandroidble2.exceptions.BleServiceNotFoundException;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class RxBleDeviceServices {
   final List<BluetoothGattService> bluetoothGattServices;

   public RxBleDeviceServices(List<BluetoothGattService> var1) {
      this.bluetoothGattServices = var1;
   }

   public List<BluetoothGattService> getBluetoothGattServices() {
      return this.bluetoothGattServices;
   }

   public Single<BluetoothGattCharacteristic> getCharacteristic(UUID var1) {
      return Single.fromCallable(new Callable<BluetoothGattCharacteristic>(this, var1) {
         final RxBleDeviceServices this$0;
         final UUID val$characteristicUuid;

         {
            this.this$0 = var1;
            this.val$characteristicUuid = var2;
         }

         public BluetoothGattCharacteristic call() {
            Iterator var1x = this.this$0.bluetoothGattServices.iterator();

            while (var1x.hasNext()) {
               BluetoothGattCharacteristic var2 = ((BluetoothGattService)var1x.next()).getCharacteristic(this.val$characteristicUuid);
               if (var2 != null) {
                  return var2;
               }
            }

            throw new BleCharacteristicNotFoundException(this.val$characteristicUuid);
         }
      });
   }

   public Single<BluetoothGattCharacteristic> getCharacteristic(UUID var1, UUID var2) {
      return this.getService(var1).map(new Function<BluetoothGattService, BluetoothGattCharacteristic>(this, var2) {
         final RxBleDeviceServices this$0;
         final UUID val$characteristicUuid;

         {
            this.this$0 = var1;
            this.val$characteristicUuid = var2x;
         }

         public BluetoothGattCharacteristic apply(BluetoothGattService var1) {
            BluetoothGattCharacteristic var2x = var1.getCharacteristic(this.val$characteristicUuid);
            if (var2x != null) {
               return var2x;
            } else {
               throw new BleCharacteristicNotFoundException(this.val$characteristicUuid);
            }
         }
      });
   }

   public Single<BluetoothGattDescriptor> getDescriptor(UUID var1, UUID var2) {
      return this.getCharacteristic(var1).map(new Function<BluetoothGattCharacteristic, BluetoothGattDescriptor>(this, var2) {
         final RxBleDeviceServices this$0;
         final UUID val$descriptorUuid;

         {
            this.this$0 = var1;
            this.val$descriptorUuid = var2x;
         }

         public BluetoothGattDescriptor apply(BluetoothGattCharacteristic var1) {
            BluetoothGattDescriptor var2x = var1.getDescriptor(this.val$descriptorUuid);
            if (var2x != null) {
               return var2x;
            } else {
               throw new BleDescriptorNotFoundException(this.val$descriptorUuid);
            }
         }
      });
   }

   public Single<BluetoothGattDescriptor> getDescriptor(UUID var1, UUID var2, UUID var3) {
      return this.getService(var1).map(new Function<BluetoothGattService, BluetoothGattCharacteristic>(this, var2) {
         final RxBleDeviceServices this$0;
         final UUID val$characteristicUuid;

         {
            this.this$0 = var1;
            this.val$characteristicUuid = var2;
         }

         public BluetoothGattCharacteristic apply(BluetoothGattService var1) {
            return var1.getCharacteristic(this.val$characteristicUuid);
         }
      }).map(new Function<BluetoothGattCharacteristic, BluetoothGattDescriptor>(this, var3) {
         final RxBleDeviceServices this$0;
         final UUID val$descriptorUuid;

         {
            this.this$0 = var1;
            this.val$descriptorUuid = var2x;
         }

         public BluetoothGattDescriptor apply(BluetoothGattCharacteristic var1) {
            BluetoothGattDescriptor var2x = var1.getDescriptor(this.val$descriptorUuid);
            if (var2x != null) {
               return var2x;
            } else {
               throw new BleDescriptorNotFoundException(this.val$descriptorUuid);
            }
         }
      });
   }

   public Single<BluetoothGattService> getService(UUID var1) {
      return Observable.fromIterable(this.bluetoothGattServices).filter(new Predicate<BluetoothGattService>(this, var1) {
         final RxBleDeviceServices this$0;
         final UUID val$serviceUuid;

         {
            this.this$0 = var1;
            this.val$serviceUuid = var2;
         }

         public boolean test(BluetoothGattService var1) {
            return var1.getUuid().equals(this.val$serviceUuid);
         }
      }).firstElement().switchIfEmpty(Single.error(new BleServiceNotFoundException(var1)));
   }
}
