package io.sentry;

import java.util.List;

public final class NoOpTransactionPerformanceCollector implements TransactionPerformanceCollector {
   private static final NoOpTransactionPerformanceCollector instance = new NoOpTransactionPerformanceCollector();

   private NoOpTransactionPerformanceCollector() {
   }

   public static NoOpTransactionPerformanceCollector getInstance() {
      return instance;
   }

   @Override
   public void close() {
   }

   @Override
   public void onSpanFinished(ISpan var1) {
   }

   @Override
   public void onSpanStarted(ISpan var1) {
   }

   @Override
   public void start(ITransaction var1) {
   }

   @Override
   public List<PerformanceCollectionData> stop(ITransaction var1) {
      return null;
   }
}
