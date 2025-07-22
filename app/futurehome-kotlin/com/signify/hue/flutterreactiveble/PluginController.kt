package com.signify.hue.flutterreactiveble

import android.content.Context
import com.polidea.rxandroidble2.RxBleDeviceServices
import com.signify.hue.flutterreactiveble.ble.BleClient
import com.signify.hue.flutterreactiveble.ble.CharOperationFailed
import com.signify.hue.flutterreactiveble.ble.CharOperationResult
import com.signify.hue.flutterreactiveble.ble.CharOperationSuccessful
import com.signify.hue.flutterreactiveble.ble.MtuNegotiateFailed
import com.signify.hue.flutterreactiveble.ble.MtuNegotiateResult
import com.signify.hue.flutterreactiveble.ble.ReactiveBleClient
import com.signify.hue.flutterreactiveble.ble.RequestConnectionPriorityFailed
import com.signify.hue.flutterreactiveble.ble.RequestConnectionPriorityResult
import com.signify.hue.flutterreactiveble.channelhandlers.BleStatusHandler
import com.signify.hue.flutterreactiveble.channelhandlers.CharNotificationHandler
import com.signify.hue.flutterreactiveble.channelhandlers.DeviceConnectionHandler
import com.signify.hue.flutterreactiveble.channelhandlers.ScanDevicesHandler
import com.signify.hue.flutterreactiveble.converters.ProtobufMessageConverter
import com.signify.hue.flutterreactiveble.converters.UuidConverter
import com.signify.hue.flutterreactiveble.model.ClearGattCacheErrorType
import com.signify.hue.flutterreactiveble.utils.BleWrapperExtensionsKt
import com.signify.hue.flutterreactiveble.utils.DiscardKt
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics

