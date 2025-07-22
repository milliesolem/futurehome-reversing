package io.flutter.plugins.webviewflutter

import android.view.View
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiView {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiView(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
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

   public abstract fun getScrollPosition(pigeon_instance: View): WebViewPoint {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: View, callback: (Result<Unit>) -> Unit) {
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
               "dev.flutter.pigeon.webview_flutter_android.View.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiView$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.View.pigeon_newInstance")
            );
      }
   }

   public abstract fun scrollBy(pigeon_instance: View, x: Long, y: Long) {
   }

   public abstract fun scrollTo(pigeon_instance: View, x: Long, y: Long) {
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var7: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as View;
         val var8: Any = var7.get(1);
         val var5: Long = var8 as java.lang.Long;
         val var14: Any = var7.get(2);
         val var3: Long = var14 as java.lang.Long;

         label12:
         try {
            var0.scrollTo(var1, var5, var3);
            var11 = CollectionsKt.listOf(null);
         } catch (var9: java.lang.Throwable) {
            var11 = AndroidWebkitLibrary_gKt.access$wrapError(var9);
            break label12;
         }

         var2.reply(var11);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var7: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as View;
         val var8: Any = var7.get(1);
         val var3: Long = var8 as java.lang.Long;
         val var14: Any = var7.get(2);
         val var5: Long = var14 as java.lang.Long;

         label12:
         try {
            var0.scrollBy(var1, var3, var5);
            var11 = CollectionsKt.listOf(null);
         } catch (var9: java.lang.Throwable) {
            var11 = AndroidWebkitLibrary_gKt.access$wrapError(var9);
            break label12;
         }

         var2.reply(var11);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$5$lambda$4`(var0: PigeonApiView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as View;

         label12:
         try {
            var5 = CollectionsKt.listOf(var0.getScrollPosition(var1));
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiView?) {
         var var6: MessageCodec;
         label28: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label28;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiView.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.View.scrollTo", var6);
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiView$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var9: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.View.scrollBy", var6);
         if (var2 != null) {
            var9.setMessageHandler(new PigeonApiView$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var9.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.View.getScrollPosition", var6);
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiView$Companion$$ExternalSyntheticLambda2(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
