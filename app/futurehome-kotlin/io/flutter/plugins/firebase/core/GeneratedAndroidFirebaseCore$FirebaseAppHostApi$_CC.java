package io.flutter.plugins.firebase.core;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;

// $VF: synthetic class
public final class GeneratedAndroidFirebaseCore$FirebaseAppHostApi$_CC {
   public static MessageCodec<Object> getCodec() {
      return new StandardMessageCodec();
   }

   public static void setup(BinaryMessenger var0, GeneratedAndroidFirebaseCore.FirebaseAppHostApi var1) {
      BasicMessageChannel var2 = new BasicMessageChannel<>(var0, "dev.flutter.pigeon.FirebaseAppHostApi.setAutomaticDataCollectionEnabled", getCodec());
      if (var1 != null) {
         var2.setMessageHandler(new GeneratedAndroidFirebaseCore$FirebaseAppHostApi$$ExternalSyntheticLambda0(var1));
      } else {
         var2.setMessageHandler(null);
      }

      var2 = new BasicMessageChannel<>(var0, "dev.flutter.pigeon.FirebaseAppHostApi.setAutomaticResourceManagementEnabled", getCodec());
      if (var1 != null) {
         var2.setMessageHandler(new GeneratedAndroidFirebaseCore$FirebaseAppHostApi$$ExternalSyntheticLambda1(var1));
      } else {
         var2.setMessageHandler(null);
      }

      BasicMessageChannel var3 = new BasicMessageChannel<>(var0, "dev.flutter.pigeon.FirebaseAppHostApi.delete", getCodec());
      if (var1 != null) {
         var3.setMessageHandler(new GeneratedAndroidFirebaseCore$FirebaseAppHostApi$$ExternalSyntheticLambda2(var1));
      } else {
         var3.setMessageHandler(null);
      }
   }
}
