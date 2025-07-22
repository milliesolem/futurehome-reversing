package io.sentry;

import io.sentry.clientreport.DiscardReason;
import io.sentry.exception.SentryEnvelopeException;
import io.sentry.hints.Backfillable;
import io.sentry.hints.DiskFlushNotification;
import io.sentry.hints.TransactionEnd;
import io.sentry.metrics.EncodedMetrics;
import io.sentry.metrics.IMetricsClient;
import io.sentry.metrics.NoopMetricsAggregator;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.transport.ITransport;
import io.sentry.transport.RateLimiter;
import io.sentry.util.CheckInUtils;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import io.sentry.util.Random;
import io.sentry.util.SentryRandom;
import io.sentry.util.TracingUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class SentryClient implements ISentryClient, IMetricsClient {
   static final String SENTRY_PROTOCOL_VERSION = "7";
   private boolean enabled;
   private final IMetricsAggregator metricsAggregator;
   private final SentryOptions options;
   private final SentryClient.SortBreadcrumbsByDate sortBreadcrumbsByDate = new SentryClient.SortBreadcrumbsByDate();
   private final ITransport transport;

   SentryClient(SentryOptions var1) {
      this.options = Objects.requireNonNull(var1, "SentryOptions is required.");
      this.enabled = true;
      ITransportFactory var3 = var1.getTransportFactory();
      Object var2 = var3;
      if (var3 instanceof NoOpTransportFactory) {
         var2 = new AsyncHttpTransportFactory();
         var1.setTransportFactory((ITransportFactory)var2);
      }

      this.transport = ((ITransportFactory)var2).create(var1, new RequestDetailsResolver(var1).resolve());
      Object var4;
      if (var1.isEnableMetrics()) {
         var4 = new MetricsAggregator(var1, this);
      } else {
         var4 = NoopMetricsAggregator.getInstance();
      }

      this.metricsAggregator = (IMetricsAggregator)var4;
   }

   private void addScopeAttachmentsToHint(IScope var1, Hint var2) {
      if (var1 != null) {
         var2.addAttachments(var1.getAttachments());
      }
   }

   private CheckIn applyScope(CheckIn var1, IScope var2) {
      if (var2 != null) {
         ISpan var3 = var2.getSpan();
         if (var1.getContexts().getTrace() == null) {
            if (var3 == null) {
               var1.getContexts().setTrace(TransactionContext.fromPropagationContext(var2.getPropagationContext()));
            } else {
               var1.getContexts().setTrace(var3.getSpanContext());
            }
         }
      }

      return var1;
   }

   private <T extends SentryBaseEvent> T applyScope(T var1, IScope var2) {
      if (var2 != null) {
         if (var1.getRequest() == null) {
            var1.setRequest(var2.getRequest());
         }

         if (var1.getUser() == null) {
            var1.setUser(var2.getUser());
         }

         if (var1.getTags() == null) {
            var1.setTags(new HashMap<>(var2.getTags()));
         } else {
            for (Entry var4 : var2.getTags().entrySet()) {
               if (!var1.getTags().containsKey(var4.getKey())) {
                  var1.getTags().put((String)var4.getKey(), (String)var4.getValue());
               }
            }
         }

         if (var1.getBreadcrumbs() == null) {
            var1.setBreadcrumbs(new ArrayList<>(var2.getBreadcrumbs()));
         } else {
            this.sortBreadcrumbsByDate(var1, var2.getBreadcrumbs());
         }

         if (var1.getExtras() == null) {
            var1.setExtras(new HashMap<>(var2.getExtras()));
         } else {
            for (Entry var8 : var2.getExtras().entrySet()) {
               if (!var1.getExtras().containsKey(var8.getKey())) {
                  var1.getExtras().put((String)var8.getKey(), var8.getValue());
               }
            }
         }

         Contexts var7 = var1.getContexts();

         for (Entry var5 : new Contexts(var2.getContexts()).entrySet()) {
            if (!var7.containsKey(var5.getKey())) {
               var7.put((String)var5.getKey(), var5.getValue());
            }
         }
      }

      return (T)var1;
   }

   private SentryEvent applyScope(SentryEvent var1, IScope var2, Hint var3) {
      SentryEvent var4 = var1;
      if (var2 != null) {
         this.applyScope(var1, var2);
         if (var1.getTransaction() == null) {
            var1.setTransaction(var2.getTransactionName());
         }

         if (var1.getFingerprints() == null) {
            var1.setFingerprints(var2.getFingerprint());
         }

         if (var2.getLevel() != null) {
            var1.setLevel(var2.getLevel());
         }

         ISpan var5 = var2.getSpan();
         if (var1.getContexts().getTrace() == null) {
            if (var5 == null) {
               var1.getContexts().setTrace(TransactionContext.fromPropagationContext(var2.getPropagationContext()));
            } else {
               var1.getContexts().setTrace(var5.getSpanContext());
            }
         }

         var4 = this.processEvent(var1, var3, var2.getEventProcessors());
      }

      return var4;
   }

   private SentryReplayEvent applyScope(SentryReplayEvent var1, IScope var2) {
      if (var2 != null) {
         if (var1.getRequest() == null) {
            var1.setRequest(var2.getRequest());
         }

         if (var1.getUser() == null) {
            var1.setUser(var2.getUser());
         }

         if (var1.getTags() == null) {
            var1.setTags(new HashMap<>(var2.getTags()));
         } else {
            for (Entry var4 : var2.getTags().entrySet()) {
               if (!var1.getTags().containsKey(var4.getKey())) {
                  var1.getTags().put((String)var4.getKey(), (String)var4.getValue());
               }
            }
         }

         Contexts var6 = var1.getContexts();

         for (Entry var5 : new Contexts(var2.getContexts()).entrySet()) {
            if (!var6.containsKey(var5.getKey())) {
               var6.put((String)var5.getKey(), var5.getValue());
            }
         }

         ISpan var7 = var2.getSpan();
         if (var1.getContexts().getTrace() == null) {
            if (var7 == null) {
               var1.getContexts().setTrace(TransactionContext.fromPropagationContext(var2.getPropagationContext()));
            } else {
               var1.getContexts().setTrace(var7.getSpanContext());
            }
         }
      }

      return var1;
   }

   private SentryEnvelope buildEnvelope(CheckIn var1, TraceContext var2) {
      ArrayList var3 = new ArrayList();
      var3.add(SentryEnvelopeItem.fromCheckIn(this.options.getSerializer(), var1));
      return new SentryEnvelope(new SentryEnvelopeHeader(var1.getCheckInId(), this.options.getSdkVersion(), var2), var3);
   }

   private SentryEnvelope buildEnvelope(SentryBaseEvent var1, List<Attachment> var2, Session var3, TraceContext var4, ProfilingTraceData var5) throws IOException, SentryEnvelopeException {
      ArrayList var6 = new ArrayList();
      SentryId var7;
      if (var1 != null) {
         var6.add(SentryEnvelopeItem.fromEvent(this.options.getSerializer(), var1));
         var7 = var1.getEventId();
      } else {
         var7 = null;
      }

      if (var3 != null) {
         var6.add(SentryEnvelopeItem.fromSession(this.options.getSerializer(), var3));
      }

      SentryId var10 = var7;
      if (var5 != null) {
         var6.add(SentryEnvelopeItem.fromProfilingTrace(var5, this.options.getMaxTraceFileSize(), this.options.getSerializer()));
         var10 = var7;
         if (var7 == null) {
            var10 = new SentryId(var5.getProfileId());
         }
      }

      if (var2 != null) {
         for (Attachment var8 : var2) {
            var6.add(SentryEnvelopeItem.fromAttachment(this.options.getSerializer(), this.options.getLogger(), var8, this.options.getMaxAttachmentSize()));
         }
      }

      return !var6.isEmpty() ? new SentryEnvelope(new SentryEnvelopeHeader(var10, this.options.getSdkVersion(), var4), var6) : null;
   }

   private SentryEnvelope buildEnvelope(SentryReplayEvent var1, ReplayRecording var2, TraceContext var3, boolean var4) {
      ArrayList var5 = new ArrayList();
      var5.add(SentryEnvelopeItem.fromReplay(this.options.getSerializer(), this.options.getLogger(), var1, var2, var4));
      return new SentryEnvelope(new SentryEnvelopeHeader(var1.getEventId(), this.options.getSessionReplay().getSdkVersion(), var3), var5);
   }

   private SentryEnvelope buildEnvelope(UserFeedback var1) {
      ArrayList var2 = new ArrayList();
      var2.add(SentryEnvelopeItem.fromUserFeedback(this.options.getSerializer(), var1));
      return new SentryEnvelope(new SentryEnvelopeHeader(var1.getEventId(), this.options.getSdkVersion()), var2);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryEvent executeBeforeSend(SentryEvent var1, Hint var2) {
      SentryOptions.BeforeSendCallback var4 = this.options.getBeforeSend();
      SentryEvent var3 = var1;
      if (var4 != null) {
         try {
            var3 = var4.execute(var1, var2);
         } catch (Throwable var6) {
            this.options.getLogger().log(SentryLevel.ERROR, "The BeforeSend callback threw an exception. It will be added as breadcrumb and continue.", var6);
            var3 = null;
            return var3;
         }
      }

      return var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryReplayEvent executeBeforeSendReplay(SentryReplayEvent var1, Hint var2) {
      SentryOptions.BeforeSendReplayCallback var4 = this.options.getBeforeSendReplay();
      SentryReplayEvent var3 = var1;
      if (var4 != null) {
         try {
            var3 = var4.execute(var1, var2);
         } catch (Throwable var6) {
            this.options
               .getLogger()
               .log(SentryLevel.ERROR, "The BeforeSendReplay callback threw an exception. It will be added as breadcrumb and continue.", var6);
            var3 = null;
            return var3;
         }
      }

      return var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryTransaction executeBeforeSendTransaction(SentryTransaction var1, Hint var2) {
      SentryOptions.BeforeSendTransactionCallback var4 = this.options.getBeforeSendTransaction();
      SentryTransaction var3 = var1;
      if (var4 != null) {
         try {
            var3 = var4.execute(var1, var2);
         } catch (Throwable var6) {
            this.options
               .getLogger()
               .log(SentryLevel.ERROR, "The BeforeSendTransaction callback threw an exception. It will be added as breadcrumb and continue.", var6);
            var3 = null;
            return var3;
         }
      }

      return var3;
   }

   private List<Attachment> filterForTransaction(List<Attachment> var1) {
      if (var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();

         for (Attachment var4 : var1) {
            if (var4.isAddToTransactions()) {
               var2.add(var4);
            }
         }

         return var2;
      }
   }

   private void finalizeTransaction(IScope var1, Hint var2) {
      ITransaction var4 = var1.getTransaction();
      if (var4 != null && HintUtils.hasType(var2, TransactionEnd.class)) {
         Object var3 = HintUtils.getSentrySdkHint(var2);
         if (var3 instanceof DiskFlushNotification) {
            ((DiskFlushNotification)var3).setFlushable(var4.getEventId());
            var4.forceFinish(SpanStatus.ABORTED, false, var2);
         } else {
            var4.forceFinish(SpanStatus.ABORTED, false, null);
         }
      }
   }

   private List<Attachment> getAttachments(Hint var1) {
      List var2 = var1.getAttachments();
      Attachment var3 = var1.getScreenshot();
      if (var3 != null) {
         var2.add(var3);
      }

      var3 = var1.getViewHierarchy();
      if (var3 != null) {
         var2.add(var3);
      }

      Attachment var4 = var1.getThreadDump();
      if (var4 != null) {
         var2.add(var4);
      }

      return var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryEvent processEvent(SentryEvent var1, Hint var2, List<EventProcessor> var3) {
      Iterator var6 = var3.iterator();
      SentryEvent var21 = var1;

      while (true) {
         var1 = var21;
         if (!var6.hasNext()) {
            break;
         }

         EventProcessor var7 = (EventProcessor)var6.next();

         label138: {
            boolean var4;
            boolean var5;
            try {
               var4 = var7 instanceof BackfillingEventProcessor;
               var5 = HintUtils.hasType(var2, Backfillable.class);
            } catch (Throwable var19) {
               this.options
                  .getLogger()
                  .log(SentryLevel.ERROR, var19, "An exception occurred while processing event by processor: %s", var7.getClass().getName());
               var1 = var21;
               break label138;
            }

            if (var5 && var4) {
               label123:
               try {
                  var1 = var7.process(var21, var2);
               } catch (Throwable var18) {
                  this.options
                     .getLogger()
                     .log(SentryLevel.ERROR, var18, "An exception occurred while processing event by processor: %s", var7.getClass().getName());
                  var1 = var21;
                  break label123;
               }
            } else {
               var1 = var21;
               if (!var5) {
                  var1 = var21;
                  if (!var4) {
                     label119:
                     try {
                        var1 = var7.process(var21, var2);
                     } catch (Throwable var17) {
                        this.options
                           .getLogger()
                           .log(SentryLevel.ERROR, var17, "An exception occurred while processing event by processor: %s", var7.getClass().getName());
                        var1 = var21;
                        break label119;
                     }
                  }
               }
            }
         }

         var21 = var1;
         if (var1 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by a processor: %s", var7.getClass().getName());
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Error);
            break;
         }
      }

      return var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryReplayEvent processReplayEvent(SentryReplayEvent var1, Hint var2, List<EventProcessor> var3) {
      Iterator var4 = var3.iterator();

      while (true) {
         var8 = var1;
         if (!var4.hasNext()) {
            break;
         }

         EventProcessor var5 = (EventProcessor)var4.next();

         label38:
         try {
            var8 = var5.process(var1, var2);
         } catch (Throwable var7) {
            this.options
               .getLogger()
               .log(SentryLevel.ERROR, var7, "An exception occurred while processing replay event by processor: %s", var5.getClass().getName());
            var8 = var1;
            break label38;
         }

         var1 = var8;
         if (var8 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Replay event was dropped by a processor: %s", var5.getClass().getName());
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Replay);
            break;
         }
      }

      return var8;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryTransaction processTransaction(SentryTransaction var1, Hint var2, List<EventProcessor> var3) {
      Iterator var6 = var3.iterator();

      while (true) {
         var10 = var1;
         if (!var6.hasNext()) {
            break;
         }

         EventProcessor var7 = (EventProcessor)var6.next();
         int var5 = var1.getSpans().size();

         label49:
         try {
            var10 = var7.process(var1, var2);
         } catch (Throwable var9) {
            this.options
               .getLogger()
               .log(SentryLevel.ERROR, var9, "An exception occurred while processing transaction by processor: %s", var7.getClass().getName());
            var10 = var1;
            break label49;
         }

         int var4;
         if (var10 == null) {
            var4 = 0;
         } else {
            var4 = var10.getSpans().size();
         }

         if (var10 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Transaction was dropped by a processor: %s", var7.getClass().getName());
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Transaction);
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Span, var5 + 1);
            break;
         }

         var1 = var10;
         if (var4 < var5) {
            var4 = var5 - var4;
            this.options.getLogger().log(SentryLevel.DEBUG, "%d spans were dropped by a processor: %s", var4, var7.getClass().getName());
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Span, var4);
            var1 = var10;
         }
      }

      return var10;
   }

   private boolean sample() {
      Random var3;
      if (this.options.getSampleRate() == null) {
         var3 = null;
      } else {
         var3 = SentryRandom.current();
      }

      Double var4 = this.options.getSampleRate();
      boolean var2 = true;
      boolean var1 = var2;
      if (var4 != null) {
         var1 = var2;
         if (var3 != null) {
            if (!(this.options.getSampleRate() < var3.nextDouble())) {
               var1 = var2;
            } else {
               var1 = false;
            }
         }
      }

      return var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryId sendEnvelope(SentryEnvelope var1, Hint var2) throws IOException {
      SentryOptions.BeforeEnvelopeCallback var3 = this.options.getBeforeEnvelopeCallback();
      if (var3 != null) {
         label34:
         try {
            var3.execute(var1, var2);
         } catch (Throwable var5) {
            this.options.getLogger().log(SentryLevel.ERROR, "The BeforeEnvelope callback threw an exception.", var5);
            break label34;
         }
      }

      if (var2 == null) {
         this.transport.send(var1);
      } else {
         this.transport.send(var1, var2);
      }

      SentryId var6 = var1.getHeader().getEventId();
      if (var6 == null) {
         var6 = SentryId.EMPTY_ID;
      }

      return var6;
   }

   private boolean shouldApplyScopeData(CheckIn var1, Hint var2) {
      if (HintUtils.shouldApplyScopeData(var2)) {
         return true;
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Check-in was cached so not applying scope: %s", var1.getCheckInId());
         return false;
      }
   }

   private boolean shouldApplyScopeData(SentryBaseEvent var1, Hint var2) {
      if (HintUtils.shouldApplyScopeData(var2)) {
         return true;
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Event was cached so not applying scope: %s", var1.getEventId());
         return false;
      }
   }

   private boolean shouldSendSessionUpdateForDroppedEvent(Session var1, Session var2) {
      if (var2 == null) {
         return false;
      } else if (var1 == null) {
         return true;
      } else {
         return var2.getStatus() == Session.State.Crashed && var1.getStatus() != Session.State.Crashed ? true : var2.errorCount() > 0 && var1.errorCount() <= 0;
      }
   }

   private void sortBreadcrumbsByDate(SentryBaseEvent var1, Collection<Breadcrumb> var2) {
      List var3 = var1.getBreadcrumbs();
      if (var3 != null && !var2.isEmpty()) {
         var3.addAll(var2);
         Collections.sort(var3, this.sortBreadcrumbsByDate);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public SentryId captureCheckIn(CheckIn var1, IScope var2, Hint var3) {
      Hint var4 = var3;
      if (var3 == null) {
         var4 = new Hint();
      }

      if (var1.getEnvironment() == null) {
         var1.setEnvironment(this.options.getEnvironment());
      }

      if (var1.getRelease() == null) {
         var1.setRelease(this.options.getRelease());
      }

      CheckIn var14 = var1;
      if (this.shouldApplyScopeData(var1, var4)) {
         var14 = this.applyScope(var1, var2);
      }

      if (CheckInUtils.isIgnored(this.options.getIgnoredCheckIns(), var14.getMonitorSlug())) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Check-in was dropped as slug %s is ignored", var14.getMonitorSlug());
         return SentryId.EMPTY_ID;
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Capturing check-in: %s", var14.getCheckInId());
         SentryId var5 = var14.getCheckInId();
         TraceContext var12;
         if (var2 != null) {
            try {
               var11 = var2.getTransaction();
            } catch (IOException var9) {
               this.options.getLogger().log(SentryLevel.WARNING, var9, "Capturing check-in %s failed.", var5);
               return SentryId.EMPTY_ID;
            }

            if (var11 != null) {
               try {
                  var12 = var11.traceContext();
               } catch (IOException var8) {
                  this.options.getLogger().log(SentryLevel.WARNING, var8, "Capturing check-in %s failed.", var5);
                  return SentryId.EMPTY_ID;
               }
            } else {
               try {
                  var12 = TracingUtils.maybeUpdateBaggage(var2, this.options).traceContext();
               } catch (IOException var7) {
                  this.options.getLogger().log(SentryLevel.WARNING, var7, "Capturing check-in %s failed.", var5);
                  return SentryId.EMPTY_ID;
               }
            }
         } else {
            var12 = null;
         }

         try {
            SentryEnvelope var13 = this.buildEnvelope(var14, var12);
            var4.clear();
            var10 = this.sendEnvelope(var13, var4);
         } catch (IOException var6) {
            this.options.getLogger().log(SentryLevel.WARNING, var6, "Capturing check-in %s failed.", var5);
            var10 = SentryId.EMPTY_ID;
         }

         return var10;
      }
   }

   @Override
   public SentryId captureEnvelope(SentryEnvelope var1, Hint var2) {
      Objects.requireNonNull(var1, "SentryEnvelope is required.");
      Hint var3 = var2;
      if (var2 == null) {
         var3 = new Hint();
      }

      try {
         var3.clear();
         return this.sendEnvelope(var1, var3);
      } catch (IOException var4) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to capture envelope.", var4);
         return SentryId.EMPTY_ID;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public SentryId captureEvent(SentryEvent var1, IScope var2, Hint var3) {
      SentryEvent var5 = var1;
      Objects.requireNonNull(var1, "SentryEvent is required.");
      Hint var6;
      if (var3 == null) {
         var6 = new Hint();
      } else {
         var6 = var3;
      }

      if (this.shouldApplyScopeData(var1, var6)) {
         this.addScopeAttachmentsToHint(var2, var6);
      }

      this.options.getLogger().log(SentryLevel.DEBUG, "Capturing event: %s", var1.getEventId());
      if (var1 != null) {
         Throwable var31 = var1.getThrowable();
         if (var31 != null && this.options.containsIgnoredExceptionForType(var31)) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped as the exception %s is ignored", var31.getClass());
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Error);
            return SentryId.EMPTY_ID;
         }
      }

      if (this.shouldApplyScopeData(var1, var6)) {
         var1 = this.applyScope(var1, var2, var6);
         var5 = var1;
         if (var1 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by applyScope");
            return SentryId.EMPTY_ID;
         }
      }

      SentryEvent var32 = this.processEvent(var5, var6, this.options.getEventProcessors());
      var1 = var32;
      if (var32 != null) {
         SentryEvent var33 = this.executeBeforeSend(var32, var6);
         var1 = var33;
         if (var33 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by beforeSend");
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Error);
            var1 = var33;
         }
      }

      if (var1 == null) {
         return SentryId.EMPTY_ID;
      } else {
         Object var9 = null;
         Session var8;
         if (var2 != null) {
            var8 = var2.withSession(new SentryClient$$ExternalSyntheticLambda0());
         } else {
            var8 = null;
         }

         Session var7;
         if (var1 != null) {
            Session var34;
            if (var8 != null && var8.isTerminated()) {
               var34 = null;
            } else {
               var34 = this.updateSessionData(var1, var6, var2);
            }

            var5 = var1;
            if (!this.sample()) {
               this.options.getLogger().log(SentryLevel.DEBUG, "Event %s was dropped due to sampling decision.", var1.getEventId());
               this.options.getClientReportRecorder().recordLostEvent(DiscardReason.SAMPLE_RATE, DataCategory.Error);
               var5 = null;
            }

            var7 = var34;
         } else {
            var7 = null;
            var5 = var1;
         }

         boolean var4 = this.shouldSendSessionUpdateForDroppedEvent(var8, var7);
         if (var5 == null && !var4) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Not sending session update for dropped event as it did not cause the session health to change.");
            return SentryId.EMPTY_ID;
         } else {
            SentryId var35 = SentryId.EMPTY_ID;
            SentryId var26 = var35;
            if (var5 != null) {
               var26 = var35;
               if (var5.getEventId() != null) {
                  var26 = var5.getEventId();
               }
            }

            var4 = HintUtils.hasType(var6, Backfillable.class);
            if (var5 != null && !var4 && (var5.isErrored() || var5.isCrashed())) {
               this.options.getReplayController().captureReplay(var5.isCrashed());
            }

            label135: {
               label166: {
                  label133: {
                     if (var4) {
                        if (var5 != null) {
                           try {
                              var28 = Baggage.fromEvent(var5, this.options).toTraceContext();
                              break label133;
                           } catch (IOException var22) {
                              var27 = var22;
                              break label166;
                           } catch (SentryEnvelopeException var23) {
                              var27 = var23;
                              break label166;
                           }
                        }
                     } else if (var2 != null) {
                        try {
                           var29 = var2.getTransaction();
                        } catch (IOException var20) {
                           var27 = var20;
                           break label166;
                        } catch (SentryEnvelopeException var21) {
                           var27 = var21;
                           break label166;
                        }

                        if (var29 != null) {
                           try {
                              var28 = var29.traceContext();
                              break label133;
                           } catch (IOException var18) {
                              var27 = var18;
                              break label166;
                           } catch (SentryEnvelopeException var19) {
                              var27 = var19;
                              break label166;
                           }
                        } else {
                           try {
                              var28 = TracingUtils.maybeUpdateBaggage(var2, this.options).traceContext();
                              break label133;
                           } catch (IOException var16) {
                              var27 = var16;
                              break label166;
                           } catch (SentryEnvelopeException var17) {
                              var27 = var17;
                              break label166;
                           }
                        }
                     }

                     var28 = null;
                  }

                  List var40 = (List)var9;
                  if (var5 != null) {
                     try {
                        var40 = this.getAttachments(var6);
                     } catch (IOException var14) {
                        var27 = var14;
                        break label166;
                     } catch (SentryEnvelopeException var15) {
                        var27 = var15;
                        break label166;
                     }
                  }

                  try {
                     var39 = this.buildEnvelope(var5, var40, var7, var28, null);
                     var6.clear();
                  } catch (IOException var12) {
                     var27 = var12;
                     break label166;
                  } catch (SentryEnvelopeException var13) {
                     var27 = var13;
                     break label166;
                  }

                  var30 = var26;
                  if (var39 == null) {
                     break label135;
                  }

                  try {
                     var30 = this.sendEnvelope(var39, var6);
                     break label135;
                  } catch (IOException var10) {
                     var27 = var10;
                  } catch (SentryEnvelopeException var11) {
                     var27 = var11;
                  }
               }

               this.options.getLogger().log(SentryLevel.WARNING, (Throwable)var27, "Capturing event %s failed.", var26);
               var30 = SentryId.EMPTY_ID;
            }

            if (var2 != null) {
               this.finalizeTransaction(var2, var6);
            }

            return var30;
         }
      }
   }

   @Override
   public SentryId captureMetrics(EncodedMetrics var1) {
      SentryEnvelopeItem var2 = SentryEnvelopeItem.fromMetrics(var1);
      SentryId var3 = this.captureEnvelope(
         new SentryEnvelope(new SentryEnvelopeHeader(new SentryId(), this.options.getSdkVersion(), null), Collections.singleton(var2))
      );
      if (var3 == null) {
         var3 = SentryId.EMPTY_ID;
      }

      return var3;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public SentryId captureReplayEvent(SentryReplayEvent var1, IScope var2, Hint var3) {
      Objects.requireNonNull(var1, "SessionReplay is required.");
      Hint var5 = var3;
      if (var3 == null) {
         var5 = new Hint();
      }

      if (this.shouldApplyScopeData(var1, var5)) {
         this.applyScope(var1, var2);
      }

      this.options.getLogger().log(SentryLevel.DEBUG, "Capturing session replay: %s", var1.getEventId());
      SentryId var16 = SentryId.EMPTY_ID;
      if (var1.getEventId() != null) {
         var16 = var1.getEventId();
      }

      var1 = this.processReplayEvent(var1, var5, this.options.getEventProcessors());
      SentryReplayEvent var6 = var1;
      if (var1 != null) {
         var1 = this.executeBeforeSendReplay(var1, var5);
         var6 = var1;
         if (var1 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by beforeSendReplay");
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Replay);
            var6 = var1;
         }
      }

      if (var6 == null) {
         return SentryId.EMPTY_ID;
      } else {
         TraceContext var14;
         if (var2 != null) {
            try {
               var13 = var2.getTransaction();
            } catch (IOException var10) {
               this.options.getLogger().log(SentryLevel.WARNING, var10, "Capturing event %s failed.", var16);
               SentryId var17 = SentryId.EMPTY_ID;
               return var17;
            }

            if (var13 != null) {
               try {
                  var14 = var13.traceContext();
               } catch (IOException var9) {
                  this.options.getLogger().log(SentryLevel.WARNING, var9, "Capturing event %s failed.", var16);
                  SentryId var18 = SentryId.EMPTY_ID;
                  return var18;
               }
            } else {
               try {
                  var14 = TracingUtils.maybeUpdateBaggage(var2, this.options).traceContext();
               } catch (IOException var8) {
                  this.options.getLogger().log(SentryLevel.WARNING, var8, "Capturing event %s failed.", var16);
                  SentryId var19 = SentryId.EMPTY_ID;
                  return var19;
               }
            }
         } else {
            var14 = null;
         }

         try {
            boolean var4 = HintUtils.hasType(var5, Backfillable.class);
            SentryEnvelope var15 = this.buildEnvelope(var6, var5.getReplayRecording(), var14, var4);
            var5.clear();
            this.transport.send(var15, var5);
         } catch (IOException var7) {
            this.options.getLogger().log(SentryLevel.WARNING, var7, "Capturing event %s failed.", var16);
            var16 = SentryId.EMPTY_ID;
         }

         return var16;
      }
   }

   @Override
   public void captureSession(Session var1, Hint var2) {
      Objects.requireNonNull(var1, "Session is required.");
      if (var1.getRelease() != null && !var1.getRelease().isEmpty()) {
         try {
            var4 = SentryEnvelope.from(this.options.getSerializer(), var1, this.options.getSdkVersion());
         } catch (IOException var3) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to capture session.", var3);
            return;
         }

         this.captureEnvelope(var4, var2);
      } else {
         this.options.getLogger().log(SentryLevel.WARNING, "Sessions can't be captured without setting a release.");
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public SentryId captureTransaction(SentryTransaction var1, TraceContext var2, IScope var3, Hint var4, ProfilingTraceData var5) {
      Objects.requireNonNull(var1, "Transaction is required.");
      Hint var9;
      if (var4 == null) {
         var9 = new Hint();
      } else {
         var9 = var4;
      }

      if (this.shouldApplyScopeData(var1, var9)) {
         this.addScopeAttachmentsToHint(var3, var9);
      }

      this.options.getLogger().log(SentryLevel.DEBUG, "Capturing transaction: %s", var1.getEventId());
      SentryId var21 = SentryId.EMPTY_ID;
      if (var1.getEventId() != null) {
         var21 = var1.getEventId();
      }

      SentryTransaction var22 = var1;
      if (this.shouldApplyScopeData(var1, var9)) {
         SentryTransaction var23 = this.applyScope(var1, var3);
         var1 = var23;
         if (var23 != null) {
            var1 = var23;
            if (var3 != null) {
               var1 = this.processTransaction(var23, var9, var3.getEventProcessors());
            }
         }

         var22 = var1;
         if (var1 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Transaction was dropped by applyScope");
            var22 = var1;
         }
      }

      var1 = var22;
      if (var22 != null) {
         var1 = this.processTransaction(var22, var9, this.options.getEventProcessors());
      }

      if (var1 == null) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Transaction was dropped by Event processors.");
         return SentryId.EMPTY_ID;
      } else {
         int var7 = var1.getSpans().size();
         var1 = this.executeBeforeSendTransaction(var1, var9);
         int var6;
         if (var1 == null) {
            var6 = 0;
         } else {
            var6 = var1.getSpans().size();
         }

         if (var1 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Transaction was dropped by beforeSendTransaction.");
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Transaction);
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Span, var7 + 1);
            return SentryId.EMPTY_ID;
         } else {
            if (var6 < var7) {
               var6 = var7 - var6;
               this.options.getLogger().log(SentryLevel.DEBUG, "%d spans were dropped by beforeSendTransaction.", var6);
               this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Span, var6);
            }

            label75: {
               try {
                  var20 = this.buildEnvelope(var1, this.filterForTransaction(this.getAttachments(var9)), null, var2, var5);
                  var9.clear();
               } catch (IOException var13) {
                  var18 = var13;
                  break label75;
               } catch (SentryEnvelopeException var14) {
                  var18 = var14;
                  break label75;
               }

               if (var20 == null) {
                  return var21;
               }

               try {
                  return this.sendEnvelope(var20, var9);
               } catch (IOException var11) {
                  var18 = var11;
               } catch (SentryEnvelopeException var12) {
                  var18 = var12;
               }
            }

            this.options.getLogger().log(SentryLevel.WARNING, (Throwable)var18, "Capturing transaction %s failed.", var21);
            return SentryId.EMPTY_ID;
         }
      }
   }

   @Override
   public void captureUserFeedback(UserFeedback var1) {
      Objects.requireNonNull(var1, "SentryEvent is required.");
      if (SentryId.EMPTY_ID.equals(var1.getEventId())) {
         this.options.getLogger().log(SentryLevel.WARNING, "Capturing userFeedback without a Sentry Id.");
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Capturing userFeedback: %s", var1.getEventId());

         try {
            this.sendEnvelope(this.buildEnvelope(var1), null);
         } catch (IOException var3) {
            this.options.getLogger().log(SentryLevel.WARNING, var3, "Capturing user feedback %s failed.", var1.getEventId());
         }
      }
   }

   @Override
   public void close() {
      this.close(false);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public void close(boolean var1) {
      this.options.getLogger().log(SentryLevel.INFO, "Closing SentryClient.");

      try {
         this.metricsAggregator.close();
      } catch (IOException var9) {
         this.options.getLogger().log(SentryLevel.WARNING, "Failed to close the metrics aggregator.", var9);
      }

      label39: {
         long var2;
         if (var1) {
            var2 = 0L;
         } else {
            try {
               var2 = this.options.getShutdownTimeoutMillis();
            } catch (IOException var10) {
               this.options.getLogger().log(SentryLevel.WARNING, "Failed to close the connection to the Sentry Server.", var10);
               break label39;
            }
         }

         try {
            this.flush(var2);
            this.transport.close(var1);
         } catch (IOException var8) {
            this.options.getLogger().log(SentryLevel.WARNING, "Failed to close the connection to the Sentry Server.", var8);
         }
      }

      for (EventProcessor var6 : this.options.getEventProcessors()) {
         if (var6 instanceof Closeable) {
            try {
               ((Closeable)var6).close();
            } catch (IOException var7) {
               this.options.getLogger().log(SentryLevel.WARNING, "Failed to close the event processor {}.", var6, var7);
            }
         }
      }

      this.enabled = false;
   }

   @Override
   public void flush(long var1) {
      this.transport.flush(var1);
   }

   @Override
   public IMetricsAggregator getMetricsAggregator() {
      return this.metricsAggregator;
   }

   @Override
   public RateLimiter getRateLimiter() {
      return this.transport.getRateLimiter();
   }

   @Override
   public boolean isEnabled() {
      return this.enabled;
   }

   @Override
   public boolean isHealthy() {
      return this.transport.isHealthy();
   }

   Session updateSessionData(SentryEvent var1, Hint var2, IScope var3) {
      if (HintUtils.shouldApplyScopeData(var2)) {
         if (var3 != null) {
            return var3.withSession(new SentryClient$$ExternalSyntheticLambda1(this, var1, var2));
         }

         this.options.getLogger().log(SentryLevel.INFO, "Scope is null on client.captureEvent");
      }

      return null;
   }

   private static final class SortBreadcrumbsByDate implements Comparator<Breadcrumb> {
      private SortBreadcrumbsByDate() {
      }

      public int compare(Breadcrumb var1, Breadcrumb var2) {
         return var1.getTimestamp().compareTo(var2.getTimestamp());
      }
   }
}
