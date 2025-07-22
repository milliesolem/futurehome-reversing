package io.flutter.plugins.firebase.core;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;

// $VF: synthetic class
public final class GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$_CC {
   public static MessageCodec<Object> getCodec() {
      return GeneratedAndroidFirebaseCore.FirebaseCoreHostApiCodec.INSTANCE;
   }

   public static void setup(BinaryMessenger var0, GeneratedAndroidFirebaseCore.FirebaseCoreHostApi var1) {
      BasicMessageChannel var2 = new BasicMessageChannel<>(var0, "dev.flutter.pigeon.FirebaseCoreHostApi.initializeApp", getCodec());
      if (var1 != null) {
         var2.setMessageHandler(new GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$$ExternalSyntheticLambda0(var1));
      } else {
         var2.setMessageHandler(null);
      }

      var2 = new BasicMessageChannel<>(var0, "dev.flutter.pigeon.FirebaseCoreHostApi.initializeCore", getCodec());
      if (var1 != null) {
         var2.setMessageHandler(new GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$$ExternalSyntheticLambda1(var1));
      } else {
         var2.setMessageHandler(null);
      }

      BasicMessageChannel var3 = new BasicMessageChannel<>(var0, "dev.flutter.pigeon.FirebaseCoreHostApi.optionsFromResource", getCodec());
      if (var1 != null) {
         var3.setMessageHandler(new GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$$ExternalSyntheticLambda2(var1));
      } else {
         var3.setMessageHandler(null);
      }
   }
}
