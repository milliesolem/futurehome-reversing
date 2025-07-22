package com.signify.hue.flutterreactiveble.channelhandlers

import com.signify.hue.flutterreactiveble.ProtobufModel.ConnectToDeviceRequest
import com.signify.hue.flutterreactiveble.ProtobufModel.DeviceInfo
import com.signify.hue.flutterreactiveble.ble.BleClient
import com.signify.hue.flutterreactiveble.ble.ConnectionUpdate
import com.signify.hue.flutterreactiveble.ble.ConnectionUpdateError
import com.signify.hue.flutterreactiveble.ble.ConnectionUpdateSuccess
import com.signify.hue.flutterreactiveble.converters.ProtobufMessageConverter
import com.signify.hue.flutterreactiveble.utils.Duration
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics

public class DeviceConnectionHandler(bleClient: BleClient) : EventChannel.StreamHandler {
   private final val bleClient: BleClient
   private final var connectDeviceSink: EventSink?
   private final val converter: ProtobufMessageConverter
   private final lateinit var connectionUpdatesDisposable: Disposable

   init {
      this.bleClient = var1;
      this.converter = new ProtobufMessageConverter();
   }

   private fun handleDeviceConnectionUpdateResult(connectionUpdateMessage: DeviceInfo) {
      if (this.connectDeviceSink != null) {
         this.connectDeviceSink.success(var1.toByteArray());
      }
   }

   private fun listenToConnectionChanges(): Disposable {
      return this.bleClient
         .getConnectionUpdateSubject()
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new DeviceConnectionHandler$$ExternalSyntheticLambda1(new DeviceConnectionHandler$$ExternalSyntheticLambda0(this)));
   }

   @JvmStatic
   fun `listenToConnectionChanges$lambda$1`(var0: DeviceConnectionHandler, var1: ConnectionUpdate): Unit {
      if (var1 is ConnectionUpdateSuccess) {
         var0.handleDeviceConnectionUpdateResult(var0.converter.convertToDeviceInfo(var1 as ConnectionUpdateSuccess));
      } else {
         if (var1 !is ConnectionUpdateError) {
            throw new NoWhenBranchMatchedException();
         }

         var0.handleDeviceConnectionUpdateResult(
            var0.converter.convertConnectionErrorToDeviceInfo((var1 as ConnectionUpdateError).getDeviceId(), (var1 as ConnectionUpdateError).getErrorMessage())
         );
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `listenToConnectionChanges$lambda$2`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   public fun connectToDevice(connectToDeviceMessage: ConnectToDeviceRequest) {
      val var2: BleClient = this.bleClient;
      val var3: java.lang.String = var1.getDeviceId();
      var2.connectToDevice(var3, new Duration((long)var1.getTimeoutInMs(), TimeUnit.MILLISECONDS));
   }

   public fun disconnectAll() {
      this.connectDeviceSink = null;
      this.bleClient.disconnectAllDevices();
   }

   public fun disconnectDevice(deviceId: String) {
      this.bleClient.disconnectDevice(var1);
   }

   public override fun onCancel(objectSink: Any?) {
      this.disconnectAll();
      var1 = this.connectionUpdatesDisposable;
      if (this.connectionUpdatesDisposable == null) {
         Intrinsics.throwUninitializedPropertyAccessException("connectionUpdatesDisposable");
         var1 = null;
      }

      var1.dispose();
   }

   public override fun onListen(objectSink: Any?, eventSink: EventSink?) {
      if (var2 != null) {
         this.connectDeviceSink = var2;
         this.connectionUpdatesDisposable = this.listenToConnectionChanges();
      }
   }
}
