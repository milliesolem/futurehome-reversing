package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import java.util.List;

public interface ITransaction extends ISpan {
   void finish(SpanStatus var1, SentryDate var2, boolean var3, Hint var4);

   void forceFinish(SpanStatus var1, boolean var2, Hint var3);

   Contexts getContexts();

   SentryId getEventId();

   Span getLatestActiveSpan();

   String getName();

   TracesSamplingDecision getSamplingDecision();

   List<Span> getSpans();

   TransactionNameSource getTransactionNameSource();

   Boolean isProfileSampled();

   Boolean isSampled();

   void scheduleFinish();

   void setContext(String var1, Object var2);

   void setName(String var1);

   void setName(String var1, TransactionNameSource var2);

   ISpan startChild(String var1, String var2, SentryDate var3);
}
