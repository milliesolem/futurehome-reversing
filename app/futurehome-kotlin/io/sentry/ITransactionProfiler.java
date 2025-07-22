package io.sentry;

import java.util.List;

public interface ITransactionProfiler {
   void bindTransaction(ITransaction var1);

   void close();

   boolean isRunning();

   ProfilingTraceData onTransactionFinish(ITransaction var1, List<PerformanceCollectionData> var2, SentryOptions var3);

   void start();
}
