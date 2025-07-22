package io.sentry;

import io.sentry.protocol.SentryStackTrace;
import io.sentry.protocol.SentryThread;
import io.sentry.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class SentryThreadFactory {
   private final SentryOptions options;
   private final SentryStackTraceFactory sentryStackTraceFactory;

   public SentryThreadFactory(SentryStackTraceFactory var1, SentryOptions var2) {
      this.sentryStackTraceFactory = Objects.requireNonNull(var1, "The SentryStackTraceFactory is required.");
      this.options = Objects.requireNonNull(var2, "The SentryOptions is required");
   }

   private SentryThread getSentryThread(boolean var1, StackTraceElement[] var2, Thread var3) {
      SentryThread var4 = new SentryThread();
      var4.setName(var3.getName());
      var4.setPriority(var3.getPriority());
      var4.setId(var3.getId());
      var4.setDaemon(var3.isDaemon());
      var4.setState(var3.getState().name());
      var4.setCrashed(var1);
      List var5 = this.sentryStackTraceFactory.getStackFrames(var2, false);
      if (this.options.isAttachStacktrace() && var5 != null && !var5.isEmpty()) {
         SentryStackTrace var6 = new SentryStackTrace(var5);
         var6.setSnapshot(true);
         var4.setStacktrace(var6);
      }

      return var4;
   }

   List<SentryThread> getCurrentThread() {
      HashMap var2 = new HashMap();
      Thread var1 = Thread.currentThread();
      var2.put(var1, var1.getStackTrace());
      return this.getCurrentThreads(var2, null, false);
   }

   List<SentryThread> getCurrentThreads(List<Long> var1) {
      return this.getCurrentThreads(Thread.getAllStackTraces(), var1, false);
   }

   List<SentryThread> getCurrentThreads(List<Long> var1, boolean var2) {
      return this.getCurrentThreads(Thread.getAllStackTraces(), var1, var2);
   }

   List<SentryThread> getCurrentThreads(Map<Thread, StackTraceElement[]> var1, List<Long> var2, boolean var3) {
      Thread var6 = Thread.currentThread();
      ArrayList var9;
      if (!var1.isEmpty()) {
         ArrayList var5 = new ArrayList();
         if (!var1.containsKey(var6)) {
            var1.put(var6, var6.getStackTrace());
         }

         Iterator var7 = var1.entrySet().iterator();

         while (true) {
            var9 = var5;
            if (!var7.hasNext()) {
               break;
            }

            Entry var8 = (Entry)var7.next();
            Thread var10 = (Thread)var8.getKey();
            boolean var4;
            if ((var10 != var6 || var3) && (var2 == null || !var2.contains(var10.getId()))) {
               var4 = false;
            } else {
               var4 = true;
            }

            var5.add(this.getSentryThread(var4, (StackTraceElement[])var8.getValue(), (Thread)var8.getKey()));
         }
      } else {
         var9 = null;
      }

      return var9;
   }
}
