package io.sentry;

public interface IPerformanceContinuousCollector extends IPerformanceCollector {
   void clear();

   void onSpanFinished(ISpan var1);

   void onSpanStarted(ISpan var1);
}
