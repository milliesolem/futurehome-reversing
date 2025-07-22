package okhttp3.internal.concurrent

import java.util.Arrays
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.StringCompanionObject

@JvmSynthetic
fun `access$log`(var0: Task, var1: TaskQueue, var2: java.lang.String) {
   log(var0, var1, var2);
}

public fun formatDuration(ns: Long): String {
   var var4: java.lang.String;
   if (var0 <= -999500000) {
      val var2: StringBuilder = new StringBuilder();
      var2.append((var0 - (long)500000000) / (long)1000000000);
      var2.append(" s ");
      var4 = var2.toString();
   } else if (var0 <= -999500) {
      val var5: StringBuilder = new StringBuilder();
      var5.append((var0 - (long)500000) / (long)1000000);
      var5.append(" ms");
      var4 = var5.toString();
   } else if (var0 <= 0L) {
      val var6: StringBuilder = new StringBuilder();
      var6.append((var0 - (long)500) / (long)1000);
      var6.append(" µs");
      var4 = var6.toString();
   } else if (var0 < 999500) {
      val var7: StringBuilder = new StringBuilder();
      var7.append((var0 + (long)500) / (long)1000);
      var7.append(" µs");
      var4 = var7.toString();
   } else if (var0 < 999500000) {
      val var8: StringBuilder = new StringBuilder();
      var8.append((var0 + (long)500000) / (long)1000000);
      var8.append(" ms");
      var4 = var8.toString();
   } else {
      val var9: StringBuilder = new StringBuilder();
      var9.append((var0 + (long)500000000) / (long)1000000000);
      var9.append(" s ");
      var4 = var9.toString();
   }

   val var3: StringCompanionObject = StringCompanionObject.INSTANCE;
   var4 = java.lang.String.format("%6s", Arrays.copyOf(new Object[]{var4}, 1));
   Intrinsics.checkExpressionValueIsNotNull(var4, "java.lang.String.format(format, *args)");
   return var4;
}

private fun log(task: Task, queue: TaskQueue, message: String) {
   val var4: Logger = TaskRunner.Companion.getLogger();
   val var3: StringBuilder = new StringBuilder();
   var3.append(var1.getName$okhttp());
   var3.append(' ');
   val var5: StringCompanionObject = StringCompanionObject.INSTANCE;
   val var6: java.lang.String = java.lang.String.format("%-22s", Arrays.copyOf(new Object[]{var2}, 1));
   Intrinsics.checkExpressionValueIsNotNull(var6, "java.lang.String.format(format, *args)");
   var3.append(var6);
   var3.append(": ");
   var3.append(var0.getName());
   var4.fine(var3.toString());
}

internal inline fun <T> logElapsed(task: Task, queue: TaskQueue, block: () -> T): T {
   label26: {
      Intrinsics.checkParameterIsNotNull(var0, "task");
      Intrinsics.checkParameterIsNotNull(var1, "queue");
      Intrinsics.checkParameterIsNotNull(var2, "block");
      val var7: Boolean = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
      val var3: Long;
      if (var7) {
         var3 = var1.getTaskRunner$okhttp().getBackend().nanoTime();
         access$log(var0, var1, "starting");
      } else {
         var3 = -1L;
      }

      try {
         ;
      } catch (var9: java.lang.Throwable) {
         if (var7) {
            val var5: Long = var1.getTaskRunner$okhttp().getBackend().nanoTime();
            val var8: StringBuilder = new StringBuilder("failed a run in ");
            var8.append(formatDuration(var5 - var3));
            access$log(var0, var1, var8.toString());
         }
      }

      if (var7) {
         val var13: Long = var1.getTaskRunner$okhttp().getBackend().nanoTime();
         val var12: StringBuilder = new StringBuilder("finished run in ");
         var12.append(formatDuration(var13 - var3));
         access$log(var0, var1, var12.toString());
      }
   }
}

internal inline fun taskLog(task: Task, queue: TaskQueue, messageBlock: () -> String) {
   Intrinsics.checkParameterIsNotNull(var0, "task");
   Intrinsics.checkParameterIsNotNull(var1, "queue");
   Intrinsics.checkParameterIsNotNull(var2, "messageBlock");
   if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
      access$log(var0, var1, var2.invoke() as java.lang.String);
   }
}
