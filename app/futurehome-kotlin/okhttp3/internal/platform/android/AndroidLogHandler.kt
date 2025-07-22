package okhttp3.internal.platform.android

import java.util.logging.Handler
import java.util.logging.LogRecord
import kotlin.jvm.internal.Intrinsics

public object AndroidLogHandler : Handler {
   public override fun close() {
   }

   public override fun flush() {
   }

   public override fun publish(record: LogRecord) {
      Intrinsics.checkParameterIsNotNull(var1, "record");
      val var5: AndroidLog = AndroidLog.INSTANCE;
      val var4: java.lang.String = var1.getLoggerName();
      Intrinsics.checkExpressionValueIsNotNull(var4, "record.loggerName");
      val var2: Int = AndroidLogKt.access$getAndroidLevel$p(var1);
      val var3: java.lang.String = var1.getMessage();
      Intrinsics.checkExpressionValueIsNotNull(var3, "record.message");
      var5.androidLog$okhttp(var4, var2, var3, var1.getThrown());
   }
}
