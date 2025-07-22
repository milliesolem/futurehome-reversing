package io.sentry;

import io.sentry.protocol.SentryStackFrame;
import io.sentry.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class SentryStackTraceFactory {
   private static final int STACKTRACE_FRAME_LIMIT = 100;
   private final SentryOptions options;

   public SentryStackTraceFactory(SentryOptions var1) {
      this.options = var1;
   }

   public List<SentryStackFrame> getInAppCallStack() {
      return this.getInAppCallStack(new Exception());
   }

   List<SentryStackFrame> getInAppCallStack(Throwable var1) {
      List var2 = this.getStackFrames(var1.getStackTrace(), false);
      if (var2 == null) {
         return Collections.emptyList();
      } else {
         List var3 = CollectionUtils.filterListEntries(var2, new SentryStackTraceFactory$$ExternalSyntheticLambda0());
         return !var3.isEmpty() ? var3 : CollectionUtils.filterListEntries(var2, new SentryStackTraceFactory$$ExternalSyntheticLambda1());
      }
   }

   public List<SentryStackFrame> getStackFrames(StackTraceElement[] var1, boolean var2) {
      ArrayList var9;
      if (var1 != null && var1.length > 0) {
         ArrayList var5 = new ArrayList();

         for (StackTraceElement var7 : var1) {
            if (var7 != null) {
               String var6 = var7.getClassName();
               if (var2 || !var6.startsWith("io.sentry.") || var6.startsWith("io.sentry.samples.") || var6.startsWith("io.sentry.mobile.")) {
                  SentryStackFrame var8 = new SentryStackFrame();
                  var8.setInApp(this.isInApp(var6));
                  var8.setModule(var6);
                  var8.setFunction(var7.getMethodName());
                  var8.setFilename(var7.getFileName());
                  if (var7.getLineNumber() >= 0) {
                     var8.setLineno(var7.getLineNumber());
                  }

                  var8.setNative(var7.isNativeMethod());
                  var5.add(var8);
                  if (var5.size() >= 100) {
                     break;
                  }
               }
            }
         }

         Collections.reverse(var5);
         var9 = var5;
      } else {
         var9 = null;
      }

      return var9;
   }

   public Boolean isInApp(String var1) {
      Boolean var2 = true;
      if (var1 != null && !var1.isEmpty()) {
         Iterator var3 = this.options.getInAppIncludes().iterator();

         while (var3.hasNext()) {
            if (var1.startsWith((String)var3.next())) {
               return var2;
            }
         }

         Iterator var4 = this.options.getInAppExcludes().iterator();

         while (var4.hasNext()) {
            if (var1.startsWith((String)var4.next())) {
               return false;
            }
         }

         return null;
      } else {
         return var2;
      }
   }
}
