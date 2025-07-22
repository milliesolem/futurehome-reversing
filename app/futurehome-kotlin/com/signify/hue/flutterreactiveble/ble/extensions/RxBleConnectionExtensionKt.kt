package com.signify.hue.flutterreactiveble.ble.extensions

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDeviceServices
import io.reactivex.Single
import io.reactivex.SingleSource
import java.util.ArrayList
import java.util.UUID
import kotlin.jvm.functions.Function1

@JvmSynthetic
fun `$r8$lambda$DJMNiRUlDh0KKeNVbTLqtniN13A`(var0: UUID, var1: Int, var2: RxBleDeviceServices): SingleSource {
   return resolveCharacteristic$lambda$2(var0, var1, var2);
}

@JvmSynthetic
fun `$r8$lambda$Nr6QzhZ_lUbjI0rLymxaxJGHqsE`(var0: Function1, var1: Any): SingleSource {
   return resolveCharacteristic$lambda$3(var0, var1);
}

public fun RxBleConnection.resolveCharacteristic(uuid: UUID, instanceId: Int): Single<BluetoothGattCharacteristic> {
   val var3: Single = var0.discoverServices()
      .flatMap(new RxBleConnectionExtensionKt$$ExternalSyntheticLambda1(new RxBleConnectionExtensionKt$$ExternalSyntheticLambda0(var1, var2)));
   return var3;
}

fun `resolveCharacteristic$lambda$2`(var0: UUID, var1: Int, var2: RxBleDeviceServices): SingleSource {
   val var8: java.util.List = var2.getBluetoothGattServices();
   val var3: java.lang.Iterable = var8;
   val var9: java.util.Collection = new ArrayList();
   val var10: java.util.Iterator = var3.iterator();

   while (var10.hasNext()) {
      val var4: java.util.List = (var10.next() as BluetoothGattService).getCharacteristics();
      val var5: java.lang.Iterable = var4;
      val var11: java.util.Collection = new ArrayList();

      for (Object var6 : var5) {
         if ((var6 as BluetoothGattCharacteristic).getUuid() == var0 && (var6 as BluetoothGattCharacteristic).getInstanceId() == var1) {
            var11.add(var6);
         }
      }

      CollectionsKt.addAll(var9, var11 as java.util.List);
   }

   return Single.just(CollectionsKt.single(var9 as java.util.List));
}

fun `resolveCharacteristic$lambda$3`(var0: Function1, var1: Any): SingleSource {
   return var0.invoke(var1) as SingleSource;
}

public fun RxBleConnection.writeCharWithResponse(characteristic: BluetoothGattCharacteristic, value: ByteArray): Single<ByteArray> {
   var1.setWriteType(2);
   val var3: Single = var0.writeCharacteristic(var1, var2);
   return var3;
}

public fun RxBleConnection.writeCharWithoutResponse(characteristic: BluetoothGattCharacteristic, value: ByteArray): Single<ByteArray> {
   var1.setWriteType(1);
   val var3: Single = var0.writeCharacteristic(var1, var2);
   return var3;
}
