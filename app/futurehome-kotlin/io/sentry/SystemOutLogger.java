package io.sentry;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class SystemOutLogger implements ILogger {
   private String captureStackTrace(Throwable var1) {
      StringWriter var2 = new StringWriter();
      var1.printStackTrace(new PrintWriter(var2));
      return var2.toString();
   }

   @Override
   public boolean isEnabled(SentryLevel var1) {
      return true;
   }

   @Override
   public void log(SentryLevel var1, String var2, Throwable var3) {
      if (var3 == null) {
         this.log(var1, var2);
      } else {
         System.out.println(String.format("%s: %s\n%s", var1, String.format(var2, var3.toString()), this.captureStackTrace(var3)));
      }
   }

   @Override
   public void log(SentryLevel var1, String var2, Object... var3) {
      System.out.println(String.format("%s: %s", var1, String.format(var2, var3)));
   }

   @Override
   public void log(SentryLevel var1, Throwable var2, String var3, Object... var4) {
      if (var2 == null) {
         this.log(var1, var3, var4);
      } else {
         System.out.println(String.format("%s: %s \n %s\n%s", var1, String.format(var3, var4), var2.toString(), this.captureStackTrace(var2)));
      }
   }
}
