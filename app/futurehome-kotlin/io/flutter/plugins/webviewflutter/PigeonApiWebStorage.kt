package io.flutter.plugins.webviewflutter

import android.webkit.WebStorage
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiWebStorage {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiWebStorage(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
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

   public abstract fun deleteAllData(pigeon_instance: WebStorage) {
   }

   public abstract fun instance(): WebStorage {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: WebStorage, callback: (Result<Unit>) -> Unit) {
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
               "dev.flutter.pigeon.webview_flutter_android.WebStorage.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiWebStorage$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.WebStorage.pigeon_newInstance")
            );
      }
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiWebStorage, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         val var3: Long = var1 as java.lang.Long;

         label12:
         try {
            var0.getPigeonRegistrar().getInstanceManager().addDartCreatedInstance(var0.instance(), var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiWebStorage, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebStorage;

         label12:
         try {
            var0.deleteAllData(var1);
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiWebStorage?) {
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

         val var7: PigeonApiWebStorage.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebStorage.instance", var6);
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiWebStorage$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebStorage.deleteAllData", var6);
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiWebStorage$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
