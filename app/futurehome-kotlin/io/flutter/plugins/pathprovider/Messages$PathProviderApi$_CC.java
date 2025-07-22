package io.flutter.plugins.pathprovider;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;

// $VF: synthetic class
public final class Messages$PathProviderApi$_CC {
   public static MessageCodec<Object> getCodec() {
      return Messages.PigeonCodec.INSTANCE;
   }

   public static void setUp(BinaryMessenger var0, Messages.PathProviderApi var1) {
      setUp(var0, "", var1);
   }

   public static void setUp(BinaryMessenger var0, String var1, Messages.PathProviderApi var2) {
      if (var1.isEmpty()) {
         var1 = "";
      } else {
         StringBuilder var3 = new StringBuilder(".");
         var3.append(var1);
         var1 = var3.toString();
      }

      BinaryMessenger.TaskQueue var7 = var0.makeBackgroundTaskQueue();
      StringBuilder var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getTemporaryPath");
      var4.append(var1);
      BasicMessageChannel var8 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var8.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda0(var2));
      } else {
         var8.setMessageHandler(null);
      }

      var7 = var0.makeBackgroundTaskQueue();
      var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getApplicationSupportPath");
      var4.append(var1);
      BasicMessageChannel var10 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var10.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda1(var2));
      } else {
         var10.setMessageHandler(null);
      }

      var7 = var0.makeBackgroundTaskQueue();
      var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getApplicationDocumentsPath");
      var4.append(var1);
      BasicMessageChannel var12 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var12.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda2(var2));
      } else {
         var12.setMessageHandler(null);
      }

      var7 = var0.makeBackgroundTaskQueue();
      var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getApplicationCachePath");
      var4.append(var1);
      BasicMessageChannel var14 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var14.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda3(var2));
      } else {
         var14.setMessageHandler(null);
      }

      var7 = var0.makeBackgroundTaskQueue();
      var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getExternalStoragePath");
      var4.append(var1);
      BasicMessageChannel var16 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var16.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda4(var2));
      } else {
         var16.setMessageHandler(null);
      }

      var7 = var0.makeBackgroundTaskQueue();
      var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getExternalCachePaths");
      var4.append(var1);
      BasicMessageChannel var18 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var18.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda5(var2));
      } else {
         var18.setMessageHandler(null);
      }

      var7 = var0.makeBackgroundTaskQueue();
      var4 = new StringBuilder("dev.flutter.pigeon.path_provider_android.PathProviderApi.getExternalStoragePaths");
      var4.append(var1);
      BasicMessageChannel var5 = new BasicMessageChannel<>(var0, var4.toString(), getCodec(), var7);
      if (var2 != null) {
         var5.setMessageHandler(new Messages$PathProviderApi$$ExternalSyntheticLambda6(var2));
      } else {
         var5.setMessageHandler(null);
      }
   }
}
