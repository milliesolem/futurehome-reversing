package io.sentry;

import java.util.List;

public final class NoOpTransactionProfiler implements ITransactionProfiler {
   private static final NoOpTransactionProfiler instance = new NoOpTransactionProfiler();

   private NoOpTransactionProfiler() {
   }

   public static NoOpTransactionProfiler getInstance() {
      return instance;
   }

   @Override
   public void bindTransaction(ITransaction var1) {
   }

   @Override
   public void close() {
   }

   @Override
   public boolean isRunning() {
      return false;
   }

   @Override
   public ProfilingTraceData onTransactionFinish(ITransaction var1, List<PerformanceCollectionData> var2, SentryOptions var3) {
      return null;
   }

   @Override
   public void start() {
   }
}
