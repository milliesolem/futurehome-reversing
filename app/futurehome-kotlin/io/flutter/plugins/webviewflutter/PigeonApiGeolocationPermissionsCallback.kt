package io.flutter.plugins.webviewflutter

import android.webkit.GeolocationPermissions.Callback
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiGeolocationPermissionsCallback {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiGeolocationPermissionsCallback(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `pigeon_newInstance$lambda$0`(var0: Function1, var1: java.lang.String, var2: Any) {
      if (var2 is java.util.List) {
         val var4: java.util.List = var2 as java.util.List;
         if ((var2 as java.util.List).size() > 1) {
            var2 = Result.Companion;
            var2 = var4.get(0);
            val var3: java.lang.String = var2 as java.lang.String;
            var2 = var4.get(1);
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError(var3, var2 as java.lang.String, var4.get(2) as java.lang.String)))
               )
            );
         } else {
            val var5: Result.Companion = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(Unit.INSTANCE)));
         }
      } else {
         var2 = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
   }

   public abstract fun invoke(pigeon_instance: Callback, origin: String, allow: Boolean, retain: Boolean) {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: Callback, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var6: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var5: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.GeolocationPermissionsCallback.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiGeolocationPermissionsCallback$$ExternalSyntheticLambda0(
                  var2, "dev.flutter.pigeon.webview_flutter_android.GeolocationPermissionsCallback.pigeon_newInstance"
               )
            );
      }
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiGeolocationPermissionsCallback, var1: Any, var2: BasicMessageChannel.Reply) {
         val var5: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as Callback;
         var var6: Any = var5.get(1);
         var6 = var6 as java.lang.String;
         val var7: Any = var5.get(2);
         val var3: Boolean = var7 as java.lang.Boolean;
         val var13: Any = var5.get(3);
         val var4: Boolean = var13 as java.lang.Boolean;

         label12:
         try {
            var0.invoke(var1, (java.lang.String)var6, var3, var4);
            var10 = CollectionsKt.listOf(null);
         } catch (var8: java.lang.Throwable) {
            var10 = AndroidWebkitLibrary_gKt.access$wrapError(var8);
            break label12;
         }

         var2.reply(var10);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiGeolocationPermissionsCallback?) {
         var var6: MessageCodec;
         label18: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label18;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiGeolocationPermissionsCallback.Companion = this;
         val var5: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.GeolocationPermissionsCallback.invoke", var6);
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiGeolocationPermissionsCallback$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
