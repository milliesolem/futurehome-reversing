package okhttp3.internal.platform.android

import android.util.Log
import java.util.LinkedHashMap
import java.util.Map.Entry
import java.util.concurrent.CopyOnWriteArraySet
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.jvm.internal.Intrinsics
import okhttp3.OkHttpClient
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.http2.Http2

public object AndroidLog {
   private const val MAX_LOG_LENGTH: Int = 4000
   private final val configuredLoggers: CopyOnWriteArraySet<Logger> = new CopyOnWriteArraySet()
   private final val knownLoggers: Map<String, String>

   @JvmStatic
   fun {
      val var1: LinkedHashMap = new LinkedHashMap();
      val var0: Package = OkHttpClient.class.getPackage();
      val var2: java.lang.String;
      if (var0 != null) {
         var2 = var0.getName();
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var1.put(var2, "OkHttp");
      }

      val var3: java.util.Map = var1;
      val var4: java.lang.String = OkHttpClient.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var4, "OkHttpClient::class.java.name");
      var3.put(var4, "okhttp.OkHttpClient");
      val var5: java.lang.String = Http2.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var5, "Http2::class.java.name");
      var3.put(var5, "okhttp.Http2");
      val var6: java.lang.String = TaskRunner.class.getName();
      Intrinsics.checkExpressionValueIsNotNull(var6, "TaskRunner::class.java.name");
      var3.put(var6, "okhttp.TaskRunner");
      knownLoggers = MapsKt.toMap(var3);
   }

   private fun enableLogging(logger: String, tag: String) {
      val var3: Logger = Logger.getLogger(var1);
      if (configuredLoggers.add(var3)) {
         Intrinsics.checkExpressionValueIsNotNull(var3, "logger");
         var3.setUseParentHandlers(false);
         val var4: Level;
         if (Log.isLoggable(var2, 3)) {
            var4 = Level.FINE;
         } else if (Log.isLoggable(var2, 4)) {
            var4 = Level.INFO;
         } else {
            var4 = Level.WARNING;
         }

         var3.setLevel(var4);
         var3.addHandler(AndroidLogHandler.INSTANCE);
      }
   }

   private fun loggerTag(loggerName: String): String {
      val var2: java.lang.String = knownLoggers.get(var1);
      if (var2 != null) {
         var1 = var2;
      } else {
         var1 = StringsKt.take(var1, 23);
      }

      return var1;
   }

   internal fun androidLog(loggerName: String, logLevel: Int, message: String, t: Throwable?) {
      Intrinsics.checkParameterIsNotNull(var1, "loggerName");
      Intrinsics.checkParameterIsNotNull(var3, "message");
      val var9: java.lang.String = this.loggerTag(var1);
      if (Log.isLoggable(var9, var2)) {
         var1 = var3;
         if (var4 != null) {
            val var11: StringBuilder = new StringBuilder();
            var11.append(var3);
            var11.append("\n");
            var11.append(Log.getStackTraceString(var4));
            var1 = var11.toString();
         }

         val var7: Int = var1.length();
         var var5: Int = 0;

         while (var5 < var7) {
            var var6: Int = StringsKt.indexOf$default(var1, '\n', var5, false, 4, null);
            if (var6 == -1) {
               var6 = var7;
            }

            while (true) {
               val var8: Int = Math.min(var6, var5 + 4000);
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var3 = var1.substring(var5, var8);
               Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               Log.println(var2, var9, var3);
               if (var8 >= var6) {
                  var5 = var8 + 1;
                  break;
               }

               var5 = var8;
            }
         }
      }
   }

   public fun enable() {
      for (Entry var2 : knownLoggers.entrySet()) {
         this.enableLogging(var2.getKey() as java.lang.String, var2.getValue() as java.lang.String);
      }
   }
}
