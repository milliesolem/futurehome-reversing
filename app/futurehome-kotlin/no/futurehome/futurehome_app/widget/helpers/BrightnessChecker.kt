package no.futurehome.futurehome_app.widget.helpers

import android.content.Context

public class BrightnessChecker private constructor() {
   public final var isDark: Boolean
      private set

   public fun updateTheme(context: Context) {
      val var2: Int = var1.getResources().getConfiguration().uiMode and 48;
      var var3: Boolean = false;
      if (var2 != 0) {
         var3 = false;
         if (var2 != 16) {
            if (var2 != 32) {
               var3 = false;
            } else {
               var3 = true;
            }
         }
      }

      this.isDark = var3;
   }

   public companion object {
      public final val instance: BrightnessChecker
   }
}
