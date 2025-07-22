package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.util.Objects;

public final class TransactionContext extends SpanContext {
   private static final String DEFAULT_NAME = "<unlabeled transaction>";
   private static final TransactionNameSource DEFAULT_NAME_SOURCE = TransactionNameSource.CUSTOM;
   private static final String DEFAULT_OPERATION = "default";
   private Baggage baggage;
   private Instrumenter instrumenter = Instrumenter.SENTRY;
   private boolean isForNextAppStart = false;
   private String name;
   private TracesSamplingDecision parentSamplingDecision;
   private TransactionNameSource transactionNameSource;

   public TransactionContext(SentryId var1, SpanId var2, SpanId var3, TracesSamplingDecision var4, Baggage var5) {
      super(var1, var2, "default", var3, null);
      this.name = "<unlabeled transaction>";
      this.parentSamplingDecision = var4;
      this.transactionNameSource = DEFAULT_NAME_SOURCE;
      this.baggage = var5;
   }

   public TransactionContext(String var1, TransactionNameSource var2, String var3) {
      this(var1, var2, var3, null);
   }

   public TransactionContext(String var1, TransactionNameSource var2, String var3, TracesSamplingDecision var4) {
      super(var3);
      this.name = Objects.requireNonNull(var1, "name is required");
      this.transactionNameSource = var2;
      this.setSamplingDecision(var4);
   }

   public TransactionContext(String var1, String var2) {
      this(var1, var2, null);
   }

   public TransactionContext(String var1, String var2, TracesSamplingDecision var3) {
      this(var1, TransactionNameSource.CUSTOM, var2, var3);
   }

   public static TransactionContext fromPropagationContext(PropagationContext var0) {
      Boolean var4 = var0.isSampled();
      TracesSamplingDecision var2;
      if (var4 == null) {
         var2 = null;
      } else {
         var2 = new TracesSamplingDecision(var4);
      }

      Baggage var3 = var0.getBaggage();
      if (var3 != null) {
         var3.freeze();
         Double var5 = var3.getSampleRateDouble();
         boolean var1;
         if (var4 != null) {
            var1 = var4;
         } else {
            var1 = false;
         }

         var4 = var1;
         if (var5 != null) {
            var2 = new TracesSamplingDecision(var4, var5);
         } else {
            var2 = new TracesSamplingDecision(var4);
         }
      }

      return new TransactionContext(var0.getTraceId(), var0.getSpanId(), var0.getParentSpanId(), var2, var3);
   }

   @Deprecated
   public static TransactionContext fromSentryTrace(String var0, String var1, SentryTraceHeader var2) {
      Boolean var3 = var2.isSampled();
      TracesSamplingDecision var5;
      if (var3 == null) {
         var5 = null;
      } else {
         var5 = new TracesSamplingDecision(var3);
      }

      TransactionContext var4 = new TransactionContext(var2.getTraceId(), new SpanId(), var2.getSpanId(), var5, null);
      var4.setName(var0);
      var4.setTransactionNameSource(TransactionNameSource.CUSTOM);
      var4.setOperation(var1);
      return var4;
   }

   public Baggage getBaggage() {
      return this.baggage;
   }

   public Instrumenter getInstrumenter() {
      return this.instrumenter;
   }

   public String getName() {
      return this.name;
   }

   public Boolean getParentSampled() {
      TracesSamplingDecision var1 = this.parentSamplingDecision;
      return var1 == null ? null : var1.getSampled();
   }

   public TracesSamplingDecision getParentSamplingDecision() {
      return this.parentSamplingDecision;
   }

   public TransactionNameSource getTransactionNameSource() {
      return this.transactionNameSource;
   }

   public boolean isForNextAppStart() {
      return this.isForNextAppStart;
   }

   public void setForNextAppStart(boolean var1) {
      this.isForNextAppStart = var1;
   }

   public void setInstrumenter(Instrumenter var1) {
      this.instrumenter = var1;
   }

   public void setName(String var1) {
      this.name = Objects.requireNonNull(var1, "name is required");
   }

   public void setParentSampled(Boolean var1) {
      if (var1 == null) {
         this.parentSamplingDecision = null;
      } else {
         this.parentSamplingDecision = new TracesSamplingDecision(var1);
      }
   }

   public void setParentSampled(Boolean var1, Boolean var2) {
      if (var1 == null) {
         this.parentSamplingDecision = null;
      } else if (var2 == null) {
         this.parentSamplingDecision = new TracesSamplingDecision(var1);
      } else {
         this.parentSamplingDecision = new TracesSamplingDecision(var1, null, var2, null);
      }
   }

   public void setTransactionNameSource(TransactionNameSource var1) {
      this.transactionNameSource = var1;
   }
}
