package dev.fluttercommunity.plus.wakelock

import android.app.Activity

internal class Wakelock {
   public final var activity: Activity?
      internal set

   private final val enabled: Boolean
      private final get() {
         val var2: Activity = this.activity;
         val var1: Boolean;
         if ((var2.getWindow().getAttributes().flags and 128) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public fun isEnabled(): IsEnabledMessage {
      if (this.activity != null) {
         return new IsEnabledMessage(this.getEnabled());
      } else {
         throw new NoActivityException();
      }
   }

   public fun toggle(message: ToggleMessage) {
      val var3: Activity = this.activity;
      if (this.activity != null) {
         val var2: Boolean = this.getEnabled();
         val var4: java.lang.Boolean = var1.getEnable();
         if (var4) {
            if (!var2) {
               var3.getWindow().addFlags(128);
            }
         } else if (var2) {
            var3.getWindow().clearFlags(128);
         }
      } else {
         throw new NoActivityException();
      }
   }
}
