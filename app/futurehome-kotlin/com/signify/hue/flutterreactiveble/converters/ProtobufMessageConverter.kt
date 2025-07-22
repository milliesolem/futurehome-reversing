package com.signify.hue.flutterreactiveble.converters

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.google.protobuf.ByteString
import com.google.protobuf.GeneratedMessageLite
import com.polidea.rxandroidble2.RxBleDeviceServices
import com.signify.hue.flutterreactiveble.ProtobufModel
import com.signify.hue.flutterreactiveble.ProtobufModel.ChangeConnectionPriorityInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.CharacteristicAddress
import com.signify.hue.flutterreactiveble.ProtobufModel.CharacteristicValueInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.ClearGattCacheInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.DeviceInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.DeviceScanInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.DiscoverServicesInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.DiscoveredService
import com.signify.hue.flutterreactiveble.ProtobufModel.NegotiateMtuInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.ReadRssiResult
import com.signify.hue.flutterreactiveble.ProtobufModel.ServiceDataEntry
import com.signify.hue.flutterreactiveble.ProtobufModel.Uuid
import com.signify.hue.flutterreactiveble.ProtobufModel.WriteCharacteristicInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.WriteCharacteristicRequest
import com.signify.hue.flutterreactiveble.ProtobufModel.CharacteristicAddress.Builder
import com.signify.hue.flutterreactiveble.ble.ConnectionUpdateSuccess
import com.signify.hue.flutterreactiveble.ble.MtuNegotiateFailed
import com.signify.hue.flutterreactiveble.ble.MtuNegotiateResult
import com.signify.hue.flutterreactiveble.ble.MtuNegotiateSuccessful
import com.signify.hue.flutterreactiveble.ble.RequestConnectionPriorityFailed
import com.signify.hue.flutterreactiveble.ble.RequestConnectionPriorityResult
import com.signify.hue.flutterreactiveble.ble.RequestConnectionPrioritySuccess
import com.signify.hue.flutterreactiveble.ble.ScanInfo
import com.signify.hue.flutterreactiveble.model.CharacteristicErrorType
import com.signify.hue.flutterreactiveble.model.ClearGattCacheErrorType
import com.signify.hue.flutterreactiveble.model.ConnectionErrorType
import com.signify.hue.flutterreactiveble.model.ConnectionState
import com.signify.hue.flutterreactiveble.model.NegotiateMtuErrorType
import com.signify.hue.flutterreactiveble.model.ScanErrorType
import java.util.ArrayList
import java.util.UUID
import java.util.Map.Entry

public class ProtobufMessageConverter {
   private final val uuidConverter: UuidConverter = new UuidConverter()

