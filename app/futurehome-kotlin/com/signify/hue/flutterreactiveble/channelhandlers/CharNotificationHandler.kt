package com.signify.hue.flutterreactiveble.channelhandlers

import com.polidea.rxandroidble2.exceptions.BleDisconnectedException
import com.signify.hue.flutterreactiveble.ProtobufModel
import com.signify.hue.flutterreactiveble.ProtobufModel.CharacteristicAddress
import com.signify.hue.flutterreactiveble.ProtobufModel.CharacteristicValueInfo
import com.signify.hue.flutterreactiveble.ProtobufModel.NotifyCharacteristicRequest
import com.signify.hue.flutterreactiveble.ProtobufModel.NotifyNoMoreCharacteristicRequest
import com.signify.hue.flutterreactiveble.ble.BleClient
import com.signify.hue.flutterreactiveble.converters.ProtobufMessageConverter
import com.signify.hue.flutterreactiveble.converters.UuidConverter
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function1

public class CharNotificationHandler(bleClient: BleClient) : EventChannel.StreamHandler {
   private final val bleClient: BleClient
   private final val uuidConverter: UuidConverter
   private final val protobufConverter: ProtobufMessageConverter

   init {
      this.bleClient = var1;
      this.uuidConverter = new UuidConverter();
      this.protobufConverter = new ProtobufMessageConverter();
   }

   private fun handleNotificationError(subscriptionRequest: CharacteristicAddress, error: Throwable) {
      val var4: ProtobufMessageConverter = this.protobufConverter;
      val var3: java.lang.String = var2.getMessage();
      var var6: java.lang.String = var3;
      if (var3 == null) {
         var6 = "";
      }

      val var7: ProtobufModel.CharacteristicValueInfo = var4.convertCharacteristicError(var1, var6);
      if (charNotificationSink != null) {
         charNotificationSink.success(var7.toByteArray());
      }
   }

   private fun handleNotificationValue(subscriptionRequest: CharacteristicAddress, value: ByteArray) {
      val var4: ProtobufModel.CharacteristicValueInfo = this.protobufConverter.convertCharacteristicInfo(var1, var2);
      if (charNotificationSink != null) {
         charNotificationSink.success(var4.toByteArray());
      }
   }

   @JvmStatic
   fun `subscribeToNotifications$lambda$1`(var0: CharNotificationHandler, var1: ProtobufModel.NotifyCharacteristicRequest, var2: ByteArray): Unit {
      val var3: ProtobufModel.CharacteristicAddress = var1.getCharacteristic();
      var0.handleNotificationValue(var3, var2);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `subscribeToNotifications$lambda$2`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `subscribeToNotifications$lambda$3`(var0: ProtobufModel.NotifyCharacteristicRequest, var1: CharNotificationHandler, var2: java.lang.Throwable): Unit {
      if (var2 is BleDisconnectedException) {
         val var3: Disposable = subscriptionMap.remove(var0.getCharacteristic());
         if (var3 != null) {
            var3.dispose();
         }
      } else {
         val var4: ProtobufModel.CharacteristicAddress = var0.getCharacteristic();
         var1.handleNotificationError(var4, var2);
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `subscribeToNotifications$lambda$4`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   private fun unsubscribeFromAllNotifications() {
      charNotificationSink = null;
      val var1: java.util.Iterator = subscriptionMap.entrySet().iterator();

      while (var1.hasNext()) {
         ((var1.next() as Entry).getValue() as Disposable).dispose();
      }
   }

   public fun addSingleErrorToStream(subscriptionRequest: CharacteristicAddress, error: String) {
      val var4: ProtobufModel.CharacteristicValueInfo = this.protobufConverter.convertCharacteristicError(var1, var2);
      if (charNotificationSink != null) {
         charNotificationSink.success(var4.toByteArray());
      }
   }

   public fun addSingleReadToStream(charInfo: CharacteristicValueInfo) {
      val var2: ProtobufModel.CharacteristicAddress = var1.getCharacteristic();
      val var3: ByteArray = var1.getValue().toByteArray();
      this.handleNotificationValue(var2, var3);
   }

   public override fun onCancel(objectSink: Any?) {
      this.unsubscribeFromAllNotifications();
   }

   public override fun onListen(objectSink: Any?, eventSink: EventSink?) {
      if (var2 != null) {
         charNotificationSink = var2;
      }
   }

   public fun subscribeToNotifications(request: NotifyCharacteristicRequest) {
      val var2: UuidConverter = this.uuidConverter;
      val var3: ByteArray = var1.getCharacteristic().getCharacteristicUuid().getData().toByteArray();
      val var4: UUID = var2.uuidFromByteArray(var3);
      val var8: BleClient = this.bleClient;
      val var5: java.lang.String = var1.getCharacteristic().getDeviceId();
      val var6: java.lang.String = var1.getCharacteristic().getCharacteristicInstanceId();
      subscriptionMap.put(
         var1.getCharacteristic(),
         var8.setupNotification(var5, var4, Integer.parseInt(var6))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               new CharNotificationHandler$$ExternalSyntheticLambda1(new CharNotificationHandler$$ExternalSyntheticLambda0(this, var1)),
               new CharNotificationHandler$$ExternalSyntheticLambda3(new CharNotificationHandler$$ExternalSyntheticLambda2(var1, this))
            )
      );
   }

   public fun unsubscribeFromNotifications(request: NotifyNoMoreCharacteristicRequest) {
      val var2: Disposable = subscriptionMap.remove(var1.getCharacteristic());
      if (var2 != null) {
         var2.dispose();
      }
   }

   public companion object {
      private final var charNotificationSink: EventSink?
      private final val subscriptionMap: MutableMap<CharacteristicAddress, Disposable>
   }
}
