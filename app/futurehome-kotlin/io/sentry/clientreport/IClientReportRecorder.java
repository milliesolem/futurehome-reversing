package io.sentry.clientreport;

import io.sentry.DataCategory;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;

public interface IClientReportRecorder {
   SentryEnvelope attachReportToEnvelope(SentryEnvelope var1);

   void recordLostEnvelope(DiscardReason var1, SentryEnvelope var2);

   void recordLostEnvelopeItem(DiscardReason var1, SentryEnvelopeItem var2);

   void recordLostEvent(DiscardReason var1, DataCategory var2);

   void recordLostEvent(DiscardReason var1, DataCategory var2, long var3);
}
