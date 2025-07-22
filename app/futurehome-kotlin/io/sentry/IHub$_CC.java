package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;

// $VF: synthetic class
public final class IHub$_CC {
   public static void $default$addBreadcrumb(IHub var0, String var1) {
      var0.addBreadcrumb(new Breadcrumb(var1));
   }

   public static void $default$addBreadcrumb(IHub var0, String var1, String var2) {
      Breadcrumb var3 = new Breadcrumb(var1);
      var3.setCategory(var2);
      var0.addBreadcrumb(var3);
   }

   public static SentryId $default$captureEnvelope(IHub var0, SentryEnvelope var1) {
      return var0.captureEnvelope(var1, new Hint());
   }

   public static SentryId $default$captureEvent(IHub var0, SentryEvent var1) {
      return var0.captureEvent(var1, new Hint());
   }

   public static SentryId $default$captureEvent(IHub var0, SentryEvent var1, ScopeCallback var2) {
      return var0.captureEvent(var1, new Hint(), var2);
   }

   public static SentryId $default$captureException(IHub var0, Throwable var1) {
      return var0.captureException(var1, new Hint());
   }

   public static SentryId $default$captureException(IHub var0, Throwable var1, ScopeCallback var2) {
      return var0.captureException(var1, new Hint(), var2);
   }

   public static SentryId $default$captureMessage(IHub var0, String var1) {
      return var0.captureMessage(var1, SentryLevel.INFO);
   }

   public static SentryId $default$captureMessage(IHub var0, String var1, ScopeCallback var2) {
      return var0.captureMessage(var1, SentryLevel.INFO, var2);
   }

   public static SentryId $default$captureTransaction(IHub var0, SentryTransaction var1, Hint var2) {
      return var0.captureTransaction(var1, null, var2);
   }

   public static SentryId $default$captureTransaction(IHub var0, SentryTransaction var1, TraceContext var2) {
      return var0.captureTransaction(var1, var2, null);
   }

   public static SentryId $default$captureTransaction(IHub var0, SentryTransaction var1, TraceContext var2, Hint var3) {
      return var0.captureTransaction(var1, var2, var3, null);
   }

   @Deprecated
   public static void $default$reportFullDisplayed(IHub var0) {
      var0.reportFullyDisplayed();
   }

   public static ITransaction $default$startTransaction(IHub var0, TransactionContext var1) {
      return var0.startTransaction(var1, new TransactionOptions());
   }

   public static ITransaction $default$startTransaction(IHub var0, String var1, String var2) {
      return var0.startTransaction(var1, var2, new TransactionOptions());
   }

   public static ITransaction $default$startTransaction(IHub var0, String var1, String var2, TransactionOptions var3) {
      return var0.startTransaction(new TransactionContext(var1, var2), var3);
   }
}
