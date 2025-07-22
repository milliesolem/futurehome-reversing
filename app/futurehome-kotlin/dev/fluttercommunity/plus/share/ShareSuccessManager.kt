package dev.fluttercommunity.plus.share

import android.content.Context
import android.content.Intent
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.MethodChannel.Result
import java.util.concurrent.atomic.AtomicBoolean

internal class ShareSuccessManager(context: Context) : PluginRegistry.ActivityResultListener {
   private final val context: Context
   private final var callback: Result?
   private final var isCalledBack: AtomicBoolean

   init {
      this.context = var1;
      this.isCalledBack = new AtomicBoolean(true);
   }

   private fun returnResult(result: String) {
      if (this.isCalledBack.compareAndSet(false, true)) {
         val var2: MethodChannel.Result = this.callback;
         if (this.callback != null) {
            var2.success(var1);
            this.callback = null;
         }
      }
   }

   public fun clear() {
      this.isCalledBack.set(true);
      this.callback = null;
   }

   public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
      val var4: Boolean;
      if (var1 == 22643) {
         this.returnResult(SharePlusPendingIntent.Companion.getResult());
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public fun setCallback(callback: Result) {
      if (this.isCalledBack.compareAndSet(true, false)) {
         SharePlusPendingIntent.Companion.setResult("");
         this.isCalledBack.set(false);
         this.callback = var1;
      } else {
         if (this.callback != null) {
            this.callback.success("dev.fluttercommunity.plus/share/unavailable");
         }

         SharePlusPendingIntent.Companion.setResult("");
         this.isCalledBack.set(false);
         this.callback = var1;
      }
   }

   public fun unavailable() {
      this.returnResult("dev.fluttercommunity.plus/share/unavailable");
   }

   public companion object {
      public const val ACTIVITY_CODE: Int
      public const val RESULT_UNAVAILABLE: String
   }
}
