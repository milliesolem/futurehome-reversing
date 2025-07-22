package io.flutter.plugins.webviewflutter

import android.webkit.PermissionRequest
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiPermissionRequest {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiPermissionRequest(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `pigeon_newInstance$lambda$0`(var0: Function1, var1: java.lang.String, var2: Any) {
      if (var2 is java.util.List) {
         val var4: java.util.List = var2 as java.util.List;
         if ((var2 as java.util.List).size() > 1) {
            var2 = Result.Companion;
            var2 = var4.get(0);
            var2 = var2 as java.lang.String;
            val var3: Any = var4.get(1);
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError(var2, var3 as java.lang.String, var4.get(2) as java.lang.String)))
               )
            );
         } else {
            val var5: Result.Companion = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(Unit.INSTANCE)));
         }
      } else {
         val var9: Result.Companion = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
   }

   public abstract fun deny(pigeon_instance: PermissionRequest) {
   }

   public abstract fun grant(pigeon_instance: PermissionRequest, resources: List<String>) {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: PermissionRequest, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var7: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var6: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.PermissionRequest.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1), this.resources(var1)}),
               new PigeonApiPermissionRequest$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.PermissionRequest.pigeon_newInstance")
            );
      }
   }

   public abstract fun resources(pigeon_instance: PermissionRequest): List<String> {
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiPermissionRequest, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as PermissionRequest;
         var var9: Any = var3.get(1);
         var9 = var9 as java.util.List;

         label12:
         try {
            var0.grant(var1, (java.util.List<java.lang.String>)var9);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiPermissionRequest, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as PermissionRequest;

         label12:
         try {
            var0.deny(var1);
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiPermissionRequest?) {
         var var6: MessageCodec;
         label23: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label23;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiPermissionRequest.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.PermissionRequest.grant", var6);
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiPermissionRequest$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.PermissionRequest.deny", var6);
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiPermissionRequest$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
