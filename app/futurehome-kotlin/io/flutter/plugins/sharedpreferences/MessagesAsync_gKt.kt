package io.flutter.plugins.sharedpreferences

import android.util.Log

@JvmSynthetic
fun `access$wrapError`(var0: java.lang.Throwable): java.util.List {
   return wrapError(var0);
}

private fun wrapError(exception: Throwable): List<Any?> {
   val var6: java.util.List;
   if (var0 is SharedPreferencesError) {
      var6 = CollectionsKt.listOf(
         new Object[]{(var0 as SharedPreferencesError).getCode(), (var0 as SharedPreferencesError).getMessage(), (var0 as SharedPreferencesError).getDetails()}
      );
   } else {
      val var2: java.lang.String = var0.getClass().getSimpleName();
      val var1: java.lang.String = var0.toString();
      val var3: java.lang.Throwable = var0.getCause();
      val var7: java.lang.String = Log.getStackTraceString(var0);
      val var4: StringBuilder = new StringBuilder("Cause: ");
      var4.append(var3);
      var4.append(", Stacktrace: ");
      var4.append(var7);
      var6 = CollectionsKt.listOf(new java.lang.String[]{var2, var1, var4.toString()});
   }

   return var6;
}

private fun wrapResult(result: Any?): List<Any?> {
   return CollectionsKt.listOf(var0);
}
