package io.flutter.plugins.webviewflutter

import android.webkit.CookieManager
import android.webkit.WebView
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiCookieManager {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiCookieManager(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
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

   public abstract fun instance(): CookieManager {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: CookieManager, callback: (Result<Unit>) -> Unit) {
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
               "dev.flutter.pigeon.webview_flutter_android.CookieManager.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiCookieManager$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.CookieManager.pigeon_newInstance")
            );
      }
   }

   public abstract fun removeAllCookies(pigeon_instance: CookieManager, callback: (Result<Boolean>) -> Unit) {
   }

   public abstract fun setAcceptThirdPartyCookies(pigeon_instance: CookieManager, webView: WebView, accept: Boolean) {
   }

   public abstract fun setCookie(pigeon_instance: CookieManager, url: String, value: String) {
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiCookieManager, var1: Any, var2: BasicMessageChannel.Reply) {
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
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiCookieManager, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as CookieManager;
         var var3: Any = var4.get(1);
         var3 = var3 as java.lang.String;
         var var11: Any = var4.get(2);
         var11 = var11 as java.lang.String;

         label12:
         try {
            var0.setCookie(var1, (java.lang.String)var3, (java.lang.String)var11);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      @JvmStatic
      fun `setUpMessageHandlers$lambda$6$lambda$5`(var0: PigeonApiCookieManager, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var0.removeAllCookies(var1 as CookieManager, new PigeonApiCookieManager$Companion$$ExternalSyntheticLambda0(var2));
      }

      @JvmStatic
      fun `setUpMessageHandlers$lambda$6$lambda$5$lambda$4`(var0: BasicMessageChannel.Reply, var1: Result): Unit {
         var var2: java.lang.Throwable = Result.exceptionOrNull-impl(var1.unbox-impl());
         if (var2 != null) {
            var0.reply(AndroidWebkitLibrary_gKt.access$wrapError(var2));
         } else {
            var2 = (java.lang.Throwable)var1.unbox-impl();
            var var3: Any = var2;
            if (Result.isFailure-impl(var2)) {
               var3 = null;
            }

            var0.reply(AndroidWebkitLibrary_gKt.access$wrapResult(var3 as java.lang.Boolean));
         }

         return Unit.INSTANCE;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$8$lambda$7`(var0: PigeonApiCookieManager, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as CookieManager;
         var var5: Any = var4.get(1);
         var5 = var5 as WebView;
         val var11: Any = var4.get(2);
         val var3: Boolean = var11 as java.lang.Boolean;

         label12:
         try {
            var0.setAcceptThirdPartyCookies(var1, (WebView)var5, var3);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = AndroidWebkitLibrary_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiCookieManager?) {
         var var6: MessageCodec;
         label33: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label33;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiCookieManager.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.CookieManager.instance", var6);
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiCookieManager$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var9: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.CookieManager.setCookie", var6);
         if (var2 != null) {
            var9.setMessageHandler(new PigeonApiCookieManager$Companion$$ExternalSyntheticLambda2(var2));
         } else {
            var9.setMessageHandler(null);
         }

         val var10: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.CookieManager.removeAllCookies", var6);
         if (var2 != null) {
            var10.setMessageHandler(new PigeonApiCookieManager$Companion$$ExternalSyntheticLambda3(var2));
         } else {
            var10.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.CookieManager.setAcceptThirdPartyCookies", var6
         );
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiCookieManager$Companion$$ExternalSyntheticLambda4(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
