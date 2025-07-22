package io.flutter.plugins.webviewflutter

import android.webkit.WebSettings
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

public abstract class PigeonApiWebSettings {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiWebSettings(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
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

   public abstract fun getUserAgentString(pigeon_instance: WebSettings): String {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: WebSettings, callback: (Result<Unit>) -> Unit) {
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
               "dev.flutter.pigeon.webview_flutter_android.WebSettings.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiWebSettings$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.WebSettings.pigeon_newInstance")
            );
      }
   }

   public abstract fun setAllowContentAccess(pigeon_instance: WebSettings, enabled: Boolean) {
   }

   public abstract fun setAllowFileAccess(pigeon_instance: WebSettings, enabled: Boolean) {
   }

   public abstract fun setBuiltInZoomControls(pigeon_instance: WebSettings, enabled: Boolean) {
   }

   public abstract fun setDisplayZoomControls(pigeon_instance: WebSettings, enabled: Boolean) {
   }

   public abstract fun setDomStorageEnabled(pigeon_instance: WebSettings, flag: Boolean) {
   }

   public abstract fun setGeolocationEnabled(pigeon_instance: WebSettings, enabled: Boolean) {
   }

   public abstract fun setJavaScriptCanOpenWindowsAutomatically(pigeon_instance: WebSettings, flag: Boolean) {
   }

   public abstract fun setJavaScriptEnabled(pigeon_instance: WebSettings, flag: Boolean) {
   }

   public abstract fun setLoadWithOverviewMode(pigeon_instance: WebSettings, overview: Boolean) {
   }

   public abstract fun setMediaPlaybackRequiresUserGesture(pigeon_instance: WebSettings, require: Boolean) {
   }

   public abstract fun setSupportMultipleWindows(pigeon_instance: WebSettings, support: Boolean) {
   }

   public abstract fun setSupportZoom(pigeon_instance: WebSettings, support: Boolean) {
   }

   public abstract fun setTextZoom(pigeon_instance: WebSettings, textZoom: Long) {
   }

   public abstract fun setUseWideViewPort(pigeon_instance: WebSettings, use: Boolean) {
   }

   public abstract fun setUserAgentString(pigeon_instance: WebSettings, userAgentString: String?) {
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setDomStorageEnabled(var1, var3);
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
      fun `setUpMessageHandlers$lambda$11$lambda$10`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebSettings;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setMediaPlaybackRequiresUserGesture((WebSettings)var4, var3);
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
      fun `setUpMessageHandlers$lambda$13$lambda$12`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebSettings;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setSupportZoom((WebSettings)var4, var3);
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
      fun `setUpMessageHandlers$lambda$15$lambda$14`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setLoadWithOverviewMode(var1, var3);
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
      fun `setUpMessageHandlers$lambda$17$lambda$16`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebSettings;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setUseWideViewPort((WebSettings)var4, var3);
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
      fun `setUpMessageHandlers$lambda$19$lambda$18`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebSettings;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setDisplayZoomControls((WebSettings)var4, var3);
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
      fun `setUpMessageHandlers$lambda$21$lambda$20`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebSettings;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setBuiltInZoomControls((WebSettings)var4, var3);
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
      fun `setUpMessageHandlers$lambda$23$lambda$22`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebSettings;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setAllowFileAccess((WebSettings)var4, var3);
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
      fun `setUpMessageHandlers$lambda$25$lambda$24`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setAllowContentAccess(var1, var3);
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
      fun `setUpMessageHandlers$lambda$27$lambda$26`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setGeolocationEnabled(var1, var3);
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
      fun `setUpMessageHandlers$lambda$29$lambda$28`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var9: java.util.List = var1 as java.util.List;
         var var5: Any = (var1 as java.util.List).get(0);
         var5 = var5 as WebSettings;
         var1 = var9.get(1);
         val var3: Long = var1 as java.lang.Long;

         label12:
         try {
            var0.setTextZoom((WebSettings)var5, var3);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = AndroidWebkitLibrary_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setJavaScriptCanOpenWindowsAutomatically(var1, var3);
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
      fun `setUpMessageHandlers$lambda$31$lambda$30`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;

         label12:
         try {
            var5 = CollectionsKt.listOf(var0.getUserAgentString(var1));
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$5$lambda$4`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setSupportMultipleWindows(var1, var3);
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
      fun `setUpMessageHandlers$lambda$7$lambda$6`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebSettings;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setJavaScriptEnabled(var1, var3);
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
      fun `setUpMessageHandlers$lambda$9$lambda$8`(var0: PigeonApiWebSettings, var1: Any, var2: BasicMessageChannel.Reply) {
         val var7: java.util.List = var1 as java.util.List;
         var var3: Any = (var1 as java.util.List).get(0);
         var3 = var3 as WebSettings;
         var1 = var7.get(1) as java.lang.String;

         label12:
         try {
            var0.setUserAgentString((WebSettings)var3, var1);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiWebSettings?) {
         var var6: MessageCodec;
         label93: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label93;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiWebSettings.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setDomStorageEnabled", var6);
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var9: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setJavaScriptCanOpenWindowsAutomatically", var6
         );
         if (var2 != null) {
            var9.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda13(var2));
         } else {
            var9.setMessageHandler(null);
         }

         val var10: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setSupportMultipleWindows", var6
         );
         if (var2 != null) {
            var10.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda14(var2));
         } else {
            var10.setMessageHandler(null);
         }

         val var11: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setJavaScriptEnabled", var6);
         if (var2 != null) {
            var11.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda15(var2));
         } else {
            var11.setMessageHandler(null);
         }

         val var12: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setUserAgentString", var6);
         if (var2 != null) {
            var12.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var12.setMessageHandler(null);
         }

         val var13: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setMediaPlaybackRequiresUserGesture", var6
         );
         if (var2 != null) {
            var13.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda2(var2));
         } else {
            var13.setMessageHandler(null);
         }

         val var14: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setSupportZoom", var6);
         if (var2 != null) {
            var14.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda3(var2));
         } else {
            var14.setMessageHandler(null);
         }

         val var15: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setLoadWithOverviewMode", var6);
         if (var2 != null) {
            var15.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda4(var2));
         } else {
            var15.setMessageHandler(null);
         }

         val var16: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setUseWideViewPort", var6);
         if (var2 != null) {
            var16.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda5(var2));
         } else {
            var16.setMessageHandler(null);
         }

         val var17: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setDisplayZoomControls", var6);
         if (var2 != null) {
            var17.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda6(var2));
         } else {
            var17.setMessageHandler(null);
         }

         val var18: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setBuiltInZoomControls", var6);
         if (var2 != null) {
            var18.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda7(var2));
         } else {
            var18.setMessageHandler(null);
         }

         val var19: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setAllowFileAccess", var6);
         if (var2 != null) {
            var19.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda8(var2));
         } else {
            var19.setMessageHandler(null);
         }

         val var20: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setAllowContentAccess", var6);
         if (var2 != null) {
            var20.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda9(var2));
         } else {
            var20.setMessageHandler(null);
         }

         val var21: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setGeolocationEnabled", var6);
         if (var2 != null) {
            var21.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda10(var2));
         } else {
            var21.setMessageHandler(null);
         }

         val var22: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.setTextZoom", var6);
         if (var2 != null) {
            var22.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda11(var2));
         } else {
            var22.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebSettings.getUserAgentString", var6);
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiWebSettings$Companion$$ExternalSyntheticLambda12(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
