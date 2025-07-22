package io.flutter.plugins.sharedpreferences;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;

// $VF: synthetic class
public final class Messages$SharedPreferencesApi$_CC {
   public static MessageCodec<Object> getCodec() {
      return Messages.PigeonCodec.INSTANCE;
   }

   public static void setUp(BinaryMessenger var0, Messages.SharedPreferencesApi var1) {
      setUp(var0, "", var1);
   }

   public static void setUp(BinaryMessenger var0, String var1, Messages.SharedPreferencesApi var2) {
      if (var1.isEmpty()) {
         var1 = "";
      } else {
         StringBuilder var3 = new StringBuilder(".");
         var3.append(var1);
         var1 = var3.toString();
      }

      BinaryMessenger.TaskQueue var7 = var0.makeBackgroundTaskQueue();
      StringBuilder var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.remove");
      var4.append(var1);
      BasicMessageChannel var8 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var8.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda0(var2));
      } else {
         var8.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.setBool");
      var4.append(var1);
      BasicMessageChannel var10 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var10.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda1(var2));
      } else {
         var10.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.setString");
      var4.append(var1);
      BasicMessageChannel var12 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var12.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda2(var2));
      } else {
         var12.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.setInt");
      var4.append(var1);
      BasicMessageChannel var14 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var14.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda3(var2));
      } else {
         var14.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.setDouble");
      var4.append(var1);
      BasicMessageChannel var16 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var16.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda4(var2));
      } else {
         var16.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.setEncodedStringList");
      var4.append(var1);
      BasicMessageChannel var18 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var18.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda5(var2));
      } else {
         var18.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.setDeprecatedStringList");
      var4.append(var1);
      BasicMessageChannel var20 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var20.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda6(var2));
      } else {
         var20.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.clear");
      var4.append(var1);
      BasicMessageChannel var22 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var22.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda7(var2));
      } else {
         var22.setMessageHandler(null);
      }

      var4 = new StringBuilder("dev.flutter.pigeon.shared_preferences_android.SharedPreferencesApi.getAll");
      var4.append(var1);
      BasicMessageChannel var5 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var5.setMessageHandler(new Messages$SharedPreferencesApi$$ExternalSyntheticLambda8(var2));
      } else {
         var5.setMessageHandler(null);
      }
   }
}
