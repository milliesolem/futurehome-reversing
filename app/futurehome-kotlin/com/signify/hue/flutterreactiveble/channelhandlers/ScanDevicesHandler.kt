package com.signify.hue.flutterreactiveble.channelhandlers

import android.os.ParcelUuid
import com.signify.hue.flutterreactiveble.ProtobufModel
import com.signify.hue.flutterreactiveble.ProtobufModel.DeviceScanInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.ScanForDevicesRequest
import com.signify.hue.flutterreactiveble.ble.BleClient
import com.signify.hue.flutterreactiveble.ble.ScanInfo
import com.signify.hue.flutterreactiveble.converters.ProtobufMessageConverter
import com.signify.hue.flutterreactiveble.converters.UuidConverter
import com.signify.hue.flutterreactiveble.model.ScanModeKt
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.ArrayList
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics

public class ScanDevicesHandler(bleClient: BleClient) : EventChannel.StreamHandler {
   private final val bleClient: BleClient
   private final var scanDevicesSink: EventSink?
   private final lateinit var scanForDevicesDisposable: Disposable
   private final val converter: ProtobufMessageConverter

   init {
      this.bleClient = var1;
      this.converter = new ProtobufMessageConverter();
   }

   private fun handleDeviceScanResult(discoveryMessage: DeviceScanInfo) {
      if (this.scanDevicesSink != null) {
         this.scanDevicesSink.success(var1.toByteArray());
      }
   }

   private fun startDeviceScan() {
      val var1: ScanParameters = scanParameters;
      if (scanParameters != null) {
         this.scanForDevicesDisposable = this.bleClient
            .scanForDevices(scanParameters.getFilter(), var1.getMode(), var1.getLocationServiceIsMandatory())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new ScanDevicesHandler$$ExternalSyntheticLambda1(new ScanDevicesHandler$$ExternalSyntheticLambda0(this)),
               new ScanDevicesHandler$$ExternalSyntheticLambda3(new ScanDevicesHandler$$ExternalSyntheticLambda2(this))
            );
      } else {
         this.handleDeviceScanResult(this.converter.convertScanErrorInfo("Scanning parameters are not set"));
      }
   }

   @JvmStatic
   fun `startDeviceScan$lambda$5$lambda$1`(var0: ScanDevicesHandler, var1: ScanInfo): Unit {
      val var2: ProtobufMessageConverter = var0.converter;
      var0.handleDeviceScanResult(var2.convertScanInfo(var1));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `startDeviceScan$lambda$5$lambda$2`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `startDeviceScan$lambda$5$lambda$3`(var0: ScanDevicesHandler, var1: java.lang.Throwable): Unit {
      var0.handleDeviceScanResult(var0.converter.convertScanErrorInfo(var1.getMessage()));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `startDeviceScan$lambda$5$lambda$4`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   public override fun onCancel(objectSink: Any?) {
      this.stopDeviceScan();
      this.scanDevicesSink = null;
   }

   public override fun onListen(objectSink: Any?, eventSink: EventSink?) {
      if (var2 != null) {
         this.scanDevicesSink = var2;
         this.startDeviceScan();
      }
   }

   public fun prepareScan(scanMessage: ScanForDevicesRequest) {
      this.stopDeviceScan();
      val var2: java.util.List = var1.getServiceUuidsList();
      val var3: java.lang.Iterable = var2;
      val var6: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

      for (ProtobufModel.Uuid var5 : var3) {
         val var7: UuidConverter = new UuidConverter();
         val var8: ByteArray = var5.getData().toByteArray();
         var6.add(new ParcelUuid(var7.uuidFromByteArray(var8)));
      }

      scanParameters = new ScanParameters(
         var6 as MutableList<ParcelUuid>, ScanModeKt.createScanMode(var1.getScanMode()), var1.getRequireLocationServicesEnabled()
      );
   }

   public fun stopDeviceScan() {
      if (this.scanForDevicesDisposable != null) {
         var var1: Disposable = this.scanForDevicesDisposable;
         if (this.scanForDevicesDisposable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scanForDevicesDisposable");
            var1 = null;
         }

         if (!var1.isDisposed()) {
            var1.dispose();
            scanParameters = null;
         }
      }
   }

   public companion object {
      private final var scanParameters: ScanParameters?
   }
}
