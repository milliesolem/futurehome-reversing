package io.sentry;

import io.sentry.protocol.Message;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;

// $VF: synthetic class
public final class ISentryClient$_CC {
   public static SentryId $default$captureEnvelope(ISentryClient var0, SentryEnvelope var1) {
      return var0.captureEnvelope(var1, null);
   }

   public static SentryId $default$captureEvent(ISentryClient var0, SentryEvent var1) {
      return var0.captureEvent(var1, null, null);
   }

   public static SentryId $default$captureEvent(ISentryClient var0, SentryEvent var1, Hint var2) {
      return var0.captureEvent(var1, null, var2);
   }

   public static SentryId $default$captureEvent(ISentryClient var0, SentryEvent var1, IScope var2) {
      return var0.captureEvent(var1, var2, null);
   }

   public static SentryId $default$captureException(ISentryClient var0, Throwable var1) {
      return var0.captureException(var1, null, null);
   }

   public static SentryId $default$captureException(ISentryClient var0, Throwable var1, Hint var2) {
      return var0.captureException(var1, null, var2);
   }

   public static SentryId $default$captureException(ISentryClient var0, Throwable var1, IScope var2) {
      return var0.captureException(var1, var2, null);
   }

   public static SentryId $default$captureException(ISentryClient var0, Throwable var1, IScope var2, Hint var3) {
      return var0.captureEvent(new SentryEvent(var1), var2, var3);
   }

   public static SentryId $default$captureMessage(ISentryClient var0, String var1, SentryLevel var2) {
      return var0.captureMessage(var1, var2, null);
   }

   public static SentryId $default$captureMessage(ISentryClient var0, String var1, SentryLevel var2, IScope var3) {
      SentryEvent var4 = new SentryEvent();
      Message var5 = new Message();
      var5.setFormatted(var1);
      var4.setMessage(var5);
      var4.setLevel(var2);
      return var0.captureEvent(var4, var3);
   }

   public static void $default$captureSession(ISentryClient var0, Session var1) {
      var0.captureSession(var1, null);
   }

   public static SentryId $default$captureTransaction(ISentryClient var0, SentryTransaction var1) {
      return var0.captureTransaction(var1, null, null, null);
   }

   public static SentryId $default$captureTransaction(ISentryClient var0, SentryTransaction var1, IScope var2, Hint var3) {
      return var0.captureTransaction(var1, null, var2, var3);
   }

   public static SentryId $default$captureTransaction(ISentryClient var0, SentryTransaction var1, TraceContext var2) {
      return var0.captureTransaction(var1, var2, null, null);
   }

   public static SentryId $default$captureTransaction(ISentryClient var0, SentryTransaction var1, TraceContext var2, IScope var3, Hint var4) {
      return var0.captureTransaction(var1, var2, var3, var4, null);
   }

   public static boolean $default$isHealthy(ISentryClient var0) {
      return true;
   }
}
