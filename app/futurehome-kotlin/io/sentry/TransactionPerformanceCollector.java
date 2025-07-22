package io.sentry;

import java.util.List;

public interface TransactionPerformanceCollector {
   void close();

   void onSpanFinished(ISpan var1);

   void onSpanStarted(ISpan var1);

   void start(ITransaction var1);

   List<PerformanceCollectionData> stop(ITransaction var1);
}
