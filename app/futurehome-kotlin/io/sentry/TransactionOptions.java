package io.sentry;

public final class TransactionOptions extends SpanOptions {
   public static final long DEFAULT_DEADLINE_TIMEOUT_AUTO_TRANSACTION = 30000L;
   private boolean bindToScope;
   private CustomSamplingContext customSamplingContext = null;
   private Long deadlineTimeout;
   private Long idleTimeout;
   private boolean isAppStartTransaction;
   private SentryDate startTimestamp;
   private TransactionFinishedCallback transactionFinishedCallback;
   private boolean waitForChildren;

   public TransactionOptions() {
      this.bindToScope = false;
      this.startTimestamp = null;
      this.isAppStartTransaction = false;
      this.waitForChildren = false;
      this.idleTimeout = null;
      this.deadlineTimeout = null;
      this.transactionFinishedCallback = null;
   }

   public CustomSamplingContext getCustomSamplingContext() {
      return this.customSamplingContext;
   }

   public Long getDeadlineTimeout() {
      return this.deadlineTimeout;
   }

   public Long getIdleTimeout() {
      return this.idleTimeout;
   }

   public SentryDate getStartTimestamp() {
      return this.startTimestamp;
   }

   public TransactionFinishedCallback getTransactionFinishedCallback() {
      return this.transactionFinishedCallback;
   }

   public boolean isAppStartTransaction() {
      return this.isAppStartTransaction;
   }

   public boolean isBindToScope() {
      return this.bindToScope;
   }

   public boolean isWaitForChildren() {
      return this.waitForChildren;
   }

   public void setAppStartTransaction(boolean var1) {
      this.isAppStartTransaction = var1;
   }

   public void setBindToScope(boolean var1) {
      this.bindToScope = var1;
   }

   public void setCustomSamplingContext(CustomSamplingContext var1) {
      this.customSamplingContext = var1;
   }

   public void setDeadlineTimeout(Long var1) {
      this.deadlineTimeout = var1;
   }

   public void setIdleTimeout(Long var1) {
      this.idleTimeout = var1;
   }

   public void setStartTimestamp(SentryDate var1) {
      this.startTimestamp = var1;
   }

   public void setTransactionFinishedCallback(TransactionFinishedCallback var1) {
      this.transactionFinishedCallback = var1;
   }

   public void setWaitForChildren(boolean var1) {
      this.waitForChildren = var1;
   }
}
