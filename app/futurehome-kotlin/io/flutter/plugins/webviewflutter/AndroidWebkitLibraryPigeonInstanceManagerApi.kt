package io.flutter.plugins.webviewflutter

import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import kotlin.jvm.functions.Function1

private class AndroidWebkitLibraryPigeonInstanceManagerApi(binaryMessenger: BinaryMessenger) {
   public final val binaryMessenger: BinaryMessenger

   init {
      this.binaryMessenger = var1;
   }

   @JvmStatic
   fun `codec_delegate$lambda$1`(): AndroidWebkitLibraryPigeonCodec {
      return new AndroidWebkitLibraryPigeonCodec();
   }

   @JvmStatic
   fun `removeStrongReference$lambda$0`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   public fun removeStrongReference(identifierArg: Long, callback: (Result<Unit>) -> Unit) {
      new BasicMessageChannel<>(
            this.binaryMessenger, "dev.flutter.pigeon.webview_flutter_android.PigeonInternalInstanceManager.removeStrongReference", Companion.getCodec()
         )
         .send(
            CollectionsKt.listOf(var1),
            new AndroidWebkitLibraryPigeonInstanceManagerApi$$ExternalSyntheticLambda0(
               var3, "dev.flutter.pigeon.webview_flutter_android.PigeonInternalInstanceManager.removeStrongReference"
            )
         );
   }

   public companion object {
      public final val codec: MessageCodec<Any?>
         public final get() {
            return AndroidWebkitLibraryPigeonInstanceManagerApi.access$getCodec$delegate$cp().getValue() as MessageCodec<Object>;
         }


      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: AndroidWebkitLibraryPigeonInstanceManager, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         val var3: Long = var1 as java.lang.Long;

         label12:
         try {
            var0.remove(var3);
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
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: AndroidWebkitLibraryPigeonInstanceManager, var1: Any, var2: BasicMessageChannel.Reply) {
         label12:
         try {
            var0.clear();
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, instanceManager: AndroidWebkitLibraryPigeonInstanceManager?) {
         val var3: AndroidWebkitLibraryPigeonInstanceManagerApi.Companion = this;
         val var5: BasicMessageChannel = new BasicMessageChannel<>(
            var1, "dev.flutter.pigeon.webview_flutter_android.PigeonInternalInstanceManager.removeStrongReference", this.getCodec()
         );
         if (var2 != null) {
            var5.setMessageHandler(new AndroidWebkitLibraryPigeonInstanceManagerApi$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var5.setMessageHandler(null);
         }

         val var4: BasicMessageChannel = new BasicMessageChannel<>(
            var1, "dev.flutter.pigeon.webview_flutter_android.PigeonInternalInstanceManager.clear", this.getCodec()
         );
         if (var2 != null) {
            var4.setMessageHandler(new AndroidWebkitLibraryPigeonInstanceManagerApi$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var4.setMessageHandler(null);
         }
      }
   }
}
