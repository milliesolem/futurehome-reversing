package io.sentry.clientreport;

import io.sentry.DataCategory;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;

public final class NoOpClientReportRecorder implements IClientReportRecorder {
   @Override
   public SentryEnvelope attachReportToEnvelope(SentryEnvelope var1) {
      return var1;
   }

   @Override
   public void recordLostEnvelope(DiscardReason var1, SentryEnvelope var2) {
   }

   @Override
   public void recordLostEnvelopeItem(DiscardReason var1, SentryEnvelopeItem var2) {
   }

   @Override
   public void recordLostEvent(DiscardReason var1, DataCategory var2) {
   }

   @Override
   public void recordLostEvent(DiscardReason var1, DataCategory var2, long var3) {
   }
}
