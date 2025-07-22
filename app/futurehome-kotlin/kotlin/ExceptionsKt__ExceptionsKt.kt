package kotlin

import java.io.PrintStream
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.internal.PlatformImplementationsKt

internal class ExceptionsKt__ExceptionsKt {
   public final val stackTrace: Array<StackTraceElement>
      public final get() {
         val var1: Array<StackTraceElement> = var0.getStackTrace();
         return var1;
      }


   public final val suppressedExceptions: List<Throwable>
      public final get() {
         return PlatformImplementationsKt.IMPLEMENTATIONS.getSuppressed(var0);
      }


   open fun ExceptionsKt__ExceptionsKt() {
   }

   @JvmStatic
   public fun Throwable.addSuppressed(exception: Throwable) {
      if (var0 != var1) {
         PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(var0, var1);
      }
   }

   @JvmStatic
   public inline fun Throwable.printStackTrace() {
      var0.printStackTrace();
   }

   @JvmStatic
   public inline fun Throwable.printStackTrace(stream: PrintStream) {
      var0.printStackTrace(var1);
   }

   @JvmStatic
   public inline fun Throwable.printStackTrace(writer: PrintWriter) {
      var0.printStackTrace(var1);
   }

   @JvmStatic
   public fun Throwable.stackTraceToString(): String {
      val var1: StringWriter = new StringWriter();
      val var2: PrintWriter = new PrintWriter(var1);
      var0.printStackTrace(var2);
      var2.flush();
      val var3: java.lang.String = var1.toString();
      return var3;
   }
}
