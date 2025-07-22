package no.futurehome.futurehome_app.widget.helpers

import android.content.Context
import android.net.ConnectivityManager

public class ConnectionChecker(context: Context) {
   private final val context: Context

   init {
      this.context = var1;
   }

   public fun isConnected(): Boolean {
      var var3: Any = this.context.getSystemService("connectivity");
      var3 = (var3 as ConnectivityManager).getActiveNetworkInfo();
      var var1: Boolean = false;
      if (var3 != null) {
         var1 = false;
         if (var3.isConnected()) {
            var1 = true;
         }
      }

      return var1;
   }
}
