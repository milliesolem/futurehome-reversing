package io.flutter.plugins.localauth;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;

// $VF: synthetic class
public final class Messages$LocalAuthApi$_CC {
   public static MessageCodec<Object> getCodec() {
      return Messages.PigeonCodec.INSTANCE;
   }

   public static void setUp(BinaryMessenger var0, Messages.LocalAuthApi var1) {
      setUp(var0, "", var1);
   }

   public static void setUp(BinaryMessenger var0, String var1, Messages.LocalAuthApi var2) {
      if (var1.isEmpty()) {
         var1 = "";
      } else {
         StringBuilder var3 = new StringBuilder(".");
         var3.append(var1);
         var1 = var3.toString();
      }

      StringBuilder var6 = new StringBuilder("dev.flutter.pigeon.local_auth_android.LocalAuthApi.isDeviceSupported");
      var6.append(var1);
      BasicMessageChannel var7 = new BasicMessageChannel<>(var0, var6.toString(), getCodec());
      if (var2 != null) {
         var7.setMessageHandler(new Messages$LocalAuthApi$$ExternalSyntheticLambda0(var2));
      } else {
         var7.setMessageHandler(null);
      }

      var6 = new StringBuilder("dev.flutter.pigeon.local_auth_android.LocalAuthApi.deviceCanSupportBiometrics");
      var6.append(var1);
      BasicMessageChannel var9 = new BasicMessageChannel<>(var0, var6.toString(), getCodec());
      if (var2 != null) {
         var9.setMessageHandler(new Messages$LocalAuthApi$$ExternalSyntheticLambda1(var2));
      } else {
         var9.setMessageHandler(null);
      }

      var6 = new StringBuilder("dev.flutter.pigeon.local_auth_android.LocalAuthApi.stopAuthentication");
      var6.append(var1);
      BasicMessageChannel var11 = new BasicMessageChannel<>(var0, var6.toString(), getCodec());
      if (var2 != null) {
         var11.setMessageHandler(new Messages$LocalAuthApi$$ExternalSyntheticLambda2(var2));
      } else {
         var11.setMessageHandler(null);
      }

      var6 = new StringBuilder("dev.flutter.pigeon.local_auth_android.LocalAuthApi.getEnrolledBiometrics");
      var6.append(var1);
      BasicMessageChannel var13 = new BasicMessageChannel<>(var0, var6.toString(), getCodec());
      if (var2 != null) {
         var13.setMessageHandler(new Messages$LocalAuthApi$$ExternalSyntheticLambda3(var2));
      } else {
         var13.setMessageHandler(null);
      }

      var6 = new StringBuilder("dev.flutter.pigeon.local_auth_android.LocalAuthApi.authenticate");
      var6.append(var1);
      BasicMessageChannel var4 = new BasicMessageChannel<>(var0, var6.toString(), getCodec());
      if (var2 != null) {
         var4.setMessageHandler(new Messages$LocalAuthApi$$ExternalSyntheticLambda4(var2));
      } else {
         var4.setMessageHandler(null);
      }
   }
}
