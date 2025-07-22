package io.flutter.plugins.webviewflutter

import android.util.Log

@JvmSynthetic
fun `access$createConnectionError`(var0: java.lang.String): AndroidWebKitError {
   return createConnectionError(var0);
}

@JvmSynthetic
fun `access$wrapError`(var0: java.lang.Throwable): java.util.List {
   return wrapError(var0);
}

@JvmSynthetic
fun `access$wrapResult`(var0: Any): java.util.List {
   return wrapResult(var0);
}

private fun createConnectionError(channelName: String): AndroidWebKitError {
   val var1: StringBuilder = new StringBuilder("Unable to establish connection on channel: '");
   var1.append(var0);
   var1.append("'.");
   return new AndroidWebKitError("channel-error", var1.toString(), "");
}

private fun wrapError(exception: Throwable): List<Any?> {
   val var6: java.util.List;
   if (var0 is AndroidWebKitError) {
      var6 = CollectionsKt.listOf(
         new Object[]{(var0 as AndroidWebKitError).getCode(), (var0 as AndroidWebKitError).getMessage(), (var0 as AndroidWebKitError).getDetails()}
      );
   } else {
      val var3: java.lang.String = var0.getClass().getSimpleName();
      val var1: java.lang.String = var0.toString();
      val var2: java.lang.Throwable = var0.getCause();
      val var7: java.lang.String = Log.getStackTraceString(var0);
      val var4: StringBuilder = new StringBuilder("Cause: ");
      var4.append(var2);
      var4.append(", Stacktrace: ");
      var4.append(var7);
      var6 = CollectionsKt.listOf(new java.lang.String[]{var3, var1, var4.toString()});
   }

   return var6;
}

private fun wrapResult(result: Any?): List<Any?> {
   return CollectionsKt.listOf(var0);
}