public class PluginController {
   private final val pluginMethods: Map<String, (MethodCall, Result) -> Unit> =
      MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to(
               "initialize",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "initializeClient",
                        "initializeClient(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$initializeClient(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "deinitialize",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "deinitializeClient",
                        "deinitializeClient(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$deinitializeClient(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "scanForDevices",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "scanForDevices",
                        "scanForDevices(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$scanForDevices(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "connectToDevice",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "connectToDevice",
                        "connectToDevice(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$connectToDevice(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "clearGattCache",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "clearGattCache",
                        "clearGattCache(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$clearGattCache(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "disconnectFromDevice",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "disconnectFromDevice",
                        "disconnectFromDevice(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$disconnectFromDevice(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "readCharacteristic",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "readCharacteristic",
                        "readCharacteristic(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$readCharacteristic(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "writeCharacteristicWithResponse",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "writeCharacteristicWithResponse",
                        "writeCharacteristicWithResponse(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$writeCharacteristicWithResponse(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "writeCharacteristicWithoutResponse",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "writeCharacteristicWithoutResponse",
                        "writeCharacteristicWithoutResponse(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$writeCharacteristicWithoutResponse(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "readNotifications",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "readNotifications",
                        "readNotifications(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$readNotifications(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "stopNotifications",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "stopNotifications",
                        "stopNotifications(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$stopNotifications(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "negotiateMtuSize",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "negotiateMtuSize",
                        "negotiateMtuSize(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$negotiateMtuSize(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "requestConnectionPriority",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "requestConnectionPriority",
                        "requestConnectionPriority(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$requestConnectionPriority(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "discoverServices",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "discoverServices",
                        "discoverServices(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$discoverServices(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "getDiscoveredServices",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "discoverServices",
                        "discoverServices(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$discoverServices(this.receiver as PluginController, var1, var2);
                  }
               }
            ),
            TuplesKt.to(
               "readRssi",
               new Function2<MethodCall, MethodChannel.Result, Unit>(this) {
                  {
                     super(
                        2,
                        var1,
                        PluginController::class.java,
                        "readRssi",
                        "readRssi(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V",
                        0
                     );
                  }

                  public final void invoke(MethodCall var1, MethodChannel.Result var2) {
                     PluginController.access$readRssi(this.receiver as PluginController, var1, var2);
                  }
               }
            )
         }
      )

   private final lateinit var bleClient: BleClient
   private final lateinit var scanchannel: EventChannel
   private final lateinit var deviceConnectionChannel: EventChannel
   private final lateinit var charNotificationChannel: EventChannel
   private final lateinit var scanDevicesHandler: ScanDevicesHandler
   private final lateinit var deviceConnectionHandler: DeviceConnectionHandler
   private final lateinit var charNotificationHandler: CharNotificationHandler
   private final val uuidConverter: UuidConverter = new UuidConverter()
   private final val protoConverter: ProtobufMessageConverter

   private fun clearGattCache(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.ClearGattCacheRequest = ProtobufModel.ClearGattCacheRequest.parseFrom(var5 as ByteArray);
      var var6: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var6 = null;
      }

      val var7: java.lang.String = var4.getDeviceId();
      DiscardKt.discard(
         var6.clearGattCache(var7)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda25(var2),
               new PluginController$$ExternalSyntheticLambda1(new PluginController$$ExternalSyntheticLambda26(this, var2))
            )
      );
   }

   @JvmStatic
   fun `clearGattCache$lambda$0`(var0: MethodChannel.Result) {
      var0.success(ProtobufModel.ClearGattCacheInfo.getDefaultInstance().toByteArray());
   }

   @JvmStatic
   fun `clearGattCache$lambda$1`(var0: PluginController, var1: MethodChannel.Result, var2: java.lang.Throwable): Unit {
      var1.success(var0.protoConverter.convertClearGattCacheError(ClearGattCacheErrorType.UNKNOWN, var2.getMessage()).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `clearGattCache$lambda$2`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun connectToDevice(call: MethodCall, result: Result) {
      var2.success(null);
      val var4: Any = var1.arguments;
      val var6: ProtobufModel.ConnectToDeviceRequest = ProtobufModel.ConnectToDeviceRequest.parseFrom(var4 as ByteArray);
      var var5: DeviceConnectionHandler = this.deviceConnectionHandler;
      if (this.deviceConnectionHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("deviceConnectionHandler");
         var5 = null;
      }

      var5.connectToDevice(var6);
   }

   private fun deinitializeClient(call: MethodCall, result: Result) {
      this.deinitialize$reactive_ble_mobile_release();
      var2.success(null);
   }

   private fun disconnectFromDevice(call: MethodCall, result: Result) {
      var2.success(null);
      val var4: Any = var1.arguments;
      val var6: ProtobufModel.DisconnectFromDeviceRequest = ProtobufModel.DisconnectFromDeviceRequest.parseFrom(var4 as ByteArray);
      var var5: DeviceConnectionHandler = this.deviceConnectionHandler;
      if (this.deviceConnectionHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("deviceConnectionHandler");
         var5 = null;
      }

      val var7: java.lang.String = var6.getDeviceId();
      var5.disconnectDevice(var7);
   }

   private fun discoverServices(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.DiscoverServicesRequest = ProtobufModel.DiscoverServicesRequest.parseFrom(var5 as ByteArray);
      var var6: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var6 = null;
      }

      val var7: java.lang.String = var4.getDeviceId();
      DiscardKt.discard(
         var6.discoverServices(var7)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda12(new PluginController$$ExternalSyntheticLambda10(var2, this, var4)),
               new PluginController$$ExternalSyntheticLambda14(new PluginController$$ExternalSyntheticLambda13(var2))
            )
      );
   }

   @JvmStatic
   fun `discoverServices$lambda$19`(var0: MethodChannel.Result, var1: PluginController, var2: ProtobufModel.DiscoverServicesRequest, var3: RxBleDeviceServices): Unit {
      val var4: ProtobufMessageConverter = var1.protoConverter;
      val var5: java.lang.String = var2.getDeviceId();
      var0.success(var4.convertDiscoverServicesInfo(var5, var3).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `discoverServices$lambda$20`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `discoverServices$lambda$21`(var0: MethodChannel.Result, var1: java.lang.Throwable): Unit {
      val var2: java.lang.String = var1.toString();
      val var3: Array<StackTraceElement> = var1.getStackTrace();
      var0.error("service_discovery_failure", var2, ArraysKt.toList(var3).toString());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `discoverServices$lambda$22`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun executeWriteAndPropagateResultToChannel(
      call: MethodCall,
      result: Result,
      writeOperation: (BleClient, String, UUID, Int, ByteArray) -> Single<CharOperationResult>
   ) {
      val var9: Any = var1.arguments;
      val var6: ProtobufModel.WriteCharacteristicRequest = ProtobufModel.WriteCharacteristicRequest.parseFrom(var9 as ByteArray);
      var var10: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var10 = null;
      }

      val var11: java.lang.String = var6.getCharacteristic().getDeviceId();
      val var8: UuidConverter = this.uuidConverter;
      val var7: ByteArray = var6.getCharacteristic().getCharacteristicUuid().getData().toByteArray();
      val var12: UUID = var8.uuidFromByteArray(var7);
      val var13: java.lang.String = var6.getCharacteristic().getCharacteristicInstanceId();
      val var4: Int = Integer.parseInt(var13);
      val var14: ByteArray = var6.getValue().toByteArray();
      DiscardKt.discard(
         (var3.invoke(var10, var11, var12, var4, var14) as Single)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda11(new PluginController$$ExternalSyntheticLambda0(var2, this, var6)),
               new PluginController$$ExternalSyntheticLambda20(new PluginController$$ExternalSyntheticLambda19(var2, this, var6))
            )
      );
   }

   @JvmStatic
   fun `executeWriteAndPropagateResultToChannel$lambda$10`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `executeWriteAndPropagateResultToChannel$lambda$7`(
      var0: MethodChannel.Result, var1: PluginController, var2: ProtobufModel.WriteCharacteristicRequest, var3: CharOperationResult
   ): Unit {
      if (var3 is CharOperationSuccessful) {
         val var4: ProtobufMessageConverter = var1.protoConverter;
         var0.success(var4.convertWriteCharacteristicInfo(var2, null).toByteArray());
      } else {
         if (var3 !is CharOperationFailed) {
            throw new NoWhenBranchMatchedException();
         }

         val var5: ProtobufMessageConverter = var1.protoConverter;
         var0.success(var5.convertWriteCharacteristicInfo(var2, (var3 as CharOperationFailed).getErrorMessage()).toByteArray());
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `executeWriteAndPropagateResultToChannel$lambda$8`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `executeWriteAndPropagateResultToChannel$lambda$9`(
      var0: MethodChannel.Result, var1: PluginController, var2: ProtobufModel.WriteCharacteristicRequest, var3: java.lang.Throwable
   ): Unit {
      val var4: ProtobufMessageConverter = var1.protoConverter;
      var0.success(var4.convertWriteCharacteristicInfo(var2, var3.getMessage()).toByteArray());
      return Unit.INSTANCE;
   }

   private fun initializeClient(call: MethodCall, result: Result) {
      var var4: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var4 = null;
      }

      var4.initializeClient();
      var2.success(null);
   }

   private fun negotiateMtuSize(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.NegotiateMtuRequest = ProtobufModel.NegotiateMtuRequest.parseFrom(var5 as ByteArray);
      var var6: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var6 = null;
      }

      val var7: java.lang.String = var4.getDeviceId();
      DiscardKt.discard(
         var6.negotiateMtuSize(var7, var4.getMtuSize())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda22(new PluginController$$ExternalSyntheticLambda21(var2, this)),
               new PluginController$$ExternalSyntheticLambda24(new PluginController$$ExternalSyntheticLambda23(var2, this, var4))
            )
      );
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$11`(var0: MethodChannel.Result, var1: PluginController, var2: MtuNegotiateResult): Unit {
      val var3: ProtobufMessageConverter = var1.protoConverter;
      var0.success(var3.convertNegotiateMtuInfo(var2).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$12`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$13`(var0: MethodChannel.Result, var1: PluginController, var2: ProtobufModel.NegotiateMtuRequest, var3: java.lang.Throwable): Unit {
      val var4: ProtobufMessageConverter = var1.protoConverter;
      val var5: java.lang.String = var2.getDeviceId();
      val var7: java.lang.String = var3.getMessage();
      var var6: java.lang.String = var7;
      if (var7 == null) {
         var6 = "";
      }

      var0.success(var4.convertNegotiateMtuInfo(new MtuNegotiateFailed(var5, var6)).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$14`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun readCharacteristic(call: MethodCall, result: Result) {
      var2.success(null);
      val var7: Any = var1.arguments;
      val var5: ProtobufModel.ReadCharacteristicRequest = ProtobufModel.ReadCharacteristicRequest.parseFrom(var7 as ByteArray);
      val var11: java.lang.String = var5.getCharacteristic().getDeviceId();
      val var8: UuidConverter = this.uuidConverter;
      val var6: ByteArray = var5.getCharacteristic().getCharacteristicUuid().getData().toByteArray();
      val var12: UUID = var8.uuidFromByteArray(var6);
      val var9: java.lang.String = var5.getCharacteristic().getCharacteristicInstanceId();
      val var3: Int = Integer.parseInt(var9);
      var var10: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var10 = null;
      }

      DiscardKt.discard(
         var10.readCharacteristic(var11, var12, var3)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda16(new PluginController$$ExternalSyntheticLambda15(this, var5)),
               new PluginController$$ExternalSyntheticLambda18(new PluginController$$ExternalSyntheticLambda17(this, var5))
            )
      );
   }

   @JvmStatic
   fun `readCharacteristic$lambda$3`(var0: PluginController, var1: ProtobufModel.ReadCharacteristicRequest, var2: CharOperationResult): Unit {
      if (var2 is CharOperationSuccessful) {
         val var13: ProtobufMessageConverter = var0.protoConverter;
         val var9: ProtobufModel.CharacteristicAddress = var1.getCharacteristic();
         val var10: ProtobufModel.CharacteristicValueInfo = var13.convertCharacteristicInfo(
            var9, CollectionsKt.toByteArray((var2 as CharOperationSuccessful).getValue())
         );
         var var7: CharNotificationHandler = var0.charNotificationHandler;
         if (var0.charNotificationHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("charNotificationHandler");
            var7 = null;
         }

         var7.addSingleReadToStream(var10);
      } else {
         if (var2 !is CharOperationFailed) {
            throw new NoWhenBranchMatchedException();
         }

         val var6: ProtobufMessageConverter = var0.protoConverter;
         val var12: ProtobufModel.CharacteristicAddress = var1.getCharacteristic();
         var6.convertCharacteristicError(var12, "Failed to connect");
         var var8: CharNotificationHandler = var0.charNotificationHandler;
         if (var0.charNotificationHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("charNotificationHandler");
            var8 = null;
         }

         val var11: ProtobufModel.CharacteristicAddress = var1.getCharacteristic();
         var8.addSingleErrorToStream(var11, (var2 as CharOperationFailed).getErrorMessage());
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$4`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `readCharacteristic$lambda$5`(var0: PluginController, var1: ProtobufModel.ReadCharacteristicRequest, var2: java.lang.Throwable): Unit {
      val var3: ProtobufMessageConverter = var0.protoConverter;
      val var4: ProtobufModel.CharacteristicAddress = var1.getCharacteristic();
      var3.convertCharacteristicError(var4, var2.getMessage());
      var var5: CharNotificationHandler = var0.charNotificationHandler;
      if (var0.charNotificationHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("charNotificationHandler");
         var5 = null;
      }

      label14: {
         var9 = var1.getCharacteristic();
         if (var2 != null) {
            val var7: java.lang.String = var2.getMessage();
            var6 = var7;
            if (var7 != null) {
               break label14;
            }
         }

         var6 = "Failure";
      }

      var5.addSingleErrorToStream(var9, var6);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$6`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun readNotifications(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.NotifyCharacteristicRequest = ProtobufModel.NotifyCharacteristicRequest.parseFrom(var5 as ByteArray);
      var var6: CharNotificationHandler = this.charNotificationHandler;
      if (this.charNotificationHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("charNotificationHandler");
         var6 = null;
      }

      var6.subscribeToNotifications(var4);
      var2.success(null);
   }

   private fun readRssi(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.ReadRssiRequest = ProtobufModel.ReadRssiRequest.parseFrom(var5 as ByteArray);
      var var6: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var6 = null;
      }

      val var7: java.lang.String = var4.getDeviceId();
      DiscardKt.discard(
         var6.readRssi(var7)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda3(new PluginController$$ExternalSyntheticLambda2(this, var2)),
               new PluginController$$ExternalSyntheticLambda5(new PluginController$$ExternalSyntheticLambda4(var2))
            )
      );
   }

   @JvmStatic
   fun `readRssi$lambda$23`(var0: PluginController, var1: MethodChannel.Result, var2: Int): Unit {
      val var3: ProtobufMessageConverter = var0.protoConverter;
      var1.success(var3.convertReadRssiResult(var2).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `readRssi$lambda$24`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `readRssi$lambda$25`(var0: MethodChannel.Result, var1: java.lang.Throwable): Unit {
      var0.error("read_rssi_error", var1.getMessage(), null);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `readRssi$lambda$26`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun requestConnectionPriority(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.ChangeConnectionPriorityRequest = ProtobufModel.ChangeConnectionPriorityRequest.parseFrom(var5 as ByteArray);
      var var6: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var6 = null;
      }

      val var7: java.lang.String = var4.getDeviceId();
      DiscardKt.discard(
         var6.requestConnectionPriority(var7, BleWrapperExtensionsKt.toConnectionPriority(var4.getPriority()))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new PluginController$$ExternalSyntheticLambda7(new PluginController$$ExternalSyntheticLambda6(var2, this)),
               new PluginController$$ExternalSyntheticLambda9(new PluginController$$ExternalSyntheticLambda8(var2, this, var4))
            )
      );
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$15`(var0: MethodChannel.Result, var1: PluginController, var2: RequestConnectionPriorityResult): Unit {
      val var3: ProtobufMessageConverter = var1.protoConverter;
      var0.success(var3.convertRequestConnectionPriorityInfo(var2).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$16`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$17`(
      var0: MethodChannel.Result, var1: PluginController, var2: ProtobufModel.ChangeConnectionPriorityRequest, var3: java.lang.Throwable
   ): Unit {
      var var4: ProtobufMessageConverter;
      var var5: java.lang.String;
      label11: {
         var4 = var1.protoConverter;
         var5 = var2.getDeviceId();
         if (var3 != null) {
            val var7: java.lang.String = var3.getMessage();
            var6 = var7;
            if (var7 != null) {
               break label11;
            }
         }

         var6 = "Unknown error";
      }

      var0.success(var4.convertRequestConnectionPriorityInfo(new RequestConnectionPriorityFailed(var5, var6)).toByteArray());
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$18`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun scanForDevices(call: MethodCall, result: Result) {
      var var3: ScanDevicesHandler = this.scanDevicesHandler;
      if (this.scanDevicesHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scanDevicesHandler");
         var3 = null;
      }

      val var5: Any = var1.arguments;
      val var6: ProtobufModel.ScanForDevicesRequest = ProtobufModel.ScanForDevicesRequest.parseFrom(var5 as ByteArray);
      var3.prepareScan(var6);
      var2.success(null);
   }

   private fun stopNotifications(call: MethodCall, result: Result) {
      val var5: Any = var1.arguments;
      val var4: ProtobufModel.NotifyNoMoreCharacteristicRequest = ProtobufModel.NotifyNoMoreCharacteristicRequest.parseFrom(var5 as ByteArray);
      var var6: CharNotificationHandler = this.charNotificationHandler;
      if (this.charNotificationHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("charNotificationHandler");
         var6 = null;
      }

      var6.unsubscribeFromNotifications(var4);
      var2.success(null);
   }

   private fun writeCharacteristicWithResponse(call: MethodCall, result: Result) {
      this.executeWriteAndPropagateResultToChannel(var1, var2, <unrepresentable>.INSTANCE);
   }

   private fun writeCharacteristicWithoutResponse(call: MethodCall, result: Result) {
      this.executeWriteAndPropagateResultToChannel(var1, var2, <unrepresentable>.INSTANCE);
   }

   internal fun deinitialize() {
      var var1: ScanDevicesHandler = this.scanDevicesHandler;
      if (this.scanDevicesHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scanDevicesHandler");
         var1 = null;
      }

      var1.stopDeviceScan();
      var var4: DeviceConnectionHandler = this.deviceConnectionHandler;
      if (this.deviceConnectionHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("deviceConnectionHandler");
         var4 = null;
      }

      var4.disconnectAll();
   }

   internal fun execute(call: MethodCall, result: Result) {
      val var3: Function2 = this.pluginMethods.get(var1.method);
      if (var3 != null) {
         var3.invoke(var1, var2);
      } else {
         var2.notImplemented();
      }
   }

   internal fun initialize(messenger: BinaryMessenger, context: Context) {
      this.bleClient = new ReactiveBleClient(var2);
      this.scanchannel = new EventChannel(var1, "flutter_reactive_ble_scan");
      this.deviceConnectionChannel = new EventChannel(var1, "flutter_reactive_ble_connected_device");
      this.charNotificationChannel = new EventChannel(var1, "flutter_reactive_ble_char_update");
      val var5: EventChannel = new EventChannel(var1, "flutter_reactive_ble_status");
      var var7: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var7 = null;
      }

      this.scanDevicesHandler = new ScanDevicesHandler(var7);
      var var8: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var8 = null;
      }

      this.deviceConnectionHandler = new DeviceConnectionHandler(var8);
      var var9: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var9 = null;
      }

      this.charNotificationHandler = new CharNotificationHandler(var9);
      var var10: BleClient = this.bleClient;
      if (this.bleClient == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bleClient");
         var10 = null;
      }

      val var6: BleStatusHandler = new BleStatusHandler(var10);
      var var11: EventChannel = this.scanchannel;
      if (this.scanchannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scanchannel");
         var11 = null;
      }

      var var19: ScanDevicesHandler = this.scanDevicesHandler;
      if (this.scanDevicesHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scanDevicesHandler");
         var19 = null;
      }

      var11.setStreamHandler(var19);
      var var12: EventChannel = this.deviceConnectionChannel;
      if (this.deviceConnectionChannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("deviceConnectionChannel");
         var12 = null;
      }

      var var21: DeviceConnectionHandler = this.deviceConnectionHandler;
      if (this.deviceConnectionHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("deviceConnectionHandler");
         var21 = null;
      }

      var12.setStreamHandler(var21);
      var var13: EventChannel = this.charNotificationChannel;
      if (this.charNotificationChannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("charNotificationChannel");
         var13 = null;
      }

      var var23: CharNotificationHandler = this.charNotificationHandler;
      if (this.charNotificationHandler == null) {
         Intrinsics.throwUninitializedPropertyAccessException("charNotificationHandler");
         var23 = null;
      }

      var13.setStreamHandler(var23);
      var5.setStreamHandler(var6);
   }
}
