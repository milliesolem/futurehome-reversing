package dev.fluttercommunity.plus.share

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0

internal class SharePlusPendingIntent : BroadcastReceiver {
   public open fun onReceive(context: Context, intent: Intent) {
      val var3: ComponentName;
      if (VERSION.SDK_INT >= 33) {
         var3 = ExternalSyntheticApiModelOutline0.m(var2, "android.intent.extra.CHOSEN_COMPONENT", ComponentName.class) as ComponentName;
      } else {
         var3 = var2.getParcelableExtra("android.intent.extra.CHOSEN_COMPONENT") as ComponentName;
      }

      if (var3 != null) {
         result = var3.flattenToString();
      }
   }

   public companion object {
      public final var result: String
         internal set
   }
}