   private fun convertInternalService(gattService: BluetoothGattService): DiscoveredService {
      val var3: ProtobufModel.DiscoveredService.Builder = ProtobufModel.DiscoveredService.newBuilder();
      val var2: UUID = var1.getUuid();
      val var9: ProtobufModel.DiscoveredService.Builder = var3.setServiceUuid(this.createUuidFromParcelUuid(var2));
      val var11: java.util.List = var1.getCharacteristics();
      val var12: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));
      val var5: java.util.Iterator = var11.iterator();

      while (var5.hasNext()) {
         val var15: UUID = (var5.next() as BluetoothGattCharacteristic).getUuid();
         var12.add(this.createUuidFromParcelUuid(var15));
      }

      val var10: ProtobufModel.DiscoveredService.Builder = var9.addAllCharacteristicUuids(var12);
      val var6: java.util.List = var1.getIncludedServices();
      val var13: java.lang.Iterable = var6;
      val var7: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));

      for (BluetoothGattService var14 : var13) {
         var7.add(this.convertInternalService(var14));
      }

      val var8: GeneratedMessageLite = var10.addAllIncludedServices(var7).build();
      return var8 as ProtobufModel.DiscoveredService;
   }

   private fun createCharacteristicAddress(request: CharacteristicAddress): Builder? {
      return ProtobufModel.CharacteristicAddress.newBuilder()
         .setDeviceId(var1.getDeviceId())
         .setServiceUuid(var1.getServiceUuid())
         .setServiceInstanceId(var1.getServiceInstanceId())
         .setCharacteristicInstanceId(var1.getCharacteristicInstanceId())
         .setCharacteristicUuid(var1.getCharacteristicUuid());
   }

   private fun createServiceDataEntry(serviceData: Map<UUID, ByteArray>): List<ServiceDataEntry> {
      val var2: java.util.List = new ArrayList();

      for (Entry var3 : var1.entrySet()) {
         val var5: GeneratedMessageLite = ProtobufModel.ServiceDataEntry.newBuilder()
            .setServiceUuid(this.createUuidFromParcelUuid(var3.getKey() as UUID))
            .setData(ByteString.copyFrom(var3.getValue() as ByteArray))
            .build();
         var2.add(var5);
      }

      return var2;
   }

   private fun createServiceUuids(serviceUuids: List<UUID>): List<Uuid> {
      val var3: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));
      val var4: java.util.Iterator = var1.iterator();

      while (var4.hasNext()) {
         var3.add(this.createUuidFromParcelUuid(var4.next() as UUID));
      }

      return var3 as MutableList<ProtobufModel.Uuid>;
   }

   private fun createUuidFromParcelUuid(uuid: UUID): Uuid {
      val var3: GeneratedMessageLite = ProtobufModel.Uuid.newBuilder().setData(ByteString.copyFrom(this.uuidConverter.byteArrayFromUuid(var1))).build();
      return var3 as ProtobufModel.Uuid;
   }

   private fun fromBluetoothGattService(gattService: BluetoothGattService): DiscoveredService {
      val var9: ProtobufModel.DiscoveredService.Builder = ProtobufModel.DiscoveredService.newBuilder();
      val var8: UUID = var1.getUuid();
      val var17: ProtobufModel.DiscoveredService.Builder = var9.setServiceUuid(this.createUuidFromParcelUuid(var8))
         .setServiceInstanceId(java.lang.String.valueOf(var1.getInstanceId()));
      val var20: java.util.List = var1.getCharacteristics();
      val var21: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));
      val var11: java.util.Iterator = var20.iterator();

      while (var11.hasNext()) {
         val var26: UUID = (var11.next() as BluetoothGattCharacteristic).getUuid();
         var21.add(this.createUuidFromParcelUuid(var26));
      }

      val var18: ProtobufModel.DiscoveredService.Builder = var17.addAllCharacteristicUuids(var21);
      val var22: java.util.List = var1.getCharacteristics();
      val var27: java.lang.Iterable = var22;
      val var23: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var22, 10));

      for (BluetoothGattCharacteristic var30 : var27) {
         val var2: Int = var30.getProperties();
         var var7: Boolean = true;
         val var3: Boolean;
         if ((var2 and 2) > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         val var4: Boolean;
         if ((var2 and 8) > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         val var5: Boolean;
         if ((var2 and 4) > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         val var6: Boolean;
         if ((var2 and 16) > 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if ((var2 and 32) <= 0) {
            var7 = false;
         }

         var var12: ProtobufModel.DiscoveredCharacteristic.Builder = ProtobufModel.DiscoveredCharacteristic.newBuilder();
         val var13: UUID = var30.getUuid();
         var12 = var12.setCharacteristicId(this.createUuidFromParcelUuid(var13)).setCharacteristicInstanceId(java.lang.String.valueOf(var30.getInstanceId()));
         val var31: UUID = var30.getService().getUuid();
         var23.add(
            var12.setServiceId(this.createUuidFromParcelUuid(var31))
               .setIsReadable(var3)
               .setIsWritableWithResponse(var4)
               .setIsWritableWithoutResponse(var5)
               .setIsNotifiable(var6)
               .setIsIndicatable(var7)
               .build()
         );
      }

      val var19: ProtobufModel.DiscoveredService.Builder = var18.addAllCharacteristics(var23);
      val var14: java.util.List = var1.getIncludedServices();
      val var24: java.lang.Iterable = var14;
      val var15: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));

      for (BluetoothGattService var29 : var24) {
         var15.add(this.convertInternalService(var29));
      }

      val var16: GeneratedMessageLite = var19.addAllIncludedServices(var15).build();
      return var16 as ProtobufModel.DiscoveredService;
   }

   public fun convertCharacteristicError(request: CharacteristicAddress, error: String?): CharacteristicValueInfo {
      val var3: ProtobufModel.CharacteristicAddress.Builder = this.createCharacteristicAddress(var1);
      val var4: ProtobufModel.GenericFailure.Builder = ProtobufModel.GenericFailure.newBuilder().setCode(CharacteristicErrorType.UNKNOWN.getCode());
      var var5: java.lang.String = var2;
      if (var2 == null) {
         var5 = "Unknown error";
      }

      val var7: GeneratedMessageLite = ProtobufModel.CharacteristicValueInfo.newBuilder().setCharacteristic(var3).setFailure(var4.setMessage(var5)).build();
      return var7 as ProtobufModel.CharacteristicValueInfo;
   }

   public fun convertCharacteristicInfo(request: CharacteristicAddress, value: ByteArray): CharacteristicValueInfo {
      val var4: GeneratedMessageLite = ProtobufModel.CharacteristicValueInfo.newBuilder()
         .setCharacteristic(this.createCharacteristicAddress(var1))
         .setValue(ByteString.copyFrom(var2))
         .build();
      return var4 as ProtobufModel.CharacteristicValueInfo;
   }

   public fun convertClearGattCacheError(code: ClearGattCacheErrorType, message: String?): ClearGattCacheInfo {
      val var3: ProtobufModel.GenericFailure.Builder = ProtobufModel.GenericFailure.newBuilder().setCode(var1.getCode());
      if (var2 != null) {
         var3.setMessage(var2);
      }

      val var4: GeneratedMessageLite = ProtobufModel.ClearGattCacheInfo.newBuilder().setFailure(var3).build();
      return var4 as ProtobufModel.ClearGattCacheInfo;
   }

   public fun convertConnectionErrorToDeviceInfo(deviceId: String, errorMessage: String?): DeviceInfo {
      val var4: ProtobufModel.DeviceInfo.Builder = ProtobufModel.DeviceInfo.newBuilder().setId(var1).setConnectionState(ConnectionState.DISCONNECTED.getCode());
      val var3: ProtobufModel.GenericFailure.Builder = ProtobufModel.GenericFailure.newBuilder().setCode(ConnectionErrorType.FAILEDTOCONNECT.getCode());
      var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      val var6: GeneratedMessageLite = var4.setFailure(var3.setMessage(var1).build()).build();
      return var6 as ProtobufModel.DeviceInfo;
   }

   public fun convertDiscoverServicesInfo(deviceId: String, services: RxBleDeviceServices): DiscoverServicesInfo {
      val var5: ProtobufModel.DiscoverServicesInfo.Builder = ProtobufModel.DiscoverServicesInfo.newBuilder().setDeviceId(var1);
      val var7: java.util.List = var2.getBluetoothGattServices();
      val var3: java.lang.Iterable = var7;
      val var8: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (BluetoothGattService var4 : var3) {
         var8.add(this.fromBluetoothGattService(var4));
      }

      val var6: GeneratedMessageLite = var5.addAllServices(var8).build();
      return var6 as ProtobufModel.DiscoverServicesInfo;
   }

   public fun convertNegotiateMtuInfo(result: MtuNegotiateResult): NegotiateMtuInfo {
      val var5: ProtobufModel.NegotiateMtuInfo;
      if (var1 is MtuNegotiateSuccessful) {
         val var4: GeneratedMessageLite = ProtobufModel.NegotiateMtuInfo.newBuilder()
            .setDeviceId((var1 as MtuNegotiateSuccessful).getDeviceId())
            .setMtuSize((var1 as MtuNegotiateSuccessful).getSize())
            .build();
         var5 = var4 as ProtobufModel.NegotiateMtuInfo;
      } else {
         if (var1 !is MtuNegotiateFailed) {
            throw new NoWhenBranchMatchedException();
         }

         val var7: GeneratedMessageLite = ProtobufModel.NegotiateMtuInfo.newBuilder()
            .setDeviceId((var1 as MtuNegotiateFailed).getDeviceId())
            .setFailure(
               ProtobufModel.GenericFailure.newBuilder()
                  .setCode(NegotiateMtuErrorType.UNKNOWN.getCode())
                  .setMessage((var1 as MtuNegotiateFailed).getErrorMessage())
                  .build()
            )
            .build();
         var5 = var7 as ProtobufModel.NegotiateMtuInfo;
      }

      return var5;
   }

   public fun convertReadRssiResult(rssi: Int): ReadRssiResult {
      val var2: GeneratedMessageLite = ProtobufModel.ReadRssiResult.newBuilder().setRssi(var1).build();
      return var2 as ProtobufModel.ReadRssiResult;
   }

   public fun convertRequestConnectionPriorityInfo(result: RequestConnectionPriorityResult): ChangeConnectionPriorityInfo {
      val var4: ProtobufModel.ChangeConnectionPriorityInfo;
      if (var1 is RequestConnectionPrioritySuccess) {
         val var3: GeneratedMessageLite = ProtobufModel.ChangeConnectionPriorityInfo.newBuilder()
            .setDeviceId((var1 as RequestConnectionPrioritySuccess).getDeviceId())
            .build();
         var4 = var3 as ProtobufModel.ChangeConnectionPriorityInfo;
      } else {
         if (var1 !is RequestConnectionPriorityFailed) {
            throw new NoWhenBranchMatchedException();
         }

         val var6: GeneratedMessageLite = ProtobufModel.ChangeConnectionPriorityInfo.newBuilder()
            .setDeviceId((var1 as RequestConnectionPriorityFailed).getDeviceId())
            .setFailure(ProtobufModel.GenericFailure.newBuilder().setCode(0).setMessage((var1 as RequestConnectionPriorityFailed).getErrorMessage()).build())
            .build();
         var4 = var6 as ProtobufModel.ChangeConnectionPriorityInfo;
      }

      return var4;
   }

   public fun convertScanErrorInfo(errorMessage: String?): DeviceScanInfo {
      val var3: ProtobufModel.DeviceScanInfo.Builder = ProtobufModel.DeviceScanInfo.newBuilder();
      val var4: ProtobufModel.GenericFailure.Builder = ProtobufModel.GenericFailure.newBuilder().setCode(ScanErrorType.UNKNOWN.getCode());
      var var2: java.lang.String = var1;
      if (var1 == null) {
         var2 = "";
      }

      val var5: GeneratedMessageLite = var3.setFailure(var4.setMessage(var2).build()).build();
      return var5 as ProtobufModel.DeviceScanInfo;
   }

   public fun convertScanInfo(scanInfo: ScanInfo): DeviceScanInfo {
      val var2: GeneratedMessageLite = ProtobufModel.DeviceScanInfo.newBuilder()
         .setId(var1.getDeviceId())
         .setName(var1.getName())
         .setRssi(var1.getRssi())
         .setIsConnectable(ProtobufModel.IsConnectable.newBuilder().setCode(var1.getConnectable().getCode()).build())
         .addAllServiceData(this.createServiceDataEntry(var1.getServiceData()))
         .addAllServiceUuids(this.createServiceUuids(var1.getServiceUuids()))
         .setManufacturerData(ByteString.copyFrom(var1.getManufacturerData()))
         .build();
      return var2 as ProtobufModel.DeviceScanInfo;
   }

   public fun convertToDeviceInfo(connection: ConnectionUpdateSuccess): DeviceInfo {
      val var2: GeneratedMessageLite = ProtobufModel.DeviceInfo.newBuilder().setId(var1.getDeviceId()).setConnectionState(var1.getConnectionState()).build();
      return var2 as ProtobufModel.DeviceInfo;
   }

   public fun convertWriteCharacteristicInfo(request: WriteCharacteristicRequest, error: String?): WriteCharacteristicInfo {
      val var3: ProtobufModel.WriteCharacteristicInfo.Builder = ProtobufModel.WriteCharacteristicInfo.newBuilder().setCharacteristic(var1.getCharacteristic());
      if (var2 != null) {
         var3.setFailure(ProtobufModel.GenericFailure.newBuilder().setCode(CharacteristicErrorType.UNKNOWN.getCode()).setMessage(var2));
      }

      val var4: GeneratedMessageLite = var3.build();
      return var4 as ProtobufModel.WriteCharacteristicInfo;
   }

   public companion object {
      private const val positionMostSignificantBit: Int
      private const val positionLeastSignificantBit: Int
   }
}
