package io.sentry;

import io.sentry.exception.ExceptionMechanismException;
import io.sentry.protocol.Mechanism;
import io.sentry.protocol.SentryException;
import io.sentry.protocol.SentryStackFrame;
import io.sentry.protocol.SentryStackTrace;
import io.sentry.protocol.SentryThread;
import io.sentry.util.Objects;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

public final class SentryExceptionFactory {
   private final SentryStackTraceFactory sentryStackTraceFactory;

   public SentryExceptionFactory(SentryStackTraceFactory var1) {
      this.sentryStackTraceFactory = Objects.requireNonNull(var1, "The SentryStackTraceFactory is required.");
   }

   private SentryException getSentryException(Throwable var1, Mechanism var2, Long var3, List<SentryStackFrame> var4, boolean var5) {
      Package var9 = var1.getClass().getPackage();
      String var6 = var1.getClass().getName();
      SentryException var7 = new SentryException();
      String var8 = var1.getMessage();
      String var10 = var6;
      if (var9 != null) {
         StringBuilder var11 = new StringBuilder();
         var11.append(var9.getName());
         var11.append(".");
         var10 = var6.replace(var11.toString(), "");
      }

      if (var9 != null) {
         var6 = var9.getName();
      } else {
         var6 = null;
      }

      if (var4 != null && !var4.isEmpty()) {
         SentryStackTrace var12 = new SentryStackTrace(var4);
         if (var5) {
            var12.setSnapshot(true);
         }

         var7.setStacktrace(var12);
      }

      var7.setThreadId(var3);
      var7.setType(var10);
      var7.setMechanism(var2);
      var7.setModule(var6);
      var7.setValue(var8);
      return var7;
   }

   private List<SentryException> getSentryExceptions(Deque<SentryException> var1) {
      return new ArrayList<>(var1);
   }

   Deque<SentryException> extractExceptionQueue(Throwable var1) {
      ArrayDeque var8 = new ArrayDeque();
      HashSet var7 = new HashSet();

      while (var1 != null && var7.add(var1)) {
         boolean var2 = var1 instanceof ExceptionMechanismException;
         boolean var4 = false;
         Mechanism var5;
         Thread var6;
         if (var2) {
            ExceptionMechanismException var9 = (ExceptionMechanismException)var1;
            var5 = var9.getExceptionMechanism();
            var1 = var9.getThrowable();
            var6 = var9.getThread();
            var2 = var9.isSnapshot();
         } else {
            var6 = Thread.currentThread();
            var5 = null;
            var2 = false;
         }

         boolean var3 = var4;
         if (var5 != null) {
            var3 = var4;
            if (Boolean.FALSE.equals(var5.isHandled())) {
               var3 = true;
            }
         }

         List var11 = this.sentryStackTraceFactory.getStackFrames(var1.getStackTrace(), var3);
         var8.addFirst(this.getSentryException(var1, var5, var6.getId(), var11, var2));
         var1 = var1.getCause();
      }

      return var8;
   }

   public List<SentryException> getSentryExceptions(Throwable var1) {
      return this.getSentryExceptions(this.extractExceptionQueue(var1));
   }

   public List<SentryException> getSentryExceptionsFromThread(SentryThread var1, Mechanism var2, Throwable var3) {
      SentryStackTrace var5 = var1.getStacktrace();
      if (var5 == null) {
         return new ArrayList<>(0);
      } else {
         ArrayList var4 = new ArrayList(1);
         var4.add(this.getSentryException(var3, var2, var1.getId(), var5.getFrames(), true));
         return var4;
      }
   }
}
