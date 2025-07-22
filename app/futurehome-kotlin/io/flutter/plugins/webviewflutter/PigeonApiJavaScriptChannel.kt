package io.flutter.plugins.webviewflutter

import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiJavaScriptChannel {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiJavaScriptChannel(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `postMessage$lambda$0`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   public abstract fun pigeon_defaultConstructor(channelName: String): JavaScriptChannel {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: JavaScriptChannel, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var4: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var3: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         throw new IllegalStateException("Attempting to create a new Dart instance of JavaScriptChannel, but the class has a nonnull callback method.");
      }
   }

   public fun postMessage(pigeon_instanceArg: JavaScriptChannel, messageArg: String, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var4: Result.Companion = Result.Companion;
         var3.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.JavaScriptChannel.postMessage",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2}),
               new PigeonApiJavaScriptChannel$$ExternalSyntheticLambda0(var3, "dev.flutter.pigeon.webview_flutter_android.JavaScriptChannel.postMessage")
            );
      }
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiJavaScriptChannel, var1: Any, var2: BasicMessageChannel.Reply) {
         val var5: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         val var3: Long = var1 as java.lang.Long;
         var1 = var5.get(1);
         var1 = var1 as java.lang.String;

         label12:
         try {
            var0.getPigeonRegistrar().getInstanceManager().addDartCreatedInstance(var0.pigeon_defaultConstructor(var1), var3);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = AndroidWebkitLibrary_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiJavaScriptChannel?) {
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

         val var7: PigeonApiJavaScriptChannel.Companion = this;
         val var5: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.JavaScriptChannel.pigeon_defaultConstructor", var6
         );
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiJavaScriptChannel$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
