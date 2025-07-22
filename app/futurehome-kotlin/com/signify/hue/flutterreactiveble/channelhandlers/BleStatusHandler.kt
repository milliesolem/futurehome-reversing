package com.signify.hue.flutterreactiveble.channelhandlers

import com.signify.hue.flutterreactiveble.ProtobufModel
import com.signify.hue.flutterreactiveble.ble.BleClient
import com.signify.hue.flutterreactiveble.ble.BleStatus
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.SerialDisposable
import java.util.concurrent.TimeUnit
import kotlin.jvm.functions.Function1

public class BleStatusHandler(bleClient: BleClient) : EventChannel.StreamHandler {
   private final val bleClient: BleClient
   private final val subscriptionDisposable: SerialDisposable

   init {
      this.bleClient = var1;
      this.subscriptionDisposable = new SerialDisposable();
   }

   private fun listenToBleStatus(eventSink: EventSink): Disposable {
      val var2: Disposable = Observable.timer(500L, TimeUnit.MILLISECONDS)
         .switchMap(new BleStatusHandler$$ExternalSyntheticLambda1(new BleStatusHandler$$ExternalSyntheticLambda0(this)))
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(
            new BleStatusHandler$$ExternalSyntheticLambda3(new BleStatusHandler$$ExternalSyntheticLambda2(var1)),
            new BleStatusHandler$$ExternalSyntheticLambda5(new BleStatusHandler$$ExternalSyntheticLambda4(var1))
         );
      return var2;
   }

   @JvmStatic
   fun `listenToBleStatus$lambda$0`(var0: BleStatusHandler, var1: java.lang.Long): ObservableSource {
      return var0.bleClient.observeBleStatus();
   }

   @JvmStatic
   fun `listenToBleStatus$lambda$1`(var0: Function1, var1: Any): ObservableSource {
      return var0.invoke(var1) as ObservableSource;
   }

   @JvmStatic
   fun `listenToBleStatus$lambda$2`(var0: EventChannel.EventSink, var1: BleStatus): Unit {
      var0.success(ProtobufModel.BleStatusInfo.newBuilder().setStatus(var1.getCode()).build().toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `listenToBleStatus$lambda$3`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `listenToBleStatus$lambda$4`(var0: EventChannel.EventSink, var1: java.lang.Throwable): Unit {
      var0.error("ObserveBleStatusFailure", var1.getMessage(), null);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `listenToBleStatus$lambda$5`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   public override fun onCancel(arg: Any?) {
      this.subscriptionDisposable.set(null);
   }

   public override fun onListen(arg: Any?, eventSink: EventSink?) {
      if (var2 != null) {
         var1 = this.listenToBleStatus(var2);
      } else {
         var1 = null;
      }

      this.subscriptionDisposable.set(var1);
   }

   public companion object {
      private const val delayListenBleStatus: Long
   }
}
